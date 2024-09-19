package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired  OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        int price = 10000;
        int stockQuantity = 10;
        Item book = createBook("시골 JPA", price, stockQuantity);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then - 비즈니스 로직 메서드 검증
        Order order = orderRepository.findOne(orderId);
        assertEquals("상품 주문 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, order.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다", price * orderCount, order.getTotalPrice());
        assertEquals("재고 수량은 주문 수량만큼 감소", stockQuantity-orderCount, book.getStockQuantity());
        assertEquals("주문 회원은 회원", order.getMember(), member);
    }

    @org.junit.Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();

        int price = 10000;
        int stockQuantity = 10;
        Item book = createBook("시골 JPA", price, stockQuantity);
        int orderCount = 11;

        //when
        orderService.order(member.getId(), book.getId(), orderCount); //exception 발생

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();

        int price = 10000;
        int stockQuantity = 10;
        Item book = createBook("시골 JPA", price, stockQuantity);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when - 테스트하고 싶은 것
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals("주문 취소 시 상태가 CANCELED 이다.", OrderStatus.CANCELLED, order.getStatus());
        assertEquals("주문 취소하면 수량이 원복되어야 한다.", stockQuantity, book.getStockQuantity());
    }

    private Book createBook(String boonName, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(boonName);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강남", "123-123"));
        em.persist(member);
        return member;
    }

}
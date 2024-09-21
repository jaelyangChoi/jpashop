package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.service.UpdateItemDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
@Getter @Setter(value = AccessLevel.PRIVATE)
public class Book extends Item {
    private String author;
    private String isbn;

    public static Book createBook(BookForm form) {
        Book book = new Book();

        if(form.getId() !=null)
            book.setId(form.getId());

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        return book;
    }

    public void update(UpdateItemDto param) {
        this.setName(param.getName());
        this.setPrice(param.getPrice());
        this.setStockQuantity(param.getStockQuantity());
        this.setAuthor(param.getAuthor());
    }
}

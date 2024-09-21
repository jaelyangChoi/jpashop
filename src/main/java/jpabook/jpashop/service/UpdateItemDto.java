package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateItemDto {
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
//    private String isbn; //isbn 은 변경 불가 정책

    public   UpdateItemDto(BookForm form) {
        this.name = form.getName();
        this.price = form.getPrice();
        this.stockQuantity = form.getStockQuantity();
        this.author = form.getAuthor();
//        this.isbn = form.getIsbn();
    }
}

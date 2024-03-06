package com.example.unittests.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.data.vo.v1.BookVO;
import com.example.models.Book;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookVO mockVO() {
        return mockVO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test " + number);
        book.setTitle("Title Test " + number);
        book.setId(number.longValue());
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setAuthor("Author Test " + number);
        book.setTitle("Title Test " + number);
        book.setKey(number.longValue());
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        return book;
    }

}

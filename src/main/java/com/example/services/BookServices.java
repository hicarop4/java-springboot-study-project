package com.example.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.controllers.BookController;
import com.example.data.vo.v1.BookVO;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.Mapper;
import com.example.models.Book;
import com.example.repositories.BookRepository;

@Service
public class BookServices {
    @Autowired
    BookRepository bookRepository;

    public List<BookVO> findAll() throws Exception {
        List<BookVO> books = Mapper.parseListObjects(bookRepository.findAll(), BookVO.class);
        books.forEach(book -> {
            try {
                book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return books;
    }

    public BookVO findById(Long id) {
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No book found with id " + id));
        BookVO bookVO = Mapper.parseObject(entity, BookVO.class);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    public BookVO create(BookVO bookVO) {
        if (bookVO == null) {
            throw new RequiredObjectIsNullException();
        }
        Book book = Mapper.parseObject(bookVO, Book.class);
        Book entity = bookRepository.save(book);
        BookVO newBookVO = Mapper.parseObject(entity, BookVO.class);
        newBookVO.add(linkTo(methodOn(BookController.class).findById(newBookVO.getKey())).withSelfRel());
        return newBookVO;
    }

    public BookVO update(BookVO bookVO) {
        if (bookVO == null) {
            throw new RequiredObjectIsNullException();
        }
        Book entity = bookRepository.findById(bookVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No book found with id " + bookVO.getKey()));
        entity.setAuthor(bookVO.getAuthor());
        entity.setLaunchDate(bookVO.getLaunchDate());
        entity.setPrice(bookVO.getPrice());
        entity.setTitle(bookVO.getTitle());
        Book newBook = bookRepository.save(entity);

        BookVO newBookVO = Mapper.parseObject(newBook, BookVO.class);
        newBookVO.add(linkTo(methodOn(BookController.class).findById(newBookVO.getKey())).withSelfRel());
        return newBookVO;
    }

    public void delete(Long id) {
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No book found with id " + id));
        bookRepository.delete(entity);
    }
}

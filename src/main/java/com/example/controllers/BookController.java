package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.vo.v1.BookVO;
import com.example.services.BookServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/book/v1")
@Tag(name = "Book", description = "Endpoints for managing books")
public class BookController {
    @Autowired
    private BookServices bookServices;

    @Operation(summary = "Find a book", description = "Search in the repository for all books registered.")
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<BookVO> findAll() throws Exception {
        return bookServices.findAll();
    }

    @Operation(summary = "Find a book", description = "Search in the repository for a book with received id")
    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public BookVO findById(@PathVariable(name = "id") Long id) {
        return bookServices.findById(id);
    }

    @Operation(summary = "Create a book", description = "Create a book in the repository")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public BookVO create(@RequestBody BookVO bookVO) {
        return bookServices.create(bookVO);
    }

    @Operation(summary = "Update a book", description = "Search in the repository for a book and update it with the following fields")
    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public BookVO update(@RequestBody BookVO bookVO) {
        return bookServices.update(bookVO);
    }

    @Operation(summary = "Delete a book", description = "Find a book by id and delete that book in the repository.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        bookServices.delete(id);
    }

}

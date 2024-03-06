package com.example.unittests.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.data.vo.v1.BookVO;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.models.Book;
import com.example.repositories.BookRepository;
import com.example.services.BookServices;
import com.example.unittests.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {
    private Logger logger = Logger.getLogger(BookServicesTest.class.getName());
    MockBook input;

    @InjectMocks
    private BookServices services;

    @Mock
    private BookRepository repository;

    @BeforeEach
    public void setup() throws Exception {
        logger.info("Setting up a book mock...");
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        Book entity = input.mockEntity();
        entity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        BookVO result = services.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(1L, result.getKey());
        assertTrue(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test 0", result.getAuthor());
        assertEquals("Title Test 0", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());

    }

    @Test
    void testCreate() throws Exception {
        Book persisted = input.mockEntity(1);
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(any(Book.class))).thenReturn(persisted);

        var result = services.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test 1", result.getAuthor());
        assertEquals("Title Test 1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testCreateWithNullBook() {
        BookVO persisted = null;
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.create(persisted);
        });
        String expectedMessage = "You can't persist a null object";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testUpdate() throws Exception {
        Book entity = input.mockEntity();
        Book persisted = input.mockEntity(1);
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(Book.class))).thenReturn(persisted);

        var result = services.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test 1", result.getAuthor());
        assertEquals("Title Test 1", result.getTitle());
        assertEquals(25D, result.getPrice());
        assertNotNull(result.getLaunchDate());
    }

    @Test
    void testUpdateWithNullBook() {
        BookVO persisted = null;
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            services.update(persisted);
        });
        String expectedMessage = "You can't persist a null object";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Book entity = input.mockEntity();
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        services.delete(1L);
    }

    @Test
    void testFindAll() throws Exception {
        List<Book> entityList = input.mockEntityList();
        when(repository.findAll()).thenReturn(entityList);
        List<BookVO> voList = services.findAll();

        int[] indexes = { 0, 6, 13 };
        for (int position : indexes) {
            var bookVO = voList.get(position);
            assertNotNull(bookVO);
            assertNotNull(bookVO.getKey());
            assertNotNull(bookVO.getLinks());

            assertTrue(
                    bookVO.toString().contains(String.format("links: [</api/book/v1/%d>;rel=\"self\"]", position)));
            assertEquals("Author Test " + position, bookVO.getAuthor());
            assertEquals("Title Test " + position, bookVO.getTitle());
            assertEquals(25D, bookVO.getPrice());
            assertNotNull(bookVO.getLaunchDate());
        }

    }

}

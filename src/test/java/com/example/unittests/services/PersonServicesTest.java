package com.example.unittests.services;

import static org.junit.jupiter.api.Assertions.*;
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

import com.example.data.vo.v1.PersonVO;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.models.Person;
import com.example.repositories.PersonRepository;
import com.example.services.PersonServices;
import com.example.unittests.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {
    private Logger logger = Logger.getLogger(PersonServicesTest.class.getName());
    MockPerson input;

    @InjectMocks
    private PersonServices personServices;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setup() throws Exception {
        logger.info("Setting up a person mock...");
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() throws Exception {
        Person entity = input.mockEntity();
        entity.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));

        PersonVO result = personServices.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertEquals(1L, result.getKey());
        assertTrue(result.toString().contains("[</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test 0", result.getAddress());
        assertEquals("First Name Test 0", result.getFirstName());
        assertEquals("Last Name Test 0", result.getLastName());
        assertEquals("Male", result.getGender());

    }

    @Test
    void testCreate() throws Exception {
        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(personRepository.save(any(Person.class))).thenReturn(persisted);

        var result = personServices.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test 1", result.getAddress());
        assertEquals("First Name Test 1", result.getFirstName());
        assertEquals("Last Name Test 1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testCreateWithNullPerson() {
        PersonVO persisted = null;
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            personServices.create(persisted);
        });
        String expectedMessage = "You can't persist a null object";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testUpdate() throws Exception {
        Person entity = input.mockEntity();
        Person persisted = input.mockEntity(1);
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(personRepository.save(any(Person.class))).thenReturn(persisted);

        var result = personServices.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Address Test 1", result.getAddress());
        assertEquals("First Name Test 1", result.getFirstName());
        assertEquals("Last Name Test 1", result.getLastName());
        assertEquals("Female", result.getGender());
    }

    @Test
    void testUpdateWithNullPerson() {
        PersonVO persisted = null;
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            personServices.update(persisted);
        });
        String expectedMessage = "You can't persist a null object";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void testDelete() {
        Person entity = input.mockEntity();
        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        personServices.delete(1L);
    }

    @Test
    void testFindAll() throws Exception {
        List<Person> entityList = input.mockEntityList();
        when(personRepository.findAll()).thenReturn(entityList);
        List<PersonVO> voList = personServices.findAll();

        int[] indexes = { 0, 6, 13 };
        for (int position : indexes) {
            var personVO = voList.get(position);
            assertNotNull(personVO);
            assertNotNull(personVO.getKey());
            assertNotNull(personVO.getLinks());

            assertTrue(
                    personVO.toString().contains(String.format("links: [</api/person/v1/%d>;rel=\"self\"]", position)));
            assertEquals("Address Test " + position, personVO.getAddress());
            assertEquals("First Name Test " + position, personVO.getFirstName());
            assertEquals("Last Name Test " + position, personVO.getLastName());
            assertEquals(position % 2 == 0 ? "Male" : "Female", personVO.getGender());
        }

    }

}

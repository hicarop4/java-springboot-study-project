package com.example.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.mapper.Mapper;
import com.example.data.vo.v1.PersonVO;
import com.example.models.Person;
import com.example.unittests.mocks.MockPerson;

public class MapperConverterTest {

    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest() {
        PersonVO output = Mapper.parseObject(inputObject.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name Test 0", output.getFirstName());
        assertEquals("Last Name Test 0", output.getLastName());
        assertEquals("Address Test 0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonVO> outputList = Mapper.parseListObjects(inputObject.mockEntityList(), PersonVO.class);
        PersonVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test 0", outputZero.getFirstName());
        assertEquals("Last Name Test 0", outputZero.getLastName());
        assertEquals("Address Test 0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        PersonVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test 7", outputSeven.getFirstName());
        assertEquals("Last Name Test 7", outputSeven.getLastName());
        assertEquals("Address Test 7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        PersonVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("First Name Test 12", outputTwelve.getFirstName());
        assertEquals("Last Name Test 12", outputTwelve.getLastName());
        assertEquals("Address Test 12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseVOToEntityTest() {
        Person output = Mapper.parseObject(inputObject.mockVO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test 0", output.getFirstName());
        assertEquals("Last Name Test 0", output.getLastName());
        assertEquals("Address Test 0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Person> outputList = Mapper.parseListObjects(inputObject.mockVOList(), Person.class);
        Person outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test 0", outputZero.getFirstName());
        assertEquals("Last Name Test 0", outputZero.getLastName());
        assertEquals("Address Test 0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        Person outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test 7", outputSeven.getFirstName());
        assertEquals("Last Name Test 7", outputSeven.getLastName());
        assertEquals("Address Test 7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        Person outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test 12", outputTwelve.getFirstName());
        assertEquals("Last Name Test 12", outputTwelve.getLastName());
        assertEquals("Address Test 12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }
}

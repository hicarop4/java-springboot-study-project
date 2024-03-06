package com.example.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.data.vo.v2.PersonVOV2;
import com.example.models.Person;

@Service
public class PersonMapper {
    public PersonVOV2 convertEntityToVOV2(Person person) {
        PersonVOV2 vov2 = new PersonVOV2();
        vov2.setId(person.getId());
        vov2.setFirstName(person.getFirstName());
        vov2.setLastName(person.getLastName());
        vov2.setAddress(person.getAddress());
        vov2.setGender(person.getGender());
        vov2.setBirthday(new Date());
        return vov2;
    }

    public Person convertVOV2ToEntity(PersonVOV2 vov2) {
        Person person = new Person();
        person.setId(vov2.getId());
        person.setFirstName(vov2.getFirstName());
        person.setLastName(vov2.getLastName());
        person.setAddress(vov2.getAddress());
        person.setGender(vov2.getGender());
        return person;
    }
}

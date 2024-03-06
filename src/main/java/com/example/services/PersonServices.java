package com.example.services;

// import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.example.controllers.PersonController;
import com.example.data.vo.v1.PersonVO;
import com.example.exceptions.RequiredObjectIsNullException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.mapper.Mapper;
// import com.example.mapper.custom.PersonMapper;
import com.example.models.Person;
import com.example.repositories.PersonRepository;

@Service
public class PersonServices {
	// private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository personRepository;

	// @Autowired
	// PersonMapper personMapper;

	public List<PersonVO> findAll() throws Exception {
		List<PersonVO> persons = Mapper.parseListObjects(personRepository.findAll(), PersonVO.class);
		persons.stream()
				.forEach(person -> {
					try {
						person
								.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
		return persons;
	}

	public PersonVO findById(Long id) throws Exception {
		logger.info("Finding one person...");
		Person entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No person found with id " + id));

		PersonVO personVO = Mapper.parseObject(entity, PersonVO.class);
		// adiciona o link de hateos para o objeto retornado pelo controller
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	public PersonVO create(PersonVO personVO) throws Exception {
		if (personVO == null) {
			throw new RequiredObjectIsNullException();
		}
		logger.info("Creating person...");
		Person person = Mapper.parseObject(personVO, Person.class);
		Person entity = personRepository.save(person);
		PersonVO newPersonVO = Mapper.parseObject(entity, PersonVO.class);
		newPersonVO.add(linkTo(methodOn(PersonController.class).findById(newPersonVO.getKey())).withSelfRel());

		return newPersonVO;
	}

	// public PersonVOV2 createV2(PersonVOV2 personVOV2) throws Exception {
	// logger.info("Creating person...");
	// Person person = personMapper.convertVOV2ToEntity(personVOV2);
	// Person entity = personRepository.save(person);
	// PersonVOV2 newPersonVOV2 = personMapper.convertEntityToVOV2(entity);
	// return newPersonVOV2;
	// }

	public PersonVO update(PersonVO personVO) throws Exception {
		if (personVO == null) {
			throw new RequiredObjectIsNullException();
		}
		logger.info("Updating person...");
		Person entity = personRepository.findById(personVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No person found with id " + personVO.getKey()));

		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		Person newPerson = personRepository.save(entity);

		PersonVO newPersonVO = Mapper.parseObject(newPerson, PersonVO.class);
		newPersonVO.add(linkTo(methodOn(PersonController.class).findById(newPersonVO.getKey())).withSelfRel());
		return newPersonVO;
	}

	public void delete(Long id) {
		logger.info("Deleting " + id + "...");
		Person entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No person found with id " + id));
		personRepository.delete(entity);
	}

	// private Person mockPerson(int id) {
	// Person person = new Person();
	// person.setId(counter.incrementAndGet());
	// person.setFirstName("Person " + id);
	// person.setLastName("Last name " + id);
	// person.setAddress("Rua dos bobos, número " + id);
	// String gender = id % 2 == 0 ? "Masculino" : "Feminino";
	// person.setGender(gender);
	// return person;
	// }

}

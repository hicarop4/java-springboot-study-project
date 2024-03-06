package com.example.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.vo.v1.PersonVO;
import com.example.services.PersonServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for managing people")
public class PersonController {

	@Autowired
	private PersonServices services;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Find all people", description = "Search in the repository for all people registered")
	public List<PersonVO> findAll() throws Exception {
		return services.findAll();
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@Operation(summary = "Find a person", description = "Search in the repository for a person with received id")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public PersonVO findById(@PathVariable(value = "id") Long id) throws Exception {
		return services.findById(id);
	}

	@CrossOrigin(origins = { "http://localhost:8080", "https://hicaro.tech" })
	@Operation(summary = "Create a person", description = "Create a person in the repository")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<PersonVO> create(@RequestBody PersonVO person) throws Exception {
		PersonVO createdPerson = services.create(person);
		return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
	}

	@Operation(summary = "Update a person", description = "Search in the repository for a person and update it with the following fields")
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<PersonVO> update(@RequestBody PersonVO person) throws Exception {
		PersonVO updatedPerson = services.update(person);
		return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
	}

	@Operation(summary = "Delete a person", description = "Find a person by id and delete that person in the repository.")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
		services.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

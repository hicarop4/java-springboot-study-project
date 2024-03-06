package com.example.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.data.vo.v1.BookVO;
import com.example.data.vo.v1.PersonVO;
import com.example.models.Book;
import com.example.models.Person;

public class Mapper {
    private static ModelMapper mapper = new ModelMapper();

    // CONFIGURAÇÃO PARA O MAPPER
    // Será inicializado pelo ClassLoader quando a classe for carregada
    static {
        mapper.createTypeMap(Person.class, PersonVO.class)
                .addMapping(Person::getId, PersonVO::setKey);
        mapper.createTypeMap(PersonVO.class, Person.class)
                .addMapping(PersonVO::getKey, Person::setId);

        mapper.createTypeMap(Book.class, BookVO.class)
                .addMapping(Book::getId, BookVO::setKey);
        mapper.createTypeMap(BookVO.class, Book.class)
                .addMapping(BookVO::getKey, Book::setId);

        // mapper.createTypeMap(Book.class, BookVO.class)
        // .addMapping(Book::getId, BookVO::setId);
        // mapper.createTypeMap(BookVO.class, Book.class)
        // .addMapping(BookVO::getKey, Book::setId);

    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        // para cada item da lista de origem,
        // mapeia para o tipo de destino e depois adiciona na lista de destino
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}

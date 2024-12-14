package com.example.safetynet.service;

import com.example.safetynet.model.Person;
import java.util.List;
import java.util.Optional;

public interface IPersonService {
    List<Person> getAllPersons();
    Optional<Person> getPerson(String firstName, String lastName);
    List<Person> getPersonsByAddress(String address);
    List<Person> getPersonsByCity(String city);
}

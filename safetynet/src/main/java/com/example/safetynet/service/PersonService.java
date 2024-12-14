package com.example.safetynet.service;

import com.example.safetynet.model.Person;
import com.example.safetynet.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService {
    private final DataRepository dataRepository;

    public PersonService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        return dataRepository.getPersons();
    }

    @Override
    public Optional<Person> getPerson(String firstName, String lastName) {
        return dataRepository.getPersons().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }

    @Override
    public List<Person> getPersonsByAddress(String address) {
        return dataRepository.getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getPersonsByCity(String city) {
        return dataRepository.getPersons().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
}

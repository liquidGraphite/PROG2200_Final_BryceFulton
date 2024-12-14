package com.example.safetynet.service;

import com.example.safetynet.model.FireStation;
import com.example.safetynet.model.Person;
import com.example.safetynet.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationService implements IFireStationService {
    private final DataRepository dataRepository;

    public FireStationService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public List<FireStation> getAllStations() {
        return dataRepository.getFirestations();
    }

    @Override
    public List<Person> getPersonsCoveredByStation(String stationNumber) {
        List<String> addresses = dataRepository.getFirestations().stream()
                .filter(fs -> fs.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        return dataRepository.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .collect(Collectors.toList());
    }

    @Override
    public String getStationNumberByAddress(String address) {
        return dataRepository.getFirestations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse("");
    }
}

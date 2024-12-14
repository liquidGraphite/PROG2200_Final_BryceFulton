package com.example.safetynet.service;

import com.example.safetynet.model.FireStation;
import com.example.safetynet.model.Person;
import java.util.List;

public interface IFireStationService {
    List<FireStation> getAllStations();
    List<Person> getPersonsCoveredByStation(String stationNumber);
    String getStationNumberByAddress(String address);
}

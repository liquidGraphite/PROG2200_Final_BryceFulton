package com.example.safetynet.service;

import com.example.safetynet.dto.*;

import java.util.List;
import java.util.Map;

public interface IAlertsService {
    FireStationCoverageDTO getCoverageByStation(String stationNumber);
    ChildAlertDTO getChildrenByAddress(String address);
    List<String> getPhonesByStation(String stationNumber);
    FireDTO getFireInfoByAddress(String address);
    FloodDTO getFloodInfoByStations(List<String> stations);
    List<PersonDetailedDTO> getPersonInfo(String firstName, String lastName);
    CommunityEmailDTO getCommunityEmails(String city);
}

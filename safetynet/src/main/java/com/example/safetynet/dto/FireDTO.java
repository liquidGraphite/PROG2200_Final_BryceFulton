package com.example.safetynet.dto;

import java.util.List;

public class FireDTO {
    private String stationNumber;
    private List<PersonDetailedDTO> residents;

    public FireDTO() {
    }

    public FireDTO(String stationNumber, List<PersonDetailedDTO> residents) {
        this.stationNumber = stationNumber;
        this.residents = residents;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public List<PersonDetailedDTO> getResidents() {
        return residents;
    }

    public void setResidents(List<PersonDetailedDTO> residents) {
        this.residents = residents;
    }
}

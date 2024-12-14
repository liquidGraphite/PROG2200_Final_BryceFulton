package com.example.safetynet.dto;

import java.util.List;

public class FireStationCoverageDTO {
    private List<PersonInfoDTO> persons;
    private int adultsCount;
    private int childrenCount;

    public FireStationCoverageDTO() {
    }

    public FireStationCoverageDTO(List<PersonInfoDTO> persons, int adultsCount, int childrenCount) {
        this.persons = persons;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
    }

    public List<PersonInfoDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonInfoDTO> persons) {
        this.persons = persons;
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public void setAdultsCount(int adultsCount) {
        this.adultsCount = adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }
}

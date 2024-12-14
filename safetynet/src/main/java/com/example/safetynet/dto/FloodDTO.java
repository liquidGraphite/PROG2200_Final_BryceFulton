package com.example.safetynet.dto;

import java.util.List;
import java.util.Map;

public class FloodDTO {
    // Map of address -> list of PersonDetailedDTO
    private Map<String, List<PersonDetailedDTO>> households;

    public FloodDTO() {
    }

    public FloodDTO(Map<String, List<PersonDetailedDTO>> households) {
        this.households = households;
    }

    public Map<String, List<PersonDetailedDTO>> getHouseholds() {
        return households;
    }

    public void setHouseholds(Map<String, List<PersonDetailedDTO>> households) {
        this.households = households;
    }
}

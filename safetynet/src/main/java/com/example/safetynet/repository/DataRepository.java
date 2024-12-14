package com.example.safetynet.repository;

import com.example.safetynet.model.FireStation;
import com.example.safetynet.model.MedicalRecord;
import com.example.safetynet.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepository {

    private List<Person> persons = new ArrayList<>();
    private List<FireStation> firestations = new ArrayList<>();
    private List<MedicalRecord> medicalrecords = new ArrayList<>();

    @PostConstruct
    private void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream("/data.json");
            DataWrapper data = mapper.readValue(is, DataWrapper.class);
            this.persons = data.getPersons();
            this.firestations = data.getFirestations();
            this.medicalrecords = data.getMedicalrecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<FireStation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    // Inner class to map data.json
    public static class DataWrapper {
        private List<Person> persons;
        private List<FireStation> firestations;
        private List<MedicalRecord> medicalrecords;

        public List<Person> getPersons() { return persons; }
        public void setPersons(List<Person> persons) { this.persons = persons; }

        public List<FireStation> getFirestations() { return firestations; }
        public void setFirestations(List<FireStation> firestations) { this.firestations = firestations; }

        public List<MedicalRecord> getMedicalrecords() { return medicalrecords; }
        public void setMedicalrecords(List<MedicalRecord> medicalrecords) { this.medicalrecords = medicalrecords; }
    }
}

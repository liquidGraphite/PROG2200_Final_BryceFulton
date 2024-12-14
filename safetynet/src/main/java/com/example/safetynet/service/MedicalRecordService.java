package com.example.safetynet.service;

import com.example.safetynet.model.MedicalRecord;
import com.example.safetynet.repository.DataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    private final DataRepository dataRepository;

    public MedicalRecordService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return dataRepository.getMedicalrecords();
    }

    @Override
    public Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName) {
        return dataRepository.getMedicalrecords().stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(firstName)
                        && m.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }
}

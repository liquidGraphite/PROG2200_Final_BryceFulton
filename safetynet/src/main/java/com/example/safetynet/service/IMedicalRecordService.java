package com.example.safetynet.service;

import com.example.safetynet.model.MedicalRecord;
import java.util.List;
import java.util.Optional;

public interface IMedicalRecordService {
    List<MedicalRecord> getAllMedicalRecords();
    Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName);
}

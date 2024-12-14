package com.example.safetynet.service;

import com.example.safetynet.dto.*;
import com.example.safetynet.model.MedicalRecord;
import com.example.safetynet.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlertsService implements IAlertsService {

    private final IPersonService personService;
    private final IFireStationService fireStationService;
    private final IMedicalRecordService medicalRecordService;

    public AlertsService(IPersonService personService, IFireStationService fireStationService, IMedicalRecordService medicalRecordService) {
        this.personService = personService;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob = LocalDate.parse(birthdate, formatter);
        return Period.between(dob, LocalDate.now()).getYears();
    }

    @Override
    public FireStationCoverageDTO getCoverageByStation(String stationNumber) {
        List<Person> persons = fireStationService.getPersonsCoveredByStation(stationNumber);
        int adults = 0;
        int children = 0;
        List<PersonInfoDTO> personInfoList = new ArrayList<>();
        for (Person p : persons) {
            Optional<MedicalRecord> mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
            int age = mr.map(m -> calculateAge(m.getBirthdate())).orElse(0);
            if (age <= 18) {
                children++;
            } else {
                adults++;
            }
            PersonInfoDTO info = new PersonInfoDTO();
            info.setFirstName(p.getFirstName());
            info.setLastName(p.getLastName());
            info.setAddress(p.getAddress());
            info.setPhone(p.getPhone());
            personInfoList.add(info);
        }

        FireStationCoverageDTO dto = new FireStationCoverageDTO();
        dto.setPersons(personInfoList);
        dto.setAdultsCount(adults);
        dto.setChildrenCount(children);
        return dto;
    }

    @Override
    public ChildAlertDTO getChildrenByAddress(String address) {
        ChildAlertDTO result = new ChildAlertDTO();

        List<Person> persons = personService.getPersonsByAddress(address);
        if (persons.isEmpty()) {
            result.setChildren(Collections.emptyList());
            return result;
        }

        List<ChildAlertDTO.ChildDTO> children = new ArrayList<>();
        for (Person p : persons) {
            Optional<MedicalRecord> mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
            int age = mr.map(m -> calculateAge(m.getBirthdate())).orElse(0);
            if (age < 18) {
                ChildAlertDTO.ChildDTO child = new ChildAlertDTO.ChildDTO();
                child.setFirstName(p.getFirstName());
                child.setLastName(p.getLastName());
                child.setAge(age);

                List<PersonInfoDTO> otherMembers = persons.stream()
                        .filter(o -> !(o.getFirstName().equalsIgnoreCase(p.getFirstName()) && o.getLastName().equalsIgnoreCase(p.getLastName())))
                        .map(o -> {
                            PersonInfoDTO info = new PersonInfoDTO();
                            info.setFirstName(o.getFirstName());
                            info.setLastName(o.getLastName());
                            return info;
                        }).collect(Collectors.toList());

                child.setOtherHouseMembers(otherMembers);
                children.add(child);
            }
        }

        result.setChildren(children);
        return result;
    }

    @Override
    public List<String> getPhonesByStation(String stationNumber) {
        return fireStationService.getPersonsCoveredByStation(stationNumber).stream()
                .map(Person::getPhone)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public FireDTO getFireInfoByAddress(String address) {
        String stationNumber = fireStationService.getStationNumberByAddress(address);
        FireDTO dto = new FireDTO();
        dto.setStationNumber(stationNumber);

        List<Person> persons = personService.getPersonsByAddress(address);
        List<PersonDetailedDTO> residents = new ArrayList<>();
        for (Person p : persons) {
            PersonDetailedDTO pd = new PersonDetailedDTO();
            pd.setFirstName(p.getFirstName());
            pd.setLastName(p.getLastName());
            pd.setPhone(p.getPhone());
            Optional<MedicalRecord> mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
            mr.ifPresent(m -> {
                pd.setAge(calculateAge(m.getBirthdate()));
                pd.setMedications(m.getMedications());
                pd.setAllergies(m.getAllergies());
            });
            residents.add(pd);
        }

        dto.setResidents(residents);
        return dto;
    }

    @Override
    public FloodDTO getFloodInfoByStations(List<String> stations) {
        // Gather all addresses covered by provided stations
        Set<String> addresses = new HashSet<>();
        for (String station : stations) {
            fireStationService.getPersonsCoveredByStation(station).forEach(p -> addresses.add(p.getAddress()));
        }

        Map<String, List<PersonDetailedDTO>> resultMap = new HashMap<>();
        for (String address : addresses) {
            List<Person> persons = personService.getPersonsByAddress(address);
            List<PersonDetailedDTO> list = new ArrayList<>();
            for (Person p : persons) {
                PersonDetailedDTO pd = new PersonDetailedDTO();
                pd.setFirstName(p.getFirstName());
                pd.setLastName(p.getLastName());
                pd.setPhone(p.getPhone());
                Optional<MedicalRecord> mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
                mr.ifPresent(m -> {
                    pd.setAge(calculateAge(m.getBirthdate()));
                    pd.setMedications(m.getMedications());
                    pd.setAllergies(m.getAllergies());
                });
                list.add(pd);
            }
            resultMap.put(address, list);
        }

        FloodDTO dto = new FloodDTO();
        dto.setHouseholds(resultMap);
        return dto;
    }

    @Override
    public List<PersonDetailedDTO> getPersonInfo(String firstName, String lastName) {
        return personService.getAllPersons().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .map(p -> {
                    PersonDetailedDTO pd = new PersonDetailedDTO();
                    pd.setFirstName(p.getFirstName());
                    pd.setLastName(p.getLastName());
                    pd.setPhone(p.getPhone());
                    pd.setAllergies(Collections.emptyList());
                    pd.setMedications(Collections.emptyList());
                    Optional<MedicalRecord> mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
                    mr.ifPresent(m -> {
                        pd.setAge(calculateAge(m.getBirthdate()));
                        pd.setMedications(m.getMedications());
                        pd.setAllergies(m.getAllergies());
                    });
                    return pd;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommunityEmailDTO getCommunityEmails(String city) {
        List<String> emails = personService.getPersonsByCity(city).stream()
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());
        CommunityEmailDTO dto = new CommunityEmailDTO();
        dto.setEmails(emails);
        return dto;
    }
}

package com.example.safetynet.controller;

import com.example.safetynet.dto.*;
import com.example.safetynet.service.IAlertsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class AlertsController {

    private static final Logger logger = LoggerFactory.getLogger(AlertsController.class);

    private final IAlertsService alertsService;

    public AlertsController(IAlertsService alertsService) {
        this.alertsService = alertsService;
    }

    /**
     * Endpoint: /firestation?stationNumber=<station_number>
     * Returns a list of persons covered by the given fire station number
     * and counts of adults and children.
     */
    @GetMapping("/firestation")
    public ResponseEntity<FireStationCoverageDTO> getPeopleCoveredByStation(@RequestParam("stationNumber") String stationNumber) {
        logger.info("Request received: GET /firestation?stationNumber={}", stationNumber);
        FireStationCoverageDTO dto = alertsService.getCoverageByStation(stationNumber);
        logger.info("Response: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint: /childAlert?address=<address>
     * Returns a list of children at a given address, including other members of the household.
     */
    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> getChildAlert(@RequestParam("address") String address) {
        logger.info("Request received: GET /childAlert?address={}", address);
        ChildAlertDTO dto = alertsService.getChildrenByAddress(address);
        logger.info("Response: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint: /phoneAlert?firestation=<firestation_number>
     * Returns a list of phone numbers of residents covered by the given fire station.
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getPhoneAlert(@RequestParam("firestation") String stationNumber) {
        logger.info("Request received: GET /phoneAlert?firestation={}", stationNumber);
        var result = alertsService.getPhonesByStation(stationNumber);
        logger.info("Response: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint: /fire?address=<address>
     * Returns the fire station number for the given address and the residents' information.
     */
    @GetMapping("/fire")
    public ResponseEntity<FireDTO> getFireInfo(@RequestParam("address") String address) {
        logger.info("Request received: GET /fire?address={}", address);
        FireDTO dto = alertsService.getFireInfoByAddress(address);
        logger.info("Response: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint: /flood/stations?stations=<a list of station_numbers>
     * Returns a list of households by address, and the residents' details for each specified station.
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<FloodDTO> getFloodInfo(@RequestParam("stations") String stations) {
        logger.info("Request received: GET /flood/stations?stations={}", stations);
        String[] stationArray = stations.split(",");
        FloodDTO dto = alertsService.getFloodInfoByStations(Arrays.asList(stationArray));
        logger.info("Response: {}", dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint: /personInfo?firstName=<firstName>&lastName=<lastName>
     * Returns the person's name, address, age, email, medications, and allergies.
     * If multiple people have the same name, return all of them.
     */
    @GetMapping("/personInfo")
    public ResponseEntity<?> getPersonInfo(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName) {
        logger.info("Request received: GET /personInfo?firstName={}&lastName={}", firstName, lastName);
        var result = alertsService.getPersonInfo(firstName, lastName);
        logger.info("Response: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint: /communityEmail?city=<city>
     * Returns a list of email addresses for everyone in the given city.
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailDTO> getCommunityEmails(@RequestParam("city") String city) {
        logger.info("Request received: GET /communityEmail?city={}", city);
        CommunityEmailDTO dto = alertsService.getCommunityEmails(city);
        logger.info("Response: {}", dto);
        return ResponseEntity.ok(dto);
    }

}

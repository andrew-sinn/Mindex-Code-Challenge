package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingUrl;
 
    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testRead() {
        Employee testEmployee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");

        // Read test
        ReportingStructure reporting = restTemplate.getForEntity(reportingUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();
        assertEquals(4, reporting.getNumOfReports());
    }
}

package com.mindex.challenge.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() {
    	
    	Compensation testCompensation = new Compensation();
    	Employee testEmployee1 = employeeRepository.findByEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");
    	Employee testEmployee2 = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    	testCompensation.setEmployee(testEmployee1);
    	testCompensation.setSalary(50000);
    	testCompensation.setEffectiveDate("01/02/2020");

        // Create checks
        Compensation pay = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertNotNull(pay.getSalary());
        assertNotNull(pay.getEffectiveDate());


        // Read checks
        pay = restTemplate.getForEntity(compensationIdUrl, Compensation.class, testEmployee2.getEmployeeId()).getBody();
        assertEquals(60000, pay.getSalary());
        assertEquals("01/01/2020", pay.getEffectiveDate());
    }
}

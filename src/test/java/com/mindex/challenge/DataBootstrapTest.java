package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired
    private DataBootstrap dataBootstrap;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;    

    @Before
    public void init() {
        dataBootstrap.init();
    }

    @Test
    public void test() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());
        
        ReportingStructure reporting = new ReportingStructure(employeeRepository, employee);
        assertNotNull(reporting);
        assertEquals(4, reporting.getNumOfReports());
        
        Compensation pay = compensationRepository.findByEmployee(employee);
        assertNotNull(employee);
        assertEquals("60000", pay.getSalary());
        assertEquals("01/01/2020", pay.getEffectiveDate());  
    }
}

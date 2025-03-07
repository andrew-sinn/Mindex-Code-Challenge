package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation pay) {
        LOG.debug("Creating compensation [{}]", pay);

        compensationRepository.insert(pay);

        return pay;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        
        Compensation pay = compensationRepository.findByEmployee(employee);

        if (pay == null) {
            throw new RuntimeException("Compensation not found for employee: " + id);
        }

        return pay;
    }
}

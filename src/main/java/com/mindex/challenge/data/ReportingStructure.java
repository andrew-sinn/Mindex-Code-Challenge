package com.mindex.challenge.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mindex.challenge.dao.EmployeeRepository;

public class ReportingStructure {

	@Autowired
    private EmployeeRepository employeeRepository;
	
	private Employee employee;
	private int numOfReports;
	
	public ReportingStructure() {
		
	}
	
	public ReportingStructure(EmployeeRepository employeeRepository, Employee employee) {
		this.employeeRepository = employeeRepository;
		this.employee = employee;
		numOfReports = calculateNumOfReports(employee);
	}
	
	public int calculateNumOfReports(Employee employee) {
		List<Employee> listEmployees = employee.getDirectReports();
		
		int numEmployees = 0;
		
		//If there are no direct reports, return 0 for the current Employee.
		if(listEmployees != null) {
			
			//Add the number of direct reports to the current Employee count
			numEmployees += listEmployees.size();
			
			for(Employee emp: listEmployees) {
				Employee singleEmployee = employeeRepository.findByEmployeeId(emp.getEmployeeId());
				
				//Check each Employee's direct reports counts.
				numEmployees += calculateNumOfReports(singleEmployee); 
			}
		}
		
		return numEmployees;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getNumOfReports() {
		return numOfReports;
	}

	public void setNumOfReports(int numOfReports) {
		this.numOfReports = numOfReports;
	}
	
}

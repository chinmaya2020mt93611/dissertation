package com.scalable.assignment;

import java.net.http.HttpClient;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AssignmentController {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@GetMapping("/getDetails")
	public String index() {
		String sql = "SELECT * FROM PATIENT";
        
		List<Map<String, Object>> employees = jdbcTemplate.queryForList(sql);
		StringBuilder sb = new StringBuilder();
		
		if (employees!=null && !employees.isEmpty()) {
			
			for (Map<String, Object> employee : employees) {
				
				for (Iterator<Map.Entry<String, Object>> it = employee.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = it.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					sb.append(key + " = " + value + "<br>");
				}
				sb.append("<br>");
				sb.append("------------------------------");
				sb.append("<br>");
			}
			
		}
		return sb.toString();
	}
	
	//CREATE TABLE COMPANY(PatientId INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE TEXT NOT NULL);
	
	/*
	 * @GetMapping("/createTable") public void tableCreate() { String sql =
	 * "CREATE TABLE PATIENT(PatientId INT PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, AGE TEXT NOT NULL)"
	 * ;
	 * 
	 * jdbcTemplate.execute(sql); }
	 */
	
	// API: POST - /add/patientDetails
	@PostMapping("/add/patientDetails")
	public void addPatient(@RequestBody PatientRecord request, HttpServletResponse response) {
		String sql = "INSERT INTO PATIENT VALUES (?::int, ?, ?)";
		jdbcTemplate.update(sql, request.patientId, request.name, request.age);
	}
	
	@PutMapping("/update/patientDetails")
	public void updatePatient(String patientid) {
		
	}
	
	@GetMapping("/fetch/patientDrugDetails/{id}")
	public String fetchDrug(@PathVariable String id) {
		String obj=  new RestTemplate().getForObject("http://localhost:8082/fetch/drugIdDetails/"+id, String.class);
		
		return obj;
	}

}
package com.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import demo.rest.EmployeeAPI;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes=EmployeeAPI.class)

public class EmployeeApiTest {

	 @Autowired
	    private TestRestTemplate template;
	 
	    // ... other methods
	 
	    @Test
	    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
	        ResponseEntity<String> result = template.withBasicAuth("admin", "abc")
	          .getForEntity("/rest/employees", String.class);
	        assertEquals(HttpStatus.OK, result.getStatusCode());
	    }
}


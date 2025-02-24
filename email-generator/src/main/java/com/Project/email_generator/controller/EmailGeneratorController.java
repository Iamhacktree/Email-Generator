package com.Project.email_generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.email_generator.model.EmailRequest;
import com.Project.email_generator.service.EmailGeneratorService;

@RestController
@RequestMapping("/api/email")
public class EmailGeneratorController {
	
	@Autowired
	EmailGeneratorService emailGeneratorService; 
	
	@PostMapping("generate")
	public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest){
		String response = emailGeneratorService.generateEmailReply(emailRequest);
		return ResponseEntity.ok(response );
	}
}

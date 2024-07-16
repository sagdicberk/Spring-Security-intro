package com.example.SpringSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class privateController {
	@GetMapping
	public String HelloPrivate() {
		return "Selam kullanıcı";
	}
	
//	@PreAuthorize("hasROLE('USER')")   // direk config dosyasından da yapılabilir. oraya bak
	@GetMapping("/user")
	public String HelloUserPrivate() {
		return "Selam USER kullanıcı";
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String HelloAdminPrivate() {
		return "Selam ADMIN kullanıcı";
	}
}

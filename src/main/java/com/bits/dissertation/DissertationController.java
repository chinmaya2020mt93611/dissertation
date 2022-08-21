package com.bits.dissertation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DissertationController {

	@GetMapping("/getDetails")
	public String index() {
		return "Hello World";
	}

}
package com.uam.predictionapp.contants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UserConstants {

	public static final String BASE_URL;
	
	@Autowired
	public static Environment env;
	
	static {
		BASE_URL = "";//env.getProperty("userManagement.baseUrl");
	}
}

package com.tdsproject.apigateway;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) throws IOException {
		FirebaseOptions options = new FirebaseOptions.Builder().
				setCredentials(GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream())).
				setProjectId("rentalm-e4a0c").
				setStorageBucket("rentalm-e4a0c.appspot.com").
				build();

		FirebaseApp.initializeApp(options);

		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

package com.sabc.digitalchampions;

import com.sabc.digitalchampions.entity.User;
import com.sabc.digitalchampions.services.UserService;
import com.sabc.digitalchampions.utils.CustomCodeGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Date;

@SpringBootApplication
public class DigitalChampionsApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	private Logger logger = LogManager.getLogger(DigitalChampionsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DigitalChampionsApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter(){
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("PUT");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedMethod("PATCH");
		corsConfiguration.addAllowedMethod("DELETE");
		corsConfiguration.addAllowedMethod("OPTIONS");
		source.registerCorsConfiguration("/**", corsConfiguration);

		return new CorsFilter(source);
	}

	@Override
	public void run(String... args) {
		if (!userService.checkAdmin()){
			User defaultUser = new User();
			defaultUser.setFirstname("John")
					.setMatricule("0000000000")
					.setLastname("Doe")
					.setPassword("admin12345")
					.setEmail("admin@custom.com")
					.setNickname("The Speaker")
					.setPhone("690000000")
					.setPhoneChecked(true)
					.setEmailChecked(true)
					.setMatricule(CustomCodeGenerator.generateUserCode())
					.setRole("ROLE_ADMIN")
					.setCreatedAt(new Date())
					.setLastUpdatedAt(new Date())
					.setEnabled(true)
					.setEnabledAt(new Date());
			try {
				if(!userService.checkAdmin()){
					userService.register(defaultUser);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}
}

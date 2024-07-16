package com.example.SpringSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // config dosyası olduğunu belirtiyoruz
@EnableWebSecurity // aktfi ediyoruz security
@EnableMethodSecurity // rest controller methodlarını korumak için
public class SecurityConfig {
	// gelen şifreyi encryp için
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService users() {
		UserDetails user1 = User.builder()
				.username("Turan")
				.password(passwordEncoder().encode("pass"))
				.roles("USER") // ikisi de aynı anlama geliyor. bi tanesi yeterli
				.build();
		
		UserDetails admin = User.builder()
				.username("Kağan")
				.password(passwordEncoder().encode("word"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user1, admin);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security.headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(x -> x.requestMatchers("/public/**", "/auth/**").permitAll()) // herkes ulaşsın
				.authorizeHttpRequests(x -> x.requestMatchers("/private/user/**").hasRole("USER"))
				.authorizeHttpRequests(x -> x.requestMatchers("/private/admin/**").hasRole("ADMIN"))
				.authorizeHttpRequests(x -> x.anyRequest().authenticated()) // kalanına zorunlu tut
				.httpBasic(Customizer.withDefaults());
		
		return security.build();
				
				
	}
	
}

package com.usuarios.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	//AUTORIZA TRANSACCIONES POR ROL
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/usuarios/inscripcion/save").hasRole("USER")
                .requestMatchers("/api/usuarios/login").hasRole("USER")
                .requestMatchers("/api/usuarios/tareas/getlistatareabyusuario").hasRole("USER")
                .requestMatchers("/api/usuarios/tareas/savetareabyusuario").hasRole("USER")
                .requestMatchers("/api/usuarios/tareas/marcartareabyusuario").hasRole("USER")
                .requestMatchers("/api/usuarios/tareas/eliminatarea/**").hasRole("USER")
                
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }
	
	//SETEA CREDENCIALES Y ROLES
	@Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("adm")
                .password(passwordEncoder.encode("adm"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
	
	// CODIFICA LLAS CLAVES
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

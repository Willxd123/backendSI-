package com.examen;

import com.examen.entity.ERole;
import com.examen.entity.RoleEntity;
import com.examen.entity.Usuario;
import com.examen.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@SpringBootApplication
public class ExamenApplication {
	private Usuario usuario;
	public static void main(String[] args) {
		SpringApplication.run(ExamenApplication.class, args);
	}

/*	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {
			Usuario usuario = Usuario.builder()
					.email("docente@gmail.com")
					.password(passwordEncoder.encode("12345678"))
					.username("docente1")
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.DOCENTE.name()))
							.build()
					)).build();

			Usuario usuario2 = Usuario.builder()
					.email("administrador@gmail.com")
					.password(passwordEncoder.encode("12345678"))
					.username("administrador2")
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()
					)).build();
			usuarioRepository.save(usuario);
			usuarioRepository.save(usuario2);
		};
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*");
			}
		};
	}
	*/
		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**")
							.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
				}
			};
		}
}

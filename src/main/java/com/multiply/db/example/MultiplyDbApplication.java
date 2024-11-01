package com.multiply.db.example;


import com.multiply.db.example.entity.db1.Cliente;
import com.multiply.db.example.entity.db1.ClienteRepository;
import com.multiply.db.example.entity.db2.Persona;
import com.multiply.db.example.entity.db2.PersonaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MultiplyDbApplication {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PersonaRepository personaRepository;

	public static void main(String[] args) {
		SpringApplication.run(MultiplyDbApplication.class, args);


	}

	@PostConstruct
	private void getAll(){
		List<Cliente> clientes = clienteRepository.findAll();
		List<Persona> personas = personaRepository.findAll();
		System.out.println(clientes);
		System.out.println("\n : "+personas);
	}

}

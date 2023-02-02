package br.com.banco.riscoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EntityScan(basePackages = {"br.com.banco.riscoapi.modelo"})
@EnableJpaRepositories(basePackages = {"br.com.banco.riscoapi.repositorio"})
@ComponentScan(basePackages = {"br.com.banco.riscoapi.servico", "br.com.banco.riscoapi.controlador"})
@SpringBootApplication
public class RiscoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiscoApiApplication.class, args);
    }

}

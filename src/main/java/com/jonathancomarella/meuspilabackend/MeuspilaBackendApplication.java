package com.jonathancomarella.meuspilabackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeuspilaBackendApplication {

    private static Logger logger = LoggerFactory.getLogger(MeuspilaBackendApplication.class);
    public static void main(String[] args) {
        logger.info("Iniciando API Meuspila");
        SpringApplication.run(MeuspilaBackendApplication.class, args);
        logger.info("API Meuspila iniciada com sucesso!");
    }

}

package com.bank.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Servidor de configuración centralizada del sistema bancario.
 * Sirve las propiedades de {@code src/main/resources/config-repo} (backend nativo)
 * a todos los microservicios; ninguno guarda configuración de infraestructura localmente.
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    /**
     * Punto de entrada del Config Server.
     *
     * @param args argumentos de línea de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}

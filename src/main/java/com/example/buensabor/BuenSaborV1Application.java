package com.example.buensabor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mercadopago.MercadoPagoConfig;

@SpringBootApplication
public class BuenSaborV1Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BuenSaborV1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MercadoPagoConfig.setAccessToken("TEST-3307395906811292-061317-9f49b3853328f8c842e309e727e9f7d8-240553608");
    }
}

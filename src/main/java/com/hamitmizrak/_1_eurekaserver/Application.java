package com.hamitmizrak._1_eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// Eureka Server(Active)
// Bu anotasyon, sınıfı bir Eureka Server olarak tanımlar.
// Eureka Server, mikroservislerin adreslerini keşfetmek ve yönetmek için kullanılır.
@EnableEurekaServer

// Bu anotasyon, sınıfı bir Spring Boot uygulaması olarak işaretler
// ve otomatik yapılandırma ile bileşen taraması sağlar.
@SpringBootApplication
public class Application {

    // Uygulamanın ana (main) metodu, Spring Boot uygulamasının başlangıç noktasıdır.
    public static void main(String[] args) {
        // SpringApplication.run, Spring uygulamasını başlatır ve Spring konteynırını oluşturur.
        SpringApplication.run(Application.class, args);
    } // end psvm

} // end Application


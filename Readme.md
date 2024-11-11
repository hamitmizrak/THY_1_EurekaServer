# Microservice Eureka Server (Discover Server)
[GitHub](https://github.com/hamitmizrak/THY_1_EurekaServer)
---

## Eureka EndPoint 
```sh 
http://localhost:8761
```

## Eureka Server (Discovery Server)
```sh 
- Eureka Server (Discovery Server)Microservislerin birbirini bulabilmesi için kayıot ve keşif sistemidir.

- Tüm diğer microservislerin kaydolduğu ve birbirini keşfettiği yerdir.
```


---
## pom.xml
```sh 

    <dependencies>

        <!--Netflix Eureka Server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <!--Lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!--Test-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
        <!--dependencyManagement-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

```

---
## @SpringBootApplicaiton
```sh 

// Eureka Server(Active)
@EnableEurekaServer
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } //end psvm

} //end Application


```

---
## Application.properties
```sh 
# Dağıtık sistemlerde Eureka Server görevi microservilerin kayıt edilmesi ve birbirini tanımlamak için kullanıyoruz.

# Spring boot uygulama adını gösteriri
spring.application.name=_1_EurekaServer

# Eureka server kullanıcı arayüzü portu
server.port=8761

# Eğer uygulamamız Eureka Server ise bu değeri false yapmamız gerekecektir.
# ÇÜNKÜ:
# Eureka Server kendini tekrar başka bir servera kayıt etmemesi için false yapıyoruz.
# Default: true
eureka.client.register-with-eureka=false

# Eureka Client'ların Eureka Server'dan servis kayıtlarının(Registry) alıp almayacağını bu ayarlar belirleriz
# Eğer false yaparsak; Kendisinin diğer sunucudan veri gelmesine gerek yoktur diyoruz.
# Default: true
eureka.client.fetch-registry=false

# Eureka server hata durumlarında kendisini korumaya alacaktır.
# Eğer false yaparsak: Eureka Server gelen ve giden istekleri sınırlarayarak Veri kaybını önleyecektir.
# Default: true
eureka.server.enable-self-preservation=false

```

---
## Eureka Server
```sh 
```

---
## Eureka Server
```sh 
```

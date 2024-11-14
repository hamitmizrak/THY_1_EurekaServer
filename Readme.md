# Microservice Eureka Server (Discover Server)
[GitHub](https://github.com/hamitmizrak/THY_1_EurekaServer)
---

| Spring Boot Sürümü | Spring Cloud Sürümü (Release Train) |
|--------------------|------------------------------------|
| 2.4.x              | 2020.0.x (Kod adı: Ilford)         |
| 2.5.x - 2.7.x      | 2021.x (Kod adı: Jubilee)          |
| 3.0.x - 3.2.x      | 2022.x (Kod adı: Kilburn)          |
| 3.3.x ve sonrası   | 2023.x ve sonrası                  |

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

Eureka Server, farklı mikroservislerin çalışıp çalışmadığını ve erişilebilir olup olmadığını, kayıtlı olan servislerin durum bilgilerini sürekli güncelleyerek sağlar. Bu, **Eureka Server’ın sağladığı durum izleme mekanizması** sayesinde mümkündür. Bir mikroservisin çalışıp çalışmadığını anlamak için aşağıdaki yöntemleri kullanabilirsiniz:

### 1. Eureka Server Web Arayüzünü Kullanmak

Eureka Server, varsayılan olarak bir **web arayüzü** sunar ve bu arayüzde kayıtlı olan tüm servislerin durumunu görebilirsiniz.

- **Eureka Server arayüzüne erişim sağlamak için**: genellikle `http://localhost:8761` adresini kullanarak bu arayüze ulaşabilirsiniz (varsayılan port 8761’dir).
- **Servis Durumları**: Web arayüzünde her bir mikroservisin çalışıp çalışmadığını görebilirsiniz. Eğer bir servis aktif ve erişilebilir durumdaysa "UP" olarak işaretlenir. Eğer bir servis erişilemez durumda ise Eureka onu "DOWN" olarak işaretleyebilir.

### 2. Heartbeat (Kalp Atışı) Mekanizması

Eureka Server, her bir mikroservisin **periyodik olarak kendisini canlı olduğunu bildirmesi** için bir heartbeat (kalp atışı) mekanizması kullanır. Mikroservisler, belirli aralıklarla Eureka Server’a bir heartbeat sinyali gönderir ve bu sinyal sayesinde Eureka Server, mikroservisin çalıştığını doğrular.

- **Heartbeat Aralığı**: Mikroservisler, `eureka.instance.lease-renewal-interval-in-seconds` ayarı ile belirlenen aralıklarla heartbeat sinyali gönderirler (örneğin, 30 saniyede bir).
- **Lease Expiration (Süre Sonu)**: Eğer bir mikroservis belirli bir süre boyunca (örneğin, `eureka.instance.lease-expiration-duration-in-seconds` ayarında tanımlı süre) heartbeat göndermezse Eureka Server, bu mikroservisi **"DOWN" (kapalı)** olarak işaretler.

```yaml
eureka:
  instance:
    lease-renewal-interval-in-seconds: 30  # Heartbeat gönderme sıklığı
    lease-expiration-duration-in-seconds: 90  # Heartbeat alınmazsa DOWN olarak işaretlenme süresi
```

### 3. API Kullanarak Mikroservis Durumlarını Sorgulama

Eureka Server, kayıtlı servislerin durumlarını sorgulamak için bir **REST API** sunar. Bu API’yi kullanarak tüm servislerin veya belirli bir servisin durumunu programatik olarak kontrol edebilirsiniz.

Örneğin:
- **Tüm Servislerin Durumunu Sorgulamak**:
    - `http://localhost:8761/eureka/apps` adresine GET isteği göndererek kayıtlı tüm servislerin durumlarını görebilirsiniz.

- **Belirli Bir Servisin Durumunu Sorgulamak**:
    - `http://localhost:8761/eureka/apps/SERVIS_ADI` adresine GET isteği göndererek belirli bir servisin durumunu sorgulayabilirsiniz. Burada `SERVIS_ADI`, sorgulamak istediğiniz servisin adıdır.

Bu istekler, her bir servisin hangi durumda olduğunu ve erişilebilir olup olmadığını gösteren JSON yanıtı döndürür.

### 4. Sağlık Kontrolü (Health Check) Entegrasyonu

Eureka Server, mikroservislerin sağlığını daha kapsamlı bir şekilde izlemek için Spring Boot Actuator ile entegre edilebilir. **Actuator’ın /health endpoint’i** üzerinden mikroservislerin sağlık durumunu alarak bu bilgiyi Eureka’ya iletmek mümkündür.

- **Actuator İle Sağlık Durumu İzleme**: Mikroservislerde Spring Boot Actuator bağımlılığını ekleyerek `http://mikroservis-url/actuator/health` adresini kullanabilirsiniz.
- **Eureka ile Health Check Entegrasyonu**: Eureka, bu endpoint’i belirli aralıklarla kontrol eder ve mikroservisin gerçekten çalışıp çalışmadığını daha kapsamlı bir şekilde izler.

```yaml
eureka:
  client:
    healthcheck:
      enabled: true  # Health check özelliğini aktif eder
management:
  endpoints:
    web:
      exposure:
        include: health  # Health endpoint'ini açar
```

### 5. Otomatik Bildirim ve İzleme Araçları

Eureka Server’ın sağladığı durum bilgileri, başka bir izleme aracıyla (örneğin **Prometheus**, **Grafana**, veya **Zipkin**) entegre edilebilir. Bu araçlarla mikroservislerin durumları ve erişilebilirlik bilgileri gerçek zamanlı olarak takip edilip bildirimler alınabilir.

### Özet

Mikroservislerin çalışıp çalışmadığını ve erişilebilir olup olmadığını anlamak için Eureka Server şu yöntemleri sunar:

1. **Eureka Web Arayüzü**: Tüm servislerin durumlarını görüntüler.
2. **Heartbeat Mekanizması**: Mikroservislerin periyodik olarak gönderdiği heartbeat sinyalleri ile servislerin durumu kontrol edilir.
3. **Eureka REST API**: Tüm servislerin veya belirli bir servisin durumunu sorgulamak için kullanılabilir.
4. **Health Check Entegrasyonu**: Spring Boot Actuator’ın /health endpoint’i ile servislerin kapsamlı sağlık kontrolü yapılır.
5. **İzleme ve Bildirim Araçlarıyla Entegrasyon**: Prometheus, Grafana gibi araçlarla entegre edilerek daha detaylı izleme ve bildirimler sağlanabilir.

Bu yöntemler, mikroservislerin çalışıp çalışmadığını, performansını ve erişilebilirliğini etkili bir şekilde izlemenizi sağlar.

---
## Eureka Server
```sh 
```


Bu bilgilere ek olarak aşağıdaki ayarları ekleyebiliriz. Her bir ayar için detaylı açıklama ekleyerek yapılandırmanın nasıl çalıştığını daha iyi anlamanızı sağlar:

```properties
# Eureka Server cluster (küme) yapılandırması
# Eğer birden fazla Eureka Server'ınız varsa, her bir sunucunun diğerleriyle iletişim kurabilmesi için
# serviceUrl.defaultZone ayarını kullanabilirsiniz. Bu, Eureka Server'ların birbirleriyle 
# veri paylaşmasını sağlar. Yüksek erişilebilirlik sağlamak amacıyla kullanılan küme yapısında
# Eureka Server'ların birbirlerini tanıması için birbirlerine kaydolmaları gerekir.
# Diğer Eureka Server'ların URL'lerini buraya ekleyin.
eureka.client.serviceUrl.defaultZone=http://localhost:8762/eureka/

# Instance ID: Her bir mikroservisin kendine özgü bir kimliği olması için
# instanceId özelliğini kullanabilirsiniz. Bu özellik, özellikle aynı uygulamanın
# birden fazla örneği çalıştığında ayırt edici bir kimlik sağlar. Böylece her bir mikroservis
# diğerlerinden ayırt edilir.
eureka.instance.instanceId=${spring.application.name}:${spring.cloud.client.hostname}

# Prefer IP Address: Eğer IP adresini hostname yerine kullanmak istiyorsanız,
# bu ayarı true yapabilirsiniz. Bu ayar, mikroservislerin IP adreslerini kullanarak 
# birbirlerini tanımasını sağlar. IP adresine dayalı olarak mikroservisler arasında
# daha hızlı ve doğru bir bağlantı kurulabilir.
eureka.instance.prefer-ip-address=true

# Eureka Sunucusu'ndaki kayıt yenileme aralığı
# eureka.server.eviction-interval-timer-in-ms, Eureka Server'ın kayıtları
# güncelleyip temizleme süresini milisaniye cinsinden belirler. Varsayılan olarak 60 saniyedir.
# Bu, Eureka Server’ın DOWN olarak işaretlenen mikroservisleri belirli aralıklarla
# kayıt listesinden çıkarmasını sağlar.
eureka.server.eviction-interval-timer-in-ms=60000

# Eureka Client'ın Eureka Server'dan yanıt alamazsa kaç kez yeniden deneyeceği
# Eğer Eureka Client bir Eureka Server'a bağlanamazsa, yeniden bağlanma girişimlerinin
# sayısını belirleyebilirsiniz. retry-limit, Client’ın yeniden bağlantı deneme sayısını 
# belirler. Böylece Eureka Server geçici olarak kapalı olduğunda yeniden bağlantı
# denemeleri sınırlandırılabilir.
eureka.client.retry-limit=5

# Eureka Server'dan kaydı yenileme sıklığı
# registry-fetch-interval-seconds, Eureka Client’ın servis kaydını güncelleme sıklığını
# ayarlamanızı sağlar. Örneğin, her 60 saniyede bir servis kaydının güncellenmesini istiyorsanız,
# bu değeri 60 olarak ayarlayabilirsiniz.
eureka.client.registry-fetch-interval-seconds=60

# Eureka Client kimlik doğrulama
# Eğer Eureka Server güvenli bir yapıdaysa ve istemcilerin doğrulanması gerekiyorsa,
# bu ayarlarla kimlik doğrulama ekleyebilirsiniz.
# Temel kimlik doğrulama için kullanıcı adı ve parola tanımlanabilir.
eureka.client.serviceUrl.defaultZone=http://username:password@localhost:8761/eureka/
```

### Eklenen Özelliklerin Detaylı Açıklaması

1. **Eureka Server Cluster (Küme) Yapılandırması**: `eureka.client.serviceUrl.defaultZone` ile birden fazla Eureka Server’ın birbirleriyle iletişime geçmesi sağlanır. Bu ayar, yüksek erişilebilirlik (high availability) için kullanılır ve birden fazla Eureka Server’ın birbirleriyle veri paylaşarak sürekli güncel kalmalarını sağlar.

2. **Instance ID**: `eureka.instance.instanceId` her mikroservise benzersiz bir kimlik sağlar. Özellikle aynı mikroservisin birden fazla örneği çalıştığında hangi örneğin aktif olduğunu ayırt etmenizi sağlar.

3. **Prefer IP Address**: `eureka.instance.prefer-ip-address=true` ile hostname yerine IP adresi kullanabilirsiniz. Mikroservisler IP üzerinden birbirlerine daha hızlı bağlanabilir, özellikle hostname üzerinden DNS çözümlemesi sorun çıkarıyorsa faydalıdır.

4. **Eviction Interval (Kayıt Yenileme Süresi)**: `eureka.server.eviction-interval-timer-in-ms`, Eureka Server’ın hangi sıklıkla DOWN olan servisleri sistemden temizleyeceğini belirler. Örneğin, bu süreyi 60 saniye yaparak her dakika güncellenmeyen kayıtları kaldırabilirsiniz.

5. **Retry Limit (Yeniden Deneme Sayısı)**: `eureka.client.retry-limit`, Eureka Client'ın, Eureka Server’a ulaşamadığında kaç kez yeniden bağlantı denemesi yapacağını belirler. Özellikle server kısa süreli kapandığında sürekli denemeleri engelleyip sınırlama sağlar.

6. **Registry Fetch Interval (Kayıt Yenileme Aralığı)**: `eureka.client.registry-fetch-interval-seconds`, mikroservisin Eureka Server’dan servis kayıtlarını hangi sıklıkla güncelleyeceğini belirler. Örneğin, 60 saniyede bir güncelleme yapılmasını sağlayabilirsiniz.

7. **Eureka Client Kimlik Doğrulama**: Eğer Eureka Server güvenlik amacıyla kimlik doğrulaması istiyorsa, `eureka.client.serviceUrl.defaultZone` ayarında kullanıcı adı ve parola ekleyerek bu doğrulamayı yapabilirsiniz. Bu, güvenliği artırmak amacıyla kullanılabilir.

Bu eklemeler, Eureka Server ve Client konfigürasyonunu daha kapsamlı hale getirerek, özellikle yüksek erişilebilirlik, güvenlik, benzersizlik ve hata durumlarında iyileştirme sağlar.


---
## Self-Preservation Modunun
```sh 
```

Bu uyarı, **Eureka Server’ın self-preservation (kendini koruma) modunun devre dışı bırakıldığını** ve bu nedenle ağ sorunları veya diğer beklenmedik hatalar durumunda kayıtlı servislerin yanlışlıkla "DOWN" olarak işaretlenip silinebilme riskinin olduğunu belirtir.

### Self-Preservation (Kendini Koruma) Modu Nedir?

Self-preservation modu, **Eureka Server’ın hata durumlarında kendini ve mikroservislerin kayıtlarını koruma amacıyla aldığı bir önlemdir**. Eğer Eureka Server, belirli bir süre boyunca bazı mikroservislerden heartbeat (kalp atışı) almazsa, ağ sorunları gibi geçici problemler nedeniyle bu mikroservisleri yanlışlıkla erişilemez durumda (DOWN) olarak işaretleyebilir. Self-preservation modu, Eureka Server’ın bu tür geçici ağ problemlerinde servis kayıtlarını korumasını sağlar.

### Self-Preservation Modunun Avantajları

Self-preservation modu etkinleştirildiğinde, Eureka Server belirli bir zaman diliminde eksik heartbeat sinyallerine rağmen kayıtları korumaya devam eder ve servisleri DOWN olarak işaretlemez. Bu özellik, özellikle aşağıdaki durumlarda faydalıdır:

1. **Ağ Sorunları**: Geçici ağ sorunlarında Eureka Server, client’ların kayıtlarını silmez ve sistemin stabilitesini korur.
2. **Beklenmeyen Hata Durumları**: Bir mikroservisin geçici olarak heartbeat gönderememesi gibi durumlarda bu servis "DOWN" olarak işaretlenmez.

### Neden Bu Uyarıyı Alıyorsunuz?

Uyarı, `eureka.server.enable-self-preservation=false` ayarını yaparak self-preservation modunu devre dışı bıraktığınızda gösterilir. Bu durumda Eureka Server, heartbeat sinyali almadığı mikroservisleri hızlıca "DOWN" olarak işaretleyebilir ve bu da bazı servislerin yanlışlıkla devre dışı kalmasına yol açabilir.

### Çözüm ve Öneriler

Eğer self-preservation modunu devre dışı bırakmamak istiyorsanız, **`eureka.server.enable-self-preservation=true`** ayarını yaparak bu özelliği etkin hale getirebilirsiniz. Bu durumda Eureka Server, geçici sorunlarda kendini koruma moduna geçecek ve servislerin kayıtlarını koruyacaktır.

```yaml
eureka:
  server:
    enable-self-preservation: true
```

Bu ayarı yaptıktan sonra:

1. **Eureka Server geçici ağ sorunları yaşasa bile kayıtları silmez**, böylece sistemin kararlılığı artar.
2. Mikroservislerin yanlışlıkla "DOWN" olarak işaretlenmesi riski azalır.

### Self-Preservation Modunu Devre Dışı Bırakmak Ne Zaman Uygundur?

Bu modu yalnızca aşağıdaki durumlarda devre dışı bırakmak uygun olabilir:

- **Geliştirme Ortamları**: Eğer sadece geliştirme ortamında test yapıyorsanız ve sürekli olarak mikroservisleri başlatıp durduruyorsanız bu modu devre dışı bırakabilirsiniz.
- **Kısa Süreli Testler**: Kısa süreli testlerde ve geçici denemelerde self-preservation modunu kapatmak isteyebilirsiniz.

Ancak **prodüksiyon ortamında self-preservation modunu açık bırakmanız önerilir**. Bu, sistemin daha kararlı çalışmasını sağlar ve geçici sorunlarda mikroservislerin kayıtlarının korunmasına yardımcı olur.

### Özet

- **Self-preservation modunu etkinleştirmek**: Prodüksiyon ortamlarında daha güvenilir bir yapı sağlar ve geçici sorunlarda servislerin yanlışlıkla "DOWN" olarak işaretlenmesini engeller.
- **Self-preservation modunu devre dışı bırakmak**: Test veya geliştirme ortamlarında kullanılabilir, ancak prodüksiyon ortamında önerilmez.

Self-preservation modunu açık tutarak, Eureka Server’ın kendini ve mikroservislerin kayıtlarını ağ problemlerine karşı korumasını sağlayabilirsiniz.

---
## Eureka Server
```sh 
```


---
## Eureka Server
```sh 
```

---
## Eureka Server
```sh 
```

---
## Eureka Server
```sh 
```

---
## Eureka Server
```sh 
```
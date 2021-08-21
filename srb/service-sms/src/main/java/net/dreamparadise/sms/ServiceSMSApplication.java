package net.dreamparadise.sms;

import net.dreamparadise.sms.util.SMSProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties({SMSProperties.class})
@ComponentScan({"net.dreamparadise"})
@EnableFeignClients
public class ServiceSMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSMSApplication.class,args);
    }
}

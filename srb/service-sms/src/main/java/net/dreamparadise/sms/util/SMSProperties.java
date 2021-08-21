package net.dreamparadise.sms.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Data
@ConfigurationProperties(prefix = "twilio.sms")
public class SMSProperties implements InitializingBean {

    private String accountSid;
    private String authToken;
    private String orgNumber;

    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;
    public static String ORG_NUMBER;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCOUNT_SID = accountSid;
        AUTH_TOKEN = authToken;
        ORG_NUMBER = orgNumber;
    }
}

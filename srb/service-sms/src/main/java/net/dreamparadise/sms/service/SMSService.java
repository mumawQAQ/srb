package net.dreamparadise.sms.service;
import com.twilio.rest.api.v2010.account.Message;


import java.util.Map;

public interface SMSService {
    Message send(String mobile, String template);
}

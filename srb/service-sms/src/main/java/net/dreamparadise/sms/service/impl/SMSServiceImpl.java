package net.dreamparadise.sms.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.common.exception.Assert;
import net.dreamparadise.common.exception.BusinessException;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.sms.service.SMSService;
import net.dreamparadise.sms.util.SMSProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class SMSServiceImpl implements SMSService {
    @Override
    public Message send(String mobile,String template) {
        Twilio.init(SMSProperties.ACCOUNT_SID, SMSProperties.AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(mobile),  // To number
                new PhoneNumber(SMSProperties.ORG_NUMBER),  // From number
                template                    // SMS body
        ).create();

        return message;

    }
}

package net.dreamparadise.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class test {
    // Find your Account Sid and Token at twilio.com/console 
    public static final String ACCOUNT_SID = "AC699cdc6b2caf8a4181fab949c3d94cf8";
    public static final String AUTH_TOKEN = "30dc0e9f68a6da39e921a6d67afba21f";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        try {
            Message message = Message.creator(
                    new PhoneNumber("+13478211187"),  // To number
                    new PhoneNumber("+16172297274"),  // From number
                    "Hello world!"                    // SMS body
            ).create();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
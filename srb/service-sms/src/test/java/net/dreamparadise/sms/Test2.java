package net.dreamparadise.sms;

import net.dreamparadise.common.util.RegexValidateUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test2 {
    @Test
    public void test(){
        System.out.println(RegexValidateUtils.checkCellphone("+1347821"));
    }

}

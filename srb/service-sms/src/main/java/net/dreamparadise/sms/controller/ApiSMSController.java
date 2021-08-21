package net.dreamparadise.sms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.common.exception.Assert;
import net.dreamparadise.common.exception.BusinessException;
import net.dreamparadise.common.result.R;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.common.util.RandomUtils;
import net.dreamparadise.common.util.RegexValidateUtils;
import net.dreamparadise.sms.client.CoreUserInfoClient;
import net.dreamparadise.sms.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/sms")
public class ApiSMSController {

    @Autowired
    private SMSService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CoreUserInfoClient coreUserInfoClient;

    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(@PathVariable String mobile){
        if(!StringUtils.isEmpty(mobile)&&mobile!=null&&RegexValidateUtils.checkCellphone(mobile)){

            R r = coreUserInfoClient.checkMobile(mobile);
            Boolean isExist = (Boolean) r.getData().get("isExist");
            log.info(String.valueOf(isExist));

            Assert.isTrue(isExist==false,ResponseEnum.MOBILE_EXIST_ERROR);

            String code = RandomUtils.getFourBitRandom();
            String template = "[srb] 验证码: " + code;
//            Message message = smsService.send(mobile,template);
            redisTemplate.opsForValue().set("srb:sms:code"+mobile,code,5, TimeUnit.MINUTES);
//            if(message.getErrorCode()!=null){
//                log.error("发送短信错误:"+message.getErrorCode()+"|"+message.getErrorMessage());
//                throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR);
//            }
        }else{
            throw new BusinessException(ResponseEnum.MOBILE_ERROR);
        }
        return R.ok().message("短信发送成功");
    }
}

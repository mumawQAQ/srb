package net.dreamparadise.sms.client.fallback;

import net.dreamparadise.common.result.R;
import net.dreamparadise.sms.client.CoreUserInfoClient;
import org.springframework.stereotype.Component;

@Component
public class CoreUserInfoClientFallback implements CoreUserInfoClient {
    @Override
    public R checkMobile(String mobile) {
        return R.ok().data("isExist",false);
    }
}

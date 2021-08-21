package net.dreamparadise.sms.client;

import net.dreamparadise.common.result.R;
import net.dreamparadise.sms.client.fallback.CoreUserInfoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "server-core",fallback = CoreUserInfoClientFallback.class)
public interface CoreUserInfoClient {
    @GetMapping("/api/core/userInfo/checkMobile/{mobile}")
    R checkMobile(@PathVariable String mobile);
}

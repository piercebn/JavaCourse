package com.example.hmily.feign;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bank2-server", fallback = Bank2ClientFallback.class)
public interface Bank2Client {

    @PostMapping("/bank2/transfer")
    @Hmily
    Boolean transfer(@RequestParam("name")String name,@RequestParam("amount") Double amount);

    @GetMapping("/bank2/hi")
    String hello();
}


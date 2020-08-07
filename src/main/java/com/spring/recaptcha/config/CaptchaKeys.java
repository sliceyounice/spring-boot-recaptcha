package com.spring.recaptcha.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "recaptcha")
@Getter
@AllArgsConstructor
public class CaptchaKeys {

    private String secret;

    private String site;

}

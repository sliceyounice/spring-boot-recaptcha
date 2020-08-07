package com.spring.recaptcha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "recaptcha")
public class CaptchaKeys {

    private String secret;

    private String site;

    public String getSecret() {
        return secret;
    }

    public String getSite() {
        return site;
    }

    public CaptchaKeys(String secret, String site) {
        this.secret = secret;
        this.site = site;
    }
}

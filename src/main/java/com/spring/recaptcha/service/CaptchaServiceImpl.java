package com.spring.recaptcha.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.recaptcha.config.CaptchaKeys;
import com.spring.recaptcha.exception.BadRequestException;
import com.spring.recaptcha.exception.ServerErrorException;
import com.spring.recaptcha.payload.CaptchaResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final CaptchaKeys captchaKeys;

    private HttpServletRequest httpServletRequest;

    protected static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    protected static final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s";

    private static final ObjectMapper mapper = new ObjectMapper();

    private String captchaSecret() {
        return this.captchaKeys.getSecret();
    }

    private String captchaSite() {
        return this.captchaKeys.getSite();
    }

    private boolean responseCheck(String response) {
        return StringUtils.hasText(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

    private String getClientIP() {
        final String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return httpServletRequest.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    public void validateCaptcha(String response) {
        if (!responseCheck(response)) {
            throw new BadRequestException("Response has invalid characters!");
        }
        String link = String.format(RECAPTCHA_URL_TEMPLATE, captchaSecret(), response, getClientIP());
        CaptchaResponse googleResponse = Unirest.post(link).asObjectAsync(CaptchaResponse.class).join().getBody();
            if (!googleResponse.isSuccess()) {
                if (googleResponse.hasClientError()) {
                    throw new BadRequestException("Unable to validate reCaptcha");
                }
                throw new ServerErrorException("Application couldn't`t perform reCaptcha validation");
            }

    }

    @Autowired
    public void setHttpServletRequest(HttpServletRequest servletRequest) {
        this.httpServletRequest = servletRequest;
    }

    public CaptchaServiceImpl(CaptchaKeys captchaKeys) {
        this.captchaKeys = captchaKeys;
    }
}

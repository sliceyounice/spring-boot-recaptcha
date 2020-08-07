package com.spring.recaptcha.web.controller;

import com.spring.recaptcha.service.CaptchaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CaptchaController {

    private final CaptchaService captchaService;

    @GetMapping
    public ModelAndView getTemplate(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping
    @ResponseBody
    public String postText(@RequestParam("g-recaptcha-response") String response, @RequestParam String text ) {
        captchaService.validateCaptcha(response);
        return text;
    }

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }
}

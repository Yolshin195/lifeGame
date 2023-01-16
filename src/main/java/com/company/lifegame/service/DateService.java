package com.company.lifegame.service;

import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("lg_DateService")
public class DateService {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    public LocalDateTime now() {
        return LocalDateTime.now(currentAuthentication.getTimeZone().toZoneId());
    }
}
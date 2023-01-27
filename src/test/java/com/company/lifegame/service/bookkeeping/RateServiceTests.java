package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.bookkeeping.Rate;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class RateServiceTests {
    @Autowired
    SystemAuthenticator authenticator;
    @Autowired
    RateService rateService;

    @BeforeEach
    void setUp() {
        authenticator.begin();
    }

    @AfterEach
    void tearDown() {
        authenticator.end();
    }

    @Test
    void findByCodeTest() {
        Optional<Rate> optionalRate = rateService.findByCode("USD", "VND", LocalDate.now());
        optionalRate.ifPresent(System.out::print);
    }
}

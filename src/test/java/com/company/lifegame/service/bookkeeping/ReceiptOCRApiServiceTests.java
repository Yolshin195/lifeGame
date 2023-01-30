package com.company.lifegame.service.bookkeeping;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ReceiptOCRApiService.class})
public class ReceiptOCRApiServiceTests {
    @Autowired
    ReceiptOCRApiService api;

    @Test
    void test() {
    }
}

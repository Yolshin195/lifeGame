package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.bookkeeping.Currency;
import com.company.lifegame.entity.bookkeeping.Rate;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component("lg_RateService")
public class RateService {
    @Autowired
    private DataManager dataManager;

    public BigDecimal convertToRUB(BigDecimal value, Currency from, LocalDateTime date) {
        if (date == null) return BigDecimal.ZERO;

        return convertToRUB(value, from,  date.toLocalDate());
    }

    public BigDecimal convertToUSD(BigDecimal value, Currency from, LocalDateTime date) {
        if (date == null) return BigDecimal.ZERO;

        return convertToUSD(value, from, date.toLocalDate());
    }

    public BigDecimal convertToRUB(BigDecimal value, Currency from, LocalDate date) {
        return convert(value, from, "RUB", date);
    }

    public BigDecimal convertToUSD(BigDecimal value, Currency from, LocalDate date) {
        return convert(value, from, "USD", date);
    }

    public BigDecimal convert(BigDecimal value, Currency from, String to, LocalDate date) {
        if (value == null || from == null) return BigDecimal.ZERO;

        return convert(value, from.getShortName(), to, date);
    }

    public BigDecimal convert(BigDecimal value, String from, String to, LocalDate date) {
        Optional<Rate> optionalRate = findByCode(from, to, date);

        if (optionalRate.isPresent()) {
            Rate rate = optionalRate.get();

            if (rate.getFrom().getShortName().equalsIgnoreCase(to)) {
                return value.divide(rate.getValue(), 2, RoundingMode.HALF_UP);
            }

            if (rate.getTo().getShortName().equalsIgnoreCase(to)) {
                return value.multiply(rate.getValue());
            }
        }

        return BigDecimal.ZERO;
    }

    private Optional<Rate> findByCode(String from, String to, LocalDate date) {

        return dataManager.load(Rate.class)
                .query("(e.code = :firstCode or e.code = :secondCode) and e.date = :date")
                .parameter("firstCode", from+to)
                .parameter("secondCode", to+from)
                .parameter("date", date)
                .optional();
    }
}
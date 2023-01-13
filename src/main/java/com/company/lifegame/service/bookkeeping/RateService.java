package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.bookkeeping.Currency;
import com.company.lifegame.entity.bookkeeping.Rate;
import io.jmix.core.DataManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component("lg_RateService")
public class RateService {
    private static final Logger log = LoggerFactory.getLogger(RateService.class);
    @Autowired
    private DataManager dataManager;

    public void createCurrentRUBVND() {
        try {
            log.info("Start createCurrentRUBVND");
            if (existCurrentRUBVND()) {
                log.info("Skip createCurrentRUBVND");
                return;
            }

            Document doc = Jsoup.connect("https://pokur.su/rub/vnd/1/").get();

            String value = doc.selectFirst("input[data-role=\"secondary-input\"]").attributes().get("value");

            log.info("createCurrentRUBVND: {}", value);


            Currency rub = getCurrency("RUB");
            Currency vnd = getCurrency("VND");

            Rate rubToVnd = dataManager.create(Rate.class);
            rubToVnd.setValue(new BigDecimal(value.replace(",", ".")));
            rubToVnd.setCode(rub.getShortName() + vnd.getShortName());
            rubToVnd.setFrom(rub);
            rubToVnd.setTo(vnd);
            rubToVnd.setDate(LocalDate.now());
            rubToVnd = dataManager.save(rubToVnd);

            log.info("Success createCurrentRUBVND: RUB to VND {} {}", rubToVnd.getValue(), rubToVnd.getDate());

        } catch (Exception e) {
            log.info("Error createCurrentRUBVND: {}", e.getMessage());
            e.printStackTrace();
        }
        log.info("End createCurrentRUBVND");
    }

    public BigDecimal convertToRUB(BigDecimal value, Currency from, LocalDateTime date) {
        if (date == null) return BigDecimal.ZERO;

        return convertToRUB(value, from, date.toLocalDate());
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
                .parameter("firstCode", from + to)
                .parameter("secondCode", to + from)
                .parameter("date", date)
                .optional();
    }

    private Currency getCurrency(String shortName) {
        return dataManager.load(Currency.class)
                .query("e.shortName = :shortName and e.deletedDate is null")
                .parameter("shortName", shortName)
                .one();
    }

    private boolean existCurrentRUBVND() {
        return !dataManager.load(Rate.class)
                .query("e.date = :date and e.code = :code")
                .parameter("date", LocalDate.now())
                .parameter("code", "RUBVND")
                .list().isEmpty();
    }
}
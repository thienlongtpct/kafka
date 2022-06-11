package lab4.exchange.service;

import lab4.exchange.dto.ExchangeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ExchangeGenerate {
    private final ExecutorService executor;
    private final KafkaTemplate<String, ExchangeInfo> kafkaTemplate;

    private final Random rand = new Random();
    private final String[] currencies = {"RUB", "VND", "EUR", "USD", "NDT", "JPY"};

    @Autowired
    public ExchangeGenerate(KafkaTemplate<String, ExchangeInfo> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.executor = Executors.newFixedThreadPool(10);
    }

    public void update() {
        for (int i = 0; i < currencies.length; ++i) {
            String fromCurrency = currencies[i];
            for (int j = i + 1; j < currencies.length; ++j) {
                String toCurrency = currencies[j];
                double maxRate = 200.;
                double minRate = 0.;
                double randomRate = minRate + rand.nextDouble() * (maxRate - minRate);
                this.executor.submit(new ExchangeLog(fromCurrency, toCurrency, randomRate));
                this.executor.submit(new ExchangeLog(toCurrency, fromCurrency, 1. / randomRate));
            }
        }
    }

    class ExchangeLog implements Callable<Boolean> {
        private final String fromCurrency;
        private final String toCurrency;
        private final double rate;

        public ExchangeLog(String fromCurrency, String toCurrency, double rate) {
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
            this.rate = rate;
        }

        @Override
        public Boolean call() {
            ExchangeInfo info = new ExchangeInfo(fromCurrency, toCurrency, rate, LocalDateTime.now());
            kafkaTemplate.send("exchange-all", info.getLog().format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss")), info);
            if (fromCurrency.equals("RUB"))
                kafkaTemplate.send("exchange-rub", info.getLog().format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss")), info);
            if (fromCurrency.equals("USD"))
                kafkaTemplate.send("exchange-usd", info.getLog().format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss")), info);
            if (fromCurrency.equals("EUR"))
                kafkaTemplate.send("exchange-eur", info.getLog().format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss")), info);
            System.out.println(info);
            return true;
        }
    }
}

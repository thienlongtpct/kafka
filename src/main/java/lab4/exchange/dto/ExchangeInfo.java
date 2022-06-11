package lab4.exchange.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExchangeInfo implements Serializable {
    private String fromCurrency;
    private String toCurrency;
    private double rate;
    private LocalDateTime log;

    public ExchangeInfo(String fromCurrency, String toCurrency, double rate, LocalDateTime log) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
        this.log = log;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public LocalDateTime getLog() {
        return log;
    }

    public void setLog(LocalDateTime log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "ExchangeInfo{" +
                "fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", rate=" + rate +
                ", log=" + log +
                '}';
    }
}

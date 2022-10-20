package io.tracker.webscraper.models;

import java.time.LocalDate;
import java.util.Date;

public class StockStats {

    private String ticker;
    private String open;
    private String close;
    private LocalDate date;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "StockStats{" +
                "ticker='" + ticker + '\'' +
                ", open='" + open + '\'' +
                ", close='" + close + '\'' +
                ", date=" + date +
                '}';
    }
}

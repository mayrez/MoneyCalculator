package model;

import java.util.Date;

public class ExchangeRate {

    private Date date;
    private Currency from;
    private Currency to;
    private Number rate;

    public ExchangeRate(Date date, Currency from, Currency to, Number rate) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public ExchangeRate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ExchangeRate load(Date date, Currency from, Currency to, Number rate) {
        return new ExchangeRate(date, from, to, rate);
    }

    public ExchangeRate load(Currency from, Currency to, Number rate) {
        return load(new Date(), from, to, rate);
    }

    public Currency getFromCurrency() {
        return from;
    }

    public Currency getToCurrency() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public Number getRate() {
        return rate;
    }

    public Currency getTo() {
        return to;
    }
}

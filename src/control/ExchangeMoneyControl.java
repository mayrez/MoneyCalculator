package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Currency;
import model.ExchangeRate;
import model.Money;
import model.MoneyExchanger;
import persistance.CurrencySetLoader;
import persistance.ExchangeRateLoader;
import ui.ConsoleCurrencyDialog;
import ui.CurrencyViewer;
import ui.MoneyViewer;

public class ExchangeMoneyControl {
    ExchangeRateLoader exchangeRateLoader;
    CurrencySetLoader currencySelLoader;
    Money money;
    Currency currency;
    ExchangeRate exchangeRate;
    
 

    public ExchangeMoneyControl(ExchangeRateLoader exchageRateLoader, CurrencySetLoader currencySetLoader, Money money, Currency currency) {
        this.money = money;
        this.currency = currency;
    }

    
    public void execute ()throws IOException {
        money = readMoney();
        currency = readCurrency();
        exchangeRate = exchangeRateLoader.load(money.getCurrency(), currency); 
        Money resultExchange = exchange(money, exchangeRate);
        new MoneyViewer(resultExchange).show();
        new CurrencyViewer(resultExchange.getCurrency()).show();
    }
    
    private Money exchange(Money money, ExchangeRate exchangeRate ) {
        MoneyExchanger exchanger = new MoneyExchanger();
        return exchanger.exchange(money, exchangeRate);
    }

    public Money readMoney() throws IOException {
          return money;
    }

    public Currency readCurrency() throws IOException {
        BufferedReader reader =  new BufferedReader ( new InputStreamReader(System.in));
        ConsoleCurrencyDialog dialog = new ConsoleCurrencyDialog(reader, currencySelLoader);
        dialog.execute();
        return dialog.getCurrency();
    }
    
}

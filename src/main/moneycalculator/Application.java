package main.moneycalculator;

import persistance.DataBaseExchangeRateLoader;
import control.ExchangeMoneyControl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Currency;
import model.Money;
import persistance.CurrencySetLoader;
import persistance.DataBaseCurrencySetLoader;
import persistance.ExchangeRateLoader;
import ui.ConsoleMoneyDialog;

public class Application {

   public static void main(String[] args) throws IOException {
        Application.execute();
    }

    Money money;
    Currency currency;

    public Application(Money money, Currency currency) {
        this.money = money;
        this.currency = currency;

    }

    private static void execute() throws IOException {
        CurrencySetLoader currencySetLoader = createCurrencySetLoader();
        ConsoleMoneyDialog moneyDialog = readMoneyConsole(currencySetLoader);
        Application application = new Application(moneyDialog.getMoney(), moneyDialog.getMoney().getCurrency());
        ExchangeMoneyControl exchangeMoneyControl = new ExchangeMoneyControl(createExchangeRateLoader(), currencySetLoader, application.money, application.currency);
        exchangeMoneyControl.execute();

    }

    private static ConsoleMoneyDialog readMoneyConsole(CurrencySetLoader currencySetLoader) {
        BufferedReader moneyReader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleMoneyDialog moneyDialog = new ConsoleMoneyDialog(moneyReader, currencySetLoader);
        moneyDialog.execute();
        return moneyDialog;
    }

    private static CurrencySetLoader createCurrencySetLoader() {
        return createDataBaseCurrencySetLoader();
    }
     private static CurrencySetLoader createDataBaseCurrencySetLoader() {
         return new DataBaseCurrencySetLoader();
     }

    private static ExchangeRateLoader createExchangeRateLoader() {
        return createDataBaseExchangeRateLoader();
    }

    private static ExchangeRateLoader createDataBaseExchangeRateLoader() {
        return new DataBaseExchangeRateLoader();
    }

    
}

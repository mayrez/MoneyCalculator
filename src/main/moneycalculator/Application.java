package main.moneycalculator;

import persistance.DataBaseExchangeRateLoader;
import control.ExchangeMoneyControl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            CurrencySetLoader currencySetLoader = createCurrencySetLoader();
            ConsoleMoneyDialog moneyDialog = readMoneyConsole(currencySetLoader);
            Application application = new Application(moneyDialog.getMoney(), moneyDialog.getMoney().getCurrency());
            ExchangeMoneyControl exchangeMoneyControl = new ExchangeMoneyControl(createExchangeRateLoader(), currencySetLoader, application.money, application.currency);
            exchangeMoneyControl.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static ConsoleMoneyDialog readMoneyConsole(CurrencySetLoader currencySetLoader) {
        BufferedReader moneyReader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleMoneyDialog moneyDialog = new ConsoleMoneyDialog(moneyReader, currencySetLoader);
        moneyDialog.execute();
        return moneyDialog;
    }

    private static CurrencySetLoader createCurrencySetLoader() throws SQLException {
        return createDataBaseCurrencySetLoader();
    }

    private static CurrencySetLoader createDataBaseCurrencySetLoader() throws SQLException {
        return new DataBaseCurrencySetLoader();
    }

    private static ExchangeRateLoader createExchangeRateLoader() throws SQLException {
        return createDataBaseExchangeRateLoader();
    }

    private static ExchangeRateLoader createDataBaseExchangeRateLoader() throws SQLException {
        return new DataBaseExchangeRateLoader();
    }
}

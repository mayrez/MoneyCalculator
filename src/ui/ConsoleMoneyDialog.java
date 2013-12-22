package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Currency;
import model.Money;
import model.Number;
import persistance.CurrencySetLoader;

public class ConsoleMoneyDialog implements MoneyDialog {

    private BufferedReader reader;
    private Money money;
    private CurrencySetLoader currencySetLoader;

    public ConsoleMoneyDialog(BufferedReader reader, CurrencySetLoader currencySetLoader) {
        this.reader = reader;
        this.currencySetLoader = currencySetLoader;
    }

    @Override
    public Money getMoney() {
        return money;
    }

    @Override
    public void execute() {
        try {
            money = new Money(Number.valueOf(readAmount(reader)), readCurrency());
        } catch (NumberFormatException ex) {
            System.out.println("No ha introducido un número");
        } catch (IOException ex) {
            System.out.println("Se ha producido un error");
        }
    }

    private static String readAmount(BufferedReader reader) throws IOException {
        System.out.print("Introduzca la cantidad de dinero a convertir: ");
        return reader.readLine();
    }

    public Currency readCurrency() {
        BufferedReader currencyReader = new BufferedReader(new InputStreamReader(System.in));
        CurrencyDialog dialog = new ConsoleCurrencyDialog(currencyReader, currencySetLoader);
        dialog.execute();
        return dialog.getCurrency();
    }
}

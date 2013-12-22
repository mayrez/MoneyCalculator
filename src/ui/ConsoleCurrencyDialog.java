package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import persistance.CurrencySetLoader;

public class ConsoleCurrencyDialog implements CurrencyDialog {

    private Currency currency;
    private BufferedReader reader;
    private CurrencySetLoader currencySetLoader;


    public ConsoleCurrencyDialog(BufferedReader reader, CurrencySetLoader currencySetLoader) {
        this.reader = reader;
        this.currencySetLoader = currencySetLoader;
    }

    @Override
    public void execute() {
        try {
            currency = currencySetLoader.load().get(readCurrency());
        } catch (IOException ex) {
            Logger.getLogger(ConsoleCurrencyDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String readCurrency() throws IOException {
        System.out.print("Introduzca una divisa: ");
        return reader.readLine();
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }
}

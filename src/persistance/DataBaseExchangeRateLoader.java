package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import model.ExchangeRate;
import model.Number;

public class DataBaseExchangeRateLoader implements ExchangeRateLoader {

    private Connection connection;

    public DataBaseExchangeRateLoader() {
    }

    @Override
    public ExchangeRate load(Currency from, Currency to, Date date) {
        try {
            createConnection();
            if (from.getCode().equalsIgnoreCase(to.getCode())) {
                return new ExchangeRate(from, to, new Number(1));
            }

            if (from.getCode().equalsIgnoreCase("EUR")) {
                return new ExchangeRate(from, to, getExchangeRate_EUR_to(to, date));
            } else {
                return new ExchangeRate(from, to,
                        Number.multiplicate(getExchangeRate_from_EUR(from, date), getExchangeRate_EUR_to(to, date)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            createConnection();
            if (from.getCode().equalsIgnoreCase(to.getCode())) {
                return new ExchangeRate(from, to, new Number(1));
            }

            if (from.getCode().equalsIgnoreCase("EUR")) {
                return new ExchangeRate(from, to, getExchangeRate_EUR_to(to));
            } else {
                return new ExchangeRate(from, to,
                        Number.multiplicate(getExchangeRate_from_EUR(from), getExchangeRate_EUR_to(to)));
            }


        } catch (SQLException ex) {
            Logger.getLogger(DataBaseExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Connection createConnection() throws SQLException {

        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "system");

        return connection;

    }

    private Number getExchangeRate_EUR_to(Currency currency) {
        if (currency.getCode().equalsIgnoreCase("EUR")) {
            return new Number(1);
        }
        String query = "SELECT * FROM CAMBIO_EUR_A WHERE DIVISA = '" + currency.getCode() + "'";
        return getRateFromDataBase(query);
    }

    private Number getExchangeRate_from_EUR(Currency from) {
        return new Number(1).divide(getExchangeRate_EUR_to(from));

    }

    private Number getExchangeRate_EUR_to(Currency currency, Date date) {
        if (currency.getCode().equalsIgnoreCase("EUR")) {
            return new Number(1);
        }
        String query = "SELECT * FROM CAMBIO_EUR_A WHERE DIVISA = '" + currency.getCode() + "and" + "WHERE alta=" + date + "'";
        return getRateFromDataBase(query);
    }

    private Number getExchangeRate_from_EUR(Currency from, Date date) {
        return new Number(1).divide(getExchangeRate_EUR_to(from));

    }

    private Number getRateFromDataBase(String query) {
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            if (!result.next()) {
                throw new SQLException();
            }

            return new Number(result.getBigDecimal("CAMBIO").doubleValue());
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
            return new Number(0);
        }

    }
}

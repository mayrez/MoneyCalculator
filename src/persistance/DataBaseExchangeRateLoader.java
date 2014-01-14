package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import model.ExchangeRate;
import model.Number;

public class DataBaseExchangeRateLoader implements ExchangeRateLoader {

 
    private Statement statement;
    private Connection connection;
    private ResultSet resultSet;

    public DataBaseExchangeRateLoader() {
      
    }

    @Override
    public ExchangeRate load(Currency from, Currency to, Date date) {
        try {
            createStatement();
            createResultSet(date, to);
            while (resultSet.next()) {
                if (resultSet.getString("DIVISA").equalsIgnoreCase(to.getCode().toString())) {
                    return new ExchangeRate().load(from, to, Number.valueOf(resultSet.getString("CAMBIO")));
                }
                System.out.print(resultSet.getString("DIVISA") + " ");
                System.out.println(resultSet.getBigDecimal("CAMBIO"));

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
            createStatement();
            createresultSet(to);
            while (resultSet.next()) {
                if (resultSet.getString("DIVISA").equalsIgnoreCase(to.getCode().toString())) {
                    return new ExchangeRate().load(from, to, Number.valueOf(resultSet.getString("CAMBIO")));
                }
                System.out.print(resultSet.getString("DIVISA") + " ");
                System.out.println(resultSet.getBigDecimal("CAMBIO"));

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
    private void createStatement() {
        try {
            statement = connection.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseCurrencySetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createResultSet(Date date, Currency to) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM CAMBIO_EUR_A WHERE DIVISA = '" + to.getCode() + "'" + "and" + "WHERE alta=" + date);
    }

    private void createresultSet(Currency to) throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM CAMBIO_EUR_A WHERE DIVISA = '" + to.getCode() + "'");
    }
}

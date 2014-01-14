package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import model.CurrencySet;

public class DataBaseCurrencySetLoader implements CurrencySetLoader {

    private ResultSet resultSet;
    private Connection connection;
    private Statement statement;

    public DataBaseCurrencySetLoader() {
    }

    @Override
    public CurrencySet load() {
        CurrencySet set = CurrencySet.getInstance();
        try {
            createConnection();
            createResultSet();
            set.add(new Currency("EUR", "", ""));
            while (resultSet.next()) {
                set.add(new Currency("DIVISA", "", ""));
            }


        } catch (SQLException ex) {
            Logger.getLogger(DataBaseCurrencySetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return set;
    }

    private Connection createConnection() throws SQLException {

        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "system");

        return connection;

    }

    private void createResultSet() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CAMBIO_EUR_A");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseCurrencySetLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

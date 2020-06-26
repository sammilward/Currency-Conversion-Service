/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam Milward
 */
public class CurrencyRepository {
    
    private final String username = "SA";
    private final String password = "Password1";
    private final String CURRENCYTABLE = "tbl_Currency";
    
    private Connection connection;
    private CurrencyMapper currencyMapper;
    
    public CurrencyRepository()
    {
        currencyMapper = new CurrencyMapper();        
        try {
            connection = getConnection();
        } catch (Exception ex) {
            Logger.getLogger(CurrencyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Connection getConnection() throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.username);
        connectionProps.put("password", this.password);
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection("jdbc:sqlserver://SM-HP\\SQLEXPRESS;DatabaseName=CurrencyConversion;user=sa;password=Password1");     
        System.out.println("Connected to database");
        return conn;
    }
    
    public List<Currency> GetCurrency()
    {
        List<Currency> currencies = new ArrayList<Currency>();
        String query = String.format("SELECT * FROM %s", CURRENCYTABLE);
        try {
            Statement statement = connection.createStatement();
            if(statement.execute(query))
            {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next())
                {
                    currencies.add(currencyMapper.ResultSetToCurrency(statement.getResultSet()));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currencies;
    }
    
    public Currency GetCurrency(String Code)
    {
        Currency currency = null;
        String query = String.format("SELECT * FROM %s WHERE Code = '%s'", CURRENCYTABLE, Code) ;
        try {
            Statement statement = connection.createStatement();
            if(statement.execute(query))
            {
                ResultSet resultSet = statement.getResultSet();
                if(resultSet.next())
                {
                    currency = currencyMapper.ResultSetToCurrency(resultSet);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currency;
    }
    
    public void UpdateCurrency(Currency currency)
    {
        String query = String.format("UPDATE %s set RateInEUR = %f, UpdatedTime = CURRENT_TIMESTAMP WHERE Code = '%s'", CURRENCYTABLE, currency.getRateInEUR() ,currency.getCode()) ;
        try {
            Statement statement = connection.createStatement();
            if(statement.execute(query))
            {
                int updatedCount = statement.getUpdateCount();
                if(!(updatedCount == 1))
                {
                    System.err.println("Can not update currency with code:" + currency.getCode());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CurrencyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void UpdateCurrencies(List<Currency> currencies)
    {
        for (Currency curr : currencies) {
            UpdateCurrency(curr);
        }
    }
}

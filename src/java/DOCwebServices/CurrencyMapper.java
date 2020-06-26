/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Sam Milward
 */
public class CurrencyMapper {
    
    private final String CODECOLUMN = "Code";
    private final String NAMECOLUMN = "Name";
    private final String RATEINEURCOLUMN = "RateInEUR";
    private final String UPDATEDTIMECOLUMN = "UpdatedTime";
    
    public Currency ResultSetToCurrency(ResultSet resultSet)
    {
        Currency currency = new Currency();
        try {
                currency.setCode(resultSet.getString(CODECOLUMN));
                currency.setName(resultSet.getString(NAMECOLUMN));
                currency.setRateInEUR(resultSet.getDouble(RATEINEURCOLUMN));
                Timestamp timestamp = resultSet.getTimestamp(UPDATEDTIMECOLUMN);
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                currency.setUpdatedTime(localDateTime.toString());
        } catch (Exception ex) {
            Logger.getLogger(CurrencyMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currency;
    }
    
    public List<Currency> JsonToCurrencyList(JSONObject json)
    {
        Map rates = ((Map)json.get("rates"));

        ArrayList<Currency> currencies = new ArrayList<Currency>();
        Iterator<Map.Entry> itr1 = rates.entrySet().iterator();

        while (itr1.hasNext()) {
            Map.Entry jsonMap = itr1.next();
            Currency currency = new Currency();
            currency.setCode(jsonMap.getKey().toString());
            currency.setRateInEUR(Double.parseDouble(jsonMap.getValue().toString()));
            currencies.add(currency);
        }  

        return currencies;
    }
}

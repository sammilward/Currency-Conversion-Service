/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DOCwebServices;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.jws.WebService;
import org.json.simple.JSONObject;

/**
 *
 * @author Sam Milward
 */
@WebService()
public class CurrencyConversionWS {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final FixerAPILatestInvoker fixerAPIInvoker;
    
    private LocalDateTime apiLastCalled;
       
    public CurrencyConversionWS()
    {
        currencyRepository = new CurrencyRepository();
        currencyMapper = new CurrencyMapper();
        fixerAPIInvoker = new FixerAPILatestInvoker();
        apiLastCalled = null;
    }
      
    public double GetConversionRate(String cur1, String cur2) {
        if(!RatesUpToDate()) UpdateDatebase();
        Currency currency1 = currencyRepository.GetCurrency(cur1);
        Currency currency2 = currencyRepository.GetCurrency(cur2);
        
        try {
            double rate1 = currency1.getRateInEUR();
            double rate2 = currency2.getRateInEUR();
            return rate2/rate1;
        }
        catch (IllegalArgumentException iae) {
            return -1;
        }
    }
    
    public ConvertResponce Convert(double no, String cur1, String cur2)
    {
        if(!RatesUpToDate()) UpdateDatebase();
        Currency currency1 = currencyRepository.GetCurrency(cur1);
        Currency currency2 = currencyRepository.GetCurrency(cur2);
        
        try {
            double conversionRate = currency2.getRateInEUR()/currency1.getRateInEUR();
            ConvertResponce responce = new ConvertResponce();
            responce.setValue(no*conversionRate);
            responce.setDateOfConverstion(currency1.getUpdatedTime());
            
            return responce;
        }
        catch (IllegalArgumentException iae) {
            ConvertResponce convertResponce = new ConvertResponce();
            convertResponce.setDateOfConverstion("NA");
            convertResponce.setValue(-1);
            return convertResponce;
        }
    }

    public List<Currency> GetCurrencies() {
        if(!RatesUpToDate()) UpdateDatebase();
        List<Currency> currencies = currencyRepository.GetCurrency();
        return currencies;
    }
    
    private boolean RatesUpToDate()
    {
        LocalDateTime currentMinusAnHour = LocalDateTime.now().minusHours(1);
        if(apiLastCalled == null || currentMinusAnHour.isAfter(apiLastCalled))
        //If Api has not been called since start, or has not been updated within the hour
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    private void UpdateDatebase()
    {
        JSONObject json = fixerAPIInvoker.InvokeLatestRates();
        if(json != null)
        {
            if(Boolean.parseBoolean(json.get("success").toString()))
            {
                long epochupdated = Long.parseLong(json.get("timestamp").toString());
                apiLastCalled = Instant.ofEpochSecond(epochupdated).atZone(ZoneId.systemDefault()).toLocalDateTime();
                List<Currency> currencies = currencyMapper.JsonToCurrencyList(json);
                currencyRepository.UpdateCurrencies(currencies);
           }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Sam Milward
 */
public class FixerAPILatestInvoker {    
    private final String ACCESSKEY = "ACCESSKEY";
    private final String BASEURL = "http://data.fixer.io/api/";
    private final String BASECURRENCY = "EUR";
    private final String LATESTENDPOINT = "latest";
    
    public JSONObject InvokeLatestRates()
    {
        String latestRatesEndpointUri = BASEURL + LATESTENDPOINT + "?access_key=" + ACCESSKEY + "&base=" + BASECURRENCY;
        try {
            HttpURLConnection conn = CreateConnection(new URL(latestRatesEndpointUri), "GET");
            int status = conn.getResponseCode();
            if (status == 200)
            {
                String content = GetResponseContent(conn);
                conn.disconnect();

                Object obj = new JSONParser().parse(content);
                JSONObject jo = (JSONObject) obj;
                
                return jo;
            }
            } catch (MalformedURLException ex) {
                Logger.getLogger(FixerAPILatestInvoker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ProtocolException ex) {
                Logger.getLogger(FixerAPILatestInvoker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FixerAPILatestInvoker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FixerAPILatestInvoker.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
    } 
    
    private String GetResponseContent(HttpURLConnection conn)
    {
        StringBuffer content = new StringBuffer();
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (Exception ex) {
            Logger.getLogger(FixerAPILatestInvoker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content.toString();
    }
    
    private HttpURLConnection CreateConnection(URL url, String HTTPMethod)
    {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HTTPMethod);
        } catch (ProtocolException ex) {
            Logger.getLogger(FixerAPILatestInvoker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FixerAPILatestInvoker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}

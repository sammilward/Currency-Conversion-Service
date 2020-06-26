/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 *
 * @author Sam Milward
 */
public class Currency {
    private String Code;
    private String Name;
    private double RateInEUR;
    private String UpdatedTime;

    /**
     * @return the Code
     */
    public String getCode() {
        return Code;
    }

    /**
     * @param Code the Code to set
     */
    public void setCode(String Code) {
        this.Code = Code;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the RateInGBP
     */
    public double getRateInEUR() {
        return RateInEUR;
    }

    /**
     * @param RateInEUR the RateInGBP to set
     */
    public void setRateInEUR(double RateInEUR) {
        this.RateInEUR = RateInEUR;
    }

    /**
     * @return the UpdatedTime
     */
    public String getUpdatedTime() {
        return UpdatedTime;
    }

    /**
     * @param UpdatedTime the UpdatedTime to set
     */
    public void setUpdatedTime(String UpdatedTime) {
        this.UpdatedTime = UpdatedTime;
    }
}

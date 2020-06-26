/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

/**
 *
 * @author Sam Milward
 */
public class ConvertResponce {
    private String DateOfConverstion;
    private double value;

    /**
     * @return the DateOfConverstion
     */
    public String getDateOfConverstion() {
        return DateOfConverstion;
    }

    /**
     * @param DateOfConverstion the DateOfConverstion to set
     */
    public void setDateOfConverstion(String DateOfConverstion) {
        this.DateOfConverstion = DateOfConverstion;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
}

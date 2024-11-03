/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.shedule;

import entity.shedule.Employee;

/**
 *
 * @author Admin
 */
public class SchedualEmployee {
    private int seid;
    private SchedualCampaign sc;
    private Employee e;
    private int quantity;

    public int getSeid() {
        return seid;
    }

    public void setSeid(int seid) {
        this.seid = seid;
    }

    public SchedualCampaign getSc() {
        return sc;
    }

    public void setSc(SchedualCampaign sc) {
        this.sc = sc;
    }

    public Employee getE() {
        return e;
    }

    public void setE(Employee e) {
        this.e = e;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

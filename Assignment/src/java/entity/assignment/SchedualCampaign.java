/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.assignment;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class SchedualCampaign {
//    [ScID]
//      ,[PlanCampnID]
//      ,[Date]
//      ,[Shift]
//      ,[Quantity]
    private Plan plan;
    private PlanCampaign plancampn;
    private Date date;
    private int id, shift , quantity;
    
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SchedualCampaign{" + "plan=" + plan.getId() + ", plancampn=" + plancampn.getId() + ", date=" + date + ", id=" + id + ", shift=" + shift + ", quantity=" + quantity + '}';
    }

    public void setId(int scid) {
        this.id = scid;
    }

    public PlanCampaign getPlancampn() {
        return plancampn;
    }

    public void setPlancampn(PlanCampaign plancampn) {
        this.plancampn = plancampn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

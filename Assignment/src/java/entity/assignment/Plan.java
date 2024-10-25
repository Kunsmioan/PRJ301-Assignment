/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.assignment;
import dal.assignment.PlanCampaignDBContext;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author sonnt-local
 */
public class Plan {
    private int id;
    private String name;
    private Date start;
    private Date end;
    private Department dept;
    private int quantity;
    

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private ArrayList<PlanCampaign> campains = new ArrayList<>();

    public ArrayList<PlanCampaign> getCampains() {
        return campains;
    }

    public void setCampains(ArrayList<PlanCampaign> campains) {
        this.campains = campains;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.shedule;

/**
 *
 * @author Admin
 */
public class Attendence {
    private int id;
    private int quantity;
    private float alpha;
    private SchedualEmployee se;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public SchedualEmployee getSe() {
        return se;
    }

    public void setSe(SchedualEmployee se) {
        this.se = se;
    }
    
}

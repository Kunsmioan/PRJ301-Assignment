/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.assignment;

/**
 *
 * @author Admin
 */
public class ShiftData {
    private int productId;
    private String productName;
    private int shift;
    private int quantity;

    public ShiftData(int productId, String productName, int shift, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.shift = shift;
        this.quantity = quantity;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getShift() {
        return shift;
    }

    public int getQuantity() {
        return quantity;
    }
}

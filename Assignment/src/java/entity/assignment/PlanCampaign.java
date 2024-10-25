/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.assignment;

import dal.assignment.ProductDBContext;
import java.util.ArrayList;

/**
 *
 * @author sonnt-local
 */
public class PlanCampaign {
    private int id;
    private Plan plan;
    private Product product;
    private int quantity;
    private float estimate;
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
      private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        ProductDBContext db = new ProductDBContext();
        for(Product p : db.list()){
            if(p.getId() == (id)){
                products.add(p);
            }
        }
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getEstimate() {
        return estimate;
    }

    public void setEstimate(float estimate) {
        this.estimate = estimate;
    }

}

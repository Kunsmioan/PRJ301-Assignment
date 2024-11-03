/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.shedule;

import entity.shedule.Attendence;
import entity.accesscontrol.Role;
import entity.assignment.Department;
import entity.assignment.Product;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class Employee {
    private int id;
    private String name, address;
    private boolean gender;
    private Role role;
    private Date date;
    private Department department;
    private float salary;
    private Attendence attendence;
    private Product product;
    private SchedualEmployee se;
    private SchedualCampaign sc;

    public Attendence getAttendence() {
        return attendence;
    }

    public void setAttendence(Attendence attendence) {
        this.attendence = attendence;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SchedualEmployee getSe() {
        return se;
    }

    public void setSe(SchedualEmployee se) {
        this.se = se;
    }

    public SchedualCampaign getSc() {
        return sc;
    }

    public void setSc(SchedualCampaign sc) {
        this.sc = sc;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
    
}

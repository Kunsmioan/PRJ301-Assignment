/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity.assignment;

/**
 *
 * @author Admin
 */
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DateShiftData {
    private Date date;
    private List<ShiftData> shifts;

    public DateShiftData(Date date) {
        this.date = date;
        this.shifts = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public List<ShiftData> getShifts() {
        return shifts;
    }

    public void addShift(ShiftData shiftData) {
        this.shifts.add(shiftData);
    }
}

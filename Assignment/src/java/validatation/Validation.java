/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validatation;

/**
 *
 * @author Admin
 */
public class Validation {
    public String nameValid(String name){
        String words[] = name.split(" ");
        name = "";
        for(String s : words){
            name += s.substring(0, 1).toUpperCase()
                    +s.substring(1, s.length()).toLowerCase()
                    +" ";
        }
        return name;
    }
}

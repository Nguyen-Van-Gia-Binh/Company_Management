package model;

import java.io.Serializable;

/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
public class Tester extends Employee implements Serializable {

    private double bonusRate;
    private String type;

// Constructor
    public Tester(double bonusRate, String type, String empID, String empName, double baseSal) {
        super(empID, empName, baseSal);
        this.bonusRate = bonusRate;
        this.type = type;
    }

    // Get and set 
    public double getBonusRate() {
        return bonusRate;
    }

    public String getType() {
        return type;
    }

    public void setBonusRate(double bonusRate) {
        this.bonusRate = bonusRate;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public double getSalary() {
        return baseSal + bonusRate * baseSal;
    }

    @Override
    public String toString() {
        return empID + "_" + empName + "_" + baseSal + "_" + type + "_" + bonusRate;
    }

}

/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package model;

import java.io.Serializable;

public abstract class Employee implements Serializable {

    //Attri
    protected String empID;
    protected String empName;
    protected double baseSal;

    // constructor
    public Employee(String empID, String empName, double baseSal) {
        this.empID = empID;
        this.empName = empName;
        this.baseSal = baseSal;
    }

    // Get & Set
    public String getEmpID() {
        return empID;
    }

    // setId is not allowed 
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getBaseSal() {
        return baseSal;
    }

    public void setBaseSal(double baseSal) {
        this.baseSal = baseSal;
    }

    // Method chung
    public abstract double getSalary();

    @Override
    public String toString() {
        return empID + "_" + empName + "_" + baseSal;
    }

}

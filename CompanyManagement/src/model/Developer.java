/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Developer extends Employee implements Serializable {

    private String teamName;
    private ArrayList<String> programmingLanguages;
    private int expYear;

    // Constructor  
    public Developer(String teamName, ArrayList<String> programmingLanguages, int expYear, String empID, String empName, double baseSal) {
        super(empID, empName, baseSal);
        this.teamName = teamName;
        this.programmingLanguages = programmingLanguages;
        this.expYear = expYear;
    }

// get and set => Create để hiện thực tính đóng gói
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<String> getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(ArrayList<String> programmingLanguages) {

        this.programmingLanguages = programmingLanguages;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

// Ghi đè phương thức 
    @Override
    public double getSalary() {
        if (expYear >= 10) {
            return baseSal + expYear * 2000000;
        } else if (expYear >= 3) {
            return baseSal + expYear * 1000000;
        } else {
            return baseSal;
        }
    }

    @Override
    public String toString() {
        return empID + "_" + empName + "_" + baseSal + "_" + teamName + "_" + expYear + "_" + programmingLanguages;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setBaseSal(double baseSal) {
        this.baseSal = baseSal;
    }

}

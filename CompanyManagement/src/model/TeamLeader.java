/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

public class TeamLeader extends Developer implements Serializable {

    private double bonusRate;

//constructor   
    public TeamLeader(String teamName, ArrayList<String> programmingLanguages, int expYear, String empID, String empName, double baseSal, double bonusRate) {
        super(teamName, programmingLanguages, expYear, empID, empName, baseSal);
        this.bonusRate = bonusRate;
    }
// Get and set

    public double getBonusRate() {
        return bonusRate;
    }

    public void setBonusRate(double bonusRate) {
        this.bonusRate = bonusRate;
    }

// Ghi đè
    @Override
    public double getSalary() {
        return (1 + bonusRate) * super.getSalary();
    }
}

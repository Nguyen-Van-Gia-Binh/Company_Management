/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package fileio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Developer;
import model.Employee;
import model.TeamLeader;
import model.Tester;

public class EmployeeFileText implements IFileReadWrite<Employee> {

    private final String FILE_NAME = "src/fileio/Employee.txt";

    // Quy ước D là dev, L là teamLead, T là tester 
    // D_empID_empName_baseSal_teamName_expYear_ProgramList(-)
    // T
    @Override
    public List<Employee> read() throws Exception {
        File file = new File(FILE_NAME);
        ArrayList<Employee> list = new ArrayList<>();

        // Trả về rỗng nếu không tồn tại
        if (!file.exists()) {
            return list;
        }

        try ( BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;

            // Đọc từng dòng rồi thêm vào cho đến khi cuối file
            while ((line = r.readLine()) != null) {
                // Tách chuỗi
                String[] parts = line.trim().split("_");
                // Làm sạch danh sách 
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                // L_empID_empName_baseSal_teamName_expYear_bonusRate_ProgramList(-)
                if (parts[0].equalsIgnoreCase("L")) {
                    String empID = parts[1];
                    String empName = parts[2];
                    double baseSal = Double.parseDouble(parts[3]);
                    String teamName = parts[4];
                    int expYear = Integer.parseInt(parts[5]);
                    double bonusRate = Double.parseDouble(parts[6]);
                    String programmingLanguagesList[] = parts[7].split("-");
                    ArrayList<String> programmingLanguages = new ArrayList<>();
                    for (String pl : programmingLanguagesList) {
                        if (!(pl.isEmpty())) {
                            programmingLanguages.add(pl.trim());
                        }
                    }
                    list.add(new TeamLeader(teamName, programmingLanguages, expYear, empID, empName, baseSal, bonusRate));
                } // D_empID_empName_baseSal_teamName_expYear_ProgramList(-)
                else if (parts[0].equalsIgnoreCase("D")) {
                    String empID = parts[1];
                    String empName = parts[2];
                    double baseSal = Double.parseDouble(parts[3]);
                    String teamName = parts[4];
                    int expYear = Integer.parseInt(parts[5]);
                    String programmingLanguagesList[] = parts[6].split("-");
                    ArrayList<String> programmingLanguages = new ArrayList<>();
                    for (String pl : programmingLanguagesList) {
                        if (!(pl.isEmpty())) {
                            programmingLanguages.add(pl.trim());
                        }
                    }
                    list.add(new Developer(teamName, programmingLanguages, expYear, empID, empName, baseSal));
                } // T_empID_empName_baseSal_type_bonusRate
                else if (parts[0].equalsIgnoreCase("T")) {
                    String empID = parts[1];
                    String empName = parts[2];
                    double baseSal = Double.parseDouble(parts[3]);
                    String type = parts[4];
                    double bonusRate = Double.parseDouble(parts[5]);

                    list.add(new Tester(bonusRate, type, empID, empName, baseSal));
                }
            }
        } catch (Exception e) {
            System.out.println("Read file FAILED: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean write(List<Employee> list) throws Exception {

        // Tạo chuỗi rồi ghi 
        // Ghi mới lên không có true FileOutputStream(FILE_NAME, true)
        try ( BufferedWriter r = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_NAME), "UTF-8"))) {
            for (Employee e : list) {
                // L_empID_empName_baseSal_teamName_expYear_bonusRate_ProgramList(-)
                if (e instanceof TeamLeader) {
                    TeamLeader l = (TeamLeader) e;
                    String pls = String.join("-", l.getProgrammingLanguages());
                    String line = String.format("L_%s_%s_%.2f_%s_%d_%.2f_%s", l.getEmpID(), l.getEmpName(), l.getBaseSal(), l.getTeamName(), l.getExpYear(), l.getBonusRate(), pls.toString());
                    r.write(line);
                    r.newLine();
                } // D_empID_empName_baseSal_teamName_expYear_ProgramList(-)
                else if (e instanceof Developer) {
                    Developer d = (Developer) e;
                    String pls = String.join("-", d.getProgrammingLanguages());
                    String line = String.format("D_%s_%s_%.2f_%s_%d_%s", d.getEmpID(), d.getEmpName(), d.getBaseSal(), d.getTeamName(), d.getExpYear(), pls.toString());
                    r.write(line);
                    r.newLine();
                } // T_empID_empName_baseSal_type_bonusRate
                else if (e instanceof Tester) {
                    Tester t = (Tester) e;
                    String line = String.format("T_%s_%s_%.2f_%s_%.2f", t.getEmpID(), t.getEmpName(), t.getBaseSal(), t.getType(), t.getBonusRate());
                    r.write(line);
                    r.newLine();
                }
            }
            System.out.println("Save Successful: ");
            return true;
        } catch (Exception e) {
            System.out.println("Write file FAILED: " + e.getMessage());
            return false;
        }
    }

}

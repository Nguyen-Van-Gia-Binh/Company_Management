/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package controller;

import fileio.EmployeeFileText;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToLongFunction;
import model.*;
import utilities.Inputter;

public class CompanyManagement {

    // Fields
    private List<Employee> empList = null;
    private static final String FILENAME = "/src/fileio/helo.dat";

    // Constructor
    public CompanyManagement() {
        if (!load() || empList == null) {
            empList = new ArrayList<>();
        }
    }

    public boolean addEmployee(Employee emp) {
        if (emp == null) {
            return false;

        }

        // Check duplicate 
        if (isExistCode(emp.getEmpID())) {
            System.out.println("Error: Empoyee code already exists.");
            return false;
        }

        // Check team lead 
        if (emp instanceof TeamLeader) {
            if (((isTeamLeaderExist(((TeamLeader) emp).getTeamName())))) {
                System.out.println("Error: Team '" + ((TeamLeader) emp).getTeamName() + "' already has a Team Leader.");
                return false;
            }
        }
        empList.add(emp);
        return true;

    }

    public Employee getEmployee(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        // Neu ton tai thi tra ve 
        for (Employee emp : empList) {
            if (emp.getEmpID().equalsIgnoreCase(code)) {
                return emp;
            }
        }
        return null;
    }

    public List<Employee> getAll() {
        return empList;
    }

    // Tìm các nhân viên
    public List<Employee> getByName(String name) {

        // Nhập 
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>(); // null sẽ cần check do vậy trả về rỗng
        }
        List<Employee> list = new ArrayList<>();
        for (Employee emp : empList) {
            if (emp.getEmpName().toLowerCase().contains(name.toLowerCase())) {
                list.add(emp);
            }
        }
        return list;
    }

    public List<Employee> getEmployeeBySalary(double minSalary) {
        List<Employee> list = new ArrayList<>();
        for (Employee emp : empList) {
            if (emp.getSalary() > minSalary) {
                list.add(emp);
            }
        }
        return list;
    }

    public List<Employee> getTesterHighestSalary() {

        // Loc mang test
        // C1: sap xep - duyet mang tim phan tu lon nhat
        // Tra ve mang
        // C2: Tim max - Duyet tim == max thi them vao
        // tối ưu lần 1: Bỏ danh sách phụ 
//        List<Employee> list = new ArrayList<>();
//        double maxHighestSalary = 0;
//        for (Employee emp : empList) {
//            if (emp instanceof Tester) {
//                list.add(emp);
//                double empSalary = emp.getSalary();
//                if (maxHighestSalary < empSalary) {
//                    maxHighestSalary = empSalary;
//                }
//            }
//        }
        double maxHighestSalary = -1; // -1 hay hơn vì đảm bảo lương là số sẽ lớn hơn 1 
        for (Employee emp : empList) {
            if (emp instanceof Tester) {
                double empSalary = emp.getSalary();
                if (maxHighestSalary < empSalary) {
                    maxHighestSalary = empSalary;
                }
            }
        }
        // Nếu không có nhân viên nào thì dừng
        if (maxHighestSalary == -1) {
            return new ArrayList<>();
        }
        List<Employee> resultList = new ArrayList<>();
        for (Employee emp : empList) {
            if (emp instanceof Tester && emp.getSalary() == maxHighestSalary) {
                resultList.add(emp);
            }
        }
        return resultList;
    }

    // Tim danh sach nhan vien co ngon ngu do
    public List<Employee> getProgramingLanguages(String pl) {
        List<Employee> list = new ArrayList<>();

        // Nếu đầu vào không rỗng, null 
        if (pl == null || pl.trim().isEmpty()) {
            return list; // Thống nhất là trả về rỗng
        }
//        // co Danh sach ngon ngu do
//        String[] strDevloper = pl.trim().split(",");
//        for (Employee emp : empList) {
//            if (emp instanceof Developer) {
//                // Lap chuoi ngon ngu
//                for (String p : ((Developer) emp).getProgrammingLanguages()) {
//                    // Lap qua ngon ngu
//                    for (String str : strDevloper) {
//                        if (str.trim().equalsIgnoreCase(p)) { // Trim
//                            list.add(emp);
//                            break; // Tìm thấy thì dừng thôi
//                        }
//                    }
//                }
//            }
//        }

        // co chua ngon ngu do 
        for (Employee emp : empList) {
            if (emp instanceof Developer) {
                // Lap chuoi ngon ngu
                for (String p : ((Developer) emp).getProgrammingLanguages()) {
                    if (p.trim().toLowerCase().contains(pl.toLowerCase())) { // Trim
                        list.add(emp);
                        break; // Tìm thấy thì dừng thôi
                    }
                }
            }
        }

        return list;
    }

// Update Employee
    public boolean updateEmployee(String empID) {
        // Cập nhật theo từng trường, nếu muốn bỏ qua cái nào thì nhấn enter

        boolean flag = false;
        Employee emp = getEmployee(empID); // Lấy nhân viên bằng mã code

        // Xử lý trường hợp đầu vào xấu 
        if (emp == null) {
            System.out.println("Employee is not exist");
        } else {
            System.out.println("Updating employee : " + emp.getEmpName());

            // Cập nhật trường chung 
            String newName = Inputter.inputStr("Enter new name (leave blank to keep old ): ");
            if (!(newName.trim().isEmpty())) {
                emp.setEmpName(newName);
                System.out.println("Update successfull");
            }

            Double newBaseSal = Inputter.inputUpdateDouble("Enter new base salary (leave blank to keep old): ", 0.0, Double.MAX_VALUE);
            if (!(newBaseSal == -1)) {
                emp.setBaseSal(newBaseSal);
                System.out.println("Update successfull");
            }

            // Cập nhật trường riêng
            // Tester
            if (emp instanceof Tester) {
                String newType = Inputter.inputStr("Enter new type (leave blank to keep old ): ");
                if (!(newType.trim().isEmpty())) {
                    ((Tester) emp).setType(newType);
                    System.out.println("Update successfull");
                }
                Double newBonus = Inputter.inputUpdateDouble("Enter new bonus rate (leave blank to keep old): ", 0.0, Double.MAX_VALUE);
                if (!(newBonus == -1)) {
                    ((Tester) emp).setBonusRate(newBonus);
                    System.out.println("Update successfull");
                }
            }

            // Developer
            if (emp instanceof Developer) {
                // Update teamname: Make sure one team lead one team
                String newTeam = Inputter.inputStr("Enter new team (leave blank to keep old ): ");
                if (!(newTeam.trim().isEmpty())) {
                    // Use flag to reuse a method 
                    boolean canUpdate = true;
                    if (emp instanceof TeamLeader) {
                        if (isTeamLeaderExist(newName)) {
                            canUpdate = false;
                            System.out.println("Error: Team '" + newTeam + "' already has a Leader. Cannot update.");
                        }
                    }
                    // Nornal Dev
                    if (canUpdate) {
                        ((Developer) emp).setTeamName(newName);
                        System.out.println("Update Successful");
                    }
                }
                // Update programming 
                String programming = Inputter.inputStr("Enter new programming list spread by , (leave blank to keep old ): ");
                if (!(programming == null || programming.trim().isEmpty())) {
                    String[] listProgramming = programming.trim().split(",");
                    ArrayList<String> newListProgramming = new ArrayList<>();
                    for (String s : listProgramming) {
                        newListProgramming.add(s.trim());
                    }
                    ((Developer) emp).setProgrammingLanguages(newListProgramming);
                }

                // Update expYear 
                int newExp = Inputter.inputInt("Enter new expYear (leave blank to keep old ): ", -1, 100);
                if (newExp != -1) {
                    ((Developer) emp).setExpYear(newExp);
                }
                if (emp instanceof TeamLeader) {
                    Double newBonus = Inputter.inputUpdateDouble("Enter new bonus rate (leave blank to keep old): ", 0.0, Double.MAX_VALUE);
                    if (!(newBonus == -1)) {
                        ((TeamLeader) emp).setBonusRate(newBonus);
                        System.out.println("Update successfull");
                    }
                }
            }
            flag = true;
        }
        return flag;
    }

    // Sort 
    public List<Employee> sortBySalName() {
        // Xử lý trường hợp xấu là danh sách rỗng
        if (empList.isEmpty()) {
            return new ArrayList<>();
        }

        // Tạo danh sách copy không làm thay đổi danh sách gốc
        List<Employee> listCopy = new ArrayList<>();
        for (Employee e : empList) {
            listCopy.add(e);
        }

        // Lamda cho comparator
        Comparator<Employee> cmp = (Employee o1, Employee o2) -> {
            // Tạo danh sách giảm dần
            if (Double.compare(o2.getSalary(), o1.getSalary()) != 0) {
                return Double.compare(o2.getSalary(), o1.getSalary());
            }
            // Nếu lương bằng nhau, sắp xếp giảm dần theo tên
            return o2.getEmpName().compareTo(o1.getEmpName());
        };

        Collections.sort(listCopy, cmp);

        System.out.println("Employee list has been sorted");
        return listCopy;
    }

    public boolean isExistCode(String code) {
        return getEmployee(code) != null;
    }

    // Check team lead
    public boolean isTeamLeaderExist(String teamName) {
        // Duyệt và tìm thôi
        for (Employee emp : empList) {
            if (emp instanceof TeamLeader) {
                if ((((TeamLeader) emp).getTeamName()).equalsIgnoreCase(teamName)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Lưu data 
//    public boolean save() {
//        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
//            oos.writeObject(empList);
//            System.out.println("Data saved successfully to " + FILENAME);
//            return true;
//        } catch (IOException e) {
//            System.out.println("Error saving data: " + e.getMessage());
//            return false;
//        }
//    }
    public boolean save() throws Exception {
        EmployeeFileText s = new EmployeeFileText();
        return s.write(empList);
    }

    // Nạp dữ liệu 
//    private boolean load() {
//        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
//            // Ép kiểu danh sách về danh sách đúng
//            empList = (List<Employee>) ois.readObject();
//            return true;
//        } catch (Exception ex) {
//            System.out.println("Erron: Can not read file ");
//            return false;
//        }
//    }
    private boolean load() {
        try {
            EmployeeFileText load = new EmployeeFileText();
            empList = load.read();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Find team most members

    public String getTeamsMostMember() {
//        // Can danh sach team
//        Map<String, Integer> teamCount = new HashMap<>();
//
//        // Neu chua co thi them co thi tang 1 
//        for (Employee e : empList) {
//            if (e instanceof Developer) {
//                String teamName = ((Developer) e).getTeamName();
//                teamCount.put(teamName, teamCount.getOrDefault(teamName, 0) + 1);
//            }
//        }
//        // lap va dem
//        String teamMax = "";
//        int teamMaxCount = -1;
//        for (Map.Entry<String, Integer> entry : teamCount.entrySet()) {
//            if (entry.getValue() >= teamMaxCount) {
//                teamMax += entry.getKey() + " ";
//                teamMaxCount = entry.getValue();
//            }
//        }
//        return teamMax.trim();

        // List Key 
        Map<String, Integer> teamCount = new HashMap<>();
        for (Employee employee : empList) {
            if (employee instanceof Developer) {
                teamCount.put(((Developer) employee).getTeamName(), teamCount.getOrDefault(((Developer) employee).getTeamName(), 0) + 1);
            }
        }
        // Duyet va tim max
        String teamName = "";
        int max = -1;
        for (Map.Entry<String, Integer> entry : teamCount.entrySet()) {
            if (entry.getValue() > max) {
                teamName = entry.getKey();
                max = entry.getValue();
            }
        }
        return teamName.trim();
    }

    // sort by name
    public List<Employee> sortByName() {
        ArrayList<Employee> res = new ArrayList<>();
        for (Employee e : empList) {
            res.add(e);
        }
        Collections.sort(res, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getEmpName().compareToIgnoreCase(o2.getEmpName());
            }
        });
        return res;
    }
}

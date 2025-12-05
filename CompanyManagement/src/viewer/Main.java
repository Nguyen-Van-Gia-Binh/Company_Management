/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package viewer;

import controller.CompanyManagement;
import java.util.*;
import utilities.Inputter;
import model.*;

public class Main {

    public static void main(String[] args) throws Exception {

        String[] options = {"Show the Employee list",
            "Add Employee", "Update Employee ",
            "Search Employee", "Save",
            "Sort Employees", "Other Fuction", "Exit"};

        // Khoi tao trinh quan ly 
        CompanyManagement cm = new CompanyManagement();

        int choice;
        do {
            System.out.println("\nCompany Employee Management Program");
            choice = Menu.getChoice(options); // show Menu options
            switch (choice) {
                case 1:
                    System.out.println("-> You selected: Show list");
                    showEmployees(cm);
                    break;
                case 2:
                    System.out.println("-> You selected: Add Employee");
                    addEmployee(cm);
                    break;
                case 3:
                    System.out.println("-> You selected: Update Employee");
                    updateEmployee(cm);
                    break;

                case 4:
                    System.out.println("-> You selected: Search Employee");
                    searchEmployee(cm);
                    break;
                case 5:
                    System.out.println("-> You selected: Store data to file");
                    System.out.println(cm.getTeamsMostMember());
                    break;
                case 6:
                    System.out.println("-> You selected: Sort Employee");
                    sortEmployees(cm);
                    break;
                case 7:
                    System.out.println("You selected: Other Fuction");
//                    System.out.println((cm.getTeamsMostMember().isEmpty()) ? "Do not have max team" : cm.getTeamsMostMember());
                    for (Employee e : cm.sortByName()) {
                        System.out.println(e);
                    }
                default:
                    System.out.println("Good bye!");
            }

        } while (choice > 0 && choice < options.length);

    }

    // Method in menu main 
    private static void showEmployees(CompanyManagement cm) {
        List<Employee> listEmp = cm.getAll();
        if (listEmp.isEmpty()) {
            System.out.println("The employee list is empty.");
        } else {
            System.out.println("--- List of Employees ---");
            for (Employee emp : listEmp) {
                // In ra thoi 
                System.out.println(emp);
            }
        }
    }

    private static void addEmployee(CompanyManagement cm) {
        // Code cho chức năng 2 sẽ ở đây
        // W1: cập nhật trường chung
        // Nhập role rồi cho cập nhật các trường khác 
        // W2: Nhập role từ đầu luôn. => rồi cho cập nhật 
        String[] optionForAddEmployee = {"Add new Tester",
            "Add new Developer",
            "Add new Team Leader",
            "Return to main menu"};
        int addChoice;
        do {
            System.out.println("\n--- Add Employee Sub-menu ---");
            addChoice = Menu.getChoice(optionForAddEmployee);

            switch (addChoice) {
                case 1:
                    doAddNewTester(cm);
                    break;
                case 2:
                    doAddNewDeveloper(cm);
                    break;
                case 3:
                    doAddNewTeamLeader(cm);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (addChoice != 4);
    }

    // Submethods for AddEmployee
    private static void doAddNewTester(CompanyManagement cm) {
        System.out.println("---Add new Tester---");
        String empId;
        while (true) {
            empId = Inputter.inputPattern("Enter Employee ID (e.g., T001): ", "^[Tt]\\d{3}$");
            if (cm.isExistCode(empId)) { // Id mà tồn tại thì bắt nhập lại
                System.out.println("Error: Employee code already exists. Please enter another code.");
            } else {
                break; // Thoát trình nhập 
            }
        }
        String empName = Inputter.inputNonBlankStr("Enter name: ");
        double baseSal = Inputter.inputDoub("Enter base salary: ", 0, Double.MAX_VALUE);
        String type = Inputter.inputPattern("Enter type (AM for Automation / MT for Manual). Only AM/MT or not you will need to input again: ", "^(?i)(am|mt)$").toUpperCase();
        double bonusRate = Inputter.inputDoub("Enter bouns rate: ", 0, Double.MAX_VALUE);
        Tester newTester = new Tester(bonusRate, type, empId, empName, baseSal);
        // Add 
        if (cm.addEmployee(newTester)) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add unsuccessful, has some errons");
        }
    }

    private static void doAddNewDeveloper(CompanyManagement cm) {
        System.out.println("---Add new Developer---");
        String empId;
        while (true) {
            empId = Inputter.inputPattern("Enter Employee ID (e.g., D001): ", "^[Dd]\\d{3}$");
            if (cm.isExistCode(empId)) { // Id mà tồn tại thì bắt nhập lại
                System.out.println("Error: Employee code already exists. Please enter another code.");
            } else {
                break; // Thoát trình nhập 
            }
        }
        String empName = Inputter.inputNonBlankStr("Enter name: ");
        double baseSal = Inputter.inputDoub("Enter base salary: ", 0, Double.MAX_VALUE);

        // Trường riêng
        String teamName = Inputter.inputNonBlankStr("Enter team name: ");
        int expYear = Inputter.inputInt("Enter exp year: ", 0, 100);
        ArrayList<String> listProgramming = Inputter.inputProgrammingLanguages("developer");

        Developer newDev = new Developer(teamName, listProgramming, expYear, empId, empName, baseSal);

        // Add 
        if (cm.addEmployee(newDev)) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add unsuccessful, has some errons");
        }
    }

    private static void doAddNewTeamLeader(CompanyManagement cm) {
        System.out.println("---Add new TeamLeader---");
        String empId;
        while (true) {
            empId = Inputter.inputPattern("Enter Employee ID (e.g., D001): ", "^[Dd]\\d{3}$");
            if (cm.isExistCode(empId)) { // Id mà tồn tại thì bắt nhập lại
                System.out.println("Error: Employee code already exists. Please enter another code.");
            } else {
                break; // Thoát trình nhập 
            }
        }
        String empName = Inputter.inputNonBlankStr("Enter name: ");
        double baseSal = Inputter.inputDoub("Enter base salary: ", 0, Double.MAX_VALUE);

        // Trường riêng
        // Mỗi team chỉ có một Leader
        // Cho nhập đến khi đúng thì thôi
        // input -> check 
        String teamName = "";
        while (true) {
            teamName = Inputter.inputNonBlankStr("Enter team name: ");
            if (cm.isTeamLeaderExist(teamName)) {
                System.out.println("Error: " + teamName + " has already leader. Please enter another team name");
            } else {
                break;
            }
        }
        int expYear = Inputter.inputInt("Enter exp year: ", 0, 100);
        double bonusRate = Inputter.inputDoub("Enter bonus rate: ", 0, Double.MAX_VALUE);

        ArrayList<String> listProgramming = Inputter.inputProgrammingLanguages("team leader");

        // Lợi ích code đúng tên tham số khởi tạo :)) 
        //Add
        TeamLeader newLead = new TeamLeader(teamName, listProgramming, expYear, empId, empName, baseSal, bonusRate);
        if (cm.addEmployee(newLead)) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add unsuccessful, has some errons");
        }
    }

    private static void updateEmployee(CompanyManagement cm) {
        String empID = Inputter.inputStr("Enter employee ID: ");
        if (cm.updateEmployee(empID.trim())) {
            System.out.println("Update all succefull");
        } else {
            System.out.println("Update all unsuccefull");
        }
    }

    private static void searchEmployee(CompanyManagement cm) {
        // Code cho chức năng 4 sẽ ở đây
        // Menu
        String[] optionsSearch = {"Search by ID",
            "Search by Salary (Higher than)",
            "Search by Name",
            "Find Testers with Highest Salary",
            "Search by Programming Language",
            "Return to main menu"};
        int searchChoice;
        do {
            System.out.println("\n--- Search Employee Sub-menu ---");
            // Lấy lựa chọn
            searchChoice = Menu.getChoice(optionsSearch);
            // Tối ưu code bằng cách tách hàm in ra danh sách kết quả :)
            switch (searchChoice) {
                case 1:
                    System.out.println("--- Search by ID ---");
                    String empID = Inputter.inputStr("Enter employee ID: ");
                    Employee emp = cm.getEmployee(empID);
                    if (emp != null) {
                        System.out.println("Employee found:");
                        System.out.println(emp);
                    } else {
                        System.out.println("No Employee is matched.");
                    }
                    break;
                case 2:
                    System.out.println("--- Search by Salary (Higher than) ---");
                    double minSalary = Inputter.inputDoub("Enter minimum salary: ", 0, Double.MAX_VALUE);
                    System.out.println("Employees with salary higher than " + minSalary + ":");
                    printEmployeeList(cm.getEmployeeBySalary(minSalary));
                    break;
                case 3:
                    System.out.println("--- Search by Name ---");
                    String empName = Inputter.inputNonBlankStr("Enter name employee: ");
                    printEmployeeList(cm.getByName(empName));
                    break;
                case 4:
                    System.out.println("--- Find Testers with Highest Salary ---");
                    printEmployeeList(cm.getTesterHighestSalary());
                    break;
                case 5:
                    System.out.println("--- Search by Programming Language ---");
                    String prLang = Inputter.inputNonBlankStr("Enter name programming language: ");
                    printEmployeeList(cm.getProgramingLanguages(prLang));
                    break;
                case 6:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again");
            }
        } while (searchChoice != 6); // Sửa: Điều kiện thoát là 6
    }

    // Submethods for searchEmployee
    private static void printEmployeeList(List<Employee> empList) {
        if (empList.isEmpty()) {
            System.out.println("No Employee is matched.");
            return;
        }
        for (Employee emp : empList) {
            System.out.println(emp); // Phê thề hiểu tại sao người ta khuyên toString rồi. 
        }
    }

    private static void sortEmployees(CompanyManagement cm) {
        List<Employee> sortList = cm.sortBySalName();
        System.out.println("-- List employee in decending order sorted by name and salary ---");
        for (Employee e : sortList) {
            System.out.println(e);
        }
    }

}

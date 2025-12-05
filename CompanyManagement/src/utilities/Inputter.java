/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package utilities;

import java.util.ArrayList;
import java.util.Scanner;

public class Inputter {

    public static Scanner sc = new Scanner(System.in);

    // get an double between min and max 
    public static double inputDoub(String msg, double min, double max) {
        if (min > max) {
            double t = min;
            min = max;
            max = t;
        }
        double data;
        // Xu ly nhap sai 
        // sai mien gia tri
        // Sai format
        while (true) {
            try {
                System.out.print(msg);
                data = Double.parseDouble(sc.nextLine());
                if (data >= min && data <= max) {
                    break;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return data;
    }

    // cho phep khong nhap 
    /**
     * Lấy Double trả -1 nếu khoảng trắng, nhập cho đến khi đúng
     *
     * @param msg
     * @param min
     * @param max
     * @return
     */
    public static double inputUpdateDouble(String msg, double min, double max) {
        // Khoang trang thi next
        String input = inputStr(msg);
        if (input.trim().isEmpty()) {
            return -1;
        }
        // Cap nhat 
        // Neu nhap dung cap nhat, neu nhap sai 
        // sai data nhap laii
        // sai fomart thi bao loi nhap lai
        try {
            double data = Double.parseDouble(input);
            if (data < min || data > max) {
                System.out.println("Please enter a number between " + min + " and " + max);
                // Gọi lại chính nó để bắt nhập lại
                return inputUpdateDouble(msg, min, max);
            } else {
                return data;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number or leave blank to skip.");
            return inputUpdateDouble(msg, min, max); // Gọi lại
        }
    }

    /**
     * Lấy int trả -1 nếu khoảng trắng, nhập cho đến khi đúng
     *
     * @param msg
     * @param min
     * @param max
     * @return
     */
    // get an int between min and max
    public static int inputInt(String msg, int min, int max) {
        if (min > max) {
            int t = min;
            min = max;
            max = t;
        }
        String input = inputStr(msg);
        if (input.trim().isEmpty()) {
            return -1;
        }

        try {
            int data = Integer.parseInt(input);
            if (data < min || data > max) {
                System.out.println("Please input again in " + min + " and " + max);
                return inputInt(msg, min, max);
            } else {
                return data;
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input. Please input agian");
            return inputInt(msg, min, max);
        }

    }

    // get a string with no condition
    public static String inputStr(String msg) {
        System.out.print(msg);
        String data = sc.nextLine().trim();
        return data;
    }

    // get a non-blank string
    public static String inputNonBlankStr(String msg) {
        String data;
        do {
            System.out.print(msg);
            data = sc.nextLine().trim();
        } while (data.length() == 0);
        return data;
    }

    // get a string following a pattern
    public static String inputPattern(String msg, String pattern) {
        String data;
        do {
            System.out.print(msg);
            data = sc.nextLine().trim();
        } while (!data.matches(pattern));
        return data;
    }

    // Input programing lists
    public static ArrayList<String> inputProgrammingLanguages(String name) {
        ArrayList<String> listProgramming = new ArrayList<>(); // Tạo danh sách ngôn ngữ lập trình
        // Sử dụng lại code update programming nhưng biến tấu đảm bảo có ít nhât một ngôn ngữ lập trình
        while (true) {
            String programming = Inputter.inputStr("Enter programming language list spread by ,: ");
            if (!(programming == null || programming.trim().isEmpty())) {
                String[] listProgrammings = programming.split(",");
                for (String s : listProgrammings) {
                    //Đảm bảo phân tử không được rỗng
                    if (!(s == null || s.trim().isEmpty())) {
                        listProgramming.add(s.trim());
                    }
                }
                // Kiểm tra list có rỗng không 
                if (listProgramming.isEmpty()) {
                    System.out.println("Input contains no valid languages. Please try again.");
                } else {
                    break;
                }
            } else {
                // Bắt nhập lại nếu để rỗng
                // Hàm này không kiểm tra xem devlop có nhập đúng tên ngôn ngữ không vì có quá nhiều ngôn ngữ
                System.out.println("A " + name + " must know at least one language. Please add one.");
            }
        }
        return listProgramming;
    }
}

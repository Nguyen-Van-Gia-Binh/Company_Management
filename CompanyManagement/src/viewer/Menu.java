/**
 *
 * @author Nguyen Van Gia Binh SE203555
 */
package viewer;

import utilities.Inputter;

public class Menu {

    public static int getChoice(Object[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        return Inputter.inputInt("Your options from 1 - " + options.length + ": ", 1, options.length);
    }
}

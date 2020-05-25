import java.util.InputMismatchException;
import java.util.Scanner;
import app.*;
import ui.*;

/**
 * DRIVER FOR THE SYSTEM OF NATIONAL ID (SNID))
 * 
 * @author Anakai Richards
 * @author Matthew Palmer
 * @author Michael Young
 * @version 1.0
 */
public class Main {

    /**
     * Runs the TextUI then the GUI
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);) {
            String startMessage = "\n\nWelcome! This Project has been completed by Anakai Richards, Matthew Palmer and Michael Young";
            System.out.println(startMessage);
            System.out.println("=".repeat(startMessage.length()));
            Integer ch = null;
            do {
                System.out.println("Please choose which UI to run:\n1\t-\tTextUI\n2\t-\tGUI\n0\t-\tExit");
                try {
                    ch = sc.nextInt();
                    sc.nextLine();
                    switch (ch) {
                        case 1:
                            SNIDApp app = new SNIDApp("data.db", ',');
                            TextUI ui = new TextUI();
                            System.out.println(
                                    "Note: The citizen ID number is a number padded up to 8 digits with zeroes to the left");
                            System.out.println("Press enter to start the TextUI");
                            sc.nextLine();
                            System.out.println("Starting TextUI...\n\n");
                            ui.go(app);
                            // to prevent errors from System.in having been closed in TextUI
                            System.out.println("Program must be restarted to effect changes.");
                            System.exit(0);
                            break;

                        case 2:
                            System.out.println("The GUI will run next, but first some quick instructions.");
                            System.out.println(
                                    "When searching by biometric the the tag precedes the data and there is no space between them eg. D1000.\nThe tag must be a capital F(Fingerprint) or a capital D(DNA)");
                            System.out.println(
                                    "Also, a reminder that the format of the ID numbers is a number padded up to 8 digits with zeroes to the left");
                            System.out.println("Press enter to start the GUI");
                            sc.nextLine();
                            System.out.println("Starting GUI....");
                            SNIDGUI gui = new SNIDGUI(new SNIDApp("data.db", ','));
                            gui.setVisible(true);

                            break;
                        case 0:
                            System.out.println("Thank you for using this program! The program will exit now");
                            break;
                        default:
                            System.out.println("Invalid option. Try again");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid option. Try again");
                    sc.nextLine();
                }
            } while (!ch.equals(0));

        } catch (Exception b) {
            System.out.println("Fatal Error. Program could not start - " + b.getLocalizedMessage() + "\n");
        }

    }

}
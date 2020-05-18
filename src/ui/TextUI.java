package ui;

import app.*;
import java.util.*;

/**
 * Text-based menu interface for SNID Management. Interacts with SNIDApp
 */

public class TextUI {

    public void go(SNIDApp app) {
        Scanner in = new Scanner(System.in);

        // Menu to be displayed to user
        System.out.println("Welcome to the System for National Identification\n");
        System.out.println("            Main Menu\n");
        System.out.println("a. Register a Birth\n");
        System.out.println("b. Update Parent Data\n");
        System.out.println("c. Update a Citizen's Address\n");
        System.out.println("d. Register a Death\n");
        System.out.println("e. Register a Marriage\n");
        System.out.println("f. Generate a Mailing Label\n");
        System.out.println("g. Search\n");
        System.out.println("h. Exit Application\n");

        String option = in.nextLine();

        if (option.toLowerCase() != "a" || option.toLowerCase() != "b" || option.toLowerCase() != "c"
                || option.toLowerCase() != "d" || option.toLowerCase() != "e" || option.toLowerCase() != "f"
                || option.toLowerCase() != "g" || option.toLowerCase() != "h") {
            System.out.println("Invalid choice. Select from a-h\n");
            option = in.nextLine();
        }

        while (option.toLowerCase() != "h") {
            if (option == "a") {
                // Input of birth information
                System.out.println("Enter gender (M for male or F for female");
                String gender = in.nextLine();

                // to check if a valid gender is entered
                if (gender.toUpperCase() != "M" || gender.toUpperCase() != "F") {
                    System.out.println("Not a gender option. Enter M or F");
                    gender = in.nextLine();
                }

                System.out.println("Enter year of birth");
                int yob = 0;
                try {
                    yob = in.nextInt();
                } catch (InputMismatchException a) {
                    System.out.println("Enter an integer representing the year of birth");

                } // to catch if user does not enter an integer

                System.out.println("Enter first name: ");
                String fname = in.nextLine();

                System.out.println("Enter middle name: ");
                String mname = in.nextLine();

                System.out.println("Enter last name");
                String lname = in.nextLine();

                app.registerBirth(gender.charAt(0), yob, fname, mname, lname);

            } // end of option a
            else if (option.toLowerCase() == "b") {
                System.out.println("Enter Id of citizen's whose parent data is to be updated:");
                String updateID = in.nextLine();

                System.out.println("Enter Id of citizen's father: ");
                String fatherID = in.nextLine();

                System.out.println("Enter Id of citizen's mother: ");
                String motherID = in.nextLine();

                // Addition of parent informaation to citizen
                app.addParentData(updateID, fatherID, motherID);

            } // end of option b

            else if (option.toLowerCase() == "c") {
                System.out.println("Enter id of citizen: ");
                String updateID = in.nextLine();

                System.out.println("Enter street of address: ");
                String street = in.nextLine();

                System.out.println("Enter town of address: ");
                String town = in.nextLine();

                System.out.println("Enter parish of address:");
                String parish = in.nextLine();

                System.out.println("Enter country of address: ");
                String country = in.nextLine();

                // Update of citizen's address
                app.updateAddress(updateID, street, town, parish, country);

            } // end of option c

            else if (option.toLowerCase() == "d") {
                System.out.println("Enter citizen id: ");
                String id = in.nextLine();

                System.out.println("Enter cause of death: ");
                String cause = in.nextLine();

                System.out.println("Enter place of death: ");
                String place = in.nextLine();

                System.out.println("Enter date of death: ");
                String date = in.nextLine();

                // Registry of citizen's death
                app.registerDeath(id, cause, place, date);

            } // end of option d

            else if (option.toLowerCase() == "e") {
                System.out.println("Enter groom id: ");
                String groomId = in.nextLine();

                System.out.println("Enter bride id: ");
                String brideId = in.nextLine();

                System.out.println("Enter date of marriage; ");
                String date = in.nextLine();

                // Registry of marriage
                app.registerMarriage(groomId, brideId, date);

            } // end of option e

            else if (option.toLowerCase() == "f") {
                System.out.println("Enter id of citizen to generate mailing label");
                String id = in.nextLine();

                // Generation of mailing label
                app.mailingLabel(id);

            } // end of option f

            else if (option.toLowerCase() == "g") {
                // Menu to select search criteria

                System.out.println("1. To search by id of citizen\n");
                System.out.println("2. To search by first and last name of citizen\n");
                System.out.println("3. To search by biometric data\n");
                System.out.println("4. To exit search");

                int choice = 0;
                try {
                    choice = in.nextInt();
                } catch (InputMismatchException a) {
                    System.out.println("Invalid choice. Enter an option from 1 - 3\n");
                }

                if (choice == 1) {
                    System.out.println("Enter citizen id: ");
                    String id = in.nextLine();

                    // Search by id
                    app.search(id);

                } else if (choice == 2) {
                    System.out.println("Enter citizen first name: ");
                    String firstName = in.nextLine();

                    System.out.println("Enter citizen last name: ");
                    String lastName = in.nextLine();

                    // search by name
                    app.search(firstName, lastName);

                } else if (choice == 3) {
                    System.out.println("Enter the value for the DNA: ");
                    String value = in.nextLine();

                    System.out.println("Enter citizen's biometric tag (F for fingerprint or D for DNA): ");
                    String tag = in.nextLine();

                    // Search by biometric data
                    app.search(tag, value);
                }

            } // end of option g

            else if (option.toLowerCase() == "h") {

                System.exit(0);

            } // end of option h

            System.out.println("Welcome to the System for National Identification\n");
            System.out.println("            Main Menu\n");
            System.out.println("a. Register a Birth\n");
            System.out.println("b. Update Parent Data\n");
            System.out.println("c. Update a Citizen's Address\n");
            System.out.println("d. Register a Death\n");
            System.out.println("e. Register a Marriage\n");
            System.out.println("f. Generate a Mailing Label\n");
            System.out.println("g. Search\n");
            System.out.println("h. Exit Application\n");

            option = in.nextLine();

        } // end of while loop

        in.close();

    }

    public static void main(String[] args) {
        TextUI ui = new TextUI();
        
    
    }
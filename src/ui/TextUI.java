package ui;

import app.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Text-based menu interface for SNID Management. Interacts with SNIDApp
 */

public class TextUI {

    public void go(SNIDApp app) {
        Scanner in = new Scanner(System.in);

        // Menu to be displayed to user
        System.out.println("Welcome to the System for National Identification\n");
        System.out.println("                Main Menu\n");
        System.out.println("a. Register a Birth");
        System.out.println("b. Update Parent Data");
        System.out.println("c. Update a Citizen's Address");
        System.out.println("d. Register a Death");
        System.out.println("e. Register a Marriage");
        System.out.println("f. Generate a Mailing Label");
        System.out.println("g. Search");
        System.out.println("h. Exit Application");

        String option = in.nextLine();

        while (!(option.equalsIgnoreCase("h"))) {
            switch (option.toLowerCase()) {

                case "a":
                    // Input of birth information
                    System.out.println("Enter gender (M for male or F for female):");
                    String gender = in.nextLine();

                    // to check if a valid gender is entered
                    while (!(gender.equals("M")) && !(gender.equals("F"))) {
                        System.out.println("Not a gender option. Enter M or F");
                        gender = in.nextLine();
                    }

                    System.out.println("Enter year of birth");
                    int yob = 0;

                    try {
                        yob = in.nextInt();

                    } catch (InputMismatchException i) {

                        System.out.println("Incorrect entry. Please enter an integer representing the year of birth:");

                    } // to catch if user does not enter an integer

                    in.nextLine();

                    System.out.println("Enter first name: ");
                    String fname = in.nextLine();

                    System.out.println("Enter middle name: ");
                    String mname = in.nextLine();

                    System.out.println("Enter last name:");
                    String lname = in.nextLine();

                    app.registerBirth(gender.charAt(0), yob, fname, mname, lname);

                    break;

                case "b":

                    System.out.println("Enter Id of citizen's whose parent data is to be updated:");
                    String updateID = in.nextLine();

                    System.out.println("Enter Id of citizen's father: ");
                    String fatherID = in.nextLine();

                    System.out.println("Enter Id of citizen's mother: ");
                    String motherID = in.nextLine();

                    // Addition of parent informaation to citizen
                    app.addParentData(updateID, fatherID, motherID);
                    System.out.println("Parent Data Updated\n");

                    break;

                case "c":

                    System.out.println("Enter id of citizen: ");
                    String updateId = in.nextLine();

                    System.out.println("Enter street of address: ");
                    String street = in.nextLine();

                    System.out.println("Enter town of address: ");
                    String town = in.nextLine();

                    System.out.println("Enter parish of address: ");
                    String parish = in.nextLine();

                    System.out.println("Enter country of address: ");
                    String country = in.nextLine();

                    // Update of citizen's address
                    app.updateAddress(updateId, street, town, parish, country);
                    break;

                case "d":

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
                    System.out.println("Death Registered\n");

                    break;

                case "e":

                    System.out.println("Enter groom id: ");
                    String groomId = in.nextLine();

                    System.out.println("Enter bride id: ");
                    String brideId = in.nextLine();

                    System.out.println("Enter date of marriage: ");
                    String date1 = in.nextLine();

                    // Registry of marriage
                    app.registerMarriage(groomId, brideId, date1);
                    System.out.println("Marriage Registered\n");

                    break;

                case "f":

                    System.out.println("Enter id of citizen to generate mailing label:");
                    String Id = in.nextLine();

                    // Generation of mailing label
                    app.mailingLabel(Id);
                    System.out.println("Mailing Label Generated\n");
                    System.out.println(app.mailingLabel(Id));

                    break;

                case "g":

                    // Menu to select search criteria
                    System.out.println("\nSelect an option to search by:\n");
                    System.out.println("a. To search by id of citizen");
                    System.out.println("b. To search by first and last name of citizen");
                    System.out.println("c. To search by biometric data");
                    System.out.println("d. To exit search");

                    String choice = in.nextLine();

                    switch (choice) {

                        case "a":

                            System.out.println("Enter citizen id: ");
                            String ID = in.nextLine();

                            // Search by id
                            System.out.println(app.search(ID));

                            break;

                        case "b":

                            System.out.println("Enter citizen first name: ");
                            String firstName = in.nextLine();

                            System.out.println("Enter citizen last name: ");
                            String lastName = in.nextLine();

                            // search by name
                            System.out.println(Arrays.toString(app.search(firstName, lastName)));

                            break;

                        case "c":

                            System.out.println("Enter the value for the DNA: ");
                            String value = in.nextLine();

                            System.out.println("Enter citizen's biometric tag (F for fingerprint or D for DNA): ");
                            String tag = in.nextLine();

                            // Search by biometric data
                            System.out.println(Arrays.toString(app.search(tag, value)));

                            break;
                    } // end of switch for case "g"

                    break;

                case "h":
                    try {
                        app.shutdown();
                    } catch (FileNotFoundException m) {
                        m.printStackTrace();
                        ;
                    } catch (IOException n) {
                        n.printStackTrace();
                    }

                    break;

                default:
                    System.out.println("Invalid choice. Select from a-h\n");
                    option = in.nextLine();
            }

            System.out.println("\nWelcome to the System for National Identification\n");
            System.out.println("                Main Menu\n");
            System.out.println("a. Register a Birth");
            System.out.println("b. Update Parent Data");
            System.out.println("c. Update a Citizen's Address");
            System.out.println("d. Register a Death");
            System.out.println("e. Register a Marriage");
            System.out.println("f. Generate a Mailing Label");
            System.out.println("g. Search");
            System.out.println("h. Exit Application");

            option = in.nextLine();

        } // end of while loop

        in.close();

    }

    public static void main(String[] args) {
        TextUI ui = new TextUI();
        try {
            ui.go(new SNIDApp("data.db", ','));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
package ui;

import app.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletionException;

/**
 * Text-based menu interface for SNID Management. Interacts with SNIDApp
 */
public class TextUI {

    /**
     * Starts the TextUI and handles all user interactions. Saves changes made to
     * the databse on shutdown.
     * 
     * @param app An instance of SNIDApp to be used to manipulate the database of
     *            citizens
     */
    public void go(SNIDApp app) {
        Scanner in = new Scanner(System.in);
        String option, value, ID;
        String tag;
        do {
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
            System.out.println("h. Add Biometric");
            System.out.println("i. Get Biometric");
            System.out.println("j. Exit Application");

            option = in.nextLine();

            switch (option.toLowerCase()) {

                case "a":
                    System.out.println("\n----REGISTER BIRTH----\n");
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

                    while (true) {
                        try {
                            yob = in.nextInt();
                            in.nextLine();
                            break;
                        } catch (InputMismatchException i) {

                            System.out.println(
                                    "Incorrect entry. Please enter an integer representing the year of birth:");
                            in.nextLine();
                        } // to catch if user does not enter an integer
                    }

                    System.out.println("Enter first name: ");
                    String fname = in.nextLine();

                    System.out.println("Enter middle name: ");
                    String mname = in.nextLine();

                    System.out.println("Enter last name:");
                    String lname = in.nextLine();

                    try {
                        app.registerBirth(gender.charAt(0), yob, fname, mname, lname);
                        System.out.println("Request Complete.");
                    } catch (CompletionException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;

                case "b":
                    System.out.println("\n----UPDATE PARENT DATA----\n");
                    try {
                        System.out.println("Enter Id of citizen's whose parent data is to be updated:");
                        String updateID = in.nextLine();

                        System.out.println("Enter Id of citizen's father: ");
                        String fatherID = in.nextLine();

                        System.out.println("Enter Id of citizen's mother: ");
                        String motherID = in.nextLine();

                        // Addition of parent informaation to citizen
                        app.addParentData(updateID, fatherID, motherID);
                        System.out.println("Parent Data Updated. Request Complete.\n");
                    } catch (CompletionException p) {
                        System.out.println(p.getLocalizedMessage() + p.getCause().getLocalizedMessage()
                                + " Check to ensure that ALL IDs entered are valid. Try again");

                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;

                case "c":
                    System.out.println("\n----UPDATE CITIZEN ADDRESS----\n");

                    System.out.println("Enter id of citizen: ");
                    String updateId = in.nextLine();
                    System.out.println(
                            "Note: to remove the address for the citizen (in the event that they become homeless, for example) enter only blank lines");
                    System.out.println("Enter street of address: ");
                    String street = in.nextLine();

                    System.out.println("Enter town of address: ");
                    String town = in.nextLine();

                    System.out.println("Enter parish of address: ");
                    String parish = in.nextLine();

                    System.out.println("Enter country of address: ");
                    String country = in.nextLine();

                    // Update of citizen's address
                    try {
                        app.updateAddress(updateId, street, town, parish, country);
                        System.out.println("Request Complete");
                    } catch (CompletionException r) {
                        System.out.println("Citizen ID not found. Try Again Entering a Valid ID");
                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;

                case "d":
                    System.out.println("\n----REGISTER DEATH----\n");
                    System.out.println("Enter citizen id: ");
                    String id = in.nextLine();

                    System.out.println("Enter cause of death: ");
                    String cause = in.nextLine();

                    System.out.println("Enter place of death: ");
                    String place = in.nextLine();

                    System.out.println("Enter date of death in the format dd/mm/yyyy: ");
                    String date = in.nextLine();
                    while (true) {
                        try {
                            //check for valid date via parse
                            Calendar dateCheck = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            Date d = sdf.parse(date);
                            dateCheck.setTime(d);
                            break;
                        } catch (Exception e) {
                            System.out.println(
                                    "Invalid date. Please re-enter in a dd/mm/yyyy format and ensure that the date is possible:");
                            date = in.nextLine();
                        }
                    }
                    // Registry of citizen's death
                    try {
                        app.registerDeath(id, cause, place, date);
                        System.out.println("Death Registered\n");
                    } catch (CompletionException ye) {
                        System.out.println(ye.getLocalizedMessage()+ " "+ ye.getCause().getLocalizedMessage());
                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;

                case "e":
                    System.out.println("\n----REGISTER MARRIAGE----\n");
                    try {
                        System.out.println("Enter groom id: ");
                        String groomId = in.nextLine();
                        
                        System.out.println("Enter bride id: ");
                        String brideId = in.nextLine();

                        System.out.println("Enter date of marriage: ");
                        String date1 = in.nextLine();

                        while (true) {
                            try {
                                //check for valid date via parse
                                Calendar dateCheck = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                sdf.setLenient(false);
                                Date d = sdf.parse(date1);
                                dateCheck.setTime(d);
                                break;
                            } catch (Exception e) {
                                System.out.println(
                                        "Invalid date. Please re-enter in a dd/mm/yyyy format and ensure that the date is possible:");
                                date1 = in.nextLine();
                            }
                        }
                        // Registry of marriage
                        app.registerMarriage(groomId, brideId, date1);
                        System.out.println("Marriage Registered\n");
                    } catch (CompletionException w) {
                        System.out.println(w.getLocalizedMessage()+" "+w.getCause().getLocalizedMessage()+" Try Again");
                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;

                case "f":
                    System.out.println("\n----GENERATE MAILING LABEL----\n");
                    System.out.println("Enter id of citizen to generate mailing label:");
                    String Id = in.nextLine();

                    // Generation of mailing label
                    if (app.mailingLabel(Id) == null) {
                        System.out.println(
                                "Citizen Not Found. Cannot Generate Mailing Label. Check if ID entered is valid");
                    } else {
                        System.out.println("Mailing Label Generated\n");
                        System.out.println(app.mailingLabel(Id));
                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;

                case "g":
                    System.out.println("\n----SEARCH DATABASE----\n");
                    // Menu to select search criteria
                    System.out.println("\nSelect an option to search by:\n");
                    System.out.println("a. To search by id of citizen");
                    System.out.println("b. To search by first and last name of citizen");
                    System.out.println("c. To search by biometric data");
                    System.out.println("d. To exit search");

                    String choice = in.nextLine();

                    switch (choice) {

                        case "a":
                            System.out.println("\n----SEARCH BY ID----\n");
                            System.out.println("Enter citizen id: ");
                            ID = in.nextLine();

                            // Search by id
                            if (app.search(ID).equals("")) {
                                System.out.println("Citizen Not Found");
                            } else {
                                System.out.println("Citizen Found: " + app.search(ID));
                                // System.out.println(app.search(ID));
                            }

                            break;

                        case "b":
                            System.out.println("\n----SEARCH BY FIRST AND LAST NAME----\n");
                            System.out.println("Enter citizen first name: ");
                            String firstName = in.nextLine();

                            System.out.println("Enter citizen last name: ");
                            String lastName = in.nextLine();

                            // search by name
                            try {
                                if (app.search(firstName, lastName).length == 0) {
                                    System.out.println("Citizen Not Found");
                                } else {
                                    System.out.println(
                                            "Citizen Found: " + Arrays.toString(app.search(firstName, lastName)));
                                }
                            } catch (CompletionException e) {
                                System.out.println(e.getLocalizedMessage());
                            }

                            break;

                        case "c":
                            System.out.println("\n----SEARCH BY BIOMETRIC----\n");
                            System.out.println("Enter the value for the Biometric data: ");
                            value = in.nextLine();

                            do {
                                try {
                                    System.out.println(
                                            "Enter citizen's biometric tag (F for fingerprint or D for DNA): ");
                                    tag = in.nextLine().toUpperCase();
                                    if (tag.equals("F") || tag.equals("D")) {
                                        break;
                                    } else {
                                        System.out.println(
                                                "Invalid entry. Please enter 'F' for fingerprint or 'D' for DNA");
                                    }
                                } catch (Exception e) {
                                    System.out
                                            .println("Invalid entry. Please enter 'F' for fingerprint or 'D' for DNA");
                                }
                            } while (true);
                            // Search by biometric data
                            try {
                                String result = app.search(tag.charAt(0), value);
                                if (result.isEmpty() || result.isBlank()) {
                                    System.out.println("Citizen Not Found");
                                } else {
                                    System.out.println(result);
                                }
                            } catch (CompletionException e) {
                                System.out.println(e.getLocalizedMessage());
                            }
                            break;
                    } // end of switch for case "g"
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;

                case "h":
                    System.out.println("\n----ADD CITIZEN BIOMETRIC DATA----\n");
                    System.out.println("Enter citizen id: ");
                    ID = in.nextLine();
                    do {
                        try {
                            System.out.println("Enter citizen's biometric tag (F for fingerprint or D for DNA): ");
                            tag = in.nextLine().toUpperCase();
                            if (tag.equals("F") || tag.equals("D")) {
                                break;
                            } else {
                                System.out.println("Invalid entry. Please enter 'F' for fingerprint or 'D' for DNA");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid entry. Please enter 'F' for fingerprint or 'D' for DNA");
                        }
                    } while (true);
                    System.out.println("Enter the value for the Biometric data: ");
                    value = in.nextLine();
                    try {
                        app.addBiometric(ID, tag + value);
                        System.out.println("Request Complete");
                    } catch (InvalidParameterException e) {
                        System.out.println(
                                "The format of the data was incorrect. The tag must be 'F' or 'D'. Changes not saved");
                    } catch (CompletionException ce) {
                        System.out.println(
                                "Error. " + ce.getLocalizedMessage() + " " + ce.getCause().getLocalizedMessage());
                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;
                case "i":
                    System.out.println("\n----GET CITIZEN BIOMETRIC DATA----\n");
                    System.out.println("Enter citizen id: ");
                    ID = in.nextLine();
                    do {
                        try {
                            System.out.println("Enter citizen's biometric tag (F for fingerprint or D for DNA): ");
                            tag = in.nextLine().toUpperCase();
                            if (tag.equals("F") || tag.equals("D")) {
                                break;
                            } else {
                                System.out.println("Invalid entry. Please enter 'F' for fingerprint or 'D' for DNA");
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid entry. Please enter 'F' for fingerprint or 'D' for DNA");
                        }
                    } while (true);
                    try {
                        System.out.println("Data value: " + app.getBiometric(ID, tag));
                        System.out.println("Request Complete");
                    } catch (CompletionException e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    System.out.println("Press enter to return to the menu...");
                    in.nextLine();
                    break;
                case "j":
                    try {
                        System.out.println("Shutting down and finalizing changes....");
                        app.shutdown();
                        System.out.println("Shutdown complete, press enter to exit the TextUI.");
                        in.nextLine();
                    } catch (FileNotFoundException m) {
                        System.out.println("File not found");
                    } catch (IOException n) {
                        System.out.println("An unknown error occured while writing to the file.");
                    }

                    break;

                default:
                    System.out.println("Invalid choice. Select from a-j");
                    System.out.println("Press enter to try again...");
                    in.nextLine();
                    break;
            }

        } while (!(option.equalsIgnoreCase("j")));// end of do while loop
        in.close();

    }

    /*
     * public static void main(String[] args) { TextUI ui = new TextUI(); try {
     * ui.go(new SNIDApp("data.db", ',')); } catch (Exception e) {
     * e.printStackTrace(); } }
     */

}
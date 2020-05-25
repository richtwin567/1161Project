package app;

import java.util.*;
import java.util.concurrent.CompletionException;
import java.util.regex.PatternSyntaxException;
import java.io.*;
import java.security.InvalidParameterException;

import data.*;
import snid.*;

/**
 * The app for the SNID management. Interacts with the database and the TextUI
 * and the SNIDGUI
 * 
 * @see data.SNIDDb
 * @see ui.SNIDGUI
 */
public class SNIDApp {

    // SNIDDb data to be used to populate Citizen records.
    private ArrayList<Citizen> records;
    private SNIDDb data;

    /**
     * <p>
     * This constructor creates a SNIDDb object which fetches data from the
     * specified file and builds a list of objects of type Citizen using data from
     * the file
     * </p>
     * 
     * @param fileName  - Name of file
     * @param delimiter - character which separated data in the file
     * @throws InvalidParameterException if the file contains incompatible biometric
     *                                   information. Biometric data must have a tag
     *                                   as specified in {@link data.BiometricData
     *                                   BiometricData}
     * @throws FileNotFoundException     if the database file cannot be
     *                                   found/created
     * @throws IOException               if something goes wrong while reading from
     *                                   the database
     * @throws Exception                 if an unknown problem occurs, or an error
     *                                   while access a null object, or an invalid
     *                                   array index
     * @throws PatternSyntaxException    if the delimeter for the data is invalid
     * @throws NumberFormatException     indicates that the file data has been
     *                                   corrupted or is invalid for one or more
     *                                   records
     */
    public SNIDApp(String fileName, char delimiter) throws FileNotFoundException, IOException, Exception {
        records = new ArrayList<>();
        try {

            File pas = new File(fileName);
            pas.createNewFile();
            data = new SNIDDb(fileName, delimiter);
            String[] tokens;
            while (data.hasNext()) {
                // get the next set of citizen data from the file
                tokens = data.getNext();
                try {
                    // separate citizen data
                    String id = tokens[0];
                    char gender = tokens[1].charAt(0);
                    int yob = Integer.parseInt(tokens[2]);
                    String firstName = tokens[3];
                    String middleName = tokens[4];
                    String lastName = tokens[5];
                    char lifeStatus = tokens[6].charAt(0);
                    String motherId = tokens[7];
                    String fatherId = tokens[8];
                    Citizen mother = null;
                    Citizen father = null;
                    try {
                        mother = searchDb(motherId);
                        father = searchDb(fatherId);
                    } catch (Exception e) {

                    }
                    Address address = new Address(tokens[11]);

                    // create basic citizen record
                    Citizen citizen = new Citizen(id, gender, yob, firstName, middleName, lastName, lifeStatus, mother,
                            father, address);

                    try {
                        // separate biometric data
                        String[] biometricData = tokens[9].split("&");
                        // add biometric data to record
                        for (String data : biometricData) {
                            BiometricData biodata = new BiometricData(data.charAt(0), data.substring(1));
                            citizen.addBiometric(biodata);
                        }
                    } catch (Exception e) {
                        // no biometric data exists
                    }

                    try {
                        // separate the Civic doc tokens according to how they are stored
                        String[] docs = tokens[10].split("@");
                        String[][] docParts = new String[docs.length][4];
                        for (int x = 0; x < docs.length; x++) {
                            String[] parts = docs[x].split("\\|");
                            docParts[x] = parts;
                        }
                        // add all CivicDoc to record
                        for (String[] doc : docParts) {
                            if (doc[0].equals("M")) {
                                citizen.addPaper(new MarriageCertificate(doc[1], doc[2], doc[3]));
                            } else {
                                citizen.addPaper(new DeathCertificate(doc[1], doc[2], doc[3]));
                            }
                        }
                    } catch (Exception e) {
                        // no civic docs
                    }

                    // add the citizen to the list of records
                    records.add(citizen);
                } catch (InvalidParameterException | NumberFormatException | IndexOutOfBoundsException e) {
                    continue;
                }
            }
            // precautionary sorting
            Collections.sort(records);
        } catch (FileNotFoundException f) {
            throw f;
        } catch (PatternSyntaxException | NullPointerException | IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Searches the citizen database by ID and returns the citizen if found
     * 
     * @param id The ID number of the citizen to be found
     * @return The citizen if found or null if not found
     */
    private Citizen searchDb(String id) {
        int m, l, f;
        f = 0;
        l = records.size() - 1;
        try {
            while (f <= l) {
                m = (f + l) / 2;
                if (records.get(m).getId().compareTo(id) == 0) {
                    return records.get(m);
                } else if (records.get(m).getId().compareTo(id) < 0) {
                    f = m + 1;
                } else {
                    l = m - 1;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Creates a death certificate for the deceased citizen and attaches it to their
     * record. The life status of the citizen will also be set to dead on their
     * record.
     * 
     * @param id    The ID number of the dead citizen
     * @param cause the cause of death
     * @param place the place of death
     * @param date  the date of death
     * @throws CompletionException if registering the death of the citizen fails
     *                             because the ID passes does not exist in the
     *                             database or some other unknown reason.
     */
    public void registerDeath(String id, String cause, String place, String date) {
        try {
            Citizen newlyDead = searchDb(id);
            newlyDead.setLifeStatus(1);
            newlyDead.addPaper(new DeathCertificate(cause, date, place));
        } catch (Exception e) {
            throw new CompletionException("Death could not be registered", e);
        }
    }

    /**
     * Creates a marriage certifiacte for the newly weds and attaches the
     * certificate to both their records.
     * 
     * @param groomId the groom's ID number
     * @param brideId the bride's ID number
     * @param date    the date of the marraige
     * @throws CompletionException if the marraige could not be registered because
     *                             the bride and/or the groom's ID passed do not
     *                             exist in the database or some other unknown
     *                             reason.
     */
    public void registerMarriage(String groomId, String brideId, String date) {
        try {
            Citizen bride = searchDb(brideId);
            Citizen groom = searchDb(groomId);
            bride.changeLastName(groom.getNameAttr().getLastName());
            bride.addPaper(new MarriageCertificate(groomId, brideId, date));
            groom.addPaper(new MarriageCertificate(groomId, brideId, date));
        } catch (Exception e) {
            throw new CompletionException("Marriage could not be registered", e);
        }
    }

    /**
     * Formats the address of a specified ciizen (by ID) into a mailing Label
     * 
     * @param id the ID of the citizen whose address should be formatted into a
     *           mailing label
     * @return the mailing label
     */
    public String mailingLabel(String id) {
        try {
            Citizen citizen = searchDb(id);
            return String.format("%s\n%s", citizen.getNameFull(), citizen.getAddress());
        } catch (Exception e) {
            // Citizen not found
            return null;
        }
    }

    /**
     * Gets the information of the mother of a particular citizen. Information is in
     * the format 434552,Lisa,Anne,Rodney where 434552 is the mother's ID number,
     * Lisa is her first name, Anne is her middle name, Rodney is her last name.
     * 
     * @param id the ID of the citizen whose mother's information should be fetched
     * @return A {@code String} with the mother's information if the citizen has a
     *         mother or and empty {@code String} if the citizen has no mother or
     *         the citizen's ID can't be found in the database
     */
    public String getMother(String id) {
        try {
            Citizen person = searchDb(id);
            Citizen mother = (Citizen) person.getParent('M');
            return String.format("%s,%s,%s,%s", mother.getId(), mother.getNameAttr().getFirstName(),
                    mother.getNameAttr().getMiddleName(), mother.getNameAttr().getLastName());
        } catch (Exception e) {
            // This citizen has no mother
            return "";
        }
    }

    /**
     * Gets the information of the father of a particular citizen. Information is in
     * the format 545669,Brian,Joseph,Charles where 545669 is the father's ID
     * number, Brian is his first name, Joseph is his middle name, Charles is his
     * last name.
     * 
     * @param id the ID of the citizen whose father's information should be fetched
     * @return A {@code String} with the father's information if the citizen has a
     *         father or and empty {@code String} if the citizen has no father or
     *         the citizen's ID can't be found in the database
     */
    public String getFather(String id) {
        try {
            Citizen person = searchDb(id);
            Citizen father = (Citizen) person.getParent('M');
            return String.format("%s,%s,%s,%s", father.getId(), father.getNameAttr().getFirstName(),
                    father.getNameAttr().getMiddleName(), father.getNameAttr().getLastName());
        } catch (Exception e) {
            // This citizen has no father
            return "";
        }
    }

    /**
     * Searches the database by citizen ID and return's the citizen's information if
     * the citizen is found Information is in the format 434552,Lisa,Anne,Rodney
     * where 434552 is the ID number, Lisa is the first name, Anne is the middle
     * name, Rodney is the last name.
     * 
     * @param id the ID of the citizen to search for
     * @return the citizen's information or and empty {@code String}
     */
    public String search(String id) {
        try {
            Citizen citizen = searchDb(id);
            return String.format("%s,%s,%s,%s,%s", citizen.getId(), citizen.getGender() == 'M' ? "Male" : "Female",
                    citizen.getNameAttr().getFirstName(), citizen.getNameAttr().getMiddleName(),
                    citizen.getNameAttr().getLastName());
        } catch (Exception e) {
            // No matching citizen found
            return "";
        }
    }

    /**
     * Searches the database by citizen ID and return's the citizen's information
     * formatted fo the GUI
     * 
     * @param id the ID of the citizen to search for
     * @return the citizen's information or and empty {@code String}
     */
    public String searchGUI(String id) {
        try {
            Citizen citizen = searchDb(id);
            return citizen.toGUIPrint();
        } catch (Exception e) {
            // No matching citizen found
            throw new CompletionException("something went wrong", e);
        }
    }

    /**
     * Searches the database by first and last name. A list of the information for
     * all matches is compiled. Information is in the format 434552,Lisa,Anne,Rodney
     * where 434552 is the ID number, Lisa is the first name, Anne is the middle
     * name, Rodney is the last name.
     * 
     * @param firstName the first name of the citizen(s) to be found.
     * @param lastName  the last name of the citizen(s) to be found
     * @return a list of the information for all matchesor an empty list
     * @throws CompletionException if something goes wrong during the search or
     *                             compiling the list and it could not be completed
     */
    public String[] search(String firstName, String lastName) {
        ArrayList<String> matches = new ArrayList<>();
        try {
            for (Citizen citizen : records) {
                if (citizen.getNameAttr().getFirstName().equals(firstName)
                        && citizen.getNameAttr().getLastName().equals(lastName)) {
                    String print = String.format("%s,%s,%s,%s,%s", citizen.getId(),
                            citizen.getGender() == 'M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),
                            citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
                    matches.add(print);
                }
            }
        } catch (Exception e) {
            throw new CompletionException("Search could not be completed", e);
        }
        matches.trimToSize();
        return matches.toArray(new String[matches.size()]);
    }

    /**
     * Searches the database by first and last name. A list of the information for
     * all matches is compiled for the GUI
     * 
     * @param firstName the first name of the citizen(s) to be found.
     * @param lastName  the last name of the citizen(s) to be found
     * @return a list of the information for all matchesor an empty list
     * @throws CompletionException if something goes wrong during the search or
     *                             compiling the list and it could not be completed
     */
    public String[] searchGUI(String firstName, String lastName) {
        ArrayList<String> matches = new ArrayList<>();
        try {
            for (Citizen citizen : records) {
                if (citizen.getNameAttr().getLastName().equals(lastName)) {
                    matches.add(citizen.toGUIPrint());
                }
            }
        } catch (Exception e) {
            throw new CompletionException("Search could not be completed", e);
        }
        matches.trimToSize();
        return matches.toArray(new String[matches.size()]);
    }

    /**
     * Searches for a citizen by their biometric data and returns their information.
     * Information is in the format 434552,Lisa,Anne,Rodney where 434552 is the ID
     * number, Lisa is the first name, Anne is the middle name, Rodney is the last
     * name.
     * 
     * @param tag   the type of biometric data being used to search
     * @param value the biometric value
     * @throws CompletionException if something goes wrong while trying to search
     *                             the database
     * @return the citzen's information if found or an empty {@code String} if not
     *         found
     */
    public String search(char tag, String value) {
        try {
            BiometricData key = new BiometricData(tag, value);
            for (Citizen citizen : records) {
                Biometric biodata = citizen.getBiometric(Character.toString(tag));
                if (biodata != null) {
                    if (((BiometricData) biodata).match(key) == 0) {
                        return String.format("%s,%s,%s,%s,%s", citizen.getId(),
                                citizen.getGender() == 'M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),
                                citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
                    }
                }
            }
        } catch (Exception e) {
            throw new CompletionException("Something went wrong while searching the database", e);
        }
        return "";
    }

    /**
     * Searches for a citizen by their biometric data and returns their information
     * for the GUI.
     * 
     * @param tag   the type of biometric data being used to search
     * @param value the biometric value
     * @throws CompletionException if something goes wrong while trying to search
     *                             the database
     * @return the citzen's information if found or an empty {@code String} if not
     *         found
     */
    public String searchGUI(char tag, String value) {
        try {
            BiometricData key = new BiometricData(tag, value);
            for (Citizen citizen : records) {
                Biometric biodata = citizen.getBiometric(Character.toString(tag));
                if (biodata != null) {
                    if (((BiometricData) biodata).match(key) == 0) {
                        return citizen.toGUIPrint();
                    }
                }
            }
        } catch (Exception e) {
            throw new CompletionException("Something went wrong while searching the database", e);
        }
        return "";
    }

    /**
     * <p>
     * This method adds a new Citizen object to the system and generates an unique
     * i.d for the object, attributes are set to default for the object and
     * lifeStatus is set to 'A' for the object
     * </p>
     * 
     * @param gender - character specifying the gender of citizen
     * @param yob    - integer representing the year of birth
     * @param fname  - String representing the first name of individuals
     * @param mname  - String representing the middle name of individuals
     * @param lname  - String representing the last name of individuals
     * @throws CompletionException if creating the new record or adding it to the
     *                             database fails
     */
    public void registerBirth(char gender, int yob, String fname, String mname, String lname) {
        try {
            Citizen person = new Citizen(gender, yob, fname, mname, lname);
            // person.setLifeStatus(0);
            records.add(person);
            Collections.sort(records);
        } catch (Exception e) {
            throw new CompletionException("Birth could not be registered", e);
        }
    }

    // /**
    // * <p>
    // * This method follows the algorithm of binary search but takes in only the
    // * search id key to find the object
    // * </p>
    // *
    // * @param key - ID to be search for
    // * @return {@code positive integer} representing the index location of the id
    // is
    // * found and {@code negative integer} if the index location cannot be
    // * found or the id does not exist
    // */
    // private int binarySearch(String key) {
    // int low = 0;
    // int high = records.size() - 1;

    // while (low <= high) {
    // int mid = (int) ((low + high) / 2);
    // String midVal = (records.get(mid)).getId();
    // int cmp = midVal.compareTo(key);

    // if (cmp < 0)
    // low = mid + 1;
    // else if (cmp > 0)
    // high = mid - 1;
    // else
    // return mid; // key found
    // }

    // return -(low + 1); // key not found
    // }

    /**
     * <p>
     * This method updates the reference locations for a Citizen object using a
     * specified Id for the object to be updated and the Id of the parent objects
     * </p>
     * 
     * @param updateID - ID for the object to be updated
     * @param fatherID - ID for the father object
     * @param motherID - ID for the mother object
     * @throws CompletionExeption if the record for either parent(s) or child is not
     *                            found
     */
    public void addParentData(String updateID, String fatherID, String motherID) throws CompletionException {
        try {
            Citizen person = searchDb(updateID);
            Citizen mother = searchDb(motherID);
            Citizen father = searchDb(fatherID);

            person.setParent('F', father);
            person.setParent('M', mother);

        } catch (Exception e) {
            throw new CompletionException("One or more persons could not be found", e);
        }

    }

    /**
     * <p>
     * This method updates the reference location for the address attribute of a
     * Citizen object
     * </p>
     * 
     * @param updateID - ID for the object to be updated
     * @param street   - street line of Address
     * @param town     - town line of Address
     * @param parish   - parish line of Address
     * @param country  - country line of Address
     * 
     */
    public void updateAddress(String updateID, String street, String town, String parish, String country)
            throws CompletionException {

        try {
            String line = street + "|" + town + "|" + parish + "|" + country;
            Citizen person = searchDb(updateID);
            person.setAddress(new Address(line));
        } catch (Exception e) {
            throw new CompletionException("Person could not be found", e);
        }

    }

    /**
     * Add biometric data to a citizen's record
     * 
     * @param id   the ID of the citizen whose record the biometric data should be
     *             added to.
     * @param data the biometric data to be added
     * @throws InvalidParameterException if the biometric data is in an incompatible
     *                                   format
     * @throws CompletionException       if the data could not be added to the
     *                                   citizen's record, most likely because the
     *                                   citizen was not found
     */
    public void addBiometric(String id, String data) {
        try {
            Citizen citizen = searchDb(id);
            citizen.addBiometric(new BiometricData(data.charAt(0), data.substring(1)));
        } catch (InvalidParameterException p) {
            throw p;
        } catch (Exception e) {
            throw new CompletionException("The biometric data could not be added to the ciizen's record", e);
        }
    }

    /**
     * Gets the value of a citizen's biometric data
     * 
     * @param id  The citizen's ID
     * @param tag the type of biometric data to get as specified in
     *            {@link data.BiometricData BiometricData}
     * @return the biometric value
     * @throws CompletionException if the biometric data could not be found or
     *                             retreived or the citizen could not be found
     */
    public String getBiometric(String id, String tag) {
        try {
            Citizen citizen = searchDb(id);
            return citizen.getBiometric(tag).getValue();
        } catch (Exception e) {
            throw new CompletionException("Biometric data could not be retrieved", e);
        }
    }

    /**
     * Shutsdown the program. All records are saved before termination.
     * 
     * @throws FileNotFoundException if the file to be written to cannot be
     *                               found/created
     * @throws IOException           if something goes wrong while trying to write
     *                               the records to file
     */
    public void shutdown() throws FileNotFoundException, IOException {
        try {
            data.rewrite();
            for (Citizen citizen : records) {
                ArrayList<String> tokens = new ArrayList<>();
                StringBuffer docs = new StringBuffer("");
                StringBuffer biodata = new StringBuffer("");
                StringBuffer address = new StringBuffer("");
                String[] addressParts;
                tokens.add(citizen.getId());
                tokens.add(Character.toString(citizen.getGender()));
                tokens.add(Integer.toString(citizen.getYOB()));
                tokens.add(citizen.getNameAttr().getFirstName());
                tokens.add(citizen.getNameAttr().getMiddleName());
                tokens.add(citizen.getNameAttr().getLastName());
                tokens.add(Character.toString(citizen.getLifeStatus()));
                try {
                    tokens.add(citizen.getParent('M').getId());
                } catch (Exception e) {
                    tokens.add("");
                }
                try {
                    tokens.add(citizen.getParent('F').getId());
                } catch (Exception e) {
                    tokens.add("");
                }
                for (Biometric data : citizen.getBiometricList()) {
                    biodata.append(data.toString());
                }
                tokens.add(biodata.toString());
                for (CivicDoc doc : citizen.getPapers()) {
                    if (doc.getType() == 'M') {
                        docs.append(String.format("%c|%s|%s|%s@", doc.getType(),
                                ((MarriageCertificate) doc).getGroomId(), ((MarriageCertificate) doc).getBrideId(),
                                ((MarriageCertificate) doc).getDate()));
                    } else {
                        docs.append(String.format("%c|%s|%s|%s@", doc.getType(), ((DeathCertificate) doc).getCause(),
                                ((DeathCertificate) doc).getDate(), ((DeathCertificate) doc).getPlace()));
                    }
                }
                tokens.add(docs.toString());
                try {
                    addressParts = citizen.getAddress().toString().split("\n");
                    address.append(String.format("%s|", addressParts[0]));
                    for (int x = 1; x < addressParts.length - 1; x++) {
                        address.append(String.format("%s|", addressParts[x]));
                    }
                    address.append(addressParts[addressParts.length - 1]);
                } catch (Exception e) {
                } finally {
                    tokens.add(address.toString());
                }
                data.putNext(tokens.toArray(new String[tokens.size()]));
            }
            data.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

   /*  
    public ArrayList<Citizen> getRecords() {
        return records;
    }

    public static void main(String[] args) {
        int choice = 0;
        String id;
        String updateID;
        String date;
        Scanner sc = new Scanner(System.in);
        
            try {
                SNIDApp app = new SNIDApp("data.db", ',');
                
                while (true) {
                    System.out.println("");
                    for (Citizen citizen : app.getRecords()) {
                        System.out.println(citizen.getId() + " "+ citizen.getName());
                    }
                System.out.println(
                        "1\t-\tRegister Birth\n2\t-\tRegister Marriage\n3\t-\tRegister Death\n4\t-\tGet mother\n5\t-\tGet Father\n6\t-\tSearch for citizen\n7\t-\tAdd Parent\n8\t-\tUpdate Address\n9\t-\tAdd Biometric\n10\t-\tShutdown");
                
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        char gender = sc.nextLine().charAt(0);
                        int yob = sc.nextInt();
                        sc.nextLine();
                        String fname = sc.nextLine();
                        String mname = sc.nextLine();
                        String lname = sc.nextLine();
                        app.registerBirth(gender, yob, fname, mname, lname);
                        break;
                    case 2:
                        String groomId = sc.nextLine();
                        String brideId = sc.nextLine();
                        date = sc.nextLine();
                        app.registerMarriage(groomId, brideId, date);
                        break;
                    case 3:
                        id = sc.nextLine();
                        String cause = sc.nextLine();
                        String place = sc.nextLine();
                        date = sc.nextLine();
                        app.registerDeath(id, cause, place, date);
                        break;
                    case 4:
                        id = sc.nextLine();
                        app.getMother(id);
                        break;
                    case 5:
                        id = sc.nextLine();
                        app.getFather(id);
                        break;
                    case 6:
                        break;
                    case 7:
                        updateID = sc.nextLine();
                        String fatherID = sc.nextLine();
                        String motherID = sc.nextLine();
                        app.addParentData(updateID, fatherID, motherID);
                        break;
                    case 8:
                        updateID = sc.nextLine();
                        String street = sc.nextLine();
                        String town = sc.nextLine();
                        String parish = sc.nextLine();
                        String country = sc.nextLine();
                        app.updateAddress(updateID, street, town, parish, country);
                        break;
                    case 9:
                        id = sc.nextLine();
                        String data = sc.nextLine();
                        app.addBiometric(id, data);
                        break;
                    case 10:
                        app.shutdown();
                        break;
                    default:
                        break;

                }
                if (choice == 10) {
                    break;
                }
            }

                /*
                 * app.registerBirth('F', 1970, "Lucy", "Annie", "George");
                 * app.registerBirth('M', 1972, "Harry", "Joseph", "George");
                 * app.updateAddress("00000001", "12 Test lane", "Town1", "Parish2", "Jamaica");
                 * app.updateAddress("00000002", "12 Test lane", "Town1", "Parish2", "Jamaica");
                 * String label = app.mailingLabel("00000001"); System.out.println(label);
                 * app.registerMarriage("00000002", "00000001", "02/04/2001");
                 * app.registerBirth('M', 2005, "Tim", "Mike", "George");
                 * app.addParentData("00000003", "00000002", "00000001");
                 * app.registerDeath("00000001", "Childbirth", "UWI hospital", "06/07/2005");
                 * app.registerBirth('F', 2000, "Annie", "Marie", "Bishop");
                 * app.addBiometric("00000004", "F9097");
                 * System.out.println(app.search("00000003")); System.out.println("complete");
                 * System.out.println(app.search("00000004")); System.out.println("complete");
                 * System.out.println(((Citizen)(app.searchDb("00000003")).getParent('F')).
                 * getNameFull());
                 
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //
                e.printStackTrace();
                
            }
            
        sc.close();
    } */
}

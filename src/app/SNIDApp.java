package app;

import java.util.*;
import java.util.concurrent.CompletionException;
import java.io.*;
import data.*;
import snid.*;

/**
 * The app for the SNID management. Interacts with the database and the TextUI
 * 
 * @see data.SNIDDb
 * 
 */
public class SNIDApp {

    // SNIDDb to be used to populate citiens.
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
     * @throws IndexOutOfBoundsException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     */
    public SNIDApp(String fileName, char delimiter) throws FileNotFoundException, IOException, Exception {
        records = new ArrayList<>();
        try {
            File pas = new File(fileName);
            pas.createNewFile();
            /*
             * System.out.println("File created"); }else{
             * System.out.println("File already exists"); }
             */

            data = new SNIDDb(fileName, delimiter);
            String[] tokens;

            while (data.hasNext()) {
                tokens = data.getNext();
                Citizen citizen = new Citizen(tokens[0].charAt(0), Integer.parseInt(tokens[1]), tokens[2], tokens[3],
                        tokens[4]);

                for (int i = 5; i < tokens.length; i++) {
                    String[] docParts = tokens[i].split("\\|");
                    if (docParts[0].equals("M")) {
                        citizen.addPaper(new MarriageCertificate(docParts[1], docParts[2], docParts[3]));
                    } else {
                        citizen.addPaper(new DeathCertificate(docParts[1], docParts[2], docParts[3]));
                    }
                }
                //TODO add biometric data from file
                records.add(citizen);
            }
            Collections.sort(records);

        } catch (IndexOutOfBoundsException o) {
            throw o;
        } catch (FileNotFoundException f) {
            throw f;
        } catch (IOException i) {
            throw i;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 
     * @param id
     * @return
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
     * 
     * @param id
     * @param cause
     * @param place
     * @param date
     * @throws CompletionException
     */
    public void registerDeath(String id, String cause, String place, String date) {
        try {
            Citizen newlyDead = searchDb(id);
            newlyDead.setLifeStatus(1);
            newlyDead.addPaper(new DeathCertificate(cause, date, place));
        } catch (Exception e) {
            // System.out.println("Death could not be registered");
            throw new CompletionException("Death could not be registered", e);
        }
    }

    /**
     * 
     * @param groomId
     * @param brideId
     * @param date
     * @throws CompletionException
     */
    public void registerMarriage(String groomId, String brideId, String date) {
        try {
            Citizen bride = searchDb(brideId);
            Citizen groom = searchDb(groomId);
            bride.addPaper(new MarriageCertificate(groomId, brideId, date));
            groom.addPaper(new MarriageCertificate(groomId, brideId, date));
        } catch (Exception e) {
            // System.out.println("Marriage could not be registered");
            throw new CompletionException("Marriage could not be registered", e);
        }
    }

    /**
     * 
     * @param id
     * @return
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
     * 
     * @param id
     * @return
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
     * 
     * @param id
     * @return
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
     * 
     * @param id
     * @return
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
     * 
     * @param firstName
     * @param lastName
     * @return
     * @throws CompletionException
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
        return (String[]) matches.toArray();
    }

    /**
     * 
     * @param tag
     * @param value
     * @return
     */
    public String search(char tag, String value) {
        try {
            BiometricData key = new BiometricData(tag, value);
            for (Citizen citizen : records) {
                Biometric biodata = citizen.getBiometric(Character.toString(tag));
                if (((BiometricData) biodata).match(key) == 0) {
                    return String.format("%s,%s,%s,%s,%s", citizen.getId(),
                            citizen.getGender() == 'M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),
                            citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
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
     * @throws CompletionException
     */
    public void registerBirth(char gender, int yob, String fname, String mname, String lname) {
        try {
            Citizen person = new Citizen(gender, yob, fname, mname, lname);
            person.setLifeStatus(0);
            records.add(person);
            Collections.sort(records);
        } catch (Exception e) {
            throw new CompletionException("Birth could not be registered", e);
        }
    }

    /**
     * <p>
     * This method follows the algorithm of binary search but takes in only the
     * search id key to find the object
     * </p>
     * 
     * @param key - ID to be search for
     * @return {@code positive integer} representing the index location of the id is
     *         found and {@code negative integer} if the index location cannot be
     *         found or the id does not exist
     */
    private int binarySearch(String key) {
        int low = 0;
        int high = records.size() - 1;

        while (low <= high) {
            int mid = (int) ((low + high) / 2);
            String midVal = (records.get(mid)).getId();
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }

        return -(low + 1); // key not found
    }

    /**
     * <p>
     * This method updates the reference locations for a Citizen object using a
     * specified Id for the object to be updated and the Id of the parent objects
     * </p>
     * 
     * @param updateID - ID for the object to be updated
     * @param fatherID - ID for the father object
     * @param motherID - ID for the mother object
     * @throws IndexOutOfBoundsException
     */
    public void addParentData(String updateID, String fatherID, String motherID) throws IndexOutOfBoundsException {

        // Collections.sort(records);
        int posid = binarySearch(updateID);
        int fid = binarySearch(fatherID);
        int mid = binarySearch(motherID);
        if (posid < 0 || fid < 0 || mid < 0)
            throw new IndexOutOfBoundsException("Invalid Array Index");
        (records.get(posid)).setParent('F', records.get(fid));
        (records.get(posid)).setParent('M', records.get(mid));

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
     * @throws IndexOutOfBoundsException
     */
    public void updateAddress(String updateID, String street, String town, String parish, String country)
            throws IndexOutOfBoundsException {
        String line = street + "|" + town + "|" + parish + "|" + country;
        int posid = binarySearch(updateID);
        if (posid < 0)
            throw new IndexOutOfBoundsException("Invalid Array Index");
        (records.get(posid)).setAddress(new Address(line));

    }


    /**
     * 
     * @param id
     * @param data
     */
    public void addBiometric(String id, String data){

    }

    /**
     * 
     */
    public String getBiometric(String id, String tag){
        return null;
    }

    /**
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void shutdown() throws FileNotFoundException, IOException {
        try {
            data.rewrite();
            for (Citizen citizen : records) {
                ArrayList<String> tokens = new ArrayList<>();
                tokens.add(Character.toString(citizen.getGender()));
                tokens.add(Integer.toString(citizen.getYOB()));
                tokens.add(citizen.getNameAttr().getFirstName());
                tokens.add(citizen.getNameAttr().getMiddleName());
                tokens.add(citizen.getNameAttr().getLastName());
                for (CivicDoc doc : citizen.getPapers()) {
                    if (doc.getType() == 'M') {
                        tokens.add(String.format("%c|%s|%s|%s", doc.getType(), ((MarriageCertificate) doc).getGroomId(),
                                ((MarriageCertificate) doc).getBrideId(), ((MarriageCertificate) doc).getDate()));
                    } else {
                        tokens.add(String.format("%c|%s|%s|%s", doc.getType(),
                                ((DeathCertificate) doc).getCause(), ((DeathCertificate) doc).getDate(),
                                ((DeathCertificate) doc).getPlace()));
                    }
                }
                //TODO put biometric data
                data.putNext(tokens.toArray(new String[tokens.size()]));
            }
            data.close();

        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

    }

    //TODO Delete getRecords in SNIDApp method. Only for testing
    private ArrayList<Citizen> getRecords(){
        return records;
    }

    public static void main(String[] args) {
        try {
            SNIDApp app = new SNIDApp("data.db", ',');
            for (Citizen citizen: app.getRecords()){
                System.out.println(citizen.printPapers());
            }
            app.registerBirth('F', 1970, "Lucy", "Annie", "George");
            app.registerBirth('M', 1972, "Harry", "Joseph", "George");
            app.updateAddress("00000001", "12 Test lane", "Town1", "Parish2", "Jamaica");
            app.updateAddress("00000002", "12 Test lane", "Town1", "Parish2", "Jamaica");
            String label = app.mailingLabel("00000001");
            System.out.println(label);
            app.registerMarriage("00000002", "00000001", "02/04/2001");
            app.registerBirth('M', 2005, "Tim", "Mike", "George");
            app.addParentData("00000003", "00000002", "00000001");
            app.registerDeath("00000001", "Childbirth", "UWI hospital", "06/07/2005");
            System.out.println(app.search("00000003"));
            System.out.println("complete");
            System.out.println(app.search("00000004"));
            app.shutdown();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
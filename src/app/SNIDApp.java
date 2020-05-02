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
    private ArrayList<DeathCertificate> deathLog;
    private ArrayList<MarriageCertificate> marriageLog;
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
     */
    public SNIDApp(String fileName, char delimiter) throws FileNotFoundException, IOException, Exception {
        records = new ArrayList<>();
        deathLog = new ArrayList<>();
        marriageLog = new ArrayList<>();
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
                records.add(
                        new Citizen(tokens[0].charAt(0), Integer.parseInt(tokens[1]), tokens[2], tokens[3], tokens[4]));
            }

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
        l = records.size();
        try {
            while (f <= l) {
                m = (f + (l - f)) / 2;
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
            deathLog.add(new DeathCertificate(id, cause, date, place));
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
            marriageLog.add(new MarriageCertificate(groom.getId(), bride.getId(), date));
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

        Collections.sort(records);
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

}
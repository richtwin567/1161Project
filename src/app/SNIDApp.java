  package app;

  import java.util.*;
  import java.io.*;
  import data.*;
  import snid.*;

/**
 * 
 */
public class SNIDApp {

    // SNIDDb to be used to populate citiens.
    private ArrayList<DeathData> deathLog;
    private ArrayList<MarriageData> marriageLog;
    private ArrayList<Citizen> citizens;
    private SNIDDb helper;

    

    /**
     * <p> This constructor creates a SNIDDb object	
     * which fetches data from the specified file and 
     * builds a list of objects of type Citizen using data from the file</p>
     * @param fileName - Name of file
     * @param delimiter - character which separated data in the file
     */
    public SNIDApp(String fileName, char delimiter){
        citizens = new ArrayList<>();
        deathLog = new ArrayList<>();
        marriageLog = new ArrayList<>();
        /*try{
            File pas = new File(fileName);
            if(pas.createNewFile()){
                System.out.println("File created");
            }else{
                System.out.println("File already exists");
            }
    
            helper = new SNIDDb(fileName,delimiter);
            String[] tokens;

            while(helper.hasNext()){
                tokens = helper.getNext();
                //citizens.add(new Citizen());
            }


        }catch(FileNotFoundException f){
            System.out.println(f.getMessage());
            f.printStackTrace();
        }catch(IOException i){
            System.out.println(i.getMessage());
            i.printStackTrace();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }*/
        
    }
  
    /**
     * 
     * @param id
     * @return
     */
    private Citizen searchDb(String id) {
        int m, l, f;
        f = 0;
        l = citizens.size();
        try {
            while (f <= l) {
                m = (f + (l - f)) / 2;
                if (citizens.get(m).getId().compareTo(id) == 0) {
                    return citizens.get(m);
                } else if (citizens.get(m).getId().compareTo(id) < 0) {
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
     */
    public void registerDeath(String id, String cause, String place, String date) {
        try {
            Citizen newlyDead = searchDb(id);
            newlyDead.setLifeStatus(1);
            deathLog.add(new DeathData(id, cause, date, place));
        } catch (Exception e) {
            //System.out.println("Death could not be registered");
        }
    }

    /**
     * 
     * @param groomId
     * @param brideId
     * @param date
     */
    public void registerMarriage(String groomId, String brideId, String date) {
        Citizen bride = searchDb(brideId);
        Citizen groom = searchDb(groomId);
        if (bride != null && groom != null) {
            marriageLog.add(new MarriageData(groomId, brideId, date));
        } else {
            //System.out.println("Marriage could not be registered");
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
            //Citizen not found
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
            //This citizen has no mother
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
            //This citizen has no father
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
            return String.format("%s,%s,%s,%s,%s", citizen.getId(), citizen.getGender()=='M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),
                    citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
    } catch (Exception e) {
        //No matching citizen found
        return "";
        }
    }

    /**
     * 
     * @param firstName
     * @param lastName
     * @return
     */
    public String[] search(String firstName, String lastName) {
        ArrayList<String> matches = new ArrayList<>();
        for (Citizen citizen: citizens){
            if (citizen.getNameAttr().getFirstName().equals(firstName) && citizen.getNameAttr().getLastName().equals(lastName)){
                String print = String.format("%s,%s,%s,%s,%s", citizen.getId(), citizen.getGender()=='M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
                matches.add(print);
            }
        }
        matches.trimToSize();
        return (String[])matches.toArray();
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
            for(Citizen citizen : citizens){
                Biometric biodata = citizen.getBiometric(Character.toString(tag));
                if (((BiometricData)biodata).match(key)==0){
                    return String.format("%s,%s,%s,%s,%s", citizen.getId(), citizen.getGender()=='M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
                }
            }            
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 
     */
    private class MarriageData {

        private String groomId;
        private String brideId;
        private String date;

        /**
         * 
         * @param groomId
         * @param brideId
         * @param date
         */
        public MarriageData(String groomId, String brideId, String date) {
            this.brideId = brideId;
            this.date = date;
            this.groomId = groomId;
        }

        /**
         * @return String return the groomId
         */
        public String getGroomId() {
            return groomId;
        }

        /**
         * @param groomId the groomId to set
         */
        public void setGroomId(String groomId) {
            this.groomId = groomId;
        }

        /**
         * @return String return the brideId
         */
        public String getBrideId() {
            return brideId;
        }

        /**
         * @param brideId the brideId to set
         */
        public void setBrideId(String brideId) {
            this.brideId = brideId;
        }

        /**
         * @return String return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(String date) {
            this.date = date;
        }
    }

    /**
     * 
     */
    private class DeathData {
        private String id;
        private String cause;
        private String date;
        private String place;

        /**
         * @param id
         * @param cause
         * @param date
         * @param place
         */
        public DeathData(String id, String cause, String date, String place) {
            this.id = id;
            this.cause = cause;
            this.date = date;
            this.place = place;

        }

        /**
         * @return String return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return String return the cause
         */
        public String getCause() {
            return cause;
        }

        /**
         * @param cause the cause to set
         */
        public void setCause(String cause) {
            this.cause = cause;
        }

        /**
         * @return String return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * @return String return the place
         */
        public String getPlace() {
            return place;
        }

        /**
         * @param place the place to set
         */
        public void setPlace(String place) {
            this.place = place;
        }

    }


    /**
     * <p> This method adds a new Citizen object to the system and generates an unique i.d for the object, 
     * attributes are set to default for the object and lifeStatus is set to 'A' for the object </p>
     * @param gender - character specifying the gender of citizen
     * @param yob - integer representing the year of birth
     * @param fname - String representing the first name of individuals
     * @param mname - String representing the middle name of individuals
     * @param lname - String representing the last name of individuals
     */
    public void registerBirth(char gender, int yob, String fname,String mname,String lname){
        Citizen person = new Citizen(gender,yob, fname, mname,lname);
        person.setLifeStatus(0);
        citizens.add(person);
    }
    
    /**
     * <p>This method follows the algorithm of binary search but takes in only the search id key to find the object</p>
     * @param key - ID to be search for
     * @return {@code positive integer} representing the index location of the id is found and 
     *          {@code negative integer} if the index location cannot be found or the id does not exist
     */
    private int binarySearch(String key){
        int low = 0;
        int high = citizens.size()-1;

        while (low <= high) {
            int mid = (int) ((low + high) / 2);
            String midVal = (citizens.get(mid)).getId();
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }

        return -(low + 1);  // key not found
    }

    /**
     * <p>This method updates the reference locations for a Citizen object using a specified Id for the object
     * to be updated and the Id of the parent objects</p>
     * @param updateID - ID for the object to be updated
     * @param fatherID - ID for the father object
     * @param motherID - ID for the mother object
     * @throws IndexOutOfBoundsException
     */
    public void addParentData(String updateID, String fatherID, String motherID) throws IndexOutOfBoundsException {
        
        Collections.sort(citizens);
        int posid = binarySearch(updateID);
        int fid = binarySearch(fatherID);
        int mid = binarySearch(motherID);
        if(posid < 0 || fid < 0 || mid < 0)
            throw new IndexOutOfBoundsException("Invalid Array Index");
        (citizens.get(posid)).setParent('F', citizens.get(fid));
        (citizens.get(posid)).setParent('M', citizens.get(mid));

    }

    /**
     * <p>This method updates the referenc location for the address attribute of a Citizen object </p>
     * @param updateID - ID for the object to be updated
     * @param street - street line of Address
     * @param town - town line of Address
     * @param parish - parish line of Address
     * @param country - country line of Address
     * @throws IndexOutOfBoundsException
     */
    public void updateAddress(String updateID, String street, String town,String parish,String country) throws IndexOutOfBoundsException {
        String line = street + "|" + town + "|" + parish + "|" + country;
        int posid = binarySearch(updateID);
        if(posid < 0)
            throw new IndexOutOfBoundsException("Invalid Array Index");
        (citizens.get(posid)).setAddress(new Address(line));
        
    }

}
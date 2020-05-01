package app;

import java.util.*;
import java.io.*;
import data.*;
import snid.*;


public class SNIDApp{

    private SNIDDb helper;
    private ArrayList<Citizen> citizens;

    /**
     * <p> This constructor creates a SNIDDb object	
     * which fetches data from the specified file and 
     * builds a list of objects of type Citizen using data from the file</p>
     * @param fileName - Name of file
     * @param delimiter - character which separated data in the file
     */
    public SNIDApp(String fileName, char delimiter){
        citizens = new ArrayList<>();
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
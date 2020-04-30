package snid;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class representing a Person.
 *
 * @author Anakai Richards
 */
public abstract class Person {

    private String id;
    private static Random tail = new Random();
    private static Random prefix = new Random();
    private char gender;
    private int yob;
    private char lifeStatus;
    private Person mother;
    private Person father;
    private ArrayList<Biometric> biodata;

    /**
     * The constructor for the Person class.
     *
     * @param gender the person's gender
     * @param yob the person's year of birth
     */
    public Person(char gender, int yob) {
        this.gender = gender;
        this.yob = yob;
        id = generateID();
        mother = null;
        father = null;
        lifeStatus = 'A';
        biodata = new ArrayList<>();
    }

    /**
     * The getter method for the person's ID
     *
     * @return the ID as a String
     */
    public String getId() {
        return id;
    }

    /**
     * The getter method for the person's gender. The gender will be either 'F'
     * for female or 'M' for male
     *
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * The getter method for the person's year of birth
     *
     * @return the year of birth
     */
    public int getYOB() {
        return yob;
    }

    /**
     * The getter method for the person's life status. A person can be either
     * 'A' for alive or 'D' for dead.
     *
     * @return the person's life status
     */
    public char getLifeStatus() {
        return lifeStatus;
    }

    /**
     * Updates the person's life status. The person's life status is determined
     * by 0 which indicated that they are alive or 1 which indicates that they
     * are dead.
     *
     * @param status an integer 1 or 0 to set the life status
     */
    public void setLifeStatus(int status) {
        if (status == 0) {
            this.lifeStatus = 'A';
        } else {
            this.lifeStatus = 'D';
        }
    }

    /**
     * The getter method for the person's parent(s). This method returns either
     * the mother or the father; not both. Mother is denoted by 'M' and father
     * is denoted by 'F'.
     *
     * @param type the type of parent the you want to get denoted by
     * {@code char} 'M' or 'F'
     * @return the parent as selected for by the argument passed to the method
     * @throws IllegalArgumentException if the argument is not a character 'F' or 'M'.
     */
    public Person getParent(char type) throws IllegalArgumentException{
        switch (Character.toUpperCase(type)) {
            case 'M':
                return mother;
            case 'F':
                return father;
            default:
                throw new IllegalArgumentException(
                        "The character entered must be 'M' to represent the mother or 'F' to represent the father");
        }
    }

    /**
     * The setter method for the person's parent(s). This method sets either the
     * mother or the father; not both. Mother is denoted by 'M' and father is
     * denoted by 'F'.
     *
     * @param type the type of parent the you want to set denoted by
     * {@code char} 'M' or 'F'
     * @param parent either the mother or the father of this person
     * @throws IllegalArgumentException if the argument is not a character 'F' or 'M'.
     */
    public void setParent(char type, Person parent) throws IllegalArgumentException{
        switch (Character.toUpperCase(type)) {
            case 'F':
                father = parent;
                break;
            case 'M':
                mother = parent;
                break;
            default:
                throw new IllegalArgumentException(
                        "The character entered for type must be 'M' to represent the mother or 'F' to represent the father");
        }
    }

    /**
     * Adds Biometric data to the person's Biometric information. This method is
     * a stub.
     *
     * @param data the biometric data to be added
     */
    public void addBiometric(Biometric data) {
        biodata.add(data);
    }

    /**
     * The getter method for the Biometric data. This method is a stub.
     *
     * @param tag the tag by which to search for and select the desired set of
     * biometric information
     * @return the biometric data
     */
    public ArrayList<Biometric> getBiometric(String tag) {
        return null;
    }

    /**
     * Creates a unique ID for each person instance.
     *
     * @return The person's unique ID as a String.
     */
    private String generateID() {
        int[] arr = {prefix.ints(65, 90).findFirst().getAsInt(),
                     prefix.ints(65, 90).findFirst().getAsInt(),
                     prefix.ints(65, 90).findFirst().getAsInt(),
                     prefix.ints(65, 90).findFirst().getAsInt()};//generate 4 random ints to convert to capital letters and assign them to an array
        return String.format("%c%c%c%c%08d", (char) arr[0], (char) arr[1],
                             (char) arr[2], (char) arr[3], tail.nextInt(100000000));//create an ID as a String  of 4 capital letters and 8 random digits
    }
}

package snid;

import java.util.ArrayList;

/**
 * The class representing a citizen of the country. A citizen is also a Person.
 * Their data can be compared to another person in the system.
 *
 * @see snid.Person Person
 * @author Anakai Richards
 */
public class Citizen
        extends Person
        implements Comparable<Citizen> {

    private ArrayList<CivicDoc> papers;
    private Name name;
    private Address address;

    /**
     * Constructor for the citizen class.
     *
     * @param gender the citizen's gender. Must be 'F' for female or 'M' for
     * male
     * @param yob the citizen's year of birth
     * @param firstName the citizen's first name
     * @param middleName the citizen's middle name
     * @param lastName the citizen's last name
     */
    public Citizen(char gender, int yob, String firstName, String middleName,
                   String lastName) {
        super(gender, yob);
        name = new Name(firstName, middleName, lastName);
        papers = new ArrayList<>();
        address = null;
    }

    /**
     * Gets the citizen's unique ID number.
     *
     * @return the citizen's ID number
     */
    @Override
    public String getId() {
        return super.getId();
    }

    /**
     * Updates the citizen's living address.
     *
     * @param address the citizen's address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Getter method for the citizen's address.
     *
     * @return the citizen's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Updates the citizen's last name
     *
     * @param newLast the new last name name for the citizen.
     */
    public void changeLastName(String newLast) {
        name.setLastName(newLast);
    }

    /**
     * Lexicographically compares the citizen's ID to another citizen's ID.
     *
     * @param o another citizen.
     * @return {@code 0} if the two IDs are equal, a negative integer if this
     * citizen's ID lexicographically precedes the argument ID or a positive
     * integer if this citizen's ID lexicographically comes after the argument
     * ID.
     */
    @Override
    public int compareTo(Citizen o) {
        return this.getId().compareTo(o.getId());
    }

    /**
     * Method created to provide access to getters of the {@link snid.Name Name} class in SNIDApp
     * @return the name attribute of this Citizen
     */
    public Name getNameAttr(){
        return name;
    }

    /**
     * Creates a string with the citizen's name in the format:<br/>
     * {@code BROWN, John Andrew} where "BROWN" is the last name, "John" is the
     * first name and "Andrew" is the middle name.
     *
     * @return the name of the citizen.
     */
    public String getNameFull() {
        //changed the format of the middle name from first initial to full name due 
        //to instructions in part 2
        return String.format("%s, %s %s", name.getLastName().toUpperCase(),
                             name.getFirstName(), name.getMiddleName());
    }

    /**
     * Creates a string with the citizen's name in the format:<br/>
     * {@code BROWN, John A.} where "BROWN" is the last name, "John" is the
     * first name and "A." is the middle name initial.
     *
     * @return the name of the citizen.
     */
    public String getName() {
        return String.format("%s, %s %s.", name.getLastName().toUpperCase(),
                             name.getFirstName(), name.getMiddleName().charAt(0));
    }

    /**
     * Getter for the person's Civic Documents
     * @return return the list of Civic Document papers
     */
    public ArrayList<CivicDoc> getPapers() {
        return papers;
    }

    /**
     * Adds either Marriage certificate or Death certificate to the person's record
     * @param papers Adds a new Civic document to list of papers
     */
    public void addPaper(CivicDoc paper) {
        papers.add(paper);
    }

    //TODO delete printPapers in Citizen method. only for testing
    public String printPapers(){
        StringBuffer printable = new StringBuffer("");
        for (CivicDoc doc :getPapers()){
            printable.append(String.format("%s\n%s\n", getNameFull(),doc.toString()));
        }
        return printable.toString();
    }


}

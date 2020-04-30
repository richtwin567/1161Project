package snid;

/**
 * Represents a person's name, i.e. first,middle and last name.
 *
 * @author Anakai Richards
 */
public class Name {

    private String fn;
    private String mn;
    private String ln;

    /**
     * Constructor for the Person class
     *
     * @param fn the person's first name
     * @param mn the person's middle name
     * @param ln the person's last name
     */
    public Name(String fn, String mn, String ln) {
        this.fn = fn;
        this.mn = mn;
        this.ln = ln;
    }

    /**
     * Gets the person's first name
     *
     * @return the person's first name as a String
     */
    public String getFirstName() {
        return fn;
    }

    /**
     * Gets the person's last name
     *
     * @return the person's last name as a String
     */
    public String getLastName() {
        return ln;
    }

    /**
     * Updates the person's last name
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.ln = lastName;
    }

    /**
     * Gets the persons middle name
     *
     * @return the person's middle name as a String
     */
    public String getMiddleName() {
        return mn;
    }

    /**
     * Checks if a name passed as an argument is the same as this name.
     *
     * @param name a name to be compared to the this name
     * @return {@code true} if the names are the same or {@code false} if they
     * are not.
     */
    public boolean equals(Name name) {
        return getFirstName().equals(name.getFirstName()) && getMiddleName().equals(
                name.getMiddleName()) && getLastName().equals(name.getLastName());
    }

    /**
     * Formats the entire person's name into a String
     *
     * @return a String representing the persons full name
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", getFirstName(), getMiddleName(),
                             getLastName());
    }

}

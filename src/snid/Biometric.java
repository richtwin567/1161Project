package snid;

/**
 * This defines the interactions the system should have with person's biometric
 * data
 *
 * @author Anakai Richards
 */
public interface Biometric {

    /**
     * Gets the person's unique biometric tag
     *
     * @return a String representing the biometric tag for that person
     */
    public String getTag();

    /**
     * Gets the biometric value for the person
     *
     * @return A string representing the biometric value for that person
     */
    public String getValue();

    /**
     * Matches two biometric data sets.
     *
     * @param other another set of biometric data
     * @return a number represent whether or not the match was successful
     */
    public int match(Biometric other);
}

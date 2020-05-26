package snid;

/**
 * This defines the interactions the system should have with person's biometric
 * data
 *
 * @author Anakai Richards - ID: 620132232
 * @author Matthew Palmer - ID: 620131688
 * @author Michael Young - ID: 620131387
 * @version 1.0
 */
public interface Biometric {

    /**
     * Gets the type of biometric data. 'F' represents Fingerprint. 'D' represents DNA.
     *
     * @return a String representing the biometric type
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

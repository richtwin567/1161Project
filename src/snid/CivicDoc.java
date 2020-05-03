package snid;

/**
 * This define the interactions the system should have with a Citizen's civic
 * documents
 *
 * @author Anakai Richards
 */
public interface CivicDoc {

    /**
     * Gets the reference number for the document
     *
     * @return the document's reference number as a String
     */
    public String getRefNo();

    /**
     * 
     * @return
     */
    public char getType();
}

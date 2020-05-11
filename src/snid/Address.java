package snid;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for storing a Person's address.
 *
 * @author Anakai Richards
 */
public class Address {

    ArrayList<String> addressLines;

    /**
     * Constructor for the Address class. Splits the address into different parts
     * and stores them.
     *
     * @param lines the lines of the address delimited by |
     */
    public Address(String lines) {
        String[] arr = lines.split("\\|");
        this.addressLines = new ArrayList<>(Arrays.asList(arr));
        addressLines.removeIf(e -> (e.equals("")));
    }

    /**
     * Gets the country name from the Address
     *
     * @return a String representing the country
     */
    public String getCountry() {
        return addressLines.get(addressLines.size() - 1);
    }

    /**
     * Formats a String with each part of the address on a new line. For the TextUI
     *
     * @return a String representing the Address
     */
    @Override
    public String toString() {
        StringBuffer returnable = new StringBuffer("");
        try {
            for (int i = 0; i < addressLines.size() - 1; i++) {
                returnable.append(addressLines.get(i)).append("\n");
            }
            returnable.append(addressLines.get(addressLines.size() - 1));

        } catch (Exception e) {
        }
        return returnable.toString();

    }

    /**
     * Formats the address for the GUI
     * 
     * @return the addresss
     */
    public String toGUIPrint() {
        StringBuffer returnable = new StringBuffer("");
        try {
            for (int i = 0; i < addressLines.size() - 1; i++) {
                returnable.append(addressLines.get(i)).append("<br/>");
            }
            returnable.append(addressLines.get(addressLines.size() - 1));
        } catch (Exception e) {
        }
        return returnable.toString();
    }
}

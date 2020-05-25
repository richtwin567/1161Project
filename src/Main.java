import app.*;
import ui.*;

/**
 * DRIVER FOR THE SYSTEM OF NATIONAL ID (SNID))
 * @author Anakai Richards 
 * @author Matthew Palmer 
 * @author Michael Young
 * @version 1.0
 */
public class Main {

    /**
     * Runs the TextUI then the GUI
     * @param args command line arguments
     */
    public static void main(String[] args){
        try{
            SNIDApp app = new SNIDApp("data.db",',');
            TextUI ui = new TextUI();
            ui.go(app);
            app=null;
            System.out.println("The GUI will run now, but first some quick instructions.");
            System.out.println("When searching by biometric the the tag precedes the data and there is no space between them eg. D1000.\nThe tag must be a capital F(Fingerprint) or a capital D(DNA)");
            System.out.println("Also, a reminder that the format of the ID numbers is a number padded up to 8 digits with zeroes to the left");
            SNIDGUI gui = new SNIDGUI(new SNIDApp("data.db", ','));
            gui.setVisible(true);
        }catch(Exception b){
            System.out.println("Fatal Error. Program could not start - "+b.getLocalizedMessage());
        }
        
    }
    
}
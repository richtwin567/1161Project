
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

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

    public static void main(String[] args){
        try{
            SNIDApp app = new SNIDApp("data.db",',');
            TextUI ui = new TextUI();
            ui.go(app);
            app = null;
            SNIDGUI gui = new SNIDGUI(new SNIDApp("data.db",','));
            gui.setVisible(true);
        }catch(InvalidParameterException | FileNotFoundException  e){

        }catch(IOException i){

        }catch(Exception b){

        }
        
    }
    
}
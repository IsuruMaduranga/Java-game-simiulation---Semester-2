package Grid;

import Inhabitant.Inhabitant;
import java.util.logging.Level;
import java.util.logging.Logger;
import Warrior.Warrior;

public class GridPoint{
    private volatile  boolean empty = true;
    private Inhabitant inhabitant = null;

    public synchronized void move(Warrior warrior) {
        this.empty = false;
        if(!(inhabitant == null)){
            inhabitant.update(warrior);
        }
        try {
            wait(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(GridPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addInhabitant(Inhabitant inhabitant) {
        this.inhabitant = inhabitant;
    }

    public synchronized boolean isEmpty() {
        return empty;
    }  

    public synchronized void setEmpty(boolean empty) {
        this.empty = empty;
    }
    
}

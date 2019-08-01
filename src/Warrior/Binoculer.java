
package Warrior;
import java.util.ArrayList;

public class Binoculer {
    ArrayList<int[]> lotusLocations;
    
    public Binoculer(ArrayList<int[]> l){
        this.lotusLocations = l;
    }
    
    public ArrayList searchLotus(ArrayList<int[]> positions){
        
        ArrayList<int[]> output = new ArrayList<>();
        
        positions.stream().filter((p) -> (lotusLocations.contains(p))).forEachOrdered((p) -> {
            output.add(p);
        });
        
        return output;
    }
}


package Grid;

import Inhabitant.Inhabitant;


public class Grid {
    private final GridPoint[][] grid;

    public Grid(){
        this.grid =  new GridPoint[11][11];
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                grid[i][j] = new GridPoint();
            }
        }
    }
   
    public GridPoint getGridPoint(int[] loc) {
        return grid[loc[0]][loc[1]];
    }
    
    public void addInhabitant(Inhabitant inhabitant) {
        int x= inhabitant.getPosition()[0];
        int y= inhabitant.getPosition()[1];
        this.grid[x][y].addInhabitant(inhabitant);
    }
    
}

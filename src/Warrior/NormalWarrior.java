package Warrior;

import Grid.Grid;
import java.util.ArrayList;
import java.util.Arrays;

public class NormalWarrior extends Warrior{
    private static int numOfNormalWarriors;
    
    public NormalWarrior(int[] position,String name,Grid grid){
        
        super(position,name,grid);
        numOfNormalWarriors++;
    
    }
    
    @Override
    public void run(){
        while(!super.isGameOver() && this.hasSwimPin() && this.isAlive()){
            this.swim();
        }
    }
    
    @Override
    public void swim(){
        
            ArrayList<int[]> locations = super.availableLocations(grid);
            
            while(!super.isGameOver() && this.hasSwimPin() && this.isAlive()){
                int[] nextLoc = super.nextLoc(locations);
                if(grid.getGridPoint(nextLoc).isEmpty()){
                    System.out.println(super.getName() + " moved to " + Arrays.toString(nextLoc));
                    grid.getGridPoint(nextLoc).move(this);
                    grid.getGridPoint(super.getPosition()).setEmpty(true);
                    super.setPosition(nextLoc);
                    break;
                }
            }   
    }
    
    @Override
    public void eat(){}
    
    @Override
    public void sleep(){}

    public static int getNumOfNormalWarriors() {
        return numOfNormalWarriors;
    }
    
}
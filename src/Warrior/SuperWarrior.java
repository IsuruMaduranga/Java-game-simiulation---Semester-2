package Warrior;

import Grid.Grid;
import java.util.ArrayList;
import java.util.Arrays;

public class SuperWarrior extends Warrior{
    private final Binoculer binoculer;
    private static int numOfSuperWarriors;
    
    public SuperWarrior(int[] position,String name,Grid grid,Binoculer binoculer){
        super(position,name,grid);
        this.binoculer = binoculer;
        numOfSuperWarriors++;
   
    }
    
     @Override
    public void run(){
        while(!super.isGameOver() && this.isAlive() && this.hasSwimPin()){
            this.swim();
        }
    }
    
    @Override
    public void swim(){
        
            ArrayList<int[]> locations = super.availableLocations(grid);
            ArrayList<int[]> locationsOfLotus = binoculer.searchLotus(locations);
            int[] nextLoc;
            if(this.isImmortal() || locationsOfLotus.isEmpty()){
                while(!super.isGameOver() && this.hasSwimPin() && this.isAlive()){
                    nextLoc = super.nextLoc(locations);
                    if(grid.getGridPoint(nextLoc).isEmpty()){
                        System.out.println(super.getName() + " moved to " + Arrays.toString(nextLoc));
                        grid.getGridPoint(nextLoc).move(this);
                        grid.getGridPoint(super.getPosition()).setEmpty(true);
                        super.setPosition(nextLoc);
                        break;
                    }
                }
            }
            else{
                while(!super.isGameOver() && this.hasSwimPin() && this.isAlive()){
                    nextLoc = super.nextLoc(locationsOfLotus);
                    if(grid.getGridPoint(nextLoc).isEmpty()){
                        grid.getGridPoint(nextLoc).move(this);
                        grid.getGridPoint(super.getPosition()).setEmpty(true);
                        super.setPosition(nextLoc);
                        System.out.println(super.getName() + " moved to " + Arrays.toString(nextLoc));
                        break;
                    }
                }
                  
            }   
    }
    
    @Override
    public void eat(){}
    
    @Override
    public void sleep(){}

    public static int getNumOfSuperWarriors() {
        return numOfSuperWarriors;
    }
        
}



package Warrior;
import Grid.Grid;
import java.util.ArrayList;
import java.util.Random;


abstract public class Warrior implements Runnable,Observer{
    public Grid grid;
    private final String name;
    private boolean hasSwimPin;
    private boolean immortal;
    private boolean alive;
    private int[] position = new int[2];
    private static int numOfActiveWarriors=0;
    private volatile boolean gameOver;
    
    public Warrior(int[] position,String name,Grid grid){
        this.grid = grid;
        this.name= name;
        this.hasSwimPin = true;
        this.immortal = false;
        this.alive = true;
        this.position = position;
        this.gameOver = false;
        numOfActiveWarriors++;
    
    }
  
    
    abstract public void swim();
    
    abstract public void eat();
    
    abstract public void sleep();
    
    final public void losefin(){
        numOfActiveWarriors--;
        this.hasSwimPin = false;
    }
    
    @Override
    public void update(){
        this.gameOver = true;
    }
    
    final public void die(){
        if(!this.immortal){
            numOfActiveWarriors--;
            this.alive=false;
            this.hasSwimPin=false;
            grid.getGridPoint(this.getPosition()).setEmpty(true);
        }
    }
    
    final public void won(){
        System.out.println(this.getName() + " won the Game!");
    }
    

    final public void setImmortal() {
        this.immortal = true;
    }


    final public int[] getPosition() {
        return position;
    }

    final public void setPosition(int[] position) {
        this.position = position;
    }

    final public String getName() {
        return name;
    }

    final public boolean isAlive() {
        return alive;
    }

    public boolean hasSwimPin() {
        return hasSwimPin;
    }


    public boolean isImmortal() {
        return immortal;
    }

    public static int getNumOfActiveWarriors() {
        return numOfActiveWarriors;
    }

    public boolean isGameOver() {
        return gameOver;
    }
    
    
    /*searching free locations to move*/
    
    public ArrayList<int[]> availableLocations(Grid grid){
        
        ArrayList<int[]> output = new ArrayList<>();
        // getting current positions of the warrior
        int x;
        int y;
        x=this.getPosition()[0];
        y=this.getPosition()[1];
        
        int[] array1 = {-1,+1};
        
        //checking for valid locations in the grid
        for(int z:array1){
            int y1 = y + z;
            if(0<=y1 && y1<=10){
                int[] out = {x,y1};
                output.add(out);
            }
        }
        
        for(int z2:array1){
            int x1 = x + z2;
            if(0<=x1 && x1<=10){
                int[] out= {x1,y};
                output.add(out);
            }
        }
        return output;
        
    }
    
    /*Deciding next location to move*/
    
    public int[] nextLoc(ArrayList<int[]> positions){
        
        Random generator = new Random();
        return positions.get(generator.nextInt(positions.size()));
        
        
    }
    
}








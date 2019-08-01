package Inhabitant;

import Warrior.Warrior;
import Inhabitant.Inhabitant;


public class Lotus implements Inhabitant{
    private int numofpetals;
    private final int[] position;
    private final String name;
    private static int numOfLotus;
    
    public Lotus(int[] position,String name){
        this.numofpetals = 100;
        this.position = position;
        this.name= name;
        numOfLotus++;
    }
    
    @Override
    public void update(Warrior warrior){
        if(this.numofpetals>0 && !(warrior.isImmortal())){
            warrior.setImmortal();
            System.out.println(warrior.getName() + " ate lotus petal from " + this.getName()+ " and he is now immortal!");
            this.losepetal();
        }
    }
    
    public void losepetal(){
        numofpetals-=1;
    }
    
    @Override
    public int[] getPosition(){
        return this.position;
    }

    public String getName() {
        return name;
    } 

    public static int getNumOfLotus() {
        return numOfLotus;
    }
    
    
}
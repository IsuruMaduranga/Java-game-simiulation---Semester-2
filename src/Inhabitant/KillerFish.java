package Inhabitant;

import Warrior.Warrior;


public class KillerFish extends Fish{
    private static int numOfKFish=0;

    public KillerFish(int[] position,String name){
        super(position,name);
        numOfKFish++;
    }
    
    
    @Override
    public void update(Warrior warrior){
        if(!(warrior.isImmortal()) && warrior.isAlive()){
            this.kill(warrior);
        }
        else if(warrior.isImmortal() && warrior.isAlive()){
            System.out.println(warrior.getName()+ " met " + super.getName() + " but he is immortal!");
        }
    }
    
    private void kill(Warrior warrior){
        warrior.die();
        System.out.println(warrior.getName() + " is killed by " + this.getName() +"!");
    }

    public static int getNumOfKFish() {
        return numOfKFish;
    }
    
    
}

package Inhabitant;


import Warrior.Warrior;

public class InnocentFish extends Fish{
    private static int numOfIFish;
    
    public InnocentFish(int[] position,String name){
        super(position,name);
        numOfIFish++;
        
    }
    
    @Override
    public void update(Warrior warrior){
        System.out.println(warrior.getName() + " met " + this.getName() + "!");
        
    }

    public static int getNumOfIFish() {
        return numOfIFish;
    }
    
}



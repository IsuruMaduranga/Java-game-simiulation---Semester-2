package Inhabitant;


import Warrior.Warrior;

public class RubberFish extends Fish{
    private static int numOfRFish=0;
    
    public RubberFish(int[] position,String name){
        super(position,name);
        numOfRFish++;
    }
    
 
    @Override
    public void update(Warrior warrior){
        if(warrior.hasSwimPin()){
            this.pullfins(warrior);
        }
    }
    

    private void pullfins(Warrior w){
        w.losefin();
        System.out.println(this.getName() + " pulled the swimming fins of " + w.getName() + " and warrior cant swim now!");
    }

    public static int getNumOfRFish() {
        return numOfRFish;
    }
    
    
}

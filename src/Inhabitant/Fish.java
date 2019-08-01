package Inhabitant;

import Warrior.Warrior;


abstract class Fish implements Inhabitant{
    private int[] position = new int[2];
    private final String name;
    
    public Fish(int[] position,String name){
        this.position=position;
        this.name=name;
    }
    
    @Override
    abstract public void update(Warrior warrior);
    
    @Override
    public int[] getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    
}





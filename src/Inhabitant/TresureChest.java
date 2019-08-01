package Inhabitant;

import Warrior.Observer;
import Warrior.Warrior;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TresureChest implements Inhabitant,Observable{
    private int[] position = new int[2];
    private final ArrayList<Observer> observers;
    private String winnerDetail;
    
    public TresureChest(int[] position){
        this.position = position;
        observers = new ArrayList<>();
        winnerDetail=null;
    }
    
    
    @Override
    public void update(Warrior warrior){
        winnerDetail = warrior.getName() + " won the game at " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()); 
        System.out.println(warrior. getName() + " won the game! ");
        notifyObservers();
        
    }
    
    @Override
    public void subscribe(Observer observer){
        observers.add(observer);
    }
    @Override
    public void notifyObservers(){
        observers.forEach((observer) -> {
            observer.update();
        });
    }
    
    @Override
    public int[] getPosition() {
        return position;
    }

    public String getWinnerDetail() {
        return winnerDetail;
    }
    
    
}

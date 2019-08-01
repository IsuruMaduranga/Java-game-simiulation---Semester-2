package mygame;

import Inhabitant.TresureChest;
import Warrior.Observer;
import Warrior.Warrior;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class GameController extends Thread implements Observer{
    private final TresureChest tresure;
    private volatile boolean GameOver;
    

    public GameController(TresureChest tresure) {
        this.tresure = tresure;
        this.GameOver = false;
    }
    
    
    @Override
    public void run(){
        String output = null;
        boolean done = true;
        
        do{ 
            
            if(!GameOver){
                if(Warrior.getNumOfActiveWarriors()==0){
                    done = false;
                    output = "No winners!";
                    System.out.println("No winners!");
                    System.out.println("Game Over!");
                }   
            }else{
                System.out.println("Game Over!");
                output = tresure.getWinnerDetail();
                done = false;
            }
        }while(done);
                
        try {
            File file = new File("Game_Detail.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file,true);
            writer.write(output+System.lineSeparator());
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(){
        GameOver= true;
    }
        
}
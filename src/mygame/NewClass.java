import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyGame {
    public static void main(String[] args){
        Game game = new Game();
        System.out.println("Game Starts!"); 
        game.startGame();
    }
}


class Game{
    private final Grid grid;
    private final ArrayList<Warrior> warriors;
    private final Thread[] warriorThreads;
    private final TresureChest tresure;
    private final GameController gameController;
    
    public Game() {
        
    /*Creating the grid*/
        
        grid = new Grid();

    /*Generating positions for warriors and other game objects without overlaping them using GameTools*/
       
        ArrayList positions;
        positions = this.generatePositions();
        Iterator itr = positions.iterator();
    
    /*Creating the tresurechest and adding to the grid*/
        
        this.tresure = new TresureChest((int[]) itr.next());
        System.out.println("TresureChest ---> " + Arrays.toString(tresure.getPosition()));
        grid.addInhabitant(tresure);
    
    /*Creating warriors*/
        
        int counter=0;
        
        warriors = new ArrayList<>(4);
        
        //creating 2 normal warriors
        
        do{
            NormalWarrior nw=new NormalWarrior((int[]) itr.next(),"NormalWarrior"+ Integer.toString(NormalWarrior.getNumOfNormalWarriors()+1),grid);
            warriors.add(nw);
            System.out.println(nw.getName()+ " ---> "+ Arrays.toString(nw.getPosition()));
            counter++;
        }while(counter!=2);
        
        //Creating 2 super warriors
        
        ArrayList<int[]> lotusLocations = new ArrayList<>();
        for(int i = 10;i<=15;i++){
             lotusLocations.add((int[])positions.get(i));
        }
        
        do{
            
            SuperWarrior sw=new SuperWarrior((int[]) itr.next(),"SuperWarrior"+ Integer.toString(SuperWarrior.getNumOfSuperWarriors()+1),grid,new Binoculer(lotusLocations));
            warriors.add(sw);
            System.out.println(sw.getName()+ " ---> "+ Arrays.toString(sw.getPosition()));
            counter++;
        }while(counter!=4);
         
        
    /*Creating fishes and adding to the grid*/

        //Creating 2 killer fishes and adding to the grid
        
        do{
            KillerFish kf=new KillerFish((int[]) itr.next(),"KillerFish"+Integer.toString(KillerFish.getNumOfKFish()+1));
            System.out.println(kf.getName() + "--->" + Arrays.toString(kf.getPosition()));
            grid.addInhabitant(kf);
            counter++;
        }while(counter!=6);
        
        //Creating 2 rubber fishes and adding to the grid
        
        do{
            RubberFish rf=new RubberFish((int[]) itr.next(),"RubberFish"+Integer.toString(RubberFish.getNumOfRFish()+1));
            System.out.println(rf.getName() + "--->" + Arrays.toString(rf.getPosition()));
            grid.addInhabitant(rf);
            counter++;
        }while(counter!=8);
        
        //Creating 2 Innocent fishes and adding to the grid
        do{
            
            InnocentFish If=new InnocentFish((int[]) itr.next(),"InnocentFish"+Integer.toString(InnocentFish.getNumOfIFish()+1));
            System.out.println(If.getName() + "--->" + Arrays.toString(If.getPosition()));
            grid.addInhabitant(If);
            counter++;
        }while(counter!=10);    
        
    /*Creating 5 Lotus flowers and adding to the grid*/
        
        do{
            Lotus l=new Lotus((int[]) itr.next(),"Lotus" +Integer.toString(Lotus.getNumOfLotus()+1));
            System.out.println(l.getName() + "--->" + Arrays.toString(l.getPosition()));
            grid.addInhabitant(l);
            counter++;
        }while(counter!=15);
        
    /*creating threads*/
        warriorThreads = new Thread[4];
        
        for(int i = 0;i<4;i++){
            warriorThreads[i] = new Thread(warriors.get(i));
            tresure.subscribe((Observer)warriors.get(i));
            
        }
        
        gameController = new GameController(tresure);
        tresure.subscribe((Observer)gameController);
    
    }
    
    
    public void startGame(){
        
        for(Thread t:warriorThreads){
            t.start();
        }
        gameController.setPriority(1);
        gameController.start();
    }
     
    /////*Following methods generate random positions for warriors and GAme objects*/////
    
    private int[] randomPositionsForWarriors(){
        int[] array = {0,1,2,3,4,5,6,7,8,9,10};
    	int[] array1 = {0,10};
    	int[] out=new int[2];
    	Random generator = new Random();
    	out[0] = generator.nextInt(array.length);
    	if((out[0]==0) || (out[0]==10)) {
    		out[1] = array[generator.nextInt(array.length)];
    	}
    	else{
    		out[1] = array1[generator.nextInt(array1.length)];
    	}
    	return out;
    }
    
    /*This function for generate random positions for other game objects*/
    
    private int[] randomPositionsForOthers(){
        int[] array = {0,1,2,3,4,5,6,7,8,9,10};
    	Random generator = new Random();
    	int[] out=new int[2];

        out[0] = array[generator.nextInt(array.length)];
        out[1] = array[generator.nextInt(array.length)];
    	return out;
    }
    
    private ArrayList<int[]> generatePositions(){
        
        ArrayList<int[]> allPositions = new ArrayList<>();
        int[] positionOfTresure={5,5};
        allPositions.add(positionOfTresure);
        int[] position;
        
        //generating positions for warriors
        
        do{
            position = randomPositionsForWarriors();
            done : {
                for(int[] arr: allPositions)
                    if(Arrays.equals(arr, position)){
                        break done;
                    }
            
                
                allPositions.add(position);
            }
        }while(allPositions.size()!=5);
        
        
        //generating positions for other objects
        
        do{
            position = randomPositionsForOthers();
            done : {
                for(int[] arr: allPositions){
                    if(Arrays.equals(arr, position)){
                        break done;
                    }
                }
                
                allPositions.add(position);
            }
        }while(allPositions.size()!=16);
        
        
        return allPositions;
    }
    
}

    

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
            Logger.getLogger(mygame.Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(){
        GameOver= true;
    }
        
}

abstract class Warrior implements Runnable,Observer{
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


class SuperWarrior extends Warrior{
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

class NormalWarrior extends Warrior{
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

class Binoculer {
    ArrayList<int[]> lotusLocations;
    
    public Binoculer(ArrayList<int[]> l){
        this.lotusLocations = l;
    }
    
    public ArrayList searchLotus(ArrayList<int[]> positions){
        
        ArrayList<int[]> output = new ArrayList<>();
        
        positions.stream().filter((p) -> (lotusLocations.contains(p))).forEachOrdered((p) -> {
            output.add(p);
        });
        
        return output;
    }
}


interface Observer {
    public void update();
}




interface Inhabitant{
    public int[] getPosition();
    public void update(Warrior warrior);
}


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

class InnocentFish extends Fish{
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


class KillerFish extends Fish{
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

class RubberFish extends Fish{
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

class Lotus implements Inhabitant{
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

interface Observable {
    public void subscribe(Observer observer);
    public void notifyObservers();
}

class TresureChest implements Inhabitant,Observable{
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

class GridPoint{
    private volatile  boolean empty = true;
    private Inhabitant inhabitant = null;

    public synchronized void move(Warrior warrior) {
        this.empty = false;
        if(!(inhabitant == null)){
            inhabitant.update(warrior);
        }
        try {
            wait(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(GridPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addInhabitant(Inhabitant inhabitant) {
        this.inhabitant = inhabitant;
    }

    public synchronized boolean isEmpty() {
        return empty;
    }  

    public synchronized void setEmpty(boolean empty) {
        this.empty = empty;
    }
    
}

class Grid {
    private final GridPoint[][] grid;

    public Grid(){
        this.grid =  new GridPoint[11][11];
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                grid[i][j] = new GridPoint();
            }
        }
    }
   
    public GridPoint getGridPoint(int[] loc) {
        return grid[loc[0]][loc[1]];
    }
    
    public void addInhabitant(Inhabitant inhabitant) {
        int x= inhabitant.getPosition()[0];
        int y= inhabitant.getPosition()[1];
        this.grid[x][y].addInhabitant(inhabitant);
    }
    
}
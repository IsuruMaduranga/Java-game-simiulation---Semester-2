
package mygame;
import Inhabitant.TresureChest;
import Inhabitant.Lotus;
import Warrior.Observer;
import Warrior.Binoculer;
import Warrior.Warrior;
import Warrior.SuperWarrior;
import Warrior.NormalWarrior;
import Grid.Grid;
import Inhabitant.InnocentFish;
import Inhabitant.KillerFish;
import Inhabitant.RubberFish;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Game{
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

    

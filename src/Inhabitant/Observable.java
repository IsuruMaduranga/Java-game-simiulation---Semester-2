
package Inhabitant;

import Warrior.Observer;

interface Observable {
    public void subscribe(Observer observer);
    public void notifyObservers();
}

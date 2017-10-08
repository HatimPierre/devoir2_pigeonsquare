package pigeonsquare.utils;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> obs_l;
    private boolean changed;

    protected Observable(){
        obs_l = new ArrayList<>();
    }

    protected void setChanged(){
        changed = true;
    }

    protected boolean hasChanged(){
        return changed;
    }

    public void addObs(Observer obs){
        obs_l.add(obs);
    }

    private void notifyObserver(Observer obs, Message msg){
        obs.receive_msg(msg);
    }

    protected void notifyObservers(Message msg){
        for (Observer obs : obs_l){
            notifyObserver(obs, msg);
        }
    }
}

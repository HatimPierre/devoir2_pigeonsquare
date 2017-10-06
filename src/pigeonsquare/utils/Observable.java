package pigeonsquare.utils;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> obs_l;
    boolean changed;

    public Observable(){
        obs_l = new ArrayList<>();
    }

    protected void setChanged(){
        changed = true;
    }

    protected boolean hasChanged(){
        return changed;
    }

    protected void addObs(Observer obs){
        obs_l.add(obs);
    }

    protected void notify(Observer obs, Message msg){
        obs.receive_msg(msg);
    }

    protected void notifyAll(Message msg){
        for (Observer obs : obs_l){
            notify(obs, msg);
        }
    }
}

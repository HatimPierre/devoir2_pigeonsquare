package pigeonsquare.model;

import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable implements Runnable, Observer{
    private List<Food> foods;
    private List<Pigeon> pigeons;
    private List<Rock> rocks;
    private Observer mainView;

    public Model(Observer obs){
        this.addObs(obs);
        mainView = obs;
        foods = new ArrayList<>();
        pigeons = new ArrayList<>();
        rocks = new ArrayList<>();
    }

    public void spawnFood(int x, int y){
        Food new_food = new Food(mainView, x, y);
        foods.add(new_food);
        Message msg = new Message();
        msg.commands.add("FOOD");
        msg.commands.add("SPAWN");
        msg.x = x;
        msg.y = y;
        msg.id = new_food.getId();
        this.notifyObservers(msg);
    }

    public boolean canEat(float x, float y){
        //FIXME
        return false;
    }

    public synchronized void eat(float x, float y){
        //FIXME
    }
    @Override
    public void run() {
        //FIXME poll on msg queue
    }

    @Override
    public void receive_msg(Message msg) {

    }
}

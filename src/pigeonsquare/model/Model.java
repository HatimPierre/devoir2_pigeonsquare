package pigeonsquare.model;

import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;
import pigeonsquare.view.View;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable implements Runnable, Observer{
    private List<Food> food;
    private Observer mainView;

    public Model(Observer obs){
        this.addObs(obs);
        mainView = obs;
        food = new ArrayList<>();
    }

    public void spawnFood(int x, int y){
        food.add(new Food(mainView));
        System.out.println("Registered new food at [" + x + ", " + y + "]");
    }

    private boolean canEat(){
        //FIXME
        return false;
    }

    private void eat(int x, int y){
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

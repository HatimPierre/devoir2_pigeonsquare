package pigeonsquare.model;

import pigeonsquare.utils.Maths;
import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable implements Runnable, Observer{
    volatile List<Food> foods;
    private List<Pigeon> pigeons;
    List<Rock> rocks;

    public Model(Observer obs){
        this.addObs(obs);
        foods = new ArrayList<>();
        pigeons = new ArrayList<>();
        rocks = new ArrayList<>();
    }

    private synchronized void generate_rock(){

    }

    public synchronized void spawnRock(){
        int x = Maths.sec_rand.nextInt(651);
        int y = Maths.sec_rand.nextInt(656);
        Rock new_rock = new Rock(x, y);
        rocks.add(new_rock);
        Message msg = new Message();
        msg.commands.add("ROCK");
        msg.commands.add("SPAWN");
        msg.x = x;
        msg.y = y;
        msg.id = new_rock.getId();
        this.notifyObservers(msg);
    }

    public synchronized void spawnFood(int x, int y){
        Food new_food = new Food(x, y);
        foods.add(new_food);
        Message msg = new Message();
        msg.commands.add("FOOD");
        msg.commands.add("SPAWN");
        msg.x = x;
        msg.y = y;
        msg.id = new_food.getId();
        this.notifyObservers(msg);
    }

    private synchronized void killRock(Rock r){
        rocks.remove(r);
        Message msg = new Message();
        msg.commands.add("ROCK");
        msg.commands.add("KILL");
        msg.id = r.getId();
        this.notifyObservers(msg);
    }

    private synchronized void spoilFood(Food f){
        f.spoil();
        Message msg = new Message();
        msg.commands.add("FOOD");
        msg.commands.add("SPOIL");
        msg.id = f.getId();
        this.notifyObservers(msg);
    }

    private synchronized void handle_objs_life(){
        for (Rock r: rocks) {
            r.lifespan--;
            if (r.lifespan < 0)
                killRock(r);
        }
        for (Food f : foods){
            f.lifespan--;
            if (f.lifespan < 0)
                spoilFood(f);
        }
    }

    synchronized void eat(Food f){
        if (foods.contains(f)){
            Message msg = new Message();
            msg.commands.add("FOOD");
            msg.commands.add("ATE");
            msg.id = f.getId();
            foods.remove(f);
            this.notifyObservers(msg);
        }
    }
    @Override
    public void run() {

        handle_objs_life();
    }

    @Override
    public void receive_msg(Message msg) {

    }
}

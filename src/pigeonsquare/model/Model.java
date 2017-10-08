package pigeonsquare.model;

import pigeonsquare.utils.Maths;
import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static pigeonsquare.utils.GameConst.ROCK_GEN_DIVISOR;

public class Model extends Observable implements Runnable, Observer{
    volatile List<Food> foods;
    private volatile List<Rock> rocks;
    private final Stack<Rock> toKill;

    public Model(Observer obs){
        this.addObs(obs);
        foods = new ArrayList<>();
        rocks = new ArrayList<>();
        toKill = new Stack<>();
    }

    private synchronized void generate_rock(){
        float tmp = Maths.sec_rand.nextFloat() / ROCK_GEN_DIVISOR;
        if (Maths.sec_rand.nextFloat() > (1 - tmp))
            spawnRock();
    }

    private synchronized void spawnRock(){
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
            if(rocks.contains(r)){
                Message msg = new Message();
                msg.commands.add("ROCK");
                msg.commands.add("KILL");
                msg.id = r.getId();
                rocks.remove(r);
                this.notifyObservers(msg);
            }
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
                toKill.push(r);
        }
        for (Food f : foods){
            f.lifespan--;
            if (f.lifespan < 0)
                spoilFood(f);
        }
        while (!toKill.empty())
            killRock(toKill.pop());
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
        generate_rock();
        handle_objs_life();
    }

    @Override
    public void receive_msg(Message msg) {

    }
}

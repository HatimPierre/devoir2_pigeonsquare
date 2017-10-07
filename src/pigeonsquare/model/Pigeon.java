package pigeonsquare.model;

import pigeonsquare.utils.Maths;
import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Math.*;
import static pigeonsquare.utils.GameConst.*;

public class Pigeon extends Observable implements Runnable, Observer {
    private final BlockingQueue<Message> msg_q;
    private final Model model;
    private Message curr_msg;
    private float x, y;
    private float x_dest, y_dest;
    private float angle;
    private UUID id;
    private boolean initialized;
    private Food target_food;

    public Pigeon(Model model){
        initialized = false;
        x = Maths.sec_rand.nextInt(597);
        y = Maths.sec_rand.nextInt(613);
        id = UUID.randomUUID();
        this.model = model;
        msg_q = new LinkedBlockingQueue<>();
        curr_msg = null;
        target_food = null;
        resetState();
    }

    private void chooseTarget(){
        float best_dist = Float.MAX_VALUE;
        float tmp_dist;
        synchronized (model){
            if (!model.foods.contains(target_food))
                resetState();
            for (Food f: model.foods) {
                tmp_dist = Maths.distance(x, y, f.getX(), f.getY());
                if (best_dist > tmp_dist){
                    best_dist = tmp_dist;
                    x_dest = f.getX();
                    y_dest = f.getY();
                    target_food = f;
                    }
            }
        }
    }

    private boolean evaluateMove(int x2, int y2){
        float d1 = Maths.distance(x, y, x_dest, y_dest);
        float d2 = Maths.distance(x, y, x2, y2);
        System.out.println(d1 + " / " + d2);
        if(d1 < NEAR_DIST || d1 > d2) {
            moveTo(x2, y2);
            return true;
        }
        return false;
    }

    private void moveTo(int x, int y){
        x_dest = x;
        y_dest = y;
    }

    private void resetState(){
        x_dest = x;
        y_dest = y;
        angle = 0.0f;
        target_food = null;
    }

    private void step(){
        angle = Maths.angle(this.x, this.y, x_dest, y_dest);
        x += STEP * cos(angle);
        y += STEP * sin(angle);
        advertiseSelf("MOVE");
    }

    private void advertiseSelf(String info){
        Message msg = new Message();
        msg.commands.add("PIGEON");
        msg.commands.add(info);
        msg.x = (int) x;
        msg.y = (int) y;
        msg.id = this.getId();
        this.notifyObservers(msg);
    }

    private boolean needs_move(){
        return Maths.distance(x, y, x_dest, y_dest) > NEAR_DIST;
    }

    public UUID getId(){
        return id;
    }

    private void eat(){
        model.eat(target_food);
        resetState();
    }

    @Override
    public void run() {
        if(!initialized){
            advertiseSelf("SPAWN");
            initialized = true;
        }
        chooseTarget();
        if(needs_move())
            step();
        else if (target_food != null)
            eat();
    }

    @Override
    public void receive_msg(Message msg) {

    }
}

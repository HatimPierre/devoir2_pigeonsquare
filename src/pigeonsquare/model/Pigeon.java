package pigeonsquare.model;

import pigeonsquare.utils.*;

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
    private long scare_duration;
    private boolean scared;

    public Pigeon(Model model){
        initialized = false;
        x = Maths.sec_rand.nextInt(597);
        y = Maths.sec_rand.nextInt(613);
        id = UUID.randomUUID();
        this.model = model;
        msg_q = new LinkedBlockingQueue<>();
        curr_msg = null;
        target_food = null;
        scared = false;
        resetState();
    }

    private void processMsg(){
        synchronized (model){
            if (curr_msg.commands.size() > 0 && curr_msg.commands.get(0).equals("ROCK")) {
                if (curr_msg.commands.size() > 1 && curr_msg.commands.get(1).equals("SPAWN")) {
                    if (Maths.distance(x, y, curr_msg.x, curr_msg.y) < PIGEON_SCARE_DISTANCE){
                        scare_duration = PIGEON_SCARE_DURATION;
                        scared = true;
                        int scare_x = (int) max(min(2*x - curr_msg.x, 596), 0);
                        int scare_y = (int) max(min(2*y - curr_msg.y, 612),0);
                        moveTo(scare_x, scare_y);
                    }
                }
            }
        }
    }

    private void evaluateScariness(){
        if (--scare_duration < 0){
            resetState();
        }
        else if(needs_move())
                step();
    }

    private void chooseTarget(){
        float best_dist = Float.MAX_VALUE;
        float tmp_dist;
        synchronized (model){
            if (!model.foods.contains(target_food) || !target_food.isFresh())
                resetState();
            for (Food f: model.foods) {
                if (f.isFresh()){
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
    }

    private void moveTo(float x, float y){
        x_dest = x;
        y_dest = y;
    }

    private void resetState(){
        x_dest = x;
        y_dest = y;
        angle = 0.0f;
        target_food = null;
        scared = false;
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
        while((curr_msg = msg_q.poll()) != null)
            processMsg();
        if (!scared){
            chooseTarget();
            if(needs_move())
                step();
            else if (target_food != null)
                eat();
        } else
            evaluateScariness();
    }

    @Override
    public void receive_msg(Message msg) {
        msg_q.offer(msg);
    }
}

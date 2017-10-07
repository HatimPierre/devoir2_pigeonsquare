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
    private Message curr_msg;
    private float x, y;
    private float x_dest, y_dest;
    private float angle;
    private UUID id;
    private Model model;
    private boolean initialized;

    public Pigeon(Model model){
        initialized = false;
        x = Maths.sec_rand.nextInt(597);
        y = Maths.sec_rand.nextInt(613);
        id = UUID.randomUUID();
        this.model = model;
        msg_q = new LinkedBlockingQueue<>();
        curr_msg = null;
        stopMove();
    }

    private void evaluateMove(int x2, int y2){
        float d1 = Maths.distance(x, y, x_dest, y_dest);
        float d2 = Maths.distance(x, y, x2, y2);
        if(!needs_move() || d1 > d2)
            moveTo(x2, y2);
    }

    private void moveTo(int x, int y){
        x_dest = x;
        y_dest = y;
        angle = Maths.angle(this.x, this.y, x, y);
    }

    private void stopMove(){
        x_dest = x;
        y_dest = y;
        angle = 0.0f;
    }

    private void step(){
        // FIXME -> notifyObserver
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

    private void processMsg(){
        if (curr_msg.commands.size() > 0) {
            if (curr_msg.commands.get(0).equals("FOOD")) {
                if (curr_msg.commands.size() > 1 && curr_msg.commands.get(1).equals("SPAWN")) {
                    evaluateMove(curr_msg.x, curr_msg.y);
                }
            }
        }
    }
    @Override
    public void run() {
        if(!initialized){
            advertiseSelf("SPAWN");
            initialized = true;
        }
        while((curr_msg = msg_q.poll()) != null)
            processMsg();
        if(needs_move())
            step();
        if(model.canEat(x, y))
            stopMove();
            model.eat(x, y);
    }

    @Override
    public void receive_msg(Message msg) {
        msg_q.offer(msg);
       //FIXME react to new food spawning
    }
}

package pigeonsquare.model;

import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;

import java.util.UUID;

public class Pigeon extends Observable implements Runnable, Observer {
    private int x, y;
    private int x_dest, y_dest;
    private UUID id;
    private Model model;

    public Pigeon(Model model){
        id = UUID.randomUUID();
        this.model = model;
    }

    private void moveTo(int x, int y){
        x_dest = x;
        y_dest = y;
    }

    private void stopMove(){
        x_dest = x;
        y_dest = y;
    }

    private void step(){
        // FIXME -> notify
    }

    private boolean needs_move(){
        return x == x_dest && y == y_dest;
    }

    public UUID getId(){
        return id;
    }

    @Override
    public void run() {
        if(needs_move())
            step();
    }

    @Override
    public void receive_msg(Message msg) {
       //FIXME
    }
}

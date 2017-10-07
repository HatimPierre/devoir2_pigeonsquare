package pigeonsquare.model;

import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;
import pigeonsquare.view.View;

import java.util.UUID;

public class Food extends Observable{
    private int x, y;
    private int lifespan;
    public static float RADIUS_EFFECT = 3.0f;
    private UUID id;
    private boolean fresh;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public UUID getId() {
        return id;
    }

    public boolean isFresh(){
        return fresh;
    }
    public Food(Observer view, int x, int y){
        // FIXME random position
        super();
        this.addObs(view);
        id = UUID.randomUUID();
        fresh = true;
        this.x = x;
        this.y = y;
    }
}

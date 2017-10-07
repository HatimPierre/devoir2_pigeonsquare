package pigeonsquare.model;

import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;
import pigeonsquare.view.View;

import java.util.UUID;

import static pigeonsquare.utils.GameConst.FOOD_LIFESPAN;

public class Food{
    private int x, y;
    int lifespan;
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

    public void spoil(){
        fresh = false;
    }

    public Food(int x, int y){
        id = UUID.randomUUID();
        fresh = true;
        this.x = x;
        this.y = y;
        lifespan = FOOD_LIFESPAN;
    }
}

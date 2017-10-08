package pigeonsquare.model;

import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;
import pigeonsquare.view.View;

import java.util.UUID;

import static pigeonsquare.utils.GameConst.FOOD_LIFESPAN;

class Food{
    private int x, y;
    int lifespan;
    private UUID id;
    private boolean fresh;

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    UUID getId() {
        return id;
    }

    boolean isFresh(){
        return fresh;
    }

    void spoil(){
        fresh = false;
    }

    Food(int x, int y){
        id = UUID.randomUUID();
        fresh = true;
        this.x = x;
        this.y = y;
        lifespan = FOOD_LIFESPAN;
    }
}

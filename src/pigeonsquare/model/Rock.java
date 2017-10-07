package pigeonsquare.model;

import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;
import pigeonsquare.view.View;

import java.util.UUID;

import static pigeonsquare.utils.GameConst.FOOD_LIFESPAN;
import static pigeonsquare.utils.GameConst.ROCK_LIFESPAN;

public class Rock{
    private int x, y;
    int lifespan;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public Rock(int x, int y){
        id = UUID.randomUUID();
        this.x = x;
        this.y = y;
        lifespan = ROCK_LIFESPAN;
    }
}

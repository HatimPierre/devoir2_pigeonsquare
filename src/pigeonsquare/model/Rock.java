package pigeonsquare.model;

import pigeonsquare.utils.Observable;
import pigeonsquare.utils.Observer;
import pigeonsquare.view.View;

public class Rock extends Observable{
    private int x, y;
    private int lifespan;
    public static float RADIUS_EFFECT = 3.0f;

    public Rock(Observer view){
        // FIXME random position
        super();
        this.addObs(view);
    }

    public void kill(){
        // FIXME send msg to view to stop draw it
    }
}

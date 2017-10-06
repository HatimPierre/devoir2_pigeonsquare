package pigeonsquare.controller;

import pigeonsquare.model.Model;
import pigeonsquare.view.View;

public class Controller {
    private Model model;
    private View view;

    public Controller(){
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void clickAt(int x, int y){
        // FIXME
        model.spawnFood(x, y);
    }
}

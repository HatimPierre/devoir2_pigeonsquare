package pigeonsquare.controller;

import pigeonsquare.model.Model;

public class Controller {
    private Model model;

    public Controller(){
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void clickAt(int x, int y){
        model.spawnFood(x, y);
    }
}

package pigeonsquare.view.graphics;

import javax.swing.*;

public enum ImgHolder {
    INSTANCE;

    public final ImageIcon food_icon;
    public final ImageIcon pigeon_icon;
    public final ImageIcon rock_icon;

    private ImgHolder(){
        food_icon = new ImageIcon("assets/food_LD.png");
        rock_icon = new ImageIcon("assets/rock_LD.png");
        pigeon_icon = new ImageIcon("assets/pigeon_LD.png");
    }
}
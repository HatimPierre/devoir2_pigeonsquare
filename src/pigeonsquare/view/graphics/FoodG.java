package pigeonsquare.view.graphics;

public class FoodG extends GenericObjG {
    public FoodG(){
        super(ImgHolder.INSTANCE.food_icon);
    }
    public void spoil(){
        this.img = ImgHolder.INSTANCE.food_spoiled_icon;
        this.setIcon(img);
        this.repaint();
    }
}

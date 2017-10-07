package pigeonsquare.view.graphics;

import javax.swing.*;

public class GenericObjG extends JLabel {
    protected ImageIcon img;

    public GenericObjG(ImageIcon img){
        super();
        this.img = img;
        this.setSize(img.getIconWidth(), img.getIconHeight());
        this.setIcon(img);
        this.setOpaque(false);
    }
}

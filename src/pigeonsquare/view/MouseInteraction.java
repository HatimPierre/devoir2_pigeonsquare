package pigeonsquare.view;

import pigeonsquare.controller.Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInteraction implements MouseListener {
    private Controller ctrl;

    MouseInteraction(Controller ctrl){
        this.ctrl = ctrl;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            ctrl.clickAt(e.getX(), e.getY());

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

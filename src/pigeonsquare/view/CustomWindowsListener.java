package pigeonsquare.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CustomWindowsListener implements WindowListener {
    private View view;

    CustomWindowsListener(View view){
        this.view = view;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        view.end();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

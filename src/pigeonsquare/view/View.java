package pigeonsquare.view;

import pigeonsquare.controller.Controller;
import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observer;

import javax.swing.*;
import java.awt.*;

public class View implements Runnable, Observer {
    private JFrame frame;
    private JPanel panel;
    private Dimension dim;
    private Controller ctrl;

    public void createGUI(){
        frame = new JFrame("Feed the pigeons");
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setBackground(Color.GRAY);
        panel.addMouseListener(new MouseInteraction(ctrl));
        frame.setSize(dim);
        frame.setMaximumSize(dim);
        frame.setMinimumSize(dim);
        frame.setPreferredSize(dim);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public View(Controller ctrl){
        dim = new Dimension(700, 700);
        this.ctrl = ctrl;
    }

    @Override
    public void run() {
        createGUI();
    }

    @Override
    public void receive_msg(Message msg) {
    }
}

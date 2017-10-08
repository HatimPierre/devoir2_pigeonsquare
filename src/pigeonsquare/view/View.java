package pigeonsquare.view;

import pigeonsquare.controller.Controller;
import pigeonsquare.utils.Message;
import pigeonsquare.utils.Observer;
import pigeonsquare.view.graphics.FoodG;
import pigeonsquare.view.graphics.GenericObjG;
import pigeonsquare.view.graphics.PigeonG;
import pigeonsquare.view.graphics.RockG;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class View implements Runnable, Observer {
    private final BlockingQueue<Message> msg_q;
    private Message curr_msg;
    private JFrame frame;
    private JPanel panel;
    private Dimension dim;
    private Controller ctrl;
    private Map<UUID, FoodG> food_map;
    private Map<UUID, PigeonG> pigeon_map;
    private Map<UUID, RockG> rock_map;
    private boolean initialized;
    private boolean end;

    private void createGUI(){
        frame = new JFrame("Feed the pigeons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addWindowListener(new CustomWindowsListener(this));
        initialized = true;
        panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.addMouseListener(new MouseInteraction(ctrl));
        panel.setLayout(null);
        panel.setPreferredSize(dim);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public View(Controller ctrl){
        initialized = false;
        end = false;
        curr_msg = null;
        msg_q = new LinkedBlockingQueue<>();
        dim = new Dimension(700, 700);
        this.ctrl = ctrl;
        food_map = new HashMap<>();
        pigeon_map = new HashMap<>();
        rock_map = new HashMap<>();
    }

    private void spawnGenObj(GenericObjG obj){
        panel.add(obj);
        obj.setBounds(curr_msg.x, curr_msg.y, obj.getWidth(), obj.getHeight());
    }

    private void processMsg(){
        if (curr_msg.commands.size() > 0) {
            switch (curr_msg.commands.get(0)) {
                case "FOOD":
                    if (curr_msg.commands.size() > 1) {
                        switch (curr_msg.commands.get(1)) {
                            case "SPAWN": {
                                FoodG tmp = new FoodG();
                                spawnGenObj(tmp);
                                food_map.put(curr_msg.id, tmp);
                                break;
                            }
                            case "ATE": {
                                FoodG tmp = food_map.get(curr_msg.id);
                                food_map.remove(curr_msg.id);
                                panel.remove(tmp);
                                break;
                            }
                            case "SPOIL": {
                                FoodG tmp = food_map.get(curr_msg.id);
                                tmp.spoil();
                                break;
                            }
                        }
                    }
                    break;
                case "PIGEON":
                    if (curr_msg.commands.size() > 1) {
                        if (curr_msg.commands.get(1).equals("SPAWN")) {
                            PigeonG tmp = new PigeonG();
                            spawnGenObj(tmp);
                            pigeon_map.put(curr_msg.id, tmp);
                        } else if (curr_msg.commands.get(1).equals("MOVE")) {
                            PigeonG tmp = pigeon_map.get(curr_msg.id);
                            tmp.setBounds(curr_msg.x, curr_msg.y, tmp.getWidth(), tmp.getHeight());
                        }
                    }
                    break;
                case "ROCK":
                    if (curr_msg.commands.size() > 1) {
                        if (curr_msg.commands.get(1).equals("SPAWN")) {
                            RockG tmp = new RockG();
                            spawnGenObj(tmp);
                            rock_map.put(curr_msg.id, tmp);
                        } else if (curr_msg.commands.get(1).equals("KILL")) {
                            RockG tmp = rock_map.get(curr_msg.id);
                            rock_map.remove(curr_msg.id);
                            panel.remove(tmp);
                        }
                    }
                    break;
            }
        }
        panel.revalidate();
        panel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    void end(){
        end = true;
    }
    @Override
    public void run()
    {
        if (!initialized)
            createGUI();
        while (!end){
            while((curr_msg = msg_q.poll()) != null)
                processMsg();
        }
    }

    @Override
    public synchronized void receive_msg(Message msg) {
        msg_q.offer(msg);
        if(msg.commands.contains("ROCK"))
            System.out.println("Msg " + msg.commands + " for " + msg.id.toString());
    }
}

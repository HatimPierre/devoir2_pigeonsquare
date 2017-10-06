package pigeonsquare;

import pigeonsquare.controller.Controller;
import pigeonsquare.model.Model;
import pigeonsquare.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private Main(){
    }

    public static void main(String[] args){
        ExecutorService view_exec = Executors.newSingleThreadExecutor();
        ExecutorService models_exec = Executors.newScheduledThreadPool(Integer.MAX_VALUE);
        Controller ctrl = new Controller();
        View view = new View(ctrl);
        Model model = new Model(view);
        ctrl.setModel(model);
        ctrl.setView(view);



        view_exec.submit(view);
    }
}

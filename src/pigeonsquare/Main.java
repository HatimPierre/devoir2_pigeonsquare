package pigeonsquare;

import pigeonsquare.controller.Controller;
import pigeonsquare.model.Model;
import pigeonsquare.model.Pigeon;
import pigeonsquare.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static pigeonsquare.utils.GameConst.MODEL_TIME_STEP_MILLI;

public class Main {
    private Main(){
    }

    public static void main(String[] args){
        ExecutorService view_exec = Executors.newSingleThreadExecutor();
        ScheduledThreadPoolExecutor models_exec = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(Integer.MAX_VALUE);
        List<Pigeon> pigeons = new ArrayList<>();
        Controller ctrl = new Controller();
        View view = new View(ctrl);
        Model model = new Model(view);

        for (int i = 0; i < 5; i++){
            Pigeon pig = new Pigeon(model);
            pigeons.add(pig);
            pig.addObs(view);
            model.addObs(pig);
        }
        ctrl.setModel(model);

        view_exec.submit(view);

        models_exec.scheduleAtFixedRate(model, 0, MODEL_TIME_STEP_MILLI, TimeUnit.MILLISECONDS);

        for (Pigeon p: pigeons) {
            models_exec.scheduleAtFixedRate(p, 0,MODEL_TIME_STEP_MILLI, TimeUnit.MILLISECONDS);
        }
    }
}

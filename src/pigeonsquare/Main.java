package pigeonsquare;

import pigeonsquare.controller.Controller;
import pigeonsquare.model.Model;
import pigeonsquare.model.Pigeon;
import pigeonsquare.utils.CustomThreadPoolExecutor;
import pigeonsquare.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static pigeonsquare.utils.GameConst.MODEL_TIME_STEP_MILLI;

public class Main {
    private Main(){
    }

    public static void main(String[] args){
        ScheduledThreadPoolExecutor exec = new CustomThreadPoolExecutor(6);
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

        exec.submit(view);

        exec.scheduleAtFixedRate(model, 0, MODEL_TIME_STEP_MILLI, TimeUnit.MILLISECONDS);

        for (Pigeon p: pigeons) {
            exec.scheduleAtFixedRate(p, 0,MODEL_TIME_STEP_MILLI, TimeUnit.MILLISECONDS);
        }
    }
}

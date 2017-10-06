package pigeonsquare.utils;

import static java.lang.Math.*;

public class Maths {
    private Maths(){}
    public static float distance(int x1, int y1, int x2, int y2){
        return (float) sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }
}

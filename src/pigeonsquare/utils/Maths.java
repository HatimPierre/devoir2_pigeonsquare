package pigeonsquare.utils;

import java.security.SecureRandom;

import static java.lang.Math.*;

public class Maths {
    private Maths(){}
    public static SecureRandom sec_rand = new SecureRandom();
    public static float angle(float x1, float y1, float x2, float y2){
        double tmp = Math.atan2(y2 - y1, x2 - x1);
        //tmp += Math.PI/2.0;
        return (float) tmp;
    }
    public static float distance(float x1, float y1, float x2, float y2){
        return (float) sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }
}

package com.east.control.cons;

import java.util.HashMap;
import java.util.Map;

public class EastCons {
    public static final String GET_DEVICE= "adb devices";
    public static final String INIT = "adb -s ";
    public static final String LOCK_PHONE = " shell input keyevent 26";
    public static final String UNLOCK_PHONE= " shell input keyevent 82";
    public static final String ESC= " shell input keyevent 111";
    /**
     * adb shell input touchscreen swipe 930 880 930 380 //向上滑 <br />
     * adb shell input touchscreen swipe 930 880 330 880 //向左滑<br />
     * adb shell input touchscreen swipe 330 880 930 880 //向右滑<br />
     * adb shell input touchscreen swipe 930 380 930 880 //向下滑<br />
     */
    public static final String HP_UP= " shell input touchscreen swipe 930 880 930 380";
    public static final String HP_DOWN= " shell input touchscreen swipe 930 380 930 880";
    public static final String HP_LEFT= " shell input touchscreen swipe 930 880 330 880";
    public static final String HP_RIGHT = " shell input touchscreen swipe 330 880 930 880";
    //adb shell input mouse tap 100 500
    public static final String CLICK= " shell input mouse tap ";
    public static final String INPUT_TEXT= " shell input text ";
    public static final String INPUT_ENTER= " shell input keyevent 66 ";
    public static final String RETURN= " shell input swipe 1 600 500 500";
    public static final String INFO= " shell getprop";


    public static Map<Integer,String> ACTION_MAPPING = new HashMap<Integer,String>();
    static {
        ACTION_MAPPING.put(1,"上滑");
        ACTION_MAPPING.put(2,"下滑");
        ACTION_MAPPING.put(3,"左滑");
        ACTION_MAPPING.put(4,"右滑");
        ACTION_MAPPING.put(5,"输入");
        ACTION_MAPPING.put(6,"单击");
        ACTION_MAPPING.put(7,"返回");
    }
}

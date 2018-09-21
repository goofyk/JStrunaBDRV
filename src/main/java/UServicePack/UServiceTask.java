package UServicePack;

import java.io.*;
import java.util.*;

public class UServiceTask {

    public static void main(String[] args) {
        int intervalScheduler = 5000;
//        if(!UProperties.getPropety("IntervalScheduler").isEmpty()){
//            try {
//                intervalScheduler = Integer.valueOf(UProperties.getPropety("Interval"));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
        new UServiceTask(intervalScheduler);
//        try {
//            //writeToFile(args[0]);
//            writeToFile(String.valueOf(intervalScheduler));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public static void writeToFile(String text) throws IOException {
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\ULTRA\\test.txt"), "UTF-8"));
//        out.write(text);
//        out.close();
//    }

    UServiceTask(int interval) {
        //if(interval == 0) return;
        Timer timer = new Timer();
        timer.schedule(new UServiceTaskHandler(), 0, interval);
//        int intervalScheduler = 5000;
//        try {
//            //writeToFile(args[0]);
//            writeToFile(String.valueOf(intervalScheduler));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

class UServiceTaskHandler extends TimerTask {

    public void run() {
        try {
            writeToFile("Hi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String text) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\ULTRA\\test.txt"), "UTF-8"));
        out.write(text);
        out.close();
    }
}

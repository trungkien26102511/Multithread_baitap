package com.example.kien.multithreadedbasic1;

import android.util.Log;

import java.util.Random;

/**
 * Created by Kien on 11/8/2016.
 */

public class Flipper implements Runnable {
    private final int mLoop;
    private Random mRandom;
    private int mConsecutiveR = 0;
    private int mConsecutiveL = 0;

    public Flipper(int loop){
        this.mLoop = loop;
    }
    @Override
    public void run() {
        mRandom = new Random();
        for(int i = 1; i<= mLoop; i++){
            String threadName = Thread.currentThread().getName();
            int coinHead = mRandom.nextInt(2);
            if(coinHead == 1){
                mConsecutiveR++;
                mConsecutiveL = 0;
            }else{
                mConsecutiveL++;
                mConsecutiveR = 0;
            }
            if(mConsecutiveL >= 3){
                System.out.println(threadName +",consecutive: "+ mConsecutiveL + ",time: " + i);
                Log.d("TAG",threadName +",consecutive: "+ mConsecutiveL + ",time: " + i);
            }else if(mConsecutiveR >= 3){
                System.out.println(threadName +",consecutive: "+ mConsecutiveR + ",time: " + i);
                Log.d("TAG",threadName +",consecutive: "+ mConsecutiveR + ",time: " + i);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }

    }
}

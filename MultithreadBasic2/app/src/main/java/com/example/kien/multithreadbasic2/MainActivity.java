package com.example.kien.multithreadbasic2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements Runnable{
    private Random mRandom;
    private int mConsecutiveR = 0;
    private int mConsecutiveL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void buttonHandler(View clickedButton){
        ExecutorService taskList = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5; i++){
            taskList.execute(this);
        }
    }

    @Override
    public void run() {
        mRandom = new Random();
        for(int i = 1; i <= 1000; i++){
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

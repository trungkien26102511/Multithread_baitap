package com.example.kien.multithreadpartii_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final static int TIMEOUT = 10;
    private TextView mResult;
    private Random mRandom;
    private int mMaxConsecutive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult = (TextView)findViewById(R.id.tv_result);
    }
    public void buttonHandler(View clickedButton){
        ExecutorService taskList = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5;i++){
            taskList.execute(new Flipper(10));
        }
        try {
            taskList.shutdown();
            taskList.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mResult.setText("Max consecutive heads: " + mMaxConsecutive);

    }
    public class Flipper implements Runnable{
        private final int mLoop;
        private int mConsecutiveR = 0;
        private int mConsecutiveL = 0;

        public Flipper(int loop){
            this.mLoop = loop;
        }

        @Override
        public void run() {
            mRandom = new Random();
            synchronized (MainActivity.this){
                for(int i = 1; i <= mLoop; i++){
                    int coinHead = mRandom.nextInt(2);
                    if(coinHead == 1){
                        mConsecutiveR++;
                        mConsecutiveL = 0;
                    }else{
                        mConsecutiveL++;
                        mConsecutiveR = 0;
                    }
                    if(mConsecutiveL > mMaxConsecutive){
                        mMaxConsecutive = mConsecutiveL;
                    }
                    if(mConsecutiveR > mMaxConsecutive){
                        mMaxConsecutive = mConsecutiveR;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}
                }
            }

        }
    }
}

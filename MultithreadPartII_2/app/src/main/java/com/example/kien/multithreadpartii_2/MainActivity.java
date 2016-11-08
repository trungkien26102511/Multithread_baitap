package com.example.kien.multithreadpartii_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinearLayout;
    private Random mRandom;
    private int mMaxConsecutive = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout)findViewById(R.id.ll_result);
    }
    public void buttonHandler(View clickedButton){
        ExecutorService taskList = Executors.newFixedThreadPool(5);
        for(int i = 0; i< 5; i++){
            taskList.execute(new Flipper(1000));
        }
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
                        TextView textview = getTextView(mMaxConsecutive);
                        mLinearLayout.post(new ViewAdder(textview));
                    }
                    if(mConsecutiveR > mMaxConsecutive){
                        mMaxConsecutive = mConsecutiveR;
                        TextView textview = getTextView(mMaxConsecutive);
                        mLinearLayout.post(new ViewAdder(textview));
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}
                }
            }

        }

    }
    public class ViewAdder implements Runnable{
        private TextView mResult;
        public ViewAdder(TextView tv){
            this.mResult = tv;
        }

        @Override
        public void run() {
            mLinearLayout.addView(mResult);

        }
    }
    public TextView getTextView(int max){
        TextView tv = new TextView(this);
        tv.setText("Max consecutive head: " + max);
        return tv;
    }
}

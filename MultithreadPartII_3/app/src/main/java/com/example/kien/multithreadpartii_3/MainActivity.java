package com.example.kien.multithreadpartii_3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinearLayout;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout)findViewById(R.id.ll_result);
        mRandom = new Random();
    }
    public void buttonHandler(View clickedButton){
        for(int i = 0; i < 5; i++){
            FlipperTask task =  new FlipperTask();
            task.execute(mRandom.nextInt(1000));
        }

    }
    public class FlipperTask extends AsyncTask<Integer,Void,TextView>{
        private int mMaxConsecutive = 0;
        private int mConsecutiveR = 0;
        private int mConsecutiveL = 0;

        @Override
        protected TextView doInBackground(Integer... params) {
            int loop =  params[0];
            for(int i = 1; i <= loop; i++){
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
            TextView tv = getTextView(mMaxConsecutive);
            return tv;
        }

        @Override
        protected void onPostExecute(TextView textView) {
            super.onPostExecute(textView);
            mLinearLayout.addView(textView);
        }
    }
    public TextView getTextView(int max){
        TextView tv = new TextView(MainActivity.this);
        tv.setText("Max consecutive head: " + max);
        return tv;
    }
}

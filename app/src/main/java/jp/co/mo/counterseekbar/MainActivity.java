package jp.co.mo.counterseekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_NUMBER = 100;
    private static final int MIN_NUMBER = 0;
    public boolean isRunning = false;
    private int counterNumber = 0;

    private SeekBar mSeekBat;
    private TextView mResultTextView;
    private Button mStartBtn;
    private Button mStopBtn;
    private Button mResetBtn;
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBat = findViewById(R.id.seekBar);
//        mSeekBat.setMin(MIN_NUMBER);
        mSeekBat.setMax(MAX_NUMBER);
        mResultTextView = findViewById(R.id.resultText);
        mStartBtn = findViewById(R.id.startBtn);
        mStartBtn.setOnClickListener(this);
        mStopBtn = findViewById(R.id.stopBtn);
        mStopBtn.setOnClickListener(this);
        mResetBtn = findViewById(R.id.resetBtn);
        mResetBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startBtn:
                isRunning = true;
                if(mThread == null) {
                    mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(isRunning) {
                                if(counterNumber < MAX_NUMBER) {
                                    counterNumber++;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mSeekBat.setProgress(counterNumber);
                                            mResultTextView.setText("counter is " + counterNumber);
                                        }
                                    });
                                } else {
                                    isRunning = false;
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    mThread.start();
                }
                break;
            case R.id.stopBtn:
                isRunning = false;
                mThread = null;
                break;
            case R.id.resetBtn:
                mThread = null;
                counterNumber = 0;
                mSeekBat.setProgress(counterNumber);
                mResultTextView.setText("counter is " + counterNumber);
                break;
        }
    }

}

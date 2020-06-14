package com.example.chess_clock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Integer totalTime;
    ConstraintLayout welcomePage;
    ConstraintLayout clockPage;
    ConstraintLayout whiteSide;
    ConstraintLayout blackSide;
    EditText minutes;
    Button playButton;
    Button startButton;
    TextView whiteCounterTextView;
    TextView blackCounterTextView;
    Button wSwitchButton;
    Button bSwitchButton;
    Button reset;
    MediaPlayer mp;
    CountDownTimer whiteCountDownTimer;
    CountDownTimer blackCountDownTimer;

    long wtimeleftinmillis = 60000;
    long btimeleftinmillis = 60000;
    int numberofwhiteswitchpresses =0;
    int Numberofblackswitchpresses=0;
    int number=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomePage=(ConstraintLayout)findViewById(R.id.welcomePage);
        clockPage=(ConstraintLayout)findViewById(R.id.clockPage);
        minutes=(EditText)findViewById(R.id.minutes);
        playButton=(Button)findViewById(R.id.playButton);
        startButton=(Button)findViewById(R.id.startButton);
        whiteCounterTextView=(TextView)findViewById(R.id.whiteCounterTextView);
        blackCounterTextView=(TextView)findViewById(R.id.blackCounterTextView);
        wSwitchButton=(Button)findViewById(R.id.whiteSwitchButton);
        bSwitchButton=(Button)findViewById(R.id.blackSwitchButton);
        reset=(Button)findViewById(R.id.RestartButton);
        whiteSide=(ConstraintLayout)findViewById(R.id.whiteSide);
        blackSide=(ConstraintLayout)findViewById(R.id.blackSide);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.a);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((minutes.getText().toString()).matches(""))
                    Toast.makeText(MainActivity.this, "Enter Duration First!!!", Toast.LENGTH_SHORT).show();
                else {
                    totalTime = Integer.parseInt(minutes.getText().toString());
                    if(totalTime==0)
                        Toast.makeText(MainActivity.this, "Enter proper Duration!!!", Toast.LENGTH_SHORT).show();
                    else {
                        welcomePage.setVisibility(View.INVISIBLE);
                        clockPage.setVisibility(View.VISIBLE);

                        whiteCounterTextView.setText(totalTime.toString() + ":" + "00");
                        blackCounterTextView.setText(totalTime.toString() + ":" + "00");
                    }
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying())
                    mp.stop();
                whiteCountDownTimer=new CountDownTimer(totalTime*60000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        wtimeleftinmillis=millisUntilFinished;
                        int minutes=(int) (millisUntilFinished/1000)/60;
                        int seconds=(int) (millisUntilFinished/1000)%60;
                        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
                        whiteCounterTextView.setText(timeLeftFormatted);
                    }

                    @Override
                    public void onFinish() {
                        whiteCounterTextView.setText("00:00");
                        mp = MediaPlayer.create(getApplicationContext(), R.raw.a);
                        mp.start();
                        wSwitchButton.setVisibility(View.INVISIBLE);
                        bSwitchButton.setVisibility(View.INVISIBLE);
                        whiteSide.setBackgroundColor(Color.parseColor("#F42A2A"));
                        blackSide.setBackgroundColor(Color.parseColor("#84E331"));
                    }
                }.start();
                startButton.setVisibility(View.INVISIBLE);
                wSwitchButton.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
                Numberofblackswitchpresses=1;

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberofwhiteswitchpresses>0)
                    blackCountDownTimer.cancel();
                Numberofblackswitchpresses=0;
                numberofwhiteswitchpresses=0;
                whiteCountDownTimer.cancel();
                startButton.setVisibility(View.VISIBLE);
                reset.setVisibility(View.INVISIBLE);
                wSwitchButton.setVisibility(View.INVISIBLE);
                bSwitchButton.setVisibility(View.INVISIBLE);
                whiteCounterTextView.setText(totalTime.toString()+":"+"00");
                blackCounterTextView.setText(totalTime.toString()+":"+"00");
                whiteSide.setBackgroundColor(Color.parseColor("#FFFFFF"));
                blackSide.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        wSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whiteCountDownTimer.cancel();
                if(numberofwhiteswitchpresses>0) {
                    blackCountDownTimer = new CountDownTimer(btimeleftinmillis, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btimeleftinmillis = millisUntilFinished;
                            int minutes = (int) (btimeleftinmillis / 1000) / 60;
                            int seconds = (int) (btimeleftinmillis / 1000) % 60;
                            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                            blackCounterTextView.setText(timeLeftFormatted);
                        }

                        @Override
                        public void onFinish() {
                            blackCounterTextView.setText("00:00");
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.a);
                            mp.start();
                            wSwitchButton.setVisibility(View.INVISIBLE);
                            bSwitchButton.setVisibility(View.INVISIBLE);
                            blackSide.setBackgroundColor(Color.parseColor("#F42A2A"));
                            whiteSide.setBackgroundColor(Color.parseColor("#84E331"));
                        }
                    }.start();
                }
                else
                {
                    numberofwhiteswitchpresses=1;
                    blackCountDownTimer = new CountDownTimer(totalTime*60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btimeleftinmillis = millisUntilFinished;
                            int minutes = (int) (btimeleftinmillis / 1000) / 60;
                            int seconds = (int) (btimeleftinmillis / 1000) % 60;
                            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                            blackCounterTextView.setText(timeLeftFormatted);
                        }

                        @Override
                        public void onFinish() {
                            blackCounterTextView.setText("00:00");
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.a);
                            mp.start();
                            wSwitchButton.setVisibility(View.INVISIBLE);
                            bSwitchButton.setVisibility(View.INVISIBLE);
                            blackSide.setBackgroundColor(Color.parseColor("#F42A2A"));
                            whiteSide.setBackgroundColor(Color.parseColor("#84E331"));

                        }
                    }.start();
                }
                wSwitchButton.setVisibility(View.INVISIBLE);
                bSwitchButton.setVisibility(View.VISIBLE);

            }
        });

        bSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blackCountDownTimer.cancel();
                if(Numberofblackswitchpresses>0){
                    whiteCountDownTimer = new CountDownTimer(wtimeleftinmillis, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            wtimeleftinmillis = millisUntilFinished;
                            int minutes = (int) (wtimeleftinmillis / 1000) / 60;
                            int seconds = (int) (wtimeleftinmillis / 1000) % 60;
                            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                            whiteCounterTextView.setText(timeLeftFormatted);

                        }

                        @Override
                        public void onFinish() {
                            whiteCounterTextView.setText("00:00");
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.a);
                            mp.start();
                            wSwitchButton.setVisibility(View.INVISIBLE);
                            bSwitchButton.setVisibility(View.INVISIBLE);
                            whiteSide.setBackgroundColor(Color.parseColor("#F42A2A"));
                            blackSide.setBackgroundColor(Color.parseColor("#84E331"));
                        }
                    }.start();

                }
                else
                {
                    Numberofblackswitchpresses=1;
                    whiteCountDownTimer = new CountDownTimer(totalTime*60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            wtimeleftinmillis = millisUntilFinished;
                            int minutes = (int) (wtimeleftinmillis / 1000) / 60;
                            int seconds = (int) (wtimeleftinmillis / 1000) % 60;
                            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                            whiteCounterTextView.setText(timeLeftFormatted);
                        }

                        @Override
                        public void onFinish() {
                            whiteCounterTextView.setText("00:00");
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.a);
                            mp.start();
                            wSwitchButton.setVisibility(View.INVISIBLE);
                            bSwitchButton.setVisibility(View.INVISIBLE);
                            whiteSide.setBackgroundColor(Color.parseColor("#F42A2A"));
                            blackSide.setBackgroundColor(Color.parseColor("#84E331"));
                        }
                    }.start();
                }
                bSwitchButton.setVisibility(View.INVISIBLE);
                wSwitchButton.setVisibility(View.VISIBLE);

            }
        });



    }
}
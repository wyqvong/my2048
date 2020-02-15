package com.wyq.my2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public MainActivity(){
        mainActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScore = (TextView)findViewById(R.id.tvScore);
        findViewById(R.id.newGame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameView.gameView.startGame();
            }
        });
    }

    public void clearScore(){
        score = 0;
    }
    public void  showScore(){
        tvScore.setText(score+"");
    }
    public void addScore(int s){
        score+=s;
        showScore();
    }

    private  int score = 0;
    private TextView tvScore;
    private static MainActivity mainActivity = null;

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}

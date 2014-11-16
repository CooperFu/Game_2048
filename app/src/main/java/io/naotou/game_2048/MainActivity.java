package io.naotou.game_2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tv_score;
    private static MainActivity mainActivity = null;
    private int score = 0;
    private Button btn_start_game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_score = (TextView) findViewById(R.id.textView);
        btn_start_game = (Button) findViewById(R.id.btn_start_game);
        btn_start_game.setOnClickListener(this);

    }
    public MainActivity(){
        mainActivity = this;
    }


    public static MainActivity getMainActivity() {

        return mainActivity;
    }
    public void clearScore(){
        score = 0;
        showScore();
    }
    public void showScore(){
        tv_score.setText(score+"");
    }
    public void addScore(int s) {
        score = score+s;
        showScore();
    }
    public int endScore(){
        int endScore = score;
        return endScore;
    }

    @Override
    public void onClick(View v) {

    }
}

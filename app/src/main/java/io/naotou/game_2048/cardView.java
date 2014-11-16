package io.naotou.game_2048;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Jack_Cooper on 2014/11/15.
 * Have a nice day!
 */
public class cardView extends FrameLayout {

    private int num = 0;
    private TextView label;

    public cardView(Context context) {

        super(context);
        label = new TextView(getContext());
        label.setTextSize(33);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(10, 10, 10, 10);
        addView(label, layoutParams);
    }

    public int getNum() {

        return num;
    }

    public void setNum(int num) {

        this.num = num;
        if (num <= 0) {
            //如果随机到1以下. 就填充空的
            label.setText("");
            if (label.getText().toString().equals("")) {
                label.setBackgroundColor(Color.WHITE);
            }

        } else {
            label.setText("" + num);
            if(label.getText().toString().equals("2")){
                label.setTextColor(Color.WHITE);
                label.setBackgroundColor(0xffa3cf62);
            }else if (label.getText().toString().equals("4")) {
                label.setBackgroundColor(0xffafb4db);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("8")) {
                label.setBackgroundColor(0xfffaa755);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("16")) {
                label.setBackgroundColor(0xffae6642);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("32")) {
                label.setBackgroundColor(0xff694d9f);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("64")) {
                label.setBackgroundColor(0xfff173ac);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("128")) {
                label.setBackgroundColor(0xff94d6da);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("256")) {
                label.setBackgroundColor(0xff6d5826);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("512")) {
                label.setBackgroundColor(0xffffc20e);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("1024")) {
                label.setBackgroundColor(0xff54211d);
                label.setTextColor(Color.WHITE);
            }else if (label.getText().toString().equals("2048")) {
                label.setBackgroundColor(0xff525f42);
                label.setTextColor(Color.WHITE);
            }

        }
    }

    public boolean equals(cardView o) {

        return getNum() == o.getNum();
    }
}

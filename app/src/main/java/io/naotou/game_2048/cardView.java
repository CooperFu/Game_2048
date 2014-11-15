package io.naotou.game_2048;

import android.content.Context;
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
        } else {
            label.setText("" + num);

        }
    }

    public boolean equals(cardView o) {

        return getNum() == o.getNum();
    }
}

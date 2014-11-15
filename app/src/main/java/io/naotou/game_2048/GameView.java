package io.naotou.game_2048;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack_Cooper on 2014/11/15.
 * Have a nice day!
 */
public class GameView extends GridLayout {

    public static final String TAG = "GameView";

    private cardView[][] cardViews = new cardView[4][4];
    private List<Point> emptyList = new ArrayList<Point>();
    //保险起见 把所有的构造方法都重写出来, 保证不管怎么进来的 都能执行initView方法.
    public GameView(Context context) {

        super(context);
        initView();

    }


    public GameView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        //设置4行
        setColumnCount(4);

        //设置背景颜色
        setBackgroundColor(Color.GRAY);

        setOnTouchListener(new OnTouchListener() {

            private float downX
                    ,
                    downY
                    ,
                    upY
                    ,
                    upX
                    ,
                    offsetX
                    ,
                    offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        offsetX = upX - downX;
                        offsetY = upY - downY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            //横向滑动
                            if (offsetX > 0) {
                                //右滑
                                Log.d(TAG, "右滑");
                                swipeRight();
                            } else if (offsetX < 0) {
                                //左滑
                                Log.d(TAG, "左滑");
                                swipeLeft();
                            }
                        } else if (Math.abs(offsetX) < Math.abs(offsetY)) {
                            //竖向滑动
                            if (offsetY > 0) {
                                Log.d(TAG, "下拉");
                                swipeDown();
                                // 下拉

                            } else if (offsetY < 0) {
                                Log.d(TAG, "上拉");
                                //上拉
                                swipeUp();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

        //这里是每个卡面的宽度
        int cardWidth = (Math.min(w, h) - 10) / 4;
        addCards(cardWidth, cardWidth);
    }
    private void addRandomNum(){
        emptyList.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardViews[x][y].getNum() <= 0) {
                    emptyList.add(new Point(x, y));
                }
            }
        }

    }

    private void addCards(int cardWidth, int cardHeight) {

        cardView view;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                view = new cardView(getContext());
                view.setNum(0);
                addView(view, cardWidth, cardHeight);
                cardViews[x][y] = view;
            }
        }
    }


    private void swipeUp() {


    }

    private void swipeDown() {


    }

    private void swipeLeft() {


    }

    private void swipeRight() {


    }
}

package io.naotou.game_2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack_Cooper on 2014/11/15.
 * Have a nice day!
 */
public class GameView extends GridLayout {

    public static final String TAG = "GameView";

    private cardView[][] Card = new cardView[4][4];
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

        startGame();

    }

    public void startGame() {
        //开始游戏的话先清理.
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Card[x][y].setNum(0);
            }
        }
        MainActivity.getMainActivity().clearScore();
        //添加两个随机数
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum() {

        emptyList.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (Card[x][y].getNum() <= 0) {
                    emptyList.add(new Point(x, y));
                }
            }
        }
        Point point = emptyList.remove((int) (Math.random() * emptyList.size()));
        //10分之一设置4  十分之九设置2.
        Card[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }

    private void addCards(int cardWidth, int cardHeight) {

        cardView view;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                view = new cardView(getContext());
                view.setNum(0);
                addView(view, cardWidth, cardHeight);
                Card[x][y] = view;
            }
        }
    }

    private void swipeLeft() {

        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x + 1; x1 < 4; x1++) {
                    //这里说明 向左划的时候 右面不为空
                    if (Card[x1][y].getNum() > 0) {
                        //如果当前位置是空的话, 就把这个坐标右面的块块放到当前地方去.
                        if (Card[x][y].getNum() <= 0) {
                            //把得到的坐标设置给最开始遍历的坐标
                            Card[x][y].setNum(Card[x1][y].getNum());
                            //把得到的坐标清空
                            Card[x1][y].setNum(0);

                            //如果.[x][y]为空,[x1][y]跑到左边, 如果[x2][y]和[x1][y]的值相等,就不会合并.
                            //就让他多走一次.防止这种情况发生.
                            x--;
                            merge = true;
                            //如果 两张卡片相同.
                        } else if (Card[x][y].equals(Card[x1][y])) {
                            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.swipeleft);
                            // Card[x1][y].startAnimation(animation);
                            Card[x][y].startAnimation(animation);
                            //把左边的设置成当前的二倍
                            Card[x][y].setNum(Card[x][y].getNum() * 2);
                            //清空遍历到的
                            Card[x1][y].setNum(0);
                            merge = true;
                            MainActivity.getMainActivity().addScore(Card[x][y].getNum() * 10);

                        }
                        break;

                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkOver();
        }
    }

    private void swipeUp() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (Card[x][y1].getNum() > 0) {
                        if (Card[x][y].getNum() <= 0) {
                            Card[x][y].setNum(Card[x][y1].getNum());
                            Card[x][y1].setNum(0);
                            y--;
                            merge = true;
                        } else if (Card[x][y].equals(Card[x][y1])) {
                            Card[x][y].setNum(Card[x][y].getNum() * 2);
                            Card[x][y1].setNum(0);
                            Card[x][y].startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.swipeup));
                            merge = true;
                            MainActivity.getMainActivity().addScore(Card[x][y].getNum() * 10);
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkOver();
        }

    }

    private void swipeDown() {

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (Card[x][y1].getNum() > 0) {
                        if (Card[x][y].getNum() <= 0) {
                            Card[x][y].setNum(Card[x][y1].getNum());
                            Card[x][y1].setNum(0);
                            y++;
                            merge = true;
                        } else if (Card[x][y].equals(Card[x][y1])) {
                            Card[x][y].setNum(Card[x][y].getNum() * 2);
                            Card[x][y1].setNum(0);
                            Card[x][y].startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.swipedown));
                            merge = true;
                            MainActivity.getMainActivity().addScore(Card[x][y].getNum() * 10);
                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkOver();
        }

    }

    private void swipeRight() {

        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (Card[x1][y].getNum() > 0) {
                        if (Card[x][y].getNum() <= 0) {
                            Card[x][y].setNum(Card[x1][y].getNum());
                            Card[x1][y].setNum(0);
                            x++;
                            merge = true;
                        } else if (Card[x][y].equals(Card[x1][y])) {
                            Card[x][y].setNum(Card[x][y].getNum() * 2);
                            Card[x1][y].setNum(0);
                            Card[x][y].startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.swiperight));
                            merge = true;
                            MainActivity.getMainActivity().addScore(Card[x][y].getNum() * 10);
                        }
                        break;

                    }
                }
            }
        }
        if (merge) {
            addRandomNum();
            checkOver();
        }

    }

    private void checkOver() {

        boolean over = true;
        BreakAll:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (Card[x][y].getNum() == 0 || x > 0 && (Card[x][y].equals(Card[x - 1][y])) || x < 3 && (Card[x][y].equals(Card[x + 1][y])) || y > 0 && (Card[x][y].equals(Card[x][y - 1])) || y < 3 && (Card[x][y].equals(Card[x][y + 1]))) {

                    //如果能走到这里来, 就代表游戏可以继续.
                    over = false;
                    break BreakAll;

                }
            }
        }
        if (over) {

            int i = MainActivity.getMainActivity().endScore();
            new AlertDialog.Builder(getContext()).setTitle("游戏结束").setMessage("您的最终得分是:" + i + "分!").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startGame();
                }
            }).show();
        }
    }
}



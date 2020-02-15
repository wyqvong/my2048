package com.wyq.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {//GameView继承GridLayout布局
    public GameView(Context context) {
        super(context);
        gameView = this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameView = this;
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gameView = this;
        initGameView();
    }

    public static GameView gameView = null;


    private void initGameView(){  //初始化方法
        setColumnCount(4);//布局为4列
        setBackgroundColor(0xffbbada0);//框框的背景
        setOnTouchListener(new View.OnTouchListener(){  //设置交互方式
            private float startX,startY,offsetX,offsetY;//X轴和Y轴方向上的偏移
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX()-startX;
                        offsetY = event.getY()-startY;

                        if (Math.abs(offsetX)>Math.abs(offsetY)){//判断用户的方向，解决当用户向斜方向滑动时程序应如何判断的问题
                            if (offsetX<-5){
                                swipeLeft();
                            }else if (offsetX>5){
                                swipeRight();
                            }
                        }else {
                            if (offsetY<-5){
                                swipeUp();
                            }else if (offsetY>5){
                                swipeDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //动态计算卡片宽高
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h)-10)/4;//10像素的间隔

        addCards(cardWidth,cardWidth);

        startGame();
    }
    private void addCards(int cardWidth, int cardHeight){
        Card c;
        for (int y = 0 ;y < 4; y++){
            for (int x = 0;x < 4; x++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c,cardWidth,cardHeight);

                cardsMap[x][y] = c;
            }
        }
    }

    public void startGame(){
        MainActivity.getMainActivity().clearScore();
        for (int y =0; y < 4; y++){
            for (int x =0; x < 4; x++){
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum(){ //添加随机数
        emptyPoints.clear(); //添加随机数之前先清空emptyPoints,然后把每一个空点都添加进来

        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                if (cardsMap[x][y].getNum()<=0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));//随机地移除一个点
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }

    private void swipeLeft(){
        boolean merge = false; //判断是否有合并，如果有的话就进行一些处理
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                for (int x1 = x+1; x1 < 4; x1++){ //从当前的位置往右边去遍历
                    if (cardsMap[x1][y].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;//x--之后继续遍历一次，有相同的则合并
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());//合并时加分
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){
        boolean merge = false;
        for (int y = 0; y < 4; y++){
            for (int x = 3; x >= 0; x--){
                for (int x1 = x-1; x1 >=0; x1--){
                    if (cardsMap[x1][y].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){
        boolean merge = false;
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                for (int y1 = y+1; y1 < 4; y1++){
                    if (cardsMap[x][y1].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){
        boolean merge = false;
        for (int x = 0; x < 4; x++){
            for (int y = 3; y >=0; y--){
                for (int y1 = y-1; y1 >=0; y1--){
                    if (cardsMap[x][y1].getNum()>0){
                        if (cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        if (merge){
            addRandomNum();
            checkComplete();
        }
    }
    private void checkComplete(){  //判断游戏是否结束
        boolean complete = true;
        ALL:
        for (int y = 0; y < 4; y++){
            for (int x = 0; x < 4; x++){
                if (cardsMap[x][y].getNum()==0||   //游戏没有结束的5种判定情况
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("你好").setMessage("游戏结束").setPositiveButton("重来", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }
    }
    private Card[][] cardsMap = new Card[4][4];

    private List<Point> emptyPoints = new ArrayList<Point>();//新建List用于存储空点位置，把所有空点位置放在一个数组里面
}

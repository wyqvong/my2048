package com.wyq.my2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Card extends FrameLayout {
    public Card(@NonNull Context context) {
        super(context);

        label = new TextView(getContext());//初始化
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);
        label.setGravity(Gravity.CENTER);//文字居中显示

        LayoutParams lp = new LayoutParams(-1,-1);//设置布局参数 宽和高填充满整个父级容器
        lp.setMargins(10,10,0,0);//设置格子之间的间隔
        addView(label, lp);

        setNum(0);
    }
    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;

        if (num<=0){
            label.setText("");
        }else {
            label.setText(num+"");
        }

        switch (num) {
            case 0:
                label.setBackgroundColor(0x33ffffff);//透明色
                break;
            case 2:
                label.setBackgroundColor(0xffeee4da);
                break;
            case 4:
                label.setBackgroundColor(0xffede0c8);
                break;
            case 8:
                label.setBackgroundColor(0xfff2b179);
                break;
            case 16:
                label.setBackgroundColor(0xfff59563);
                break;
            case 32:
                label.setBackgroundColor(0xfff67c5f);
                break;
            case 64:
                label.setBackgroundColor(0xfff65e3b);
                break;
            case 128:
                label.setBackgroundColor(0xffedcf72);
                break;
            case 256:
                label.setBackgroundColor(0xffffff00);
                break;
            case 512:
                label.setBackgroundColor(0xffccff00);
                break;
            case 1024:
                label.setBackgroundColor(0xff99ff00);
                break;
            case 2048:
                label.setBackgroundColor(0xff66ff00);
                break;
            case 4096:
                label.setBackgroundColor(0xff663333);
                break;
            case 8192:
                label.setBackgroundColor(0xff0000ff);
                break;
            default:
                label.setBackgroundColor(0xff3c3a32);
                break;
        }
    }

    public boolean equals(@Nullable Card o) {  //判断两个卡片上的数字是否相同
        return getNum()==o.getNum();
    }

    private TextView label;//卡片需要呈现文字

}

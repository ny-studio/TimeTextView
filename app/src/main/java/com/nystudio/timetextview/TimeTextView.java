package com.nystudio.timetextview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTextView extends RelativeLayout implements Runnable {

    private final static long LOOP_INTERVAL = 30; // 30ms

    private String pattern = "yyyy/MM/dd HH:mm:ss";
    private SimpleDateFormat simpleDataFormat = new SimpleDateFormat(pattern);
    private Thread thread = new Thread(this);
    private Date nowDate;
    private TextView timeTextView;

    //text params
    private int textSize = 20; // dp
    private int textColor = Color.BLACK;

    public TimeTextView(Context ctx) {
        super(ctx);
        initTimeTextView(ctx);
    }

    public TimeTextView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        initTimeTextView(ctx);
    }

    public TimeTextView(Context ctx, AttributeSet attrs, int defaultStyle) {
        super(ctx, attrs, defaultStyle);
        initTimeTextView(ctx);
    }

    protected void initTimeTextView(Context ctx) {
        timeTextView = new TextView(ctx);
        timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        timeTextView.setTextColor(textColor);

        this.addView(timeTextView, new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        ));

        thread.start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            post(new Runnable() {
                @Override
                public void run() {
                    nowDate = new Date(System.currentTimeMillis());
                    timeTextView.setText(simpleDataFormat.format(nowDate));
                }
            });
            try {
                Thread.sleep(LOOP_INTERVAL);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

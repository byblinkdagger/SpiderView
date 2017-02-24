package com.example.spiderview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lucky on 2017/2/24.
 */
public class SpiderView extends View {

    private float radius ;//网格最大半径
    //正多边形边数
    private int count = 6;
    //每个坐标点的角度
    private float angle ;
    private int width;

    private int height;
    private String[]  nameArry = {"物理","魔法","金钱","经验","击杀","助攻"};
    private float[] userData = {0.2f,0.8f,0.7f,0.8f,0.8f,0.2f};

    private Paint polygonPaint;
    private Paint textPaint;
    private Paint dataPaint;

    private float mTextSize = 22;
    private int mTextColor = Color.RED;

    public SpiderView(Context context) {
        this(context,null);
    }

    public SpiderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpiderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        count = nameArry.length;
        angle = (float) (Math.PI*2/count);

        polygonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        polygonPaint.setColor(Color.BLUE);
        polygonPaint.setStrokeWidth(1);
        polygonPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(mTextSize);

        dataPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dataPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        dataPaint.setAlpha(255);
        dataPaint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        //最大半径
        radius = (Math.min(w,h)-180)/2;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.translate(width/2,height/2);
        //画用户数据图
        Path dataPath = new Path();
        for (int i = 0; i < count; i++) {
            if (i==0) {
                dataPath.moveTo(width/2+radius*userData[i],height/2);
            }
            dataPath.lineTo((float)(width/2+radius*Math.cos(angle*i)*userData[i]),(float)(height/2+radius*Math.sin(angle*i)*userData[i]));
        }
        canvas.drawPath(dataPath,dataPaint);

        //画正多边形
        Path polygonPath = new Path();
        //最内层多边形的边长（每层的间隙）
        float gap = radius/(count-1);
        for (int i = 1; i < count; i++) {
            polygonPath.reset();
            float currentRadius = gap*i;
            for (int j = 0; j <count; j++) {
                if(j==0){
                    //从右水平点开始lineTo
                    polygonPath.moveTo(width/2+currentRadius,height/2);
                }else{
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (width/2+currentRadius*Math.cos(angle*j));
                    float y = (float) (height/2+currentRadius*Math.sin(angle*j));
                    polygonPath.lineTo(x,y);
                }
            }
            polygonPath.close();
            canvas.drawPath(polygonPath,polygonPaint);
        }

        //逐一画中心点到最外层多边形顶点的连接线
        Path linePath = new Path();
        for (int i = 0; i < count; i++) {
            linePath.reset();
            linePath.moveTo(width/2,height/2);
            linePath.lineTo((float)(width/2+radius*Math.cos(angle*i)),(float)(height/2+radius*Math.sin(angle*i)));
            canvas.drawPath(linePath,polygonPaint);
        }

        //画标题，从右水平方向开始
        for (int i = 0; i < count; i++) {
            String text = nameArry[i];
            Rect textRect = new Rect();
            if (((angle * i) < (Math.PI/2)) || ((angle * i)> (Math.PI*1.5))) {
                Log.d("blink","i1111:"+i);
                textPaint.getTextBounds(text, 0, text.length(), textRect);
                canvas.drawText(text, (float) (width / 2 + (radius + textRect.width() / 2) * Math.cos(angle * i)), (float) (height / 2 + (radius + textRect.height() / 2) * Math.sin(angle * i)), textPaint);
            } else{
                textPaint.getTextBounds(text, 0, text.length(), textRect);
                Log.d("blink","i2222:"+i);
                canvas.drawText(text, (float) (width / 2 + (radius + 3*textRect.width() / 2) * Math.cos(angle * i)), (float) (height / 2 + (radius + 3*textRect.height() / 2) * Math.sin(angle * i)), textPaint);
            }
        }
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setDataPaint(Paint dataPaint) {
        this.dataPaint = dataPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public void setPolygonPaint(Paint polygonPaint) {
        this.polygonPaint = polygonPaint;
    }

    public void setUserData(float[] userData) {
        this.userData = userData;
    }

    public void setNameArry(String[] nameArry) {
        this.nameArry = nameArry;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

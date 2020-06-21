package com.devashish.ludo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int calibration_status = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void unblur(View view){
        view.animate().alpha(1f).setDuration(1000);
    }

    private void longMsg(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
    }

    private float testBoxInitX = 0f;
    private float testBoxInitY = 0f;
    String b4="";

    private void translateView(TextView textView,int transX,int transY){
        textView.animate().translationX(transX).setDuration(400);
        textView.animate().translationY(transY).setDuration(400);
    }

    public void calibrate(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        testBox.setBackgroundColor(Color.rgb(20,30,80));

        calibration_status++;
        testBox.setText(calibration_status+"");
        int[] loc = new int[2];
        testBox.getLocationOnScreen(loc);
        if(calibration_status==0){
            testBoxInitX = transX;
            testBoxInitY = transY;
            longMsg("Take the box to the first box where red comes out and press this button again");
        } else if(calibration_status==1){
            Coordinates.setXY("r0",transX,transY);
            translateView(testBox,transX,transY);
            longMsg("Nice. Coordinate Noted. Now please take to the 2nd box that red takes and click me again.");
        } else if(calibration_status==2){
            Coordinates.setXY("r1",transX,transY);
            translateView(testBox,transX,transY);
            longMsg("Very Nice. Now please take to the 6th box where red goes, and click again.");
        } else if(calibration_status==3){
            Coordinates.setXY("r5",transX,transY);
            translateView(testBox,transX,transY);
            longMsg("All Done! Please wait while calibration completes.. We will notify!");
            Coordinates.calibrate();
        } else if(calibration_status>=4){
            int indx = calibration_status-4;
            char c = 'r';
            if(indx>=13 && indx<=25){
                indx = indx -13;
                c = 'g';
            }
            if(indx>=26 && indx<=38){
                indx = indx -26;
                c = 'y';
            }
            if(indx>=39 && indx<=51){
                indx = indx -39;
                c = 'b';
            }
            if(indx>=52){
                calibration_status=4;
            } else {
                testBox.setText(c+""+indx);
                transX = Coordinates.getX(c+""+indx);
                transY = Coordinates.getY(c+""+indx);
                translateView(testBox,transX,transY);
            }
        }
    }

    int transX = 0;
    int transY = 0;

    public void moveUp(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transY-=2;
        testBox.animate().translationY(transY).setDuration(100);
        int[] loc = new int[2];
        testBox.getLocationOnScreen(loc);
        coor.setText("X:"+loc[0]+",Y:"+loc[1]);
    }
    public void moveDown(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transY+=2;
        testBox.animate().translationY(transY).setDuration(100);
        int[] loc = new int[2];
        testBox.getLocationOnScreen(loc);
        coor.setText("X:"+loc[0]+",Y:"+loc[1]);
    }
    public void moveLeft(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transX-=2;
        testBox.animate().translationX(transX).setDuration(100);
        int[] loc = new int[2];
        testBox.getLocationOnScreen(loc);
        coor.setText("X:"+loc[0]+",Y:"+loc[1]);
    }
    public void moveRight(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transX+=2;
        testBox.animate().translationX(transX).setDuration(100);
        int[] loc = new int[2];
        testBox.getLocationOnScreen(loc);
        coor.setText("X:"+loc[0]+",Y:"+loc[1]);
    }
}

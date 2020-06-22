package com.devashish.ludo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private int calibration_status = -1;
    private TextView r0;
    private TextView r1;
    private TextView r2;
    private TextView r3;

    private TextView g0;
    private TextView g1;
    private TextView g2;
    private TextView g3;

    private TextView b0;
    private TextView b1;
    private TextView b2;
    private TextView b3;

    private TextView y0;
    private TextView y1;
    private TextView y2;
    private TextView y3;

    HashMap<String,String> initPos = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r0 = (TextView) findViewById(R.id.red0);
        r1 = (TextView) findViewById(R.id.red1);
        r2 = (TextView) findViewById(R.id.red2);
        r3 = (TextView) findViewById(R.id.red3);

        g0 = (TextView) findViewById(R.id.green0);
        g1 = (TextView) findViewById(R.id.green1);
        g2 = (TextView) findViewById(R.id.green2);
        g3 = (TextView) findViewById(R.id.green3);

        b0 = (TextView) findViewById(R.id.blue0);
        b1 = (TextView) findViewById(R.id.blue1);
        b2 = (TextView) findViewById(R.id.blue2);
        b3 = (TextView) findViewById(R.id.blue3);

        y0 = (TextView) findViewById(R.id.yellow0);
        y1 = (TextView) findViewById(R.id.yellow1);
        y2 = (TextView) findViewById(R.id.yellow2);
        y3 = (TextView) findViewById(R.id.yellow3);

        int DURATION = 800;



        r0.animate().translationX(-250).setDuration(DURATION);
        r0.animate().translationY(-250).setDuration(DURATION);
        initPos.put("r0","-250,-250");
        r1.animate().translationX(-150).setDuration(DURATION);
        r1.animate().translationY(-250).setDuration(DURATION);
        initPos.put("r1","-150,-250");
        r2.animate().translationX(-250).setDuration(DURATION);
        r2.animate().translationY(-150).setDuration(DURATION);
        initPos.put("r2","-250,-150");
        r3.animate().translationX(-150).setDuration(DURATION);
        r3.animate().translationY(-150).setDuration(DURATION);
        initPos.put("r3","-150,-150");

        g0.animate().translationX(-250).setDuration(DURATION);
        g0.animate().translationY(-650).setDuration(DURATION);
        initPos.put("g0","-250,-650");
        g1.animate().translationX(-150).setDuration(DURATION);
        g1.animate().translationY(-650).setDuration(DURATION);
        initPos.put("g1","-150,-650");
        g2.animate().translationX(-250).setDuration(DURATION);
        g2.animate().translationY(-550).setDuration(DURATION);
        initPos.put("g2","-250,-550");
        g3.animate().translationX(-150).setDuration(DURATION);
        g3.animate().translationY(-550).setDuration(DURATION);
        initPos.put("g3","-150,-550");

        y0.animate().translationX(250).setDuration(DURATION);
        y0.animate().translationY(-650).setDuration(DURATION);
        initPos.put("y0","250,-650");
        y1.animate().translationX(150).setDuration(DURATION);
        y1.animate().translationY(-650).setDuration(DURATION);
        initPos.put("y1","150,-650");
        y2.animate().translationX(250).setDuration(DURATION);
        y2.animate().translationY(-550).setDuration(DURATION);
        initPos.put("y2","250,-550");
        y3.animate().translationX(150).setDuration(DURATION);
        y3.animate().translationY(-550).setDuration(DURATION);
        initPos.put("y3","150,-550");

        b0.animate().translationX(250).setDuration(DURATION);
        b0.animate().translationY(-250).setDuration(DURATION);
        initPos.put("b0","250,-250");
        b1.animate().translationX(150).setDuration(DURATION);
        b1.animate().translationY(-250).setDuration(DURATION);
        initPos.put("b1","150,-250");
        b2.animate().translationX(250).setDuration(DURATION);
        b2.animate().translationY(-150).setDuration(DURATION);
        initPos.put("b2","250,-150");
        b3.animate().translationX(150).setDuration(DURATION);
        b3.animate().translationY(-150).setDuration(DURATION);
        initPos.put("b3","150,-150");

        autoCalibrate();
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

    public void autoCalibrate(){
        Coordinates.setXY("r0",-52,-138);
        Coordinates.setXY("r1",-52,-182);
        Coordinates.setXY("r5",-96,-358);
        Coordinates.calibrate();
    }

    public void calibrate(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        calibration_status++;
        if(calibration_status>=56){
            calibration_status=4;
        }
        testBox.setText(calibration_status+"");
        int[] loc = new int[2];
        testBox.getLocationOnScreen(loc);
        if(calibration_status==0){
            testBox.setBackgroundColor(Color.rgb(20,30,80));
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
            testBox.setBackgroundColor(Color.rgb(100,30,30));
            if(indx>=13 && indx<=25){
                indx = indx -13;
                c = 'g';
                testBox.setBackgroundColor(Color.rgb(30,100,30));
            }
            if(indx>=26 && indx<=38){
                indx = indx -26;
                testBox.setBackgroundColor(Color.rgb(100,100,30));
                c = 'y';
            }
            if(indx>=39 && indx<=51){
                indx = indx -39;
                testBox.setBackgroundColor(Color.rgb(30,30,100));
                c = 'b';
            }
            testBox.setText(c+""+indx);
            transX = Coordinates.getX(c+""+indx);
            transY = Coordinates.getY(c+""+indx);
            translateView(testBox,transX,transY);
        }
    }

    int transX = 0;
    int transY = 0;

    public void moveUp(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transY-=2;
        testBox.animate().translationY(transY).setDuration(100);
        coor.setText("X:"+transX+",Y:"+transY);
    }
    public void moveDown(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transY+=2;
        testBox.animate().translationY(transY).setDuration(100);
        coor.setText("X:"+transX+",Y:"+transY);
    }
    public void moveLeft(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transX-=2;
        testBox.animate().translationX(transX).setDuration(100);
        coor.setText("X:"+transX+",Y:"+transY);
    }
    public void moveRight(View view){
        TextView testBox = (TextView) findViewById(R.id.testBox);
        TextView coor = (TextView) findViewById(R.id.coor);
        transX+=2;
        testBox.animate().translationX(transX).setDuration(100);
        coor.setText("X:"+transX+",Y:"+transY);
    }

    private Game game = null;

    private void lockControls(CheckBox ck1,CheckBox ck2,CheckBox ck3,CheckBox ck4,LinearLayout layout,Button btn){
        btn.setEnabled(false);
        ck1.setEnabled(false);
        ck2.setEnabled(false);
        ck3.setEnabled(false);
        ck4.setEnabled(false);
        layout.setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.testBox)).setVisibility(View.INVISIBLE);
    }

    private void unlockControls(CheckBox ck1,CheckBox ck2,CheckBox ck3,CheckBox ck4,LinearLayout layout,Button btn){
        btn.setEnabled(true);
        ck1.setEnabled(true);
        ck2.setEnabled(true);
        ck3.setEnabled(true);
        ck4.setEnabled(true);
        layout.setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.testBox)).setVisibility(View.VISIBLE);
    }

    private Player whoseTurn;
    private boolean diceRolled = false;

    public void startGame(View view){
        CheckBox redPlaying = (CheckBox) findViewById(R.id.redPlaying);
        CheckBox greenPlaying = (CheckBox) findViewById(R.id.greenPlaying);
        CheckBox bluePlaying = (CheckBox) findViewById(R.id.bluePlaying);
        CheckBox yellowPlaying = (CheckBox) findViewById(R.id.yellowPlaying);
        ArrayList<Player.COLR> colorList = new ArrayList<Player.COLR>();
        if(redPlaying.isChecked()){
            colorList.add(Player.COLR.RED);
        }
        if(greenPlaying.isChecked()){
            colorList.add(Player.COLR.GREEN);
        }
        if(bluePlaying.isChecked()){
            colorList.add(Player.COLR.BLUE);
        }
        if(yellowPlaying.isChecked()){
            colorList.add(Player.COLR.YELLOW);
        }
        if(colorList.size()==0){
            longMsg("Can't start game. No player selected. ");
            return;
        }
        LinearLayout calibrationLayout = (LinearLayout) findViewById(R.id.calibrationLayout);
        lockControls(redPlaying,greenPlaying,bluePlaying,yellowPlaying,calibrationLayout,((Button)view));
        game = new Game(colorList);
        diceRolled = false;
        if(!game.getErrorWhileInitializing().equals("")){
            longMsg(game.getErrorWhileInitializing());
            return;
        }
        changeTurn(false);
    }

    private void changeTurn(boolean switchPlayer){
        if(switchPlayer) {
            game.switchPlayer();
        }
        whoseTurn = game.getCurrentPlayer();
        longMsg(whoseTurn.getColor()+"'s turn");
        ((Button)findViewById(R.id.start)).setBackgroundColor(game.getCurrentPlayerColor());
        rollLocked = false;
        diceRolled = false;
    }

    HashMap<String,Integer> playerPos = new HashMap<String,Integer>();
    HashMap<String,Player> playerPos2 = new HashMap<String,Player>();

    public void takeTurn(View view){
        if(game==null){
            longMsg("Game not yet started. Please start game first.");
            return;
        }
        TextView subplayer = (TextView) view;
        if(subplayer.getText().toString().charAt(0)==whoseTurn.getColor().toLowerCase().charAt(0)){
            if(diceRolled==false){
                longMsg("Dice not rolled yet. Please roll the dice first.");
                return;
            } else {
                TextView dice = (TextView) findViewById(R.id.dice);
                int diceVal = Integer.parseInt(dice.getText().toString());
                String newPos = whoseTurn.movePlayerBy(Integer.parseInt(((TextView) view).getText().charAt(1)+""),diceVal);
                if(whoseTurn.isStepUsed()){
                    //longMsg("new Position is :"+newPos);
                    if(whoseTurn.needToMove()) {
                        if(playerPos.get(newPos)!=null){
                            TextView previousPlayerOnSamePos = (TextView) findViewById(playerPos.get(newPos));
                            Player oldPlayer = playerPos2.get(newPos);
                            if(!whoseTurn.getColor().equals(oldPlayer.getColor())){
                                String coor0 = initPos.get(previousPlayerOnSamePos.getText().toString());
                                String[] coor0Split = coor0.split(",");
                                int xVal = Integer.parseInt(coor0Split[0]);
                                int yVal = Integer.parseInt(coor0Split[1]);
                                translateView(previousPlayerOnSamePos, xVal, yVal);
                                oldPlayer.returnPlayerToBase(previousPlayerOnSamePos.getText().toString());
                            }
                        }
                        playerPos.put(newPos,subplayer.getId());
                        playerPos2.put(newPos,whoseTurn);
                        translateView(subplayer, Coordinates.getX(newPos), Coordinates.getY(newPos));
                    }
                    if(diceVal==6){
                        longMsg("second move for "+whoseTurn.getColor());
                        rollLocked = false;
                        diceRolled = false;
                    } else {
                        changeTurn(true);
                    }
                } else {
                    longMsg("Illegal move.Try again");
                }
            }
        } else {
            longMsg("Illegal move attempted by "+subplayer.getText()+". It's "+whoseTurn.getColor()+"'s turn.");
        }
    }

    private boolean rollLocked = false;

    public void rollDice(View view) {
        if(!rollLocked){
            int val = Dice.roll();
            ((TextView)view).setRotation(0f);
            ((TextView)view).setAlpha(0f);
            ((TextView)view).animate().alpha(1f).rotation(360f).setDuration(800);
            ((TextView)view).setText(val+"");
            if(val==6){
                ((TextView)view).setTextColor(Color.rgb(200,30,30));
            } else {
                ((TextView)view).setTextColor(Color.rgb(30,40,50));
            }
            if(game!=null){
                if(!whoseTurn.isAnyPlayerOut() && val!=6){
                    changeTurn(true);
                } else {
                    int[] pos = whoseTurn.getPositions();
                    int validMoveCount = 0;
                    for(int i=0;i<4;i++){
                        if(pos[i]==-1){
                            if(val==6){
                                validMoveCount++;
                            }
                        } else {
                            if(pos[i]+val<pos[4]){
                                validMoveCount++;
                            }
                        }
                    }
                    if(validMoveCount==0){
                        changeTurn(true);
                    } else {
                        longMsg(validMoveCount+" valid moves.");
                        rollLocked = true;
                    }
                }
            }
            diceRolled = true;
        } else {
            longMsg("Rolling is locked until player moves");
        }
    }
}

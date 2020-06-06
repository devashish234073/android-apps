package com.devashish.currencyconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActionCodeSettings actionCodeSettings = null;
    private FirebaseAuth mAuth = null;
    private Hashtable<Integer,String> ticTacIds = new Hashtable<Integer,String>();
    private TicTacToe ticTacToe = null;
    private ArrayList<Integer> ticTacIndexes = new ArrayList<Integer>();

    private int xW = 0;
    private int oW = 0;
    private int totalW = 0;

    private boolean toastEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void updateWinLabels(){
        TextView xWin = (TextView) findViewById(R.id.xWin);
        TextView oWin = (TextView) findViewById(R.id.oWin);
        TextView totalGames = (TextView) findViewById(R.id.totalGames);
        setTextFromAnotherThread(xWin,"X: " + xW);
        setTextFromAnotherThread(oWin,"O: " + oW);
        setTextFromAnotherThread(totalGames,"T: " + totalW);
        /*xWin.setText("X: " + xW);
        oWin.setText("O: " + oW);
        totalGames.setText("T: " + totalW);*/
    }

    private void updateUserLabel(){
        if(mAuth==null) {
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView topLabel = (TextView) findViewById(R.id.topLabel);
        if(currentUser!=null){
            topLabel.setText("Logged In as: " + currentUser.getEmail());
            topLabel.setBackgroundColor(Color.rgb(100,200,100));
        } else {
            topLabel.setText("Logged In as: guest");
            topLabel.setBackgroundColor(Color.rgb(190,190,190));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUserLabel();
        initTicTacToe();
    }

    public void addLog(String data){
        /*TextView logView = (TextView) findViewById(R.id.logView);
        if(logView.getText().toString().equals("")){
            logView.setText(data);
        } else {
            logView.setText(logView.getText().toString()+"\r\n"+data);
        }*/
    }

    private void initTicTacToe(){
        ticTacIds.put(R.id.a11,"11");
        ticTacIds.put(R.id.a12,"12");
        ticTacIds.put(R.id.a13,"13");

        ticTacIds.put(R.id.a21,"21");
        ticTacIds.put(R.id.a22,"22");
        ticTacIds.put(R.id.a23,"23");

        ticTacIds.put(R.id.a31,"31");
        ticTacIds.put(R.id.a32,"32");
        ticTacIds.put(R.id.a33,"33");

        for(Integer id : ticTacIds.keySet()) {
            ticTacIndexes.add(id);
        }
    }

    public void resetTicTacToe(View view) {
        ticTacToe = null;
        for(int id : ticTacIds.keySet()){
            TextView tv = (TextView) findViewById(id);
            //tv.animate().rotation(0f).setDuration(200);
            //tv.setBackgroundColor(Color.rgb(14,100,196));
            rotateFromAnotherThread(tv,0f,200);
            setBackgroundFromAnotherThread(tv,14,100,196);
            setTextFromAnotherThread(tv,"");
        }
    }

    private void longToast(String message){
        if(toastEnabled) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    private void shortToast(String message){
        if(toastEnabled) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void rotateFromAnotherThread(final TextView view,final float angle,final int duration) {
        synchronized(view){
            try {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.animate().rotation(angle).setDuration(duration);
                    }
                });
            } catch (Exception e) {

            }
        }
    }

    private void setTextFromAnotherThread(final TextView view,final String txt) {
        synchronized(view){
            try {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setText(txt);
                    }
                });
            } catch(Exception e) {

            }
        }
    }

    private void setBackgroundFromAnotherThread(final View view,final int r,final int g,final int b){
        synchronized (view){
            try {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(Color.rgb(r,g,b));
                    }
                });
            } catch(Exception e) {

            }
        }
    }

    public void autoPlay(final View view){
        longToast("will play 10 times");
        toastEnabled = false;
        Thread t = new Thread(new Runnable(){
            public void run(){
                for(int i=0;i<10;i++){
                    resetTicTacToe(view);
                    ArrayList<Integer> idList = (ArrayList<Integer>)ticTacIndexes.clone();
                    Random r = new Random();
                    int selectedIndex = r.nextInt(idList.size());
                    TextView tttLabel = (TextView) findViewById(idList.get(selectedIndex));
                    plyTicTacToe(tttLabel);
                    idList.remove(selectedIndex);
                    while (!ticTacToe.isGameOver()){
                        selectedIndex = r.nextInt(idList.size());
                        tttLabel = (TextView) findViewById(idList.get(selectedIndex));
                        plyTicTacToe(tttLabel);
                        idList.remove(selectedIndex);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                toastEnabled = true;
            }
        });
        t.start();
    }

    public void plyTicTacToe(View view){
        TextView tv = (TextView) view;
        String coordinate = ticTacIds.get(tv.getId());
        try {
            //tv.animate().rotation(360.0f).setDuration(200);
            rotateFromAnotherThread(tv,360.0f,200);
            if (ticTacToe == null) {
                ticTacToe = new TicTacToe();
            } else if (ticTacToe.isGameOver()) {
                longToast("Game Over! Reset the game first");
                return;
            }
            char charPlayed = ticTacToe.playAt(coordinate);
            if (charPlayed != 'T') {
                //tv.setText(charPlayed + "");
                setTextFromAnotherThread(tv,charPlayed + "");
            }
            if(ticTacToe.getPlayerWon()!=null){
                String[] patternMatched = ticTacToe.getPatternMatched();
                for(int id: ticTacIds.keySet()){
                    if(ticTacIds.get(id).equals(patternMatched[0])){
                        TextView tv1tmp = (TextView) findViewById(id);
                        //tv1tmp.setBackgroundColor(Color.rgb(0,255,0));
                        setBackgroundFromAnotherThread(tv1tmp,0,255,0);
                    } else if(ticTacIds.get(id).equals(patternMatched[1])){
                        TextView tv1tmp = (TextView) findViewById(id);
                        //tv1tmp.setBackgroundColor(Color.rgb(0,255,0));
                        setBackgroundFromAnotherThread(tv1tmp,0,255,0);
                    } else if(ticTacIds.get(id).equals(patternMatched[2])){
                        TextView tv1tmp = (TextView) findViewById(id);
                        //tv1tmp.setBackgroundColor(Color.rgb(0,255,0));
                        setBackgroundFromAnotherThread(tv1tmp,0,255,0);
                    }
                }
                if(ticTacToe.getPlayerWon()=='X'){
                    xW++;
                } else if(ticTacToe.getPlayerWon()=='O'){
                    oW++;
                }
                longToast("player "+ticTacToe.getPlayerWon()+" won");
                updateWinLabels();
            }
            if(ticTacToe.isGameOver()){
                totalW++;
                updateWinLabels();
            }
        } catch(Exception e){
            longToast(e.toString());
        }
    }

    public void signIn(View view){
        if(mAuth==null) {
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            longToast( "Can not sing in. Please sign out current user:"+currentUser.getEmail());
            return;
        }
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        String user = username.getText().toString().trim().toLowerCase();
        String pass = password.getText().toString().trim().toLowerCase();
        if(user.equals("") || pass.equals("")){
            longToast("username or password can not be empty");
            return;
        }
        if(user.equals("guest")){
            longToast("cant sign in with 'guest' username");
            return;
        }
        if(user.indexOf("@")==-1){
            longToast("username must be an email Id");
            return;
        }
        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Login Success",
                            Toast.LENGTH_LONG).show();
                    addLog("login success");
                    updateUserLabel();
                    username.setText("");
                    password.setText("");
                } else {
                    // If sign in fails, display a message to the user.
                    longToast("Authentication failed.");
                }

                // ...
            }
        });
    }

    public void signOut(View view){
        if(mAuth==null) {
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            shortToast("User :"+currentUser.getEmail() + " signed out successfully");
            mAuth.signOut();
            updateUserLabel();
        }
    }

    public void signUp(final View view){
        if(mAuth==null) {
            mAuth = FirebaseAuth.getInstance();
        }
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            longToast("Can not sign up. Please sign out current user:"+currentUser.getEmail());
            return;
        }
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        String user = username.getText().toString().trim().toLowerCase();
        String pass = password.getText().toString().trim().toLowerCase();
        if(user.equals("") || pass.equals("")){
            longToast("username or password can not be empty");
            return;
        }
        if(user.equals("guest")){
            longToast("'guest' username can not be created");
            return;
        }
        if(user.indexOf("@")==-1){
            longToast("username must be an email Id");
            return;
        }
        mAuth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            longToast("a new user created");
                            updateUserLabel();
                            username.setText("");
                            password.setText("");
                        } else {
                            // If sign in fails, display a message to the user.
                            longToast("User creation failed.");
                        }

                        // ...
                    }
                });
    }

    public void btnClick(View view){
        EditText firstText = (EditText) findViewById(R.id.firstText);
        String msg = firstText.getText().toString();
        if(msg.equals("")){
            longToast("enter some text to show here");
            Log.i("btn","btnClicked");
        } else {
            longToast(msg);
            Log.i("btn","btnClicked");
        }
        /*if(actionCodeSettings == null){
            actionCodeSettings =
                    ActionCodeSettings.newBuilder()
                            .setHandleCodeInApp(true)
                            .setAndroidPackageName(
                                    "com.devashish.currencyconverter",
                                    true,
                                    "12"    )
                           .build();
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendSignInLinkToEmail("devashish234073@gmail.com", actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"Email Sent",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }
}

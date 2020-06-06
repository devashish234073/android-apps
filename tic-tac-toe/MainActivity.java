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

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    private ActionCodeSettings actionCodeSettings = null;
    private FirebaseAuth mAuth = null;
    private Hashtable<Integer,String> ticTacIds = new Hashtable<Integer,String>();
    private TicTacToe ticTacToe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    public void resetTicTacToe(View view) {
        ticTacToe = null;
        for(int id : ticTacIds.keySet()){
            TextView tv = (TextView) findViewById(id);
            tv.animate().rotation(0f).setDuration(200);
            tv.setBackgroundColor(Color.rgb(14,100,196));
            tv.setText("");
        }
    }

    private void longToast(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }

    private void shortToast(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    public void plyTicTacToe(View view){
        TextView tv = (TextView) view;
        String coordinate = ticTacIds.get(tv.getId());
        try {
            tv.animate().rotation(360.0f).setDuration(200);
            if (ticTacToe == null) {
                ticTacToe = new TicTacToe();
            } else if (ticTacToe.isGameOver()) {
                longToast("Game Over! Reset the game first");
                return;
            }
            char charPlayed = ticTacToe.playAt(coordinate);
            if (charPlayed != 'T') {
                tv.setText(charPlayed + "");
            }
            if(ticTacToe.getPlayerWon()!=null){
                String[] patternMatched = ticTacToe.getPatternMatched();
                for(int id: ticTacIds.keySet()){
                    if(ticTacIds.get(id).equals(patternMatched[0])){
                        TextView tv1tmp = (TextView) findViewById(id);
                        tv1tmp.setBackgroundColor(Color.rgb(0,255,0));
                    } else if(ticTacIds.get(id).equals(patternMatched[1])){
                        TextView tv1tmp = (TextView) findViewById(id);
                        tv1tmp.setBackgroundColor(Color.rgb(0,255,0));
                    } else if(ticTacIds.get(id).equals(patternMatched[2])){
                        TextView tv1tmp = (TextView) findViewById(id);
                        tv1tmp.setBackgroundColor(Color.rgb(0,255,0));
                    }
                }
                longToast("player "+ticTacToe.getPlayerWon()+" won");
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

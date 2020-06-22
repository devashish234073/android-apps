package com.devashish.ludo;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private int playerCount = 4;
    private HashMap<Player.COLR,Player> players = new HashMap<Player.COLR,Player>();
    private ArrayList<Player.COLR> colorSequence = new ArrayList<Player.COLR>();
    private int currentPlayerIndex;
    private String error = "";

    public Game(ArrayList<Player.COLR> col){
        if(col.size()<=4){
            playerCount = col.size();
        }
        try {
            for (int i = 0; i < col.size(); i++) {
                players.put(col.get(i), new Player(col.get(i)));
                colorSequence.add(col.get(i));
                if (i == 0) {
                    currentPlayerIndex = 0;
                }
            }
        } catch(Exception e){
            error = e.toString()+"["+e.getLocalizedMessage()+"] ["+e.getCause()+"]";
        }
    }

    public String getErrorWhileInitializing(){
        return error;
    }

    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }

    public int getCurrentPlayerColor(){
        if(colorSequence.get(currentPlayerIndex)== Player.COLR.RED){
            return Color.rgb(255,0,0);
        } else if(colorSequence.get(currentPlayerIndex)== Player.COLR.BLUE){
            return Color.rgb(0,0,180);
        } else if(colorSequence.get(currentPlayerIndex)== Player.COLR.YELLOW){
            return Color.rgb(200,200,0);
        } else {
            return Color.rgb(10,170,10);
        }
    }

    public Player getCurrentPlayer(){
        return players.get(colorSequence.get(currentPlayerIndex));
    }

    public void switchPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1)%playerCount;
    }

    public boolean isPlaying(Player.COLR colr){
        return players.get(colr)!=null;
    }
}

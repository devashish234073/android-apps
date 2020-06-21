package com.devashish.ludo;


import java.util.ArrayList;

public class Game {
    private int playerCount = 4;
    private ArrayList<Player.COLR> playerPlaying = new ArrayList<Player.COLR>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private int currentPlayerIndex;

    public Game(Player.COLR col[]){
        if(col.length<=4){
            playerCount = col.length;
        }
        for(int i=0;i<col.length;i++){
            playerPlaying.add(col[i]);
            players.add(new Player(col[i]));
            if(i==0){
                currentPlayerIndex = 0;
            }
        }
    }

    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }

    public String getCurrentPlayerColor(){
        return players.get(currentPlayerIndex).getColor().name();
    }

    public boolean isPlaying(Player.COLR colr){
        return playerPlaying.indexOf(colr)!=-1;
    }
}

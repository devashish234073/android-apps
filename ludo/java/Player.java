package com.devashish.ludo;

import java.util.ArrayList;

public class Player {
    enum COLR{GREEN,RED,BLUE,YELLOW};
    private COLR colr;
    private char col_char = '\0';
    private static ArrayList<String> default_sequence = new ArrayList<String>();
    private ArrayList<String> sequence = new ArrayList<String>();
    private ArrayList<String> path;
    private int playerPos[] = {0,0,0,0};
    private int stepsWon = 0;

    static {
        default_sequence.add("r");
        default_sequence.add("g");
        default_sequence.add("y");
        default_sequence.add("b");
    }

    public Player(COLR colr){
        this.colr = colr;
        if(this.colr==COLR.GREEN){
            col_char = 'g';
        } else if(this.colr==COLR.RED){
            col_char = 'r';
        } else if(this.colr==COLR.BLUE){
            col_char = 'b';
        } else if(this.colr==COLR.YELLOW){
            col_char = 'y';
        }
        computeSequenceAndPath();
    }

    public COLR getColor(){
        return colr;
    }

    private String playPlayer(int playerNum,int steps){
        this.stepsWon = steps;
        if(playerNum<4 && playerNum>=0){
            if(playerPos[playerNum]!=path.size()-1){
                if(playerPos[playerNum]==0 && steps==6){
                    playerPos[playerNum] = 1;
                    this.stepsWon = 0;
                } else {
                    if(steps<=6 && steps>0 && playerPos[playerNum]+steps<path.size()){
                        playerPos[playerNum]+=steps;
                        this.stepsWon = 0;
                    }
                }
            }
        }
        return path.get(playerPos[playerNum]);
    }

    public boolean areStepsWonUsed(){
        return this.stepsWon == 0;
    }

    private void computeSequenceAndPath() {
        if(sequence.size()==0){
            int startPos = default_sequence.indexOf(col_char);
            for(int i=0;i<=3;i++){
                int currPos = (startPos + i) % 4;
                sequence.add(default_sequence.get(currPos));
            }
            path = new ArrayList<String>();
            path.add("");
            for(int i=0;i<sequence.size();i++){
                String currChar = sequence.get(i);
                int MAX = 11;
                if(i==sequence.size()-1){
                    MAX = 10;
                }
                for(int j=0;j<=MAX;j++){
                    path.add(currChar+j);
                }
            }
            path.add((col_char+"").toUpperCase()+1);
            path.add((col_char+"").toUpperCase()+2);
            path.add((col_char+"").toUpperCase()+3);
            path.add((col_char+"").toUpperCase()+4);
            path.add((col_char+"").toUpperCase()+"_WON");
        }
    }
}

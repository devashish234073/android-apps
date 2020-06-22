package com.devashish.ludo;

import android.util.Log;

import java.util.ArrayList;

public class Player {
    enum COLR{GREEN,RED,BLUE,YELLOW};
    private COLR colr;
    private ArrayList<String> path = null;
    private String color_char = "";
    private String[] subPlayerPosition = {"0","0","0","0"};
    private ArrayList<String> pathSequence = new ArrayList<String>();
    boolean stepused = true;
    boolean needToMove = false;

    public String getSubPlayerIndex(int indx){
        return color_char+indx;
    }

    public String[] getPositionsStr() {
        String[] pos = new String[4];
        pos[0] = subPlayerPosition[0];
        pos[1] = subPlayerPosition[1];
        pos[2] = subPlayerPosition[2];
        pos[3] = subPlayerPosition[3];
        return pos;
    }

    public int[] getPositions(){
        int[] pos = new int[5];
        pos[0] = path.indexOf(subPlayerPosition[0]);
        pos[1] = path.indexOf(subPlayerPosition[1]);
        pos[2] = path.indexOf(subPlayerPosition[2]);
        pos[3] = path.indexOf(subPlayerPosition[3]);
        pos[4] = path.size();
        return pos;
    }

    public void returnPlayerToBase(String playerId){
        int indx = Integer.parseInt(playerId.charAt(1)+"");
        subPlayerPosition[indx] = "0";
    }


    public Player(COLR colr){
        this.colr = colr;
        path = new ArrayList<String>();
        color_char = colr.name().toLowerCase().charAt(0)+"";
        pathSequence.add("r");
        pathSequence.add("g");
        pathSequence.add("y");
        pathSequence.add("b");
        computePath();
        Log.d(colr.name().toLowerCase()+"'s init","Col="+colr.name()+",char At 0:"+colr.name().toLowerCase().charAt(0)+",computed="+color_char+",path:"+path);
    }

    public boolean isStepUsed(){
        return stepused;
    }

    public boolean needToMove(){
        return needToMove;
    }

    public boolean isAnyPlayerOut() {
        if(subPlayerPosition[0].equals("0") && subPlayerPosition[1].equals("0") && subPlayerPosition[2].equals("0") && subPlayerPosition[3].equals("0")){
            return false;
        }
        return true;
    }

    public String movePlayerBy(int subPlayerIndex,int steps){
        stepused = false;
        needToMove = false;
        if(subPlayerPosition[0].equals("0") && subPlayerPosition[1].equals("0") && subPlayerPosition[2].equals("0") && subPlayerPosition[3].equals("0") && steps!=6){
            stepused = true;
            return subPlayerPosition[subPlayerIndex];
        }
        if(subPlayerPosition[subPlayerIndex].equals("0") && steps!=6){
            return subPlayerPosition[subPlayerIndex];
        }
        int playerPositionIndex = path.indexOf(subPlayerPosition[subPlayerIndex]);
        if(subPlayerIndex<4 && subPlayerIndex>=0){
            if(subPlayerPosition[subPlayerIndex]!=path.get(path.size()-1)){
                if(subPlayerPosition[subPlayerIndex].equals("0") && steps==6){
                    subPlayerPosition[subPlayerIndex] = path.get(0);
                    stepused = true;
                    needToMove = true;
                    Log.d("Player "+colr.name(),"moved 6 , new position: "+path.get(0));
                    return path.get(0);
                } else {
                    if(steps<=6 && steps>0 && playerPositionIndex+steps<path.size()){
                        playerPositionIndex+=steps;
                        subPlayerPosition[subPlayerIndex] = path.get(playerPositionIndex);
                        stepused = true;
                        needToMove = true;
                        Log.d("Player "+colr.name(),"moved "+steps+", new position: "+path.get(playerPositionIndex));
                        return path.get(playerPositionIndex);
                    }
                }
            }
        }
        return subPlayerPosition[subPlayerIndex];
    }

    public String getColor(){
        return colr.name();
    }

    private void computePath() {
        Log.d("COMPUTE: "+colr.name(),"color_char="+color_char+",appending to 0"+(color_char+"0"));
        int colIndx = pathSequence.indexOf(color_char);
        int iterationCount = 0;
        for(String c = color_char;iterationCount<4;c=pathSequence.get(colIndx%4)){
            iterationCount++;
            int MAX = 12;
            if(iterationCount==4){
                MAX = 11;
            }
            for(int i=0;i<=MAX;i++) {
                path.add(c+""+i);
            }
            colIndx=colIndx+1;
        }
        path.add(color_char.toUpperCase()+"1");
        path.add(color_char.toUpperCase()+"2");
        path.add(color_char.toUpperCase()+"3");
        path.add(color_char.toUpperCase()+"4");
        path.add(color_char.toUpperCase()+"_WIN");
    }

}

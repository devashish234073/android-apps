package com.devashish.currencyconverter;

import java.util.Hashtable;

public class TicTacToe {
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private Character playerWon = null;
    private int filledCount = 0;
    private String[] patternMatched = new String[3];

    private Hashtable<String,Character> board = new Hashtable<String,Character>();

    private boolean isValidCoordinate(String coordinate){
        if(coordinate.length()!=2){
            return false;
        }
        if(coordinate.startsWith("1") || coordinate.startsWith("2") || coordinate.startsWith("3")){
            if(coordinate.endsWith("1") || coordinate.endsWith("2") || coordinate.endsWith("3")){
                return true;
            }
        }
        return false;
    }

    private void switchPlayer(){
        if(currentPlayer=='X'){
            currentPlayer = 'O';
        } else if(currentPlayer=='O'){
            currentPlayer = 'X';
        }
    }

    private void analyzeWin(){
        if(board.get("11")!=null && board.get("11")==board.get("12") && board.get("11")==board.get("13")){
            playerWon = board.get("11");
            patternMatched[0]="11";
            patternMatched[1]="12";
            patternMatched[2]="13";
            gameOver = true;
        } else if(board.get("11")!=null && board.get("11")==board.get("21") && board.get("11")==board.get("31")){
            playerWon = board.get("11");
            patternMatched[0]="11";
            patternMatched[1]="21";
            patternMatched[2]="31";
            gameOver = true;
        } else if(board.get("11")!=null && board.get("11")==board.get("22") && board.get("11")==board.get("33")){
            playerWon = board.get("11");
            patternMatched[0]="11";
            patternMatched[1]="22";
            patternMatched[2]="33";
            gameOver = true;
        } else if(board.get("33")!=null && board.get("33")==board.get("23") && board.get("33")==board.get("13")){
            playerWon = board.get("33");
            patternMatched[0]="13";
            patternMatched[1]="23";
            patternMatched[2]="33";
            gameOver = true;
        } else if(board.get("33")!=null && board.get("33")==board.get("32") && board.get("33")==board.get("31")){
            playerWon = board.get("33");
            patternMatched[0]="31";
            patternMatched[1]="32";
            patternMatched[2]="33";
            gameOver = true;
        } else if(board.get("22")!=null && board.get("22")==board.get("31") && board.get("22")==board.get("13")){
            playerWon = board.get("22");
            patternMatched[0]="31";
            patternMatched[1]="22";
            patternMatched[2]="13";
            gameOver = true;
        } else if(board.get("22")!=null && board.get("22")==board.get("12") && board.get("22")==board.get("32")){
            playerWon = board.get("22");
            patternMatched[0]="12";
            patternMatched[1]="22";
            patternMatched[2]="32";
            gameOver = true;
        } else if(board.get("22")!=null && board.get("22")==board.get("21") && board.get("22")==board.get("23")){
            playerWon = board.get("22");
            patternMatched[0]="21";
            patternMatched[1]="22";
            patternMatched[2]="23";
            gameOver = true;
        }
        if(filledCount==9){
            gameOver = true;
        }
    }

    public String[] getPatternMatched() {
        return patternMatched;
    }

    public char playAt(String coordinate){
        char ret = 'T';
        if(!isValidCoordinate(coordinate)){
            return 'T';
        }
        if(!gameOver){
            if(board.get(coordinate)==null){
                filledCount++;
                board.put(coordinate,currentPlayer);
                ret = currentPlayer;
                switchPlayer();
                analyzeWin();
            }
        }
        return ret;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public Character getPlayerWon(){
        if(playerWon==null){
            return null;
        }
        return playerWon.charValue();
    }
}

package com.devashish.ludo;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Coordinates {
    private static HashMap<String,Integer> xs = new HashMap<String,Integer>();
    private static HashMap<String,Integer> ys = new HashMap<String,Integer>();

    public static int getX(String boxId){
        return  xs.get(boxId);
    }
    public static int getY(String boxId){
        return ys.get(boxId);
    }

    public static void setXY(String boxId,int x,int y){
        xs.put(boxId,x);
        ys.put(boxId,y);
    }

    public static String calibrate(){
        String s = "";
        if(xs.get("r0")!=null && xs.get("r1")!=null && xs.get("r5")!=null) {
            int yDiff =  getY("r0") - getY("r1");
            int xDiff =  getX("r0") - getX("r5");
            s="xDiff: "+getX("r0")+"-"+getX("r5")+"="+xDiff;
            s+="yDiff: "+getY("r0")+"-"+getY("r5")+"="+yDiff;
            int refX = xs.get("r0");
            int refY = ys.get("r0");
            setXY("R1",refX+xDiff,refY);
            setXY("R2",refX+xDiff,refY-yDiff);
            setXY("R3",refX+xDiff,refY-2*yDiff);
            setXY("R4",refX+xDiff,refY-3*yDiff);
            setXY("R5",refX+xDiff,refY-4*yDiff);
            setXY("R_WIN",refX+xDiff,refY-5*yDiff);
            setXY("r2",refX,refY-2*yDiff);//UP (-y)
            setXY("r3",refX,refY-3*yDiff);//UP (-y)
            setXY("r4",refX,refY-4*yDiff);//UP (-y)
            setXY("r5",refX-xDiff,refY-5*yDiff);//LEFT UP (-y -x)
            setXY("r6",refX-2*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("r7",refX-3*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("r8",refX-4*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("r9",refX-5*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("r10",refX-6*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("r11",refX-6*xDiff,refY-6*yDiff);//UP  (-y)
            setXY("r12",refX-6*xDiff,refY-7*yDiff);//UP  (-y)
            //GREEN
            setXY("G1",refX-5*xDiff,refY-6*yDiff);
            setXY("G2",refX-4*xDiff,refY-6*yDiff);
            setXY("G3",refX-3*xDiff,refY-6*yDiff);
            setXY("G4",refX-2*xDiff,refY-6*yDiff);
            setXY("G5",refX-xDiff,refY-6*yDiff);
            setXY("G_WIN",refX,refY-6*yDiff);
            setXY("g0",refX-5*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("g1",refX-4*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("g2",refX-3*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("g3",refX-2*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("g4",refX-xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("g5",refX,refY-8*yDiff);//RIGHT UP (+x -y)
            setXY("g6",refX,refY-9*yDiff);//UP (-y)
            setXY("g7",refX,refY-10*yDiff);//UP (-y)
            setXY("g8",refX,refY-11*yDiff);//UP (-y)
            setXY("g9",refX,refY-12*yDiff);//UP (-y)
            setXY("g10",refX,refY-13*yDiff);//UP (-y)
            setXY("g11",refX+xDiff,refY-13*yDiff);//RIGHT (+x)
            setXY("g12",refX+2*xDiff,refY-13*yDiff);//RIGHT (+x)
            //YELLOW
            setXY("Y1",refX+xDiff,refY-12*yDiff);
            setXY("Y2",refX+xDiff,refY-11*yDiff);
            setXY("Y3",refX+xDiff,refY-10*yDiff);
            setXY("Y4",refX+xDiff,refY-9*yDiff);
            setXY("Y5",refX+xDiff,refY-8*yDiff);
            setXY("Y_WIN",refX+xDiff,refY-7*yDiff);
            setXY("y0",refX+2*xDiff,refY-12*yDiff);//DOWN (+y)
            setXY("y1",refX+2*xDiff,refY-11*yDiff);//DOWN (+y)
            setXY("y2",refX+2*xDiff,refY-10*yDiff);//DOWN (+y)
            setXY("y3",refX+2*xDiff,refY-9*yDiff);//DOWN (+y)
            setXY("y4",refX+2*xDiff,refY-8*yDiff);//DOWN (+y)
            setXY("y5",refX+3*xDiff,refY-7*yDiff);//RIGHT DOWN (+x +y)
            setXY("y6",refX+4*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("y7",refX+5*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("y8",refX+6*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("y9",refX+7*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("y10",refX+8*xDiff,refY-7*yDiff);//RIGHT (+x)
            setXY("y11",refX+8*xDiff,refY-6*yDiff);//RIGHT DOWN (+x +y)
            setXY("y12",refX+8*xDiff,refY-5*yDiff);//DOWN (+y)
            //BLUE
            setXY("B1",refX+7*xDiff,refY-6*yDiff);
            setXY("B2",refX+6*xDiff,refY-6*yDiff);
            setXY("B3",refX+5*xDiff,refY-6*yDiff);
            setXY("B4",refX+4*xDiff,refY-6*yDiff);
            setXY("B5",refX+3*xDiff,refY-6*yDiff);
            setXY("B_WIN",refX+2*xDiff,refY-6*yDiff);
            setXY("b0",refX+7*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("b1",refX+6*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("b2",refX+5*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("b3",refX+4*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("b4",refX+3*xDiff,refY-5*yDiff);//LEFT (-x)
            setXY("b5",refX+2*xDiff,refY-4*yDiff);//LEFT DOWN (-x +y)
            setXY("b6",refX+2*xDiff,refY-3*yDiff);//DOWN (+y)
            setXY("b7",refX+2*xDiff,refY-2*yDiff);//DOWN (+y)
            setXY("b8",refX+2*xDiff,refY-yDiff);//DOWN (+y)
            setXY("b9",refX+2*xDiff,refY);//DOWN (+y)
            setXY("b10",refX+2*xDiff,refY+yDiff);//DOWN (+y)
            setXY("b11",refX+xDiff,refY+yDiff);//LEFT (-x)
            setXY("b12",refX,refY+yDiff);//LEFT (-x)
        }
        return s;
    }
}

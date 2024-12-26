package org.firstinspires.ftc.teamcode;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class BooleanToggler {
    BooleanSupplier bool;
    TTimer timer;
    double delay;
    boolean prevVal = false;
    boolean toggleState = false;
    public BooleanToggler(BooleanSupplier bool){
        //timer = new TTimer(time);
        this.bool = bool;
        //this.delay = delay;
    }
    public void updateValue(){
        if(bool.getAsBoolean()&&!prevVal){
            toggleState = !toggleState;
        }
        prevVal=bool.getAsBoolean();
    }
    public boolean getState(){
        return toggleState;
    }
}

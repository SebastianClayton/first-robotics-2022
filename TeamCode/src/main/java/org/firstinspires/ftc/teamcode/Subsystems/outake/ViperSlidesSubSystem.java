package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.NullAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.ExtraMath;
import org.firstinspires.ftc.teamcode.Subsystems.ActionDcMotor;

public class ViperSlidesSubSystem{
    public ActionDcMotor l;
    public ActionDcMotor r;
    public TouchSensor LimitSwitch;

    public boolean disabled = false;
    private final int LMaxPos = -4000;
    private final int LMinPos = 0;
    private final int RMaxPos = 4000;
    private final int RMinPos = 0;
    private double posCoefficient = 0.03;//0.05<-original, worked decently
    private double downTolerance = 10, downWaitTime = 1;
    public ViperSlidesSubSystem(HardwareMap hardwareMap){
         l = new ActionDcMotor(hardwareMap,"LeftViper",0,-3500,posCoefficient);
         r = new ActionDcMotor(hardwareMap,"RightViper",0,3500,posCoefficient);
    }
    public boolean targetReached(){
        return l.targetReached()&&r.targetReached();
    }
    public double DistanceToTarget(){
        return l.getDistanceToTarget();
    }
    public double GetTgtPos(){
        return l.getTargetPos();
    }
    public Action Up() {
        if(disabled){
            return new NullAction();
        }
        else
            return new ParallelAction(l.GoToPos(1,0),r.GoToPos(1,0));
    }

    public Action Down() {
        if(disabled){
            return new NullAction();
        }
        else
            return new ParallelAction(l.GoToPosButIfStoppedAssumePosHasBeenReached(0,downTolerance,downWaitTime),r.GoToPosButIfStoppedAssumePosHasBeenReached(0,downTolerance,downWaitTime));
    }

    public boolean isDown(){
        return ExtraMath.ApproximatelyEqualTo(GetTgtPos(),0,50);
    }

    //region unused
    public Action TgtPosUp(){
        return new ParallelAction(l.new SetTgtPos(1,0),r.new SetTgtPos(1,0));
    }
    public Action TgtPosDown(){
        return new ParallelAction(l.new SetTgtPos(0,0),r.new SetTgtPos(0,0));
    }
    //endregion
}

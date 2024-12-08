package org.firstinspires.ftc.teamcode.Subsystems.outake;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public PrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    public Outtake(HardwareMap hardwareMap){
        claw = new OuttakeClaw(hardwareMap);
        pivot1 = new PrimaryOuttakePivot(hardwareMap);
        pivot2 = new SecondaryOuttakePivot(hardwareMap);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }

    /**
     * this function is meant to be looped
     */
    public void Up(){
        pivot1.Up();
        pivot2.Up();
    }
    public void Down(){
        pivot1.Down();
        pivot2.Down();
    }
    public void BasketDropping(){
        //this is meant to be looped
        if(vipers.targetReached()){
            Up();
            //TODO: probably add a sleep here, need to make a class for sleeping in a loop(everything is more complicated in a loop)
            //^ 12/6 I just did this not sure if its fixed though
            if(claw.claw.targetReached()){
                claw.open();
                Down();
                vipers.SetTgPosToRetract();
            }
        }
        else {
            vipers.SetTgPosToExtend();
        }
    }
    public void goToDefaultPos(){
        vipers.SetTgPosToRetract();
        claw.close();
    }
}

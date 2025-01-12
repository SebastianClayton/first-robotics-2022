package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

import java.util.function.DoubleSupplier;

public class Wrist{
    public ActionServo wrist;
    public Wrist(HardwareMap hardwareMap, DoubleSupplier time){
        wrist = new ActionServo(hardwareMap,"wrist",0,0.75,time,270);
    }
    public Action runToDegrees(double angle){
        return wrist.runToDegrees(angle);
    }
    public Action runToRad(double angle){
        return wrist.runToRad(angle);
    }
    public Action runToRatio(double ratio){
        return wrist.runToRatio(ratio);
    }
}

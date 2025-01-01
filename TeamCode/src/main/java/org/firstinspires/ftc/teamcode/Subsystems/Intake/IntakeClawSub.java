package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.Subsystems.ActionServo;
import org.firstinspires.ftc.teamcode.Subsystems.ServoSub;

import java.util.function.DoubleSupplier;

public class IntakeClawSub extends SubsystemBase {
    public ActionServo SERVO;


    public IntakeClawSub(HardwareMap hardwareMap, DoubleSupplier time) {
        SERVO = new ActionServo(hardwareMap, "intake claw", 0.45, 0,time);
    }

    public Action Open() {
        return SERVO.runToRatio(1);
    }

    public Action Close() {
        return SERVO.runToRatio(0);
    }
    public boolean isOpen(){
        return SERVO.getPos()==1;
    }
}


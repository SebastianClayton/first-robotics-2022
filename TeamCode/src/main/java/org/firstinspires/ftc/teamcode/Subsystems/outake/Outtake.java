package org.firstinspires.ftc.teamcode.Subsystems.outake;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.function.DoubleSupplier;

public class Outtake {
    public ViperSlidesSubSystem vipers;
    public OuttakeClaw claw;
    public PrimaryOuttakePivot pivot1;
    public SecondaryOuttakePivot pivot2;
    boolean BasketDropping = false;

    public Outtake(HardwareMap hardwareMap, DoubleSupplier time) {
        claw = new OuttakeClaw(hardwareMap, time);
        pivot1 = new PrimaryOuttakePivot(hardwareMap, time);
        pivot2 = new SecondaryOuttakePivot(hardwareMap, time);
        vipers = new ViperSlidesSubSystem(hardwareMap);
    }

    /**
     * this function is meant to be looped
     */
    public Action TransferPos() {
        return new SequentialAction(
                pivot1.outOfTheWayOfIntakePos(),
                pivot2.TransferPos(),
                pivot1.TransferPos(),
                vipers.Down()
        );
    }
    public Action OutOfTheWayOfTheIntakePos(){
        return new ParallelAction(
                pivot1.outOfTheWayOfIntakePos(),
                pivot2.outOfTheWayOfIntakePos()
        );
    }
    public Action SafeVipersDown(){
        return new SequentialAction(
                new ParallelAction(
                        pivot1.outOfTheWayOfIntakePos(),
                        pivot2.outOfTheWayOfIntakePos()
                ),
                vipers.Down()

        );
    }
}

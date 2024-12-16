package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Vision;

@TeleOp(name = "brightness test")
public class BrightnessTest extends OpMode {
    Vision vision;
    @Override
    public void init() {
        vision = new Vision(hardwareMap,telemetry);
        vision.InitPipeline();
    }

    @Override
    public void loop() {

    }
}

//It's Terry-ble!
/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.oldStuff;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This file works in conjunction with the External Hardware Class sample called: ConceptExternalHardwareClass.java
 * Please read the explanations in that Sample about how to use this class definition.
 *
 * This file defines a Java Class that performs all the setup and configuration for a sample robot's hardware (motors and sensors).
 * It assumes three motors (left_drive, right_drive and arm) and two servos (left_hand and right_hand)
 *
 * This one file/class can be used by ALL of your OpModes without having to cut & paste the code each time.
 *
 * Where possible, the actual hardware objects are "abstracted" (or hidden) so the OpMode code just makes calls into the class,
 * rather than accessing the internal hardware directly. This is why the objects are declared "private".
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with *exactly the same name*.
 *
 * Or.. In OnBot Java, add a new file named RobotHardware.java, drawing from this Sample; select Not an OpMode.
 * Also add a new OpMode, drawing from the Sample ConceptExternalHardwareClass.java; select TeleOp.
 *
 */

public class TerryHardware {
    public void move(double direction, double power){
        double power2 = -Math.sin(direction - Math.PI / 4) * power;
        lf.setPower(power2);
        double power1 = -Math.sin(direction + Math.PI / 4) * power;
        rf.setPower(power1);
        lb.setPower(power2);
        rb.setPower(power1);
    }
    //omni/mechanum wheels variable definition
    private static final double GAIN_DRIVE = 0.8;
    private static final double GAIN_STRAFE = -0.8;
    private static final double GAIN_TURN = 0.6;

    //define motors and servos
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private DcMotor arm;
    private Servo claw;
    //sensitivities outside of currentSensitivity should not be changed during opmode.
    //they are intended as base sensitivities. tweak them in code, not with a if statement.
    private double defaultSensitivity = 1.0;
    private double slowSensitivity = 0.5;
    private double currentSensitivity = 1.0;

    /* Declare OpMode members. */
    private HardwareMap robotHardware; // gain access to methods in the calling OpMode.

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public TerryHardware(HardwareMap terryHardware) {
        robotHardware = terryHardware;
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     *
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void initHardware()    {
        lf = robotHardware.get(DcMotor.class, "leftFront");
        rf = robotHardware.get(DcMotor.class, "rightFront");
        lb = robotHardware.get(DcMotor.class, "leftBack");
        rb = robotHardware.get(DcMotor.class, "rightBack");
        //arm = robotHardware.get(DcMotor.class,"arm");
        claw = robotHardware.get(Servo.class, "claw");
        /*
        YOU MUST ADD THIS TO ANYTHING THAT USES ENCODERS AFTER DEFINING MOTORS
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        //set the encoders to be enabled
        //lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //sets all motors to run without encoders. remember to set a target before turning encoders on!
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lf.setDirection(DcMotor.Direction.REVERSE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setDirection(DcMotor.Direction.REVERSE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        System.out.print("Initialized!");
    }


    public void driveRobot(double drive, double strafe, double turn) {
        // the proper formula is left stick y, left stick x, right stick x. I.E you want to input those
        // values into this function to get it to be able to drive. Current sensitivity must be defined - default
        // as of 12/16/2022 is 1.0
        lf.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        - GAIN_STRAFE * strafe * currentSensitivity
                        + GAIN_TURN * turn * currentSensitivity
        );
        rf.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        + GAIN_STRAFE * strafe * currentSensitivity
                        - GAIN_TURN * turn * currentSensitivity
        );
        lb.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        + GAIN_STRAFE * strafe * currentSensitivity
                        + GAIN_TURN * turn * currentSensitivity
        );
        rb.setPower(
                GAIN_DRIVE * drive * currentSensitivity
                        - GAIN_STRAFE * strafe * currentSensitivity
                        - GAIN_TURN * turn * currentSensitivity
        );


    }
    //sets the claw position to whatever value is passed to it.

    public void setClawPosition(double clawPos){
        claw.setPosition(clawPos);
    }
}

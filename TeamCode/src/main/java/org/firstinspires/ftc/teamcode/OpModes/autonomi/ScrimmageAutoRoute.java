package org.firstinspires.ftc.teamcode.OpModes.autonomi;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.oldStuff.VoidsAndThings;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

@TeleOp(name = "ScrimmageAuto", group = "test")

public class ScrimmageAutoRoute extends LinearOpMode {

    //We are defining all motors here, as to manually control each motor rather than use terry hardware.
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "ShinyLowLightCube.tflite";
    private static final String[] LABELS = {
            "RedCube",
    };
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;
    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;


    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    //ColorSensor Bsensor;
    //private DcMotor arm;
    //private Servo claw;
    //ColorSensor colorSensor;
    //Wheel encoders
    //This is the gobilda encoder value that is used

    final double wheelUnitTicks = 537.7;

    //this is the gear ratio (this is to make it looks consistent with the arm encoders)
    final double wheelGearRatios = 1;

    //multiplies wheelUnitTicks by wheelGearRatios
    final double wheelRotation = (wheelUnitTicks * wheelGearRatios);

    //This is the circumference value for thegobilda wheels we use in inches
    final double wheelCircumference = 12.5663706144;

    //this is the wheelUnitTicks divided by wheelCircumference to get the amount of ticks to move one inch, this
    //makes it so we can just call on this value instead of pasting the number each time in code, as well as
    //the value can be multiplied by the amount of inches desired to make it more simple.
    final double wheelOneInch = (wheelRotation / wheelCircumference);

//    private double MaxClawPos = 0.65;
//    private double MinClawPos = 0.9;
//    private double MaxArmPos = 509;
//    private double MinArmPos = 0;
//    private double minRed = 90;//under 90
//    private double minBlue = 50;

    //    private boolean GetColorBRed(){
//
//        return minRed<Bsensor.red();
//    }
//    private boolean GetColorBBlue(){
//
//        return minBlue<Bsensor.blue();//under
//    }
    public void runWithoutEncoders() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

//    public void arm(double power, double height) {//forward(1);forward(-1)
//        //resetEncoders();   <--we dont want this
//        arm.setTargetPosition((int) (height));
////--------------------------------------------------------------------------------------------------
//        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////--------------------------------------------------------------------------------------------------
//        arm.setPower(power);

        //--------------------------------Telemetry, gives data about position and makes sure it doesn't stop immediately.----------------------
//        while (arm.isBusy()) {
//            telemetry.addData("lf encoder: ",arm.getCurrentPosition());
//            telemetry.addData("power: ",arm.getPower());
//            telemetry.update();
//        }
        //-------------------------End While--------------------------------------------------------
      //  Stop(); //stopping all motors
  //  }

//    public void armDown() {
//        sleep(200);
//        arm(0.15, MaxArmPos);
//        while (arm.isBusy()) {
//            telemetry.addData("arm position", arm.getCurrentPosition());
//            telemetry.update();
//
//        }
//        sleep(200);
//        arm.setPower(0);
//        sleep(200);
//
//    }
//
//    public void armUp() {
//        arm(-0.35, MinArmPos);
//        while (arm.isBusy()) {
//            telemetry.addData("arm position", arm.getCurrentPosition());
//            telemetry.update();
//
//        }
//        arm.setPower(0);
//    }
//
//    public void ClawOpenWIDE() {
//        claw.setPosition(0);
//    }
//
//    public void ClawOpen() {
//        claw.setPosition(0.65);
//    }
//
//    public void ClawClose() {
//        claw.setPosition(0.9);
//    }
//
//

    public void move(double direction, double power) {
        lf.setPower(Math.sin(direction - Math.PI / 4) * power);
        rf.setPower(Math.sin(direction + Math.PI / 4) * power);
        lb.setPower(Math.sin(direction - Math.PI / 4) * power);
        rb.setPower(Math.sin(direction + Math.PI / 4) * power);
    }
//hello


    public void forward(double power) {//forward(1);forward(-1);
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

    }
//    public void openFunnel(){
//        funnelWheel.setPower(1);
//        sleep(1600);
//        funnelWheel.setPower(0);
//    }
//    public void tele(double power,double height) {//forward(1);forward(-1)
//        telearm.setTargetPosition((int) (height));
////--------------------------------------------------------------------------------------------------
//        telearm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
////--------------------------------------------------------------------------------------------------
//        telearm.setPower(power);
//        //--------------------------------Telemetry, gives data about position and makes sure it doesnt stop immediately.----------------------
//
//        //-------------------------End While--------------------------------------------------------
//        //stopping all motors
//        telearm.setPower(0);
//    }

    public void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runToPosition() {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //----------------------------Forward starts----------------------------------------------------
    public void forward(double power, double distance) {//forward(1);forward(-1)
        resetEncoders();
        lf.setTargetPosition((int) (distance * wheelOneInch));
        rf.setTargetPosition((int) (distance * wheelOneInch));
        lb.setTargetPosition((int) (distance * wheelOneInch));
        rb.setTargetPosition((int) (distance * wheelOneInch));
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

        //--------------------------------Telematry, gives data about position----------------------
      //  while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
//            telemetry.addData("rb encoder: ",rb.getCurrentPosition());
//            telemetry.addData("power: ",rb.getPower());
//            telemetry.addData("lb encoder: ",lb.getCurrentPosition());
//            telemetry.addData("power: ",lb.getPower());
//            telemetry.addData("rf encoder: ",rf.getCurrentPosition());
//            telemetry.addData("power: ",rf.getPower());
//            telemetry.addData("lf encoder: ",lf.getCurrentPosition());
//            telemetry.addData("power: ",lf.getPower());
//            telemetry.addData("Bsensor red: ",Bsensor.red());
//            telemetry.addData("Bsensor blue: ",Bsensor.blue());
//            telemetry.addData("claw: ",claw.getPosition());
//            telemetry.update();
//            telemetry.update();
      //  }
        //-------------------------End While--------------------------------------------------------
      //  stop(); //stopping all motors
    }

    public void turn(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);
    }



    public void turn(double power, double degrees) {
        runWithoutEncoders();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
    }

    //----------------------------------End of forward--------------------------------------------------
    //sRight = straife right
    //right = ++ left = --
//----------------------------sRight starts---------------------------------------------------------
    public void sRight(double power, double distance) {
        resetEncoders();
        lf.setTargetPosition((int) (-distance * wheelOneInch));
        rf.setTargetPosition((int) (distance * wheelOneInch));
        lb.setTargetPosition((int) (distance * wheelOneInch));
        rb.setTargetPosition((int) (-distance * wheelOneInch));
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(-power);

        //--------------------------------Telematry, gives data about position--------------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
            telemetry.addData("rb encoder: ", rb.getCurrentPosition());
            telemetry.addData("power: ", rb.getPower());
            telemetry.addData("lb encoder: ", lb.getCurrentPosition());
            telemetry.addData("power: ", lb.getPower());
            telemetry.addData("rf encoder: ", rf.getCurrentPosition());
            telemetry.addData("power: ", rf.getPower());
            telemetry.addData("lf encoder: ", lf.getCurrentPosition());
            telemetry.addData("power: ", lf.getPower());
            telemetry.update();
        }
        //-------------------------End While------------------------------------------------------------
        Stop(); //stopping all motors
    }
    //----------------------------------End of sRight----------------------------------------------

    public void Stop() {
        lf.setPower(0);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);

    }

    public void striaferight(double power) {
        lf.setPower(power);
        rf.setPower(-power);
        lb.setPower(-power);
        rb.setPower(power);

    }

    public void striafeleft(double power) {
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(-power);

    }


    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()


                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();
        //time for tfod 2!
        //tfod2 = new TfodProcessor.Builder().setModelAssetName(TFOD_MODEL_ASSET2).setModelLabels(LABELS2).build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        builder.addProcessor(tfod);

        visionPortal = builder.build();
    }

    private int getSpikeMarkVision() {

        List<Recognition> currentRecognitions = tfod.getRecognitions();

        double confidence = 0;
        double x = 100;
        double counter = 0;
        while ((currentRecognitions.size() == 0) && opModeIsActive()) {
            currentRecognitions = tfod.getRecognitions();
            counter++;
            telemetry.addData("counter", counter);
            telemetry.update();
            if (counter > 40) {
                x = 0;//to get spikemark 1
                break;
            }
            sleep(200);


        }
        for (Recognition recognition : currentRecognitions) {
            if (confidence < recognition.getConfidence()) {
                x = (recognition.getLeft() + recognition.getRight()) / 2;
                confidence = recognition.getConfidence();
            }

        }

        telemetry.addData("position", x);
        telemetry.addData("spikemark", Math.round(x / 200 + 1));
        telemetry.update();
        sleep(500);
        return (int) Math.round(x / 200 + 1);
    }


    public void ScrimmageAuto() {
        forward(.25,12);
        sRight(-.25, -30);
        forward(-.25,-10);
        sleep(2000);
        //Dispense sample
        forward(.25,10);
        sleep(2000);
        //Grab sample
        forward(-.25,-10);
        sleep(2000);
        //Dispense sample
        sRight(.25,4);
        forward(.25,10);
        sleep(2000);
        //Grab sample
        forward(-.25,-10);
        sRight(-.25,-4);
        sleep(2000);
        //Dispense samples
        forward(.25,10);
        sRight(.25,10);
        forward(.25,10);

    }


    //--------------------------------------------------------------------
    public void autoScrimmage2() {
        forward(-.25, -48);
        sleep(200);
        forward(-.25, -5);
        forward(.25, 5);
    }

    //-------------------------------------------------------------------

    public void autoScrimmage3() {
        forward(-.25, -38);
        forward(.25, 26);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //right(.25);
        sleep(1600);
        forward(0);
        forward(.25, 5);
        forward(-.25, -36);
    }

    //-----------------------------------------------------

    public void autoScrimmage5() {
        striaferight(-.25);
        sleep(1000);
        forward(0);
    }

    public void quarterTurn() {
        //right(1);
        sleep(200);
        forward(0);
    }

    public void autoScrimmagered() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        striaferight(.25);
        sleep(3400);
        forward(.25, -40);
        forward(.25, 5);

    }

    public void autoScrimmageblue() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        striaferight(-.25);
        sleep(3400);
        forward(.25, -40);
        forward(.25, 5);

    }

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);


        VoidsAndThings voidsAndThings = new VoidsAndThings(hardwareMap);
        voidsAndThings.initHardware();

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;


        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);


        initTfod();
        waitForStart();




        ScrimmageAuto();



    }



}

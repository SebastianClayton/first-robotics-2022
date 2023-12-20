package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous(name = "auto1(red)")
public class Auto1 extends LinearOpMode {




    //We are defining all motors here, as to manually control each motor rather than use terry hardware.
    private DcMotor lf;
    private DcMotor rf;
    private DcMotor lb;
    private DcMotor rb;
    private Servo claw;
    ColorSensor Fsensor;
    ColorSensor Bsensor;
    //private DcMotor arm;
    //private Servo claw;
    //ColorSensor colorSensor;
    //Wheel encoders
    //This is the gobilda encoder value that is used
    private VoidsAndThings voidsAndThings;
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

    private double MaxClawPos = 0.3;
    private double MinClawPos = 0.45;
    private double minRed=90;//under 90
    private double minBlue=50;
    private boolean GetColorBRed(){

        return minRed<Bsensor.red();
    }
    private boolean GetColorBBlue(){

        return minBlue<Bsensor.blue();//under
    }
    public void runWithoutEncoders() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
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


    public void resetEncoders() {
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void runToPosition()  {
        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    //----------------------------Forward starts----------------------------------------------------
    public void forward(double power,double distance) {//forward(1);forward(-1)
        resetEncoders();
        lf.setTargetPosition((int) (distance*wheelOneInch));
        rf.setTargetPosition((int) (distance*wheelOneInch));
        lb.setTargetPosition((int) (distance*wheelOneInch));
        rb.setTargetPosition((int) (distance*wheelOneInch));
//--------------------------------------------------------------------------------------------------
        runToPosition();
//--------------------------------------------------------------------------------------------------
        lf.setPower(power);
        rf.setPower(power);
        lb.setPower(power);
        rb.setPower(power);

        //--------------------------------Telematry, gives data about position----------------------
        while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
            telemetry.addData("rb encoder: ",rb.getCurrentPosition());
            telemetry.addData("power: ",rb.getPower());
            telemetry.addData("lb encoder: ",lb.getCurrentPosition());
            telemetry.addData("power: ",lb.getPower());
            telemetry.addData("rf encoder: ",rf.getCurrentPosition());
            telemetry.addData("power: ",rf.getPower());
            telemetry.addData("lf encoder: ",lf.getCurrentPosition());
            telemetry.addData("power: ",lf.getPower());
            telemetry.addData("Bsensor red: ",Bsensor.red());
            telemetry.addData("Bsensor blue: ",Bsensor.blue());
            telemetry.addData("claw: ",claw.getPosition());
            telemetry.update();
            telemetry.update();
        }
        //-------------------------End While--------------------------------------------------------
        stop(); //stopping all motors
    }

    public void turn(double power){
        lf.setPower(-power);
        rf.setPower(power);
        lb.setPower(-power);
        rb.setPower(power);
    }
    IMU imu;
    public void turn(double power, double degrees){
        runWithoutEncoders();
        //YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        //double initdirection = orientation.getYaw(AngleUnit.DEGREES);
        imu.resetYaw();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        while (Math.abs (orientation.getYaw(AngleUnit.DEGREES)) <=degrees) {


            turn(power);
            telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", Math.abs(orientation.getYaw(AngleUnit.DEGREES)));
//            telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
//            telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
            telemetry.update();
            orientation = imu.getRobotYawPitchRollAngles();
        }
    }
//----------------------------------End of forward--------------------------------------------------
    //sRight = straife right
    //right = ++ left = --
//----------------------------sRight starts---------------------------------------------------------
public void sRight(double power,double distance) {
    resetEncoders();
    lf.setTargetPosition((int) (-distance*wheelOneInch));
    rf.setTargetPosition((int) (distance*wheelOneInch));
    lb.setTargetPosition((int) (distance*wheelOneInch));
    rb.setTargetPosition((int) (-distance*wheelOneInch));
//--------------------------------------------------------------------------------------------------
    runToPosition();
//--------------------------------------------------------------------------------------------------
    lf.setPower(-power);
    rf.setPower(power);
    lb.setPower(power);
    rb.setPower(-power);

    //--------------------------------Telematry, gives data about position--------------------------
    while (lf.isBusy() && lb.isBusy() && rf.isBusy() && rb.isBusy()) {
        telemetry.addData("rb encoder: ",rb.getCurrentPosition());
        telemetry.addData("power: ",rb.getPower());
        telemetry.addData("lb encoder: ",lb.getCurrentPosition());
        telemetry.addData("power: ",lb.getPower());
        telemetry.addData("rf encoder: ",rf.getCurrentPosition());
        telemetry.addData("power: ",rf.getPower());
        telemetry.addData("lf encoder: ",lf.getCurrentPosition());
        telemetry.addData("power: ",lf.getPower());
        telemetry.addData("Bsensor red: ",Bsensor.red());
        telemetry.addData("Bsensor blue: ",Bsensor.blue());
        telemetry.addData("claw: ",claw.getPosition());
        telemetry.update();

        telemetry.update();
    }
    //-------------------------End While------------------------------------------------------------
    stop(); //stopping all motors
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
//    public int SpikeCheck() {
//        int max1= 10;
//        int max2= 20;
//        int spike;
//        int i=0;
//        forward(0.25,1);
//        left(0.25,-2);
//        while(sensor.getDistance(DistanceUnit.INCH)>=30) {
//            right(0.25, 2);
//            i+=2;
//        }
//        if (i<max1)
//            spike=1;
//        else if (i<max2)
//            spike=2;
//        else
//            spike=3;
//        left(0.25, 2*i);
//
//        return spike;
//
//    }

    //This is a new auto with the color sensor and straife

    public void autoScrimmageRF() {
        //claw.setPosition(MinClawPos);
        sRight(-.25, -40);
        sleep(200);
        forward(.25,4);
        sleep(200);
        //boolean spike=GetColorB();
        if(GetColorBRed()){ //Put yes statement here for detection of spike marker
            forward(.25, 2);
            forward(-.25,-7);
            turn(0.25,179);
            sRight(-.25,-7);
            forward(-.25,-2);
            forward(.25, 45);
            sleep(1000);
            //claw.setPosition(MaxClawPos);
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker
                forward(-.25, -4);
                sRight(-.25,-6);;
                turn(0.25, 176);
                sRight(-.25,-17);
                forward(0.25,4);
                sleep(200);
            if(GetColorBRed()){ //Put yes statement here for detection of spike marker
                forward(.25, 2);
                //drop piece here
                forward(.25,14);
                sleep(1000);
                //claw.setPosition(MaxClawPos);

           } //end of yes statement
            else { //Put no statement here for detection of spike marker
                forward(.25,2);
                sRight(-.25,-6);
                //drop thing
                forward(.25,35);
                sleep(1000);
                //claw.setPosition(MaxClawPos);
            }

        }


    }
    public void CameraAutoScrimmageRF() {
        //claw.setPosition(MinClawPos);
        sRight(-.25, -40);
        sleep(200);
        forward(.25,4);
        sleep(200);
        //boolean spike=GetColorB();
        if(GetColorBRed()){ //Put yes statement here for detection of spike marker
            forward(.25, 2);
            forward(-.25,-7);
            turn(0.25,179);
            sRight(-.25,-7);
            forward(-.25,-2);
            forward(.25, 45);
            sleep(1000);
            //claw.setPosition(MaxClawPos);
        } //end of yes statement
        else{ //Put no statement here for detection of spike marker
            forward(-.25, -4);
            sRight(-.25,-6);;
            turn(0.25, 176);
            sRight(-.25,-17);
            forward(0.25,4);
            sleep(200);
            if(GetColorBRed()){ //Put yes statement here for detection of spike marker
                forward(.25, 2);
                //drop piece here
                forward(.25,14);
                sleep(1000);
                //claw.setPosition(MaxClawPos);

            } //end of yes statement
            else { //Put no statement here for detection of spike marker
                forward(.25,2);
                sRight(-.25,-6);
                //drop thing
                forward(.25,35);
                sleep(1000);
                //claw.setPosition(MaxClawPos);
            }

        }


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
        forward(.25,5);
        forward(-.25,-36);
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
        forward( .25,-40);
        forward( .25,5);

    }

    public void autoScrimmageblue() {
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        striaferight(-.25);
        sleep(3400);
        forward( .25,-40);
        forward( .25,5);

    }
    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rb.setDirection(DcMotorSimple.Direction.REVERSE);
        Fsensor=hardwareMap.get(ColorSensor.class, "Fsensor");
        Bsensor=hardwareMap.get(ColorSensor.class, "Bsensor");
        imu = hardwareMap.get(IMU.class, "imu");
        claw = hardwareMap.get(Servo.class, "claw");
        voidsAndThings = new VoidsAndThings(hardwareMap);
        voidsAndThings.initHardware();
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        //arm = hardwareMap.get(DcMotor.class, "arm");
        //claw = hardwareMap.get(Servo.class, "claw");
        //colorSensor = hardwareMap.colorSensor.get("color");
        waitForStart();
        autoScrimmageRF();
        //voidsAndThings.turn(0.25, 180);
//        claw.setPosition(MinClawPos);
//        telemetry.addData("claw", claw.getPosition());
//        telemetry.update();
//        sleep(1000);
//        claw.setPosition(MaxClawPos);
//        telemetry.addData("claw", claw.getPosition());
//        telemetry.update();
//
//        sleep(1000);
//        claw.setPosition(MinClawPos);
//        telemetry.addData("claw", claw.getPosition());
//        telemetry.update();




    }


}



package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class CrankSlideSubSystem extends SubsystemBase {
    private final ServoSub CrankL;
    private final ServoSub CrankR;
    private int SplineTeeth = 25;
    private double getToothSize(int teeth){
        return 4.0/(teeth*3.0);
    }
    private double LeftMin = 0.3333-getToothSize(SplineTeeth)/2.0;
    private double RightMin = 1;
    private double LeftMax = 1-getToothSize(SplineTeeth)/2.0;
    private double RightMax = 0.3333;
    double maxExtensionInInches = 12;// needs to be updated
    double minExtensionInInches = 0;

    public CrankSlideSubSystem(HardwareMap hardwareMap) {
        CrankL = new ServoSub(hardwareMap,"CrankLeft", LeftMin, LeftMax);
        CrankR = new ServoSub(hardwareMap, "CrankRight", RightMin, RightMax);

    }
    public void goToPos(double pos){
        CrankL.goToPos(pos);
        CrankR.goToPos(pos);
    }
    public void Extend() {
        goToPos(1);
    }
    public void undeploy() {
        goToPos(0);
    }

    public double getExtensionInInches() {
        return minExtensionInInches+CrankL.getPos()*(maxExtensionInInches-minExtensionInInches);
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

public class RobotHardware {

    //These motors are used to control the drivetrain
    public DcMotor FLMotor; //Left Front Motor
    //public DcMotor LBMotor; //Left Back Motor
    //public DcMotor RBMotor; //Right Back Motor
    public DcMotor FRMotor; //Right Front Motor
    public DcMotor SpinMotor;
    public CRServo rightSpin;
    public CRServo leftSpin;

    //This is the onboard imu located on the controller hub
    public IMU imu;

    //This method initializes actuators and sensors
    public void init(HardwareMap hardwareMap) {

        FLMotor = hardwareMap.get(DcMotor.class, "FL");
        //BLMotor = hardwareMap.get(DcMotor.class, "BL");
        //BRMotor = hardwareMap.get(DcMotor.class, "BR");
        FRMotor = hardwareMap.get(DcMotor.class, "FR");
        SpinMotor = hardwareMap.get(DcMotor.class, "SpinMotor");
        rightSpin = hardwareMap.get(CRServo.class, "RS");
        leftSpin = hardwareMap.get(CRServo.class, "LS");

        FLMotor.setPower(0.0);
        //BLMotor.setPower(0.0);
        //BRMotor.setPower(0.0);
        FRMotor.setPower(0.0);
        SpinMotor.setPower(0.0);
        rightSpin.setPower(0.0);
        leftSpin.setPower(0.0);

        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        //BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        //BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        SpinMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);

        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SpinMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
}

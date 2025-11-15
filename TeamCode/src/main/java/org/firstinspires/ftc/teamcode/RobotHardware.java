package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

public class RobotHardware {

    //These are all the different parts of the robot that are connected to the Driver Hub's hardware map.
    public DcMotor FLMotor; //Left Front Motor
    public DcMotor BLMotor; //Left Back Motor
    public DcMotor BRMotor; //Right Back Motor
    public DcMotor FRMotor; //Right Front Motor
    public DcMotor SpinMotor;
    public CRServo rightSpin;
    public Servo leftSpin;
    public DcMotor intakeSpin;

    //This is the onboard imu located on the controller hub
    public IMU imu;

    //This method initializes actuators and sensors
    public void init(HardwareMap hardwareMap) {

        //The names in quotes have to match the ones in the hardware map exactly. Port numbers don't matter for code.
        FLMotor = hardwareMap.get(DcMotor.class, "FL");
        BLMotor = hardwareMap.get(DcMotor.class, "BL");
        BRMotor = hardwareMap.get(DcMotor.class, "BR");
        FRMotor = hardwareMap.get(DcMotor.class, "FR");
        SpinMotor = hardwareMap.get(DcMotor.class, "SpinMotor");
        rightSpin = hardwareMap.get(CRServo.class, "RS");
        leftSpin = hardwareMap.get(Servo.class, "LS");
        intakeSpin = hardwareMap.get(DcMotor.class, "IS");

        //Makes sure the motors and servos don't start out moving for whatever reason.
        FLMotor.setPower(0.0);
        BLMotor.setPower(0.0);
        BRMotor.setPower(0.0);
        FRMotor.setPower(0.0);
        SpinMotor.setPower(0.0);
        rightSpin.setPower(0.0);
        intakeSpin.setPower(0.0);
        leftSpin.setPosition(0.0);

        //Since the motors don't need to have their positions tracked, they don't use encoders.
        FLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        BLMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        BRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        FRMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        SpinMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        intakeSpin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);

        /*Setting the zero power behavior to brake makes sure the motors force to a stop if
          values aren't being inputted.*/
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SpinMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeSpin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name="StarterBotAuto", group="StarterBot")
public class AutoDummy extends LinearOpMode {


    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor flMotor;
    private DcMotor blMotor;
    private DcMotor frMotor;
    private DcMotor brMotor;
    private DcMotor spinMotor;
    private CRServo rightSpin;
    private CRServo leftSpin;
    private DcMotor intakeSpin;
    private boolean Manual = true; // Player can switch between manual controls and preset actions.
    private boolean Inverse = false;
    private boolean Together = false;
    private boolean Reverse = false;

    private RobotHardware robot = new RobotHardware(); // Class with all of the robot's hardware.

//    @Override
//    public void init() {
//        robot.init(hardwareMap);
//
//        flMotor = robot.FLMotor;
//        frMotor = robot.FRMotor;
//        blMotor = robot.BLMotor;
//        brMotor = robot.BRMotor;
//        spinMotor = robot.SpinMotor;
//        rightSpin = robot.rightSpin;
//        leftSpin = robot.leftSpin;
//        intakeSpin = robot.intakeSpin;
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        APrilTagWebcam aprilTagWebcam = new APrilTagWebcam(hardwareMap, telemetry);
//        AprilTagDetection detection = aprilTagWebcam.getTagBySpecificId(21);
//        telemetry.addData("Detected Tag Id:", detection.id);
//        telemetry.update();
//
//    }
    /*
     * This code runs REPEATEDLY after the driver hits START but before they hit STOP.
     */
    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);

        flMotor = robot.FrontLefMotor;
        frMotor = robot.FrontRightMotor;
        blMotor = robot.BackLeftMotor;
        brMotor = robot.BackRightMotor;
        intakeSpin = robot.IntakeMotor;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        APrilTagWebcam aprilTagWebcam = new APrilTagWebcam(hardwareMap, telemetry);
        AprilTagDetection detection = aprilTagWebcam.getTagBySpecificId(21);
        telemetry.addData("Detected Tag Id:", detection.id);
        telemetry.update();

        waitForStart();
        double initPower = 0.5 ;
        flMotor.setPower(initPower);
        frMotor.setPower(initPower);
        flMotor.setPower(0);
        frMotor.setPower(0);
    }
}

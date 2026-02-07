package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.APrilTagWebcam;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.TankDrive;
import org.firstinspires.ftc.teamcode.actions.ActionUtilities;
import org.firstinspires.ftc.teamcode.actions.ServoAction;
import org.firstinspires.ftc.teamcode.actions.Shooter;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

@Config
@Autonomous(name = "AUTO_DUMMY", group = "Autonomous")
public class AutoDummyMode extends LinearOpMode {


    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor intakeMotor;

    private Servo ShooterArmLeftServo;
    private Servo ShooterArmRightServo;

    private Servo ShooterRotator;

    private Servo carousalServo;

    private Servo backPlateServo;

    private Servo frontPlateServo;

    private boolean Manual = true; // Player can switch between manual controls and preset actions.
    private boolean Inverse = false;
    private boolean Together = false;
    private boolean Reverse = false;

    private boolean RunShooter = false;
    private DcMotor leftShooterMotor;
    private DcMotor rightShooterMotor;

    private IMU imu;
    private Limelight3A limelight;
    private RobotHardware robot = new RobotHardware(); // Class with all of the robot's hardware.
    private NormalizedRGBA frontLeftColorSensor;
    private NormalizedRGBA frontRightColorSensor;
    private NormalizedRGBA backColorSensor;

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();

    int carousalCounter = 0; //move this to init
    int carousalPickCounter = 0;

    @Override
    // WE CANNOT DO THIS AUTO W/O ROAD RUNNER BECAUSE IT WILL BREAK THE ROTATOR SERVO...PROBABLY
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);

        frontLeftMotor = robot.FrontLefMotor;
        frontRightMotor = robot.FrontRightMotor;
        backRightMotor = robot.BackRightMotor;
        backLeftMotor = robot.BackLeftMotor;

        backPlateServo = robot.BackKickerServo;
        backPlateServo.setPosition(0.93);

        ShooterRotator = robot.ShooterRotationServo;
        ShooterRotator.setPosition(0.3944);

        intakeMotor = robot.IntakeMotor;

        leftShooterMotor = robot.ShooterLeftMotor;
        rightShooterMotor = robot.ShooterRightMotor;

        ShooterArmRightServo = robot.ShooterArmRightServo;
        ShooterArmRightServo.setPosition(0.75);

        carousalServo = robot.CarousalServo;
        carousalServo.scaleRange(0.0, 1.0);
        //pre load into thing
        //drive back & shoot
        // drive to obelisk/spin limelight to find obelisk
        //read obelisk using limelight
        // pick up one set of balls - has to be really fast
        //go and shoot

        waitForStart();

        while (opModeIsActive()) {

            //leftShooterMotor.setPower(1.0);
            //rightShooterMotor.setPower(1.0);

            //drive back 8 inches
            frontRightMotor.setPower(-0.4);
            frontLeftMotor.setPower(-0.4);
            backLeftMotor.setPower(-0.4);
            backRightMotor.setPower(-0.4);

            sleep(300);

            frontRightMotor.setPower(0.0);
            frontLeftMotor.setPower(0.0);
            backLeftMotor.setPower(0.0);
            backRightMotor.setPower(0.0);

            //ball 1
//            ShooterArmRightServo.setPosition(0.6);
//            sleep(300);
//            carousalServo.setPosition(0.45);
//            sleep(1000);
//            ShooterArmRightServo.setPosition(0.7);
//            sleep(1000);
//            backPlateServo.setPosition(0.6);
//            sleep(100);
//            backPlateServo.setPosition(0.93);
//
//
//            //ball 2
//            ShooterArmRightServo.setPosition(0.6);
//            sleep(300);
//            carousalServo.setPosition(0.38);
//            sleep(1000);
//            ShooterArmRightServo.setPosition(0.7);
//            sleep(1000);
//            backPlateServo.setPosition(0.6);
//            sleep(100);
//            backPlateServo.setPosition(0.93);
//
//            //ball 3
//            ShooterArmRightServo.setPosition(0.6);
//            sleep(300);
//            carousalServo.setPosition(0.315);
//            sleep(1000);
//            ShooterArmRightServo.setPosition(0.7);
//            sleep(1000);
//            backPlateServo.setPosition(0.6);
//            sleep(100);
//            backPlateServo.setPosition(0.93);
//
//            //kill power
//            leftShooterMotor.setPower(0.0);
            //rightShooterMotor.setPower(0.0);




        }
    }
}

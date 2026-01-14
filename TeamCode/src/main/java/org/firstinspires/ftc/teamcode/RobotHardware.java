package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

public class RobotHardware {

    //These motors are used to control the drivetrain
    public DcMotor FrontLefMotor; //Left Front Motor
    public DcMotor BackLeftMotor; //Left Back Motor
    public DcMotor BackRightMotor; //Right Back Motor
    public DcMotor FrontRightMotor; //Right Front Motor
    public DcMotor IntakeMotor;

    public DcMotor ShooterLeftMotor;
    public DcMotor ShooterRightMotor;

    public Servo CarousalServo ;
    public Servo BackKickerServo ;
    public Servo ShooterArmLeftServo;
    public Servo ShooterArmRightServo;

    public Servo ShooterRotationServo ;

    public Servo ShooterLiftServo;

    public Limelight3A limelight;

    //This is the onboard imu located on the controller hub
    public IMU imu;

    public NormalizedColorSensor frontLeftColorSensor ;

    public NormalizedColorSensor frontRightColorSensor ;

    public NormalizedColorSensor backColorSensor ;

    public OpenCvCamera camera ;

    //This method initializes actuators and sensors
    public void init(HardwareMap hardwareMap, Telemetry telemetry) {

        FrontLefMotor = hardwareMap.get(DcMotor.class, "FrontLeft");
        BackLeftMotor = hardwareMap.get(DcMotor.class, "BackLeft");
        BackRightMotor = hardwareMap.get(DcMotor.class, "BackRight");
        FrontRightMotor = hardwareMap.get(DcMotor.class, "FrontRight");
        IntakeMotor = hardwareMap.get(DcMotor.class, "Intake");
        ShooterLeftMotor = hardwareMap.get(DcMotor.class, "ShooterLeft");
        ShooterRightMotor = hardwareMap.get(DcMotor.class, "ShooterRight");

        CarousalServo = hardwareMap.get(Servo.class, "Carousal");
        BackKickerServo = hardwareMap.get(Servo.class, "BackKicker") ;
        ShooterArmLeftServo = hardwareMap.get(Servo.class, "LeftShooterArm");
        ShooterArmRightServo = hardwareMap.get(Servo.class, "ShooterArmRight");
        ShooterRotationServo = hardwareMap.get(Servo.class, "ShooterRotater");
        ShooterLiftServo = hardwareMap.get(Servo.class, "ShooterLift");

        frontLeftColorSensor = hardwareMap.get(NormalizedColorSensor.class, "FrontLeftColorSensor");
        frontRightColorSensor = hardwareMap.get(NormalizedColorSensor.class, "FrontRightColorSensor");
        backColorSensor = hardwareMap.get(NormalizedColorSensor.class, "BackColorSensor");

        imu = hardwareMap.get(IMU.class, "imu");

        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId",
//                "id", hardwareMap.appContext.getOpPackageName());
//
//        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class,
//                "limelight"), cameraMonitorViewId);

        FrontLefMotor.setPower(0.0);
        BackLeftMotor.setPower(0.0);
        BackRightMotor.setPower(0.0);
        FrontRightMotor.setPower(0.0);
        IntakeMotor.setPower(0.0);
        ShooterLeftMotor.setPower(0.0);
        ShooterRightMotor.setPower(0.0);



//        FrontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        BackRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLefMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

//        FrontLefMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
//        BackLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
//        BackRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
//        FrontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        IntakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        ShooterLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        ShooterRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);

        FrontLefMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        IntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ShooterLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ShooterRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();

    }
}

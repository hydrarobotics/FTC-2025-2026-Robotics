package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.actions.Shooter;

import com.acmerobotics.roadrunner.Action;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class FtcDecodeOpMode extends OpMode {


    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor intakeMotor;

    private Servo ShooterArmLeftServo;
    private Servo ShooterArmRightServo;

    private Servo carousalServo;

    private Servo backPlateServo;

    private Servo frontPlateServo;

    private boolean Manual = true; // Player can switch between manual controls and preset actions.
    private boolean Inverse = false;
    private boolean Together = false;
    private boolean Reverse = false;

    private boolean RunShooter = false ;
    private DcMotor leftShooterMotor ;
    private DcMotor rightShooterMotor;

    private IMU imu ;
    private Limelight3A limelight;
    private RobotHardware robot = new RobotHardware(); // Class with all of the robot's hardware.
    private NormalizedRGBA frontLeftColorSensor ;
    private NormalizedRGBA frontRightColorSensor ;
    private NormalizedRGBA backColorSensor;

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();

    @Override
    public void init() {
        robot.init(hardwareMap, telemetry);

        frontLeftMotor = robot.FrontLefMotor;
        frontRightMotor = robot.FrontRightMotor;
        backLeftMotor = robot.BackLeftMotor;
        backRightMotor = robot.BackRightMotor;
        intakeMotor = robot.IntakeMotor;

        leftShooterMotor = robot.ShooterLeftMotor ;
        rightShooterMotor = robot.ShooterRightMotor ;

        ShooterArmLeftServo = robot.ShooterArmLeftServo ;
        ShooterArmRightServo = robot.ShooterArmRightServo ;
        ShooterArmLeftServo.setPosition(0.35);

        carousalServo = robot.CarousalServo ;

        backPlateServo = robot.BackKickerServo ;
        frontPlateServo = robot.ShooterLiftServo ;

        ShooterArmLeftServo.resetDeviceConfigurationForOpMode();
        ShooterArmRightServo.resetDeviceConfigurationForOpMode();

        imu = robot.imu ;

        limelight = robot.limelight ;
        dash.startCameraStream(limelight, 0);
        telemetry.setMsTransmissionInterval(11);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {


        ShooterArmLeftServo.setPosition(0.4);
        //Driver #1 Gamepad Controls
        //Buttons
        boolean G1xButton = gamepad1.x;
        boolean G1aButton = gamepad1.a;
        boolean G1bButton = gamepad1.b;
        boolean G1yButton = gamepad1.y;

        //Triggers
        double G1rightTrigger = gamepad1.right_trigger;
        double G1leftTrigger = gamepad1.left_trigger;

        //Sticks X and Y values
        double G1RightStickX = gamepad1.right_stick_x;
        double G1RightStickY = gamepad1.right_stick_y;

        double G1LeftStickX = gamepad1.left_stick_x;
        double G1LeftStickY = gamepad1.left_stick_y;

        //Bumpers
        boolean G1LeftBumper = gamepad1.left_bumper;
        boolean G1RightBumper = gamepad1.right_bumper;

        //Driver #2 Gamepad Controls
        //Buttons
        boolean G2xButton = gamepad2.x;
        boolean G2aButton = gamepad2.a;
        boolean G2bButton = gamepad2.b;
        boolean G2yButton = gamepad2.y;

        //Triggers
        double G2rightTrigger = gamepad2.right_trigger;
        double G2leftTrigger = gamepad2.left_trigger;

        //Sticks X and Y values
        double G2RightStickX = gamepad2.right_stick_x;
        double G2RightStickY = gamepad2.right_stick_y;

        double G2LeftStickX = gamepad2.left_stick_x;
        double G2LeftStickY = gamepad2.left_stick_y;

        //Bumpers
        boolean G2LeftBumper = gamepad2.left_bumper;
        boolean G2RightBumper = gamepad2.right_bumper;

        //D-pads
        boolean G2DpadUp = gamepad2.dpad_up;
        boolean G2DpadRight = gamepad2.dpad_right;
        boolean G2DpadDown = gamepad2.dpad_down;
        boolean G2DpadLeft = gamepad2.dpad_left;

        //Color Sensor
        frontLeftColorSensor = robot.frontLeftColorSensor.getNormalizedColors();
        frontRightColorSensor = robot.frontRightColorSensor.getNormalizedColors();
        backColorSensor = robot.backColorSensor.getNormalizedColors();

        // Player 2 Variables
        double intakeSpinPower = 0.0;

        if (G1yButton){
            Inverse = !Inverse;
        }

        // Gamepad 2
        if (G2yButton){
            //Together = !Together;
            backPlateServo.setPosition(0.75);


        }

        if (G2aButton){
            //RunShooter = !RunShooter ;
            backPlateServo.setPosition(1.0);

        }

        if (G2bButton){
            Reverse = !Reverse;

        }

        if (G1aButton) {
           //ShooterArmLeftServo.setPosition(0.35); // init
            frontPlateServo.setPosition(0.0);
        }
        if (G1bButton){
            //ShooterArmLeftServo.setPosition(0.40); // start/telop position
            frontPlateServo.setPosition(-0.7);
        }
        if (G1xButton) {
            //ShooterArmLeftServo.setPosition(0.39); //pick up the ball
            frontPlateServo.setPosition(1.0);
        }



        intakeSpinPower = G2leftTrigger;

        if (!Reverse) {
            intakeMotor.setPower(intakeSpinPower);
        } else {
            intakeMotor.setPower(-intakeSpinPower);
        }

        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                Pose3D botpose = result.getBotpose();
                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());
                telemetry.addData("Botpose", botpose.toString());
                telemetry.update();
            } else {
                telemetry.addData("Invalid LL Result", result);
            }
        } else {
            telemetry.addData("limelight camera not found!!!", "");
            telemetry.update();
        }

        //Mecanum Drive

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);


        ShooterArmRightServo.setDirection(Servo.Direction.REVERSE);
        ShooterArmLeftServo.setDirection(Servo.Direction.FORWARD);

        if (RunShooter){ //this is what i need to do
            runningActions.add(new SequentialAction(
                    // spin carousal so that the ball is in the position for back plate kicker... this can happen three times then reset.. there must be three balls in carosal
                    // position/ball 1 at 0.53, position 2 at 1.0, and position 3 at 0.1 for loading into back plate
                    //move down the turn table to optimal position
                    //backplate kicker - 1 is resting position, then 0.75 is when it's kicked
                    //almost immediately front plate kicker holds it aloft
                    //start motor
                    // then find the target with limelight
                    //shoot
                    //init the shooter arm in 0.5, then set it to 0.4 in regular..
//                    new InstantAction(() -> ShooterArmLeftServo.setPosition(0.6)),
//                    new InstantAction(() -> ShooterArmRightServo.setPosition(0.6)),
//                    new SleepAction(5), // code waits for 5 seconds so flywheel gets the speed
//                    new Shooter(robot).spinUp(), //flywheel goes to good speed then rotate carosel to get correct ball
//                    new InstantAction(() -> ShooterArmLeftServo.setPosition(1)),
//                    new InstantAction(() -> ShooterArmRightServo.setPosition(1)),
//                    new SleepAction(2),
//                    new Shooter(robot).spinDown(),
//                    new SleepAction(2)

                    new InstantAction(() -> carousalServo.setPosition(0.53)), // set up carosel for ball 1
                    new InstantAction(() -> ShooterArmLeftServo.setPosition(0.39)),// have to find out position, think it's 0.395 or 0.39
                    new InstantAction(() -> backPlateServo.setPosition(0.75)), // might have to reprogram
                    new InstantAction(() -> frontPlateServo.setPosition(0)), // idk position on this one either
                    new InstantAction(() -> ShooterArmLeftServo.setPosition(0)),
                    //new InstantAction(() -> ), // tbis would be to locate target with limelight and if the limelight desn't sense the april tag, turn with wheels until it does
                    new Shooter(robot).spinUp(),
                    new InstantAction(() -> frontPlateServo.setPosition(0)) // finished shooting one




            ));
        }
        updateActionsToDash() ;

        // Telemetry
        telemetry.addData("|----------------------------------------------|", "");
        telemetry.addData("Front Right Wheel Cur Power: ", frontRightMotor.getPower());
        telemetry.addData("Front Left Wheel Cur Power: ", frontLeftMotor.getPower());
        telemetry.addData("Intake Servo Cur Power: ", intakeMotor.getPower());
        telemetry.addData("|----------------------------------------------|","");
        telemetry.addData("Shooter Arm Left Servo Pos:", ShooterArmLeftServo.getPosition());
        telemetry.addData("Shooter Arm Right Servo Pos:", ShooterArmRightServo.getPosition());
        telemetry.addData("|----------------------------------------------|","");
        telemetry.addData("Movement Controls Inverse On: ", Inverse);
        telemetry.addData("Launch Wheels Together: ", Together);
        telemetry.addData("Intake Wheels Reverse: ", Reverse);
        telemetry.addData("|----------------------------------------------|","");
        telemetry.addData("Front Left Color:", convertHueColortoCommonColor((frontLeftColorSensor), telemetry));
        telemetry.addData("Front Right Color:", convertHueColortoCommonColor(frontRightColorSensor, telemetry));
        telemetry.addData("Back Color: ", convertHueColortoCommonColor(backColorSensor, telemetry));
        telemetry.addData("|----------------------------------------------|","");
        telemetry.update();
    }

    private void updateActionsToDash(){
        TelemetryPacket packet = new TelemetryPacket();

        // update running actions
        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay());
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        this.runningActions = newActions;

        dash.sendTelemetryPacket(packet);
    }

    /*
    * Method to take Normalized Color Sensor RGBA and provide color name*/
    private String convertHueColortoCommonColor(NormalizedRGBA rgba, Telemetry telemetry){
        String detectedColor = "";
        float hue = JavaUtil.colorToHue(rgba.toColor());
        telemetry.addData("Hue:", hue);
        if(hue < 30){
            detectedColor = "RED";
        } else if( hue < 60){
            detectedColor = "ORANGE";
        } else if(hue < 90){
            detectedColor = "YELLOW";
        } else if(hue < 150){
            detectedColor = "GREEN";
        } else if(hue < 225){
            detectedColor = "BLUE";
        } else if (hue < 350) {
            detectedColor = "PURPLE";
        } else {
            detectedColor = "RED";
        }
        return detectedColor ;
    }
}


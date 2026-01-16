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
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
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
        frontPlateServo.setPosition(0.77);

        imu = robot.imu ;

        limelight = robot.limelight ;
        dash.startCameraStream(limelight, 0);
        telemetry.setMsTransmissionInterval(11);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {

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
            Together = !Together;
        }

        if (G2aButton){
            RunShooter = !RunShooter ;
        }

        if (G2bButton){
            Reverse = !Reverse;
        }

        if (G1aButton) {
            ShooterArmLeftServo.setPosition(0.38);
        }

        if (G1bButton){
            telemetry.addData("G1bButton", "NOT IMPLEMENETED!!!!");
        }

        if (G1xButton) {
            ShooterArmLeftServo.setPosition(0.46);
        }

        double i = 0;
        if (G1yButton) {
            // RED color in color sensor indicates an empty space
            String frontLeftColor = convertHueColortoCommonColor(frontLeftColorSensor) ;
            String frontRightColor = convertHueColortoCommonColor(frontRightColorSensor) ;
            String backColor = convertHueColortoCommonColor(backColorSensor) ;
            double carousalPositionToIntakeBall ;
            if (frontLeftColor.equalsIgnoreCase("RED")){
                carousalPositionToIntakeBall = 0.45 ;//TODO update after measuring to take the ball into the front left space
            } else if (frontRightColor.equalsIgnoreCase("RED")){
                carousalPositionToIntakeBall = 0.45 ;//TODO update after measuring  to take the ball into the front right space
            } else if (backColor.equalsIgnoreCase("RED")){
                carousalPositionToIntakeBall = 0.45 ;//TODO update after measuring to take the ball into the back space
            } else {
                carousalPositionToIntakeBall = 0.0 ;
                telemetry.addData("Carousal", "NO EMPTY SPACE");
            }
            carousalServo.setPosition(carousalPositionToIntakeBall);
            backPlateServo.setPosition(1.0); //DOWN on Carousal
            intakeSpinPower = -1.0; //setting the intake motor to full power, adjust as needed
        }

        if (!Reverse) {
            intakeMotor.setPower(intakeSpinPower);
        } else {
            intakeMotor.setPower(-intakeSpinPower);
        }

        //Setting the limelight direction to the direction of the robot.
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw());

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
            Shooter shooter = new Shooter(robot); //using an instance for both spinup and spindown
            // RED color in color sensor indicates an empty space
            String frontLeftColor = convertHueColortoCommonColor(frontLeftColorSensor) ;
            String frontRightColor = convertHueColortoCommonColor(frontRightColorSensor) ;
            String backColor = convertHueColortoCommonColor(backColorSensor) ;

            double carousalServoPosToPickBall;
            if (frontLeftColor.equalsIgnoreCase("RED")){
                carousalServoPosToPickBall = 0.53 ; // TODO defaulting to one position but measure and update
            } else if (frontRightColor.equalsIgnoreCase("RED")){
                carousalServoPosToPickBall = 0.53 ; // TODO defaulting to one position but measure and update
            } else if (backColor.equalsIgnoreCase("RED")){
                carousalServoPosToPickBall = 0.53 ; // TODO defaulting to one position but measure and update
            } else {
                carousalServoPosToPickBall = 0.53;
            }

            runningActions.add(new SequentialAction(
                    new InstantAction(() -> carousalServo.setPosition(carousalServoPosToPickBall)),
                    new InstantAction(() -> frontPlateServo.setPosition(1.0)), //DOWN
                    new InstantAction(() -> ShooterArmLeftServo.setPosition(0.385)),// have to find out position, think it's 0.395 or 0.39
                    // should do ShooterArmRightServo both as two servos are turning the arm together.
                    new SleepAction(1), //Wait for arm to position
                    new InstantAction(() -> backPlateServo.setPosition(0.3)), // might have to reprogram
                    new InstantAction(() -> frontPlateServo.setPosition(0.5)), //MID Point to allow holding the ball

                    // should do ShooterArmRightServo both as two servos are turning the arm together.
                    //new InstantAction(() -> ), // tbis would be to locate target with limelight and if the limelight desn't sense the april tag, turn with wheels until it does
                    new InstantAction(() -> ShooterArmLeftServo.setPosition(0.40)),
                    shooter.spinUp(),
                    new SleepAction(3), //Allowing for shooter motor to spin up to full speed
                    new InstantAction(() -> frontPlateServo.setPosition(0.0)), // finished shooting one
                    new SleepAction(10),

                    // Resetting after shooting
                    new InstantAction(() -> frontPlateServo.setPosition(1.0)), //DOWN
                    new InstantAction(() -> backPlateServo.setPosition(1.0)), //DOWN on Carousal
                    new InstantAction(() -> ShooterArmLeftServo.setPosition(0.385)),
                    shooter.spinDown()
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
        telemetry.addData("Color Sensors: Front Left=", convertHueColortoCommonColor(frontLeftColorSensor)
                + "~ Front Right=" + convertHueColortoCommonColor(frontRightColorSensor)
                + "~ Back =" + convertHueColortoCommonColor(backColorSensor));
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
    private String convertHueColortoCommonColor(NormalizedRGBA rgba){
        String detectedColor = "";
        float hue = JavaUtil.colorToHue(rgba.toColor());
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


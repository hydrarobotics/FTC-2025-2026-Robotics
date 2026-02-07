package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.actions.ActionUtilities;
import org.firstinspires.ftc.teamcode.actions.Shooter;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="Decode Linear OpMode")
public class FTCDecodeLinearOpMode extends LinearOpMode {


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
    private boolean Together = false;

    // we are not using reverse as we are setting the direction of intake in code itself.
//    private boolean Reverse = false;

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
    int carousalPickCounter = 0 ;

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad() ;

    Gamepad currGamepad1 = new Gamepad() ;
    Gamepad currGamepad2 = new Gamepad() ;

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialization
        robot.init(hardwareMap, telemetry);

        frontLeftMotor = robot.FrontLefMotor;
        frontRightMotor = robot.FrontRightMotor;
        backLeftMotor = robot.BackLeftMotor;
        backRightMotor = robot.BackRightMotor;
        intakeMotor = robot.IntakeMotor;

        leftShooterMotor = robot.ShooterLeftMotor;
        rightShooterMotor = robot.ShooterRightMotor;



        ShooterArmLeftServo = robot.ShooterArmLeftServo;
        ShooterArmRightServo = robot.ShooterArmRightServo;
        ShooterRotator = robot.ShooterRotationServo;

        ShooterArmRightServo.setPosition(0.75);
        ShooterRotator.setPosition(0.3944);

        carousalServo = robot.CarousalServo;
        carousalServo.scaleRange(0.0, 1.0);

        backPlateServo = robot.BackKickerServo;
        backPlateServo.setPosition(0.93);
        frontPlateServo = robot.ShooterLiftServo;
        //frontPlateServo.setPosition(0.4);
        imu = robot.imu;

        limelight = robot.limelight;
        dash.startCameraStream(limelight, 0);
        telemetry.setMsTransmissionInterval(11);

        this.carousalCounter = 1 ;
        this.carousalPickCounter = 1 ;


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            TelemetryPacket packet = new TelemetryPacket();
            previousGamepad1.copy(currGamepad1);
            previousGamepad2.copy(currGamepad2);

            currGamepad1.copy(gamepad1);
            currGamepad2.copy(gamepad2);

            leftShooterMotor.setPower(1.0);
            //rightShooterMotor.setPower(1.0);


            //Driver #1 Gamepad Controls
            //Buttons

            boolean G1aButton = currGamepad1.a;

            boolean G1yButton = currGamepad1.y;

            //Driver #2 Gamepad Controls
            //Buttons
            boolean G2aButton = currGamepad2.a;
            boolean G2yButton = currGamepad2.y;

            //D-pads
            boolean G2DpadRight = currGamepad2.dpad_right;
            boolean G2DpadLeft = currGamepad2.dpad_left;

            //Color Sensor
            frontLeftColorSensor = robot.frontLeftColorSensor.getNormalizedColors();
            frontRightColorSensor = robot.frontRightColorSensor.getNormalizedColors();
            backColorSensor = robot.backColorSensor.getNormalizedColors();

            // Below position to allow the Carousal to spin with balls in.
            //robot.ShooterArmRightServo.setPosition(0.6);

            // Player 2 Variables
            double intakeSpinPower = 0.0;
            //  for the button after setting all three balls from intake, need to resst to 0.53

            intakeMotor.setPower(1.0);


            if (currGamepad2.x && !previousGamepad2.x) {
                if (carousalCounter == 3) { // already took three balls now resetting
                    carousalCounter = 1;
                } else {
                    carousalCounter = carousalCounter + 1;
                }

                if (carousalPickCounter == 3) {
                    carousalPickCounter = 1;
                } else{
                    carousalPickCounter++;
                }
            }

            if (currGamepad2.b && !previousGamepad2.b) { //Switch Intake Trigger to Gamepad 2 so driver can focus on driving.
                runningActions.add(
                        ActionUtilities.getIntakeActions(robot, carousalCounter)
                );
            }

            if (G2DpadRight){
                ShooterRotator.setPosition(ShooterRotator.getPosition()-0.01);
            }

            if (G2DpadLeft){
                ShooterRotator.setPosition(ShooterRotator.getPosition()+0.01);
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

            // Reverse the right side motors. This may be wrong for your setup.
            // If your robot moves backwards when commanded to go forwards,
            // reverse the left side instead.
            // See the note about this earlier on this page.
            frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

            double y = -currGamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = currGamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = currGamepad1.right_stick_x;

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

            if (currGamepad2.a && !previousGamepad2.a) {
                Shooter shooter = new Shooter(robot); //using an instance for both spinup and spindown
                // RED color in color sensor indicates an empty space
                String frontLeftColor = convertHueColortoCommonColor(frontLeftColorSensor);
                String frontRightColor = convertHueColortoCommonColor(frontRightColorSensor);
                String backColor = convertHueColortoCommonColor(backColorSensor);

                runningActions.add(ActionUtilities.getShootingActions(robot, carousalPickCounter, shooter));
            }

            // update running actions
            List<Action> newActions = new ArrayList<>();
            for (Action action : runningActions) {
                action.preview(packet.fieldOverlay());
                if (action.run(packet)) {
                    newActions.add(action);
                }
            }
            runningActions = newActions;
            dash.sendTelemetryPacket(packet);

            // Telemetry
            telemetry.addLine("|----------------------------------------------|");
            telemetry.addLine("Intake: Gamepad 2 B");
            telemetry.addLine("Shooting: Gamepad 2 A");
            telemetry.addLine("Increase Carousal Counter: Gamepad 2 X");
            telemetry.addLine("|----------------------------------------------|");
            telemetry.addData("Shooter Arm Right Servo Pos:", ShooterArmRightServo.getPosition());
            telemetry.addLine("Front Plate Pos: " + frontPlateServo.getPosition() + " | " +
                    "Back Plate Pos: " + backPlateServo.getPosition());
            telemetry.addData("Shooter Rotater Pos: ", ShooterRotator.getPosition());
            telemetry.addLine("|----------------------------------------------|");
            telemetry.addData("Color Sensors: Front Left=",
                    convertHueColortoCommonColor(frontLeftColorSensor)
                            + "~ Front Right=" +
                            convertHueColortoCommonColor(frontRightColorSensor)
                            + "~ Back =" +
                            convertHueColortoCommonColor(backColorSensor));
            telemetry.addLine("|----------------------------------------------|");
            telemetry.addData("Carousal Intake Position @" + carousalCounter + ": ", carousalServo.getPosition());
            telemetry.addLine("Carousal Shooting Position @" + carousalPickCounter + ": " +carousalServo.getPosition());
            telemetry.addLine("|----------------------------------------------|");
            telemetry.update();
        }
    }

    private String convertHueColortoCommonColor(NormalizedRGBA rgba) {
        String detectedColor = "";
        float hue = JavaUtil.colorToHue(rgba.toColor());
        if (hue < 30) {
            detectedColor = "RED";
        } else if (hue < 60) {
            detectedColor = "ORANGE";
        } else if (hue < 90) {
            detectedColor = "YELLOW";
        } else if (hue < 150) {
            detectedColor = "GREEN";
        } else if (hue < 225) {
            detectedColor = "BLUE";
        } else if (hue < 350) {
            detectedColor = "PURPLE";
        } else {
            detectedColor = "RED";
        }
        return detectedColor;
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class FtcDecodeOpMode extends OpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor flMotor;
    //private DcMotor blMotor;
    private DcMotor frMotor;
    //private DcMotor brMotor;
    private DcMotor spinMotor;
    private CRServo rightSpin;
    private CRServo leftSpin;
    private boolean Manual = true; // Player can switch between manual controls and preset actions.
    private boolean Inverse = false;
    private boolean Together = false;

    private RobotHardware robot = new RobotHardware(); // Class with all of the robot's hardware.

    @Override
    public void init() {
        robot.init(hardwareMap);

        flMotor = robot.FLMotor;
        frMotor = robot.FRMotor;
        //blMotor = robot.BLMotor;
        //brMotor = robot.BRMotor;
        spinMotor = robot.SpinMotor;
        rightSpin = robot.rightSpin;
        leftSpin = robot.leftSpin;

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

        // Player 1 Variables
        double flMotorPower = 0.0;
        double frMotorPower = 0.0;
        //double blMotorPower = 0.0;
        //double brMotorPower = 0.0;
        //double spinMotorPower = 0.0
        double forwardPower = (G1rightTrigger - G1leftTrigger);

        // Player 2 Variables
        double spinMotorPower = 0.0;
        double servoSpinPower = 0.0;

        //double servoLPos

        // This is the movement code for 4 wheel full movement.
        /*frMotorPower = ((forwardPower - G1LeftStickX) - (G1RightStickX));
        flMotorPower = ((forwardPower + G1LeftStickX) + (G1RightStickX));
        brMotorPower = ((forwardPower + G1LeftStickX) - (G1RightStickX));
        blMotorPower = ((forwardPower - G1LeftStickX) + (G1RightStickX));

        flMotor.setPower(-flMotorPower);
        frMotor.setPower(frMotorPower);
        blMotor.setPower(-blMotorPower);
        brMotor.setPower(brMotorPower);*/

        if (G1yButton){
            Inverse = true;
        }

        if (G1xButton){
            Inverse = false;
        }

        if (Inverse){
            frMotorPower = forwardPower + G1LeftStickX;
            flMotorPower = forwardPower - G1LeftStickX;
            frMotor.setPower(-frMotorPower);
            flMotor.setPower(flMotorPower);
        } else {
            frMotorPower = forwardPower - G1LeftStickX;
            flMotorPower = forwardPower + G1LeftStickX;
            frMotor.setPower(frMotorPower);
            flMotor.setPower(-flMotorPower);
        }

        // Gamepad 2

        if (G2yButton){
            Together = true;
        }
        if (G2xButton){
            Together = false;
        }

        spinMotorPower = G2rightTrigger;
        servoSpinPower = G2leftTrigger;

        if (Together){
            spinMotor.setPower(spinMotorPower);
            rightSpin.setPower(-spinMotorPower);
            leftSpin.setPower(spinMotorPower);
        } else {
            spinMotor.setPower(spinMotorPower);
            rightSpin.setPower(-servoSpinPower);
            leftSpin.setPower(servoSpinPower);
        }

        // Telemetry

        telemetry.addData("Front Right Wheel Power: ", frMotorPower);
        telemetry.addData("Front Left Wheel Power: ", flMotorPower);
        telemetry.addData("Launch Wheel Power: ", spinMotorPower);
        telemetry.addData("Launch Servo Right Power: ", servoSpinPower);
        telemetry.addData("Launch Servo Left Power: ", -servoSpinPower);
        telemetry.addData("|-----------------------|", "");
        telemetry.addData("Front Right Wheel Cur Power: ", frMotor.getPower());
        telemetry.addData("Front Left Wheel Cur Power: ", flMotor.getPower());
        telemetry.addData("Launch Wheel Cur Power: ", spinMotor.getPower());
        telemetry.addData("Launch Servo Right Cur Power: ", rightSpin.getPower());
        telemetry.addData("Launch Servo Left Cur Power: ", leftSpin.getPower());
        telemetry.addData("|-----------------------|","");
        telemetry.addData("Movement Controlls Inverse On: ", Inverse);
        telemetry.addData("Launch Wheels Together: ", Together);

        }

}


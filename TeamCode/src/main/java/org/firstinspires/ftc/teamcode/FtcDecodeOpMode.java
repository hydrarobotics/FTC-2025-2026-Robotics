package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp
public class FtcDecodeOpMode extends OpMode {


    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor flMotor;
    private DcMotor blMotor;
    private DcMotor frMotor;
    private DcMotor brMotor;
    //private DcMotor intakeSpin;
    private Servo carousel;

    private RobotHardware robot = new RobotHardware(); //Initializes the RobotHardware class to get its data.

    boolean Inverse = false; //Inverts movement.
    boolean SRSwap = false; //Swaps the strafing and rotating controls.
    boolean a1Hold = false;
    boolean b1Hold = false;
    boolean Reverse = false; //Reverses intake direction. Added for testing purposes.
    boolean a2Hold = false;

    @Override
    public void init() {
        robot.init(hardwareMap);

        flMotor = robot.FLMotor;
        frMotor = robot.FRMotor;
        blMotor = robot.BLMotor;
        brMotor = robot.BRMotor;
        //intakeSpin = robot.intakeSpin;
        carousel = robot.carousel;

        telemetry.addData("Status", "Initialized");
        telemetry.addData("//---------------| ", "Driver 1 Controls");
        telemetry.addData("Right Trigger: ", "Forward");
        telemetry.addData("Left Trigger: ", "Backward");
        telemetry.addData("Left Stick Horizontal: ", "Strafing");
        telemetry.addData("Right Stick Horizontal: ", "Turning");
        telemetry.addData("A Button: ", "Inverts Current Movement");
        telemetry.addData("B button: ", "Swaps strafing and rotation");
        telemetry.addData("//---------------| ", "Driver 2 Controls");
        telemetry.addData("Right Trigger: ", "Reduces Launch Speed");
        telemetry.addData("Left Trigger: ", "Intake Wheels");
        telemetry.addData("Dpad Left/Up/Right: ", "Carousel 0/120/240");
        telemetry.addData("A Button: ", "Inverts intake direction");
        telemetry.update();

        //Nayana Code
        //APrilTagWebcam aprilTagWebcam = new APrilTagWebcam(hardwareMap, telemetry);
        //AprilTagDetection detection = aprilTagWebcam.getTagBySpecificId(21);
        //telemetry.addData("Detected Tag Id:", detection.id);
        //telemetry.update();

    }

    @Override
    public void loop() {

        //----------------------------------------------------------------------------------| Driver #1 Gamepad Controls

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

        //----------------------------------------------------------------------------------| Driver #2 Gamepad Controls

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

        //----------------------------------------------------------------------------------| Player 1 Variables

        double flMotorPower = 0.0;
        double frMotorPower = 0.0;
        double blMotorPower = 0.0;
        double brMotorPower = 0.0;
        double forwardPower = (G1rightTrigger - G1leftTrigger);

        //----------------------------------------------------------------------------------| Player 2 Variables

        double intakeSpinPower = 0.0;

        //----------------------------------------------------------------------------------| Gamepad 1 Controls

        /* This is the movement code for 4 wheel full movement. Strafe Specifically. Will be added when we
        implement mechanum wheels. */
        /*frMotorPower = ((forwardPower - G1LeftStickX) - (G1RightStickX));
        flMotorPower = ((forwardPower + G1LeftStickX) + (G1RightStickX));
        brMotorPower = ((forwardPower + G1LeftStickX) - (G1RightStickX));
        blMotorPower = ((forwardPower - G1LeftStickX) + (G1RightStickX));

        flMotor.setPower(-flMotorPower);
        frMotor.setPower(frMotorPower);
        blMotor.setPower(-blMotorPower);
        brMotor.setPower(brMotorPower);*/


        if(G1aButton){
            if(!a1Hold) {
                Inverse = !Inverse;
            }
            a1Hold = true;
        } else {
            a1Hold = false;
        }

        if(G1bButton){
            if(!b1Hold){
                SRSwap = !SRSwap;
            }
            b1Hold = true;
        } else {
            b1Hold = false;
        }

        if (Inverse){
            if(!SRSwap) {
                frMotorPower = ((forwardPower - G1LeftStickX) + (G1RightStickX));
                flMotorPower = ((forwardPower + G1LeftStickX) - (G1RightStickX));
                brMotorPower = ((forwardPower + G1LeftStickX) + (G1RightStickX));
                blMotorPower = ((forwardPower - G1LeftStickX) - (G1RightStickX));
            } else {
                frMotorPower = ((forwardPower - G1RightStickX) + (G1LeftStickX));
                flMotorPower = ((forwardPower + G1RightStickX) - (G1LeftStickX));
                brMotorPower = ((forwardPower + G1RightStickX) + (G1LeftStickX));
                blMotorPower = ((forwardPower - G1RightStickX) - (G1LeftStickX));
            }

            flMotor.setPower(flMotorPower);
            frMotor.setPower(-frMotorPower);
            blMotor.setPower(blMotorPower);
            brMotor.setPower(-brMotorPower);
        } else {
            if(!SRSwap) {
                frMotorPower = ((forwardPower - G1LeftStickX) - (G1RightStickX));
                flMotorPower = ((forwardPower + G1LeftStickX) + (G1RightStickX));
                brMotorPower = ((forwardPower + G1LeftStickX) - (G1RightStickX));
                blMotorPower = ((forwardPower - G1LeftStickX) + (G1RightStickX));
            } else {
                frMotorPower = ((forwardPower - G1RightStickX) - (G1LeftStickX));
                flMotorPower = ((forwardPower + G1RightStickX) + (G1LeftStickX));
                brMotorPower = ((forwardPower + G1RightStickX) - (G1LeftStickX));
                blMotorPower = ((forwardPower - G1RightStickX) + (G1LeftStickX));
            }

            flMotor.setPower(-flMotorPower);
            frMotor.setPower(frMotorPower);
            blMotor.setPower(-blMotorPower);
            brMotor.setPower(brMotorPower);
        }

        //----------------------------------------------------------------------------------| Gamepad 2 Controls

        if(G2aButton){
            if(!a2Hold) {
                Reverse = !Reverse;
            }
            a2Hold = true;
        } else {
            a2Hold = false;
        }

        if(G2DpadRight){
            carousel.setPosition(0.0);
        } else if (G2DpadUp){
            carousel.setPosition(0.5);
        } else if (G2DpadLeft){
            carousel.setPosition(1.0);
        }

        intakeSpinPower = G2leftTrigger / 2; //Too strong at max.

        if (!Reverse) {
            //intakeSpin.setPower(intakeSpinPower);
        } else {
            //intakeSpin.setPower(-intakeSpinPower);
        }

        //----------------------------------------------------------------------------------| Telemetry

        telemetry.addData("Front Right Wheel Power: ", frMotorPower);
        telemetry.addData("Front Left Wheel Power: ", flMotorPower);
        telemetry.addData("Back Right Wheel Power: ", brMotorPower);
        telemetry.addData("Back Left Wheel Power: ", blMotorPower);
        //telemetry.addData("Intake Servo Power: ", intakeSpinPower);
        telemetry.addData("|-----------------------|", "");
        telemetry.addData("Front Right Wheel Cur Power: ", frMotor.getPower());
        telemetry.addData("Front Left Wheel Cur Power: ", flMotor.getPower());
        telemetry.addData("Back Right Wheel Cur Power: ", brMotor.getPower());
        telemetry.addData("Back Left Wheel Cur Power: ", blMotor.getPower());
        telemetry.addData("CS Servo Cur Position: ", carousel.getPosition());
        //telemetry.addData("Intake Servo Cur Power: ", intakeSpin.getPower());
        telemetry.addData("|-----------------------|","");
        telemetry.addData("Movement Controls Inverse On: ", Inverse);
        telemetry.addData("Strafing Rotation Swap: ", SRSwap);
        telemetry.addData("Intake Wheels Reverse: ", Reverse);
        telemetry.addData("Dpad Left: ", G2DpadLeft);
        telemetry.addData("Dpad Up: ", G2DpadUp);
        telemetry.addData("Dpad Right: ", G2DpadRight);

    }

}


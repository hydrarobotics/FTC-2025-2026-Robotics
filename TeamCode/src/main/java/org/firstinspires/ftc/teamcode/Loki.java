package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class Loki extends OpMode {
    private DcMotor Thor;
    private CRServo rightSpin;
    private CRServo leftSpin;
    private RobotHardware robot = new RobotHardware();
    private boolean Together;

    @Override
    public void init() {
        robot.init(hardwareMap);
        Thor = robot.SpinMotor;
        rightSpin = robot.rightSpin;
        leftSpin = robot.leftSpin;
    }

    @Override
    public void loop() {

        boolean Odin = gamepad2.right_bumper;
        double G2LeftStickY = gamepad2.left_stick_y;

        if (Odin == true) {
            Thor.setPower(1.0);
        } else {
            Thor.setPower(0.0);
        }

        rightSpin.setPower(-G2LeftStickY);
        leftSpin.setPower(G2LeftStickY);


    }


}

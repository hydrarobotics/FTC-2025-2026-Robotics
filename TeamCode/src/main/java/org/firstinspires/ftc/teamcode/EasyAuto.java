package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "RedAutoBackDropArea", group = "Competition Code")
public class EasyAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor flMotor;
        DcMotor blMotor;
        DcMotor frMotor;
        DcMotor brMotor;

        RobotHardware robot = new RobotHardware();
        robot.init(hardwareMap);

        flMotor = robot.FLMotor;
        frMotor = robot.FRMotor;
        blMotor = robot.BLMotor;
        brMotor = robot.BRMotor;

        waitForStart();

        //wait(2);

        frMotor.setPower(0.5);
        flMotor.setPower(-0.5);
        brMotor.setPower(0.5);
        blMotor.setPower(-0.5);

        wait(5);

        frMotor.setPower(0.0);
        flMotor.setPower(0.0);
        brMotor.setPower(0.0);
        blMotor.setPower(0.0);
    }
}

package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.TankDrive;

@Config
@Autonomous(name = "AUTO_DUMMY", group = "Autonomous")
public class AutoDummyMode extends LinearOpMode {


    @Override
    public void runOpMode(){
        Pose2d initialPose = new Pose2d(-34, -53, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        //Artifacts artifacts = new Artifacts(hardwareMap);
        //Vector2d shootingPositionVector = new Vector2d(-46, -34);

    }
}

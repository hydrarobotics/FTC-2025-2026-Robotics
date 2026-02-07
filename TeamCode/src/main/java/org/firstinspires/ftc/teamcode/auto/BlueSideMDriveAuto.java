package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.actions.ActionUtilities;
import org.firstinspires.ftc.teamcode.actions.Shooter;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.APrilTagWebcam;

import java.util.List;


@Config
@Autonomous(name = "BLUE_TEST_AUTO_PIXEL", group = "Autonomous")
public class BlueSideMDriveAuto extends LinearOpMode {


    @Override
    public void runOpMode() {
        RobotHardware robot = new RobotHardware();
        robot.init(hardwareMap, telemetry);

        Pose2d startingPosition = new Pose2d(-56, -56, Math.toRadians(230)) ;
        Pose2d shootingPose2d = new Pose2d(-34, -29, Math.toRadians(90));
        Vector2d shootingPositionVector = shootingPose2d.position;

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPosition);

        TrajectoryActionBuilder basetoPreloadedShooting =drive.actionBuilder(startingPosition)
                .strafeTo(shootingPositionVector);

        TrajectoryActionBuilder oblScanToPPG = basetoPreloadedShooting.endTrajectory().fresh()
                .waitSeconds(3)
                .turnTo(Math.toRadians(0))
                .lineToX(-11)
                .turn(Math.toRadians(-90))
                .lineToY(-40)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 1))
                .lineToY(-45)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 2))
                .lineToY(-50)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 3))
                .turn(Math.toRadians(-90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(230));

        TrajectoryActionBuilder oblScanToPGP = basetoPreloadedShooting.endTrajectory().fresh()
                .waitSeconds(3)
                .turnTo(Math.toRadians(0))
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(-40)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 1))
                .lineToY(-45)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 2))
                .lineToY(-50)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 3))
                .turn(Math.toRadians(-90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(230));

        TrajectoryActionBuilder oblScanToGPP = basetoPreloadedShooting.endTrajectory().fresh()
                .waitSeconds(3)
                .turnTo(Math.toRadians(0))
                .lineToX(35)
                .turn(Math.toRadians(-90))
                .lineToY(-40)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 1))
                .lineToY(-45)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 2))
                .lineToY(-50)
                .stopAndAdd(ActionUtilities.getIntakeActions(robot, 3))
                .turn(Math.toRadians(-90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(230));

        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addData("Position during Init", 1);
            telemetry.update();
        }

        telemetry.addData("Starting Position", 1);
        telemetry.update();
        waitForStart();

        APrilTagWebcam aPrilTagWebcam = new APrilTagWebcam(hardwareMap, telemetry);
        List<AprilTagDetection> detectedTags = aPrilTagWebcam.getDetectedTags();
        for (AprilTagDetection detectedTag : detectedTags) {
            if (detectedTag.id == 21 || detectedTag.id == 22 || detectedTag.id == 23) {
                int oblSelectedPose = detectedTag.id;
                telemetry.addData("Position during Init", oblSelectedPose);
                telemetry.update();
            }
        }

        if (isStopRequested()) return;
        Shooter shooter = new Shooter(robot);
        Actions.runBlocking(
                new SequentialAction(
                        basetoPreloadedShooting.build(),
                        ActionUtilities.getShootingActions(robot, 1, shooter),
                        ActionUtilities.getShootingActions(robot, 2, shooter),
                        ActionUtilities.getShootingActions(robot, 3, shooter),
                        oblScanToPPG.build(),
                        ActionUtilities.getShootingActions(robot, 1, shooter),
                        ActionUtilities.getShootingActions(robot, 2, shooter),
                        ActionUtilities.getShootingActions(robot, 3, shooter),
                        oblScanToPGP.build(),
                        ActionUtilities.getShootingActions(robot, 1, shooter),
                        ActionUtilities.getShootingActions(robot, 2, shooter),
                        ActionUtilities.getShootingActions(robot, 3, shooter),
                        oblScanToGPP.build(),
                        ActionUtilities.getShootingActions(robot, 1, shooter),
                        ActionUtilities.getShootingActions(robot, 2, shooter),
                        ActionUtilities.getShootingActions(robot, 3, shooter)
                )
        );
    }
}
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
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.teamcode.APrilTagWebcam;

import java.util.List;

@Config
@Autonomous(name = "RED_TEST_AUTO_PIXEL", group = "Autonomous")
public class RedSideMDriveAuto extends LinearOpMode {


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-34, -53, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Artifacts artifacts = new Artifacts(hardwareMap);
        Vector2d shootingPositionVector = new Vector2d(-46, 34);

        int oblSelectedPose = 21 ; // Hardcoding at beginning before detecting just to default

        TrajectoryActionBuilder baseToOblScan = drive.actionBuilder(initialPose)
                .lineToY(17)
                .waitSeconds(3);

        TrajectoryActionBuilder oblScanToGPP = drive.actionBuilder(new Pose2d(-38, 17, Math.toRadians(90)))
                .turn(Math.toRadians(-90))
                .lineToX(-11)
                .turn(Math.toRadians(90))
                .lineToY(50);

        TrajectoryActionBuilder oblScanToPGP = drive.actionBuilder(new Pose2d(-38, 17, Math.toRadians(90)))
                .turn(Math.toRadians(-90))
                .lineToX(12)
                .turn(Math.toRadians(90))
                .lineToY(50);


        TrajectoryActionBuilder oblScanToPPG = drive.actionBuilder(new Pose2d(-38, 17, Math.toRadians(90)))
                .turn(Math.toRadians(-90))
                .lineToX(35)
                .turn(Math.toRadians(90))
                .lineToY(50);


        Action oblSelectedAction = null ;

        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addData("Position during Init", 1);
            telemetry.update();
        }

        telemetry.addData("Starting Position", 1);
        telemetry.update();
        waitForStart();

        APrilTagWebcam aPrilTagWebcam = new APrilTagWebcam(hardwareMap, telemetry);
        List<AprilTagDetection> detectedTags = aPrilTagWebcam.getDetectedTags() ;
        for (AprilTagDetection detectedTag : detectedTags) {
            if (detectedTag.id == 21 || detectedTag.id == 22 || detectedTag.id == 23){
                oblSelectedPose = detectedTag.id ;
                telemetry.addData("Position during Init", oblSelectedPose);
                telemetry.update();
            }
        }

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        baseToOblScan.build()
                        //Add April Tag Scanning
                )
        );

        int finalXPos = 0 ;
        switch (oblSelectedPose) {
            case 21:
                finalXPos = 35;
                oblSelectedAction = oblScanToGPP.build();
                break;
            case 22:
                finalXPos = 12;
                oblSelectedAction = oblScanToPGP.build();
                break;
            case 23:
                finalXPos = -11;
                oblSelectedAction = oblScanToPPG.build();
                break;
        }

        TrajectoryActionBuilder collectedToShoot =  drive.actionBuilder(new Pose2d(finalXPos, 50, Math.toRadians(90)))
                .turn(Math.toRadians(90))
                .strafeTo(shootingPositionVector)
                .turn(Math.toRadians(-55));

        Actions.runBlocking(
                new SequentialAction(
                    oblSelectedAction,
                    artifacts.collectArtifacts(),
                    collectedToShoot.build()
                )
        );

    }

    public class Artifacts {
        private Servo spin;

        public Artifacts(HardwareMap hardwareMap) {
            spin = hardwareMap.get(Servo.class, "claw");
        }

        public class CollectArtifacts implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spin.setPosition(0.55);
                return false;
            }
        }
        public Action collectArtifacts(){
            return new CollectArtifacts();
        }
    }
}

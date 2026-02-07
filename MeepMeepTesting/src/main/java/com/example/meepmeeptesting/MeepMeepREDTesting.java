package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

// starting position for red is backwards, that's how its programmed it will still shoot forwards correctly
public class MeepMeepREDTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Pose2d startingPosition = new Pose2d(-56, 56, Math.toRadians(138)) ;
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setStartPose(startingPosition)
                .build();


        Pose2d shootingPose2d = new Pose2d(-34, 29, Math.toRadians(90));
        Vector2d shootingPositionVector = shootingPose2d.position;

        TrajectoryActionBuilder basetoPreloadedShooting = myBot.getDrive().actionBuilder(startingPosition)
                .strafeTo(shootingPositionVector);

        TrajectoryActionBuilder oblScanToGPP = basetoPreloadedShooting.endTrajectory().fresh()
                .turnTo(Math.toRadians(0))
                .lineToX(-11)
                .turn(Math.toRadians(90))
                .lineToY(60)
                .turn(Math.toRadians(90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(138));

        TrajectoryActionBuilder oblScanToPGP = basetoPreloadedShooting.endTrajectory().fresh()
                .turnTo(Math.toRadians(0))
                .lineToX(12)
                .turn(Math.toRadians(90))
                .lineToY(60)
                .turn(Math.toRadians(90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(138));

        TrajectoryActionBuilder oblScanToPPG = basetoPreloadedShooting.endTrajectory().fresh()
                .turnTo(Math.toRadians(0))
                .lineToX(35)
                .turn(Math.toRadians(90))
                .lineToY(60)
                .turn(Math.toRadians(90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(138));

        myBot.runAction(new SequentialAction(
                basetoPreloadedShooting.build(),
                oblScanToGPP.build(),
                oblScanToPGP.build(),
                oblScanToPPG.build()
        ));


        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
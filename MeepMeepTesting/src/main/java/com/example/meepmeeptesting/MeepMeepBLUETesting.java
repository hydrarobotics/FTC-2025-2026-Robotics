package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepBLUETesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Pose2d startingPosition = new Pose2d(-56, -56, Math.toRadians(230)) ;
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d shootingPose2d = new Pose2d(-34, -29, Math.toRadians(90));
        Vector2d shootingPositionVector = shootingPose2d.position;

        TrajectoryActionBuilder basetoPreloadedShooting = myBot.getDrive().actionBuilder(startingPosition)
                .strafeTo(shootingPositionVector);

        TrajectoryActionBuilder oblScanToPPG = basetoPreloadedShooting.endTrajectory().fresh()
                .waitSeconds(3)
                .turnTo(Math.toRadians(0))
                .lineToX(-11)
                .turn(Math.toRadians(-90))
                .lineToY(-60)
                .turn(Math.toRadians(-90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(230));

        TrajectoryActionBuilder oblScanToPGP =  basetoPreloadedShooting.endTrajectory().fresh()
                .waitSeconds(3)
                .turnTo(Math.toRadians(0))
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(-60)
                .turn(Math.toRadians(-90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(230));

        TrajectoryActionBuilder oblScanToGPP =  basetoPreloadedShooting.endTrajectory().fresh()
                .waitSeconds(3)
                .turnTo(Math.toRadians(0))
                .lineToX(35)
                .turn(Math.toRadians(-90))
                .lineToY(-60)
                .turn(Math.toRadians(-90))
                .strafeTo(shootingPositionVector)
                .turnTo(Math.toRadians(230));

        myBot.runAction(new SequentialAction(
                basetoPreloadedShooting.build(),
                oblScanToPPG.build(),
                oblScanToPGP.build(),
                oblScanToGPP.build()
        ));

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(false)
                .setAxesInterval(1)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
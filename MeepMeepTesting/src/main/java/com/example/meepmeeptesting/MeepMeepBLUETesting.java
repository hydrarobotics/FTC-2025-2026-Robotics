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

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Vector2d shootingPositionVector = new Vector2d(-46, -34);

        TrajectoryActionBuilder baseToOblScan = myBot.getDrive().actionBuilder(new Pose2d(-38, -51, Math.toRadians(90)))
                .lineToY(-17)
                .waitSeconds(3);

        int oblSelectedPose = 21; // One of 21, 22, 23

        TrajectoryActionBuilder oblScanToPPG = myBot.getDrive().actionBuilder(new Pose2d(-38, -17, Math.toRadians(90)))
                .turn(Math.toRadians(-90))
                .lineToX(-11)
                .turn(Math.toRadians(-90))
                .lineToY(-50);

        TrajectoryActionBuilder oblScanToPGP = myBot.getDrive().actionBuilder(new Pose2d(-38, -17, Math.toRadians(90)))
                .turn(Math.toRadians(-90))
                .lineToX(12)
                .turn(Math.toRadians(-90))
                .lineToY(-50);

        TrajectoryActionBuilder oblScanToGPP = myBot.getDrive().actionBuilder(new Pose2d(-38, -17, Math.toRadians(90)))
                .turn(Math.toRadians(-90))
                .lineToX(35)
                .turn(Math.toRadians(-90))
                .lineToY(-50);

        Action oblSelectedAction = null ;
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

        TrajectoryActionBuilder collectedToShoot = myBot.getDrive().actionBuilder(new Pose2d(finalXPos, -50, Math.toRadians(-90)))
                .turn(Math.toRadians(-90))
                .strafeTo(shootingPositionVector)
                .turn(Math.toRadians(55));

        myBot.runAction(new SequentialAction(
                baseToOblScan.build(),
                oblSelectedAction,
                //CollectArtifacts
                collectedToShoot.build()
        ));


        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
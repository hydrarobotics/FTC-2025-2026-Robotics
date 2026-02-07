package org.firstinspires.ftc.teamcode.actions;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.RobotHardware;

import java.util.ArrayList;
import java.util.List;

public class ActionUtilities {

    public static SequentialAction getShootingActions(RobotHardware robot, int carousalPickCounter, Shooter shooter){

        double carousalServoPosToPickBall = 0.0;

        SequentialAction action = null ;

        if (carousalPickCounter == 1) {
            carousalServoPosToPickBall = 0.45;//0.64;
        } else if (carousalPickCounter == 2) {
            carousalServoPosToPickBall = 0.38; //0.23;
        } else if (carousalPickCounter == 3) {
            carousalServoPosToPickBall = 0.315;//1.0;
        } else {
            carousalServoPosToPickBall = 0.315;
        }

        final double tempCarousalServoPosToPickBall = carousalServoPosToPickBall ;

        action = new SequentialAction(
                new InstantAction(() -> robot.ShooterArmRightServo.setPosition(0.6)),
                new SleepAction(0.3),
                //new InstantAction(() -> robot.ShooterLiftServo.setPosition(0.6)), // new, blocks balls when carousal spins
                new InstantAction(() -> robot.CarousalServo.setPosition(tempCarousalServoPosToPickBall)),
                new SleepAction(1),
                //new InstantAction(() -> robot.ShooterLiftServo.setPosition(0.0)), // new, must insert pos to return it to og pos
                new InstantAction(() -> robot.ShooterArmRightServo.setPosition(0.7)),
                new SleepAction(1),
                //new InstantAction(() -> robot.ShooterLiftServo.setPosition(0.4)), //DOWN-ish, 0.4
                //new InstantAction(() -> robot.ShooterLeftMotor.setPower(1.0)),
                //new InstantAction(() -> robot.ShooterRightMotor.setPower(1.0)),
                new SleepAction(2.0), //Allowing for shooter motor to spin up to full speed
                new InstantAction(() -> robot.BackKickerServo.setPosition(0.6)),
                new SleepAction(0.1),
                // Resetting after shooting
                //new InstantAction(() -> robot.ShooterLeftMotor.setPower(0.0)),
                //new InstantAction(() -> robot.ShooterRightMotor.setPower(0.0)),
                new InstantAction(() -> robot.BackKickerServo.setPosition(0.9)), //DOWN on Carousal
                new SleepAction(0.3),
                new InstantAction(() -> robot.ShooterArmRightServo.setPosition(0.6))
        );
        return action ;

    }

    public static SequentialAction getIntakeActions(RobotHardware robot, int carousalCounter){
        double carousalPosition = 0.0;

        if (carousalCounter == 1) {
            carousalPosition = 0.55;//0.03; // ncorrect
        } else if (carousalCounter == 2) {
            carousalPosition = 0.49; //0.83;
        } else if (carousalCounter == 3) {
            carousalPosition = 0.42;//0.43;
        }
        final double tempCarousalPosition = carousalPosition;

        SequentialAction action = new SequentialAction(
                new InstantAction(() -> robot.ShooterArmRightServo.setPosition(0.6)),
                //new InstantAction(() -> robot.ShooterLiftServo.setPosition(0.32)),
                new InstantAction(() -> robot.CarousalServo.setPosition(tempCarousalPosition)),
                new SleepAction(1),
                //new InstantAction(() -> robot.IntakeMotor.setPower(1.0)),
                new SleepAction(4)
                //new InstantAction(() -> robot.IntakeMotor.setPower(0.0))
        ) ;
        return action ;
    }
//
//
//    public static void shootArtifact(RobotHardware robot, int carousalPickCounter){
//        double carousalServoPosToPickBall = 0.0;
//
//        if (carousalPickCounter == 1) {
//            carousalServoPosToPickBall = 0.45;//0.64;
//        } else if (carousalPickCounter == 2) {
//            carousalServoPosToPickBall = 0.38; //0.23;
//        } else if (carousalPickCounter == 3) {
//            carousalServoPosToPickBall = 0.315;//1.0;
//        } else {
//            carousalServoPosToPickBall = 0.315;
//        }
//
//        final double tempCarousalServoPosToPickBall = carousalServoPosToPickBall ;
//
//        new SleepAction(0.3);
//        robot.CarousalServo.setPosition(tempCarousalServoPosToPickBall);
//        new SleepAction(0.3);
//        robot.ShooterLiftServo.setPosition(0.4);
//        new SleepAction(0.3); //Wait for arm to position
//        robot.ShooterArmRightServo.setPosition(0.7);
//        new SleepAction(0.3);
//        robot.ShooterLiftServo.setPosition(0.3); //MID Point to allow holding the ball
//        new SleepAction(0.1);
//        robot.BackKickerServo.setPosition(0.6); // might have to reprogram
//        new SleepAction(0.1);
//        robot.ShooterArmRightServo.setPosition(0.65);
//        new SleepAction(0.1);
//        robot.ShooterLeftMotor.setPower(0.8);
//        robot.ShooterRightMotor.setPower(0.8);
//        new SleepAction(1.0); //Allowing for shooter motor to spin up to full speed
//        robot.ShooterArmRightServo.setPosition(0.6);
//        new SleepAction(0.1);
//         robot.ShooterLiftServo.setPosition(0.2); // finished shooting one
//        new SleepAction(0.1);
//        // Resetting after shooting
//        robot.ShooterLeftMotor.setPower(0.0);
//        robot.ShooterRightMotor.setPower(0.0);
//        new SleepAction(0.1);
//        robot.ShooterLiftServo.setPosition(0.3); //DOWN
//        new SleepAction(0.3);
//        robot.BackKickerServo.setPosition(0.9); //DOWN on Carousal
//        new SleepAction(0.3);
//        robot.ShooterArmRightServo.setPosition(0.75);
//    }
}

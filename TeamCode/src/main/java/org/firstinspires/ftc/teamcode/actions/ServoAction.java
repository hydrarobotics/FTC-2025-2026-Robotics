package org.firstinspires.ftc.teamcode.actions;

import static java.lang.Math.abs;
import static java.lang.Math.round;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

import java.text.DecimalFormat;

public class ServoAction {
    private Servo servo;

    private final double STEP_SIZE = 0.01;
    public ServoAction(Servo servo) {
        this.servo = servo;
    }

    public class Move implements Action{

        private double targetPosition ;
        public Move(double targetPosition) {
            this.targetPosition = targetPosition ;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            double currServoPosition = servo.getPosition() ;
            do {
                if (currServoPosition > targetPosition) {
                    servo.setPosition((currServoPosition - STEP_SIZE));
                } else if (currServoPosition < targetPosition) {
                    servo.setPosition((currServoPosition + STEP_SIZE));
                }
                currServoPosition = servo.getPosition();
            } while (abs(round((currServoPosition - targetPosition) * 100) / 100 ) >= STEP_SIZE) ;

            return true;
        }
    }

    public Action move(double targetPosition){
        return new Move(targetPosition) ;
    }
}

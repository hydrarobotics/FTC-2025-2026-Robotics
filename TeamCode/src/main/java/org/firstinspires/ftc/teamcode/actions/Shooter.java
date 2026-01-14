package org.firstinspires.ftc.teamcode.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class Shooter {
    private DcMotor shooterLeftMotor;
    private DcMotor shooterRightMotor;

    public Shooter(RobotHardware robot) {
        shooterLeftMotor = robot.ShooterLeftMotor ;
        shooterRightMotor = robot.ShooterRightMotor ;
    }

    public class SpinUp implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                shooterLeftMotor.setPower(0.8);
                shooterRightMotor.setPower(0.8);
                initialized = true;
            }

            double shooterLeftMotorPower = shooterLeftMotor.getPower();
            double shooterRightMotorPower = shooterRightMotor.getPower();
            packet.put("shooterLeftMotorPower", shooterLeftMotorPower);
            packet.put("shooterRightMotorPower", shooterRightMotorPower);
            return shooterLeftMotorPower >= 0.8 && shooterRightMotorPower >= 0.8 ;
        }
    }

    public class SpinDown implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                shooterLeftMotor.setPower(0.0);
                shooterRightMotor.setPower(0.0);
                initialized = true;
            }

            double shooterLeftMotorPower = shooterLeftMotor.getPower();
            double shooterRightMotorPower = shooterRightMotor.getPower();
            packet.put("shooterLeftMotorPower", shooterLeftMotorPower);
            packet.put("shooterRightMotorPower", shooterRightMotorPower);
            return shooterLeftMotorPower == 0.0 && shooterRightMotorPower == 0.0;
        }
    }

    public Action spinUp() {
        return new SpinUp();
    }

    public Action spinDown(){
        return  new SpinDown();
    }
}


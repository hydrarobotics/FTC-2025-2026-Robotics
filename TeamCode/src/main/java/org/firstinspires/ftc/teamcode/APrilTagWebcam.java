package org.firstinspires.ftc.teamcode;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class APrilTagWebcam {
    private AprilTagProcessor apriltagProcessor;

    private VisionPortal visionPortal;

    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    private Telemetry telemetry;

    public APrilTagWebcam(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        this.apriltagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hwMap.get(WebcamName.class, "Webcam 1"));
        builder.setCameraResolution(new Size( 640, 480));
        builder.addProcessor(apriltagProcessor);

        this.visionPortal = builder.build();
    }

    public void update() {
        this.detectedTags = apriltagProcessor.getDetections();
    }

    public List<AprilTagDetection> getDetectedTags() {
        return this.detectedTags;
    }

    public void displayDectionTelemetry(AprilTagDetection detectedId) {
        if (detectedId == null) {return;}

        if (detectedId.metadata != null) {
            telemetry.addLine(String.format("\n==== (ID %d) %s", detectedId.id, detectedId.metadata.name));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detectedId.ftcPose.x, detectedId.ftcPose.y, detectedId.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detectedId.ftcPose.pitch, detectedId.ftcPose.roll, detectedId.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detectedId.ftcPose.range, detectedId.ftcPose.bearing, detectedId.ftcPose.elevation));
        } else {
            telemetry.addLine(String.format("\n==== (ID %d) Unknown", detectedId.id));
            telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detectedId.center.x, detectedId.center.y));
        }
    }

    public AprilTagDetection getTagBySpecificId(int id) {

        for (AprilTagDetection detection : this.detectedTags) {
            if (detection.id == 21 || detection.id == 22 || detection.id == 23) {
                telemetry.addLine(String.format("ID: ", detection.id));
                return detection;
            }
        }
        return null;
    }

    public void stop() {
        if (this.visionPortal != null) {
            this.visionPortal.close();
            telemetry.addData("AprilIdTag Vision Portal", "Closed Vision Portal");
        }
    }

}

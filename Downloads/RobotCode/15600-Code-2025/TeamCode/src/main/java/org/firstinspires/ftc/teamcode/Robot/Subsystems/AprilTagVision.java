package org.firstinspires.ftc.teamcode.Robot.Subsystems;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import java.util.List;

public class AprilTagVision extends SubsystemBase {

    private VisionPortal portal;
    Telemetry tl;
    private AprilTagProcessor processor;

    public AprilTagVision(HardwareMap hw, Telemetry tl) {

        this.tl = tl;
        processor = new AprilTagProcessor.Builder()
                .build();

        portal = new VisionPortal.Builder()
                .setCamera(hw.get(WebcamName.class, "Webcam 1"))
                .addProcessor(processor)
               // .setCameraResolution(new Size(1280, 720))
                .build();
    }

    public AprilTagDetection getFirstDetection() {
        List<AprilTagDetection> detections = processor.getDetections();
        if (detections.isEmpty()) return null;
        for (AprilTagDetection d : detections) {
            if (d.id == 21 || d.id == 22 || d.id == 23) {
                return d; // Solo regresamos IDs válidos
            }
        }
        return null;
    }

    public List<AprilTagDetection> getAllDetections() {
        return processor.getDetections();
    }

    public void stop() {
        portal.close();
    }

    public void periodic(){
        tl.addData("Detecciones AprilTag", getAllDetections());
    }
}

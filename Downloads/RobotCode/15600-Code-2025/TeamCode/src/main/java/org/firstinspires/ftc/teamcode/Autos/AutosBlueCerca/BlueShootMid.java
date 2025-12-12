package org.firstinspires.ftc.teamcode.Autos.AutosBlueCerca;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.AprilTagVision;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class BlueShootMid extends CommandOpMode {
    MecanumDriveTrain m_drive;
    AutosequenceBlue blue;
    @Override
    public void initialize() {
       m_drive = new MecanumDriveTrain(hardwareMap, telemetry, true);
        AprilTagVision april = new AprilTagVision(hardwareMap, telemetry);

        Shooter m_shooter = new Shooter(hardwareMap, telemetry);
        Intake m_intake = new Intake(hardwareMap, telemetry);
        Torreta m_torreta = new Torreta(hardwareMap, telemetry);
        Feeder m_feeder = new Feeder(hardwareMap, telemetry);
        register(m_drive, m_shooter, m_torreta, m_intake, m_feeder);
        Follower follower = m_drive.getFollower();
        new PathsBlue(follower);

        AprilTagDetection startTag = null;
        while (!isStarted() && !isStopRequested()) {
            AprilTagDetection det = april.getFirstDetection();
            if (det != null) {
                telemetry.addData("Detected ID", det.id);
                startTag = det;
            } else {
                telemetry.addData("Detected", "None");
            }
            telemetry.update();
        }

        blue = new AutosequenceBlue(m_drive, m_shooter, m_intake, m_torreta, m_feeder, telemetry, startTag);



        schedule(new ParallelCommandGroup(blue), new RunCommand(() -> {
            blackboard.put("endPose", follower.getPose());
            blackboard.put("torretaPosition", m_torreta.ticksToDegrees());
        }

        ));

        schedule( new RunCommand(()->{
            telemetry.update();
            com.pedropathing.geometry.Pose pose =
                    (com.pedropathing.geometry.Pose) blackboard.get("endPose");
            telemetry.addData("Blackboard Y", pose.getY());
        }));


        /*schedule(blue);

        schedule(new RunCommand(()-> {
            telemetry.update();
        }));*/
    }

}

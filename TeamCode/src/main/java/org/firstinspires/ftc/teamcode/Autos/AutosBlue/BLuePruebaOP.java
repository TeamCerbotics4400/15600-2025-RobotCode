package org.firstinspires.ftc.teamcode.Autos.AutosBlue;

import static com.qualcomm.robotcore.eventloop.opmode.OpMode.blackboard;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

@Autonomous
public class BLuePruebaOP extends CommandOpMode {
    MecanumDriveTrain m_drive;
   BluePruevba prueba;
    @Override
    public void initialize() {
        m_drive = new MecanumDriveTrain(hardwareMap, telemetry, true);
        Shooter m_shooter = new Shooter(hardwareMap, telemetry);
        Intake m_intake = new Intake(hardwareMap, telemetry);
        Torreta m_torreta = new Torreta(hardwareMap, telemetry);
        Feeder m_feeder = new Feeder(hardwareMap, telemetry);
        register(m_drive, m_shooter, m_torreta, m_intake, m_feeder);
        Follower follower = m_drive.getFollower();
        new PathsBlue(follower);
        prueba = new BluePruevba(m_drive, m_shooter, m_intake, m_torreta, m_feeder, telemetry);



        schedule(new ParallelCommandGroup(prueba), new RunCommand(() -> {
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
    }
}

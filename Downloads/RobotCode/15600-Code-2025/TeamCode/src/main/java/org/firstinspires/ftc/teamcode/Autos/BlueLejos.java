package org.firstinspires.ftc.teamcode.Autos;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.RunCommand;

import org.firstinspires.ftc.teamcode.Autos.AutosBlueCerca.AutosequenceBlue;
import org.firstinspires.ftc.teamcode.Autos.AutosBlueCerca.PathsBlue;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

@Autonomous
public class BlueLejos extends CommandOpMode {

    MecanumDriveTrain m_drive;
    BlueLejosProcess blue;
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
       // blue = new BlueLejosProcess(m_drive, m_shooter, m_intake, m_torreta, m_feeder, telemetry);
        schedule(blue);

        schedule(new RunCommand(()-> {
            telemetry.update();
        }));
    }
}

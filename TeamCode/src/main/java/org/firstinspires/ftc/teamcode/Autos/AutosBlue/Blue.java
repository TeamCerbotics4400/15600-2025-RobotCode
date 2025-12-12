package org.firstinspires.ftc.teamcode.Autos.AutosBlue;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;
@Autonomous
public class Blue extends CommandOpMode {
    MecanumDriveTrain m_drive;
    AutosequenceBlue blue;
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
        blue = new AutosequenceBlue(m_drive, m_shooter, m_intake, m_torreta, m_feeder, telemetry);
        schedule(blue);
    }
}

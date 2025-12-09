package org.firstinspires.ftc.teamcode.Autos.AutosRed;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autos.AutosBlue.PathsBlue;
import org.firstinspires.ftc.teamcode.Robot.Commands.PathCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.SmartShootCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.TorretaCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

public class AutoSequenceRed extends SequentialCommandGroup {

    MecanumDriveTrain m_drive;
    Shooter m_shooter;
    Intake m_intake;
    Torreta m_torreta;
    Feeder m_feeder;
    Telemetry tl;

    public AutoSequenceRed(MecanumDriveTrain m_drive, Shooter m_shooter, Intake m_intake, Torreta m_torreta, Feeder m_feeder){
        this.m_drive = m_drive;
        this.m_shooter = m_shooter;
        this.m_intake = m_intake;
        this.m_torreta = m_torreta;
        this.m_feeder = m_feeder;

        addRequirements(m_drive,m_shooter,m_intake,m_torreta,m_feeder);

        addCommands(
               // new TorretaCommand(m_drive,m_torreta,tl, new Pose2d(140, 140, Math.toRadians(0))),
                //new SmartShootCommand(m_drive, m_shooter, m_feeder, tl),//Disapara la primera ronda
                new WaitCommand(500),

                new ParallelCommandGroup(

                       // new InstantCommand(()-> m_intake.setPower(1)),
                      //  new InstantCommand(()-> m_feeder.isIntaking = true),//agarra las pelotas
                        new PathCommand(m_drive, PathsRed.Spike1)
                ),
                new WaitCommand(500),
                new PathCommand(m_drive, PathsRed.Shooter1),//Posicion disparo
                new WaitCommand(500),
               // new SmartShootCommand(m_drive, m_shooter, m_feeder, tl),//Dispara segunda ronda
                new WaitCommand(500),

                new ParallelCommandGroup(
                        //new InstantCommand(()-> m_intake.setPower(1)),
                        //new InstantCommand(()-> m_feeder.isIntaking = true),//Agarra las ultimas pelotas
                        new PathCommand(m_drive, PathsRed.Spike2)
                ),
                new WaitCommand(500),
                new PathCommand(m_drive, PathsRed.Path4),//Ultima posicion de disparo
                new WaitCommand(500)
               // new SmartShootCommand(m_drive, m_shooter, m_feeder, tl)
        );

    }

}

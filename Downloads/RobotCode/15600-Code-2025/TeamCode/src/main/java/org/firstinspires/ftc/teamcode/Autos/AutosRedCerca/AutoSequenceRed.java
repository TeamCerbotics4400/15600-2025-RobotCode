package org.firstinspires.ftc.teamcode.Autos.AutosRedCerca;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autos.AutosBlueCerca.PathsBlue;
import org.firstinspires.ftc.teamcode.Robot.Commands.PathCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.SmartShootCommandBLue;
import org.firstinspires.ftc.teamcode.Robot.Commands.SmartShootCommandRed;
import org.firstinspires.ftc.teamcode.Robot.Commands.TorretaCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class AutoSequenceRed extends SequentialCommandGroup {

    MecanumDriveTrain m_drive;
    Shooter m_shooter;
    Intake m_intake;
    Torreta m_torreta;
    Feeder m_feeder;
    Telemetry tl;

    public AutoSequenceRed(MecanumDriveTrain m_drive, Shooter m_shooter, Intake m_intake, Torreta m_torreta, Feeder m_feeder, Telemetry tl, AprilTagDetection tagDetection){
        this.m_drive = m_drive;
        this.m_shooter = m_shooter;
        this.m_intake = m_intake;
        this.m_torreta = m_torreta;
        this.m_feeder = m_feeder;
        this.tl = tl;

        addRequirements(m_drive,m_shooter,m_intake,m_torreta,m_feeder);

        addCommands(

                new ParallelCommandGroup(
                        new TorretaCommand(m_drive,m_torreta,tl, new Pose2d(140
                                ,144,Math.toRadians(0))),


                        new SequentialCommandGroup(

                                new InstantCommand(m_feeder::AllTrue),
                                new InstantCommand(()->m_feeder.setInitialColor(0,m_feeder.sensor1Color())),
                                new InstantCommand(()->m_feeder.setInitialColor(1,m_feeder.sensor2Color())),
                                new InstantCommand(()->m_feeder.setInitialColor(2,m_feeder.sensor3Color())),
                                new PathCommand(m_drive, PathsRed.Shoot1),//Posicion disparo


                                new WaitCommand(300),
                                new SmartShootCommandRed(m_drive, m_shooter, m_feeder, tl,true,3050, tagDetection.id),

                                new ParallelCommandGroup(

                                        new InstantCommand(()-> m_intake.setPower(1)),
                                        new PathCommand(m_drive, PathsRed.GoToSpike1)
                                ),


                                new ParallelCommandGroup(

                                        new InstantCommand(()-> m_intake.setPower(1)),
                                        new InstantCommand(()-> m_feeder.isIntaking = true),//agarra las pelotas
                                        new PathCommand(m_drive, PathsRed.Intake1)
                                ),
                                new WaitCommand(500),

                                new InstantCommand(m_feeder::AllTrue),
                                new InstantCommand(()->m_feeder.setInitialColor(0,m_feeder.sensor1Color())),
                                new InstantCommand(()->m_feeder.setInitialColor(1,m_feeder.sensor2Color())),
                                new InstantCommand(()->m_feeder.setInitialColor(2,m_feeder.sensor3Color())),


                                new WaitCommand(500),
                                new PathCommand(m_drive, PathsRed.Shoot2),//Posicion disparo
                                new WaitCommand(200),
                                new InstantCommand(()-> m_intake.setPower(0)),
                                new SmartShootCommandBLue(m_drive, m_shooter, m_feeder, tl,true, 3050, tagDetection.id),//Dispara segunda ronda
                                new WaitCommand(500),
                                new PathCommand(m_drive, PathsRed.Final)


                        ))



        );

    }

}

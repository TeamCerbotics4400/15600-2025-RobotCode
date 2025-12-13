package org.firstinspires.ftc.teamcode.Autos.AutosRed;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Commands.PathCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.SmartShootCommandRed;
import org.firstinspires.ftc.teamcode.Robot.Commands.TorretaCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

public class RedPruevba  extends SequentialCommandGroup {

    MecanumDriveTrain m_drive;
    Shooter m_shooter;
    Intake m_intake;
    Torreta m_torreta;
    Feeder m_feeder;
    Telemetry tl;

    public RedPruevba(MecanumDriveTrain m_drive, Shooter m_shooter, Intake m_intake, Torreta m_torreta, Feeder m_feeder,Telemetry tl){
        this.m_drive = m_drive;
        this.m_shooter = m_shooter;
        this.m_intake = m_intake;
        this.m_torreta = m_torreta;
        this.m_feeder = m_feeder;
        this.tl = tl;

        addRequirements(m_drive,m_shooter,m_intake,m_torreta,m_feeder);

        addCommands(

                new ParallelCommandGroup(


                        new TorretaCommand(m_drive,m_torreta,tl, new Pose2d(135, 135,Math.toRadians(0))),


                        new SequentialCommandGroup(

                                new InstantCommand(m_feeder::AllTrue),
                                new PathCommand(m_drive, PathsRed.Shoot1),//Posicion disparo



                                new ParallelCommandGroup(
                                        new InstantCommand(()->m_shooter.setRPM(3050)),

                                        new SequentialCommandGroup(
                                                new InstantCommand(()-> m_feeder.setPosition(0)),
                                                new WaitCommand(250),
                                                new InstantCommand(()-> m_feeder.setPosition(1920)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setCRSPower(1)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setPosition(1072)),
                                                // new InstantCommand(()-> m_feeder.setCRSPower(1)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setPosition(320)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setPosition(0)),
                                                new InstantCommand(()-> m_feeder.setCRSPower(0))

                                        )


                                ),

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


                                new WaitCommand(500),
                                new PathCommand(m_drive, PathsRed.Shoot2),//Posicion disparo
                                new WaitCommand(200),
                                new InstantCommand(()-> m_intake.setPower(0)),


                                new ParallelCommandGroup(
                                        new InstantCommand(()->m_shooter.setRPM(3050)),

                                        new SequentialCommandGroup(
                                                new InstantCommand(()-> m_feeder.setPosition(0)),
                                                new WaitCommand(250),
                                                new InstantCommand(()-> m_feeder.setPosition(1920)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setCRSPower(1)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setPosition(1072)),
                                                // new InstantCommand(()-> m_feeder.setCRSPower(1)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setPosition(320)),
                                                new WaitCommand(500),
                                                new InstantCommand(()-> m_feeder.setPosition(0)),
                                                new InstantCommand(()-> m_feeder.setCRSPower(0))

                                        )


                                ),
                                new WaitCommand(500),
                                new PathCommand(m_drive, PathsRed.Final)


                        ))
        );

    }

}

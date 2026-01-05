
package org.firstinspires.ftc.teamcode.Autos.Azul12;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Commands.GoToTargetTurret;
import org.firstinspires.ftc.teamcode.Robot.Commands.PathCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;


public class Azul12ArtsComm extends SequentialCommandGroup {
    private MecanumDriveTrain m_driveTrain;
    private Shooter m_shooter;
    private Torreta m_torreta;
    private Intake m_intake;

    public Azul12ArtsComm(MecanumDriveTrain m_driveTrain, Shooter m_shooter, Torreta m_torreta, Intake m_intake, Telemetry telemetry) {
        this.m_driveTrain = m_driveTrain;
        this.m_shooter = m_shooter;
        this.m_torreta = m_torreta;
        this.m_intake = m_intake;

        addRequirements(m_driveTrain, m_shooter, m_torreta, m_intake);


        addCommands(
            new WaitCommand(1),
                new ParallelCommandGroup(
                        new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, true),
                        new InstantCommand(() -> m_shooter.setRPM(2500)),

                        new SequentialCommandGroup(

                                new PathCommand(m_driveTrain, BluePaths12Arts.Shoot0),
                                new WaitCommand(500),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(1000),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, BluePaths12Arts.PreIntake1),

                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, BluePaths12Arts.PosIntake1),
                                        new InstantCommand(()-> m_intake.get())
                                ),


                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, BluePaths12Arts.PreOpenGate),
                                        new InstantCommand(()-> m_intake.stop())
                                ),

                                new PathCommand(m_driveTrain, BluePaths12Arts.PosOpenGate),

                                new PathCommand(m_driveTrain, BluePaths12Arts.Shoot1),
                                new WaitCommand(500),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(1000),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, BluePaths12Arts.PreIntake2),
                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, BluePaths12Arts.PosIntake2),
                                        new InstantCommand(()-> m_intake.get())
                                ),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, BluePaths12Arts.Shoot2),
                                new WaitCommand(500),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(1000),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, BluePaths12Arts.PreIntake3),
                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, BluePaths12Arts.PosIntake3),
                                        new InstantCommand(()-> m_intake.get())
                                ),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, BluePaths12Arts.Shoot3),
                                new WaitCommand(500),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(1000),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, BluePaths12Arts.Leave)

                        )

                )

        );

    }


}

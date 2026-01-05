package org.firstinspires.ftc.teamcode.Autos.Rojo6Duo;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autos.Azul6Duo.PathsAzul6Duo;
import org.firstinspires.ftc.teamcode.Autos.Rojo6Duo.PathRojo6Compl;
import org.firstinspires.ftc.teamcode.Robot.Commands.GoToTargetTurret;
import org.firstinspires.ftc.teamcode.Robot.Commands.PathCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

public class CommRojo6Compl extends SequentialCommandGroup {

    private MecanumDriveTrain m_driveTrain;
    private Shooter m_shooter;
    private Torreta m_torreta;
    private Intake m_intake;

    public CommRojo6Compl(MecanumDriveTrain m_driveTrain, Shooter m_shooter, Torreta m_torreta, Intake m_intake, Telemetry telemetry) {
        this.m_driveTrain = m_driveTrain;
        this.m_shooter = m_shooter;
        this.m_torreta = m_torreta;
        this.m_intake = m_intake;

        addRequirements(m_driveTrain, m_shooter, m_torreta, m_intake);

        addCommands(
                new WaitCommand(1),

                new ParallelCommandGroup(

                        new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, false),
                        new InstantCommand(() -> m_shooter.setRPM(2710)),

                        new SequentialCommandGroup(

                                new PathCommand(m_driveTrain, PathRojo6Compl.Shoot0),
                                new WaitCommand(700),
                                new InstantCommand(() -> m_intake.selectpower(.28,.28)),
                                new WaitCommand(3500),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, PathRojo6Compl.PreIntakeWall1),
                                new PathCommand(m_driveTrain, PathRojo6Compl.PreIntakeWall2),

                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, PathRojo6Compl.PosIntakeWall),
                                        new InstantCommand(() -> m_intake.get())
                                ),
                                new WaitCommand(700),

                                new PathCommand(m_driveTrain, PathRojo6Compl.PreShoot1),
                                new InstantCommand(()-> m_intake.stop()),


                                new PathCommand(m_driveTrain, PathRojo6Compl.Shoot1),
                                new WaitCommand(700),
                                new InstantCommand(() -> m_intake.selectpower(.28,.28)),
                                new WaitCommand(3000),

                                new InstantCommand(() -> m_intake.stop()),
                                new PathCommand(m_driveTrain, PathRojo6Compl.PreIntake2),

                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, PathRojo6Compl.PosIntake2),
                                        new InstantCommand(()-> m_intake.get())
                                ),
                                new WaitCommand(1)

                        )
                )
        );

    }
}

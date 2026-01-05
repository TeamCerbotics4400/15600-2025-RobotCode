
package org.firstinspires.ftc.teamcode.Autos.Rojo9mas3;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autos.Rojo9mas3.PathRojo9y3;
import org.firstinspires.ftc.teamcode.Robot.Commands.GoToTargetTurret;
import org.firstinspires.ftc.teamcode.Robot.Commands.PathCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;


public class CommRojo9y3 extends SequentialCommandGroup {
    private MecanumDriveTrain m_driveTrain;
    private Shooter m_shooter;
    private Torreta m_torreta;
    private Intake m_intake;

    public CommRojo9y3(MecanumDriveTrain m_driveTrain, Shooter m_shooter, Torreta m_torreta, Intake m_intake, Telemetry telemetry) {
        this.m_driveTrain = m_driveTrain;
        this.m_shooter = m_shooter;
        this.m_torreta = m_torreta;
        this.m_intake = m_intake;

        addRequirements(m_driveTrain, m_shooter, m_torreta, m_intake);


        addCommands(
                new WaitCommand(1),
                new ParallelCommandGroup(
                        new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, false),
                        new InstantCommand(() -> m_shooter.setRPM(2410)),

                        new SequentialCommandGroup(

                                new PathCommand(m_driveTrain, PathRojo9y3.Shoot0),
                                new WaitCommand(600),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(900),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, PathRojo9y3.PreIntake1),

                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain,PathRojo9y3.PosIntake1),
                                        new InstantCommand(()-> m_intake.get())
                                ),

                                new PathCommand(m_driveTrain, PathRojo9y3.Shoot1),
                                new WaitCommand(370),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(900),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, PathRojo9y3.PreIntake2),
                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, PathRojo9y3.PosIntake2),
                                        new InstantCommand(()-> m_intake.get())
                                ),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, PathRojo9y3.Shoot2),
                                new WaitCommand(370),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(900),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, PathRojo9y3.PreIntake3),
                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, PathRojo9y3.PosIntake3),
                                        new InstantCommand(()-> m_intake.get())
                                ),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, PathRojo9y3.Shoot3),
                                new WaitCommand(325),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(1200),


                                // new PathCommand(m_driveTrain, RedPaths12Arts.Leave),
                                new InstantCommand(()-> m_intake.stop())

                        )

                )

        );

    }


}

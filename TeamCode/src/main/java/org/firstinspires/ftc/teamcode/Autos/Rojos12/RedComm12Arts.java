
package org.firstinspires.ftc.teamcode.Autos.Rojos12;

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


public class RedComm12Arts extends SequentialCommandGroup {
    private MecanumDriveTrain m_driveTrain;
    private Shooter m_shooter;
    private Torreta m_torreta;
    private Intake m_intake;

    public RedComm12Arts(MecanumDriveTrain m_driveTrain, Shooter m_shooter, Torreta m_torreta, Intake m_intake, Telemetry telemetry) {
        this.m_driveTrain = m_driveTrain;
        this.m_shooter = m_shooter;
        this.m_torreta = m_torreta;
        this.m_intake = m_intake;

        addRequirements(m_driveTrain, m_shooter, m_torreta, m_intake);


        addCommands(
                new WaitCommand(1),
                new ParallelCommandGroup(
                        new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, false),
                        new InstantCommand(() -> m_shooter.setRPM(2480)),

                        new SequentialCommandGroup(

                                new PathCommand(m_driveTrain, RedPaths12Arts.Shoot0),
                                new WaitCommand(600),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(900),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, RedPaths12Arts.PreIntake1),

                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain,RedPaths12Arts.PosIntake1),
                                        new InstantCommand(()-> m_intake.get())
                                ),


                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, RedPaths12Arts.PreOpenGate),
                                        new InstantCommand(()-> m_intake.stop())
                                ),

                                new PathCommand(m_driveTrain, RedPaths12Arts.PosOpenGate),

                                new PathCommand(m_driveTrain, RedPaths12Arts.Shoot1),
                                new WaitCommand(500),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(900),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, RedPaths12Arts.PreIntake2),
                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, RedPaths12Arts.PosIntake2),
                                        new InstantCommand(()-> m_intake.get())
                                ),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, RedPaths12Arts.Shoot2),
                                new WaitCommand(500),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(900),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, RedPaths12Arts.PreIntake3),
                                new ParallelCommandGroup(
                                        new PathCommand(m_driveTrain, RedPaths12Arts.PosIntake3),
                                        new InstantCommand(()-> m_intake.get())
                                ),

                                new InstantCommand(()-> m_intake.stop()),

                                new PathCommand(m_driveTrain, RedPaths12Arts.Shoot3),
                                new WaitCommand(500),
                                new InstantCommand(() -> m_intake.autoFeed()),
                                new WaitCommand(1200),


                               // new PathCommand(m_driveTrain, RedPaths12Arts.Leave),
                                new InstantCommand(()-> m_intake.stop())

                        )

                )

        );

    }


}

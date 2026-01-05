package org.firstinspires.ftc.teamcode.Autos.Blue9yabrir;

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
    public class Azul9yabrirComm extends SequentialCommandGroup {
        private MecanumDriveTrain m_driveTrain;
        private Shooter m_shooter;
        private Torreta m_torreta;
        private Intake m_intake;

        public Azul9yabrirComm(MecanumDriveTrain m_driveTrain, Shooter m_shooter, Torreta m_torreta, Intake m_intake, Telemetry telemetry) {
            this.m_driveTrain = m_driveTrain;
            this.m_shooter = m_shooter;
            this.m_torreta = m_torreta;
            this.m_intake = m_intake;

            addRequirements(m_driveTrain, m_shooter, m_torreta, m_intake);


            addCommands(
                    new WaitCommand(1),
                    new ParallelCommandGroup(
                            new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, true),
                            new InstantCommand(() -> m_shooter.setRPM(2350)),

                            new SequentialCommandGroup(

                                    new PathCommand(m_driveTrain, BluePaths9yabrir.Shoot0),
                                    new WaitCommand(600),
                                    new InstantCommand(() -> m_intake.autoFeed()),
                                    new WaitCommand(900),

                                    new InstantCommand(()-> m_intake.stop()),

                                    new PathCommand(m_driveTrain, BluePaths9yabrir.PreIntake1),

                                    new ParallelCommandGroup(
                                            new PathCommand(m_driveTrain,BluePaths9yabrir.PosIntake1),
                                            new InstantCommand(()-> m_intake.get())
                                    ),


                                    new ParallelCommandGroup(
                                            new PathCommand(m_driveTrain, BluePaths9yabrir.PreOpenGate),
                                            new InstantCommand(()-> m_intake.stop())
                                    ),

                                    new PathCommand(m_driveTrain, BluePaths9yabrir.PosOpenGate),

                                    new PathCommand(m_driveTrain, BluePaths9yabrir.Shoot1),
                                    new WaitCommand(500),
                                    new InstantCommand(() -> m_intake.autoFeed()),
                                    new WaitCommand(900),

                                    new InstantCommand(()-> m_intake.stop()),

                                    new PathCommand(m_driveTrain, BluePaths9yabrir.PreIntake2),
                                    new ParallelCommandGroup(
                                            new PathCommand(m_driveTrain, BluePaths9yabrir.PosIntake2),
                                            new InstantCommand(()-> m_intake.get())
                                    ),

                                    new InstantCommand(()-> m_intake.stop()),

                                    new PathCommand(m_driveTrain, BluePaths9yabrir.Shoot2),
                                    new WaitCommand(500),
                                    new InstantCommand(() -> m_intake.autoFeed()),
                                    new WaitCommand(900),

                                    new InstantCommand(()-> m_intake.stop())
                            )

                    )

            );

        }


    }



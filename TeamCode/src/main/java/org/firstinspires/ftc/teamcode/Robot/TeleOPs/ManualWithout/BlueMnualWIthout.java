package org.firstinspires.ftc.teamcode.Robot.TeleOPs.ManualWithout;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Robot.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;
@TeleOp
public class BlueMnualWIthout  extends CommandOpMode {

    private MecanumDriveTrain m_driveTrain;
    private Intake m_intake;

    private Shooter m_shooter;
    private Torreta m_torreta;
    private Feeder m_feeder;

    @Override
    public void initialize() {
        m_driveTrain = new MecanumDriveTrain(hardwareMap, telemetry, true);


        m_shooter = new Shooter(hardwareMap, telemetry);
        m_feeder = new Feeder(hardwareMap,telemetry);
        m_torreta = new Torreta(hardwareMap, telemetry);
        m_intake = new Intake(hardwareMap, telemetry);

        register(m_driveTrain, m_shooter, m_torreta);schedule();

        GamepadEx g1 = new GamepadEx(gamepad1);
        GamepadEx g2 = new GamepadEx(gamepad2);

        // --- CHASIS ---
        m_driveTrain.setDefaultCommand(new DriveCommand(m_driveTrain,
                g1::getLeftY,
                g1::getLeftX,
                g1::getRightX,
                false));



        g2.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(
                        new InstantCommand(()-> m_feeder.Manual = false));






        g1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(
                        new ParallelCommandGroup(
                                new InstantCommand(()-> m_intake.setPower(1)),
                                //new InstantCommand(()-> m_feeder.isIntaking = true),
                                new InstantCommand(()-> m_feeder.Manual = true)
                        ))
                .whenReleased(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> m_intake.setPower(0)),
                                new InstantCommand(()-> m_feeder.Manual = true)
                                // new InstantCommand(()->m_feeder.isIntaking = false)
                        ));



        g1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(
                        new ParallelCommandGroup(
                                new InstantCommand(()-> m_intake.setPower(-1))

                        ))
                .whenReleased(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> m_intake.setPower(0))
                        ));

        g1.getGamepadButton(GamepadKeys.Button.Y)
                .whileHeld(new ParallelCommandGroup(
                                new RunCommand(
                                        () -> m_shooter.setRPM
                                                ((int)m_shooter.getInterpolatedShoot(m_driveTrain.obtenerDistanciaTarget(true)))),
                                new SequentialCommandGroup(
                                        new WaitCommand(3000).interruptOn(()-> m_shooter.atRPMs(m_shooter.getInterpolatedShoot(m_driveTrain.obtenerDistanciaTarget(true)), 30)),
                                        new RunCommand(()->m_feeder.setCRSPower(1))
                                )
                        )
                )

                .whenReleased(new ParallelCommandGroup(
                        new RunCommand(()-> m_feeder.setCRSPower(0)),
                        new RunCommand(() -> m_shooter.setPower(0))));


        g2.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileHeld(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(-1))))
                .whenReleased(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(0))));


        g2.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whileHeld(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(1))))
                .whenReleased(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(0))));

        g1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whileHeld(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(-1))))
                .whenReleased(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(0))));


        g1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whileHeld(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(1))))
                .whenReleased(new ParallelCommandGroup(
                        new RunCommand(() -> m_feeder.setCRSPower(0))));


        g2.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new ParallelCommandGroup(
                        new InstantCommand(()->m_feeder.setManualPower(-1)),
                        new InstantCommand(()-> m_feeder.Manual = true)))
                .whenReleased(new ParallelCommandGroup(
                        new InstantCommand(()->m_feeder.setManualPower(0)),
                        new InstantCommand(()-> m_feeder.Manual = true)));

        g2.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new ParallelCommandGroup(
                        new InstantCommand(()->m_feeder.setManualPower(1)),
                        new InstantCommand(()-> m_feeder.Manual = true)))
                .whenReleased(new ParallelCommandGroup(
                        new InstantCommand(()->m_feeder.setManualPower(0)),
                        new InstantCommand(()-> m_feeder.Manual = true)));

        g2.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()-> m_feeder.resetEncoders());








    }
}


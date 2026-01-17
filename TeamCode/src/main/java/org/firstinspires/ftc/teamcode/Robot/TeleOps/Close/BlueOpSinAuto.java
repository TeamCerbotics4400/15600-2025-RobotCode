package org.firstinspires.ftc.teamcode.Robot.TeleOps.Close;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Robot.Commands.AimTurretAtPoint;
import org.firstinspires.ftc.teamcode.Robot.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.GoToAngleTurret;
import org.firstinspires.ftc.teamcode.Robot.Commands.GoToTargetTurret;
import org.firstinspires.ftc.teamcode.Robot.Commands.Torreta.TorretaCommand;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;
@TeleOp(name = "BlueOpSinAutoClose", group = "Close")
public class BlueOpSinAuto extends CommandOpMode {

    private MecanumDriveTrain m_driveTrain;


    @Override
    public void initialize() {
        m_driveTrain = new MecanumDriveTrain(hardwareMap, telemetry, true, true);
        Intake m_intake = new Intake(hardwareMap);
        Shooter m_shooter = new Shooter(hardwareMap, telemetry);
        Torreta m_torreta = new Torreta(hardwareMap, telemetry);


        GamepadEx g1 = new GamepadEx(gamepad1);
        GamepadEx g2 = new GamepadEx(gamepad2);

        Trigger rightTrigger = new Trigger(() -> g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);
        Trigger leftTrigger = new Trigger(() -> g1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1);

        Trigger shooterReadyTrigger = rightTrigger.and(
                new Trigger(() -> m_shooter.isShooterReady(m_driveTrain.obtenerDistanciaTarget(true)))
        );

        //TORRETA
        //autoapuntado desde el enable
        //m_torreta.setDefaultCommand(new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, true));
        //m_torreta.setDefaultCommand(new AimTurretAtPoint(m_torreta, m_driveTrain, telemetry, true));
        m_torreta.setDefaultCommand(new TorretaCommand(m_driveTrain, m_torreta, telemetry, new Pose2d(10.5, 138, Math.toRadians(0))));


        /*chasis*/
        m_driveTrain.setDefaultCommand(new DriveCommand(m_driveTrain,
                () -> g1.getLeftY(),
                g1::getRightX,
                g1::getLeftX,
                true));

        /*INTAKE*/

        g1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(new RunCommand(() -> m_intake.setPower(1), m_intake));

        leftTrigger.whileActiveContinuous(new RunCommand(()-> m_intake.setPower(-1)));


        m_intake.setDefaultCommand(new RunCommand(m_intake::stop, m_intake));


//FEEDER

        rightTrigger
                .whileActiveContinuous(new ParallelCommandGroup(
                        new RunCommand(()->m_intake.setPower(.75)),
                        new RunCommand(()-> m_intake.shoot()))
                )
                .whenInactive(
                        new RunCommand(() -> m_intake.close())
                );


//SHOOTER


        g1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new GoToAngleTurret(m_torreta, telemetry, 90));
        g1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whenPressed(new GoToAngleTurret(m_torreta, telemetry, 200));

        g1.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(new InstantCommand(() -> m_shooter.goToSettedRPM()))
                .whenReleased(() -> m_shooter.offR(0));

        g1.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new InstantCommand(() -> m_shooter.setPower()))
                .whenReleased(() -> m_shooter.offR(0));

        g1.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new InstantCommand(() -> m_shooter.setRPM(2500)))
                .whenReleased(() -> m_shooter.offR(0));




       g1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(new InstantCommand(() -> m_shooter.setRPM((int)
                        m_shooter.getInterpolatedShoot(m_driveTrain.obtenerDistanciaTarget(true)))))
                .whenReleased(()-> m_shooter.setRPM(0));








        schedule( new RunCommand(()->{
            telemetry.update();
        }));
    }



}
package org.firstinspires.ftc.teamcode.Robot.TeleOps.Close;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Robot.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.GoToTargetTurret;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;
@TeleOp(name = "BlueOpClose", group = "Close")
public class BlueOP extends CommandOpMode {

    private MecanumDriveTrain m_driveTrain;


    @Override
    public void initialize() {
        m_driveTrain = new MecanumDriveTrain(hardwareMap, telemetry, true, true);

        com.pedropathing.geometry.Pose pose =
                (com.pedropathing.geometry.Pose) blackboard.get("endPose");

        m_driveTrain.getFollower().setPose(pose);
        telemetry.addData("Pose restored", pose);


        Intake m_intake = new Intake(hardwareMap);
        Shooter m_shooter = new Shooter(hardwareMap, telemetry);
        Torreta m_torreta = new Torreta(hardwareMap, telemetry);


        GamepadEx g1 = new GamepadEx(gamepad1);
        GamepadEx g2 = new GamepadEx(gamepad2);

        Trigger rightTrigger = new Trigger(() -> g1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1);

        Trigger shooterReadyTrigger = rightTrigger.and(
                new Trigger(() -> m_shooter.isShooterReady(m_driveTrain.obtenerDistanciaTarget(true)))
        );

//autoapuntado desde el enable
        m_torreta.setDefaultCommand(new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, true));


        /*chasis*/
        m_driveTrain.setDefaultCommand(new DriveCommand(m_driveTrain,
                () -> g1.getLeftY(),
                g1::getRightX,
                g1::getLeftX,
                false));

        /*INTAKE*/

        g1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(() -> m_intake.get())
                .whenReleased(() -> m_intake.stop());

        g1.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(()-> m_intake.out())
                .whenReleased(()-> m_intake.stop());




//FEEDER

        rightTrigger
                .whileActiveContinuous(() -> m_intake.feed())
                .whenInactive(() -> m_intake.stop());



/*
        g1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                        .whileHeld(()-> m_intake.feed())
                .whenReleased(()-> m_intake.stop());



        shooterReadyTrigger
                .whileActiveContinuous(() -> m_intake.feed())
                .whenInactive(() -> m_intake.stop());

*/


//TORRETA


        g1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whileHeld(() -> m_torreta.setPower(-1))
                .whenReleased(() -> m_torreta.setPower(0));


//SHOOTER

/*
        g1.getGamepadButton(GamepadKeys.Button.Y)
                .whileHeld(new InstantCommand(()-> m_shooter.setRPM(3000)))
                .whenReleased(()-> m_shooter.setRPM(0));

        g1.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(new InstantCommand(()-> m_shooter.setRPM(2800)))
                .whenReleased(()-> m_shooter.setRPM(0));

 */




        g1.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(new InstantCommand(() -> m_shooter.goToSettedRPM()))
                .whenReleased(() -> m_shooter.offR(0));


        g1.getGamepadButton(GamepadKeys.Button.B)
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
package org.firstinspires.ftc.teamcode.Robot.TeleOPs;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Robot.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.SmartShootCommand;
import org.firstinspires.ftc.teamcode.Robot.Commands.TorretaCommand;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

@TeleOp(name = " Teleop", group = "Competition")
public class RomanTeleOp extends CommandOpMode {

    private MecanumDriveTrain m_driveTrain;
    private Intake m_intake;

    private Shooter m_shooter;
    private Torreta m_torreta;
    private Feeder m_feeder;


    @Override
    public void initialize() {
        m_driveTrain = new MecanumDriveTrain(hardwareMap, telemetry, true);
        m_driveTrain.startPose(new Pose(0, 0, Math.toRadians(0)));  //64,8
        m_shooter = new Shooter(hardwareMap, telemetry);
       m_feeder = new Feeder(hardwareMap,telemetry);
        m_torreta = new Torreta(hardwareMap, telemetry);
        m_intake = new Intake(hardwareMap);

        register(m_driveTrain, m_shooter, m_torreta);

        GamepadEx g1 = new GamepadEx(gamepad1);
        GamepadEx g2 = new GamepadEx(gamepad2);

        // --- CHASIS ---
        m_driveTrain.setDefaultCommand(new DriveCommand(m_driveTrain,
                g1::getLeftY,
                g1::getLeftX,
                g1::getRightX,
                false));




        // --- INTAKE ---
        g1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(
                        new ParallelCommandGroup(
                                new InstantCommand(()-> m_intake.setPower(1)),
                                new InstantCommand(()-> m_feeder.isIntaking = true)
                        ))
                .whenReleased(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> m_intake.setPower(0)),
                                new InstantCommand(()->m_feeder.isIntaking = false)
                        ));

        g1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(
                        new ParallelCommandGroup(
                                new InstantCommand(()-> m_intake.setPower(-1)),
                                new InstantCommand(()-> m_feeder.isIntaking = true)
                        ))
                .whenReleased(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> m_intake.setPower(0)),
                                new InstantCommand(()->m_feeder.isIntaking = false)
                        ));
/*Torreta*/
        g1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new TorretaCommand(m_driveTrain, m_torreta, telemetry, new Pose2d(0, 144, Math.toRadians(0)))); //130 120


        /*g1.getGamepadButton(GamepadKeys.Button.Y).whileHeld(new ParallelCommandGroup(
                new RunCommand(
                        () -> m_shooter.setRPM
                                ((int)m_shooter.getInterpolatedShoot(m_driveTrain.obtenerDistanciaTarget(false)))),
               //new RunCommand(()-> m_feeder.setCRSPower(1)),
                new SequentialCommandGroup(
                        new RunCommand(() -> m_feeder.goToPosition(1,1835))
                                .interruptOn(()->m_feeder.atPosition(1835,5)),
                            new ParallelRaceGroup(

                                    new WaitCommand(3000)
                                    ),
                        new InstantCommand(()->m_feeder.setFalseNodeValue(0)),
                        new RunCommand(() -> m_feeder.goToPosition(1,1100))
                                .interruptOn(()->m_feeder.atPosition(1100,5)),
                        new ParallelRaceGroup(
                              new RunCommand(()->m_feeder.setCRSPower(1)),
                                new WaitCommand(1000)
                        ),
                        new InstantCommand(()->m_feeder.setFalseNodeValue(1)),
                        new RunCommand(() -> m_feeder.goToPosition(1,335))
                                .interruptOn(()->m_feeder.atPosition(330,5)),
                        new ParallelRaceGroup(
                        new InstantCommand(()->m_feeder.setFalseNodeValue(2)),
                                new WaitCommand(1000)
                        ),
                        new RunCommand(() -> m_feeder.goToPosition(0.8,0)).interruptOn(()->m_feeder.atPosition(0,1)),
                        new WaitCommand(500),
                        new InstantCommand(() -> m_feeder.resetAfterShoot())


                )))
                .whenReleased(new ParallelCommandGroup(
                        new InstantCommand(()-> m_feeder.globalSlotPosition = 0),
                        //new InstantCommand(() -> m_shooter.setPower(0)),
                        new InstantCommand(() -> m_feeder.setCRSPower(0)),
                        new RunCommand(()-> m_shooter.setRPM(0))));
*/

        g1.getGamepadButton(GamepadKeys.Button.Y)
                .whileHeld(new SmartShootCommand(
                        m_driveTrain,
                        m_shooter,
                        m_feeder,
                        telemetry
                ))
                .whenReleased(
                        new ParallelCommandGroup(
                                new InstantCommand(() -> m_feeder.setCRSPower(0)),
                                new InstantCommand(() -> m_shooter.setRPM(0)),
                                new InstantCommand(()->m_feeder.globalSlotPosition = 0),
                                new InstantCommand(()->m_feeder.globalTargetPosiion = 0),
                                new InstantCommand(()->m_feeder.setAllfalse()),
                            //    new RunCommand(()->m_feeder.goToPosition(0.1,-20)).interruptOn(()->m_feeder.limitSwitchGotPressed()),
                                new InstantCommand(()->m_feeder.goToPosition(0,0)),
                                new InstantCommand(()-> telemetry.log().add("It did reset"))
                        )
                );







        g1.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(new ParallelCommandGroup(
                        new RunCommand(() -> m_shooter.setPower(-.5)),
                        new RunCommand(() -> m_feeder.setCRSPower(-1))))
                .whenReleased(new ParallelCommandGroup(
                        new RunCommand(() -> m_shooter.setPower(0)),
                        new RunCommand(() -> m_feeder.setCRSPower(0))));

/**/

        g1.getGamepadButton(GamepadKeys.Button.B)
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



        g2.getGamepadButton(GamepadKeys.Button.Y)
                .whileHeld(()->m_feeder.goToPosition(0.3,2170))
                .whenReleased(()->m_feeder.setManualPower(0));

        g2.getGamepadButton(GamepadKeys.Button.X)
                .whileHeld(()-> m_shooter.setRPM(2500))
                .whenReleased(()-> m_shooter.setRPM(0));

        g2.getGamepadButton(GamepadKeys.Button.B)
                .whileHeld(()->m_feeder.setManualPower(-0.4))
                .whenReleased(()->m_feeder.setManualPower(0));

        g2.getGamepadButton(GamepadKeys.Button.A)
                        .whileHeld(()->m_feeder.setManualPower(0.4))
                                .whenReleased(()->m_feeder.setManualPower(0));


        g1.getGamepadButton(GamepadKeys.Button.A)
                .whileHeld(new ParallelCommandGroup(new RunCommand(()-> m_shooter.goToTargetRPM()),
                        new SequentialCommandGroup(
                        new WaitCommand(3000).interruptOn(()-> m_shooter.atRPMs(m_shooter.getRPMsDash(), 50)),
                        new RunCommand(()->m_feeder.setCRSPower(1))
                )
                )
        )
                .whenReleased(new ParallelCommandGroup(
                        new InstantCommand(()-> m_shooter.setRPM(0)),
                        new InstantCommand(()-> m_feeder.setCRSPower(0))
                ));

//378   1108   1810

        // Telemetría
        schedule(new RunCommand(() -> {
            telemetry.update();
        }));
    }
}
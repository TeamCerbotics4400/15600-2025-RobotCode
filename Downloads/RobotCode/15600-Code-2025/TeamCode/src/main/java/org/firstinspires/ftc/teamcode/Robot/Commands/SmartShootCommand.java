package org.firstinspires.ftc.teamcode.Robot.Commands;

import com.seattlesolvers.solverslib.command.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;

public class SmartShootCommand extends SequentialCommandGroup {

    int selectedIndex = 0;

    public Shooter shooter;
    public Telemetry tl;

    public Feeder feeder;
    public SmartShootCommand(MecanumDriveTrain drive,
                             Shooter shooter,
                             Feeder feeder,
                             Telemetry tl) {

        this.shooter = shooter;
        this.tl = tl;
        this.feeder = feeder;
        addCommands(

                // 1. Spin shooter to RPM
                new RunCommand(() -> shooter.setRPM(
                        (int) shooter.getInterpolatedShoot(
                                drive.obtenerDistanciaTarget(false)
                        ))
                ).interruptOn(() ->
                        shooter.atRPMs(
                                shooter.getInterpolatedShoot(
                                        drive.obtenerDistanciaTarget(false)
                                ), 1000)
                ),

                // 2. While feeder has balls → shoot next ball
                new SequentialCommandGroup(
                        new WaitUntilCommand(() -> feederHasBalls(feeder)),
                        new RepeatCommand(

                                new SequentialCommandGroup(

                                        // Select next slot
                                        new InstantCommand(() -> {
                                            feeder.globalTargetPosiion = findNextSlotToShoot(feeder);
                                        }),
                                        new InstantCommand(()->tl.log().add("hola")),
                                        // Move to slot
                                        new RunCommand(() ->
                                                feeder.goToPosition(1, feeder.globalTargetPosiion)

                                        ).interruptOn(() ->
                                                feeder.atPosition(feeder.globalTargetPosiion, 5)
                                        ),

                                        // Feed ball
                                        new ParallelRaceGroup(

                                           //     new RunCommand(() -> feeder.setCRSPower(1)),
                                                new WaitCommand(1000)
                                        ),

                                        // Mark slot empty
                                        new InstantCommand(() -> {
                                            feeder.setFalseNodeValue(selectedIndex);
                                        })

                                )

                        ).interruptOn(() -> !feederHasBalls(feeder))
                ),

                // 3. Return feeder to home
                new RunCommand(() ->
                        feeder.goToPosition(1, 0)
                ).interruptOn(() -> feeder.atPosition(0, 5)),

                // 4. Cleanup
                new InstantCommand(() -> {
                    shooter.setRPM(0);
                    feeder.setCRSPower(0);
                    feeder.globalTargetPosiion = 0;
                    feeder.resetAfterShoot();
                })
        );
    }

    // ---------------------------
    // Helper Functions
    // ---------------------------

    private boolean feederHasBalls(Feeder f) {
        return f.getSlotBallState(2) ||
                f.getSlotBallState(1) ||
                f.getSlotBallState(0);
    }

    // Fixed priority → slot 2 → 1 → 0
    private int findNextSlotToShoot(Feeder f) {
        if (f.getSlotBallState(2)) {
            selectedIndex = 2;
            return 315;
        } else if (f.getSlotBallState(1)) {
            selectedIndex = 1;
            return 1930;   // 2
        } else if (f.getSlotBallState(0)) {
            selectedIndex = 0;
        return 1150; //bien
    }
        return f.getPosition(); // no balls
    }

   @Override
   public void end(boolean interrupted){
        shooter.setRPM(0);
        feeder.globalTargetPosiion = 0;
        feeder.globalSlotPosition = 0;
   }
}

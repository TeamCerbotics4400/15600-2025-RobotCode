package org.firstinspires.ftc.teamcode.Robot.Commands;

import com.seattlesolvers.solverslib.command.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;

public class SmartShootCommandRed extends SequentialCommandGroup {
    int selectedIndex = 0;
    int numberOfBalls = 0;

    public Shooter shooter;
    public Telemetry tl;

    public Feeder feeder;

    public boolean Auto = false;
    public  int targetRPMs = 0;
    public SmartShootCommandRed(MecanumDriveTrain drive,
                                 Shooter shooter,
                                 Feeder feeder,
                                 Telemetry tl,boolean Auto, int targetRPMs) {

        this.shooter = shooter;
        this.tl = tl;
        this.feeder = feeder;
        this.Auto = Auto;
        this.targetRPMs = targetRPMs;
        addCommands(

                new InstantCommand(()->numberOfBalls = getDetectedBallCount(feeder)),
                // 1. Spin shooter to RPM
                new RunCommand(() -> shooter.setRPM(!Auto ?
                        (int) shooter.getInterpolatedShoot(
                                drive.obtenerDistanciaTarget(false)
                        ) : targetRPMs)
                ).interruptOn(() ->
                        true
                ),
                // 2. While feeder has balls → shoot next ball
                new SequentialCommandGroup(
                        new WaitUntilCommand(() -> feederHasBalls(feeder)),
                        new RepeatCommand(

                                new SequentialCommandGroup(

                                        new InstantCommand(()->tl.log().add("" + getDetectedBallCount(feeder))),

                                        // Select next slot
                                        new InstantCommand(() -> {
                                            feeder.globalTargetPosiion = findNextSlotToShoot(feeder);
                                        }),
                                        // Move to slot
                                        new RunCommand(() ->
                                                feeder.goToPosition(1, feeder.globalTargetPosiion)

                                        ).interruptOn(() ->
                                                feeder.atPosition(feeder.globalTargetPosiion, 100)
                                                        &&
                                                        shooter.atRPMs(
                                                                shooter.getInterpolatedShoot(
                                                                        drive.obtenerDistanciaTarget(false)
                                                                ), 1000)
                                        ),

                                        // Feed ball
                                        new ParallelRaceGroup(

                                                new SequentialCommandGroup(
                                                        new WaitCommand(150),
                                                        new RunCommand(() -> feeder.setCRSPower(1))
                                                ),
                                                new WaitCommand(900)
                                        ),
                                        new InstantCommand(()->tl.log().add("pow")),

                                        // Mark slot empty
                                        new InstantCommand(() -> {
                                            feeder.setFalseNodeValue(selectedIndex);
                                        }),
                                        new InstantCommand(()->feeder.setCRSPower(0

                                        ))

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

    private int getDetectedBallCount(Feeder f) {
        int count = 0;
        if (f.getSlotBallState(2)) {
            count++;
        }
        if (f.getSlotBallState(1)) {
            count++;
        }
        if (f.getSlotBallState(0)) {
            count++;
        }
        return count;
    }


    // Fixed priority → slot 2 → 1 → 0
    private int findNextSlotToShoot(Feeder f) {

        if(numberOfBalls == 3){
            if (f.getSlotBallState(2)) {
                tl.log().add("Index 2");
                selectedIndex = 2;
                return 1920;
            } else if (f.getSlotBallState(1)) {
                tl.log().add("Index 1");
                selectedIndex = 1;
                return 1072;   // 2
            } else if (f.getSlotBallState(0)) {
                tl.log().add("Index 0");
                selectedIndex = 0;
                return 370; //bien
            }


            return f.getPosition(); // no balls
        }else if(numberOfBalls == 2){
            if (f.getSlotBallState(1)) {
                selectedIndex = 1;
                return 1050;   // 2
            } else if (f.getSlotBallState(0)) {
                selectedIndex = 0;
                return 1950; //bien
            }
        }


        else if(numberOfBalls == 1) {
            return 1200;
        }

        return 0;
    }

    @Override
    public void end(boolean interrupted){
        shooter.setRPM(0);
        feeder.globalTargetPosiion = 0;
        feeder.globalSlotPosition = 0;
    }
}

package org.firstinspires.ftc.teamcode.Robot.Commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.ParallelRaceGroup;
import com.seattlesolvers.solverslib.command.RepeatCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.command.WaitUntilCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Feeder;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;

public class SmartShootCommandBLue extends SequentialCommandGroup {

    int selectedIndex = 0;
    int numberOfBalls = 0;
    int[] arraySequence;

    public Shooter shooter;
    public Telemetry tl;

    public Feeder feeder;
    int id;

    public boolean Auto = false;
    private int targetRPMs = 0;
    private int targetPositionTicks = 0;
    public SmartShootCommandBLue(MecanumDriveTrain drive,
                                Shooter shooter,
                                Feeder feeder,
                                Telemetry tl,boolean Auto,int targetRPMs, int id) {

        this.shooter = shooter;
        this.tl = tl;
        this.feeder = feeder;
        this.Auto = Auto;
        this.targetRPMs = targetRPMs;
        this.id = id;

        if(id == 21){   //GPP
            arraySequence = new int[]{0,1,1};
        }else if(id == 22){   //PGP
            arraySequence = new int[]{1,0,1};
        }else if(id == 23){   //PPG
            arraySequence = new int[]{1,1,0};
        }else{
            arraySequence = new int[]{0,0,0};
        }
        addCommands(

                new InstantCommand(()->numberOfBalls = getDetectedBallCount(feeder)),



                // 1. Spin shooter to RPM
                new RunCommand(() -> shooter.setRPM(!Auto ?
                        (int) shooter.getInterpolatedShoot(
                                drive.obtenerDistanciaTarget(true)
                        ) : targetRPMs)
                ).interruptOn(() ->
                        true
                ),

                // 2. While feeder has balls → shoot next ball
                new SequentialCommandGroup(
                        new WaitUntilCommand(() -> feederHasBalls(feeder)),
                        new RepeatCommand(

                                new SequentialCommandGroup(

                                        // Select next slot
                                        new InstantCommand(() -> {
                                            targetPositionTicks = findNextSlotToShoot(feeder);
                                        }),
                                        // Move to slot
                                        new RunCommand(() ->
                                                feeder.goToPosition(1, targetPositionTicks)

                                        ).interruptOn(() ->
                                                feeder.atPosition(targetPositionTicks, 100)
                                                        &&
                                                        shooter.atRPMs(
                                                                shooter.getInterpolatedShoot(
                                                                        drive.obtenerDistanciaTarget(true)
                                                                ), 1000)
                                        ),

                                        // Feed ball
                                        new ParallelRaceGroup(

                                                new SequentialCommandGroup(
                                                        new RunCommand(() -> feeder.setCRSPower(1))
                                                ),
                                                new WaitCommand(800)
                                        ),

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
                    feeder.globalTargetPosiion = 100;
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
        //-----------------Color----------------------
    if(Auto && numberOfBalls == 3 && (id == 21 || id == 22 || id == 23)) {
      //  tl.log().add(""+arraySequence[3-getDetectedBallCount(f)]);
        if (f.getSlotBallState(2) && f.getSlotColorState(2) == arraySequence[3-getDetectedBallCount(f)]) {
        //    tl.log().add("Index 2");
            selectedIndex = 2;
            return 1920;
        } else if (f.getSlotBallState(1) && f.getSlotColorState(0) == arraySequence[3-getDetectedBallCount(f)]) {
          //  tl.log().add("Index 1");
            selectedIndex = 1;
            return 1072;
        } else if (f.getSlotBallState(0) && f.getSlotColorState(1) == arraySequence[3-getDetectedBallCount(f)]) {
            //tl.log().add("Index 0");
            selectedIndex = 0;
            return 370;
            }
        } else {
            //-----------------not Color----------------------

            if (numberOfBalls == 3) {
              //  tl.log().add("using manual");
                if (f.getSlotBallState(2)) {
                    selectedIndex = 2;
                    return 1920;
                } else if (f.getSlotBallState(1)) {
                    selectedIndex = 1;
                    return 1072;   // 2
                } else if (f.getSlotBallState(0)) {
                    selectedIndex = 0;
                    return 370; //bien
                }
                return f.getPosition(); // no balls

            } else if (numberOfBalls == 2) {
                if (f.getSlotBallState(1)) {
                    selectedIndex = 1;
                    return 1050;   // 2
                } else if (f.getSlotBallState(0)) {
                    selectedIndex = 0;
                    return 1950; //bien
                }
            } else if (numberOfBalls == 1) {
                return 1200;
            }
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

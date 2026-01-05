
package org.firstinspires.ftc.teamcode.Autos.Rojo6Duo;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

import java.util.Timer;

//@Autonomous(name = "CompleRojo", group = "Test")
public class OpRojo6Compl extends CommandOpMode {



    MecanumDriveTrain m_driveTrain;
    private Timer pathTimer, actionTimer, opTimer;

    private int pathState;

    CommRojo6Compl commRojo6Compl;

    @Override
    public void initialize() {
        MecanumDriveTrain m_driveTrain = new MecanumDriveTrain(hardwareMap, telemetry, false);
        Shooter m_shooter = new Shooter(hardwareMap, telemetry);
        Torreta m_torreta = new Torreta(hardwareMap, telemetry);
        Intake m_intake = new Intake(hardwareMap);
        register(m_driveTrain, m_shooter, m_torreta, m_intake);

        Follower follower = m_driveTrain.getFollower();


        new PathRojo6Compl(follower);

        CommRojo6Compl sequence = new CommRojo6Compl(

                m_driveTrain,
                m_shooter,
                m_torreta,
                m_intake,
                telemetry
        );

        schedule(new ParallelCommandGroup(sequence), new RunCommand(() -> {
            blackboard.put("endPose", follower.getPose());
            blackboard.put("torretaPosition", m_torreta.getTurretPosition());
        }

        ));

        schedule( new RunCommand(()->{
            telemetry.update();
            com.pedropathing.geometry.Pose pose =
                    (com.pedropathing.geometry.Pose) blackboard.get("endPose");
            telemetry.addData("Blackboard Y", pose.getY());
        }));

    }

}


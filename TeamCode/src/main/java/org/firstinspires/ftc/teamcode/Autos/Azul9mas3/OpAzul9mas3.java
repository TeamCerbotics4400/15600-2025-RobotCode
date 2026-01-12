package org.firstinspires.ftc.teamcode.Autos.Azul9mas3;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;

import org.firstinspires.ftc.teamcode.Autos.Azul12.Azul12ArtsComm;
import org.firstinspires.ftc.teamcode.Autos.Rojo9mas3.CommRojo9y3;
import org.firstinspires.ftc.teamcode.Autos.Rojo9mas3.PathRojo9y3;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

import java.util.Timer;

@Autonomous(name = "9y3BLUE", group = "Test")
public class OpAzul9mas3 extends CommandOpMode {

    MecanumDriveTrain m_driveTrain;
    private Timer pathTimer, actionTimer, opTimer;

    private int pathState;


    @Override
    public void initialize() {
        MecanumDriveTrain m_driveTrain = new MecanumDriveTrain(hardwareMap, telemetry, true, true);
        Shooter m_shooter = new Shooter(hardwareMap, telemetry);
        Torreta m_torreta = new Torreta(hardwareMap, telemetry);
        Intake m_intake = new Intake(hardwareMap);
        register(m_driveTrain, m_shooter, m_torreta, m_intake);

        Follower follower = m_driveTrain.getFollower();
        //  m_torreta.setDefaultCommand(new GoToTargetTurret(m_torreta, m_driveTrain, telemetry, true));

        new PathsAzul9mas3(follower);

        CommAzul9mas3 sequence = new CommAzul9mas3(

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



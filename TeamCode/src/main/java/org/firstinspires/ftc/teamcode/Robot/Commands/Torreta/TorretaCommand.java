package org.firstinspires.ftc.teamcode.Robot.Commands.Torreta;


import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

import java.util.Locale;

public class TorretaCommand extends CommandBase {
    MecanumDriveTrain m_drive;
    Torreta m_turret;

    private final Pose2d targetPoint;
    private double targetAngle;
    private double lastHeading;
    private double angularVelocity;
    private long lastTime;

    private final Telemetry telemetry;

    // Tunables
    private static final double MAX_TURN_POWER = 0.75;
    private static final double MIN_TURN_POWER = 0.25;
    private static final double ANGULAR_VELOCITY_TOLERANCE = 0.05;

    public TorretaCommand(MecanumDriveTrain m_drive, Torreta m_turret, Telemetry telemetry, Pose2d targetPoint){
        this.m_drive = m_drive;
        this.m_turret = m_turret;
        this.telemetry = telemetry;
        this.targetPoint = targetPoint;

        addRequirements(m_turret);
    }
    public double normalizedAngle(double angle){
        angle = angle % 360;
        if (angle < 0) angle += 360;

        return  angle;
    }
    @Override
    public void initialize() {
       Pose robotPose = m_drive.getPose();
        double dx = targetPoint.getY() - robotPose.getY();
        double dy = targetPoint.getX() - robotPose.getX();

        targetAngle = normalizedAngle(Math.toDegrees(Math.atan2(dy, dx)));
    }

    @Override
    public void execute(){
        Pose robotPose = m_drive.getPose();
        double robotX = robotPose.getX();
        double robotY = robotPose.getY();
        double dx = targetPoint.getX() - robotX;
        double dy = targetPoint.getY() - robotY;

        targetAngle = (Math.toDegrees(Math.atan2(dy, dx)));

        double heading =  normalizedAngle(Math.toDegrees(m_drive.getPose().getHeading()));

        double turretSetpoint = (targetAngle - heading);
        double error = turretSetpoint - (-m_turret.ticksToDegrees());
        if (turretSetpoint > 17) turretSetpoint = 17;
        if (turretSetpoint < -350) turretSetpoint = -350;


        m_turret.setTurretPosition(turretSetpoint);
        telemetry.addData("Target Angle", targetAngle);
        telemetry.addData("Turret setPoint", turretSetpoint);
        telemetry.addData("Heading Command",heading);
        telemetry.addData("dx", dx);
        telemetry.addData("dy", dy);
        telemetry.addData("Target x", targetPoint.getX());
        telemetry.addData("Target y", targetPoint.getY());
        telemetry.addData("robot pose X", robotX);
        telemetry.addData("robot pose Y", robotY);
        telemetry.addData("Error", error);
        String pose = String.format(
                Locale.US,
                "X: %.3f, Y: %.3f, H: %.3f",
                m_drive.getPose().getY(),
                m_drive.getPose().getX(),
                heading
        );
        telemetry.addData(getSubsystem(), pose);
    }

    @Override
    public boolean isFinished() {
        // Must be within angular tolerance AND almost not rotating
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.setPower(0);
    }

}
package org.firstinspires.ftc.teamcode.Robot.Commands;


import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;


import java.util.Locale;

public class GoToAngleTurret extends CommandBase {
    Torreta m_turret;

    private double targetAngle;
    PIDController controller;
    private Telemetry telemetry;

    // Tunables
    private static final double MAX_TURN_POWER = 0.75;
    private static final double MIN_TURN_POWER = 0.25;
    private static final double ANGULAR_VELOCITY_TOLERANCE = 0.05;

    public GoToAngleTurret( Torreta m_turret, Telemetry telemetry, double targetAngle){
        this.m_turret = m_turret;
        this.telemetry = telemetry;
        this.targetAngle = targetAngle;
        controller = new PIDController(0.0142, 0, 0);
    }
    public double normalizedAngle(double angle){
        angle = angle % 360;
        if (angle < 0) angle += 360;

        return  angle;
    }
    @Override
    public void initialize() {
        controller.setSetPoint(targetAngle);
    }

    @Override
    public void execute(){
        double currentAngle = m_turret.getTurretPosition();

        double outputPower = -controller.calculate(currentAngle);

        double clampedPower = Math.min(Math.abs(outputPower), 0.75) * Math.signum(outputPower);

        m_turret.setPower(clampedPower);

        telemetry.addData("Target Angle", targetAngle);
        telemetry.addData("Current Angle", currentAngle);
        telemetry.addData("PID Power", outputPower);
        telemetry.addData("Clamped Power", clampedPower);    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.setPower(0);
    }

}
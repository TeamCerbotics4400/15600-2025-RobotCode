package org.firstinspires.ftc.teamcode.Robot.Commands;

// ... (tus importaciones existentes)
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

import java.lang.Math;
@Configurable
@Config

public class GoToTargetTurret extends CommandBase {
    Torreta m_turret;
    MecanumDriveTrain m_drive;

    private final Pose targetPose;
    double absoluteTargetAngleRad;
    double turretRelativeAngleDeg;

    FtcDashboard ftcDashboard;
    private final boolean isBlueAlliance;

    PIDController controller;
    private Telemetry telemetry;

    public static  double kP = 0.018;//0.018
    public static  double kI = 0.1;//0.1
    public static  double kD = 0.0032;//0.0032

    private static final double MAX_ANGLE = 21;//21
    private static final double MIN_ANGLE = -249;//-249
    private static final double ANGLE_TOLERANCE = 2.0;
    public GoToTargetTurret(Torreta m_turret, MecanumDriveTrain m_drive, Telemetry telemetry, boolean isBlueAlliance){
        this.m_turret = m_turret;
        this.m_drive = m_drive;
        this.telemetry = telemetry;
        this.isBlueAlliance = isBlueAlliance;
        this.ftcDashboard = FtcDashboard.getInstance();

        if (isBlueAlliance) {//BLUE
            this.targetPose = new Pose(32, 137);//cancha 2 mucho x no jalo con 12
        } else {//RED
            this.targetPose = new Pose(140, 140);
        }

        controller = new PIDController(kP, kI, kD);
        addRequirements(m_turret);
    }

    private double calculateTargetTurretAngle() {
        Pose robotPose = m_drive.getPose();
        double robotX = robotPose.getX();
        double robotY = robotPose.getY();

        double robotHeadingRad = robotPose.getHeading();

        double deltaX = targetPose.getX() - robotX;
        double deltaY = targetPose.getY() - robotY;

        absoluteTargetAngleRad = Math.atan2(deltaY, deltaX);

        double turretRelativeAngleRad = absoluteTargetAngleRad - robotHeadingRad;

        turretRelativeAngleDeg = Math.toDegrees(turretRelativeAngleRad);

        double limitedAngle = Math.max(MIN_ANGLE, Math.min(MAX_ANGLE, turretRelativeAngleDeg));

        return limitedAngle;
    }

    @Override
    public void initialize() {
        controller.reset();
    }

    @Override
    public void execute(){

        controller.setPID(kP, kI, kD);

        controller.setPID(kP,Math.abs(controller.getPositionError()) < 0.9 ?0:kI,kD);

        double targetAngle = calculateTargetTurretAngle();
        double currentAngle = m_turret.getTurretPosition();

        double error = targetAngle - currentAngle;

        double outputPower = -controller.calculate(error);

        double clampedPower = Math.min(Math.abs(outputPower), 0.75) * Math.signum(outputPower);

        m_turret.setPower(0);

        telemetry.addData("Target (deg)", targetAngle);
        telemetry.addData("Calculate Target (deg)", calculateTargetTurretAngle());
        telemetry.addData("Target Absoluto", Math.toDegrees(absoluteTargetAngleRad));
        telemetry.addData("Target Relativo", turretRelativeAngleDeg);
        telemetry.addData("Current (deg)", currentAngle);
        telemetry.addData("Error", error);
        telemetry.addData("Power", clampedPower);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("PID Output", clampedPower);
        packet.put("Graph degrees",currentAngle );
        packet.put("Graph setpoint", targetAngle);

        ftcDashboard.sendTelemetryPacket(packet);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_turret.setPower(0);
    }
}
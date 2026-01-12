package org.firstinspires.ftc.teamcode.Robot.Commands;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.Robot.Subsystems.Torreta;

@Config
@Configurable
public class AimTurretAtPoint extends CommandBase {

    private final Torreta turret;
    private final MecanumDriveTrain drive;
    private final Telemetry telemetry;

    private final Pose targetPose;
    private final PIDController controller;

    private static final double MAX_TURN_POWER = 0.75;
    private static final double ANGLE_TOLERANCE_DEG = 1.5;
    private static final double MAX_ANGLE = 30;
    private static final double MIN_ANGLE = -305;

    public static  double kP = 0.018;//0.0142
    public static  double kI = 0.05;
    public static  double kD = 0;

    public AimTurretAtPoint(
            Torreta turret,
            MecanumDriveTrain drive,
            Telemetry telemetry,
            boolean isBlueAlliance
    ) {
        this.turret = turret;
        this.drive = drive;
        this.telemetry = telemetry;
        targetPose = isBlueAlliance ? new Pose(12, 146) : new Pose(140, 140);;

        controller = new PIDController(kP, kI, kD);

        addRequirements(turret);
    }

    @Override
    public void initialize() {
        // Nada aquí, el setpoint se calcula en tiempo real
    }

    @Override
    public void execute() {
        Pose robotPose = drive.getPose();

        // Vector al target
        double dx = targetPose.getX() - robotPose.getX();
        double dy = targetPose.getY() - robotPose.getY();

        // Ángulo absoluto hacia el target (rad)
        double globalAngleRad = Math.atan2(dy, dx);

        // Heading del robot (rad)
        double robotHeadingRad = wrapRad(robotPose.getHeading());

        // Ángulo relativo de la torreta (rad)
        double turretTargetRad = globalAngleRad - robotHeadingRad;

        // Convertir a grados
        double turretTargetDeg = Math.toDegrees(turretTargetRad);

        double currentAngle = turret.getTurretPosition();

// ERROR ANGULAR CONTINUO
        double errorDeg = wrapDeg(turretTargetDeg - currentAngle);

        double output = -controller.calculate(errorDeg);

        double clamped = Math.min(Math.abs(output), MAX_TURN_POWER) * Math.signum(output);
        //>_<
        boolean tryingToGoPastMax = currentAngle >= MAX_ANGLE && clamped > 0;
        boolean tryingToGoPastMin = currentAngle <= MIN_ANGLE && clamped < 0;

        if (tryingToGoPastMax || tryingToGoPastMin) {
            turret.setPower(0);
        } else {
            turret.setPower(clamped);
        }

        telemetry.addData("Target X", targetPose.getX());
        telemetry.addData("Target Y", targetPose.getY());
        telemetry.addData("Turret Target Deg", wrapDeg(turretTargetDeg));
        telemetry.addData("Turret Current Deg", wrapDeg(currentAngle));
        telemetry.addData("Error", errorDeg);
        telemetry.addData("PID Output", output);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        turret.setPower(0);
    }

    private double wrapRad(double angleRad) {
        angleRad %= (2 * Math.PI);
        if (angleRad > Math.PI) angleRad -= 2 * Math.PI;
        if (angleRad < -Math.PI) angleRad += 2 * Math.PI;
        return angleRad;
    }

    private double wrapDeg(double angleDeg) {
        angleDeg %= 360;
        if (angleDeg > 180) angleDeg -= 360;
        if (angleDeg < -180) angleDeg += 360;
        return angleDeg;
    }



}

package org.firstinspires.ftc.teamcode.Robot.Subsystems;


import static com.sun.tools.javac.code.Lint.LintCategory.PATH;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.paths.HeadingInterpolator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Configurable
@Config

public class Torreta extends SubsystemBase {

    CRServo torreta, torreta2;
    DcMotorEx intake;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    FtcDashboard ftcDashboard;


    public Torreta(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        torreta = hardwareMap.get(CRServo.class, "torreta");
        torreta2 = hardwareMap.get(CRServo.class, "torreta2");
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        torreta.setDirection(DcMotorSimple.Direction.FORWARD);
        torreta2.setDirection(DcMotorSimple.Direction.FORWARD);

        this.ftcDashboard = FtcDashboard.getInstance();



    }
    private double normalizedAngle(double angle) {
        angle %= 360;
        if (angle < 0) angle += 360;
        return angle;
    }
    public void setPower(double power) {
        torreta.setPower(power);
        torreta2.setPower(power);
    }

    public double getTurretPosition(){
        double currentDegrees = ticksToDegrees();
        return currentDegrees - 4;
    }


    public double ticksToDegrees(){
        double motorTicksPerRev = 8192;
        int ticks = -intake.getCurrentPosition();
        double gearRatio = 7;
        double ticksPerTurretRev = motorTicksPerRev * gearRatio;
        return ((ticks / ticksPerTurretRev) * 360)+4;
    }



    @Override
    public void periodic() {



        // Telemetría
        telemetry.addData("Torreta Posicion", intake.getCurrentPosition());
        telemetry.addData("Torreta grados", getTurretPosition());

    }


}
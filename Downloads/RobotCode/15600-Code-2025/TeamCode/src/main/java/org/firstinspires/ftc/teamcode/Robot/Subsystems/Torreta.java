package org.firstinspires.ftc.teamcode.Robot.Subsystems;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Configurable
@Config
public class Torreta extends SubsystemBase {

    CRServo torreta;
    DcMotorEx intake;
    private double oldP, oldI, oldD, oldF;

    private static double P = 0.015 ;//0.015
    private static double I = 0.05;
    private static double D = 0.0015;//

    public static PIDController pidController = new PIDController(P,I,D);

    private boolean pidMode = false;

    HardwareMap hardwareMap;
    Telemetry telemetry;


    public Torreta(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        pidController.reset();
        torreta = hardwareMap.get(CRServo.class, "torreta");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
       // intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private double normalizedAngle(double angle) {
        angle %= 360;
        if (angle < 0) angle += 360;
        return angle;
    }

    public double getPIDOutput(){
        return pidController.calculate(ticksToDegrees());
    }
    public void setTurretPosition(double degrees){
        pidMode = true;
        pidController.setSetPoint(degrees);
    }
    public void setPower(double power) {
        torreta.setPower(power);
    }

    public double ticksToDegrees(){
        double motorTicksPerRev = 8192;
        int ticks = -intake.getCurrentPosition();
        double gearRatio = 10.5;
        double ticksPerTurretRev = motorTicksPerRev * gearRatio;
        return ((ticks / ticksPerTurretRev) * 360);

    }

    @Override
    public void periodic() {
        double currentDegrees = ticksToDegrees();

        pidController.setPID(P,Math.abs(pidController.getPositionError()) < 0.9 ?0:I,D);

        double power = pidController.calculate(currentDegrees);

        if ((currentDegrees <= -100 && power < 0) || (currentDegrees >= 90
                && power > 0)) {
            power = 0;
        }
//65   -196
        if(pidMode){
        torreta.setPower(power);
        }

        // Telemetría
        //elemetry.addData("Torreta Posicion", intake.getCurrentPosition());
        telemetry.addData("Torreta grados", currentDegrees);
       // telemetry.addData("Torreta setpoint", pidController.getSetPoint());
     //   telemetry.addData("PID output", power);
        //telemetry.addData("Error extention", pidController.getPositionError());
        //telemetry.addData("I value", pidController.getI());
/*
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("PID Output", power);
        packet.put("Graph degrees",currentDegrees );
        packet.put("Graph setpoint", pidController.getSetPoint());

        dashboard.sendTelemetryPacket(packet);*/
    }

}
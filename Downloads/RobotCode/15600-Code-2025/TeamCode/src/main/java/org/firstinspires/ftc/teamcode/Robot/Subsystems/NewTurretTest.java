package org.firstinspires.ftc.teamcode.Robot.Subsystems;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Configurable
@Config

public class NewTurretTest extends SubsystemBase {

    CRServo torreta;
    DcMotorEx intake;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    FtcDashboard ftcDashboard;


    public NewTurretTest(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        torreta = hardwareMap.get(CRServo.class, "torreta");
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        torreta.setDirection(DcMotorSimple.Direction.REVERSE);

        this.ftcDashboard = FtcDashboard.getInstance();
    }
    private double normalizedAngle(double angle) {
        angle %= 360;
        if (angle < 0) angle += 360;
        return angle;
    }
    public void setPower(double power) {
        torreta.setPower(power);
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
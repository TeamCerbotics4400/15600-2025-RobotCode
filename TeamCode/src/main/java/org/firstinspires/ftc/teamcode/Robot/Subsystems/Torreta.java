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

    //CRServo torreta, torreta2;
    DcMotorEx intake, torreta;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    FtcDashboard ftcDashboard;

    public static double P = 0.05 ;//0.06
    public static double I = 0.1;
    public static double D = 0.0009;//

    public static PIDController pidController = new PIDController(P,I,D);
    private boolean pidMode = false;


    public Torreta(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        //torreta = hardwareMap.get(CRServo.class, "torreta");
        //torreta2 = hardwareMap.get(CRServo.class, "torreta2");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        torreta = hardwareMap.get(DcMotorEx.class, "torreta");

        //intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //torreta.setDirection(DcMotorSimple.Direction.FORWARD);
        //torreta2.setDirection(DcMotorSimple.Direction.FORWARD);

        this.ftcDashboard = FtcDashboard.getInstance();



    }
    private double normalizedAngle(double angle) {
        angle %= 360;
        if (angle < 0) angle += 360;
        return angle;
    }
    public void setPower(double power) {
        torreta.setPower(power);
        //torreta2.setPower(power);
    }

    public void setTurretPosition(double degrees){
        pidMode = true;
        pidController.setSetPoint(degrees);
    }

    public double getTurretPosition(){
        double currentDegrees = ticksToDegrees();
        return -currentDegrees;
    }


    public double ticksToDegrees(){
        double motorTicksPerRev = 8192;
        int ticks = intake.getCurrentPosition();
        double gearRatio = 5;
        double ticksPerTurretRev = motorTicksPerRev * gearRatio;
        return ((ticks / ticksPerTurretRev) * 360);
    }



    @Override
    public void periodic() {
        double currentDegrees = -ticksToDegrees();

        pidController.setPID(P,Math.abs(pidController.getPositionError()) < 0.9 ?0:I,D);

        double power = pidController.calculate(currentDegrees);
        //                     -339                                     35
        if ((currentDegrees <= -350 && power < 0) || (currentDegrees >= 17 && power > 0)) {
            power = 0;
        }
//65   -196
        if(pidMode){
            torreta.setPower(power);
        }


        // Telemetría
        telemetry.addData("Torreta Posicion", intake.getCurrentPosition());
        telemetry.addData("Torreta grados", currentDegrees);

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("PID Output", power);
        packet.put("Graph degrees",currentDegrees );
        packet.put("Graph setpoint", pidController.getSetPoint());

        ftcDashboard.sendTelemetryPacket(packet);

    }


}
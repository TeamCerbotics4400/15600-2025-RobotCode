package org.firstinspires.ftc.teamcode.Robot.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.utils.InterpolatingDouble;
import org.firstinspires.ftc.teamcode.utils.InterpolatingTreeMap;

@Configurable
@Config

public class Shooter extends SubsystemBase {
    static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kDistanceToShooterSpeed = new InterpolatingTreeMap<>();
    private static Shooter instance = null;

    static {
        kDistanceToShooterSpeed.put(new InterpolatingDouble(32.91), new InterpolatingDouble(2900.0));//2520
        kDistanceToShooterSpeed.put(new InterpolatingDouble(72.74), new InterpolatingDouble(3200.0));//2520
        kDistanceToShooterSpeed.put(new InterpolatingDouble(110.0), new InterpolatingDouble(3500.0));//3065
        kDistanceToShooterSpeed.put(new InterpolatingDouble(125.84), new InterpolatingDouble(3750.0));
       // kDistanceToShooterSpeed.put(new InterpolatingDouble(150.0), new InterpolatingDouble(3650.0));
    }


    public  static  double P = 30;//30;
    public static double I = 0;
    public static double D = 0;
    public  static   double F = 15;//16;
    public static double targetSpeed = 0;

    public static double power = 1;

    public static double tunnRPMs = 0;

    public static double offRPMs = 0;

    private double oldP, oldI, oldD, oldF;

    DcMotorEx shooter1, shooter2;
    HardwareMap hwp;
    Telemetry telemetry;


    public Shooter(HardwareMap hwp, Telemetry telemetry) {
        this.hwp = hwp;
        this.telemetry = telemetry;

        shooter1 = hwp.get(DcMotorEx.class, "shooter");
        shooter1.setDirection(DcMotorSimple.Direction.FORWARD);//motor1
        PIDFCoefficients pidOrig = shooter1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        PIDFCoefficients pid = new PIDFCoefficients(P,I,D,F);
        shooter1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);


        shooter2 = hwp.get(DcMotorEx.class, "shooter2");
        shooter2.setDirection(DcMotorSimple.Direction.REVERSE);//motor1
        PIDFCoefficients pidOrig2 = shooter2.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);

        updatePIDF();



    }

    public void updatePIDF() {

        if (P != oldP || I != oldI || D != oldD || F != oldF) {
            PIDFCoefficients pid = new PIDFCoefficients(P, I, D, F);
            shooter1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);
            shooter2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);


            oldP = P; oldI = I; oldD = D; oldF = F;
        }
    }



    public void setPower(){
        shooter1.setPower(power);
        shooter2.setPower(power);
    }

    public double getInterpolatedShoot(double distance){
        return kDistanceToShooterSpeed.getInterpolated(new InterpolatingDouble(distance)).value;
    }

    public boolean isShooterReady(double distance) {
        return Math.abs(getRPMS() - getInterpolatedShoot(distance)) <= 30;
    }

    public void goToSettedRPM(){
        double TICKS_PER_REV = 28.0; // REV HD Hex motor (no gearbox)
        double gearRatio = 1;

        double targetTicksPerSeconds = (targetSpeed * TICKS_PER_REV * gearRatio) / 60;
        shooter1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter1.setVelocity(targetTicksPerSeconds);
        shooter2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter2.setVelocity(targetTicksPerSeconds);
    }

    public void setRPM(int RPM){
        double TICKS_PER_REV = 28; // REV HD Hex motor (no gearbox)
        double gearRatio = 1;

        double targetTicksPerSeconds = (RPM * TICKS_PER_REV * gearRatio) / 60;
        shooter1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter1.setVelocity(targetTicksPerSeconds);
        shooter2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter2.setVelocity(targetTicksPerSeconds);
    }

    public void offR(int RPM){
        double TICKS_PER_REV = 28.0; // REV HD Hex motor (no gearbox)
        double gearRatio = 1;

        double targetTicksPerSeconds = (offRPMs * TICKS_PER_REV * gearRatio) / 60;
        shooter1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter1.setVelocity(targetTicksPerSeconds);
        shooter2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter2.setVelocity(targetTicksPerSeconds);
    }

    public double getLeftRPM(){
        double ticksPerSecond = shooter1.getVelocity();// ticks/sec
        double TICKS_PER_REV = 28.0; // REV HD Hex motor (no gearbox)
        double gearRatio = 1;

        double rpm = (ticksPerSecond * 60) / (TICKS_PER_REV * gearRatio);
        return rpm;
    }

    public double getRightRPM(){
        double ticksPerSecond = shooter2.getVelocity();// ticks/sec
        double TICKS_PER_REV = 28.0; // REV HD Hex motor (no gearbox)
        double gearRatio = 1;

        double rpm = (ticksPerSecond * 60) / (TICKS_PER_REV * gearRatio);
        return rpm;
    }


    public double getRPMS(){
        return (getLeftRPM() + getRightRPM())/2;
    }






    @Override
    public void periodic() {
        updatePIDF();

        telemetry.addData("shooter1 RPM", getLeftRPM());
        telemetry.addData("shooter2 RPMS", getRightRPM());
        telemetry.addData("shooter RPMS", getRPMS());
        telemetry.addData("power", shooter1.getPower());
        telemetry.addData("vel sh1", shooter1.getVelocity());
        telemetry.addData("vel sh2", shooter2.getVelocity());


    }
}

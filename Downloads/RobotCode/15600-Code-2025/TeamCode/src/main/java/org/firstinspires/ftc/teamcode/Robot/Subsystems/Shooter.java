package org.firstinspires.ftc.teamcode.Robot.Subsystems;


import com.acmerobotics.dashboard.config.Config;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.InterpolatingDouble;
import org.firstinspires.ftc.teamcode.utils.InterpolatingTreeMap;

@Configurable
@Config

public class Shooter extends SubsystemBase {

    public static double offset = 0;

    static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kDistanceToShooterSpeed = new InterpolatingTreeMap<>();
    private static Shooter instance = null;


    static {
        /*Lejos*/
        kDistanceToShooterSpeed.put(new InterpolatingDouble(158.69+ offset), new InterpolatingDouble(3900.0));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(148.69+ offset), new InterpolatingDouble(3800.0));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(128.93), new InterpolatingDouble(3650.0));
        /*Medio*/
        kDistanceToShooterSpeed.put(new InterpolatingDouble(85.91), new InterpolatingDouble(3150.0));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(79.369), new InterpolatingDouble(3000.0));

        /*Cerca*/
        kDistanceToShooterSpeed.put(new InterpolatingDouble(63+offset), new InterpolatingDouble(2900.0));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(58.34+offset), new InterpolatingDouble(2750.0));
        kDistanceToShooterSpeed.put(new InterpolatingDouble(31.25+offset), new InterpolatingDouble(2600.0));
    }



    public  static  double P = 35;
    public static double I = 0;
    public static double D = 0;
    public  static   double F = 13.7;

    public static double targetSpeed = 0;


    private double oldP, oldI, oldD, oldF;

    DcMotorEx shooter1, shooter2;
    HardwareMap hwp;
    Telemetry telemetry;
    //FtcDashboard dashboard;
    public Shooter(HardwareMap hwp, Telemetry telemetry) {
        this.hwp = hwp;
        this.telemetry = telemetry;

        shooter1 = hwp.get(DcMotorEx.class, "shooter");
        shooter1.setDirection(DcMotorSimple.Direction.REVERSE);//motor1
        PIDFCoefficients pidOrig = shooter1.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        PIDFCoefficients pid = new PIDFCoefficients(P,I,D,F);
        shooter1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);
      //  this.dashboard = FtcDashboard.getInstance();



        shooter2 = hwp.get(DcMotorEx.class, "shooter2");
        shooter2.setDirection(DcMotorSimple.Direction.FORWARD);//motor1
        PIDFCoefficients pidOrig2 = shooter2.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);

        updatePIDF();

    }


    public double getInterpolatedShoot(double distance){
        targetSpeed = kDistanceToShooterSpeed.getInterpolated(new InterpolatingDouble(distance)).value;
        return kDistanceToShooterSpeed.getInterpolated(new InterpolatingDouble(distance)).value;
    }

    public void updatePIDF() {
        if (P != oldP || I != oldI || D != oldD || F != oldF) {
            PIDFCoefficients pid = new PIDFCoefficients(P, I, D, F);
            shooter1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);
            shooter2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pid);


            oldP = P; oldI = I; oldD = D; oldF = F;
        }
    }

    public boolean atRPMs(double target, double threshold) {
        return Math.abs(getRPMS() - target) <= threshold;
    }




    public void setPower(double power){
        shooter1.setPower(power);
        shooter2.setPower(power);
    }

    public void goToTargetRPM(){
        double TICKS_PER_REV = 28.0; // REV HD Hex motor (no gearbox)
        double gearRatio = 1;

        double targetTicksPerSeconds = (targetSpeed * TICKS_PER_REV * gearRatio) / 60;
        shooter1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter1.setVelocity(targetTicksPerSeconds);
        shooter2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooter2.setVelocity(targetTicksPerSeconds);
    }

    public double getRPMsDash(){
        return  targetSpeed;
    }

    public void setRPM(int RPM){
        double TICKS_PER_REV = 28.0; // REV HD Hex motor (no gearbox)
        double gearRatio = 1;

        double targetTicksPerSeconds = (RPM * TICKS_PER_REV * gearRatio) / 60;
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



       // telemetry.addData("shooter1 RPM", getLeftRPM());
        //telemetry.addData("shooter2 RPMS", getRightRPM());
        telemetry.addData("shooter RPMS", getRPMS());
        telemetry.addData("shooter target rpm", targetSpeed);

        //telemetry.addData("power", shooter1.getPower());
        //telemetry.addData("vel sh1", shooter1.getVelocity());
        //telemetry.addData("vel sh2", shooter2.getVelocity());

       /* TelemetryPacket packet = new TelemetryPacket();
        //packet.put("PID Output", power);
        packet.put("Graph shooter target",targetSpeed );
        packet.put("Actual RPMs", +getRPMS());



       dashboard.sendTelemetryPacket(packet);*/


    }
}
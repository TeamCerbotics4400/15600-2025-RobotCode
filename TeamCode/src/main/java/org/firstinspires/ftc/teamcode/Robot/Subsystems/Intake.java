package org.firstinspires.ftc.teamcode.Robot.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.SimpleServo;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@Configurable
@Config
public class Intake  extends SubsystemBase {
    DcMotorEx intake;
    ServoEx block;

    public static double angle;


    HardwareMap hw;

    public  Intake(HardwareMap hw){

        this.hw = hw;

        intake = hw.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        block = new ServoEx(hw, "block", 180, AngleUnit.DEGREES);
        block.setInverted(true);
        close();

    }

    public void setPower(double power){
        intake.setPower(power);
    }

    public void setAngle(){
        block.set(angle);
    }

    public void shoot(){
        block.set(90);
    }

    public void close(){
        block.set(57);
    }



    public void get(){
        intake.setPower(.88);
    }
    public void out(){
        intake.setPower(-1);

    }

    public void stop(){
        intake.setPower(0);
    }

    public void feed(){
        intake.setPower(.5);

    }
    public void autoFeed(){
        intake.setPower(.47);
    }

    public void selectpower(double powerfeed, double powerIntake){
        intake.setPower(powerIntake);
    }



}

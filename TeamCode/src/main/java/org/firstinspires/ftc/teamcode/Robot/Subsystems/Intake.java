package org.firstinspires.ftc.teamcode.Robot.Subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Intake extends SubsystemBase {
    DcMotorEx intake;
    HardwareMap hw;

    Telemetry tl;

    public Intake(HardwareMap hw,Telemetry tl){

        this.hw = hw;
        this.tl = tl;

        intake = hw.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
    //    intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPower(double power){
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setPower(power);
    }

    public void resetTicks(){
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void periodic(){
        tl.addData("Intake Voltage", intake.getCurrent(CurrentUnit.MILLIAMPS));
        //0.7 aggara o mas
        //0l.

    }
}

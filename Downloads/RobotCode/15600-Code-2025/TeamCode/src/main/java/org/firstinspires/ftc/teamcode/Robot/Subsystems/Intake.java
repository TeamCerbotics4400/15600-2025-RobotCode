package org.firstinspires.ftc.teamcode.Robot.Subsystems;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Intake extends SubsystemBase {
    DcMotorEx intake;
    HardwareMap hw;

    public Intake(HardwareMap hw){

        this.hw = hw;

        intake = hw.get(DcMotorEx.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPower(double power){
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setPower(power);
    }
}

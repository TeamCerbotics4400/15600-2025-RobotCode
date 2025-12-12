package org.firstinspires.ftc.teamcode.Robot.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.Debouncer;

import java.util.function.BooleanSupplier;

@Configurable
@Config
public class Feeder extends SubsystemBase {


    DcMotorEx motorLavadora;
    CRServo ser1, ser2;
    ColorSensor s1, s2, s3;

    DistanceSensor distanceSensor;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    TouchSensor limitSwitch;
    private enum SlotPosition{
        SLOT_1,
        SLOT_2,
        SLOT_3
    }



    public int globalSlotPosition = 0;
    public int globalTargetPosiion = 0;
    private static class SlotClass{
        SlotPosition slotNumber;
        boolean hasBallInside;
        int targetPosition;

        public SlotClass(SlotPosition slotNumber) {
            this.slotNumber = slotNumber;
            this.hasBallInside = false;
            this.targetPosition = 0;
        }
    }

    private final SlotClass[] slots = new SlotClass[] {
            new SlotClass(SlotPosition.SLOT_1),
            new SlotClass(SlotPosition.SLOT_2),
            new SlotClass(SlotPosition.SLOT_3)
    };
    public boolean isIntaking = false;

    private int currentSlotIndex = 0;

    public static double P = 0.002;
    public static double I = 0;
    public static double D= 0.000108;
    FtcDashboard dashboard;

    private boolean Ballinside = false;

    public PIDController m_controller = new PIDController(P,I,D);

    private Debouncer ballDebouncer = new Debouncer(0.1, Debouncer.DebounceType.kRising);//0.35
    public Feeder(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.dashboard = FtcDashboard.getInstance();

        limitSwitch = hardwareMap.get(TouchSensor.class, "limit");

        s1 = hardwareMap.get(ColorSensor.class, "s3");
        s2 = hardwareMap.get(ColorSensor.class, "s2");
        s3 = hardwareMap.get(ColorSensor.class, "s1");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "s3");


        motorLavadora = hardwareMap.get(DcMotorEx.class, "lavadora");
        motorLavadora.setDirection(DcMotorSimple.Direction.REVERSE);

        motorLavadora.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motorLavadora.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        ser1 = hardwareMap.get(CRServo.class, "ser1");
        ser2 = hardwareMap.get(CRServo.class, "ser2");

        ser1.setDirection(DcMotorSimple.Direction.FORWARD);
        ser2.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void goToPosition(double power, int targetPositionTicks){
        m_controller.setSetPoint(targetPositionTicks);
    }

    public void setPosition(double power,int pos){
        motorLavadora.setPower(power);
        motorLavadora.setTargetPosition(pos);
        motorLavadora.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setFalseNodeValue(int val){
        slots[val].hasBallInside = false;
    }

    public void checkAndChangeIntakePosition(){
        if(isIntaking && (!slots[0].hasBallInside ||!slots[1].hasBallInside||!slots[2].hasBallInside)){
            if(atPosition(globalTargetPosiion,10)) {
                if (slots[globalSlotPosition].hasBallInside) {
                    goToNextSlot();
                } else {

                }
                goToPosition(1, globalTargetPosiion);
            }
        }
    }

    public boolean detectBall(ColorSensor sensor) {
        return sensor.alpha() > 70 && sensor.alpha() < 1200;
    }


    public void goToNextSlot(){
        if(globalSlotPosition == 0){
            globalSlotPosition = 1;
            slots[globalSlotPosition].targetPosition = 781;  //Example
            globalTargetPosiion = 781;
        }
        else if(globalSlotPosition == 1){
            globalSlotPosition = 2;
            slots[globalSlotPosition].targetPosition = 1490;  //Example
            globalTargetPosiion = 1490;

        }
        else if(globalSlotPosition == 2){
            globalSlotPosition = 0;
            slots[globalSlotPosition].targetPosition = 0;  //Example
            globalTargetPosiion = 0;
        }
    }

    public void resetEncoders(){
        //  motorLavadora.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLavadora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setManualPower(double power){  //For debuging only
        motorLavadora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLavadora.setPower(power);
    }

    public void setCRSPower(double power){
        ser1.setPower(power);
        ser2.setPower(power);
    }

    public int getPosition(){
        return motorLavadora.getCurrentPosition();
    }

    public boolean atPosition(int target, int threshold) {
        return Math.abs(getPosition() - target) <= threshold;
    }

    public boolean limitSwitchGotPressed(){
        return limitSwitch.isPressed();
    }


    public void resetAfterShoot() {
        if(limitSwitchGotPressed()){
        motorLavadora.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLavadora.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m_controller.setSetPoint(0);
        }
    }

    public int detectColor(ColorSensor sensor) {
        if(sensor.green()> sensor.blue()){
            return 0;
        }else{
            return 1;
        }
    }

    private void updateSlotsUsingSensor() {// true = ball detected
        if (atPosition(globalTargetPosiion, 10) && !motorLavadora.isBusy()) {
            if (ballDebouncer.calculate(detectBall(s1))){
                if(
                        (globalSlotPosition == 1 && detectBall(s2))
                                || globalSlotPosition == 0
                                || (globalSlotPosition == 2 && detectBall(s2) && detectBall(s3))){

                    slots[globalSlotPosition].hasBallInside = true;

                    currentSlotIndex++;
                    if (currentSlotIndex >= 3) {
                        currentSlotIndex = 0;
                    }
                }
            }
        }
    }

    public boolean getSlotBallState(int index){
        return slots[index].hasBallInside;
    }

    public void setAllfalse(){
        slots[0].hasBallInside = false;
        slots[1].hasBallInside = false;
        slots[2].hasBallInside = false;

    }

    public void AllTrue(){
        slots[0].hasBallInside = true;
        slots[1].hasBallInside = true;
        slots[2].hasBallInside = true;


    }


    @Override
    public void periodic() {

        m_controller.setPID(P,I,D);

        //Verde 0   Morado 1

        /*slots[0].hasBallInside = detectBall(s1);
        slots[1].hasBallInside = detectBall(s2);
        slots[2].hasBallInside = detectBall(s3);

        telemetry.addData("S1 has ball", slots[0].hasBallInside);
        telemetry.addData("S2 has ball", slots[1].hasBallInside);
        telemetry.addData("S3 has ball", slots[2].hasBallInside);

        telemetry.addData("S1 alpha", s1.alpha());
        telemetry.addData("S3 alpha", s3.alpha());

        telemetry.addData("S1 red", s1.red());
        telemetry.addData("S1 blue", s2.blue());
        telemetry.addData("S1 green", s3.green());

        telemetry.addData("S1 Color", detectColor(s1));
        telemetry.addData("S2 Color", detectColor(s2));
        telemetry.addData("S3 Color", detectColor(s3));

        telemetry.addData("FeederPosition", getPosition());
        telemetry.addData("Intake active", isIntaking);
        //  telemetry.addData("Slot " + globalSlotPosition + " setpoint", slots[globalSlotPosition].targetPosition);




        telemetry.addData("S2 alpha", s2.alpha());
        telemetry.addData("S2 detected?", detectBall(s2));
        telemetry.addData("s1 detected?", detectBall(s1));
        telemetry.addData("S1 alpha", s1.alpha());
        telemetry.addData("Distance", distanceSensor.getDistance(DistanceUnit.MM));
        */

        motorLavadora.setPower(m_controller.calculate(getPosition()));

        telemetry.addData("Debouncer", ballDebouncer.calculate(detectBall(s1)));

        telemetry.addData("S1 hasballInside", slots[0].hasBallInside );
        telemetry.addData("S2 hasballInside", slots[1].hasBallInside);
        telemetry.addData("S3 hasballInside", slots[2].hasBallInside);
        telemetry.addData("S1 alpha", s1.alpha());
        telemetry.addData("Lavadora slot", globalSlotPosition);
        telemetry.addData("Lavadora target", globalTargetPosiion);
        telemetry.addData("FeederPosition", getPosition());
        telemetry.addData("Is Busy", motorLavadora.isBusy());
        telemetry.addData("Limit switch presed", limitSwitchGotPressed());

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("PID Output", m_controller.calculate(getPosition()));
        packet.put("Graph degrees",getPosition() );
        packet.put("Graph setpoint", m_controller.getSetPoint());

        dashboard.sendTelemetryPacket(packet);

        checkAndChangeIntakePosition();

        if(limitSwitch.isPressed()) {
            resetEncoders();
        }
        if(!slots[0].hasBallInside && !slots[1].hasBallInside && !slots[2].hasBallInside ){
            globalTargetPosiion = 0;
            globalSlotPosition = 0;
            goToPosition(1,0);
        }

        if(isIntaking){
            updateSlotsUsingSensor();
        }


    }
}
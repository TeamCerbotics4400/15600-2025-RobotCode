package org.firstinspires.ftc.teamcode.Robot;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {



    public static  class TurretCons{
        public  static double Redx = 140.5;
        public static double Redy = 140;

        public static double Bluex = 9;
        public static double BlueY = 140;

    }


    public static FollowerConstants followerConstants = new FollowerConstants()

            .mass(15)
            .forwardZeroPowerAcceleration(-45.82343139580664)
            .lateralZeroPowerAcceleration(-107.00879103289743)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.0625,0,0,0.001))
            .headingPIDFCoefficients(new PIDFCoefficients(1,0,0.001,0.02))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.0025, 0, 0, 0.6 ,0))
            .centripetalScaling(0)
            ;

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rf")
            .rightRearMotorName("rr")
            .leftRearMotorName("lr")
            .leftFrontMotorName("lf")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .useBrakeModeInTeleOp(true)
            .xVelocity( 69.97985167015256)
            .yVelocity(46.9856009896346)
            ;

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(6.4)//*0.98837
            .strafePodX(0.35)//0.35
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .yawScalar(1)

            ;


    public static PathConstraints pathConstraints = new PathConstraints(
            0.995,
            100,
            1,
            1
    );


    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }



}

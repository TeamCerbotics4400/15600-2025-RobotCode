package org.firstinspires.ftc.teamcode.Robot.Constantes;

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
import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;
import org.firstinspires.ftc.teamcode.utils.RobotConstants;


public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()

            .mass(12.5)
            .forwardZeroPowerAcceleration(-51.54322464112613)
            .lateralZeroPowerAcceleration(-110.48020786969806)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.11,0,0.009,0.044))
            .headingPIDFCoefficients(new PIDFCoefficients(1,0,0.01,0.008))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.0029, 0, 0.0009, 0.6, 0.08))
            .centripetalScaling(0.005)
            ;

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rf")
            .rightRearMotorName("rr")
            .leftRearMotorName("lr")
            .leftFrontMotorName("lf")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .useBrakeModeInTeleOp(true)
            .xVelocity(77.79242183655266)
            .yVelocity(52.73848838505783);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(MecanumDriveTrain.YPod)//6.08
            .strafePodX(MecanumDriveTrain.XPod)//0.2
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .yawScalar(1)

            ;





   public static PathConstraints pathConstraints = new PathConstraints(
            0.999,
            0.1,
            0.1,
            0.009,
            50,
            0.9,
            10,
            0.9
    );




    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }



}

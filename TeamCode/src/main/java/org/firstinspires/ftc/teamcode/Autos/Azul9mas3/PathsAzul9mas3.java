package org.firstinspires.ftc.teamcode.Autos.Azul9mas3;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathsAzul9mas3 {

    public static PathChain Shoot0;
    public static PathChain PreIntake1;
    public static PathChain PosIntake1;
    public static PathChain Shoot1;
    public static PathChain PreIntake2;
    public static PathChain PosIntake2;
    public static PathChain Shoot2;
    public static PathChain PreIntake3;
    public static PathChain PosIntake3;
    public static PathChain Shoot3;

    public PathsAzul9mas3(Follower follower) {
        Shoot0 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(26.031, 132.185), new Pose(41.354, 102.092))
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(125))
                .build();

        PreIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(41.354, 102.092), new Pose(41.169, 84.369))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        PosIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(41.169, 84.369), new Pose(15.508, 84.369))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(15.508, 84.369), new Pose(41.723, 101.908))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(155))
                .build();

        PreIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(41.723, 101.908), new Pose(42.277, 59.815))
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        PosIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(42.277, 59.815), new Pose(8.677, 59.815))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(8.677, 59.815),
                                new Pose(31.015, 58.523),
                                new Pose(41.723, 101.908)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(155))
                .build();

        PreIntake3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(41.723, 101.908), new Pose(43.569, 35.500))
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        PosIntake3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(43.569, 35.500), new Pose(8.677, 35.500))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        Shoot3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(8.677, 35.500), new Pose(46.523, 113.908))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                .build();
    }
}
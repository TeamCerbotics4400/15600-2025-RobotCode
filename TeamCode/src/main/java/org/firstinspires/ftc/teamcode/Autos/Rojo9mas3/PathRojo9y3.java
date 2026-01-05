    package org.firstinspires.ftc.teamcode.Autos.Rojo9mas3;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathRojo9y3 {

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

    public PathRojo9y3(Follower follower) {
        Shoot0 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(118.892, 132.923), new Pose(102.646, 102.092))
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(40))
                .build();

        PreIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(102.646, 102.092), new Pose(102.646, 84.631))
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(0))
                .build();

        PosIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(102.646, 84.631), new Pose(129.600, 84.631))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(129.600, 83.631), new Pose(102.646, 102.092))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        PreIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(102.646, 102.092), new Pose(105.569, 61.446))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        PosIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(105.569, 59.446), new Pose(136.062, 61.446))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(136.062, 59.446),
                                new Pose(113.723, 57.785),
                                new Pose(113.538, 85.477),
                                new Pose(102.831, 101.723)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(30))
                .build();

        PreIntake3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(104.831, 101.723), new Pose(104.200, 37.446))
                )
                .setLinearHeadingInterpolation(Math.toRadians(30), Math.toRadians(0))
                .build();

        PosIntake3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(104.200, 37.446), new Pose(132.369, 37.892))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        Shoot3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(132.369, 37.892), new Pose(98.585, 113.569))
                )
                .setConstantHeadingInterpolation(Math.toRadians(-70))
                .build();
    }
}

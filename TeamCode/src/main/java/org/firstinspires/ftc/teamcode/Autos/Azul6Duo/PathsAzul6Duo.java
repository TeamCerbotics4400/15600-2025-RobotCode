package org.firstinspires.ftc.teamcode.Autos.Azul6Duo;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathsAzul6Duo {

    public static PathChain Shoot0;
    public static PathChain PreIntakeWall1;
    public static PathChain PreIntakeWall2;
    public static PathChain PosIntakeWall;
    public static PathChain PreShoot1;
    public static PathChain Shoot1;
    public static PathChain PreIntake2;
    public static PathChain PosIntake2;

    public PathsAzul6Duo(Follower follower) {
        Shoot0 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(55.754, 9.231), new Pose(62.615, 77.908))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        PreIntakeWall1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(62.615, 77.908), new Pose(36.554, 18.462))
                )
                .setConstantHeadingInterpolation(Math.toRadians(250))
                .build();

        PreIntakeWall2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(36.554, 18.462), new Pose(10.154, 23.446))
                )
                .setConstantHeadingInterpolation(Math.toRadians(250))
                .build();

        PosIntakeWall = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(10.154, 23.446), new Pose(9.600, 11.077))
                )
                .setLinearHeadingInterpolation(Math.toRadians(240), Math.toRadians(250))
                .build();

        PreShoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(9.600, 11.077), new Pose(39.692, 18.569))
                )
                .setLinearHeadingInterpolation(Math.toRadians(250), Math.toRadians(90))
                .build();

        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(39.692, 18.569), new Pose(62.615, 77.908))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        PreIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(62.615, 77.908),
                                new Pose(55.754, 11.815),
                                new Pose(26.769, 18.277)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(220))
                .build();

        PosIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(26.769, 18.277), new Pose(11.631, 10.892))
                )
                .setLinearHeadingInterpolation(Math.toRadians(220), Math.toRadians(220))
                .build();
    }
}
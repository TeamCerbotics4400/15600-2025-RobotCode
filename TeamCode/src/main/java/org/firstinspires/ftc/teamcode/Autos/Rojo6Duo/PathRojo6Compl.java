package org.firstinspires.ftc.teamcode.Autos.Rojo6Duo;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathRojo6Compl {

    public static PathChain Shoot0;
    public static PathChain PreIntakeWall1;
    public static PathChain PreIntakeWall2;
    public static PathChain PosIntakeWall;
    public static PathChain PreShoot1;
    public static PathChain Shoot1;
    public static PathChain PreIntake2;
    public static PathChain PosIntake2;

    public PathRojo6Compl(Follower follower) {
        Shoot0 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(87.877, 8.123), new Pose(83.631, 78.462))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(70))
                .build();

        PreIntakeWall1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(83.631, 78.462), new Pose(106.708, 20.492))
                )
                .setConstantHeadingInterpolation(Math.toRadians(-70))
                .build();

        PreIntakeWall2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(106.708, 20.492), new Pose(134.400, 22.523))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-70), Math.toRadians(-63))
                .build();

        PosIntakeWall = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(134.400, 22.523), new Pose(136.431, 7.785))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-63), Math.toRadians(-63))
                .build();

        PreShoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(136.431, 7.785), new Pose(100.246, 14.585))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-63), Math.toRadians(-90))
                .build();

        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(100.246, 14.585), new Pose(83.631, 78.462))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-90))
                .build();

        PreIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(83.631, 78.462),
                                new Pose(89.169, 19.015),
                                new Pose(110.031, 19.938)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45))
                .build();

        PosIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(110.031, 19.938), new Pose(132.738, 11.631))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-40))
                .build();
    }
}
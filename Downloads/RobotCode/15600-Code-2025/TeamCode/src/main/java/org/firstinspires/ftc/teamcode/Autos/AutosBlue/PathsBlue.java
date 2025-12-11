package org.firstinspires.ftc.teamcode.Autos.AutosBlue;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathsBlue {
    public static PathChain Shoot1;
    public static PathChain GoToSpike1;
    public static PathChain Intake1;
    public static PathChain Shoot2;
    public static PathChain GoToSpike2;
    public static PathChain Intake2;
    public static PathChain Shoot3;
    public static PathChain Final;

    public PathsBlue(Follower follower) {
        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 8.000), new Pose(64.000, 28.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        GoToSpike1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 28.000), new Pose(40.467, 35.229))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();

        Intake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.467, 35.229), new Pose(24.000, 35.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24.000, 35.000), new Pose(64.000, 28.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();

        GoToSpike2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 28.000), new Pose(40.234, 58.855))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();

        Intake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.234, 58.855), new Pose(24.000, 58.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        Shoot3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24.000, 58.000), new Pose(64.000, 28.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();

        Final = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 28.000), new Pose(40.000, 32.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
    }
    }


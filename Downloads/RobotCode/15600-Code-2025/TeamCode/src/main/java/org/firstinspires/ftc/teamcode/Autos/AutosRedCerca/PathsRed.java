package org.firstinspires.ftc.teamcode.Autos.AutosRedCerca;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathsRed {

    public static PathChain Shoot1;
    public static PathChain GoToSpike1;
    public static PathChain Intake1;
    public static PathChain Shoot2;
    public static PathChain Final;

    public PathsRed(Follower follower) {
        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(80.000, 8.000), new Pose(82.554, 80.355))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        GoToSpike1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(82.554, 80.355),
                                new Pose(74.111, 23.218),
                                new Pose(100.000, 34.945)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();

        Intake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(100.000, 34.945), new Pose(130.000, 34.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(130.000, 34.000),
                                new Pose(68.951, 18.762),
                                new Pose(83.023, 83.257)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();

        Final = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(83.023, 83.257), new Pose(84.000, 65.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();
    }
    }


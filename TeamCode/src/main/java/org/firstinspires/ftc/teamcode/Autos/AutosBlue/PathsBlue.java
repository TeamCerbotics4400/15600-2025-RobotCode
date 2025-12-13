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
    public static PathChain Final;

    public PathsBlue(Follower follower) {
        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 8.000), new Pose(60.000, 70.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        GoToSpike1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.000, 70.000),
                                new Pose(68.537, 29.615),
                                new Pose(40.467, 37.229)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();

        Intake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.467, 37), new Pose(12.500, 37.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(10.000, 35.000),
                                new Pose(65.730, 41.779),
                                new Pose(60, 70.930)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
                .build();

        Final = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(62, 70), new Pose(62.000, 60.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();
    }
    }


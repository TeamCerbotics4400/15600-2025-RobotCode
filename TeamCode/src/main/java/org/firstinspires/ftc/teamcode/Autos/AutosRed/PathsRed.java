package org.firstinspires.ftc.teamcode.Autos.AutosRed;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.paths.PathConstraints;

public class PathsRed {

    public static PathChain Shoot1;
    public static PathChain GoToSpike1;
    public static PathChain Intake1;
    public static PathChain Shoot2;
    public static PathChain GoToSpike2;
    public static PathChain Intake2;
    public static PathChain Shoot3;
    //public static PathChain Final;

    public PathsRed(Follower follower) {

        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(80.000, 8.000), new Pose(82.000, 22.5))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        GoToSpike1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(90.000, 33), new Pose(100, 38.5))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();

        Intake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(100, 37), new Pose(132.000, 37))
                )
                .setVelocityConstraint(.5)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(130, 37), new Pose(83.000, 28.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();

        GoToSpike2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(80.000, 28.000), new Pose(100, 63))
                )
                .setLinearHeadingInterpolation(Math.toRadians(60), Math.toRadians(0))
                .build();

        Intake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(100, 63), new Pose(132, 63))
                )
                .setVelocityConstraint(.5)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();
        Shoot3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(132.000, 63.000), new Pose(100.000, 100.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();
      /*  Final = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(80.000, 28.000), new Pose(80, 8))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();*/
    }
    }


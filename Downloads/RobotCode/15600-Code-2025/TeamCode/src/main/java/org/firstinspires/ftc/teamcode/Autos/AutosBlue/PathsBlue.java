package org.firstinspires.ftc.teamcode.Autos.AutosBlue;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathsBlue {
    public static  PathChain Spike1;
    public static PathChain Shooter1;
    public static PathChain Spike2;
    public static PathChain Shooter2;

    public PathsBlue(Follower follower) {
        Spike1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(62.690, 8.000),
                                new Pose(76.023, 38.738),
                                new Pose(9.357, 35.463)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();

        Shooter1 = follower
                .pathBuilder()
                .addPath(new BezierLine(new Pose(9.357, 35.463), new Pose(63.391, 8.000)))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();

        Spike2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(63.391, 8.000),
                                new Pose(74.853, 66.340),
                                new Pose(11.462, 59.556)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();

        Shooter2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(11.462, 59.556), new Pose(63.625, 8.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();
    }
}

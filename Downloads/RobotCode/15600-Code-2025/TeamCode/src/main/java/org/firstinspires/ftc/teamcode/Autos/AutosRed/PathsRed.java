package org.firstinspires.ftc.teamcode.Autos.AutosRed;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathsRed {

    public static PathChain Spike1;
    public static PathChain Shooter1;
    public static PathChain Spike2;
    public static PathChain Path4;

    public PathsRed(Follower follower) {
        Spike1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(80.730, 7.651),
                                new Pose(76.023, 38.738),
                                new Pose(129.544, 35.108)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-90))
                .build();

        Shooter1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(129.544, 35.108), new Pose(80.000, 8.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(0))
                .build();

        Spike2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(80.000, 8.000),
                                new Pose(74.853, 66.340),
                                new Pose(129.122, 58.855)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-90))
                .build();

        Path4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(129.122, 58.855), new Pose(80.000, 8.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(0))
                .build();
    }
}

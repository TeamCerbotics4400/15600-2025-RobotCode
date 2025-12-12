package org.firstinspires.ftc.teamcode.Autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BluePathsLejos {
    public static PathChain Shoot1;
    public static PathChain Spike1;
    public static PathChain Intake1;
    public static PathChain Shoot2;
    public static PathChain Final;

    public BluePathsLejos(Follower follower) {
        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 8.000), new Pose(64.000, 28.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();

        Spike1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 28.000), new Pose(40.339, 34.945))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();

        Intake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(40.339, 34.945), new Pose(10.000, 34.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(10.000, 34.000), new Pose(64.000, 28.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
                .build();

        Final = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(64.000, 28.000), new Pose(60.000, 48.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();
    }
}

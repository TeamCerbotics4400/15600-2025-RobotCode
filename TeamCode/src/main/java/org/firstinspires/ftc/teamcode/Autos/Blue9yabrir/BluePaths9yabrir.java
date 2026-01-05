package org.firstinspires.ftc.teamcode.Autos.Blue9yabrir;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BluePaths9yabrir {

    public static PathChain Shoot0;
    public static PathChain PreIntake1;
    public static PathChain PosIntake1;

    public static  PathChain PreOpenGate;

    public static  PathChain PosOpenGate;
    public static PathChain Shoot1;
    public static PathChain PreIntake2;
    public static PathChain PosIntake2;
    public static PathChain Shoot2;
    public static PathChain PreIntake3;
    public static PathChain PosIntake3;
    public static PathChain Shoot3;
    public static PathChain Leave;

    public BluePaths9yabrir(Follower follower) {
        Shoot0 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(26.031, 132.185), new Pose(41.353, 101.907))
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(135))
                .build();

        PreIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(41.353, 101.907), new Pose(43.015, 84.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .build();

        PosIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(43.015, 84.000), new Pose(14.769, 84.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        PreOpenGate = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(14.769, 84.00), new Pose(21.892, 73.961))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        PosOpenGate = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose (21.892, 73.961), new Pose (7.046, 73.961))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();


        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(9.046, 73.961), new Pose(41.353, 101.907))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(160))
                .build();

        PreIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(41.353, 101.907), new Pose(36.369, 60.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(160), Math.toRadians(180))
                .build();

        PosIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(36.369, 60.000), new Pose(9.969, 59.631))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(9.969, 59.631),
                                new Pose(34.892, 58.338),
                                new Pose(44.353, 103.907)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(160))
                .build();

    }


}
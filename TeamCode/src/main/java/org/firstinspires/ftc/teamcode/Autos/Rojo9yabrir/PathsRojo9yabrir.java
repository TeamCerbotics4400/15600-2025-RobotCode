package org.firstinspires.ftc.teamcode.Autos.Rojo9yabrir;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathsRojo9yabrir {

    public static PathChain Shoot0;
    public static PathChain PreIntake1;
    public static PathChain PosIntake1;

    public static  PathChain PreOpenGate;

    public static  PathChain PosOpenGate;
    public static PathChain Shoot1;
    public static PathChain PreIntake2;
    public static PathChain PosIntake2;
    public static PathChain Shoot2;

    public PathsRojo9yabrir(Follower follower) {
        Shoot0 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(118.892, 132.923), new Pose(104.862, 103.569))
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(40))
                .build();

        PreIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(104.862, 103.569), new Pose(105.600, 85.077))
                )
                .setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(0))
                .build();

        PosIntake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(105.600, 85.077), new Pose(130.338, 85.077))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        PreOpenGate = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(130.338, 85.077), new Pose(123.323, 76.00))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        PosOpenGate = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(123.323, 76.00), new Pose(135.20, 76.00))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        Shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(135.20, 76.00), new Pose(104.862, 103.569))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))
                .build();

        PreIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(104.862, 103.569), new Pose(105.231, 61.631))
                )
                .setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(0))
                .build();

        PosIntake2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(105.231, 61.631), new Pose(134.769, 61.631))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        Shoot2 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(134.769, 61.631), new Pose(99, 114.985))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(35))
                .build();

      /*  Leave = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(106.708, 120.985), new Pose(111.508, 75.508))
                )
                .setConstantHeadingInterpolation(Math.toRadians(45))
                .build();

       */
    }
}

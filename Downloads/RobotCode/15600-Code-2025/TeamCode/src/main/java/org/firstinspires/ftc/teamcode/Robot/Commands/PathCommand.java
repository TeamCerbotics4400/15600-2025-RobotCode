package org.firstinspires.ftc.teamcode.Robot.Commands;

import com.pedropathing.paths.PathChain;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;

public class PathCommand extends CommandBase {

    private MecanumDriveTrain drivetrain;
    private PathChain path;
    public PathCommand(MecanumDriveTrain drivetrain, PathChain path) {
        this.drivetrain = drivetrain;
        this.path = path;
    }


    @Override
    public void initialize() {
        drivetrain.followPath(path);
    }

    @Override
    public void execute() {
        drivetrain.update();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            drivetrain.stopFollow();
        }
    }

    @Override
    public boolean isFinished() {
        return drivetrain.finishedPath();
    }
}

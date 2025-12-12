package org.firstinspires.ftc.teamcode.Robot.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Robot.Subsystems.MecanumDriveTrain;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {

    private MecanumDriveTrain m_driveTrain;
    private DoubleSupplier y, x, turn;
    private boolean robotCentric;

    public DriveCommand(MecanumDriveTrain driveTrain, DoubleSupplier y, DoubleSupplier x, DoubleSupplier turn, boolean robotCentric) {
        this.m_driveTrain = driveTrain;
        this.y = y;//turn
        this.x = x;
        this.turn = turn;
        this.robotCentric = robotCentric;

        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        m_driveTrain.startTeleOp();
    }

    @Override
    public void execute() {
        m_driveTrain.setDrive(y.getAsDouble(), -x.getAsDouble(), -turn.getAsDouble(), robotCentric);
        m_driveTrain.update();
    }

}
package org.firstinspires.ftc.teamcode.Robot.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.panels.Panels;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.PoseHistory;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.Constantes.Constants;
import org.firstinspires.ftc.teamcode.utils.Drawing;

import java.util.Locale;

@Configurable
@Config
public class MecanumDriveTrain extends SubsystemBase {

    HardwareMap hw;
    Telemetry tl;
    Follower m_follower;
    Panels panels;
    static PoseHistory poseHistory;

    public static double YPod = -4;
    public static double XPod = 0.23;


    static TelemetryManager telemetryM;
    Pose azulGOAL = new Pose(12.5, 134);
    Pose rojoGOAL = new Pose(131, 139);

    boolean isBlueAlliance;
    boolean isClose;


    public MecanumDriveTrain(HardwareMap hw, Telemetry tl, boolean isBlueAlliance, boolean isClose) {
        this.hw = hw;
        this.tl = tl;
        this.m_follower = Constants.createFollower(hw);
        poseHistory = m_follower.getPoseHistory();
        this.isBlueAlliance = isBlueAlliance;
        this.isClose = isClose;
        if(isBlueAlliance){
            if(isClose) {
                this.m_follower.setPose(new Pose(26.031, 132.185, Math.toRadians(135)));//normal 26.031 132.185 135
            }else{
                this.m_follower.setPose(new Pose(62.592, 8.679, Math.toRadians(90)));//normal 62.592 8.679 90
            }
            //this.m_follower.setPose(new Pose(55.75, 9.23, Math.toRadians(90)));//Complemento

        }else{
            if(isClose) {
                this.m_follower.setPose(new Pose(118.892, 132.923, Math.toRadians(45)));//normal
            }else{
                this.m_follower.setPose(new Pose(97.848, 10.832, Math.toRadians(90)));//normal

            }
            //this.m_follower.setPose(new Pose(87.877, 8.123, Math.toRadians(90)));//Complemento
        }
        setSubsystem("DriveTrain");

        m_follower.getPoseTracker();

    }

    public double obtenerDistanciaTarget(boolean isBlueAlliance){
        double distancia = 0;
        Pose poseActual = m_follower.getPose();
        if(isBlueAlliance){
            distancia = Math.sqrt(Math.pow(azulGOAL.getX() - poseActual.getX(), 2) + Math.pow(azulGOAL.getY() - poseActual.getY(), 2));
        }else{
            distancia =Math.sqrt(Math.pow(rojoGOAL.getX() - poseActual.getX(), 2) + Math.pow(rojoGOAL.getY() - poseActual.getY(), 2));
        }

        return distancia;
    }


    public void drawCurrent() {
        try {
            Drawing.drawRobot(m_follower.getPose());
            Drawing.sendPacket();
        } catch (Exception e) {
            throw new RuntimeException("Drawing failed " + e);
        }
    }

    public void drawCurrentAndHistory() {
        Drawing.drawPoseHistory(poseHistory);
        drawCurrent();
    }

    public void startPose(Pose pose) {m_follower.setStartingPose(pose);}

    public void startTeleOp() {m_follower.startTeleopDrive();}

    //ESTA DE AQUI ABAJO CHAD
    public void followPath(PathChain path) {m_follower.followPath(path);}//**********************

    public PathBuilder pathBuldier() {return m_follower.pathBuilder();}

    public boolean finishedPath () {return !m_follower.isBusy();}

    public void update() {m_follower.update();}

    public void stopFollow() {m_follower.breakFollowing();}

    public Follower getFollower() {return m_follower;}



    public void setDrive(double y, double x, double turn, boolean robotCentric) {
        if(isBlueAlliance) {
            m_follower.setTeleOpDrive(y, x, turn, false);
        }else{
            m_follower.setTeleOpDrive(-y, -x, turn, false);
        }
    }

    public Pose getPose() {
        return m_follower.getPose();
    }

    public Vector getVelocity() {
        return m_follower.getVelocity();
    }

    public double getpower(){return  m_follower.getMaxPowerScaling();
    }

    private double normalizeDegrees(double angleDeg) {
        double normalized = angleDeg % 360;
        if (normalized < 0) normalized += 360;
        return normalized;
    }


    @Override
    public void periodic() {

        double headingDeg = normalizeDegrees(getPose().getHeading() * 180 / Math.PI);

        String pose = String.format(
                Locale.US,
                "Y: %.3f, X: %.3f, H: %.3f",
                getPose().getY(),
                getPose().getX(),
                headingDeg
        );


        drawCurrentAndHistory();
        String velocity = String.format(
                Locale.US,
                "X: %.3f, Y: %.3f, Magnitude: %.3f",
                getVelocity().getXComponent(),
                getVelocity().getYComponent(),
                getVelocity().getMagnitude()
        );

        tl.addData(getSubsystem(), pose);
        tl.addData(getSubsystem(), velocity);
        tl.addData("Distancia a Rojo", obtenerDistanciaTarget(false));
        tl.addData("Distancia a Azul", obtenerDistanciaTarget(true));

        tl.addData("AngularVel", m_follower.getAngularVelocity());
    }

}

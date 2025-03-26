package frc.robot.subsystems.Vision;
import java.util.ArrayList;
import java.util.HashMap;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.RawFiducial;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;

public class Vision extends SubsystemBase {

    private CommandSwerveDrivetrain m_drivebase;
    private boolean autoUpdateOrientation = true;
    private OrientationUpdateMethod orientationUpdateMethod = OrientationUpdateMethod.DRIVEBASE_UPDATE;

    private LimelightHelpers.PoseEstimate accurateMegaTag1PoseEstimate = new LimelightHelpers.PoseEstimate();
    private LimelightHelpers.PoseEstimate accurateMegaTag2PoseEstimate = new LimelightHelpers.PoseEstimate();

    private Rotation2d accurateRotationMegaTag1 = new Rotation2d();

    private ArrayList<LimelightHelpers.PoseEstimate> mt_all1 = new ArrayList<>();
    private ArrayList<LimelightHelpers.PoseEstimate> mt_all2 = new ArrayList<>();
    private ArrayList<Double> megaTagAvgAreas = new ArrayList<>();

    public enum OrientationUpdateMethod {
        DRIVEBASE_UPDATE,
        MEGATAG_UPDATE;
    }

    public Vision(CommandSwerveDrivetrain m_drivebase, boolean autoUpdateOrientation, OrientationUpdateMethod orientationUpdateMethod) {
        this.orientationUpdateMethod = orientationUpdateMethod;
        this.autoUpdateOrientation = autoUpdateOrientation;
        this.m_drivebase = m_drivebase;
    }

    @Override
    public void periodic(){
        if (autoUpdateOrientation) {
            updateRobotOrientation();
        }

        LimelightHelpers.PoseEstimate mt_left1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightLeftName);
        LimelightHelpers.PoseEstimate mt_right1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightRightName);
        LimelightHelpers.PoseEstimate mt_back1 = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightBackName);

        LimelightHelpers.PoseEstimate mt_left2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightLeftName);
        LimelightHelpers.PoseEstimate mt_right2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightRightName);
        LimelightHelpers.PoseEstimate mt_back2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightBackName);
    
        SmartDashboard.putBoolean("LeftLimelightMegaTag2Status", mt_left2 != null);
        SmartDashboard.putBoolean("RightLimelightMegaTag2Status", mt_right2 != null);
        SmartDashboard.putBoolean("BackLimelightMegaTag2Status", mt_back2 != null);

        SmartDashboard.putBoolean("LeftLimelightMegaTag1Status", mt_left1 != null);
        SmartDashboard.putBoolean("RightLimelightMegaTag1Status", mt_right1 != null);
        SmartDashboard.putBoolean("BackLimelightMegaTag1Status", mt_back1 != null);

        mt_all1.add(mt_left1);
        mt_all1.add(mt_right1);
        mt_all1.add(mt_back1);

        mt_all2.add(mt_left2);
        mt_all2.add(mt_right2);
        mt_all2.add(mt_back2);

        double[] weights = getLimelightWeights(mt_all1);
        SmartDashboard.putNumberArray("limelightWeights", weights);


        mt_all1.clear();
        mt_all2.clear();
    }

    private double[] getLimelightWeights(ArrayList<LimelightHelpers.PoseEstimate> poses){
        ArrayList<Double> weights = new ArrayList<Double>();

        double totalArea = 0;
        for (LimelightHelpers.PoseEstimate pose : poses) {
            if (pose != null) {
                totalArea += pose.avgTagArea;
                
            }
        }

        for (LimelightHelpers.PoseEstimate pose : poses) {
            if (pose != null) {
                weights.add(pose.avgTagArea / totalArea);
            } else {
                weights.add(null);
            }
        }

        return weights.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public void updateRobotOrientation() {
        switch (orientationUpdateMethod) {
            case DRIVEBASE_UPDATE:
                LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightLeftName, m_drivebase.getState().Pose.getRotation().getDegrees(), 0 ,0, 0, 0, 0);
                LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightRightName, m_drivebase.getState().Pose.getRotation().getDegrees(), 0 ,0, 0, 0, 0);
                LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, m_drivebase.getState().Pose.getRotation().getDegrees(), 0 ,0, 0, 0, 0);
                break;
            case MEGATAG_UPDATE:
                LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightLeftName, accurateRotationMegaTag1.getDegrees(), 0 ,0, 0, 0, 0);
                LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightRightName, accurateRotationMegaTag1.getDegrees(), 0 ,0, 0, 0, 0);
                LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, accurateRotationMegaTag1.getDegrees(), 0 ,0, 0, 0, 0);
                break;
        }
    }

    public void setAutoUpdateOrientation(boolean autoUpdateOrientation) {
        this.autoUpdateOrientation = autoUpdateOrientation;
    }

    public boolean isAutoUpdateOrientation() {
        return autoUpdateOrientation;
    }

    public void setOrientationUpdateMethod(OrientationUpdateMethod orientationUpdateMethod) {
        this.orientationUpdateMethod = orientationUpdateMethod;
    }

    public OrientationUpdateMethod getOrientationUpdateMethod() {
        return orientationUpdateMethod;
    }
}


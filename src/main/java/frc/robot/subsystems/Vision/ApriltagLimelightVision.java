package frc.robot.subsystems.Vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.DriveToPosRuntime;
import frc.robot.LimelightHelpers;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.LimelightHelpers.PoseEstimate;
import frc.robot.LimelightHelpers.RawFiducial;
import frc.robot.subsystems.SubsystemUtils.SubsystemLib;

public class ApriltagLimelightVision {
    private HashMap<Double, PoseEstimate> poseEstimates = new HashMap<>();
    private ArrayList<Double> avgAreas = new ArrayList<>();
    private PoseEstimate bestEstimate = null;
    private PoseEstimate estimateOne = null; 
    private PoseEstimate estimateTwo = null; 
    private RobotContainer m_robotContainer;
    private VisionSource visionSource = VisionSource.MegaTag2;
    private Boolean averagingOneStream = false;
    private Boolean averagingMultipleStreams = false;
    private Pose2d averagedPose = null;
    


    public enum VisionSource {
        MegaTag1,
        MegaTag2
    }

    public ApriltagLimelightVision(RobotContainer robotContainer) {
        this.m_robotContainer = robotContainer;
    }

    public void setVisionSource(VisionSource source) {
        visionSource = source;
    }

    public void updateVisionEstimates(Rotation2d orientation) {
        poseEstimates.clear();
        avgAreas.clear();

        LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightLeftName, orientation.getDegrees(), 0, 0, 0, 0, 0);
        LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightRightName, orientation.getDegrees(), 0, 0, 0, 0, 0);
        LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, orientation.getDegrees(), 0, 0, 0, 0, 0);

        PoseEstimate left;
        PoseEstimate right;
        PoseEstimate back;
        if (this.visionSource == VisionSource.MegaTag1) {
            left = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightLeftName);
            right = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightRightName);
            back = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightBackName);
        } else {
            left = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightLeftName);
            right = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightRightName);
            back = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightBackName);
        }

        if (left != null) {
            poseEstimates.put(left.avgTagArea, left);
            SmartDashboard.putString("Left X Val estimate", left.pose.getX() + "");

        }
        if (right != null) {
            poseEstimates.put(right.avgTagArea, right);
            SmartDashboard.putString("Right X Val estimate", right.pose.getX() + "");
        }
        if (back != null) poseEstimates.put(back.avgTagArea, back);

        avgAreas.addAll(poseEstimates.keySet());
        avgAreas.sort(null);


    


   

        if (avgAreas.size() > 2){
            averagingMultipleStreams = true;
            averagingOneStream = false;
         
            

            double totalX = 0.0;
            double totalY = 0.0;
            double totalTheta = 0.0;
            int count = 0;
            PoseEstimate mostConfident = null;
            double highestConfidence = -1000000.0;
        
            for (PoseEstimate estimate : poseEstimates.values()) {
                totalX += estimate.pose.getX();
                totalY += estimate.pose.getY();
                totalTheta += estimate.pose.getRotation().getRadians();
                count++;
        
                if (estimate.avgTagArea > highestConfidence) {
                    highestConfidence = estimate.avgTagArea;
                    mostConfident = estimate;
                }
            }
        
            if (count > 0) {
                averagedPose = new Pose2d(
                    totalX / (count - 1),
                    totalY / (count - 1),
                    new Rotation2d(totalTheta / (count - 1))
                );
                bestEstimate = mostConfident;
            }

            SmartDashboard.putNumber("Number of streams I am looking at", count);
            SmartDashboard.putNumber("averagePoseX", averagedPose.getX());
            SmartDashboard.putNumber("averagePoseY", averagedPose.getY());
            SmartDashboard.putNumber("averagePoseTheta", averagedPose.getRotation().getDegrees());
        }

        
        else if (!avgAreas.isEmpty()) {
            averagingMultipleStreams = false;
            averagingOneStream = true;
            bestEstimate = poseEstimates.get(avgAreas.get(avgAreas.size() - 1));
        }

        SmartDashboard.putBoolean("averagingOneStream", averagingOneStream);
        SmartDashboard.putBoolean("averagingMultipleStreams", averagingMultipleStreams);


        

        if (bestEstimate != null && bestEstimate.tagCount > 0) {
            Constants.VisionConstants.bestLimelightPose = bestEstimate;
            SmartDashboard.putString("LimelightInUse", getActiveLimelightName(left, right, back));
        } else {

            bestEstimate = null;
        }
    }

    public void applyVisionToEstimator() {
        if (averagingOneStream == true && bestEstimate != null && Math.abs(m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble()) <= 720) {

            m_robotContainer.drivetrain.setVisionMeasurementStdDevs(Constants.VisionConstants.visionStdDevs); //change this to rely on vision more in the future
            m_robotContainer.drivetrain.addVisionMeasurement(bestEstimate.pose, edu.wpi.first.wpilibj.Timer.getFPGATimestamp());
        }

        else if (averagingMultipleStreams == true && bestEstimate != null && averagedPose != null && Math.abs(m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble()) <= 720) {

            m_robotContainer.drivetrain.setVisionMeasurementStdDevs(Constants.VisionConstants.visionStdDevs); //change this to rely on vision more in the future
            m_robotContainer.drivetrain.addVisionMeasurement(averagedPose, edu.wpi.first.wpilibj.Timer.getFPGATimestamp());
        }
    }

    public void resetPoseFromVision(Rotation2d rot) {
        if (bestEstimate != null && bestEstimate.tagCount > 0) {
            m_robotContainer.drivetrain.resetPose(new Pose2d(bestEstimate.pose.getTranslation(), rot));
        }
    }

    public Rotation2d estimateRotationFromVision() {
        updateVisionEstimates(m_robotContainer.drivetrain.getState().Pose.getRotation());
        return (bestEstimate != null) ? bestEstimate.pose.getRotation() : new Rotation2d();
    }

    public void updateAutoTargetFromVision() {
        if (bestEstimate == null) return;

        RawFiducial closest = null;
        for (RawFiducial tag : bestEstimate.rawFiducials) {
            if (closest == null || tag.distToRobot < closest.distToRobot) {
                closest = tag;
            }
        }

        if (closest != null) {
            Constants.DriveToPosRuntime.autoTargets = Constants.DriveToPoseConstants.tagDestinationMap.getOrDefault(Integer.toString(closest.id), new ArrayList<>());
            SmartDashboard.putNumber("frontClosestTag", closest.id);
        }
    }

    private String getActiveLimelightName(PoseEstimate left, PoseEstimate right, PoseEstimate back) {
        if (bestEstimate == left) return "Left";
        if (bestEstimate == right) return "Right";
        if (bestEstimate == back) return "Back";
        return "None";
    }

    public String getLimelightInUseName(PoseEstimate left, PoseEstimate right, PoseEstimate back) {
        if (bestEstimate == left) return "Left";
        if (bestEstimate == right) return "Right";
        if (bestEstimate == back) return "Back";
        return "None";
    }

    public void periodic() {
        // Optional: update dashboard or logs
        if (bestEstimate != null) {
            SmartDashboard.putString("Vision Pose", bestEstimate.pose.toString());
        }
    }
}
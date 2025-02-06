// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix6.Utils;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.LimelightHelpers.RawFiducial;
import frc.robot.commands.CANdleCommands.CommandCandleSetAnimation;
import frc.robot.subsystems.SensorSubsystems.CANdle_LED.AnimationTypes;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  boolean moveClimberDown;
  boolean spinIntake;
  boolean moveClimberUp;
  private SendableChooser<XboxController.Button> controlChoiceClimberDown = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceIntake = new SendableChooser<>();
  private SendableChooser<XboxController.Button> controlChoiceClimberUp = new SendableChooser<>();

  private final RobotContainer m_robotContainer;

  public final XboxController testJoystick = new XboxController(2);

  private static List<Integer> validIDs = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));





  public Robot() {
    m_robotContainer = new RobotContainer();

    SmartDashboard.putData("Selectable Action Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("Climber Up", () -> moveClimberDown, null);
        builder.addBooleanProperty("Spin Intake", () -> spinIntake, null);
        builder.addBooleanProperty("Climber Down", () -> moveClimberUp, null);
      }});
    controlChoiceClimberDown.setDefaultOption("A Button", XboxController.Button.kA);
    controlChoiceClimberDown.addOption("B Button", XboxController.Button.kB);
    controlChoiceClimberDown.addOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Climber Up Buttonmap", controlChoiceClimberDown);
    controlChoiceIntake.addOption("A Button", XboxController.Button.kA);
    controlChoiceIntake.setDefaultOption("B Button", XboxController.Button.kB);
    controlChoiceIntake.addOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Intake Buttonmap", controlChoiceIntake);
    controlChoiceClimberUp.addOption("A Button", XboxController.Button.kA);
    controlChoiceClimberUp.addOption("B Button", XboxController.Button.kB);
    controlChoiceClimberUp.setDefaultOption("X Button", XboxController.Button.kX);
    SmartDashboard.putData("Climber Down Buttonmap", controlChoiceClimberUp);
  }

  @Override
  public void robotPeriodic() {
    // System.out.println(Constants.ScoringConstants.ScoringStage + " " + Constants.ScoringConstants.ScoringStage.getElevatorRotations());

    SmartDashboard.putString("Scoring Stage", Constants.ScoringConstants.ScoringStage.toString());
    
    CommandScheduler.getInstance().run(); 

    boolean doRejectUpdate = false;
    SmartDashboard.putNumber("PigeonRotation", m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees());
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightLeftName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightRightName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    
    LimelightHelpers.PoseEstimate mt_left = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightLeftName);
    LimelightHelpers.PoseEstimate mt_right = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightRightName);

    //Update Valid IDs

    LimelightHelpers.SetFiducialIDFiltersOverride(Constants.VisionConstants.limelightLeftName, validIDs.stream().mapToInt(Integer::intValue).toArray());
    LimelightHelpers.SetFiducialIDFiltersOverride(Constants.VisionConstants.limelightRightName, validIDs.stream().mapToInt(Integer::intValue).toArray());
    if(Constants.DriveToPosRuntime.target != null){
    SmartDashboard.putString("reefTarget", Constants.DriveToPosRuntime.target);
    }




    SmartDashboard.putBoolean("LeftLimelightOnlineStatus", mt_left != null);
    SmartDashboard.putBoolean("RightLimelightOnlineStatus", mt_right != null);

    m_robotContainer.drivetrain.setVisionMeasurementStdDevs(Constants.VisionConstants.visionStdDevs);


    LimelightHelpers.PoseEstimate mt_inUse = null;

    
    if (mt_left != null && mt_right != null) {
      if (mt_left.avgTagArea > mt_right.avgTagArea) {
        mt_inUse = mt_left;
        SmartDashboard.putString("LimelightInUse", "Left");
      } else {
        mt_inUse = mt_right;
        SmartDashboard.putString("LimelightInUse", "Right");
      }
    } 
    
    else if (mt_left == null) {
      mt_inUse = mt_right;
      SmartDashboard.putString("LimelightInUse", "Right");
    } else if (mt_right == null) {
      mt_inUse = mt_left;
      SmartDashboard.putString("LimelightInUse", "Left");
    } else {
      SmartDashboard.putString("LimelightInUse", "None");
    }
    SmartDashboard.putNumber("angularVel", m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble());

    if (mt_inUse != null) {
      if(Math.abs(m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
        {
          doRejectUpdate = true;
        }
        if(mt_inUse.tagCount == 0)
        {
          doRejectUpdate = true;
        }
        if(!doRejectUpdate)
        {
          m_robotContainer.drivetrain.addVisionMeasurement(
              mt_inUse.pose,
              Utils.fpgaToCurrentTime(mt_inUse.timestampSeconds));
        }
    }
    if (Constants.DriveToPosRuntime.target == "Source") {
      tagFilter(18, false);
      tagFilter(7, false);
    } else if (Constants.DriveToPoseConstants.leftTagNames.keySet().contains(Constants.DriveToPosRuntime.target)) {
      if(Constants.DriveToPoseConstants.leftTagNames.higherEntry(Constants.DriveToPosRuntime.target) != null) {
        tagFilter(Constants.DriveToPoseConstants.leftTagNames.higherEntry(Constants.DriveToPosRuntime.target).getValue(), false);
      } else {
        tagFilter(Constants.DriveToPoseConstants.leftTagNames.firstEntry().getValue(), false);
      }
    } else if (Constants.DriveToPoseConstants.rightTagNames.keySet().contains(Constants.DriveToPosRuntime.target)) {
      if(Constants.DriveToPoseConstants.rightTagNames.lowerEntry(Constants.DriveToPosRuntime.target) != null) {
        tagFilter(Constants.DriveToPoseConstants.rightTagNames.lowerEntry(Constants.DriveToPosRuntime.target).getValue(), false);
      } else {
        tagFilter(Constants.DriveToPoseConstants.leftTagNames.lastEntry().getValue(), false);
      }
    }
    

    if (Constants.DriveToPosRuntime.target == "reefA") {
      tagFilter(19, false);
    }
    else {
      tagFilter(19, true);
    }

    

    RawFiducial closestTag = null;
    if (mt_left != null) {
      for (RawFiducial tag : mt_left.rawFiducials) {
        if (closestTag == null) {
          closestTag = tag;
        } else if (tag.distToRobot < closestTag.distToRobot) {
          closestTag = tag;
        }
      }
    }
    if (closestTag != null) {
      if (Constants.DriveToPoseConstants.tagDestinationMap.containsKey(Integer.toString(closestTag.id))) {
        Constants.DriveToPosRuntime.autoTargets = Constants.DriveToPoseConstants.tagDestinationMap.get(Integer.toString(closestTag.id));
      }
    }
    SmartDashboard.putNumber("frontClosestTag", (closestTag != null ? closestTag.id : 0));
    SmartDashboard.putString("possibleDestinationA", Constants.DriveToPosRuntime.autoTargets.get(0));
    SmartDashboard.putString("possibleDestinationB", Constants.DriveToPosRuntime.autoTargets.get(1));
    SmartDashboard.putNumberArray("Valid IDs", validIDs.stream().mapToDouble(Integer::intValue).toArray());

  }


  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  private void tagFilter(int id, boolean add) {
    if (!add) {
      if (validIDs.contains(id)) {
        validIDs.remove(Integer.valueOf(id));
      }
    } else {
      if (!validIDs.contains(id)) {
        validIDs.add(id);
      }
    }
  }
  public static void tagReset() {
    validIDs = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
  }
}

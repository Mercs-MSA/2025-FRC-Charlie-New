// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.ctre.phoenix.Util;
import com.ctre.phoenix6.Utils;

import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.net.WebServer;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DSControlWord;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.DriveToPosRuntime;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.LimelightHelpers.RawFiducial;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;



public class Robot extends TimedRobot {
  

  private Command m_autonomousCommand;

  boolean moveClimberDown;
  boolean spinIntake;
  boolean moveClimberUp;
  // private SendableChooser<XboxController.Button> controlChoiceClimberDown = new SendableChooser<>();
  // private SendableChooser<XboxController.Button> controlChoiceIntake = new SendableChooser<>();
  // private SendableChooser<XboxController.Button> controlChoiceClimberUp = new SendableChooser<>();

  private final RobotContainer m_robotContainer;

  public final XboxController testJoystick = new XboxController(2);

  LimelightHelpers.PoseEstimate mt_inUse = null;

  HashMap<Double, LimelightHelpers.PoseEstimate> mt_all = new HashMap<>();
  ArrayList<Double> megaTagAvgAreas = new ArrayList<>();

  // private static ArrayList<Integer> validIDs = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));


  DSControlWord driverStationWord = new DSControlWord();


  public Robot() {

    DataLogManager.start();

    DriverStation.startDataLog(DataLogManager.getLog());

    m_robotContainer = new RobotContainer();

    SmartDashboard.putData("Selectable Action Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("Climber Up", () -> moveClimberDown, null);
        builder.addBooleanProperty("Spin Intake", () -> spinIntake, null);
        builder.addBooleanProperty("Climber Down", () -> moveClimberUp, null);
      }});
    // controlChoiceClimberDown.setDefaultOption("A Button", XboxController.Button.kA);
    // controlChoiceClimberDown.addOption("B Button", XboxController.Button.kB);
    // controlChoiceClimberDown.addOption("X Button", XboxController.Button.kX);
    // SmartDashboard.putData("Climber Up Buttonmap", controlChoiceClimberDown);
    // controlChoiceIntake.addOption("A Button", XboxController.Button.kA);
    // controlChoiceIntake.setDefaultOption("B Button", XboxController.Button.kB);
    // controlChoiceIntake.addOption("X Button", XboxController.Button.kX);
    // SmartDashboard.putData("Intake Buttonmap", controlChoiceIntake);
    // controlChoiceClimberUp.addOption("A Button", XboxController.Button.kA);
    // controlChoiceClimberUp.addOption("B Button", XboxController.Button.kB);
    // controlChoiceClimberUp.setDefaultOption("X Button", XboxController.Button.kX);
    // SmartDashboard.putData("Climber Down Buttonmap", controlChoiceClimberUp);
    SmartDashboard.putData("PDH", m_robotContainer.m_pdh);
  }

  @Override
  public void robotInit() {
    WebServer.start(5800, Filesystem.getDeployDirectory().getPath());
  }

  @Override
  public void robotPeriodic() {
    driverStationWord.refresh();

    // System.out.println(Constants.ScoringConstants.ScoringStage + " " + Constants.ScoringConstants.ScoringStage.getElevatorRotations());
    
    SmartDashboard.putNumber("deadband val", ((0.1   /  (m_robotContainer.m_Elevator.GetPosition() + 1))));

    SmartDashboard.putNumber("left stick pos", m_robotContainer.operator.getLeftX());

    double ta = Utils.getSystemTimeSeconds();
    CommandScheduler.getInstance().run(); 
    double tX = Utils.getSystemTimeSeconds();
    SmartDashboard.putString("Scoring Stage", Constants.ScoringConstants.ScoringStage.toString());
  }







  public Rotation2d performMegaTagRotation(){
    boolean doRejectUpdate = false;
  
    LimelightHelpers.PoseEstimate mt_left = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightLeftName);
    LimelightHelpers.PoseEstimate mt_right = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightRightName);
    LimelightHelpers.PoseEstimate mt_back = LimelightHelpers.getBotPoseEstimate_wpiBlue(Constants.VisionConstants.limelightBackName);
    
    //Update Valid IDs

    // if(Constants.DriveToPosRuntime.target != null){
    // SmartDashboard.putString("reefTarget", Constants.DriveToPosRuntime.target);
    // }




    // SmartDashboard.putBoolean("LeftLimelightOnlineStatus", mt_left != null);
    // SmartDashboard.putBoolean("RightLimelightOnlineStatus", mt_right != null);
    // SmartDashboard.putBoolean("BackLimelightOnlineStatus", mt_back != null);


    if (mt_left != null) {
      mt_all.put(mt_left.avgTagArea, mt_left);
    }

    if (mt_right != null) {
      mt_all.put(mt_right.avgTagArea, mt_right);
    }

    if (mt_back != null) {
      mt_all.put(mt_back.avgTagArea, mt_back);
    }
    
    megaTagAvgAreas.addAll(mt_all.keySet());
    megaTagAvgAreas.sort(null);

    if (mt_all.size() > 0) {
      mt_inUse = mt_all.get(megaTagAvgAreas.get(megaTagAvgAreas.size()-1));
    }


    if (mt_inUse != null) {
      if(mt_inUse.tagCount == 0)
      {
        doRejectUpdate = true;
      }
      if(!doRejectUpdate)
      {
        LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightLeftName, mt_inUse.pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
        LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightRightName, mt_inUse.pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
        LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, mt_inUse.pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);    
        // m_robotContainer.drivetrain.addVisionMeasurement(
        //     mt_inUse.pose,
        //     Utils.fpgaToCurrentTime(mt_inUse.timestampSeconds));
        
      }
      Constants.VisionConstants.bestLimelightPose = mt_inUse;
      mt_all.clear();
      megaTagAvgAreas.clear();

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

      return mt_inUse.pose.getRotation();
    }
    System.out.println("bad megatagrot");
    return new Rotation2d();

  }

  public void performMegaTag2() {
    
    boolean doRejectUpdate = false;
    
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightLeftName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightRightName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, m_robotContainer.drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0);
    
    LimelightHelpers.PoseEstimate mt_left = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightLeftName);
    LimelightHelpers.PoseEstimate mt_right = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightRightName);
    LimelightHelpers.PoseEstimate mt_back = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightBackName);

    //Update Valid IDs

    if(Constants.DriveToPosRuntime.target != null){
    SmartDashboard.putString("reefTarget", Constants.DriveToPosRuntime.target);
    }




    SmartDashboard.putBoolean("LeftLimelightOnlineStatus", mt_left != null);
    SmartDashboard.putBoolean("RightLimelightOnlineStatus", mt_right != null);
    SmartDashboard.putBoolean("BackLimelightOnlineStatus", mt_back != null);

    m_robotContainer.drivetrain.setVisionMeasurementStdDevs(Constants.VisionConstants.visionStdDevs);

    if (mt_left != null) {
      mt_all.put(mt_left.avgTagArea, mt_left);
    }

    if (mt_right != null) {
      mt_all.put(mt_right.avgTagArea, mt_right);
    }

    if (mt_back != null) {
      mt_all.put(mt_back.avgTagArea, mt_back);
    }
    
    megaTagAvgAreas.addAll(mt_all.keySet());
    megaTagAvgAreas.sort(null);

    if (mt_all.size() > 0) {
      mt_inUse = mt_all.get(megaTagAvgAreas.get(megaTagAvgAreas.size()-1));
    }

    if (mt_inUse == mt_left) {
      SmartDashboard.putString("LimelightInUse", "Left");
    } else if (mt_inUse == mt_right) {
      SmartDashboard.putString("LimelightInUse", "Right");
    } else if (mt_inUse == mt_back) {
      SmartDashboard.putString("LimelightInUse", "Back");
    } else {
      SmartDashboard.putString("LimelightInUse", "None");
    }

    if(Math.abs(m_robotContainer.drivetrain.getPigeon2().getAngularVelocityZWorld().getValueAsDouble()) > 720) // if our angular velocity is greater than 720 degrees per second, ignore vision updates
    {
      doRejectUpdate = true;
    }
    if (mt_inUse != null) {
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
      Constants.VisionConstants.bestLimelightPose = mt_inUse;
      mt_all.clear();
      megaTagAvgAreas.clear();

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
    }
  }

  public void performMegaTagXY(Rotation2d rot) {
    
    boolean doRejectUpdate = false;
    
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightLeftName, rot.getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightRightName, rot.getDegrees(), 0, 0, 0, 0, 0);
    LimelightHelpers.SetRobotOrientation(Constants.VisionConstants.limelightBackName, rot.getDegrees(), 0, 0, 0, 0, 0);
    
    LimelightHelpers.PoseEstimate mt_left = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightLeftName);
    LimelightHelpers.PoseEstimate mt_right = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightRightName);
    LimelightHelpers.PoseEstimate mt_back = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(Constants.VisionConstants.limelightBackName);

    //Update Valid IDs

   




    m_robotContainer.drivetrain.setVisionMeasurementStdDevs(Constants.VisionConstants.visionStdDevs);

    if (mt_left != null) {
      mt_all.put(mt_left.avgTagArea, mt_left);
    }

    if (mt_right != null) {
      mt_all.put(mt_right.avgTagArea, mt_right);
    }

    if (mt_back != null) {
      mt_all.put(mt_back.avgTagArea, mt_back);
    }
    
    megaTagAvgAreas.addAll(mt_all.keySet());
    megaTagAvgAreas.sort(null);

    if (mt_all.size() > 0) {
      mt_inUse = mt_all.get(megaTagAvgAreas.get(megaTagAvgAreas.size()-1));
    }

    if (mt_inUse == mt_left) {
      SmartDashboard.putString("LimelightInUse", "Left");
    } else if (mt_inUse == mt_right) {
      SmartDashboard.putString("LimelightInUse", "Right");
    } else if (mt_inUse == mt_back) {
      SmartDashboard.putString("LimelightInUse", "Back");
    } else {
      SmartDashboard.putString("LimelightInUse", "None");
    }

    if (mt_inUse != null) {
      if(mt_inUse.tagCount == 0)
      {
        doRejectUpdate = true;
      }
      if(!doRejectUpdate)
      {
        m_robotContainer.drivetrain.resetPose(
            new Pose2d(mt_inUse.pose.getTranslation(), rot)
            // Utils.fpgaToCurrentTime(mt_inUse.timestampSeconds)
            );
      }
      Constants.VisionConstants.bestLimelightPose = mt_inUse;
      mt_all.clear();
      megaTagAvgAreas.clear();

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
    }
  }


  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    performMegaTag2();
  }

  @Override
  public void disabledPeriodic() {
    if (driverStationWord.isAutonomous()) {
      autoDisabledPeriodic();
    }
  }



  public void autoDisabledPeriodic(){

    performMegaTagXY(performMegaTagRotation());




  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    performMegaTag2();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  // private void tagFilter(int id, boolean add) {
  //   if (!add) {
  //     if (validIDs.contains(Integer.valueOf(id))) {
  //       validIDs.remove(Integer.valueOf(id));
  //     }
  //   } else {
  //     if (!validIDs.contains(id)) {
  //       validIDs.add(id);
  //     }
  //   }
  // }
  // public static void tagReset() {
  //   validIDs = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));
  // }
}

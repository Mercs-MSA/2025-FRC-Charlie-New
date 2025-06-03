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
import frc.robot.subsystems.Vision.ApriltagLimelightVision;
import frc.robot.subsystems.Vision.ApriltagLimelightVision.VisionSource;



public class Robot extends TimedRobot {
  

  private Command m_autonomousCommand;

  boolean moveClimberDown;
  boolean spinIntake;
  boolean moveClimberUp;
  // private SendableChooser<XboxController.Button> controlChoiceClimberDown = new SendableChooser<>();
  // private SendableChooser<XboxController.Button> controlChoiceIntake = new SendableChooser<>();
  // private SendableChooser<XboxController.Button> controlChoiceClimberUp = new SendableChooser<>();


  public final XboxController testJoystick = new XboxController(2);

  private RobotContainer m_robotContainer; 


  LimelightHelpers.PoseEstimate mt_inUse = null;

  HashMap<Double, LimelightHelpers.PoseEstimate> mt_all = new HashMap<>();
  ArrayList<Double> megaTagAvgAreas = new ArrayList<>();

  // private static ArrayList<Integer> validIDs = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));


  DSControlWord driverStationWord = new DSControlWord();

  public static ApriltagLimelightVision Vision;


  public Robot() {
  }

  @Override
  public void robotInit() {

    DataLogManager.start();
    DriverStation.startDataLog(DataLogManager.getLog());

    m_robotContainer = new RobotContainer();

    Vision = new ApriltagLimelightVision(m_robotContainer);

    SmartDashboard.putData("Selectable Action Test", new Sendable() {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("Climber Up", () -> moveClimberDown, null);
        builder.addBooleanProperty("Spin Intake", () -> spinIntake, null);
        builder.addBooleanProperty("Climber Down", () -> moveClimberUp, null);
      }
    });

    SmartDashboard.putData("PDH", m_robotContainer.m_pdh);

    WebServer.start(5800, Filesystem.getDeployDirectory().getPath());
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

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Vision.setVisionSource(VisionSource.MegaTag2);
    Vision.updateVisionEstimates(m_robotContainer.drivetrain.getState().Pose.getRotation());
    Vision.applyVisionToEstimator();  }

  @Override
  public void disabledPeriodic() {
    

    if (driverStationWord.isAutonomous() && m_robotContainer != null) {
      autoDisabledPeriodic();
    }
  }



  public void autoDisabledPeriodic(){
    Vision.setVisionSource(VisionSource.MegaTag1);
    Rotation2d rotation = Vision.estimateRotationFromVision();
    Vision.updateVisionEstimates(rotation);
    Vision.resetPoseFromVision(rotation);



  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    Vision.setVisionSource(VisionSource.MegaTag2);
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    Vision.updateVisionEstimates(m_robotContainer.drivetrain.getState().Pose.getRotation());
    Vision.updateAutoTargetFromVision();
    Vision.applyVisionToEstimator();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

 
}

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
import frc.robot.commands.CANdleCommands.CommandCandleSetAnimation;
import frc.robot.subsystems.SensorSubsystems.CANdle_LED.AnimationTypes;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;


import au.grapplerobotics.CanBridge;

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
    CanBridge.runTCP();

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
    SmartDashboard.putData("CommandSched", CommandScheduler.getInstance());
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

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
    // performMegaTag2();
  }

  @Override
  public void disabledPeriodic() {
    if (driverStationWord.isAutonomous()) {
      autoDisabledPeriodic();
    }
  }



  public void autoDisabledPeriodic(){

    // performMegaTagXY(performMegaTagRotation());




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
    // performMegaTag2();
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

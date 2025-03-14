// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.AlgaeCommands.AlgaePivotToPos;
import frc.robot.commands.AlgaeCommands.AlgaeRollerVoltage;
// import frc.robot.commands.*;
// import frc.robot.commands.CANdleCommands.CommandCandleSetAnimation;
import frc.robot.commands.ClimberCommands.CommandClimbToggleDown;
import frc.robot.commands.ClimberCommands.CommandClimbToggleUp;
import frc.robot.commands.ClimberCommands.CommandClimberManual;
import frc.robot.commands.DriveToPosCommands.AutoSeed;
import frc.robot.commands.DriveToPosCommands.CommandLoadDriveToPos;
import frc.robot.commands.DriveToPosCommands.CommandSetDriveToPos;
import frc.robot.commands.DriveToPosCommands.CommandToPos;
import frc.robot.commands.ElevatorCommands.CommandElevatorToStage;
import frc.robot.commands.FunnelCommands.CommandFunnelPivotToPos;
import frc.robot.commands.FunnelCommands.CommandFunnelToggle;
import frc.robot.commands.IntakeCommands.CommandIntakeCollect;
import frc.robot.commands.IntakeCommands.CommandIntakeCollectAuto;
import frc.robot.commands.IntakeCommands.CommandIntakeOut;
import frc.robot.commands.IntakeCommands.CommandIntakeStop;
import frc.robot.commands.IntakeCommands.CommandScoreAuto;
import frc.robot.commands.IntakeCommands.CommandWaitUntilIntakeBreak;
import frc.robot.commands.ScoringModeCommands.CommandChangeScoreStage;
import frc.robot.commands.VisionCommands.CommandForceVisionMeasurement;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.subsystems.Mechanisms.AlgaePivot.AlgaePivot;
import frc.robot.subsystems.Mechanisms.AlgaeRoller.AlgaeRoller;
import frc.robot.subsystems.Mechanisms.Climber.Climber;
import frc.robot.subsystems.Mechanisms.Elevator.Elevator;
import frc.robot.subsystems.Mechanisms.Funnel.FunnelPivot;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;
// import frc.robot.subsystems.SensorSubsystems.CANdle_LED;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;
import frc.robot.subsystems.SensorSubsystems.FunnelBeambreak;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;
import frc.robot.generated.TunerConstants;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.FieldCentric povDrive = new SwerveRequest.FieldCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    public final CommandXboxController driver = new CommandXboxController(0);
    public final CommandXboxController operator = new CommandXboxController(1);


    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final Elevator m_Elevator = new Elevator(true);


    public final Climber m_Climber = new Climber(true);

    public final IntakeFlywheels m_IntakeFlywheels = new IntakeFlywheels(true);

    public final AlgaePivot m_AlgaePivot = new AlgaePivot(true);

    public final AlgaeRoller m_AlgaeRoller = new AlgaeRoller(true);

    public final IntakeBeambreak m_intakeBeamBreak = new IntakeBeambreak();
    public final FunnelBeambreak m_funnelBeamBreak = new FunnelBeambreak();

    public final FunnelPivot m_FunnelPivot = new FunnelPivot(true);

    // public final CANdle_LED m_leds = new CANdle_LED();

    public final PowerDistribution m_pdh = new PowerDistribution();





    private final SendableChooser<Command> autoChooser;

       Map<String, Command> autonomousCommands = new HashMap<String, Command>() {
        {
            
            // put("PathWithDriveToPos", new SequentialCommandGroup(
            //     new CommandSetDriveToPos("ReefTest"),
            //     new CommandToPos(drivetrain)
            // ));

            put("Descore Low", 
            new SequentialCommandGroup(new AlgaePivotToPos(m_AlgaePivot, Constants.AlgaePivotConstants.posBottomDescore), new AlgaeRollerVoltage(m_AlgaeRoller, -10))
            );

            put("Descore High", 
            new SequentialCommandGroup(new AlgaePivotToPos(m_AlgaePivot, Constants.AlgaePivotConstants.posTopUp), new AlgaeRollerVoltage(m_AlgaeRoller, 10))           
             );

            put("Algae Pivot Reset", 
            new SequentialCommandGroup(new AlgaePivotToPos(m_AlgaePivot, 0.5), new AlgaeRollerVoltage(m_AlgaeRoller, 0))            
            );
            
            put("Safety Command",
            new CommandWaitUntilIntakeBreak(m_intakeBeamBreak)
            );

            put("Intake Collect", 
                new CommandIntakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, MaxAngularRate)
                );

            put("Auto Intake Collect",
                new CommandIntakeCollectAuto(m_IntakeFlywheels, m_funnelBeamBreak, m_intakeBeamBreak, MaxAngularRate)
                );
            
            /* Scoring */
            put("Score", 
                new CommandScoreAuto(m_IntakeFlywheels, m_intakeBeamBreak, m_Elevator, 11)
                );
            
            put("L1", new SequentialCommandGroup(
                new CommandChangeScoreStage(ScoringStageVal.L1),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator)

            ));

            put("L2", new SequentialCommandGroup(
                new CommandChangeScoreStage(ScoringStageVal.L2),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator)

            ));

            put("L3", new SequentialCommandGroup(
                new CommandChangeScoreStage(ScoringStageVal.L3),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator)

            ));

            put("L4", new SequentialCommandGroup(
                new CommandChangeScoreStage(ScoringStageVal.L4),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator)
            ));

            put("ELEVIntakePos", new SequentialCommandGroup(
                new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator)
            ));

            put("MoveFunnel", new SequentialCommandGroup(
                new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY),
                new CommandFunnelPivotToPos(Constants.FunnelPivotConstants.posUp)
            ));

            put("ForceVision", new CommandForceVisionMeasurement(drivetrain));

            put("Reset All", new ParallelCommandGroup(
                
            ));
        }
    };

    public RobotContainer() {

        boolean isCompetition = false; //Change to true at comp

        NamedCommands.registerCommands(autonomousCommands);

        // autoChooser = AutoBuilder.buildAutoChooser("Do Nothing");
        // SmartDashboard.putData("Auto Mode", autoChooser);

        /*this is to filter out all other autos*/
        autoChooser = AutoBuilder.buildAutoChooserWithOptionsModifier(
        (stream) -> isCompetition
        ? stream.filter(auto -> auto.getName().startsWith("Comp"))
        : stream
        );

        autoChooser.setDefaultOption("Do Nothing", AutoBuilder.buildAuto("Do Nothing")); // delete if it doesn't work
        SmartDashboard.putData("Auto Chooser", autoChooser);

        Trigger intakeBreakTrigger = new Trigger(m_intakeBeamBreak::checkBreak);
        intakeBreakTrigger.onTrue(new CommandIntakeStop(m_IntakeFlywheels, m_intakeBeamBreak));

        configureBindings();
        driverControls();
        operatorControls();
        }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
            drive.withDeadband(MaxSpeed * ((0.1   /  (m_Elevator.GetPosition() + 1)))).withVelocityX(-driver.getLeftY() * (MaxSpeed - Constants.slowDownWithElevator(m_Elevator.GetPosition()))) // Drive forward with negative Y (forward)
                    .withVelocityY(-driver.getLeftX() * (MaxSpeed - Constants.slowDownWithElevator(m_Elevator.GetPosition()))) // Drive left with negative X (left)
                    .withRotationalRate(-driver.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        

        // driver.b().whileTrue(drivetrain.applyRequest(() ->
        //     point.withModuleDirection(new Rotation2d(-driver.getLeftY(), -driver.getLeftX()))
        // ));

        // Run SysId routines when holding back/start and X/Y.
        // // Note that each routine should be run exactly once in a single log.
        // driver.back().and(driver.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        // driver.back().and(driver.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));   

        // driver.start().and(driver.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        // driver.start().and(driver.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        drivetrain.registerTelemetry(logger::telemeterize);

        

    }

        public void driverControls() {
            driver.pov(0).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(0.5).withVelocityY(0)));
            driver.pov(45).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(0.5).withVelocityY(-0.5)));
            driver.pov(90).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(0).withVelocityY(-0.5)));
            driver.pov(135).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(-0.5).withVelocityY(-0.5)));
            driver.pov(180).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(-0.5).withVelocityY(0)));
            driver.pov(225).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(-0.5).withVelocityY(0.5)));
            driver.pov(270).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(0).withVelocityY(0.5)));
            driver.pov(315).whileTrue(drivetrain.applyRequest(() -> povDrive.withVelocityX(0.5).withVelocityY(0.5)));

            

            driver.rightTrigger(0.5).onTrue(new CommandIntakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 8));

            driver.a().onTrue(new CommandIntakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 12));
            driver.b().onTrue(new CommandIntakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 12));



            // driver.leftTrigger().whileTrue(new CommandIntakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 8));
            // driver.rightTrigger(.8).whileTrue(new CommandIntakeOut(m_IntakeFlywheels, m_intakeBeamBreak, 8));


            driver.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric())); //Seed

            // driver.start().onTrue(new CommandCandleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Twinkle));
            driver.back().whileTrue(new CommandSetDriveToPos("AutoLeft").andThen(new CommandToPos(drivetrain)));


            driver.leftBumper().whileTrue((new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(0))).andThen(new ParallelCommandGroup (
                new CommandToPos(drivetrain),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator)
                // new CommandCandleSetAnimation(m_leds, CANdle_LED.AnimationTypes.Strobe)
                )));
            driver.rightBumper().whileTrue((new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(1))).andThen(new ParallelCommandGroup (
                new CommandToPos(drivetrain),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator)
                )));

            driver.leftTrigger().whileTrue((new CommandLoadDriveToPos(() -> Constants.DriveToPosRuntime.autoTargets.get(2))).andThen(new ParallelCommandGroup (
                new CommandToPos(drivetrain)
                )));
    






          
        }

        public void operatorControls(){


            operator.pov(180).onTrue(new CommandChangeScoreStage(ScoringStageVal.L1));

            operator.pov(270).onTrue(new CommandChangeScoreStage(ScoringStageVal.L2));

            operator.pov(0).onTrue(new CommandChangeScoreStage(ScoringStageVal.L3));

            operator.pov(90).onTrue(new CommandChangeScoreStage(ScoringStageVal.L4));


            operator.b().onTrue(new SequentialCommandGroup(

                new CommandChangeScoreStage(ScoringStageVal.INTAKEREADY),
                new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator),
                new CommandIntakeCollect(m_IntakeFlywheels, m_intakeBeamBreak, MaxAngularRate)));


            operator.start().whileTrue(new CommandChangeScoreStage(ScoringStageVal.CLIMBING).andThen(new CommandClimbToggleDown(m_Climber, m_FunnelPivot)));

            operator.back().whileTrue(new CommandChangeScoreStage(ScoringStageVal.CLIMBING).andThen(new CommandClimbToggleUp(m_Climber, m_FunnelPivot)));

            // operator.leftStick().whileTrue(new SequentialCommandGroup(
            //     new CommandChangeScoreStage(ScoringStageVal.CLIMBING), 
            //     new CommandClimberManual(m_Climber, m_FunnelPivot, operator.getLeftX()
            //     )));
                
    

            operator.rightStick().onTrue(new CommandFunnelToggle(m_FunnelPivot, m_Climber));

            operator.rightBumper().onTrue(new CommandElevatorToStage(m_intakeBeamBreak, m_Elevator));

            operator.rightTrigger(0.8).onTrue(new SequentialCommandGroup(new AlgaePivotToPos(m_AlgaePivot, Constants.AlgaePivotConstants.posTopUp), new AlgaeRollerVoltage(m_AlgaeRoller, 10)));

            operator.leftTrigger(0.8).onTrue(new SequentialCommandGroup(new AlgaePivotToPos(m_AlgaePivot, Constants.AlgaePivotConstants.posBottomDescore), new AlgaeRollerVoltage(m_AlgaeRoller, -10)));

            operator.y().onTrue(new SequentialCommandGroup(new AlgaePivotToPos(m_AlgaePivot, 0.5), new AlgaeRollerVoltage(m_AlgaeRoller, 0)));
        
        }


    public Command getAutonomousCommand() {
        return autoChooser.getSelected(); 
    }

}

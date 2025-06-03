package frc.robot.commands.DriveToPosCommands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Mechanisms.Elevator.Elevator;
import frc.robot.LimelightHelpers;


public class CommandTagAlign extends Command {

    private double MaxAngularRate;
    private DoubleSupplier StickX;
    private DoubleSupplier StickY;
    private BooleanSupplier leftStick;
    private Elevator Elevator;
    private final CommandSwerveDrivetrain drivetrain;
    SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric();

    private final ProfiledPIDController thetaController =
    new ProfiledPIDController(8, 0.01, 0.01, new TrapezoidProfile.Constraints(Math.PI, Math.PI));
  
    public CommandTagAlign (CommandSwerveDrivetrain swerve, double maxAngularRate, DoubleSupplier stickDriveX, DoubleSupplier stickDriveY, Elevator m_Elevator, BooleanSupplier leftStick) {
        this.Elevator = m_Elevator;
        this.drivetrain = swerve;
        this.StickX = stickDriveX;
        this.StickY = stickDriveY;
        this.leftStick = leftStick;
        MaxAngularRate = maxAngularRate;

        thetaController.setTolerance(Units.degreesToRadians(Constants.DriveToPoseConstants.angularDegreesTolerance));
        
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SmartDashboard.putData("aligningTheta_Pid", thetaController);

        SmartDashboard.putNumber("Theta_Tgt", -1);

        addRequirements(swerve);
    }

    @Override
    public void initialize() {
        var currPose = drivetrain.getState().Pose;
        thetaController.reset(currPose.getRotation().getRadians());
    }

    @Override
    public void execute() {
        double dval;
        dval =  LimelightHelpers.getTX("limelight-left");
        
        // targetingAngularVelocity *= MaxAngularRate;

        double currentYaw = drivetrain.getState().Pose.getRotation().getRadians();
        double targetYaw = currentYaw + Units.degreesToRadians(dval);
        double thetaVelocity = thetaController.calculate(currentYaw, targetYaw);


        SmartDashboard.putNumber("targetTheta", targetYaw);
        SmartDashboard.putNumber("thetaTargetvelocity", thetaVelocity);
        drivetrain.setControl(
                driveRequest
                .withRotationalRate(thetaVelocity)
                .withDeadband(5.5 * ((0.1   /  (Elevator.GetPosition() + 1)))).withVelocityX(-StickY.getAsDouble() * (5.5 - Constants.slowDownWithElevator(Elevator.GetPosition()))) // Drive forward with negative Y (forward)
                .withVelocityY(-StickX.getAsDouble() * (5.5 - Constants.slowDownWithElevator(Elevator.GetPosition())))
        );
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return (LimelightHelpers.getTargetCount(Constants.VisionConstants.limelightLeftName) == 0 || leftStick.getAsBoolean() == true);
    }
}

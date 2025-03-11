package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.LimelightResults;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;

public class CommandForceVisionMeasurement extends Command {
  private final CommandSwerveDrivetrain drivetrain;

  public CommandForceVisionMeasurement(CommandSwerveDrivetrain swerve) {
    this.drivetrain = swerve;
    addRequirements(swerve);
  }

  @Override
  public void initialize() {
    LimelightHelpers.SetRobotOrientation_INTERNAL(Constants.VisionConstants.limelightLeftName, drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0, false);
    LimelightHelpers.SetRobotOrientation_INTERNAL(Constants.VisionConstants.limelightRightName, drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0, false);
    LimelightHelpers.SetRobotOrientation_INTERNAL(Constants.VisionConstants.limelightBackName, drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0, false);
  }

  @Override
  public void execute() {
    System.out.println(drivetrain.getState().Pose.getRotation().getDegrees());
    System.out.println(Constants.VisionConstants.bestLimelightPose.pose.getRotation().getDegrees());
  }
  
  @Override
  public boolean isFinished(){
    if (Constants.VisionConstants.bestLimelightPose == null) {
      return false;
    }
    return Math.abs(Constants.VisionConstants.bestLimelightPose.pose.getRotation().getDegrees()) > 0.2;
  }
}
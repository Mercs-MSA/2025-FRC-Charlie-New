package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;

public class CommandForceVisionMeasurement extends Command {
  private final CommandSwerveDrivetrain drivetrain;

  public CommandForceVisionMeasurement(CommandSwerveDrivetrain swerve) {
    this.drivetrain = swerve;
    addRequirements(swerve);
  }

  @Override
  public void initialize() {
    LimelightHelpers.SetRobotOrientation_INTERNAL(Constants.VisionConstants.limelightLeftName, drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0, true);
    LimelightHelpers.SetRobotOrientation_INTERNAL(Constants.VisionConstants.limelightRightName, drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0, true);
    LimelightHelpers.SetRobotOrientation_INTERNAL(Constants.VisionConstants.limelightBackName, drivetrain.getState().Pose.getRotation().getDegrees(), 0, 0, 0, 0, 0, true);
  }

  @Override
  public void execute() {
    
}
  @Override
  public boolean isFinished(){
    return true;
  }
}
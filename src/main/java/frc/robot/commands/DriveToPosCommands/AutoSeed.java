package frc.robot.commands.DriveToPosCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve.CommandSwerveDrivetrain;

public class AutoSeed extends Command {
  private final CommandSwerveDrivetrain drivetrain;
  private final Pose2d pose;

  public AutoSeed(CommandSwerveDrivetrain swerve, Pose2d pose) {
    this.drivetrain = swerve;
    this.pose = pose;
    addRequirements(swerve);
  }

  @Override
  public void initialize() {
    this.drivetrain.resetPose(this.pose);
  }

  @Override
  public void execute() {
    
}
  @Override
  public boolean isFinished(){
    return true;
  }
}
package frc.robot.commands.DriveToPosCommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;

public class CommandLoadDriveToPos extends Command {
    private Supplier<String> getter;

    public CommandLoadDriveToPos(Supplier<String> positionGetter) {
        this.getter = positionGetter;
    }

    @Override
    public void initialize() {
        // Robot.tagReset();
        Constants.DriveToPosRuntime.target = this.getter.get();
        SmartDashboard.putString("driveToPose_Target-TAG", Constants.DriveToPosRuntime.target);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}

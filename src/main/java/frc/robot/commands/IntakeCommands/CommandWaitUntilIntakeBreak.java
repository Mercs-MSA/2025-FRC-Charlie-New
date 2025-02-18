package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;

public class CommandWaitUntilIntakeBreak extends Command{
    private IntakeBeambreak m_break;

    public CommandWaitUntilIntakeBreak(IntakeBeambreak beambreak) {
        this.m_break = beambreak;
    }

    @Override
    public boolean isFinished() {
        return m_break.checkBreak();
    }
}

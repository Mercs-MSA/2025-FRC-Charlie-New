package frc.robot.commands.AlgaeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Mechanisms.AlgaeRoller.AlgaeRoller;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;
public class AlgaeRollerVoltage extends Command {
    private final AlgaeRoller m_AlgaeRoller;
    private double voltage;

    public AlgaeRollerVoltage(AlgaeRoller m_AlgaeRoller, double voltage) {
        this.voltage = voltage;
        this.m_AlgaeRoller = m_AlgaeRoller;
        addRequirements(m_AlgaeRoller);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        m_AlgaeRoller.applyVoltage(voltage);
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
    }

    @Override 
    public boolean isFinished() {
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        return true; // Will check beambreak until it returns true (meaning it got broke)
    }
}

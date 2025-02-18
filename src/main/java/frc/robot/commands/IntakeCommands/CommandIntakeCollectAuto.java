package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;
import frc.robot.subsystems.SensorSubsystems.FunnelBeambreak;
import frc.robot.Constants.ScoringConstants;
import frc.robot.Constants.ScoringStageVal;


public class CommandIntakeCollectAuto extends Command {
    private final IntakeFlywheels m_intakeFlywheels;
    private final FunnelBeambreak m_funnelBeambreak;
    
    private double voltage;

    private final ScoringStageVal initialStage = ScoringStageVal.INTAKING;
    private final ScoringStageVal finalStage = ScoringStageVal.INTAKEREADY;



    public CommandIntakeCollectAuto(IntakeFlywheels m_intakeFlywheels, FunnelBeambreak m_funnelBeambreak, double voltage) {
        this.voltage = voltage;
        this.m_funnelBeambreak = m_funnelBeambreak;
        this.m_intakeFlywheels = m_intakeFlywheels;
        addRequirements(m_funnelBeambreak, m_intakeFlywheels);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        ScoringConstants.ScoringStage = initialStage;
        m_intakeFlywheels.applyVoltage(voltage);
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
        ScoringConstants.ScoringStage = finalStage;


    }

    @Override 
    public boolean isFinished() {
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        return m_funnelBeambreak.checkBreak(); // Will check beambreak until it returns true (meaning it got broke)
    }
}

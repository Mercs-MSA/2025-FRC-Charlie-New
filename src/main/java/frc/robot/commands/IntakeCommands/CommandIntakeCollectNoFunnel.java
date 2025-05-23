package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;
import frc.robot.Constants.ScoringConstants;
import frc.robot.Constants.ScoringStageVal;


public class CommandIntakeCollectNoFunnel extends Command {
    private final IntakeFlywheels m_intakeFlywheels;
    private final IntakeBeambreak m_IntakeBeambreak;
    
    private double voltage;

    private final ScoringStageVal initialStage = ScoringStageVal.INTAKING;
    private final ScoringStageVal finalStage = ScoringStageVal.INTAKEREADY;



    public CommandIntakeCollectNoFunnel(IntakeFlywheels m_intakeFlywheels, IntakeBeambreak m_IntakeBeambreak, double voltage) {
        this.voltage = voltage;
        this.m_IntakeBeambreak = m_IntakeBeambreak;

        this.m_intakeFlywheels = m_intakeFlywheels;
        addRequirements(m_IntakeBeambreak, m_intakeFlywheels);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        if(!m_IntakeBeambreak.checkBreak()){
        ScoringConstants.ScoringStage = initialStage;
        m_intakeFlywheels.applyVoltage(voltage);
        }
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
        ScoringConstants.ScoringStage = finalStage;

        // m_intakeFlywheels.stopIntake();
    }

    @Override 
    public boolean isFinished() {
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        return m_IntakeBeambreak.checkBreak(); // Will check beambreak until it returns true (meaning it got broke)
    }
}

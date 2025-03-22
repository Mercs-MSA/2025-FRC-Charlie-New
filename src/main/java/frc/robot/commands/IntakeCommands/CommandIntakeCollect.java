package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;
import frc.robot.subsystems.SensorSubsystems.FunnelBeambreak;
import frc.robot.Constants.ScoringConstants;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.commands.RumbleCommand.CommandRumble;


public class CommandIntakeCollect extends Command {
    private final IntakeFlywheels m_intakeFlywheels;
    private final IntakeBeambreak m_IntakeBeambreak;
    private final FunnelBeambreak m_FunnelBeambreak;


    



    
    private double voltage;

    private final ScoringStageVal initialStage = ScoringStageVal.INTAKING;
    private final ScoringStageVal finalStage = ScoringStageVal.INTAKEREADY;



    public CommandIntakeCollect(IntakeFlywheels m_intakeFlywheels, IntakeBeambreak m_IntakeBeambreak, FunnelBeambreak m_FunnelBeambreak, double voltage) {
        this.voltage = voltage;
        this.m_IntakeBeambreak = m_IntakeBeambreak;
        this.m_FunnelBeambreak = m_FunnelBeambreak;

        this.m_intakeFlywheels = m_intakeFlywheels;
        addRequirements(m_IntakeBeambreak, m_intakeFlywheels);
    }

    @Override 
    public void initialize() {
        System.out.println("DEBUG: CommandIntakeCollect::init");
        // This is where you put stuff that happens right at the start of the command
        if(!m_IntakeBeambreak.checkBreak()){
            ScoringConstants.ScoringStage = initialStage;
            System.out.println("DEBUG: CommandIntakeCollect::init - Applying voltage to flywheels");
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

        m_intakeFlywheels.stopIntake();
        System.out.println("DEBUG: CommandIntakeCollect::end");
        

        

       
      
    
        // m_intakeFlywheels.stopIntake();
    }

    @Override 
    public boolean isFinished() {
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        return m_IntakeBeambreak.checkBreak(); // Will check beambreak until it returns true (meaning it got broke)
    }
}

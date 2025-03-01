package frc.robot.commands.FunnelCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.subsystems.Mechanisms.Climber.Climber;
import frc.robot.subsystems.Mechanisms.Funnel.FunnelPivot;


public class CommandFunnelToggle extends Command {
    private FunnelPivot m_FunnelPivot;
    private Climber m_Climber;


    public CommandFunnelToggle(FunnelPivot m_FunnelPivot, Climber m_climber) {
        this.m_FunnelPivot = m_FunnelPivot;
        this.m_Climber = m_climber;
        addRequirements(m_FunnelPivot);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command
        if(Constants.ScoringConstants.ScoringStage.canPivot()  || (Constants.ScoringConstants.ScoringStage == ScoringStageVal.CLIMBING && m_Climber.GetPosition() > 0)){
            if (Constants.isWithinTol(Constants.FunnelPivotConstants.posDown, m_FunnelPivot.getPivotMotorPosition(), 0.09))
            {
                m_FunnelPivot.motorToPosMM(Constants.FunnelPivotConstants.posUp);

            }
            else
            {
                m_FunnelPivot.motorToPosMM(Constants.FunnelPivotConstants.posDown);

            }
    }
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
        // In this example, it will just instantly come out as true and stop the command as soon as it's called.
        return true;
    }


}

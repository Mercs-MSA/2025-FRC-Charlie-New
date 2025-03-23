package frc.robot.commands.ScoringModeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ScoringConstants;
import frc.robot.Constants.ScoringStageVal;

public class CommandChangeScoreStage extends Command {

    private final ScoringStageVal target;

    public CommandChangeScoreStage(ScoringStageVal t)
    {
       this.target = t;
    }
    
    @Override 
    public void initialize() {
        System.out.println("DEBUG: CommandChangeScoreStage::init");
        if(ScoringConstants.ScoringStage != ScoringStageVal.INTAKING){
            ScoringConstants.ScoringStage = target;
            System.out.println("DEBUG: CommandChangeScoreStage::init - Changed to" + this.target.toString());
        }

        // This is where you put stuff that happens right at the start of the command
    }

    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
        System.out.println("DEBUG: CommandChangeScoreStage::end");
    }

    @Override 
    public boolean isFinished() {
        
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        // In this example, it will just instantly come out as true and stop the command as soon as it's called.
        // System.out.println("isf");
        // System.out.println(Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol));
        // return Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol);
        return true;
    }

}

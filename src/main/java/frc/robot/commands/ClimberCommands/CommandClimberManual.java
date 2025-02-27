package frc.robot.commands.ClimberCommands;

import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.Elevator1Constants;
import frc.robot.subsystems.Mechanisms.Climber.Climber;
import frc.robot.subsystems.Mechanisms.Funnel.FunnelPivot;

public class CommandClimberManual extends Command {
    private double leftXVal;
    private Climber m_Climber;
    private FunnelPivot m_FunnelPivot;


    public CommandClimberManual(Climber m_Climber, FunnelPivot m_FunnelPivot, double leftXVal) {
        this.leftXVal = leftXVal;
        this.m_Climber = m_Climber;
        this.m_FunnelPivot = m_FunnelPivot;
        addRequirements(m_Climber, m_FunnelPivot);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command

        if (Constants.ScoringConstants.ScoringStage.canClimb() && m_FunnelPivot.getPivotMotorPosition() < 0.1 && leftXVal > 0.2){
            System.out.println("climbing");
            m_Climber.climberGoToPosition(ClimberConstants.positionUp);
        }

        else if (Constants.ScoringConstants.ScoringStage.canClimb() && m_FunnelPivot.getPivotMotorPosition() < 0.1 && leftXVal < -0.2){
            System.out.println("climbing");

            m_Climber.climberGoToPosition(ClimberConstants.positionDown);

        }
            }



    @Override 
    public void execute() {
        // This is where you put stuff that happens while the command is happening, it will loop here over and over
        
    }

    @Override 
    public void end(boolean interrupted) {
    }

    @Override 
    public boolean isFinished() {
        
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        // In this example, it will just instantly come out as true and stop the command as soon as it's called.
        // System.out.println("isf");
        // System.out.println(Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol));
        // return Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol);
        return false;
    }


}

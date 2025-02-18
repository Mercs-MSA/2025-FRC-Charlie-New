package frc.robot.commands.AlgaeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.AlgaePivotConstants;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Mechanisms.AlgaePivot.AlgaePivot;
import frc.robot.subsystems.Mechanisms.AlgaePivot.*;

public class AlgaePivotToPos extends Command {
    private AlgaePivot m_AlgaePivot;
    private double tarPos;


    public AlgaePivotToPos(AlgaePivot m_AlgaePivot, double tarPos) {
        this.tarPos = tarPos;
        this.m_AlgaePivot = m_AlgaePivot;
        addRequirements(m_AlgaePivot);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command

                m_AlgaePivot.PivotGoToPosition(tarPos);

        
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
        // System.out.println("isf");
        // System.out.println(Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol));
        // return Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol);
        return true;
    }


}

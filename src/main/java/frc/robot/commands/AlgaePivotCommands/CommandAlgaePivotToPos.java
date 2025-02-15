package frc.robot.commands.AlgaePivotCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Mechanisms.AlgaePivot.AlgaePivot;

public class CommandAlgaePivotToPos extends Command {
    private double targetPos;
    private AlgaePivot m_AlgaePivot;


    public CommandAlgaePivotToPos(AlgaePivot m_AlgaePivot, double tarPos) {
        this.targetPos = tarPos;
        this.m_AlgaePivot = m_AlgaePivot;
        addRequirements(m_AlgaePivot);
    }

    @Override 
    public void initialize() {
        // This is where you put stuff that happens right at the start of the command

       m_AlgaePivot.algaePivotPositionVoltage(targetPos);
        
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
        return true;
    }


}

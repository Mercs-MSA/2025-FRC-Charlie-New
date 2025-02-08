package frc.robot.commands.CANdleCommands;

import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.time.StopWatch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SensorSubsystems.CANdle_LED;
import frc.robot.subsystems.SensorSubsystems.CustomAnim;

public class CommandCandleSetCustomAnim extends Command {

    private CANdle_LED m_leds;
    private CustomAnim anim;
    private StopWatch startTime;
    private StopWatch lastInterval;
    private int lastStartPos = 0;

    public CommandCandleSetCustomAnim(CANdle_LED leds, CustomAnim anim) {
        addRequirements(leds);
        this.anim = anim;
        this.m_leds = leds;
    }

    @Override
    public void initialize() {
        startTime = new StopWatch();
        startTime.start();
        lastInterval = new StopWatch();
        lastInterval.start();
    }

    @Override
    public void execute()
    {
        switch (anim.getAnim())
        {
            case MercsPattern:
                double currTime = lastInterval.getDuration();
                if (currTime > anim.getInterval())
                {
                    SmartDashboard.putNumber(getName(), currTime);
                    m_leds.setLEDs(lastStartPos + 2, 3, new int[]{255, 0, 0});
                    m_leds.setLEDs(lastStartPos + 1, 3, new int[]{255, 255, 255});
                    m_leds.setLEDs(lastStartPos, 3, new int[]{0, 0, 0});
                    lastStartPos += 1;
                    lastInterval.start();
                }
                break;
            default:
                break;
        }
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
        m_leds.stopAnim();
    }

    @Override 
    public boolean isFinished() {
        
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        // In this example, it will just instantly come out as true and stop the command as soon as it's called.
        // System.out.println("isf");
        // System.out.println(Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol));
        // return Constants.isWithinTol(pos, m_testIntakePivot.GetPosition(), Constants.TestIntakePivotConstants.tol);
        
        return startTime.getDuration() >= anim.getDuration(); 
    }

    
}

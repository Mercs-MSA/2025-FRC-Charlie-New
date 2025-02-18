package frc.robot.subsystems.SensorSubsystems;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj.AsynchronousInterrupt;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FunnelBeambreak extends SubsystemBase{
    private final DigitalInput m_funnelBeamBreak = new DigitalInput(Constants.FunnelBeambreakConstants.beamBreakChannel);

    private boolean detectsCoral = false;

    BiConsumer<Boolean, Boolean> callback = (risingEdge, fallingEdge) -> {
        if (risingEdge){
            this.detectsCoral = false;
            // RobotContainer.stopEverything();
        }
        if (fallingEdge){
            this.detectsCoral = true;
            //RobotContainer.stopEverything();
            // RobotContainer.prepShooter();
        }
        // RobotContainer.stopEverything();
    };

    //this.detectsCoral = false;


    private AsynchronousInterrupt asynchronousInterrupt = new AsynchronousInterrupt(m_funnelBeamBreak, callback);


    public FunnelBeambreak() {
        asynchronousInterrupt.enable();

    }

    public boolean checkBreak() {
        return (m_funnelBeamBreak.get());
    }

    @Override
    public void periodic(){
        detectsCoral = m_funnelBeamBreak.get();
        SmartDashboard.putBoolean("Funnel Beambreak broken", checkBreak());

        // SmartDashboard.putBoolean("Detects coral", detectsCoral);

    }
}

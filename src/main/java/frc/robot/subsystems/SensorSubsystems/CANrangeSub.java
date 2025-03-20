package frc.robot.subsystems.SensorSubsystems;


import com.ctre.phoenix6.hardware.CANrange;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CANrangeSub extends SubsystemBase{
    private final CANrange m_IntakeCanRange = new CANrange(Constants.CANrangeConstants.deviceID);

    public CANrangeSub(){
    }

    public boolean isWithinDistanceL2L3() {
        return (m_IntakeCanRange.getDistance().getValueAsDouble() < Constants.CANrangeConstants.L2L3Range);
    }

    public boolean isWithinDistanceL4() {
        return (m_IntakeCanRange.getDistance().getValueAsDouble() < Constants.CANrangeConstants.L4Range);
    }

    public double getDistanceM(){
        return m_IntakeCanRange.getDistance().getValueAsDouble();
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Reef CANrange distance", getDistanceM());
    }
}
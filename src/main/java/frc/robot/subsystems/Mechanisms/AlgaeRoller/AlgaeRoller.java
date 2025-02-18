package frc.robot.subsystems.Mechanisms.AlgaeRoller;

import com.ctre.phoenix6.controls.NeutralOut;

import frc.robot.Constants.AlgaeRollerConstants;
import frc.robot.subsystems.SubsystemUtils.SubsystemLib;
import frc.robot.subsystems.SubsystemUtils.TalonFXFactory;

public class AlgaeRoller extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

  
        public final double velocityKp = AlgaeRollerConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;

        public TestSubsystemConfig() {
            super("AlgaeMotor", AlgaeRollerConstants.id, "rio");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(false);
            //SetPositionVoltage(rotations);
        }
    }

    public TestSubsystemConfig config;

    public AlgaeRoller(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void testMotorGoToPosition(double pos) {
        SetPositionVoltage(pos); // doesnt actually go anywhere
    }

    public void applyVoltage(double voltage)
    {
        setVoltage(voltage);
    }

    public void stopRollers(){
        motor.setControl(new NeutralOut());
    }

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }
}

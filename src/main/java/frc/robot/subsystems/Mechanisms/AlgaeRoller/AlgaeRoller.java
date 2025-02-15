package frc.robot.subsystems.Mechanisms.AlgaeRoller;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.AlgaeRollersConstants;
import frc.robot.subsystems.SubsystemUtils.SubsystemLib;
import frc.robot.subsystems.SubsystemUtils.TalonFXFactory;

public class AlgaeRoller extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

  
        public final double velocityKp = AlgaeRollersConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;

        public TestSubsystemConfig() {
            super("RollerMotor", AlgaeRollersConstants.id, "rio");  //It is on rio, but make sure that you change the id
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

    public void stopRoller(){
        setVoltage(-0.5);
    }

    public boolean statorTripped(){
        return motor.getStatorCurrent().getValueAsDouble() > AlgaeRollersConstants.statorLimit;
    }

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("rollerCurr", motor.getStatorCurrent().getValueAsDouble());
      

    }
}


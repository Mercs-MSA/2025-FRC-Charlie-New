package frc.robot.subsystems.Mechanisms.AlgaePivot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.AlgaePivotConstants;
import frc.robot.subsystems.SubsystemUtils.SubsystemLib;
import frc.robot.subsystems.SubsystemUtils.TalonFXFactory;

public class AlgaePivot extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

   

        public final double velocityKp = AlgaePivotConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        

        public TestSubsystemConfig() {
            super("AlgaePivotMotor", AlgaePivotConstants.id, "rio");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            isClockwise(false); //true if you want it to spin clockwise
     
        }
    }

    public TestSubsystemConfig config;

    public AlgaePivot(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 
        }
    }

    public void algaePivotToPosMM(double pos) {
        setMMPositionFOC(pos);
    }

    public void algaePivotPositionVoltage(double pos) {
        SetPositionVoltage(pos); 
    }

    public double getAlgaePivotMotorPosition() {
        return GetPosition();
    }

    

   

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Algae Pivot Position", getAlgaePivotMotorPosition());
    }

}


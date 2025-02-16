package frc.robot.subsystems.Mechanisms.Funnel;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.FunnelPivotConstants;
import frc.robot.Constants.elevatorMMConstants;
import frc.robot.subsystems.SubsystemUtils.SubsystemLib;
import frc.robot.subsystems.SubsystemUtils.TalonFXFactory;



public class FunnelPivot extends SubsystemLib {
    public class TestSubsystemConfig extends Config {

        /* MAKE SURE TO CHANGE THESE VALUES! THE PID IS NOT CONFIGURED */

        /* These values will later be added into a constants file that has not yet been created. 
         */

        public final int CANcoderID = 17; //find id


        public final double velocityKp = FunnelPivotConstants.kP;
        public final double velocityKs = 0;
        public final double velocityKv = 0;
        public final double rotations = 0;

        public TestSubsystemConfig() {
            super("FunnelPivot", FunnelPivotConstants.id, "rio");  //It is on rio, but make sure that you change the id
            configPIDGains(velocityKp, 0, 0);
            configForwardGains(velocityKs, velocityKv, 0, 0);
            configGearRatio(1);
            configNeutralBrakeMode(true);
            configFeedbackSensorSource(FeedbackSensorSourceValue.RemoteCANcoder);
            configFeedbackSensorID(CANcoderID);
            isClockwise(false); //true if you want it to spin clockwise
            // configStatorCurrentLimit(10, true);
            configMotionMagic(elevatorMMConstants.speed, elevatorMMConstants.acceleration, elevatorMMConstants.jerk);
            // SetPositionVoltage(rotations);
        }
    }


    public TestSubsystemConfig config;
    // public CANcoder m_CANcoder;


    public FunnelPivot(boolean attached){
        super(attached);
        if(attached){
            motor = TalonFXFactory.createConfigTalon(config.id, config.talonConfig); 

            // m_CANcoder = new CANcoder(config.CANcoderID, "rio");
            // CANcoderConfiguration cancoderConfigs = new CANcoderConfiguration();
            // //cancoderConfigs.MagnetSensor.MagnetOffset = swerveConfig.pivotCANcoderOffset;
            // cancoderConfigs.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;

            modifyMotorConfig();
          
        }
    }

    public void motorToPosMM(double pos) {
        setMMPosition(pos);
    }

    public void funnelPositionVoltage(double pos) {
        SetPositionVoltage(pos); // doesnt actually go anywhere
    }

    public double funnelGetPosition() {
        return GetPosition();
    }

    public void modifyMotorConfig() {
        config.talonConfig.Feedback.FeedbackRemoteSensorID = config.CANcoderID;

        config.talonConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
                
    }
   

    @Override
    protected Config setConfig() {
        config = new TestSubsystemConfig();
        return config;
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Funnel CANcoder pos", funnelGetPosition());
        // SmartDashboard.putNumber("Funnel CANcoder position", m_CANcoder.getPosition().getValueAsDouble());

    }
    

    

}

package frc.robot.subsystems.SensorSubsystems;


import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.RangingMode;
import edu.wpi.first.wpilibj.AsynchronousInterrupt;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LaserCANSub extends SubsystemBase{
    private final LaserCan m_IntakeLaserCan = new LaserCan(Constants.LaserCANConstants.deviceID);

 

    public LaserCANSub(){
        try {
            m_IntakeLaserCan.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16)); //Configure ROI for the proper pole placement
        }
        catch (ConfigurationFailedException e) {
            System.out.println("Configuration failed! " + e);
        }

    }




    public boolean isWithinDistanceL2L3() {
        if(m_IntakeLaserCan.getMeasurement() != null && m_IntakeLaserCan.getMeasurement().status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT && m_IntakeLaserCan.getMeasurement().distance_mm < Constants.LaserCANConstants.L2L3Range) {     
            return true;
        }
        else{
            return false;
        }

    }

    public boolean isWithinDistanceL4() {
        if(m_IntakeLaserCan.getMeasurement() != null && m_IntakeLaserCan.getMeasurement().status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT && m_IntakeLaserCan.getMeasurement().distance_mm < Constants.LaserCANConstants.L4Range){
            return true;
        }
        else{
            return (m_IntakeLaserCan.getMeasurement().distance_mm < Constants.LaserCANConstants.L4Range);
        }
    }

    public int getDistanceMM(){
        if(m_IntakeLaserCan.getMeasurement() != null && m_IntakeLaserCan.getMeasurement().status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT){
            return m_IntakeLaserCan.getMeasurement().distance_mm;
        }
        else{
            return 1000;
        }
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Reef LaserCAN distance", getDistanceMM());



    }
}
package frc.robot.subsystems.Mechanisms.L1Servo;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ServoServo extends SubsystemBase{
    private Servo m_servo1 = new Servo(5);

    public ServoServo(){
    
       //m_servo1.setBoundsMicroseconds(2000, 1800, 1500, 1200, 1000);

        //m_servo1.set(0);
    }

    public void setServo(double a){
        m_servo1.setPosition(a);
    }

    public double getservoPos(){
        return m_servo1.getPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("ServoServo Pos", m_servo1.getPosition());
    }


}

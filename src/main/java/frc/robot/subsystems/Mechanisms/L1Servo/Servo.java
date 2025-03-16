package frc.robot.subsystems.Mechanisms.L1Servo;

import java.util.function.BiConsumer;

import com.ctre.phoenix.CANifier.PWMChannel;

import edu.wpi.first.wpilibj.AsynchronousInterrupt;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Servo extends SubsystemBase{
    private static final PWM m_Servo = new PWM(2);

    public Servo() {
    }

    public void moveServo() {
        if(Constants.isWithinTol(0.5, m_Servo.getPosition(), 0.1)){
            m_Servo.setPosition(0.5);
        }

        else {
            m_Servo.setPosition(0);
        }
    }

    @Override
    public void periodic(){
       

        // SmartDashboard.putBoolean("Detects coral", detectsCoral);

    }
}
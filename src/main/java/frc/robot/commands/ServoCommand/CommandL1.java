package frc.robot.commands.ServoCommand;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Mechanisms.L1Servo.Servo;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class CommandL1 extends Command {

    public Servo m_Servo;

    public CommandL1(Servo m_Servo)  { 
        this.m_Servo = m_Servo; 
    }


    @Override
    public void initialize() {
        m_Servo.moveServo();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}

package frc.robot.commands.IntakeCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.ScoringStageVal;
import frc.robot.subsystems.Mechanisms.Intake.IntakeFlywheels;
import frc.robot.subsystems.SensorSubsystems.IntakeBeambreak;
import frc.robot.subsystems.SensorSubsystems.LaserCANSub;
import frc.robot.subsystems.Mechanisms.Elevator.Elevator;

public class CommandLaserScore extends Command {
    private final IntakeFlywheels m_intakeFlywheels;
    private final IntakeBeambreak m_breambreak;
    private final Elevator m_Elevator;
    private final LaserCANSub m_laserCAN;

    private double voltage;

    public CommandLaserScore(IntakeFlywheels m_intakeFlywheels, IntakeBeambreak m_beambreak, LaserCANSub m_laserCAN, Elevator m_Elevator, double voltage) {
        this.voltage = voltage;
        this.m_breambreak = m_beambreak;
        this.m_intakeFlywheels = m_intakeFlywheels;
        this.m_laserCAN = m_laserCAN;
        this.m_Elevator = m_Elevator;
        addRequirements(m_intakeFlywheels);
    }

    @Override 
    public void initialize() {
    }

    @Override 
    public void execute() {
        if(Constants.ScoringConstants.ScoringStage == Constants.ScoringStageVal.L4 && Constants.isWithinTol(Constants.ScoringStageVal.L4.getElevatorRotations(), m_Elevator.elev1MotorGetPosition(), 0.2) && m_laserCAN.isWithinDistanceL4()){
            m_intakeFlywheels.applyVoltage(voltage);
        }
        else if(Constants.ScoringConstants.ScoringStage == Constants.ScoringStageVal.L2 && Constants.isWithinTol(Constants.ScoringStageVal.L2.getElevatorRotations(), m_Elevator.elev1MotorGetPosition(), 0.2) && m_laserCAN.isWithinDistanceL2L3()){
            m_intakeFlywheels.applyVoltage(voltage);

        }

        else if (Constants.ScoringConstants.ScoringStage == Constants.ScoringStageVal.L3 && Constants.isWithinTol(Constants.ScoringStageVal.L3.getElevatorRotations(), m_Elevator.elev1MotorGetPosition(), 0.2) && m_laserCAN.isWithinDistanceL2L3()){
            m_intakeFlywheels.applyVoltage(voltage);

        }
    }

    @Override 
    public void end(boolean interrupted) {
        // This is where you put stuff that happens when the command ends
        m_intakeFlywheels.stopIntake();
    }

    @Override 
    public boolean isFinished() {
        // This is where you put a statment that will determine wether a boolean is true or false
        // This is checked after an execute loop and if the return comes out true the execute loop will stop and end will happen
        return !m_breambreak.checkBreak(); // Will check beambreak until it returns true (meaning it got unbroke)
    }
}

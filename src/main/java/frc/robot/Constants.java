package frc.robot;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N3;
import frc.robot.LimelightHelpers.LimelightResults;
import frc.robot.LimelightHelpers.PoseEstimate;
import frc.robot.commands.DriveToPosCommands.CommandToPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {

    public enum ScoringStageVal {
        INTAKEREADY(0, true, false, true),
        INTAKING(0, false, true, true),

        L1(0.9, true, false, false),
        L2(1.73, true, false, false),
        L3(3.18, true, false, false),
        L4(5.36, true, false, false),
        CLIMBING(0, false, true, false);


        private double elevatorRotations;

        private boolean canElev;
        private boolean canClimb;
        private boolean canPivot;


        private ScoringStageVal(double elevRotation, boolean moveElev, boolean moveClimb, boolean movePivot)
        {
            this.elevatorRotations = elevRotation;
            this.canElev = moveElev;
            this.canClimb = moveClimb;
            this.canPivot = movePivot;
        }

        public double getElevatorRotations()
        {
            return this.elevatorRotations;
        }

        public boolean canElev()
        {
            return this.canElev;
        }

        public boolean canClimb()
        {
            return this.canClimb;
        }

        public boolean canPivot()
        {
            return this.canPivot;
        }
    }

    public static final class Elevator1Constants{
        public static final int id = 14;

        public static final boolean attached = true;

        public static final double kP = 15; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
      

        public static final double tol = 0.4;
    }

    public static final class Elevator2Constants{
        public static final int id = 19;




    }

 

    public static final class ClimberConstants{
        public static final int id = 16;

        public static final boolean attached = true;

        public static final double kP = 1.9; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double deployVoltage = 3.5;

        public static final double recoilVoltage = -3.5;


        public static final double positionUp = -60;

        public static final double positionStart = 0;

        public static final double positionDown = 165;

        public static final double climberTol = 1;

    }


    public static final class elevatorMMConstants{
        public static final double acceleration = 100;
        public static final double speed = 150;
        public static final double jerk = 0;

    }



 

    public static final class FunnelPivotConstants {
        public static final int id = 15;
        public static final boolean attached = true;


        public static final double kP = 100; 
        public static final double kS = 0; 
        public static final double kV = 0; 

        public static final double posUp = -0.436; //needs to be tested
        public static final double posDown = 0; 
        
    }

    public static final class AlgaePivotConstants {
        public static final int id = 11;
        public static final boolean attached = true;


        public static final double kP = 1; 
        public static final double kS = 0; 
        public static final double kV = 0; 

        public static final double posBottomDescore = 30; //30 is old position with spinning wheel on pivot
        public static final double posTopUp = 50;
        public static final double posDown = 0; //needs to be tested
 //needs to be tested
        
    }
    
    public static final class IntakeFlywheelsConstants{
        public static final int id = 13;

        public static final boolean attached = false;

        public static final double kP = 5; 
        public static final double kS = 0; 
        public static final double kV = 0; 


        // public static final double voltageOut = 2;
        // public static final double position = 0;
    }

    public static final class AlgaeRollerConstants{
        public static final int id = 22;

        public static final boolean attached = true;

        public static final double kP = 12; 
        public static final double kS = 0; 
        public static final double kV = 0; 


    }


    public static final class IntakeBeambreakConstants {
        public static final boolean breakAttached = false;
        public static final String beamBreakName = "intake_beambreak";
        public static final int beamBreakChannel = 3; //good

    }

    public static final class LaserCANConstants {
        public static final int deviceID = 30;
        
        public static double L2L3Range = 1; // change

        public static double L4Range = 1; //change



    }

    public static final class CANrangeConstants {
        public static final int deviceID = 4;
        public static double L2L3Range = 0.070; // change
        public static double L4Range = 0.060; //change
    }

    public static final class FunnelBeambreakConstants {
        public static final boolean breakAttached = false;
        public static final String beamBreakName = "funnel_beambreak";
        public static final int beamBreakChannel = 4; //good

    }

    public static final class VisionConstants {
        public static final String limelightLeftName = "limelight-left";
        public static final String limelightRightName = "limelight-right";
        public static final String limelightBackName = "limelight-back";
        public static final Vector<N3> visionStdDevs = VecBuilder.fill(.5,.5,9999999);
        public static PoseEstimate bestLimelightPose;
    }

    public static final class FieldConstants {
        public static final double fieldLengthMeters = 17.54;
        public static final double fieldWidthMeters = 8.02;
        
    }

    public static final class DriveToPoseConstants {
        public static final double angularDegreesTolerance = 0.3;
        public static final double linearMetersTolerance = 0.01;
        public static final double linearMetersMaxVel = 3.5;
        public static final double linearMetersMaxAccel = 20.0;
        public static final HashMap<String, CommandToPos.Destination> positions = new HashMap<String, CommandToPos.Destination>() {{
              
            put("reefA", new CommandToPos.Destination("reefA", new Pose2d(3.178, 4.2, new Rotation2d(0))));
            put("reefB", new CommandToPos.Destination("reefB", new Pose2d(3.178, 3.852, new Rotation2d(0))));//used to be y = 3.892, just changed
            put("reefABDescore", new CommandToPos.Destination("reefABDescore", new Pose2d(3.12, 4.386, new Rotation2d(0))));//

            put("reefC", new CommandToPos.Destination("reefC", new Pose2d(3.696, 2.979, new Rotation2d(1.047))));
            put("reefD", new CommandToPos.Destination("reefD", new Pose2d(3.961, 2.801, new Rotation2d(1.047))));
            put("reefCDDescore", new CommandToPos.Destination("reefCDDescore", new Pose2d(3.487, 2.988, new Rotation2d(1.047))));


            put("reefE", new CommandToPos.Destination("reefE", new Pose2d(5.00, 2.81, new Rotation2d(2.0944))));
            put("reefF", new CommandToPos.Destination("reefF", new Pose2d(5.259, 2.9880, new Rotation2d(2.0944))));
            put("reefEFDescore", new CommandToPos.Destination("reefEFDescore", new Pose2d(4.867, 2.678, new Rotation2d(2.0944))));


            put("reefG", new CommandToPos.Destination("reefG", new Pose2d(5.806, 3.858, new Rotation2d(3.1459))));
            put("reefH", new CommandToPos.Destination("reefH", new Pose2d(5.75, 4.160, new Rotation2d(3.1459))));
            put("reefGHDescore", new CommandToPos.Destination("reefGHDescore", new Pose2d(5.859, 3.686, new Rotation2d(3.1459))));

            put("reefI", new CommandToPos.Destination("reefI", new Pose2d(5.308, 5.06, new Rotation2d(-2.094))));
            put("reefJ", new CommandToPos.Destination("reefJ", new Pose2d(5.016, 5.283, new Rotation2d(-2.094))));
            put("reefIJDescore", new CommandToPos.Destination("reefIJDescore", new Pose2d(5.456, 5.034, new Rotation2d(-2.094))));

            put("reefK", new CommandToPos.Destination("reefK", new Pose2d(3.9911, 5.2315, new Rotation2d(-1.047))));
            put("reefL", new CommandToPos.Destination("reefL", new Pose2d(3.7114, 5.0915, new Rotation2d(-1.047))));
            put("reefKLDescore", new CommandToPos.Destination("reefKLDescore", new Pose2d(4.107, 5.391, new Rotation2d(-1.047))));
        
            put("Source", new CommandToPos.Destination("Source", new Pose2d(1.067, 7.1, new Rotation2d(-0.939))));
            put("L1Left", new CommandToPos.Destination("L1Left", new Pose2d(3.05, 4.26, new Rotation2d(0.252))));
            put("L1Right", new CommandToPos.Destination("L1Right", new Pose2d(3.48, 2.94, new Rotation2d(1.222))));

            put("Test", new CommandToPos.Destination("Test", new Pose2d(2.16, 3.9, new Rotation2d(0))));
        }};


        public static final HashMap<String, List<String>> tagDestinationMap = new HashMap<String, List<String>>() {{
            put("18", List.of("reefA", "reefB", "reefABDescore")); // blue
            put("7", List.of("reefA", "reefB", "reefABDescore")); // red
            put("17", List.of("reefC", "reefD", "reefCDDescore")); // blue
            put("8", List.of("reefC", "reefD", "reefCDDescore")); // red
            put("19", List.of("reefK", "reefL", "reefKLDescore")); // blue
            put("6", List.of("reefK", "reefL", "reefKLDescore")); // red
            put("21", List.of("reefH", "reefG", "reefGHDescore")); // blue
            put("10", List.of("reefH", "reefG", "reefGHDescore")); // red
            put("20", List.of("reefI", "reefJ", "reefIJDescore")); // blue
            put("11", List.of("reefI", "reefJ", "reefIJDescore")); // red
            put("22", List.of("reefF", "reefE", "reefEFDescore")); // blue
            put("9", List.of("reefF", "reefE", "reefEFDescore")); // red
        }};

        // public static final TreeMap<String, Integer> leftTagNames = new TreeMap<>() {{
        //     put("reefC", 17);
        //     put("reefA", 18);
        //     put("reefK", 19);
        //     put("reefI", 20);
        //     put("reefG", 21);
        //     put("reefE", 22);
        // }};
        // public static final TreeMap<String, Integer> rightTagNames = new TreeMap<>() {{ 
        //     put("reefD", 17);
        //     put("reefB", 18);
        //     put("reefL", 19);
        //     put("reefJ", 20);
        //     put("reefH", 21);
        //     put("reefF", 22);
        // }};
        
    }

    public static boolean isWithinTol(double targetPose, double currentPose, double tolerance) {
        return (Math.abs(targetPose - currentPose) <= tolerance);
    }

    public static double slowDownWithElevator(double pos) {
        return pos * 0.8;
    }

    public static double supplyOuttakeSpeed(){

        if(ScoringConstants.ScoringStage == Constants.ScoringStageVal.L1){
                return 2;
        }

        else if (ScoringConstants.ScoringStage == Constants.ScoringStageVal.L2){
            return 8;
        }

        else if (ScoringConstants.ScoringStage == Constants.ScoringStageVal.L3){
            return 8;
        }

        else if (ScoringConstants.ScoringStage == Constants.ScoringStageVal.L4){
            return 8;
        }
        else{
            return 8;
        }
    }

    public class ScoringConstants
    {
        public static ScoringStageVal ScoringStage = ScoringStageVal.INTAKEREADY;
    }

    public class DriveToPosRuntime {
        public static String target = null;
        public static List<String> autoTargets = new ArrayList<String>(3) {{
            add("");
            add("");
            add("");
        }};
    }
    
}

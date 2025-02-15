package frc.robot;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N3;
import frc.robot.commands.DriveToPosCommands.CommandToPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Constants {

    public enum ScoringStageVal {
        INTAKEREADY(0, true, false, true),
        INTAKING(0, false, false, false),
        L1(0.005, true, false, false),
        L2(1.90, true, false, false),
        L3(3.22, true, false, false),
        L4(5.28, true, false, false),
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

        public static final double kP = 30; 
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



        public static final double voltageOut = 0;
        public static final double positionUp = 333; //-240

        public static final double positionDown = 0;

        public static final double climberTol = 1;

    }


    public static final class AlgaePivotConstants{
        public static final int id = 11;

        public static final boolean attached = true;

        public static final double kP = 3; 
        public static final double kS = 0; 
        public static final double kV = 0; 



        public static final double voltageOut = 0;
        public static final double positionDescoreLow = 37; 

        public static final double positionDescoreHigh = 45;

        public static final double positionProcessor = 3;

        public static final double restPos = 0.5;


        public static final double algaePivotTol = 1;

    }


    public static final class elevatorMMConstants{
        public static final double acceleration = 100;
        public static final double speed = 150;
        public static final double jerk = 0;

    }



    public static final class elevatorBeambreakConstants {
        public static boolean breakAttached = false;
        public static final String beamBreakName = "elevatorBeambreak";
        public static final int beamBreakChannel = 2;

    }

    public static final class FunnelPivotConstants {
        public static final int id = 15;
        public static final boolean attached = true;


        public static final double kP = 100; 
        public static final double kS = 0; 
        public static final double kV = 0; 

        public static final double posUp = 0.42; //needs to be tested
        public static final double posDown = 0; //needs to be tested
        
    }
    
    public static final class IntakeFlywheelsConstants{
        public static final int id = 13;

        public static final boolean attached = true;

        public static final double kP = 5; 
        public static final double kS = 0; 
        public static final double kV = 0; 


        // public static final double voltageOut = 2;
        // public static final double position = 0;
    }


    public static final class AlgaeRollersConstants{
        public static final int id = 31;

        public static final boolean attached = true;

        public static final double kP = 1; 
        public static final double kS = 0; 
        public static final double kV = 0; 

        public static final double statorLimit = 20;


    }


    public static final class IntakeBeambreakConstants {
        public static final boolean breakAttached = false;
        public static final String beamBreakName = "intake_beambreak";
        public static final int beamBreakChannel = 3; //good

    }

    public static final class VisionConstants {
        public static final String limelightLeftName = "limelight-left";
        public static final String limelightRightName = "limelight-right";
        public static final String limelightBackName = "limelight-back";
        public static final Vector<N3> visionStdDevs = VecBuilder.fill(.7,.7,9999999);
    }

    public static final class FieldConstants {
        public static final double fieldLengthMeters = 16.54;
        public static final double fieldWidthMeters = 8.02;
        
    }

    public static final class DriveToPoseConstants {
        public static final double angularDegreesTolerance = 0.3;
        public static final double linearMetersTolerance = 0.05;
        public static final double linearMetersMaxVel = 3.0;
        public static final double linearMetersMaxAccel = 20.0;
        public static final HashMap<String, CommandToPos.Destination> positions = new HashMap<String, CommandToPos.Destination>() {{
            put("reefA", new CommandToPos.Destination("reefA", new Pose2d(3.175, 4.181, new Rotation2d(0))));
            put("reefB", new CommandToPos.Destination("reefB", new Pose2d(3.175, 3.882, new Rotation2d(0))));//
            put("reefC", new CommandToPos.Destination("reefC", new Pose2d(3.679, 2.958, new Rotation2d(1.047))));
            put("reefD", new CommandToPos.Destination("reefD", new Pose2d(3.961, 2.801, new Rotation2d(1.047))));
            put("reefE", new CommandToPos.Destination("reefE", new Pose2d(5.00, 2.789, new Rotation2d(2.0944))));
            put("reefF", new CommandToPos.Destination("reefF", new Pose2d(5.30, 2.970, new Rotation2d(2.0944))));
            put("reefG", new CommandToPos.Destination("reefG", new Pose2d(5.806, 3.858, new Rotation2d(3.1459))));
            put("reefH", new CommandToPos.Destination("reefH", new Pose2d(5.806, 4.190, new Rotation2d(3.1459))));
            put("reefI", new CommandToPos.Destination("reefI", new Pose2d(5.350, 5.063, new Rotation2d(-2.094))));
            put("reefJ", new CommandToPos.Destination("reefJ", new Pose2d(5.012, 5.263, new Rotation2d(-2.094))));
            put("reefK", new CommandToPos.Destination("reefK", new Pose2d(3.9911, 5.2315, new Rotation2d(-1.047))));
            put("reefL", new CommandToPos.Destination("reefL", new Pose2d(3.7114, 5.0915, new Rotation2d(-1.047))));
            put("Source", new CommandToPos.Destination("Source", new Pose2d(1.00, 7.2, new Rotation2d(-1.13))));
            put("Test", new CommandToPos.Destination("Test", new Pose2d(1.956, 5.4, new Rotation2d(0))));
        }};

        public static final HashMap<String, List<String>> tagDestinationMap = new HashMap<String, List<String>>() {{
            put("18", List.of("reefA", "reefB"));
            put("17", List.of("reefC", "reefD"));
            put("19", List.of("reefK", "reefL"));
            put("21", List.of("reefH", "reefG"));
            put("20", List.of("reefI", "reefJ"));
            put("22", List.of("reefF", "reefE"));
        }};

        public static final TreeMap<String, Integer> leftTagNames = new TreeMap<>() {{
            put("reefC", 17);
            put("reefA", 18);
            put("reefK", 19);
            put("reefI", 20);
            put("reefG", 21);
            put("reefE", 22);
        }};
        public static final TreeMap<String, Integer> rightTagNames = new TreeMap<>() {{ 
            put("reefD", 17);
            put("reefB", 18);
            put("reefL", 19);
            put("reefJ", 20);
            put("reefH", 21);
            put("reefF", 22);
        }};
        
    }

    public static boolean isWithinTol(double targetPose, double currentPose, double tolerance) {
        return (Math.abs(targetPose - currentPose) <= tolerance);
    }

    public static double slowDownWithElevator(double pos) {
        return pos * 0.8;
    }

    public class ScoringConstants
    {
        public static ScoringStageVal ScoringStage = ScoringStageVal.INTAKEREADY;
    }

    public class DriveToPosRuntime {
        public static String target = null;
        public static List<String> autoTargets = new ArrayList<String>(2) {{
            add("");
            add("");
        }};
    }
    
}

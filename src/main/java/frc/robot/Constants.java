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
        L2(1.76, true, false, false),
        L3(3.21, true, false, false),
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

    public static Pose2d generateReefPose(String pole){
         final double intakeOffset = 0.0254;
         final double centerTagToPole = (0.35 / 2);
         List<Double> faceACoords = List.of(3.162, 4.022, 0.0);
         List<Double> faceBCoords = List.of(3.827, 2.884, 1.0472);
         List<Double> faceCCoords = List.of(5.145, 2.880, 2.0944);
         List<Double> faceDCoords = List.of(5.801, 4.022, 3.1459);
         List<Double> faceECoords = List.of(5.145, 5.169, -2.0944);
         List<Double> faceFCoords = List.of(3.827, 5.169, -1.0472);

         if(pole == "A") {
            List<Double> CenterA = List.of(faceACoords.get(0), faceACoords.get(1) + intakeOffset);
            List<Double> ACoords = List.of(CenterA.get(0), CenterA.get(1)+ centerTagToPole);
            return new Pose2d(ACoords.get(0), ACoords.get(1), new Rotation2d(faceACoords.get(2)));
         }

         else if(pole == "B"){
            List<Double> CenterB = List.of(faceACoords.get(0), faceACoords.get(1) + intakeOffset);
            List<Double> BCoords = List.of(CenterB.get(0), CenterB.get(1) - centerTagToPole);
            return new Pose2d(BCoords.get(0), BCoords.get(1), new Rotation2d(faceACoords.get(2)));
         }

         else if(pole == "C"){
            List<Double> CenterC = List.of(faceBCoords.get(0) - (intakeOffset * Math.sqrt(3)), faceBCoords.get(1) + (intakeOffset / 2));
            List<Double> CCoords = List.of(CenterC.get(0) - ((centerTagToPole / 2) * Math.sqrt(3)), CenterC.get(1) + (centerTagToPole/ 2));
            return new Pose2d(CCoords.get(0), CCoords.get(1), new Rotation2d(faceBCoords.get(2)));
            
         }

         else if(pole == "D"){
            List<Double> CenterD = List.of(faceBCoords.get(0) - (intakeOffset * Math.sqrt(3)), faceBCoords.get(1) + (intakeOffset / 2));
            List<Double> DCoords = List.of(CenterD.get(0) + ((centerTagToPole / 2) * Math.sqrt(3)), CenterD.get(1) - (centerTagToPole/ 2));
            return new Pose2d(DCoords.get(0), DCoords.get(1), new Rotation2d(faceBCoords.get(2)));


         }

         else if(pole == "E"){
            List<Double> CenterE = List.of(faceCCoords.get(0) - (intakeOffset * Math.sqrt(3)), faceCCoords.get(1) - (intakeOffset / 2));
            List<Double> ECoords = List.of(CenterE.get(0) - ((centerTagToPole / 2) * Math.sqrt(3)), CenterE.get(1) - (centerTagToPole/ 2));
            return new Pose2d(ECoords.get(0), ECoords.get(1), new Rotation2d(faceCCoords.get(2)));

         }

         else if(pole == "F"){
            List<Double> CenterF = List.of(faceCCoords.get(0) - (intakeOffset * Math.sqrt(3)), faceCCoords.get(1) - (intakeOffset / 2));
            List<Double> FCoords = List.of(CenterF.get(0) + ((centerTagToPole / 2) * Math.sqrt(3)), CenterF.get(1) + (centerTagToPole/ 2));
            return new Pose2d(FCoords.get(0), FCoords.get(1), new Rotation2d(faceCCoords.get(2)));

         }

         else if(pole == "G"){
            List<Double> CenterG = List.of(faceDCoords.get(0), faceDCoords.get(1) - intakeOffset);
            List<Double> GCoords = List.of(CenterG.get(0), CenterG.get(1) - centerTagToPole);
            return new Pose2d(GCoords.get(0), GCoords.get(1), new Rotation2d(faceDCoords.get(2)));
         }

         else if(pole == "H"){
            List<Double> CenterH = List.of(faceDCoords.get(0), faceDCoords.get(1) - intakeOffset);
            List<Double> HCoords = List.of(CenterH.get(0), CenterH.get(1) + centerTagToPole);
            return new Pose2d(HCoords.get(0), HCoords.get(1), new Rotation2d(faceDCoords.get(2)));
         }

         else if(pole == "I"){
            List<Double> CenterI = List.of(faceECoords.get(0) + (intakeOffset * Math.sqrt(3)), faceECoords.get(1) - (intakeOffset / 2));
            List<Double> ICoords = List.of(CenterI.get(0) + ((centerTagToPole / 2) * Math.sqrt(3)), CenterI.get(1) - (centerTagToPole/ 2));
            return new Pose2d(ICoords.get(0), ICoords.get(1), new Rotation2d(faceECoords.get(2)));
         }

         else if(pole == "J"){
            List<Double> CenterJ = List.of(faceECoords.get(0) + (intakeOffset * Math.sqrt(3)), faceECoords.get(1) - (intakeOffset / 2));
            List<Double> JCoords = List.of(CenterJ.get(0) - ((centerTagToPole / 2) * Math.sqrt(3)), CenterJ.get(1) + (centerTagToPole/ 2));
            return new Pose2d(JCoords.get(0), JCoords.get(1), new Rotation2d(faceECoords.get(2)));
         }

         else if(pole == "K"){
            List<Double> CenterK = List.of(faceFCoords.get(0) + (intakeOffset * Math.sqrt(3)), faceFCoords.get(1) + (intakeOffset / 2));
            List<Double> KCoords = List.of(CenterK.get(0) + ((centerTagToPole / 2) * Math.sqrt(3)), CenterK.get(1) + (centerTagToPole/ 2));
            return new Pose2d(KCoords.get(0), KCoords.get(1), new Rotation2d(faceFCoords.get(2)));
         }

         else if(pole == "L"){
            List<Double> CenterL = List.of(faceFCoords.get(0) + (intakeOffset * Math.sqrt(3)), faceFCoords.get(1) + (intakeOffset / 2));
            List<Double> LCoords = List.of(CenterL.get(0) - ((centerTagToPole / 2) * Math.sqrt(3)), CenterL.get(1) - (centerTagToPole/ 2));
            return new Pose2d(LCoords.get(0), LCoords.get(1), new Rotation2d(faceFCoords.get(2)));
         }

         else{
            return new Pose2d(0, 0, new Rotation2d(0));

         }
    }

    public static final class DriveToPoseConstants {
        public static final double angularDegreesTolerance = 0.3;
        public static final double linearMetersTolerance = 0.01;
        public static final double linearMetersMaxVel = 5.5; 
        public static final double linearMetersMaxAccel = 20.0;




        public static final HashMap<String, CommandToPos.Destination> positions = new HashMap<String, CommandToPos.Destination>() {{
              
           
            put("reefA", new CommandToPos.Destination("reefA", generateReefPose("A")));
            put("reefB", new CommandToPos.Destination("reefB", generateReefPose("B")));
            put("reefABDescore", new CommandToPos.Destination("reefABDescore", new Pose2d(3.12, 4.386, new Rotation2d(0))));//

           
            put("reefC", new CommandToPos.Destination("reefC", generateReefPose("C")));
            put("reefD", new CommandToPos.Destination("reefD", generateReefPose("D")));
            put("reefCDDescore", new CommandToPos.Destination("reefCDDescore", new Pose2d(3.487, 2.988, new Rotation2d(1.047))));


            
            put("reefE", new CommandToPos.Destination("reefE", generateReefPose("E")));
            put("reefF", new CommandToPos.Destination("reefF", generateReefPose("F")));
            put("reefEFDescore", new CommandToPos.Destination("reefEFDescore", new Pose2d(4.867, 2.678, new Rotation2d(2.0944))));


            
            put("reefG", new CommandToPos.Destination("reefG", generateReefPose("G")));
            put("reefH", new CommandToPos.Destination("reefH", generateReefPose("H")));
            put("reefGHDescore", new CommandToPos.Destination("reefGHDescore", new Pose2d(5.859, 3.686, new Rotation2d(3.1459))));

           
            put("reefI", new CommandToPos.Destination("reefI", generateReefPose("I")));
            put("reefJ", new CommandToPos.Destination("reefJ", generateReefPose("J")));
            put("reefIJDescore", new CommandToPos.Destination("reefIJDescore", new Pose2d(5.456, 5.034, new Rotation2d(-2.094))));

            
            put("reefK", new CommandToPos.Destination("reefK", generateReefPose("K")));
            put("reefL", new CommandToPos.Destination("reefL", generateReefPose("L")));
            put("reefKLDescore", new CommandToPos.Destination("reefKLDescore", new Pose2d(4.107, 5.391, new Rotation2d(-1.047))));
        
            put("SourceLeft", new CommandToPos.Destination("SourceLeft", new Pose2d(1.103, 7.1, new Rotation2d(-0.96))));
            put("SourceRight", new CommandToPos.Destination("SourceRight", new Pose2d(1.0103, 0.926, new Rotation2d(0.96))));

            put("L1Left", new CommandToPos.Destination("L1Left", new Pose2d(3.05, 4.26, new Rotation2d(0.252))));
            put("L1Right", new CommandToPos.Destination("L1Right", new Pose2d(3.48, 2.94, new Rotation2d(1.222))));

            put("Test", new CommandToPos.Destination("Test", new Pose2d(2.16, 3.9, new Rotation2d(0))));
        }};


        public static final HashMap<String, CommandToPos.Destination> oldPositions = new HashMap<String, CommandToPos.Destination>() {{ //If you want to revert to the old drive to pose, switch "positions" to "oldPositions" in commandToPos File
              
            put("reefA", new CommandToPos.Destination("reefA", new Pose2d(3.178, 4.2, new Rotation2d(0))));
            put("reefB", new CommandToPos.Destination("reefB", new Pose2d(3.178, 3.852, new Rotation2d(0))));
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
        
            put("SourceLeft", new CommandToPos.Destination("SourceLeft", new Pose2d(1.103, 7.1, new Rotation2d(-0.96))));
            put("SourceRight", new CommandToPos.Destination("SourceRight", new Pose2d(1.0103, 0.926, new Rotation2d(0.96))));

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
            put("20", List.of("reefJ", "reefI", "reefIJDescore")); // blue
            put("11", List.of("reefJ", "reefI", "reefIJDescore")); // red
            put("22", List.of("reefF", "reefE", "reefEFDescore")); // blue
            put("9", List.of("reefF", "reefE", "reefEFDescore")); // red
        }};

        public static final double rumbleTolerance = 0.05;

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
            return 6;
        }

        else if (ScoringConstants.ScoringStage == Constants.ScoringStageVal.L3){
            return 6;
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
        public static Pose2d dest = null;
        public static List<String> autoTargets = new ArrayList<String>(3) {{
            add("");
            add("");
            add("");
        }};
    }
    
}

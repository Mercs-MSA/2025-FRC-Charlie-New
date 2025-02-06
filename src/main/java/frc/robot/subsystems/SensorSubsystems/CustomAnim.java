package frc.robot.subsystems.SensorSubsystems;

import com.ctre.phoenix.led.Animation;

import frc.robot.subsystems.SensorSubsystems.CANdle_LED.AnimationTypes;

public class CustomAnim {

    private CustomAnimation anim;
    private int duration;
    private double interval;

    public enum CustomAnimation {
        MercsPattern
        
    }

    public CustomAnim(CustomAnimation type, int duration, double interval) {
        anim = CustomAnimation.MercsPattern;
        this.duration = duration;
        this.interval = interval;
    }

    public CustomAnimation getAnim() {
        return this.anim;
    }

    public int getDuration() {
        return this.duration;
    }

    public double getInterval()
    {
        return this.interval;
    }
    
}

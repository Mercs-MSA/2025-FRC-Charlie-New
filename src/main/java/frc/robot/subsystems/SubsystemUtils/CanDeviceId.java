package frc.robot.subsystems.SubsystemUtils;

// Based on 254-2023 Class
// https://github.com/Team254/FRC-2023-Public/blob/main/src/main/java/com/team254/lib/drivers/CanDeviceId.java
//This code is from 254 cheesy poofs, referenced from 3847 spectrumlib -Brady


public class CanDeviceId {
    private final int mDeviceNumber;
    private final String mBus;

    public CanDeviceId(int deviceNumber, String bus) {
        mDeviceNumber = deviceNumber;
        mBus = bus;
    }

    // Use the default bus name (empty string).
    public CanDeviceId(int deviceNumber) {
        this(deviceNumber, "canivore");
    }

    public int getDeviceNumber() {
        return mDeviceNumber;
    }

    public String getBus() {
        return mBus;
    }

    public boolean equals(CanDeviceId other) {
        return other.mDeviceNumber == mDeviceNumber && other.mBus == mBus;
    }
}

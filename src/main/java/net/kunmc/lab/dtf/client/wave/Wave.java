package net.kunmc.lab.dtf.client.wave;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;

public class Wave {
    private final Vec3d pos;
    private final float range;
    private final float speed;
    private long startTime;
    private boolean isOn;

    public Wave(Vec3d pos, float range, float speed) {
        this.pos = pos;
        this.range = range;
        this.speed = speed;
    }

    public Vec3d getPos() {
        return pos;
    }

    public float getRange() {
        return range;
    }

    public float getSpeed() {
        return speed;
    }

    public void startWave() {
        startTime = System.currentTimeMillis();
        isOn = true;
    }

    public float getRadius() {
        if (startTime == 0 || !isOn)
            return 0;

        final float r1 = Minecraft.getInstance().gameRenderer.getFarPlaneDistance();
        final float t1 = 5000;
        final float b = 200;
        final float n = 1f / ((t1 + b) * (t1 + b) - b * b);
        final float a = -r1 * b * b * n;
        final float c = r1 * n;
        final float t = (float) (System.currentTimeMillis() - startTime);

        float r = a + (t + b) * (t + b) * c;

        if (r > range) {
            isOn = false;
            return 0;
        }

        return r;
    }

    public boolean isOn() {
        return isOn;
    }

    @Override
    public String toString() {
        return "Wave{" +
                "pos=" + pos +
                ", range=" + range +
                ", speed=" + speed +
                ", startTime=" + startTime +
                ", isOn=" + isOn +
                '}';
    }
}

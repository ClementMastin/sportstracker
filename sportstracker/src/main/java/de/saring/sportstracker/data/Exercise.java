package de.saring.sportstracker.data;

import de.saring.util.ResourceReader;
import de.saring.util.data.IdDateObject;

/**
 * This class contains all informations of a single exercise (or workout).
 * 
 * @author  Stefan Saring
 * @version 1.0
 */
public final class Exercise extends IdDateObject {
    
    /** The related SportType object. */
    private SportType sportType;
    
    /** The related SportSubType object. */
    private SportSubType sportSubType;
    
    /** Duration of exercise in seconds. */
    private int duration;
    
    /** Intensity of exercise. */
    private IntensityType intensity;
    
    /** Distance of exercise in kilometers. */
    private float distance;
    
    /** Average speed of exercise in kilometers per hour. */
    private float avgSpeed;
    
    /** Average heartrate of exercise in beats per minute (optional). */
    private int avgHeartRate;
    
    /** Ascent (height meters) of exercise in meters (optional). */
    private int ascent;
    
    /** Amount of calories consumed (optional). */
    private int calories;
    
    /** Name of heart rate monitor file (optional). */
    private String hrmFile;
    
    /** The equipment used in this exercise (optional). */
    private Equipment equipment;
    
    /** Some exercise comments (optional). */
    private String comment;
    
    /** This is the list of possible file types of an exercise. */
    public enum IntensityType { 
        MINIMUM(0), LOW(1), NORMAL(2), HIGH(3), MAXIMUM(4), INTERVALS(5);

        /** Value of the intensity type (needed for sorting). */
        private final int value;
        
        /** Static resource reader is needed for string creation. */
        private static ResourceReader resReader;

        public static void setResReader (ResourceReader resReader) {
            IntensityType.resReader = resReader;
        }
        
        /** Standard c'tor. */
        IntensityType (int value) {
            this.value = value;
        }
        
        public int getValue () {
			return value;
		}
        
        /** 
         * Returns the translated name (to be displayed) for this intensity. 
         * @return name of this intensity
         */
        @Override public String toString () {
            switch (this) {
                case MINIMUM: 
                    return IntensityType.resReader.getString ("st.intensity.minimum");
                case LOW: 
                    return IntensityType.resReader.getString ("st.intensity.low");
                case NORMAL: 
                    return IntensityType.resReader.getString ("st.intensity.normal");
                case HIGH: 
                    return IntensityType.resReader.getString ("st.intensity.high");
                case MAXIMUM: 
                    return IntensityType.resReader.getString ("st.intensity.maximum");
                case INTERVALS: 
                    return IntensityType.resReader.getString ("st.intensity.intervals");
            }
            return "???";
        }

        /** 
         * Returns the string representation of this enum value created by the
         * toString()) method of the superclass.
         * @return String representation of the value
         */
        public String toStringEnum () {
            return super.toString ();
        }
    }

    
    /**
     * Standard c'tor.
     * @param id the ID of the object
     */
    public Exercise (int id) {
        super (id);
    }
    
    /***** BEGIN: Generated Getters and Setters *****/
    
    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }
    
    public SportSubType getSportSubType() {
        return sportSubType;
    }

    public void setSportSubType(SportSubType sportSubType) {
        this.sportSubType = sportSubType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public IntensityType getIntensity() {
        return intensity;
    }

    public void setIntensity(IntensityType intensity) {
        this.intensity = intensity;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(float avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public int getAvgHeartRate() {
        return avgHeartRate;
    }

    public void setAvgHeartRate(int avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }

    public int getAscent() {
        return ascent;
    }

    public void setAscent(int ascent) {
        this.ascent = ascent;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getHrmFile() {
        return hrmFile;
    }

    public void setHrmFile(String hrmFile) {
        this.hrmFile = hrmFile;
    }

    public Equipment getEquipment () {
        return equipment;
    }

    public void setEquipment (Equipment equipment) {
        this.equipment = equipment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /***** END: Generated Getters and Setters *****/
    
    /** 
     * Returns a string representation of this object. 
     * @return string with object content
     */
    @Override
    public String toString () {
        
        StringBuilder sBuilder = new StringBuilder ();
        sBuilder.append (this.getClass ().getName () + ":\n");
        sBuilder.append (" [id=" + this.getId () + "\n");
        sBuilder.append ("  date=" + this.getDate () + "\n");
        sBuilder.append ("  sportType=" + this.sportType + "\n");
        sBuilder.append ("  sportSubType=" + this.sportSubType + "\n");
        sBuilder.append ("  duration=" + this.duration + "\n");
        sBuilder.append ("  intensity=" + this.intensity + "\n");
        sBuilder.append ("  distance=" + this.distance + "\n");
        sBuilder.append ("  avgSpeed=" + this.avgSpeed + "\n");
        sBuilder.append ("  avgHeartRate=" + this.avgHeartRate + "\n");
        sBuilder.append ("  ascent=" + this.ascent + "\n");
        sBuilder.append ("  calories=" + this.calories + "\n");
        sBuilder.append ("  hrmFile=" + this.hrmFile + "\n");
        sBuilder.append ("  equipment=" + this.equipment + "\n");
        sBuilder.append ("  comment=" + this.comment + "]\n");
        return sBuilder.toString ();
    }
}
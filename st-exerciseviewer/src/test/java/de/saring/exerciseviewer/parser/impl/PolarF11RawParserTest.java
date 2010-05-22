package de.saring.exerciseviewer.parser.impl;

import de.saring.exerciseviewer.parser.*;
import de.saring.exerciseviewer.core.PVException;
import de.saring.exerciseviewer.data.PVExercise;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class contains all unit tests for the PolarF6RawParser class using a F11
 * RAW file.
 * 
 * Based on the PolarF6RawParserTest.
 *
 * @author Roland Hostettler
 */
public class PolarF11RawParserTest {
    
    /** Instance to be tested. */
    private AbstractExerciseParser parser;
    
    /**
     * This method initializes the environment for testing.
     */
    @Before
    public void setUp () throws Exception {
        parser = new PolarF6RawParser ();
    }
    
    /**
     * This method must fail on parsing an exerise file which doesn't exists.
     */
    @Test
    public void testParseExerciseMissingFile () 
    {
        try {
            parser.parseExercise ("missing-file.frd");
            fail ("Parse of the missing file must fail ...");
        } 
        catch (PVException e) {}
    }
    
    /**
     * This method tests the parser with a Polar F11 raw exercise file.
     */
    @Test
    public void testParseF11 () throws PVException
    {
        // parse exercise file
        PVExercise exercise = parser.parseExercise ("misc/testdata/f11-test.frd");
        
        // check exercise data
        assertEquals (exercise.getFileType (), PVExercise.ExerciseFileType.F6RAW);
        assertEquals (exercise.getUserID (), (byte) 0);
        Calendar date = Calendar.getInstance ();
        date.setTime (exercise.getDate ());
        assertEquals (1, date.get (Calendar.DAY_OF_MONTH));
        assertEquals (9-1, date.get (Calendar.MONTH));
        assertEquals (2008, date.get (Calendar.YEAR));
        assertEquals (19, date.get (Calendar.HOUR_OF_DAY));
        assertEquals (27, date.get (Calendar.MINUTE));
        assertEquals (48, date.get (Calendar.SECOND));
        assertEquals ((byte) 0, exercise.getType ());
        assertEquals ("Normal1", exercise.getTypeLabel ());
        assertEquals (false, exercise.getRecordingMode ().isAltitude ());
        assertEquals (false, exercise.getRecordingMode ().isSpeed ());
        assertEquals (false, exercise.getRecordingMode ().isCadence ());
        assertEquals (false, exercise.getRecordingMode ().isPower ());
        assertEquals ((byte) 0, exercise.getRecordingMode ().getBikeNumber ());
        assertEquals ((54*60*10) + 31*10, exercise.getDuration ());
        assertEquals ((short) 0, exercise.getRecordingInterval ());
        assertEquals ((short) 156, exercise.getHeartRateAVG ());
        assertEquals ((short) 193, exercise.getHeartRateMax ());
        assertEquals (null, exercise.getSpeed ());
        assertEquals (null, exercise.getCadence ());
        assertEquals (null, exercise.getAltitude ());
        assertEquals (null, exercise.getTemperature ());
        assertEquals (601, exercise.getEnergy ());
        assertEquals (2776, exercise.getEnergyTotal ());
        assertEquals ((4*60)+23, exercise.getSumExerciseTime ());
        assertEquals (0, exercise.getSumRideTime ());
        assertEquals (0, exercise.getOdometer ());
        
        // check heart rate limits
        assertEquals (4, exercise.getHeartRateLimits ().length);//--
        assertEquals ((short) 141, exercise.getHeartRateLimits ()[0].getLowerHeartRate ());
        assertEquals ((short) 160, exercise.getHeartRateLimits ()[0].getUpperHeartRate ());
        assertEquals (true, exercise.getHeartRateLimits ()[0].isAbsoluteRange());
        assertEquals (0, exercise.getHeartRateLimits ()[0].getTimeBelow ());
        assertEquals ((31*60)+38, exercise.getHeartRateLimits ()[0].getTimeWithin ());
        assertEquals (0, exercise.getHeartRateLimits ()[0].getTimeAbove ());
        
        assertEquals ((short) 60, exercise.getHeartRateLimits ()[1].getLowerHeartRate ());
        assertEquals ((short) 70, exercise.getHeartRateLimits ()[1].getUpperHeartRate ());
        assertEquals (false, exercise.getHeartRateLimits ()[1].isAbsoluteRange());
        assertEquals (0, exercise.getHeartRateLimits ()[1].getTimeBelow ());
        assertEquals ((6*60)+3, exercise.getHeartRateLimits ()[1].getTimeWithin ());
        assertEquals (0, exercise.getHeartRateLimits ()[1].getTimeAbove ());
        
        assertEquals ((short) 71, exercise.getHeartRateLimits ()[2].getLowerHeartRate ());
        assertEquals ((short) 80, exercise.getHeartRateLimits ()[2].getUpperHeartRate ());
        assertEquals (false, exercise.getHeartRateLimits ()[2].isAbsoluteRange());
        assertEquals (0, exercise.getHeartRateLimits ()[2].getTimeBelow ());
        assertEquals ((31*60)+38, exercise.getHeartRateLimits ()[2].getTimeWithin ());
        assertEquals (0, exercise.getHeartRateLimits ()[2].getTimeAbove ());

        assertEquals ((short) 81, exercise.getHeartRateLimits ()[3].getLowerHeartRate ());
        assertEquals ((short) 90, exercise.getHeartRateLimits ()[3].getUpperHeartRate ());
        assertEquals (false, exercise.getHeartRateLimits ()[3].isAbsoluteRange());
        assertEquals (0, exercise.getHeartRateLimits ()[3].getTimeBelow ());
        assertEquals ((16*60)+50, exercise.getHeartRateLimits ()[3].getTimeWithin ());
        assertEquals (0, exercise.getHeartRateLimits ()[3].getTimeAbove ());
        
        // check lap data
        assertEquals (exercise.getLapList ().length, 0);
        
        // check sample data
        assertEquals (exercise.getSampleList ().length, 0);
    }
}

 	  	 
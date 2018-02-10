package de.saring.exerciseviewer.parser.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

import de.saring.exerciseviewer.core.EVException;
import de.saring.exerciseviewer.data.Lap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.saring.exerciseviewer.data.EVExercise;
import de.saring.exerciseviewer.parser.AbstractExerciseParser;
import de.saring.exerciseviewer.parser.impl.garminfit.GarminFitParser;

/**
 * This class contains all unit tests for the GarminFitParser class.
 *
 * @author Stefan Saring
 */
public class GarminFitParserTest {

    /**
     * Instance to be tested.
     */
    private AbstractExerciseParser parser;

    /**
     * This method initializes the environment for testing.
     */
    @BeforeEach
    public void setUp() throws Exception {
        // change locale/timezone to Germany (files recorded there), otherwise the datetime comparision fails
        Locale.setDefault(Locale.GERMANY);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));

        parser = new GarminFitParser();
    }

    /**
     * This method must fail on parsing an exercise file which doesn't exists.
     */
    @Test
    public void testParseExerciseMissingFile() throws EVException {
        assertThrows(EVException.class, () ->
            parser.parseExercise("missing-file.fit"));
    }

    /**
     * This method tests the parser with an FIT file which contains only settings data,
     * no exercise data. An exception needs to be thrown.
     */
    @Test
    public void testParseExerciseSettingsFile() throws EVException {
        final String FILENAME = "misc/testdata/garmin-fit/Settings.fit";
        assertTrue(new File(FILENAME).exists());

        assertThrows(EVException.class, () ->
                parser.parseExercise(FILENAME));
    }

    /**
     * This method tests the parser with an exercise file with cycling data
     * (contains speed, heartrate, altitude and cadence data).
     */
    @Test
    public void testParseExercise() throws EVException {
        // parse exercise file
        EVExercise exercise = parser.parseExercise("misc/testdata/garmin-fit/2010-07-04-06-07-36.fit");

        // check exercise data
        assertEquals(EVExercise.ExerciseFileType.GARMIN_FIT, exercise.getFileType());
        assertEquals("Garmin EDGE500", exercise.getDeviceName());
        assertTrue(exercise.getRecordingMode().isHeartRate());
        assertTrue(exercise.getRecordingMode().isSpeed());
        assertTrue(exercise.getRecordingMode().isLocation());
        assertTrue(exercise.getRecordingMode().isAltitude());
        assertTrue(exercise.getRecordingMode().isCadence());
        assertTrue(exercise.getRecordingMode().isTemperature());

        assertEquals(LocalDateTime.of(2010, 7, 4, 6, 7, 36), exercise.getDateTime());
        assertEquals(146499, exercise.getDuration().intValue());

        assertEquals(121, exercise.getHeartRateAVG().intValue());
        assertEquals(180, exercise.getHeartRateMax().intValue());
        assertEquals(1567, exercise.getEnergy().intValue());

        assertEquals(101710, exercise.getSpeed().getDistance());
        assertEquals(24.9948, exercise.getSpeed().getSpeedAvg(), 0.001d);
        assertEquals(68.4648, exercise.getSpeed().getSpeedMax(), 0.001d);

        assertEquals(1115, exercise.getAltitude().getAscent());
        assertEquals(127, exercise.getAltitude().getAltitudeMin());
        assertEquals(290, exercise.getAltitude().getAltitudeAvg());
        assertEquals(419, exercise.getAltitude().getAltitudeMax());

        assertEquals(84, exercise.getCadence().getCadenceAvg());
        assertEquals(119, exercise.getCadence().getCadenceMax());

        assertEquals(19, exercise.getTemperature().getTemperatureMin());
        assertEquals(24, exercise.getTemperature().getTemperatureAvg());
        assertEquals(32, exercise.getTemperature().getTemperatureMax());

        // check lap data
        assertEquals(5, exercise.getLapList().size());
        assertEquals(((0 * 3600) + (29 * 60) + 15) * 10, exercise.getLapList().get(0).getTimeSplit());
        assertEquals(126, exercise.getLapList().get(0).getHeartRateAVG().intValue());
        assertEquals(146, exercise.getLapList().get(0).getHeartRateMax().intValue());
        assertEquals(122, exercise.getLapList().get(0).getHeartRateSplit().intValue());
        assertEquals(11084, exercise.getLapList().get(0).getSpeed().getDistance());
        assertEquals(22.7334, exercise.getLapList().get(0).getSpeed().getSpeedAVG(), 0.001d);
        assertEquals(22.1364, exercise.getLapList().get(0).getSpeed().getSpeedEnd(), 0.001d);
        assertEquals(151, exercise.getLapList().get(0).getAltitude().getAscent());
        assertEquals(302, exercise.getLapList().get(0).getAltitude().getAltitude());
        assertEquals(20, exercise.getLapList().get(0).getTemperature().getTemperature());
        assertEquals(51.05553d, exercise.getLapList().get(0).getPositionSplit().getLatitude(), 0.001d);
        assertEquals(13.93589d, exercise.getLapList().get(0).getPositionSplit().getLongitude(), 0.001d);

        assertEquals(((2 * 3600) + (11 * 60) + 46) * 10, exercise.getLapList().get(2).getTimeSplit());
        assertEquals(124, exercise.getLapList().get(2).getHeartRateAVG().intValue());
        assertEquals(145, exercise.getLapList().get(2).getHeartRateMax().intValue());
        assertEquals(98, exercise.getLapList().get(2).getHeartRateSplit().intValue());
        assertEquals(48391, exercise.getLapList().get(2).getSpeed().getDistance());
        assertEquals(21.7080, exercise.getLapList().get(2).getSpeed().getSpeedAVG(), 0.001d);
        assertEquals(1.0440, exercise.getLapList().get(2).getSpeed().getSpeedEnd(), 0.001d);
        assertEquals(342, exercise.getLapList().get(2).getAltitude().getAscent());
        assertEquals(417, exercise.getLapList().get(2).getAltitude().getAltitude());
        assertEquals(24, exercise.getLapList().get(2).getTemperature().getTemperature());
        assertEquals(51.00746d, exercise.getLapList().get(2).getPositionSplit().getLatitude(), 0.001d);
        assertEquals(14.20151d, exercise.getLapList().get(2).getPositionSplit().getLongitude(), 0.001d);

        assertEquals(((4 * 3600) + (28 * 60) + 16) * 10, exercise.getLapList().get(4).getTimeSplit());
        assertEquals(120, exercise.getLapList().get(4).getHeartRateAVG().intValue());
        assertEquals(144, exercise.getLapList().get(4).getHeartRateMax().intValue());
        assertEquals(94, exercise.getLapList().get(4).getHeartRateSplit().intValue());
        assertEquals(101711, exercise.getLapList().get(4).getSpeed().getDistance());
        assertEquals(26.0136, exercise.getLapList().get(4).getSpeed().getSpeedAVG(), 0.001d);
        assertEquals(0d, exercise.getLapList().get(4).getSpeed().getSpeedEnd(), 0.001d);
        assertEquals(206, exercise.getLapList().get(4).getAltitude().getAscent());
        assertEquals(237, exercise.getLapList().get(4).getAltitude().getAltitude());
        assertEquals(30, exercise.getLapList().get(4).getTemperature().getTemperature());
        assertEquals(51.05450d, exercise.getLapList().get(4).getPositionSplit().getLatitude(), 0.001d);
        assertEquals(13.83227d, exercise.getLapList().get(4).getPositionSplit().getLongitude(), 0.001d);

        // check sample data
        assertEquals(8235, exercise.getSampleList().size());
        assertEquals(1000, exercise.getSampleList().get(0).getTimestamp().intValue());
        assertEquals(97, exercise.getSampleList().get(0).getHeartRate().intValue());
        assertEquals(0, exercise.getSampleList().get(0).getDistance().intValue());
        assertEquals(13.8744d, exercise.getSampleList().get(0).getSpeed(), 0.001d);
        assertEquals(232, exercise.getSampleList().get(0).getAltitude().intValue());
        assertEquals(67, exercise.getSampleList().get(0).getCadence().intValue());
        assertEquals(51.05350d, exercise.getSampleList().get(0).getPosition().getLatitude(), 0.001d);
        assertEquals(13.83309d, exercise.getSampleList().get(0).getPosition().getLongitude(), 0.001d);
        assertEquals(20, exercise.getSampleList().get(0).getTemperature().intValue());

        assertEquals(10 * 1000, exercise.getSampleList().get(5).getTimestamp().intValue());
        assertEquals(110, exercise.getSampleList().get(5).getHeartRate().intValue());
        assertEquals(34, exercise.getSampleList().get(5).getDistance().intValue());
        assertEquals(12.2364, exercise.getSampleList().get(5).getSpeed(), 0.001d);
        assertEquals(233, exercise.getSampleList().get(5).getAltitude().intValue());
        assertEquals(69, exercise.getSampleList().get(5).getCadence().intValue());
        assertEquals(51.05323d, exercise.getSampleList().get(5).getPosition().getLatitude(), 0.001d);
        assertEquals(13.83324d, exercise.getSampleList().get(5).getPosition().getLongitude(), 0.001d);
        assertEquals(20, exercise.getSampleList().get(5).getTemperature().intValue());

        assertEquals(((4 * 3600) + (28 * 60) + 15) * 1000, exercise.getSampleList().get(8234).getTimestamp().intValue());
        assertEquals(94, exercise.getSampleList().get(8234).getHeartRate().intValue());
        assertEquals(101710, exercise.getSampleList().get(8234).getDistance().intValue());
        assertEquals(0d, exercise.getSampleList().get(8234).getSpeed(), 0.001d);
        assertEquals(237, exercise.getSampleList().get(8234).getAltitude().intValue());
        assertEquals(0, exercise.getSampleList().get(8234).getCadence().intValue());
        assertEquals(51.05450d, exercise.getSampleList().get(8234).getPosition().getLatitude(), 0.001d);
        assertEquals(13.83227d, exercise.getSampleList().get(8234).getPosition().getLongitude(), 0.001d);
        assertEquals(30, exercise.getSampleList().get(8234).getTemperature().intValue());
    }

    /**
     * This method tests the parser with an exercise file with running data recorded by
     * a Garmin Forerunner 910XT. Mostly the differences of this device are tested here.
     */
    @Test
    public void testParseExerciseForerunner910XT() throws EVException {
        // parse exercise file
        EVExercise exercise = parser.parseExercise("misc/testdata/garmin-fit/Garmin_Forerunner_910XT-Running.fit");

        // check exercise data
        assertEquals(EVExercise.ExerciseFileType.GARMIN_FIT, exercise.getFileType());
        assertEquals("Garmin FR910XT", exercise.getDeviceName());
        assertTrue(exercise.getRecordingMode().isHeartRate());
        assertTrue(exercise.getRecordingMode().isSpeed());
        assertTrue(exercise.getRecordingMode().isLocation());
        assertTrue(exercise.getRecordingMode().isAltitude());
        assertFalse(exercise.getRecordingMode().isCadence());
        assertFalse(exercise.getRecordingMode().isTemperature());

        assertEquals(LocalDateTime.of(2012, 9, 29, 17, 2, 19), exercise.getDateTime());
        assertEquals(30067, exercise.getDuration().intValue());

        assertEquals(155, exercise.getHeartRateAVG().intValue());
        assertEquals(168, exercise.getHeartRateMax().intValue());
        assertEquals(681, exercise.getEnergy().intValue());

        assertEquals(9843, exercise.getSpeed().getDistance());
        assertEquals(11.7841, exercise.getSpeed().getSpeedAvg(), 0.001d);
        assertEquals(15.2424, exercise.getSpeed().getSpeedMax(), 0.001d);

        assertEquals(69, exercise.getAltitude().getAscent());
        assertEquals(97, exercise.getAltitude().getAltitudeMin());
        assertEquals(108, exercise.getAltitude().getAltitudeAvg());
        assertEquals(116, exercise.getAltitude().getAltitudeMax());

        assertNull(exercise.getCadence());
        assertNull(exercise.getTemperature());

        // check some lap data
        assertEquals(10, exercise.getLapList().size());

        assertEquals(((0 * 3600) + (25 * 60) + 53) * 10, exercise.getLapList().get(4).getTimeSplit());
        assertEquals(155, exercise.getLapList().get(4).getHeartRateAVG().intValue());
        assertEquals(159, exercise.getLapList().get(4).getHeartRateMax().intValue());
        assertEquals(159, exercise.getLapList().get(4).getHeartRateSplit().intValue());
        assertEquals(5000, exercise.getLapList().get(4).getSpeed().getDistance());
        assertEquals(11.5905, exercise.getLapList().get(4).getSpeed().getSpeedAVG(), 0.001d);
        assertEquals(11.5848, exercise.getLapList().get(4).getSpeed().getSpeedEnd(), 0.001d);
        assertEquals(5, exercise.getLapList().get(4).getAltitude().getAscent());
        assertEquals(104, exercise.getLapList().get(4).getAltitude().getAltitude());
        assertNull(exercise.getLapList().get(4).getTemperature());
        assertEquals(49.42301d, exercise.getLapList().get(4).getPositionSplit().getLatitude(), 0.001d);
        assertEquals(8.620427d, exercise.getLapList().get(4).getPositionSplit().getLongitude(), 0.001d);

        // check some sample data
        assertEquals(743, exercise.getSampleList().size());
        assertEquals(0, exercise.getSampleList().get(0).getTimestamp().intValue());
        assertEquals(79, exercise.getSampleList().get(0).getHeartRate().intValue());
        assertEquals(1, exercise.getSampleList().get(0).getDistance().intValue());
        assertEquals(2.5452d, exercise.getSampleList().get(0).getSpeed(), 0.001d);
        assertEquals(109, exercise.getSampleList().get(0).getAltitude().intValue());
        assertNull(exercise.getSampleList().get(0).getCadence());
        assertEquals(49.41165d, exercise.getSampleList().get(0).getPosition().getLatitude(), 0.001d);
        assertEquals(8.65186d, exercise.getSampleList().get(0).getPosition().getLongitude(), 0.001d);
        assertNull(exercise.getSampleList().get(0).getTemperature());

        assertEquals(2048000, exercise.getSampleList().get(500).getTimestamp().intValue());
        assertEquals(157, exercise.getSampleList().get(500).getHeartRate().intValue());
        assertEquals(6670, exercise.getSampleList().get(500).getDistance().intValue());
        assertEquals(12.2508d, exercise.getSampleList().get(500).getSpeed(), 0.001d);
        assertEquals(102, exercise.getSampleList().get(500).getAltitude().intValue());
        assertNull(exercise.getSampleList().get(500).getCadence());
        assertEquals(49.42749d, exercise.getSampleList().get(500).getPosition().getLatitude(), 0.001d);
        assertEquals(8.63364d, exercise.getSampleList().get(500).getPosition().getLongitude(), 0.001d);
        assertNull(exercise.getSampleList().get(500).getTemperature());
    }

	/**
	 * This method tests the parser with an exercise file with running data
	 * recorded by a Garmin Fenix 2. Mostly the differences of this device are
	 * tested here.
	 */
	@Test
	public void testParseExerciseFenix2() throws EVException {
		EVExercise exercise = parser.parseExercise("misc/testdata/garmin-fit/Garmin_Fenix2_running_with_hrm.fit");

		// check exercise data
		assertEquals(EVExercise.ExerciseFileType.GARMIN_FIT, exercise.getFileType());
		assertEquals("Garmin FENIX2", exercise.getDeviceName());
        assertTrue(exercise.getRecordingMode().isHeartRate());
		assertTrue(exercise.getRecordingMode().isSpeed());
		assertTrue(exercise.getRecordingMode().isLocation());
		assertTrue(exercise.getRecordingMode().isAltitude());
		assertFalse(exercise.getRecordingMode().isCadence());
		assertTrue(exercise.getRecordingMode().isTemperature());

		assertEquals(LocalDateTime.of(2015, 7, 21, 19, 8, 50), exercise.getDateTime());
		assertEquals(23960, exercise.getDuration().intValue());

		assertEquals(169, exercise.getHeartRateAVG().intValue());
		assertEquals(192, exercise.getHeartRateMax().intValue());
		assertEquals(553, exercise.getEnergy().intValue());

		assertEquals(6235, exercise.getSpeed().getDistance());
		assertEquals(9.36, exercise.getSpeed().getSpeedAvg(), 0.01d);
		assertEquals(15.58, exercise.getSpeed().getSpeedMax(), 0.01d);

		assertEquals(4, exercise.getAltitude().getAscent());
		assertEquals(304, exercise.getAltitude().getAltitudeMin());
		assertEquals(305, exercise.getAltitude().getAltitudeAvg());
		assertEquals(307, exercise.getAltitude().getAltitudeMax());

		assertNull(exercise.getCadence());

		assertEquals(30, exercise.getTemperature().getTemperatureMin());
		assertEquals(31, exercise.getTemperature().getTemperatureAvg());
		assertEquals(34, exercise.getTemperature().getTemperatureMax());

		// check some lap data
		assertEquals(7, exercise.getLapList().size());

		Lap lap5 = exercise.getLapList().get(4);
		assertEquals(19520, lap5.getTimeSplit());

		// average values of heart rate are still missing
		// assertEquals(169, lap5.getHeartRateAVG());
		// assertEquals(173, lap5.getHeartRateMax());
		assertEquals(170, lap5.getHeartRateSplit().intValue());
		assertEquals(5000, lap5.getSpeed().getDistance());
		assertEquals(9.22, lap5.getSpeed().getSpeedAVG(), 0.01d);
		assertEquals(8.35, lap5.getSpeed().getSpeedEnd(), 0.01d);
		assertEquals(0, lap5.getAltitude().getAscent());
		assertEquals(305, lap5.getAltitude().getAltitude());
		assertEquals(31, lap5.getTemperature().getTemperature());
		assertEquals(49.426330681890d, lap5.getPositionSplit().getLatitude(), 0.000001d);
		assertEquals(11.115129310637, lap5.getPositionSplit().getLongitude(), 0.000001d);

		// check some sample data
		assertEquals(2392, exercise.getSampleList().size());
		assertEquals(0, exercise.getSampleList().get(0).getTimestamp().intValue());
		assertEquals(140, exercise.getSampleList().get(0).getHeartRate().intValue());
		assertEquals(0, exercise.getSampleList().get(0).getDistance().intValue());
		assertEquals(3.56d, exercise.getSampleList().get(0).getSpeed(), 0.01d);
		assertEquals(304, exercise.getSampleList().get(0).getAltitude().intValue());
		assertEquals(73, exercise.getSampleList().get(0).getCadence().intValue());
		assertEquals(49.430309236049, exercise.getSampleList().get(0).getPosition().getLatitude(), 0.000001d);
		assertEquals(11.1262008827179, exercise.getSampleList().get(0).getPosition().getLongitude(), 0.000001d);
		assertEquals(32, exercise.getSampleList().get(0).getTemperature().intValue());

		assertEquals(500000, exercise.getSampleList().get(500).getTimestamp().intValue());
		assertEquals(166, exercise.getSampleList().get(500).getHeartRate().intValue());
		assertEquals(1395, exercise.getSampleList().get(500).getDistance().intValue());
		assertEquals(9.0, exercise.getSampleList().get(500).getSpeed(), 0.01d);
		assertEquals(305, exercise.getSampleList().get(500).getAltitude().intValue());
		assertEquals(80, exercise.getSampleList().get(500).getCadence().intValue());
		assertEquals(49.433980593457d, exercise.getSampleList().get(500).getPosition().getLatitude(), 0.000001d);
		assertEquals(11.1192220263183, exercise.getSampleList().get(500).getPosition().getLongitude(), 0.000001d);
		assertEquals(32, exercise.getSampleList().get(500).getTemperature().intValue());
	}

    /**
     * This method tests the parser with an exercise file with cycling data recorded by a Garmin Edge 820.
     * It makes sure that the parser supports the new protocol format 2.0.
     */
    @Test
    public void testParseExerciseEdge820Cycling() throws EVException {
        EVExercise exercise = parser.parseExercise("misc/testdata/garmin-fit/Garmin_Edge_820-Cycling.fit");

        // check exercise data
        assertEquals(EVExercise.ExerciseFileType.GARMIN_FIT, exercise.getFileType());
        assertEquals("Garmin EDGE_820", exercise.getDeviceName());
        assertTrue(exercise.getRecordingMode().isHeartRate());
        assertTrue(exercise.getRecordingMode().isSpeed());
        assertTrue(exercise.getRecordingMode().isLocation());
        assertTrue(exercise.getRecordingMode().isAltitude());
        assertTrue(exercise.getRecordingMode().isCadence());
        assertTrue(exercise.getRecordingMode().isTemperature());

        assertEquals(LocalDateTime.of(2017, 5, 13, 8, 18, 55), exercise.getDateTime());
        assertEquals(70963, exercise.getDuration().intValue());

        assertEquals(134, exercise.getHeartRateAVG().intValue());
        assertEquals(173, exercise.getHeartRateMax().intValue());
        assertEquals(910, exercise.getEnergy().intValue());

        assertEquals(51110, exercise.getSpeed().getDistance());
        assertEquals(25.93, exercise.getSpeed().getSpeedAvg(), 0.01d);
        assertEquals(56.37, exercise.getSpeed().getSpeedMax(), 0.01d);

        assertEquals(653, exercise.getAltitude().getAscent());
        assertEquals(35, exercise.getAltitude().getAltitudeMin());
        assertEquals(130, exercise.getAltitude().getAltitudeAvg());
        assertEquals(320, exercise.getAltitude().getAltitudeMax());

        assertEquals(78, exercise.getCadence().getCadenceAvg());
        assertEquals(98, exercise.getCadence().getCadenceMax());


        assertEquals(16, exercise.getTemperature().getTemperatureMin());
        assertEquals(18, exercise.getTemperature().getTemperatureAvg());
        assertEquals(20, exercise.getTemperature().getTemperatureMax());

        // check some lap data
        assertEquals(1, exercise.getLapList().size());

        Lap lap1 = exercise.getLapList().get(0);
        assertEquals(76570, lap1.getTimeSplit());
        assertEquals(107, lap1.getHeartRateSplit().intValue());
        assertEquals(51110, lap1.getSpeed().getDistance());
        assertEquals(25.93, lap1.getSpeed().getSpeedAVG(), 0.01d);
        assertEquals(0.0, lap1.getSpeed().getSpeedEnd(), 0.01d);
        assertEquals(653, lap1.getAltitude().getAscent());
        assertEquals(126, lap1.getAltitude().getAltitude());
        assertEquals(19, lap1.getTemperature().getTemperature());
        assertEquals(41.467856112867d, lap1.getPositionSplit().getLatitude(), 0.000001d);
        assertEquals(2.0811714045703d, lap1.getPositionSplit().getLongitude(), 0.000001d);

        // check some sample data
        assertEquals(1932, exercise.getSampleList().size());
        assertEquals(0, exercise.getSampleList().get(0).getTimestamp().intValue());
        assertEquals(86, exercise.getSampleList().get(0).getHeartRate().intValue());
        assertEquals(2, exercise.getSampleList().get(0).getDistance().intValue());
        assertEquals(7.56d, exercise.getSampleList().get(0).getSpeed(), 0.01d);
        assertEquals(138, exercise.getSampleList().get(0).getAltitude().intValue());
        assertNull(exercise.getSampleList().get(0).getCadence());
        assertEquals(41.467817220836d, exercise.getSampleList().get(0).getPosition().getLatitude(), 0.000001d);
        assertEquals(2.0810086280107d, exercise.getSampleList().get(0).getPosition().getLongitude(), 0.000001d);
        assertEquals(20, exercise.getSampleList().get(0).getTemperature().intValue());

        assertEquals(216000, exercise.getSampleList().get(50).getTimestamp().intValue());
        assertEquals(91, exercise.getSampleList().get(50).getHeartRate().intValue());
        assertEquals(1202, exercise.getSampleList().get(50).getDistance().intValue());
        assertEquals(26.81, exercise.getSampleList().get(50).getSpeed(), 0.01d);
        assertEquals(127, exercise.getSampleList().get(50).getAltitude().intValue());
        assertEquals(73, exercise.getSampleList().get(50).getCadence().intValue());
        assertEquals(41.471779597923d, exercise.getSampleList().get(50).getPosition().getLatitude(), 0.000001d);
        assertEquals(2.0914101507514d, exercise.getSampleList().get(50).getPosition().getLongitude(), 0.000001d);
        assertEquals(18, exercise.getSampleList().get(50).getTemperature().intValue());
    }
}

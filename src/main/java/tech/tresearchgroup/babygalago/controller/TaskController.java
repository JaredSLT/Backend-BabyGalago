package tech.tresearchgroup.babygalago.controller;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.babygalago.controller.tasks.*;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;
import tech.tresearchgroup.schemas.galago.enums.BaseMediaTypeEnum;
import tech.tresearchgroup.schemas.galago.enums.ScanFrequencyEnum;

import java.util.List;

public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private static final JobDetail bookLibrary = JobBuilder.newJob(BookLibraryScanTask.class)
        .withIdentity("bookLibraryJob", "library")
        .build();
    private static final JobDetail gameLibrary = JobBuilder.newJob(GameLibraryScanTask.class)
        .withIdentity("gameLibraryJob", "library")
        .build();
    private static final JobDetail movieLibrary = JobBuilder.newJob(MovieLibraryScanTask.class)
        .withIdentity("movieLibraryJob", "library")
        .build();
    private static final JobDetail musicLibrary = JobBuilder.newJob(MusicLibraryScanTask.class)
        .withIdentity("musicLibraryJob", "library")
        .build();
    private static final JobDetail tvShowLibrary = JobBuilder.newJob(TvShowLibraryScanTask.class)
        .withIdentity("tvShowLibraryJob", "library")
        .build();
    private static Scheduler scheduler;
    private Trigger bookTrigger;
    private Trigger gameTrigger;
    private Trigger movieTrigger;
    private Trigger musicTrigger;
    private Trigger tvShowTrigger;

    /**
     * Creates each trigger
     *
     * @throws SchedulerException the triggers couldn't be setup
     */
    public TaskController() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        if (isEnabled(BaseMediaTypeEnum.TVSHOW)) {
            tvShowTrigger = setupTrigger(BaseMediaTypeEnum.TVSHOW);
        }
        if (isEnabled(BaseMediaTypeEnum.MUSIC)) {
            musicTrigger = setupTrigger(BaseMediaTypeEnum.MUSIC);
        }
        if (isEnabled(BaseMediaTypeEnum.MOVIE)) {
            movieTrigger = setupTrigger(BaseMediaTypeEnum.MOVIE);
        }
        if (isEnabled(BaseMediaTypeEnum.GAME)) {
            gameTrigger = setupTrigger(BaseMediaTypeEnum.GAME);
        }
        if (isEnabled(BaseMediaTypeEnum.BOOK)) {
            bookTrigger = setupTrigger(BaseMediaTypeEnum.BOOK);
        }
        initAllJobs();
        scheduler.start();
    }

    /**
     * Sets up the triggers scan frequency
     *
     * @param mediaType the media type for the trigger
     * @return the trigger
     */
    private Trigger setupTrigger(BaseMediaTypeEnum mediaType) {
        int time = calculateTime(mediaType);
        if (time == -1) {
            logger.error("Failed to calculate time for: " + mediaType);
            return null;
        }
        return TriggerBuilder.newTrigger()
            .withIdentity(mediaType + "Trigger", "library")
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(time)
                .repeatForever())
            .build();
    }

    /**
     * Whether the library is enabled
     *
     * @param mediaType the media type to check
     * @return true if enabled
     */
    private boolean isEnabled(BaseMediaTypeEnum mediaType) {
        switch (mediaType) {
            case TVSHOW: {
                return SettingsEntity.tvShowScanEnable;
            }
            case MUSIC: {
                return SettingsEntity.musicScanEnable;
            }
            case MOVIE: {
                return SettingsEntity.movieScanEnable;
            }
            case GAME: {
                return SettingsEntity.gameScanEnable;
            }
            case BOOK: {
                return SettingsEntity.bookScanEnable;
            }
        }
        return false;
    }

    /**
     * Calculates the amount of time in seconds from other formats (hours, days, etc)
     *
     * @param mediaType the media type to calculate
     * @return the time in seconds
     */
    private int calculateTime(BaseMediaTypeEnum mediaType) {
        switch (mediaType) {
            case GAME: {
                return calculateSeconds(SettingsEntity.gameScanFrequencyTime, SettingsEntity.gameScanFrequencyType);
            }
            case BOOK: {
                return calculateSeconds(SettingsEntity.bookScanFrequencyTime, SettingsEntity.bookScanFrequencyType);
            }
            case MOVIE: {
                return calculateSeconds(SettingsEntity.movieScanFrequencyTime, SettingsEntity.movieScanFrequencyType);
            }
            case MUSIC: {
                return calculateSeconds(SettingsEntity.musicScanFrequencyTime, SettingsEntity.musicScanFrequencyType);
            }
            case TVSHOW: {
                return calculateSeconds(SettingsEntity.tvShowScanFrequencyTime, SettingsEntity.tvShowScanFrequencyType);
            }
        }
        return -1;
    }

    /**
     * Calculates a duration of time in seconds
     *
     * @param number            the duration
     * @param scanFrequencyEnum the frequency
     * @return the time in seconds
     */
    private int calculateSeconds(int number, ScanFrequencyEnum scanFrequencyEnum) {
        switch (scanFrequencyEnum) {
            case DAYS: {
                return number * 24 * 60 * 60;
            }
            case HOURS: {
                return number * 60 * 60;
            }
            case MINUTES: {
                return number * 60;
            }
        }
        return -1;
    }

    /**
     * Runs all the jobs
     */
    public void initAllJobs() {
        if (SettingsEntity.bookScanEnable) {
            initBookJob();
        }
        if (SettingsEntity.gameScanEnable) {
            initGameJob();
        }
        if (SettingsEntity.movieScanEnable) {
            initMovieJob();
        }
        if (SettingsEntity.musicScanEnable) {
            initMusicJob();
        }
        if (SettingsEntity.tvShowScanEnable) {
            initTvShowJob();
        }
    }

    /**
     * Starts the book job
     */
    public void initBookJob() {
        try {
            scheduler.scheduleJob(bookLibrary, bookTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the game job
     */
    public void initGameJob() {
        try {
            scheduler.scheduleJob(gameLibrary, gameTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the movie job
     */
    public void initMovieJob() {
        try {
            scheduler.scheduleJob(movieLibrary, movieTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the music job
     */
    public void initMusicJob() {
        try {
            scheduler.scheduleJob(musicLibrary, musicTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the tv show job
     */
    public void initTvShowJob() {
        try {
            scheduler.scheduleJob(tvShowLibrary, tvShowTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stops all the jobs
     *
     * @return true if successful
     */
    public boolean stopAllJobs() {
        return stopBookJob() && stopGameJob() && stopMovieJob() && stopMusicJob() && stopTvShowJob();
    }

    /**
     * Stops the book job
     *
     * @return true if successful
     */
    public boolean stopBookJob() {
        try {
            scheduler.pauseJob(bookLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Stops the game job
     *
     * @return true if successful
     */
    public boolean stopGameJob() {
        try {
            scheduler.pauseJob(gameLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Stops the movie job
     *
     * @return true if successful
     */
    public boolean stopMovieJob() {
        try {
            scheduler.pauseJob(movieLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Stops the movie job
     *
     * @return true if successful
     */
    public boolean stopMusicJob() {
        try {
            scheduler.pauseJob(musicLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Stops the tv show job
     *
     * @return true if successful
     */
    public boolean stopTvShowJob() {
        try {
            scheduler.pauseJob(tvShowLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Starts all the jobs
     *
     * @return true if successful
     */
    public boolean startAllJobs() {
        return startBookJob() && startGameJob() && startMovieJob() && startMusicJob() && startTvShowJob();
    }

    /**
     * Stops the book job
     *
     * @return true if successful
     */
    public boolean startBookJob() {
        try {
            scheduler.resumeJob(bookLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Starts the game job
     *
     * @return true if successful
     */
    public boolean startGameJob() {
        try {
            scheduler.resumeJob(gameLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Starts the movie job
     *
     * @return true is successful
     */
    public boolean startMovieJob() {
        try {
            scheduler.resumeJob(movieLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Starts the music job
     *
     * @return true if successful
     */
    public boolean startMusicJob() {
        try {
            scheduler.resumeJob(musicLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the tv show job is running
     *
     * @return true if yes
     */
    public boolean startTvShowJob() {
        try {
            scheduler.resumeJob(tvShowLibrary.getKey());
            return true;
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the book is running
     *
     * @return true if yes
     */
    public boolean isBookRunning() {
        try {
            return !isJobPaused(bookLibrary);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the game job is running
     *
     * @return true if yes
     */
    public boolean isGameRunning() {
        try {
            return !isJobPaused(gameLibrary);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the movie job is running
     *
     * @return true if yes
     */
    public boolean isMovieRunning() {
        try {
            return !isJobPaused(movieLibrary);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the music job is running
     *
     * @return true if yes
     */
    public boolean isMusicRunning() {
        try {
            return !isJobPaused(musicLibrary);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the tv show job is running
     *
     * @return true if yes
     */
    public boolean isTvShowRunning() {
        try {
            return !isJobPaused(tvShowLibrary);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if a job is paused
     *
     * @param jobDetail the job
     * @return true if yes
     * @throws SchedulerException job examination failed
     */
    private boolean isJobPaused(JobDetail jobDetail) throws SchedulerException {
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
        for (Trigger trigger : triggers) {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (Trigger.TriggerState.PAUSED.equals(triggerState)) {
                return true;
            }
        }
        return false;
    }
}

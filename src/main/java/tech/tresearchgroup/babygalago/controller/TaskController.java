package tech.tresearchgroup.babygalago.controller;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import tech.tresearchgroup.babygalago.controller.tasks.*;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;
import tech.tresearchgroup.schemas.galago.enums.BaseMediaTypeEnum;
import tech.tresearchgroup.schemas.galago.enums.ScanFrequencyEnum;

import java.util.List;

public class TaskController {
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

    private Trigger setupTrigger(BaseMediaTypeEnum mediaType) {
        int time = calculateTime(mediaType);
        if (time == -1) {
            System.err.println("Failed to calculate time for: " + mediaType);
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

    private boolean isEnabled(BaseMediaTypeEnum mediaType) {
        switch (mediaType) {
            case TVSHOW -> {
                return SettingsEntity.tvShowScanEnable;
            }
            case MUSIC -> {
                return SettingsEntity.musicScanEnable;
            }
            case MOVIE -> {
                return SettingsEntity.movieScanEnable;
            }
            case GAME -> {
                return SettingsEntity.gameScanEnable;
            }
            case BOOK -> {
                return SettingsEntity.bookScanEnable;
            }
        }
        return false;
    }

    private int calculateTime(BaseMediaTypeEnum mediaType) {
        switch (mediaType) {
            case GAME -> {
                return calculateSeconds(SettingsEntity.gameScanFrequencyTime, SettingsEntity.gameScanFrequencyType);
            }
            case BOOK -> {
                return calculateSeconds(SettingsEntity.bookScanFrequencyTime, SettingsEntity.bookScanFrequencyType);
            }
            case MOVIE -> {
                return calculateSeconds(SettingsEntity.movieScanFrequencyTime, SettingsEntity.movieScanFrequencyType);
            }
            case MUSIC -> {
                return calculateSeconds(SettingsEntity.musicScanFrequencyTime, SettingsEntity.musicScanFrequencyType);
            }
            case TVSHOW -> {
                return calculateSeconds(SettingsEntity.tvShowScanFrequencyTime, SettingsEntity.tvShowScanFrequencyType);
            }
        }
        return -1;
    }

    private int calculateSeconds(int number, ScanFrequencyEnum scanFrequencyEnum) {
        switch (scanFrequencyEnum) {
            case DAYS -> {
                return number * 24 * 60 * 60;
            }
            case HOURS -> {
                return number * 60 * 60;
            }
            case MINUTES -> {
                return number * 60;
            }
        }
        return -1;
    }

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

    public void initBookJob() {
        try {
            scheduler.scheduleJob(bookLibrary, bookTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    public void initGameJob() {
        try {
            scheduler.scheduleJob(gameLibrary, gameTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    public void initMovieJob() {
        try {
            scheduler.scheduleJob(movieLibrary, movieTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    public void initMusicJob() {
        try {
            scheduler.scheduleJob(musicLibrary, musicTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    public void initTvShowJob() {
        try {
            scheduler.scheduleJob(tvShowLibrary, tvShowTrigger);
        } catch (SchedulerException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }

    public boolean stopAllJobs() {
        return stopBookJob() && stopGameJob() && stopMovieJob() && stopMusicJob() && stopTvShowJob();
    }

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

    public boolean startAllJobs() {
        return startBookJob() && startGameJob() && startMovieJob() && startMusicJob() && startTvShowJob();
    }

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

package tech.tresearchgroup.babygalago.controller.tasks;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TvShowLibraryScanTask implements Job {
    private static final Logger logger = LoggerFactory.getLogger(TvShowLibraryScanTask.class);

    public static void processTvShow(Path libraryPath) throws IOException {
        List<Path> files = Files.list(libraryPath).collect(Collectors.toList());
        List<String> submissions = new LinkedList<>();
        for (Path filePath : files) {
            logger.info(String.valueOf(filePath.toAbsolutePath()));
        }
    }

    @Override
    public void execute(JobExecutionContext context) {
        try {
            logger.info("Scanning: " + SettingsEntity.tvShowLibraryPath);
            processTvShow(Path.of(SettingsEntity.tvShowLibraryPath));
        } catch (IOException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }
}

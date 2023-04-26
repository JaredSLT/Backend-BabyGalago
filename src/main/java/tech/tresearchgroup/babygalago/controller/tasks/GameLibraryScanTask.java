package tech.tresearchgroup.babygalago.controller.tasks;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.palila.model.BaseSettings;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GameLibraryScanTask implements Job {
    private static final Logger logger = LoggerFactory.getLogger(GameLibraryScanTask.class);

    public static void processGame(Path libraryPath) throws IOException {
        List<Path> files = Files.list(libraryPath).toList();
        List<String> submissions = new LinkedList<>();
        for (Path filePath : files) {
            if(BaseSettings.debug) {
                logger.info(String.valueOf(filePath.toAbsolutePath()));
            }
        }
    }

    @Override
    public void execute(JobExecutionContext context) {
        try {
            logger.info("Scanning: " + SettingsEntity.gameLibraryPath);
            processGame(Path.of(SettingsEntity.gameLibraryPath));
        } catch (IOException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }
}

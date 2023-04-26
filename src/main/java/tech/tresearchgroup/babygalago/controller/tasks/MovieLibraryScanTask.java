package tech.tresearchgroup.babygalago.controller.tasks;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.tresearchgroup.babygalago.controller.scanners.FileScanController;
import tech.tresearchgroup.babygalago.controller.scanners.VideoScanController;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieLibraryScanTask implements Job {
    private static final Logger logger = LoggerFactory.getLogger(MovieLibraryScanTask.class);

    public static List<String> processMovie(Path libraryPath) throws IOException {
        List<Path> files = Files.list(libraryPath).toList();
        List<String> submissions = new LinkedList<>();
        for (Path filePath : files) {
            String fileExtension = FileScanController.getExtension(filePath);
            if (VideoScanController.isVideoFile(fileExtension)) {
                logger.info("Processing: " + filePath.toAbsolutePath());
                String submission = VideoScanController.getSubmission(filePath);
                if (submission != null) {
                    if (submission.length() > 1) {
                        submissions.add(submission);
                    }
                }
            }
        }
        return submissions;
    }

    @Override
    public void execute(JobExecutionContext context) {
        try {
            logger.info("Scanning: " + SettingsEntity.movieLibraryPath);
            List<String> submissions = processMovie(Path.of(SettingsEntity.movieLibraryPath));
            //Todo submit the submissions to Mama Galago
            logger.info("Done");
        } catch (IOException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
    }
}

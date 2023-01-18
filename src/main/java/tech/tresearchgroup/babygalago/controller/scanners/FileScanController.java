package tech.tresearchgroup.babygalago.controller.scanners;

import tech.tresearchgroup.schemas.mediainfo.MediaInfoOutput;
import tech.tresearchgroup.wrappers.mediainfo.MediaInfo;
import tech.tresearchgroup.wrappers.mediainfo.model.MediaInfoOptions;

import java.nio.file.Path;

public class FileScanController {
    public static String getExtension(Path filePath) {
        String[] parts = filePath.getFileName().toString().split("\\.");
        return parts[parts.length - 1];
    }

    public static MediaInfoOutput getMediaInfo(Path path) {
        MediaInfo mediaInfo = new MediaInfo();
        MediaInfoOptions mediaInfoOptions = new MediaInfoOptions();
        mediaInfoOptions.setFull(true);
        mediaInfoOptions.setOutput("JSON");
        mediaInfo.setMediaInfoOptions(mediaInfoOptions);
        mediaInfo.setFile(path.toAbsolutePath().toString());
        return mediaInfo.getOutput(mediaInfo.getOptions());
    }
}

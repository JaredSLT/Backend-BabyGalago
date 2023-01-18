package tech.tresearchgroup.babygalago.controller.scanners;

import tech.tresearchgroup.schemas.fpcalc.FPCalcOutput;
import tech.tresearchgroup.schemas.galago.enums.VideoContainerEnum;
import tech.tresearchgroup.schemas.mediainfo.Media;
import tech.tresearchgroup.schemas.mediainfo.MediaInfoOutput;
import tech.tresearchgroup.schemas.mediainfo.TrackItem;
import tech.tresearchgroup.wrappers.ffmpeg.FFMpeg;
import tech.tresearchgroup.wrappers.ffmpeg.model.FFMpegOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class VideoScanController {
    public static boolean isVideoFile(String fileExtension) {
        for (VideoContainerEnum container : VideoContainerEnum.values()) {
            if (container.name().equals(fileExtension.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static int getSubtitleCount(MediaInfoOutput mediaInfoOutput) {
        int subsCount = 0;
        try {
            Media media = mediaInfoOutput.getMedia();
            if (media != null) {
                for (TrackItem trackItem : media.getTrack()) {
                    if (trackItem.getType().equals("General")) {
                        subsCount = Integer.parseInt(trackItem.getTextCount());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subsCount;
    }

    public static List<List<String>> getSubtitles(Path path, MediaInfoOutput mediaInfoOutput) throws IOException {
        FFMpeg ffMpeg = new FFMpeg();
        FFMpegOptions ffMpegOptions = new FFMpegOptions();
        ffMpegOptions.setOverwriteOutput(true);
        ffMpegOptions.setInput(path.toAbsolutePath().toString());
        List<List<String>> subtitles = new LinkedList<>();
        int subsCount = getSubtitleCount(mediaInfoOutput);
        for (int i = 0; i < subsCount; i++) {
            System.out.println("Getting subtitle: " + i);
            Path subsPath = Path.of("subs-" + i + ".srt");
            if (Files.exists(subsPath)) {
                Files.delete(subsPath);
            }
            ffMpeg.setFile("subs-" + i + ".srt");
            ffMpegOptions.setMap("0:s:" + i);
            ffMpeg.setFfMpegOptions(ffMpegOptions);
            ffMpeg.getOutput(ffMpeg.getOptions());
            List<String> sub = Files.readAllLines(subsPath);
            Files.delete(subsPath);
            subtitles.add(sub);
        }
        return subtitles;
    }

    public static String getSubmission(Path filePath) throws IOException {
        MediaInfoOutput mediaInfoOutput = FileScanController.getMediaInfo(filePath);
        FPCalcOutput fpCalcOutput = AudioScanController.getFPCalc(filePath);
        List<List<String>> subtitles = VideoScanController.getSubtitles(filePath, mediaInfoOutput);

        return null;
    }
}

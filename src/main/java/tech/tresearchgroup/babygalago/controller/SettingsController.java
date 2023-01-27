package tech.tresearchgroup.babygalago.controller;

import com.google.gson.Gson;
import tech.tresearchgroup.dao.model.DatabaseTypeEnum;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.model.enums.CompressionMethodEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.sao.model.SearchMethodEnum;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;
import tech.tresearchgroup.schemas.galago.entities.SettingsFileEntity;
import tech.tresearchgroup.schemas.galago.entities.UserSettingsEntity;
import tech.tresearchgroup.schemas.galago.enums.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsController extends BasicController {
    private static final Gson gson = new Gson();

    /**
     * Loads the settings from Settings.json and applies them to SettingsEntity
     */
    public static void loadSettings() throws IOException {
        Path file = Paths.get("Settings.json");
        if (!file.toFile().exists()) {
            SettingsFileEntity settingsFileEntity = new SettingsFileEntity();
            settingsFileEntity.setDefaults();
            Files.write(file, gson.toJson(settingsFileEntity).getBytes());
        }
        String json = Files.readString(file);
        SettingsFileEntity settingsFileEntity = new Gson().fromJson(json, SettingsFileEntity.class);
        SettingsEntity.interfaceMethod = settingsFileEntity.getInterfaceMethod();
        SettingsEntity.defaultPlaybackQuality = settingsFileEntity.getDefaultPlaybackQuality();
        SettingsEntity.debug = settingsFileEntity.isDebug();
        SettingsEntity.maintenanceMode = settingsFileEntity.isMaintenanceMode();
        SettingsEntity.enableSecurity = settingsFileEntity.isEnableSecurity();
        SettingsEntity.compressionMethod = settingsFileEntity.getCompressionMethod();
        SettingsEntity.compressionQuality = settingsFileEntity.getCompressionQuality();
        SettingsEntity.issuer = settingsFileEntity.getIssuer();
        SettingsEntity.secretKey = settingsFileEntity.getSecretKey();
        SettingsEntity.searchHost = settingsFileEntity.getSearchHost();
        SettingsEntity.searchKey = settingsFileEntity.getSearchKey();
        SettingsEntity.displayMode = settingsFileEntity.getDisplayMode();
        SettingsEntity.encoderProgram = settingsFileEntity.getEncoderProgram();
        SettingsEntity.inspectorProgram = settingsFileEntity.getInspectorProgram();
        SettingsEntity.audioCodec = settingsFileEntity.getAudioCodec();
        SettingsEntity.audioRate = settingsFileEntity.getAudioRate();
        SettingsEntity.audioPreset = settingsFileEntity.getAudioPreset();
        SettingsEntity.videoContainer = settingsFileEntity.getVideoContainer();
        SettingsEntity.videoCodec = settingsFileEntity.getVideoCodec();
        SettingsEntity.encoderPreset = settingsFileEntity.getEncoderPreset();
        SettingsEntity.videoTuneFilm = settingsFileEntity.isVideoTuneFilm();
        SettingsEntity.videoTuneAnimation = settingsFileEntity.isVideoTuneAnimation();
        SettingsEntity.videoTuneGrain = settingsFileEntity.isVideoTuneGrain();
        SettingsEntity.videoTuneStillImage = settingsFileEntity.isVideoTuneStillImage();
        SettingsEntity.videoTuneFastDecode = settingsFileEntity.isVideoTuneFastDecode();
        SettingsEntity.videoTuneZeroLatency = settingsFileEntity.isVideoTuneZeroLatency();
        SettingsEntity.videoFastStart = settingsFileEntity.isVideoFastStart();
        SettingsEntity.videoTunePsnr = settingsFileEntity.isVideoTunePsnr();
        SettingsEntity.videoTuneSsnr = settingsFileEntity.isVideoTuneSsnr();
        SettingsEntity.videoCrf = settingsFileEntity.getVideoCrf();
        SettingsEntity.videoBlackBorder = settingsFileEntity.isVideoBlackBorder();
        SettingsEntity.videoCudaAcceleration = settingsFileEntity.isVideoCudaAcceleration();
        SettingsEntity.oneFourFourVideoTranscodeBitrate = settingsFileEntity.getOneFourFourVideoTranscodeBitrate();
        SettingsEntity.twoFourZeroVideoTranscodeBitrate = settingsFileEntity.getTwoFourZeroVideoTranscodeBitrate();
        SettingsEntity.threeSixZeroVideoTranscodeBitrate = settingsFileEntity.getThreeSixZeroVideoTranscodeBitrate();
        SettingsEntity.fourEightZeroVideoTranscodeBitrate = settingsFileEntity.getFourEightZeroVideoTranscodeBitrate();
        SettingsEntity.sevenTwoZeroVideoTranscodeBitrate = settingsFileEntity.getSevenTwoZeroVideoTranscodeBitrate();
        SettingsEntity.oneZeroEightZeroVideoTranscodeBitrate = settingsFileEntity.getOneZeroEightZeroVideoTranscodeBitrate();
        SettingsEntity.twoKVideoTranscodeBitrate = settingsFileEntity.getTwoKVideoTranscodeBitrate();
        SettingsEntity.fourKVideoTranscodeBitrate = settingsFileEntity.getFourKVideoTranscodeBitrate();
        SettingsEntity.eightKVideoTranscodeBitrate = settingsFileEntity.getEightKVideoTranscodeBitrate();
        SettingsEntity.tableShowPoster = settingsFileEntity.isTableShowPoster();
        SettingsEntity.tableShowName = settingsFileEntity.isTableShowName();
        SettingsEntity.tableShowRuntime = settingsFileEntity.isTableShowRuntime();
        SettingsEntity.tableShowGenre = settingsFileEntity.isTableShowGenre();
        SettingsEntity.tableShowMpaaRating = settingsFileEntity.isTableShowMpaaRating();
        SettingsEntity.tableShowUserRating = settingsFileEntity.isTableShowUserRating();
        SettingsEntity.tableShowLanguage = settingsFileEntity.isTableShowLanguage();
        SettingsEntity.tableShowReleaseDate = settingsFileEntity.isTableShowReleaseDate();
        SettingsEntity.tableShowActions = settingsFileEntity.isTableShowActions();
        SettingsEntity.bookLibraryEnable = settingsFileEntity.isBookLibraryEnable();
        SettingsEntity.bookLibraryPath = settingsFileEntity.getBookLibraryPath();
        SettingsEntity.bookScanEnable = settingsFileEntity.isBookScanEnable();
        SettingsEntity.bookScanFrequencyTime = settingsFileEntity.getBookScanFrequencyTime();
        SettingsEntity.bookScanFrequencyType = settingsFileEntity.getBookScanFrequencyType();
        SettingsEntity.gameLibraryEnable = settingsFileEntity.isGameLibraryEnable();
        SettingsEntity.gameLibraryPath = settingsFileEntity.getGameLibraryPath();
        SettingsEntity.gameScanEnable = settingsFileEntity.isGameScanEnable();
        SettingsEntity.gameScanFrequencyTime = settingsFileEntity.getGameScanFrequencyTime();
        SettingsEntity.gameScanFrequencyType = settingsFileEntity.getGameScanFrequencyType();
        SettingsEntity.movieLibraryEnable = settingsFileEntity.isMovieLibraryEnable();
        SettingsEntity.movieLibraryPath = settingsFileEntity.getMovieLibraryPath();
        SettingsEntity.movieScanEnable = settingsFileEntity.isMovieScanEnable();
        SettingsEntity.moviePreTranscodeEnable = settingsFileEntity.isMoviePreTranscodeEnable();
        SettingsEntity.moviePreTranscode144p = settingsFileEntity.isMoviePreTranscode144p();
        SettingsEntity.moviePreTranscode240p = settingsFileEntity.isMoviePreTranscode240p();
        SettingsEntity.moviePreTranscode360p = settingsFileEntity.isMoviePreTranscode360p();
        SettingsEntity.moviePreTranscode480p = settingsFileEntity.isMoviePreTranscode480p();
        SettingsEntity.moviePreTranscode720p = settingsFileEntity.isMoviePreTranscode720p();
        SettingsEntity.moviePreTranscode1080p = settingsFileEntity.isMoviePreTranscode1080p();
        SettingsEntity.moviePreTranscode2k = settingsFileEntity.isMoviePreTranscode2k();
        SettingsEntity.moviePreTranscode4k = settingsFileEntity.isMoviePreTranscode4k();
        SettingsEntity.moviePreTranscode8k = settingsFileEntity.isMoviePreTranscode8k();
        SettingsEntity.movieScanFrequencyTime = settingsFileEntity.getMovieScanFrequencyTime();
        SettingsEntity.movieScanFrequencyType = settingsFileEntity.getMovieScanFrequencyType();
        SettingsEntity.moviePreTranscodeLibraryPath = settingsFileEntity.getMoviePreTranscodeLibraryPath();
        SettingsEntity.musicLibraryEnable = settingsFileEntity.isMusicLibraryEnable();
        SettingsEntity.musicLibraryPath = settingsFileEntity.getMusicLibraryPath();
        SettingsEntity.musicScanEnable = settingsFileEntity.isMusicScanEnable();
        SettingsEntity.musicPreTranscodeEnable = settingsFileEntity.isMusicPreTranscodeEnable();
        SettingsEntity.musicPreTranscode64k = settingsFileEntity.isMusicPreTranscode64k();
        SettingsEntity.musicPreTranscode96k = settingsFileEntity.isMusicPreTranscode96k();
        SettingsEntity.musicPreTranscode128k = settingsFileEntity.isMusicPreTranscode128k();
        SettingsEntity.musicPreTranscode320k = settingsFileEntity.isMusicPreTranscode320k();
        SettingsEntity.musicPreTranscode1411k = settingsFileEntity.isMusicPreTranscode1411k();
        SettingsEntity.musicScanFrequencyTime = settingsFileEntity.getMusicScanFrequencyTime();
        SettingsEntity.musicScanFrequencyType = settingsFileEntity.getMusicScanFrequencyType();
        SettingsEntity.musicPreTranscodeLibraryPath = settingsFileEntity.getMusicPreTranscodeLibraryPath();
        SettingsEntity.tvShowLibraryEnable = settingsFileEntity.isTvShowLibraryEnable();
        SettingsEntity.tvShowLibraryPath = settingsFileEntity.getTvShowLibraryPath();
        SettingsEntity.tvShowScanEnable = settingsFileEntity.isTvShowScanEnable();
        SettingsEntity.tvShowPreTranscodeEnable = settingsFileEntity.isTvShowPreTranscodeEnable();
        SettingsEntity.tvShowPreTranscode144p = settingsFileEntity.isTvShowPreTranscode144p();
        SettingsEntity.tvShowPreTranscode240p = settingsFileEntity.isTvShowPreTranscode240p();
        SettingsEntity.tvShowPreTranscode360p = settingsFileEntity.isTvShowPreTranscode360p();
        SettingsEntity.tvShowPreTranscode480p = settingsFileEntity.isTvShowPreTranscode480p();
        SettingsEntity.tvShowPreTranscode720p = settingsFileEntity.isTvShowPreTranscode720p();
        SettingsEntity.tvShowPreTranscode1080p = settingsFileEntity.isTvShowPreTranscode1080p();
        SettingsEntity.tvShowPreTranscode2k = settingsFileEntity.isTvShowPreTranscode2k();
        SettingsEntity.tvShowPreTranscode4k = settingsFileEntity.isTvShowPreTranscode4k();
        SettingsEntity.tvShowPreTranscode8k = settingsFileEntity.isTvShowPreTranscode8k();
        SettingsEntity.tvShowScanFrequencyTime = settingsFileEntity.getTvShowScanFrequencyTime();
        SettingsEntity.tvShowScanFrequencyType = settingsFileEntity.getTvShowScanFrequencyType();
        SettingsEntity.tvShowPreTranscodeLibraryPath = settingsFileEntity.getTvShowPreTranscodeLibraryPath();
        SettingsEntity.serverName = settingsFileEntity.getServerName();
        SettingsEntity.allowRegistration = settingsFileEntity.isAllowRegistration();
        SettingsEntity.homePageShowNewBook = settingsFileEntity.isHomePageShowNewBook();
        SettingsEntity.homePageShowNewGame = settingsFileEntity.isHomePageShowNewGame();
        SettingsEntity.homePageShowNewMovie = settingsFileEntity.isHomePageShowNewMovie();
        SettingsEntity.homePageShowNewMusic = settingsFileEntity.isHomePageShowNewMusic();
        SettingsEntity.homePageShowNewTvShow = settingsFileEntity.isHomePageShowNewTvShow();
        SettingsEntity.homePageShowPopularBook = settingsFileEntity.isHomePageShowPopularBook();
        SettingsEntity.homePageShowPopularGame = settingsFileEntity.isHomePageShowPopularGame();
        SettingsEntity.homePageShowPopularMovie = settingsFileEntity.isHomePageShowPopularMovie();
        SettingsEntity.homePageShowPopularMusic = settingsFileEntity.isHomePageShowPopularMusic();
        SettingsEntity.homePageShowPopularTvShow = settingsFileEntity.isHomePageShowPopularTvShow();
        SettingsEntity.searchMethod = settingsFileEntity.getSearchMethod();
        SettingsEntity.maxSearchResults = settingsFileEntity.getMaxSearchResults();
        SettingsEntity.maxUIBrowseResults = settingsFileEntity.getMaxUIBrowseResults();
        SettingsEntity.maxAPIBrowseResults = settingsFileEntity.getMaxAPIBrowseResults();
        SettingsEntity.cardWidth = settingsFileEntity.getCardWidth();
        SettingsEntity.stickyTopMenu = settingsFileEntity.isStickyTopMenu();
        SettingsEntity.databaseType = settingsFileEntity.getDatabaseType();
        SettingsEntity.databaseName = settingsFileEntity.getDatabaseName();
        SettingsEntity.minDatabaseConnections = settingsFileEntity.getMinDatabaseConnections();
        SettingsEntity.maxDatabaseConnections = settingsFileEntity.getMaxDatabaseConnections();
        SettingsEntity.loggingEnabled = settingsFileEntity.isLoggingEnabled();
        SettingsEntity.baseLibraryPath = settingsFileEntity.getBaseLibraryPath();
        SettingsEntity.entityPackages = settingsFileEntity.getEntityPackages();
        SettingsEntity.enableHistory = settingsFileEntity.isEnableHistory();
        SettingsEntity.enableUpload = settingsFileEntity.isEnableUpload();
        SettingsEntity.profilePhotoFolder = settingsFileEntity.getProfilePhotoFolder();
        SettingsEntity.chunk = settingsFileEntity.getChunk();
    }

    /**
     * Saves the SettingsFileEntity to Settings.json
     *
     * @param settingsFileEntity the settings entity to save
     * @return whether saving was successful or not
     */
    public static boolean saveSettings(SettingsFileEntity settingsFileEntity) {
        String json = gson.toJson(settingsFileEntity);
        try {
            FileOutputStream outputStream = new FileOutputStream("Settings.json");
            byte[] strToBytes = json.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
            return true;
        } catch (IOException e) {
            if (SettingsEntity.debug) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks whether the library for the class is disabled or not
     *
     * @param theClass the class to check
     * @return true if it is disabled
     */
    public boolean isLibraryDisabled(Class theClass) {
        switch (theClass.getSimpleName().toLowerCase()) {
            case "bookentity" -> {
                return !isBookLibraryEnable();
            }
            case "movieentity" -> {
                return !isMovieLibraryEnable();
            }
            case "songentity" -> {
                return !isMusicLibraryEnable();
            }
            case "tvshowentity" -> {
                return !isTvShowLibraryEnable();
            }
            case "gameentity" -> {
                return !isGameLibraryEnable();
            }
        }
        return false;
    }

    /**
     * Gets the interface method from the user settings
     *
     * @param userSettingsEntity the users settings entity
     * @return the interface method
     */
    public InterfaceMethodEnum getInterfaceMethod(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.getInterfaceMethod();
        }
        return SettingsEntity.interfaceMethod;
    }

    /**
     * Gets the default video player playback quality from user settings
     *
     * @param userSettingsEntity the users settings entity
     * @return the playback quality
     */
    public PlaybackQualityEnum getDefaultPlaybackQuality(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.getDefaultPlaybackQuality();
        }
        return SettingsEntity.defaultPlaybackQuality;
    }

    /**
     * Whether the server is in debug mode
     *
     * @return true if it is
     */
    public boolean isDebug() {
        return SettingsEntity.debug;
    }

    /**
     * Set the server to debug mode
     *
     * @param debug true to display more output
     */
    public void setDebug(boolean debug) {
        SettingsEntity.debug = debug;
    }

    /**
     * Whether the server is in maintenance mode
     *
     * @return true if it is
     */
    public boolean isMaintenanceMode() {
        return SettingsEntity.maintenanceMode;
    }

    /**
     * Sets the server into maintenance mode
     *
     * @param maintenanceMode true to redirect users to the maintenance page
     */
    public void setMaintenanceMode(boolean maintenanceMode) {
        SettingsEntity.maintenanceMode = maintenanceMode;
    }

    /**
     * Whether to check user authentication
     *
     * @return true if it will
     */
    public boolean isEnableSecurity() {
        return SettingsEntity.enableSecurity;
    }

    /**
     * Sets the servers security setting
     *
     * @param enableSecurity true to check authentications, false to skip them
     */
    public void setEnableSecurity(boolean enableSecurity) {
        SettingsEntity.enableSecurity = enableSecurity;
    }

    /**
     * Gets the page compression method. The compression method must be supported by both the transmission type
     * (HTTP, HTTPS) and the browser that is loading the content.
     *
     * @return the compression method
     */
    public CompressionMethodEnum getCompressionMethod() {
        return SettingsEntity.compressionMethod;
    }

    /**
     * Sets the servers compression method. If you're using HTTPS and are targeting modern browsers you should use
     * BROTLI.
     *
     * @param compressionMethod the compression method
     */
    public void setCompressionMethod(CompressionMethodEnum compressionMethod) {
        SettingsEntity.compressionMethod = compressionMethod;
    }

    /**
     * Get the JWT issuer
     *
     * @return the issuer
     */
    public String getIssuer() {
        return SettingsEntity.issuer;
    }

    /**
     * Sets the JWT issuer
     *
     * @param issuer the issuer
     */
    public void setIssuer(String issuer) {
        SettingsEntity.issuer = issuer;
    }

    /**
     * Gets the JWT secret key
     *
     * @return the key
     */
    public String getSecretKey() {
        return SettingsEntity.secretKey;
    }

    /**
     * Sets the JWT secret key
     *
     * @param secretKey the secret key
     */
    public void setSecretKey(String secretKey) {
        SettingsEntity.secretKey = secretKey;
    }

    /**
     * Gets the search host
     *
     * @return the search host
     */
    public String getSearchHost() {
        return SettingsEntity.searchHost;
    }

    /**
     * Sets the search server host (with port)
     *
     * @param searchHost the host
     */
    public void setSearchHost(String searchHost) {
        SettingsEntity.searchHost = searchHost;
    }

    /**
     * Gets the search key
     *
     * @return the search key
     */
    public String getSearchKey() {
        return SettingsEntity.searchKey;
    }

    /**
     * Sets the search key
     *
     * @param searchKey the search key
     */
    public void setSearchKey(String searchKey) {
        SettingsEntity.searchKey = searchKey;
    }

    /**
     * Gets the display mode. This is used during UI page generation
     *
     * @param userSettingsEntity the user settings entity
     * @return the users desired display mode
     */
    public DisplayModeEnum getDisplayMode(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.getDisplayMode();
        }
        return SettingsEntity.displayMode;
    }

    /**
     * Gets the administrators preferred encoder program
     *
     * @return the program name
     */
    public EncoderProgramEnum getEncoderProgram() {
        return SettingsEntity.encoderProgram;
    }

    /**
     * Sets the preferred encoder program
     *
     * @param encoderProgram the encoder name
     */
    public void setEncoderProgram(EncoderProgramEnum encoderProgram) {
        SettingsEntity.encoderProgram = encoderProgram;
    }

    /**
     * Gets the preffered inspector program
     *
     * @return the inspector program name
     */
    public InspectorProgramEnum getInspectorProgram() {
        return SettingsEntity.inspectorProgram;
    }

    /**
     * Sets the preferred inspector program
     *
     * @param inspectorProgram the inspector name
     */
    public void setInspectorProgram(InspectorProgramEnum inspectorProgram) {
        SettingsEntity.inspectorProgram = inspectorProgram;
    }

    /**
     * Gets the audio codec (used for transcoding)
     *
     * @return the audio codec name
     */
    public AudioCodecEnum getAudioCodec() {
        return SettingsEntity.audioCodec;
    }

    /**
     * Sets the preferred audio codec
     *
     * @param audioCodec the audio codec name
     */
    public void setAudioCodec(AudioCodecEnum audioCodec) {
        SettingsEntity.audioCodec = audioCodec;
    }

    /**
     * Gets the preferred audio rate
     *
     * @return the audio rate
     */
    public AudioRateEnum getAudioRate() {
        return SettingsEntity.audioRate;
    }

    /**
     * Sets the preferred audio rate
     *
     * @param audioRate the audio rate
     */
    public void setAudioRate(AudioRateEnum audioRate) {
        SettingsEntity.audioRate = audioRate;
    }

    /**
     * Gets the audio encoder preset
     *
     * @return the preset
     */
    public EncoderPresetEnum getAudioPreset() {
        return SettingsEntity.audioPreset;
    }

    /**
     * Sets the audio encoder preset
     *
     * @param audioPreset the preset
     */
    public void setAudioPreset(EncoderPresetEnum audioPreset) {
        SettingsEntity.audioPreset = audioPreset;
    }

    /**
     * Gets the video container (used for transcoding)
     *
     * @return the video container name
     */
    public VideoContainerEnum getVideoContainer() {
        return SettingsEntity.videoContainer;
    }

    /**
     * Sets the preferred video container (used for transcoding)
     *
     * @param videoContainer the video container
     */
    public void setVideoContainer(VideoContainerEnum videoContainer) {
        SettingsEntity.videoContainer = videoContainer;
    }

    /**
     * Gets the video codec
     *
     * @return the name of the video codec
     */
    public VideoCodecEnum getVideoCodec() {
        return SettingsEntity.videoCodec;
    }

    /**
     * Sets the preferred video codec
     *
     * @param videoCodec the video codec
     */
    public void setVideoCodec(VideoCodecEnum videoCodec) {
        SettingsEntity.videoCodec = videoCodec;
    }

    /**
     * Gets the encoder preset
     *
     * @return the encoder preset
     */
    public EncoderPresetEnum getEncoderPreset() {
        return SettingsEntity.encoderPreset;
    }

    /**
     * Sets the encoder preset
     *
     * @param encoderPreset the preset
     */
    public void setEncoderPreset(EncoderPresetEnum encoderPreset) {
        SettingsEntity.encoderPreset = encoderPreset;
    }

    /**
     * Whether the encoder should tune for film
     *
     * @return true if yes
     */
    public boolean isVideoTuneFilm() {
        return SettingsEntity.videoTuneFilm;
    }

    /**
     * Sets the encoders film tune function
     *
     * @param videoTuneFilm true to use it
     */
    public void setVideoTuneFilm(boolean videoTuneFilm) {
        SettingsEntity.videoTuneFilm = videoTuneFilm;
    }

    /**
     * Whether the encoder should tune for animation
     *
     * @return true if yes
     */
    public boolean isVideoTuneAnimation() {
        return SettingsEntity.videoTuneAnimation;
    }

    /**
     * Sets the encoders animation tune function
     *
     * @param videoTuneAnimation true to use it
     */
    public void setVideoTuneAnimation(boolean videoTuneAnimation) {
        SettingsEntity.videoTuneAnimation = videoTuneAnimation;
    }

    /**
     * Whether the encoder should tune for grain
     *
     * @return true if yes
     */
    public boolean isVideoTuneGrain() {
        return SettingsEntity.videoTuneGrain;
    }

    /**
     * Sets the encoders grain tune function
     *
     * @param videoTuneGrain true to use it
     */
    public void setVideoTuneGrain(boolean videoTuneGrain) {
        SettingsEntity.videoTuneGrain = videoTuneGrain;
    }

    /**
     * Whether the encoder should tune for still images
     *
     * @return true if yes
     */
    public boolean isVideoTuneStillImage() {
        return SettingsEntity.videoTuneStillImage;
    }

    /**
     * Sets the encoders still image tune function
     *
     * @param videoTuneStillImage true to use it
     */
    public void setVideoTuneStillImage(boolean videoTuneStillImage) {
        SettingsEntity.videoTuneStillImage = videoTuneStillImage;
    }

    /**
     * Whether the encoder should tune for fast decoding
     *
     * @return true if yes
     */
    public boolean isVideoTuneFastDecode() {
        return SettingsEntity.videoTuneFastDecode;
    }

    /**
     * Sets the encoders fast decode function
     *
     * @param videoTuneFastDecode true to use it
     */
    public void setVideoTuneFastDecode(boolean videoTuneFastDecode) {
        SettingsEntity.videoTuneFastDecode = videoTuneFastDecode;
    }

    /**
     * Whether the encoder should tune for zero latency
     *
     * @return true if yes
     */
    public boolean isVideoTuneZeroLatency() {
        return SettingsEntity.videoTuneZeroLatency;
    }

    /**
     * Sets the encoders zero latency function
     *
     * @param videoTuneZeroLatency true to use it
     */
    public void setVideoTuneZeroLatency(boolean videoTuneZeroLatency) {
        SettingsEntity.videoTuneZeroLatency = videoTuneZeroLatency;
    }

    /**
     * Whether the encoder should tune for fast start
     *
     * @return true if yes
     */
    public boolean isVideoFastStart() {
        return SettingsEntity.videoFastStart;
    }

    /**
     * Sets the encoders fast start function
     *
     * @param videoFastStart true to use it
     */
    public void setVideoFastStart(boolean videoFastStart) {
        SettingsEntity.videoFastStart = videoFastStart;
    }

    /**
     * Whether the encoder should tune for PSNR
     *
     * @return true if yes
     */
    public boolean isVideoTunePsnr() {
        return SettingsEntity.videoTunePsnr;
    }

    /**
     * Sets the encoders tune psnr function
     *
     * @param videoTunePsnr true to use it
     */
    public void setVideoTunePsnr(boolean videoTunePsnr) {
        SettingsEntity.videoTunePsnr = videoTunePsnr;
    }

    /**
     * Whether the encoder should tune for SSNR
     *
     * @return true if yes
     */
    public boolean isVideoTuneSsnr() {
        return SettingsEntity.videoTuneSsnr;
    }

    /**
     * Sets the encoders tune ssnr function
     *
     * @param videoTuneSsnr true to use it
     */
    public void setVideoTuneSsnr(boolean videoTuneSsnr) {
        SettingsEntity.videoTuneSsnr = videoTuneSsnr;
    }

    /**
     * Gets the encoders constant frame rate value
     *
     * @return the frame rate
     */
    public int getVideoCrf() {
        return SettingsEntity.videoCrf;
    }

    /**
     * Sets the frame rate to a constant value
     *
     * @param videoCrf the frame rate
     */
    public void setVideoCrf(int videoCrf) {
        SettingsEntity.videoCrf = videoCrf;
    }

    /**
     * Whether the encoder will attempt to remove black borders
     *
     * @return true if yes
     */
    public boolean isVideoBlackBorder() {
        return SettingsEntity.videoBlackBorder;
    }

    /**
     * Sets the encoders remove black borders function
     *
     * @param videoBlackBorder true to use
     */
    public void setVideoBlackBorder(boolean videoBlackBorder) {
        SettingsEntity.videoBlackBorder = videoBlackBorder;
    }

    /**
     * Whether the encoder is using CUDA video acceleration
     *
     * @return true if yes
     */
    public boolean isVideoCudaAcceleration() {
        return SettingsEntity.videoCudaAcceleration;
    }

    /**
     * Determines whether the encoder should use CUDA acceleration
     *
     * @param videoCudaAcceleration true to use
     */
    public void setVideoCudaAcceleration(boolean videoCudaAcceleration) {
        SettingsEntity.videoCudaAcceleration = videoCudaAcceleration;
    }

    /**
     * Gets the video bitrate for 144p transcodes
     *
     * @return the bitrate
     */
    public int getOneFourFourVideoTranscodeBitrate() {
        return SettingsEntity.oneFourFourVideoTranscodeBitrate;
    }

    /**
     * SEts the video bitrate for 144p transcodes
     *
     * @param oneFourFourVideoTranscodeBitrate the bitrate
     */
    public void setOneFourFourVideoTranscodeBitrate(int oneFourFourVideoTranscodeBitrate) {
        SettingsEntity.oneFourFourVideoTranscodeBitrate = oneFourFourVideoTranscodeBitrate;
    }

    /**
     * Gets the video bitrate for 240p transcodes
     *
     * @return the bitrate
     */
    public int getTwoFourZeroVideoTranscodeBitrate() {
        return SettingsEntity.twoFourZeroVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 240p transcodes
     *
     * @param twoFourZeroVideoTranscodeBitrate the bitrate
     */
    public void setTwoFourZeroVideoTranscodeBitrate(int twoFourZeroVideoTranscodeBitrate) {
        SettingsEntity.twoFourZeroVideoTranscodeBitrate = twoFourZeroVideoTranscodeBitrate;
    }

    /**
     * Gets the bitrate for 360p transcodes
     *
     * @return the bitrate
     */
    public int getThreeSixZeroVideoTranscodeBitrate() {
        return SettingsEntity.threeSixZeroVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 360p transcodes
     *
     * @param threeSixZeroVideoTranscodeBitrate the bitrate
     */
    public void setThreeSixZeroVideoTranscodeBitrate(int threeSixZeroVideoTranscodeBitrate) {
        SettingsEntity.threeSixZeroVideoTranscodeBitrate = threeSixZeroVideoTranscodeBitrate;
    }

    /**
     * Gets the bitrate for 480p transcodes
     *
     * @return the bitrate
     */
    public int getFourEightZeroVideoTranscodeBitrate() {
        return SettingsEntity.fourEightZeroVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 480p transcodes
     *
     * @param fourEightZeroVideoTranscodeBitrate the bitrate
     */
    public void setFourEightZeroVideoTranscodeBitrate(int fourEightZeroVideoTranscodeBitrate) {
        SettingsEntity.fourEightZeroVideoTranscodeBitrate = fourEightZeroVideoTranscodeBitrate;
    }

    /**
     * Gets the bitrate for 720p transcodes
     *
     * @return the bitrate
     */
    public int getSevenTwoZeroVideoTranscodeBitrate() {
        return SettingsEntity.sevenTwoZeroVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 720p transcodes
     *
     * @param sevenTwoZeroVideoTranscodeBitrate the bitrate
     */
    public void setSevenTwoZeroVideoTranscodeBitrate(int sevenTwoZeroVideoTranscodeBitrate) {
        SettingsEntity.sevenTwoZeroVideoTranscodeBitrate = sevenTwoZeroVideoTranscodeBitrate;
    }

    /**
     * Gets the bitrate for 1080p transcodes
     *
     * @return the bitrate
     */
    public int getOneZeroEightZeroVideoTranscodeBitrate() {
        return SettingsEntity.oneZeroEightZeroVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 1080p transcodes
     *
     * @param oneZeroEightZeroVideoTranscodeBitrate the bitrate
     */
    public void setOneZeroEightZeroVideoTranscodeBitrate(int oneZeroEightZeroVideoTranscodeBitrate) {
        SettingsEntity.oneZeroEightZeroVideoTranscodeBitrate = oneZeroEightZeroVideoTranscodeBitrate;
    }

    /**
     * Gets the bitrate for 2k transcodes
     *
     * @return the bitrate
     */
    public int getTwoKVideoTranscodeBitrate() {
        return SettingsEntity.twoKVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 2k transcodes
     *
     * @param twoKVideoTranscodeBitrate the bitrate
     */
    public void setTwoKVideoTranscodeBitrate(int twoKVideoTranscodeBitrate) {
        SettingsEntity.twoKVideoTranscodeBitrate = twoKVideoTranscodeBitrate;
    }

    /**
     * Gets the bitrate for 4k transcodes
     *
     * @return the bitrate
     */
    public int getFourKVideoTranscodeBitrate() {
        return SettingsEntity.fourKVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 4k transcodes
     *
     * @param fourKVideoTranscodeBitrate the bitrate
     */
    public void setFourKVideoTranscodeBitrate(int fourKVideoTranscodeBitrate) {
        SettingsEntity.fourKVideoTranscodeBitrate = fourKVideoTranscodeBitrate;
    }

    /**
     * Gets the bitrate for 4k transcodes
     *
     * @return the bitrate
     */
    public int getEightKVideoTranscodeBitrate() {
        return SettingsEntity.eightKVideoTranscodeBitrate;
    }

    /**
     * Sets the bitrate for 4k transcodes
     *
     * @param eightKVideoTranscodeBitrate the bitrate
     */
    public void setEightKVideoTranscodeBitrate(int eightKVideoTranscodeBitrate) {
        SettingsEntity.eightKVideoTranscodeBitrate = eightKVideoTranscodeBitrate;
    }

    /**
     * Whether to show the posters in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowPoster(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowPoster();
        }
        return SettingsEntity.tableShowPoster;
    }

    /**
     * Whether to show the names in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowName(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowName();
        }
        return SettingsEntity.tableShowName;
    }

    /**
     * Whether to show run time in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowRuntime(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowRuntime();
        }
        return SettingsEntity.tableShowRuntime;
    }

    /**
     * Whether to show the genres in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowGenre(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowGenre();
        }
        return SettingsEntity.tableShowGenre;
    }

    /**
     * Whether to show the MPAA ratings in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowMpaaRating(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowMpaaRating();
        }
        return SettingsEntity.tableShowMpaaRating;
    }

    /**
     * Whether to show the user ratings in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowUserRating(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowUserRating();
        }
        return SettingsEntity.tableShowUserRating;
    }

    /**
     * Whether to show languages in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowLanguage(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowLanguage();
        }
        return SettingsEntity.tableShowLanguage;
    }

    /**
     * Whether to show the release dates in the table view
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isTableShowReleaseDate(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowReleaseDate();
        }
        return SettingsEntity.tableShowReleaseDate;
    }

    /**
     * Whether to show actions in the table view
     *
     * @param userSettingsEntity the users settings
     * @return the users preference
     */
    public boolean isTableShowActions(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isTableShowActions();
        }
        return SettingsEntity.tableShowActions;
    }

    /**
     * Whether the book library is enabled or not
     *
     * @return true if it is
     */
    public boolean isBookLibraryEnable() {
        return SettingsEntity.bookLibraryEnable;
    }

    /**
     * Whether the book library is enabled
     *
     * @param bookLibraryEnable true if it is
     */
    public void setBookLibraryEnable(boolean bookLibraryEnable) {
        SettingsEntity.bookLibraryEnable = bookLibraryEnable;
    }

    /**
     * Gets the path to the book library
     *
     * @return the path
     */
    public String getBookLibraryPath() {
        return SettingsEntity.bookLibraryPath;
    }

    /**
     * Sets the path to the book library
     *
     * @param bookLibraryPath the path
     */
    public void setBookLibraryPath(String bookLibraryPath) {
        SettingsEntity.bookLibraryPath = bookLibraryPath;
    }

    /**
     * Whether to routinely scan the book library for new entities
     *
     * @return true if yes
     */
    public boolean isBookScanEnable() {
        return SettingsEntity.bookScanEnable;
    }

    /**
     * Sets whether the book library should be scanned
     *
     * @param bookScanEnable true if yes
     */
    public void setBookScanEnable(boolean bookScanEnable) {
        SettingsEntity.bookScanEnable = bookScanEnable;
    }

    /**
     * Gets the frequency in which the book library will be scanned
     *
     * @return the duration between scans
     */
    public int getBookScanFrequencyTime() {
        return SettingsEntity.bookScanFrequencyTime;
    }

    /**
     * Sets the book scan frequency time
     *
     * @param bookScanFrequencyTime the duration between scans
     */
    public void setBookScanFrequencyTime(int bookScanFrequencyTime) {
        SettingsEntity.bookScanFrequencyTime = bookScanFrequencyTime;
    }

    /**
     * Gets the book scan frequency type (seconds, minutes, etc)
     *
     * @return the scan frequency
     */
    public ScanFrequencyEnum getBookScanFrequencyType() {
        return SettingsEntity.bookScanFrequencyType;
    }

    /**
     * Sets the book scan frequency type (seconds, minutes, etc)
     *
     * @param bookScanFrequencyType the frequency type
     */
    public void setBookScanFrequencyType(ScanFrequencyEnum bookScanFrequencyType) {
        SettingsEntity.bookScanFrequencyType = bookScanFrequencyType;
    }

    /**
     * Whether the game library is enabled
     *
     * @return true if it is
     */
    public boolean isGameLibraryEnable() {
        return SettingsEntity.gameLibraryEnable;
    }

    /**
     * Sets whether the game library is enabled
     *
     * @param gameLibraryEnable true if it is
     */
    public void setGameLibraryEnable(boolean gameLibraryEnable) {
        SettingsEntity.gameLibraryEnable = gameLibraryEnable;
    }

    /**
     * Gets the game library path
     *
     * @return the path
     */
    public String getGameLibraryPath() {
        return SettingsEntity.gameLibraryPath;
    }

    /**
     * Sets the game library path
     *
     * @param gameLibraryPath the path
     */
    public void setGameLibraryPath(String gameLibraryPath) {
        SettingsEntity.gameLibraryPath = gameLibraryPath;
    }

    /**
     * Whether to scan the game library for new files or not
     *
     * @return true if yes
     */
    public boolean isGameScanEnable() {
        return SettingsEntity.gameScanEnable;
    }

    /**
     * Sets whether to scan the game library for new files
     *
     * @param gameScanEnable true if yes
     */
    public void setGameScanEnable(boolean gameScanEnable) {
        SettingsEntity.gameScanEnable = gameScanEnable;
    }

    /**
     * Gets the game scan frequency
     *
     * @return the duration
     */
    public int getGameScanFrequencyTime() {
        return SettingsEntity.gameScanFrequencyTime;
    }

    /**
     * Sets the gmae scan frequency
     *
     * @param gameScanFrequencyTime the duration
     */
    public void setGameScanFrequencyTime(int gameScanFrequencyTime) {
        SettingsEntity.gameScanFrequencyTime = gameScanFrequencyTime;
    }

    /**
     * Gets the game scan frequency type
     *
     * @return the frequency type (seconds, minutes, etc)
     */
    public ScanFrequencyEnum getGameScanFrequencyType() {
        return SettingsEntity.gameScanFrequencyType;
    }

    /**
     * Sets the game scan frequency type
     *
     * @param gameScanFrequencyType the frequency type (seconds, minutes, etc)
     */
    public void setGameScanFrequencyType(ScanFrequencyEnum gameScanFrequencyType) {
        SettingsEntity.gameScanFrequencyType = gameScanFrequencyType;
    }

    /**
     * Gets whether the movie library is enabled
     *
     * @return true if yes
     */
    public boolean isMovieLibraryEnable() {
        return SettingsEntity.movieLibraryEnable;
    }

    /**
     * Sets whether the movie library is enabled
     *
     * @param movieLibraryEnable true if yes
     */
    public void setMovieLibraryEnable(boolean movieLibraryEnable) {
        SettingsEntity.movieLibraryEnable = movieLibraryEnable;
    }

    /**
     * Gets the movie library path
     *
     * @return the path
     */
    public String getMovieLibraryPath() {
        return SettingsEntity.movieLibraryPath;
    }

    /**
     * Sets the movie library path
     *
     * @param movieLibraryPath the library path
     */
    public void setMovieLibraryPath(String movieLibraryPath) {
        SettingsEntity.movieLibraryPath = movieLibraryPath;
    }

    /**
     * Gets whether the movie library scanning is enabled
     *
     * @return true if yes
     */
    public boolean isMovieScanEnable() {
        return SettingsEntity.movieScanEnable;
    }

    /**
     * Sets the movie scan function
     *
     * @param movieScanEnable enabled if true
     */
    public void setMovieScanEnable(boolean movieScanEnable) {
        SettingsEntity.movieScanEnable = movieScanEnable;
    }

    /**
     * Gets whether movie pretranscoding is enabled
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscodeEnable() {
        return SettingsEntity.moviePreTranscodeEnable;
    }

    /**
     * Sets whether movies should be pretranscoded
     *
     * @param moviePreTranscodeEnable true if yes
     */
    public void setMoviePreTranscodeEnable(boolean moviePreTranscodeEnable) {
        SettingsEntity.moviePreTranscodeEnable = moviePreTranscodeEnable;
    }

    /**
     * Gets whether movies should be pretranscoded for 144p
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode144p() {
        return SettingsEntity.moviePreTranscode144p;
    }

    /**
     * Sets whether movies should be pretranscoded to 144p
     *
     * @param moviePreTranscode144p true if yes
     */
    public void setMoviePreTranscode144p(boolean moviePreTranscode144p) {
        SettingsEntity.moviePreTranscode144p = moviePreTranscode144p;
    }

    /**
     * Gets whether movies should be pretranscoded to 240p
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode240p() {
        return SettingsEntity.moviePreTranscode240p;
    }

    /**
     * Sets whether movies should be pretranscoded to 240p
     *
     * @param moviePreTranscode240p true if yes
     */
    public void setMoviePreTranscode240p(boolean moviePreTranscode240p) {
        SettingsEntity.moviePreTranscode240p = moviePreTranscode240p;
    }

    /**
     * Gets whether movies should be pretranscoded to 360p
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode360p() {
        return SettingsEntity.moviePreTranscode360p;
    }

    /**
     * Sets whether movies should be pretranscoded to 360p
     *
     * @param moviePreTranscode360p true if yes
     */
    public void setMoviePreTranscode360p(boolean moviePreTranscode360p) {
        SettingsEntity.moviePreTranscode360p = moviePreTranscode360p;
    }

    /**
     * Gets whether movies should be pretranscoded to 480p
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode480p() {
        return SettingsEntity.moviePreTranscode480p;
    }

    /**
     * Sets whether movies should be pretranscoded to 480p
     *
     * @param moviePreTranscode480p true if yes
     */
    public void setMoviePreTranscode480p(boolean moviePreTranscode480p) {
        SettingsEntity.moviePreTranscode480p = moviePreTranscode480p;
    }

    /**
     * Gets whether movies should be pretranscoded to 720p
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode720p() {
        return SettingsEntity.moviePreTranscode720p;
    }

    /**
     * Sets whether movies should be pretranscoded to 720p
     *
     * @param moviePreTranscode720p true if yes
     */
    public void setMoviePreTranscode720p(boolean moviePreTranscode720p) {
        SettingsEntity.moviePreTranscode720p = moviePreTranscode720p;
    }

    /**
     * Gets whether movies should be pretranscoded to 1080p
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode1080p() {
        return SettingsEntity.moviePreTranscode1080p;
    }

    /**
     * Sets whether movies should be pretranscoded to 1080p
     *
     * @param moviePreTranscode1080p true if yes
     */
    public void setMoviePreTranscode1080p(boolean moviePreTranscode1080p) {
        SettingsEntity.moviePreTranscode1080p = moviePreTranscode1080p;
    }

    /**
     * Sets wheter movies should be pretransocded to 2k
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode2k() {
        return SettingsEntity.moviePreTranscode2k;
    }

    /**
     * Sets whether movies should be pretranscoded to 2k
     *
     * @param moviePreTranscode2k true if yes
     */
    public void setMoviePreTranscode2k(boolean moviePreTranscode2k) {
        SettingsEntity.moviePreTranscode2k = moviePreTranscode2k;
    }

    /**
     * Gets whether movies should be tretranscoded to 4k
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode4k() {
        return SettingsEntity.moviePreTranscode4k;
    }

    /**
     * Sets whether movies should be pretranscoded to 4k
     *
     * @param moviePreTranscode4k true if yes
     */
    public void setMoviePreTranscode4k(boolean moviePreTranscode4k) {
        SettingsEntity.moviePreTranscode4k = moviePreTranscode4k;
    }

    /**
     * Gets whether movies should be ptretranscoded to 8k
     *
     * @return true if yes
     */
    public boolean isMoviePreTranscode8k() {
        return SettingsEntity.moviePreTranscode8k;
    }

    /**
     * Sets whether movies should be pretranscoded to 8k
     *
     * @param moviePreTranscode8k true if yes
     */
    public void setMoviePreTranscode8k(boolean moviePreTranscode8k) {
        SettingsEntity.moviePreTranscode8k = moviePreTranscode8k;
    }

    /**
     * Gets the movie scan frequency time
     *
     * @return the duration
     */
    public int getMovieScanFrequencyTime() {
        return SettingsEntity.movieScanFrequencyTime;
    }

    /**
     * Sets the movie scan frequency time
     *
     * @param movieScanFrequencyTime the duration
     */
    public void setMovieScanFrequencyTime(int movieScanFrequencyTime) {
        SettingsEntity.movieScanFrequencyTime = movieScanFrequencyTime;
    }

    /**
     * Gets the movie scan frequency type
     *
     * @return the scan frequency
     */
    public ScanFrequencyEnum getMovieScanFrequencyType() {
        return SettingsEntity.movieScanFrequencyType;
    }

    /**
     * Sets the movie scan frequency type
     *
     * @param movieScanFrequencyType the scan frequency
     */
    public void setMovieScanFrequencyType(ScanFrequencyEnum movieScanFrequencyType) {
        SettingsEntity.movieScanFrequencyType = movieScanFrequencyType;
    }

    /**
     * Gets the movie pretranscode library path
     *
     * @return the path
     */
    public String getMoviePreTranscodeLibraryPath() {
        return SettingsEntity.moviePreTranscodeLibraryPath;
    }

    /**
     * Sets the movie library pretranscode path
     *
     * @param moviePreTranscodeLibraryPath the path
     */
    public void setMoviePreTranscodeLibraryPath(String moviePreTranscodeLibraryPath) {
        SettingsEntity.moviePreTranscodeLibraryPath = moviePreTranscodeLibraryPath;
    }

    /**
     * Gets whether the music library is enabled
     *
     * @return true if yes
     */
    public boolean isMusicLibraryEnable() {
        return SettingsEntity.musicLibraryEnable;
    }

    /**
     * Sets whether the music library is enabled
     *
     * @param musicLibraryEnable true if yes
     */
    public void setMusicLibraryEnable(boolean musicLibraryEnable) {
        SettingsEntity.musicLibraryEnable = musicLibraryEnable;
    }

    /**
     * Gets the music library path
     *
     * @return the path
     */
    public String getMusicLibraryPath() {
        return SettingsEntity.musicLibraryPath;
    }

    /**
     * Sets the music library path
     *
     * @param musicLibraryPath the path
     */
    public void setMusicLibraryPath(String musicLibraryPath) {
        SettingsEntity.musicLibraryPath = musicLibraryPath;
    }

    /**
     * Gets whether the music scan is enabled
     *
     * @return true if yes
     */
    public boolean isMusicScanEnable() {
        return SettingsEntity.musicScanEnable;
    }

    /**
     * Sets whether the music library will be scanned
     *
     * @param musicScanEnable true if yes
     */
    public void setMusicScanEnable(boolean musicScanEnable) {
        SettingsEntity.musicScanEnable = musicScanEnable;
    }

    /**
     * Gets whether music pretranscoding is enabled
     *
     * @return true if yes
     */
    public boolean isMusicPreTranscodeEnable() {
        return SettingsEntity.musicPreTranscodeEnable;
    }

    /**
     * Sets whether music will be pretranscoded
     *
     * @param musicPreTranscodeEnable true if yes
     */
    public void setMusicPreTranscodeEnable(boolean musicPreTranscodeEnable) {
        SettingsEntity.musicPreTranscodeEnable = musicPreTranscodeEnable;
    }

    /**
     * Gets whether music should be pretranscoded to 64Kbps
     *
     * @return true if yes
     */
    public boolean isMusicPreTranscode64k() {
        return SettingsEntity.musicPreTranscode64k;
    }

    /**
     * Sets whether music should be pretranscoded to 64Kbps
     *
     * @param musicPreTranscode64k true if yes
     */
    public void setMusicPreTranscode64k(boolean musicPreTranscode64k) {
        SettingsEntity.musicPreTranscode64k = musicPreTranscode64k;
    }

    /**
     * Gets whether music should be pretranscoded to 96Kbps
     *
     * @return true if yes
     */
    public boolean isMusicPreTranscode96k() {
        return SettingsEntity.musicPreTranscode96k;
    }

    /**
     * Sets whether music should be pretranscoded to 96Kbps
     *
     * @param musicPreTranscode96k true if yes
     */
    public void setMusicPreTranscode96k(boolean musicPreTranscode96k) {
        SettingsEntity.musicPreTranscode96k = musicPreTranscode96k;
    }

    /**
     * Gets whether music should be pretranscoded to 128Kbps
     *
     * @return true if yes
     */
    public boolean isMusicPreTranscode128k() {
        return SettingsEntity.musicPreTranscode128k;
    }

    /**
     * Sets whether music should be pretranscoded to 128Kbps
     *
     * @param musicPreTranscode128k true if yes
     */
    public void setMusicPreTranscode128k(boolean musicPreTranscode128k) {
        SettingsEntity.musicPreTranscode128k = musicPreTranscode128k;
    }

    /**
     * Gets whether music should be pretranscoded to 320Kbps
     *
     * @return true if yes
     */
    public boolean isMusicPreTranscode320k() {
        return SettingsEntity.musicPreTranscode320k;
    }

    /**
     * Sets whether music should be pretranscoded to 320Kbps
     *
     * @param musicPreTranscode320k true if yes
     */
    public void setMusicPreTranscode320k(boolean musicPreTranscode320k) {
        SettingsEntity.musicPreTranscode320k = musicPreTranscode320k;
    }

    /**
     * Gets whether music should be pretranscoded to 1411Kbps
     *
     * @return true if yes
     */
    public boolean isMusicPreTranscode1411k() {
        return SettingsEntity.musicPreTranscode1411k;
    }

    /**
     * Sets whether music should be pretranscoded to 1411Kbps
     *
     * @param musicPreTranscode1411k true if yes
     */
    public void setMusicPreTranscode1411k(boolean musicPreTranscode1411k) {
        SettingsEntity.musicPreTranscode1411k = musicPreTranscode1411k;
    }

    /**
     * Gets the music scan frequency
     *
     * @return the duration
     */
    public int getMusicScanFrequencyTime() {
        return SettingsEntity.musicScanFrequencyTime;
    }

    /**
     * Sets the music scan frequency
     *
     * @param musicScanFrequencyTime the duration
     */
    public void setMusicScanFrequencyTime(int musicScanFrequencyTime) {
        SettingsEntity.musicScanFrequencyTime = musicScanFrequencyTime;
    }

    /**
     * Get the music scan frequency time
     *
     * @return the scan frequency
     */
    public ScanFrequencyEnum getMusicScanFrequencyType() {
        return SettingsEntity.musicScanFrequencyType;
    }

    /**
     * Set the music scan frequency type
     *
     * @param musicScanFrequencyType the frequency type
     */
    public void setMusicScanFrequencyType(ScanFrequencyEnum musicScanFrequencyType) {
        SettingsEntity.musicScanFrequencyType = musicScanFrequencyType;
    }

    /**
     * Gets the music pretranscode path
     *
     * @return the path
     */
    public String getMusicPreTranscodeLibraryPath() {
        return SettingsEntity.musicPreTranscodeLibraryPath;
    }

    /**
     * Sets the music pretranscode library path
     *
     * @param musicPreTranscodeLibraryPath the path
     */
    public void setMusicPreTranscodeLibraryPath(String musicPreTranscodeLibraryPath) {
        SettingsEntity.musicPreTranscodeLibraryPath = musicPreTranscodeLibraryPath;
    }

    /**
     * Whether the tv show library is enabled
     *
     * @return true if yes
     */
    public boolean isTvShowLibraryEnable() {
        return SettingsEntity.tvShowLibraryEnable;
    }

    /**
     * Sets whether the tv show library should be enabled
     *
     * @param tvShowLibraryEnable true if yes
     */
    public void setTvShowLibraryEnable(boolean tvShowLibraryEnable) {
        SettingsEntity.tvShowLibraryEnable = tvShowLibraryEnable;
    }

    /**
     * Get the tv show library path
     *
     * @return the path
     */
    public String getTvShowLibraryPath() {
        return SettingsEntity.tvShowLibraryPath;
    }

    /**
     * Sets the tv show library path
     *
     * @param tvShowLibraryPath the path
     */
    public void setTvShowLibraryPath(String tvShowLibraryPath) {
        SettingsEntity.tvShowLibraryPath = tvShowLibraryPath;
    }

    /**
     * Gets whether the tv show library scan is enabled
     *
     * @return true if yes
     */
    public boolean isTvShowScanEnable() {
        return SettingsEntity.tvShowScanEnable;
    }

    /**
     * Sets whether scanning the tv show library should be enabled
     *
     * @param tvShowScanEnable true if yes
     */
    public void setTvShowScanEnable(boolean tvShowScanEnable) {
        SettingsEntity.tvShowScanEnable = tvShowScanEnable;
    }

    /**
     * Gets whether the tv show library pretrancoding is enabled
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscodeEnable() {
        return SettingsEntity.tvShowPreTranscodeEnable;
    }

    /**
     * Sets whether tv show pretranscoding is enabled
     *
     * @param tvShowPreTranscodeEnable true if yes
     */
    public void setTvShowPreTranscodeEnable(boolean tvShowPreTranscodeEnable) {
        SettingsEntity.tvShowPreTranscodeEnable = tvShowPreTranscodeEnable;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 144p
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode144p() {
        return SettingsEntity.tvShowPreTranscode144p;
    }

    /**
     * Sets whether the tv shows should be pretranscoded to 144p
     *
     * @param tvShowPreTranscode144p true if yes
     */
    public void setTvShowPreTranscode144p(boolean tvShowPreTranscode144p) {
        SettingsEntity.tvShowPreTranscode144p = tvShowPreTranscode144p;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 240p
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode240p() {
        return SettingsEntity.tvShowPreTranscode240p;
    }

    /**
     * Sets whether tv shows should be pretranscoded to 240p
     *
     * @param tvShowPreTranscode240p true if yes
     */
    public void setTvShowPreTranscode240p(boolean tvShowPreTranscode240p) {
        SettingsEntity.tvShowPreTranscode240p = tvShowPreTranscode240p;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 360p
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode360p() {
        return SettingsEntity.tvShowPreTranscode360p;
    }

    /**
     * Sets whether movies should be pretranscoded to 360p
     *
     * @param tvShowPreTranscode360p true if yes
     */
    public void setTvShowPreTranscode360p(boolean tvShowPreTranscode360p) {
        SettingsEntity.tvShowPreTranscode360p = tvShowPreTranscode360p;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 480p
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode480p() {
        return SettingsEntity.tvShowPreTranscode480p;
    }

    /**
     * Sets whether tv shows should be pretranscoded to 480p
     *
     * @param tvShowPreTranscode480p true if yes
     */
    public void setTvShowPreTranscode480p(boolean tvShowPreTranscode480p) {
        SettingsEntity.tvShowPreTranscode480p = tvShowPreTranscode480p;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 720p
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode720p() {
        return SettingsEntity.tvShowPreTranscode720p;
    }

    /**
     * Sets whether tv shows should be pretranscoded to 720p
     *
     * @param tvShowPreTranscode720p true if yes
     */
    public void setTvShowPreTranscode720p(boolean tvShowPreTranscode720p) {
        SettingsEntity.tvShowPreTranscode720p = tvShowPreTranscode720p;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 1080p
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode1080p() {
        return SettingsEntity.tvShowPreTranscode1080p;
    }

    /**
     * Sets whether tv shows should be pretranscoded to 1080p
     *
     * @param tvShowPreTranscode1080p true if yes
     */
    public void setTvShowPreTranscode1080p(boolean tvShowPreTranscode1080p) {
        SettingsEntity.tvShowPreTranscode1080p = tvShowPreTranscode1080p;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 2k
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode2k() {
        return SettingsEntity.tvShowPreTranscode2k;
    }

    /**
     * Sets whether tv shows should be pretranscoded to 2k
     *
     * @param tvShowPreTranscode2k true if yes
     */
    public void setTvShowPreTranscode2k(boolean tvShowPreTranscode2k) {
        SettingsEntity.tvShowPreTranscode2k = tvShowPreTranscode2k;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 4k
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode4k() {
        return SettingsEntity.tvShowPreTranscode4k;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 4k
     *
     * @param tvShowPreTranscode4k true if yes
     */
    public void setTvShowPreTranscode4k(boolean tvShowPreTranscode4k) {
        SettingsEntity.tvShowPreTranscode4k = tvShowPreTranscode4k;
    }

    /**
     * Gets whether tv shows should be pretranscoded to 8k
     *
     * @return true if yes
     */
    public boolean isTvShowPreTranscode8k() {
        return SettingsEntity.tvShowPreTranscode8k;
    }

    /**
     * Sets whether tv shows should be pretranscoded to 8k
     *
     * @param tvShowPreTranscode8k true if yes
     */
    public void setTvShowPreTranscode8k(boolean tvShowPreTranscode8k) {
        SettingsEntity.tvShowPreTranscode8k = tvShowPreTranscode8k;
    }

    /**
     * Gets the tv show library scan frequency time
     *
     * @return the duration
     */
    public int getTvShowScanFrequencyTime() {
        return SettingsEntity.tvShowScanFrequencyTime;
    }

    /**
     * Sets the tv show scan frequency time
     *
     * @param tvShowScanFrequencyTime the duration
     */
    public void setTvShowScanFrequencyTime(int tvShowScanFrequencyTime) {
        SettingsEntity.tvShowScanFrequencyTime = tvShowScanFrequencyTime;
    }

    /**
     * Gets the tv show scan frequency type
     *
     * @return the duration
     */
    public ScanFrequencyEnum getTvShowScanFrequencyType() {
        return SettingsEntity.tvShowScanFrequencyType;
    }

    /**
     * Sets the tv show scan frequency type
     *
     * @param tvShowScanFrequencyType the type (minutes, hours, etc)
     */
    public void setTvShowScanFrequencyType(ScanFrequencyEnum tvShowScanFrequencyType) {
        SettingsEntity.tvShowScanFrequencyType = tvShowScanFrequencyType;
    }

    /**
     * Gets the tv show pretranscode library path
     *
     * @return the path
     */
    public String getTvShowPreTranscodeLibraryPath() {
        return SettingsEntity.tvShowPreTranscodeLibraryPath;
    }

    /**
     * Sets the tv show pretranscode library path
     *
     * @param tvShowPreTranscodeLibraryPath the path
     */
    public void setTvShowPreTranscodeLibraryPath(String tvShowPreTranscodeLibraryPath) {
        SettingsEntity.tvShowPreTranscodeLibraryPath = tvShowPreTranscodeLibraryPath;
    }

    /**
     * Gets the server name
     *
     * @return the server name
     */
    public String getServerName() {
        return SettingsEntity.serverName;
    }

    /**
     * Sets the server name
     *
     * @param serverName the server name
     */
    public void setServerName(String serverName) {
        SettingsEntity.serverName = serverName;
    }

    /**
     * Gets whether users are allow to register
     *
     * @return true if yes
     */
    public boolean isAllowRegistration() {
        return SettingsEntity.allowRegistration;
    }

    /**
     * Sets whether users are allowed to register
     *
     * @param allowRegistration true if yes
     */
    public void setAllowRegistration(boolean allowRegistration) {
        SettingsEntity.allowRegistration = allowRegistration;
    }

    /**
     * Gets whether new movies should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowNewMovie(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowNewMovie();
        }
        return SettingsEntity.homePageShowNewMovie;
    }

    /**
     * Gets whether new tv shows should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowNewTvShow(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowNewTvShow();
        }
        return SettingsEntity.homePageShowNewTvShow;
    }

    /**
     * Gets whether new games should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowNewGame(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowNewGame();
        }
        return SettingsEntity.homePageShowNewGame;
    }

    /**
     * Gets whether new books should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowNewBook(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowNewBook();
        }
        return SettingsEntity.homePageShowNewBook;
    }

    /**
     * Gets whether new music should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowNewMusic(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowNewMusic();
        }
        return SettingsEntity.homePageShowNewMusic;
    }

    /**
     * Gets whether popular movies should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowPopularMovie(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowPopularMovie();
        }
        return SettingsEntity.homePageShowPopularMovie;
    }

    /**
     * Gets whether popular tv shows should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowPopularTvShow(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowPopularTvShow();
        }
        return SettingsEntity.homePageShowPopularTvShow;
    }

    /**
     * Gets whether popular games should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowPopularGame(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowPopularGame();
        }
        return SettingsEntity.homePageShowPopularGame;
    }

    /**
     * Gets whether popular books should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowPopularBook(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowPopularBook();
        }
        return SettingsEntity.homePageShowPopularBook;
    }

    /**
     * Gets whether popular music should be displayed on the home page
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public boolean isHomePageShowPopularMusic(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isHomePageShowPopularMusic();
        }
        return SettingsEntity.homePageShowPopularMusic;
    }

    /**
     * Gets the search method (database, search server)
     *
     * @return the method
     */
    public SearchMethodEnum getSearchMethod() {
        return SettingsEntity.searchMethod;
    }

    /**
     * Sets the search method (database, search server)
     *
     * @param searchMethod the method
     */
    public void setSearchMethod(SearchMethodEnum searchMethod) {
        SettingsEntity.searchMethod = searchMethod;
    }

    /**
     * Gets the maximum number of entities to return when searching
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public int getMaxSearchResults(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.getMaxSearchResults();
        }
        return SettingsEntity.maxSearchResults;
    }

    /**
     * Gets the maximum number of entities to return when browsing
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public int getMaxBrowseResults(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.getMaxBrowseResults();
        }
        return SettingsEntity.maxUIBrowseResults;
    }

    /**
     * Gets the width of the cards in pixels
     *
     * @param userSettingsEntity the users settings entity
     * @return the users preference
     */
    public int getCardWidth(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.getCardWidth();
        }
        return SettingsEntity.cardWidth;
    }

    /**
     * Sets whether the top menu should be stuck to the top of the screen or not
     *
     * @param userSettingsEntity the users settings entity
     * @return true if yes
     */
    public boolean isStickyTopMenu(UserSettingsEntity userSettingsEntity) {
        if (userSettingsEntity != null) {
            return userSettingsEntity.isStickyTopMenu();
        }
        return SettingsEntity.stickyTopMenu;
    }

    /**
     * Gets whether caching should be enabled
     *
     * @return true if yes
     */
    public boolean isCacheEnable() {
        return SettingsEntity.cacheEnable;
    }

    /**
     * Sets whether caching should be enabled
     *
     * @param cacheEnable true if yes
     */
    public void setCacheEnable(boolean cacheEnable) {
        SettingsEntity.cacheEnable = cacheEnable;
    }

    /**
     * Gets the maximum asset cache duration
     *
     * @return the max age in seconds
     */
    public int getMaxAssetCacheAge() {
        return SettingsEntity.maxAssetCacheAge;
    }

    /**
     * Sets the maximum asset cache duration
     *
     * @param maxAssetCacheAge the max age in seconds
     */
    public void setMaxAssetCacheAge(int maxAssetCacheAge) {
        SettingsEntity.maxAssetCacheAge = maxAssetCacheAge;
    }

    /**
     * Sets the interface method
     *
     * @param interfaceMethod the method
     */
    public void setInterfaceMethod(InterfaceMethodEnum interfaceMethod) {
        SettingsEntity.interfaceMethod = interfaceMethod;
    }

    /**
     * Sets the default playback quality
     *
     * @param defaultPlaybackQuality the quality
     */
    public void setDefaultPlaybackQuality(PlaybackQualityEnum defaultPlaybackQuality) {
        SettingsEntity.defaultPlaybackQuality = defaultPlaybackQuality;
    }

    /**
     * Sets the display mode
     *
     * @param displayMode the display mode
     */
    public void setDisplayMode(DisplayModeEnum displayMode) {
        SettingsEntity.displayMode = displayMode;
    }

    /**
     * Sets whether poster should be displayed in the table view
     *
     * @param tableShowPoster true if yes
     */
    public void setTableShowPoster(boolean tableShowPoster) {
        SettingsEntity.tableShowPoster = tableShowPoster;
    }

    /**
     * Sets whether names should be displayed in the table view
     *
     * @param tableShowName true if yes
     */
    public void setTableShowName(boolean tableShowName) {
        SettingsEntity.tableShowName = tableShowName;
    }

    /**
     * Sets whether run time should be displayed in the table view
     *
     * @param tableShowRuntime true if yes
     */
    public void setTableShowRuntime(boolean tableShowRuntime) {
        SettingsEntity.tableShowRuntime = tableShowRuntime;
    }

    /**
     * Sets whether genres should be displayed in the table view
     *
     * @param tableShowGenre true if yes
     */
    public void setTableShowGenre(boolean tableShowGenre) {
        SettingsEntity.tableShowGenre = tableShowGenre;
    }

    /**
     * Sets whether mpaa ratings should be displayed in the table view
     *
     * @param tableShowMpaaRating true if yes
     */
    public void setTableShowMpaaRating(boolean tableShowMpaaRating) {
        SettingsEntity.tableShowMpaaRating = tableShowMpaaRating;
    }

    /**
     * Sets whether user ratings should be displayed in the table view
     *
     * @param tableShowUserRating true if yes
     */
    public void setTableShowUserRating(boolean tableShowUserRating) {
        SettingsEntity.tableShowUserRating = tableShowUserRating;
    }

    /**
     * Sets whether languages should be displayed in the table view
     *
     * @param tableShowLanguage true if yes
     */
    public void setTableShowLanguage(boolean tableShowLanguage) {
        SettingsEntity.tableShowLanguage = tableShowLanguage;
    }

    /**
     * Sets whether release dates should be displayed in the table view
     *
     * @param tableShowReleaseDate true if yes
     */
    public void setTableShowReleaseDate(boolean tableShowReleaseDate) {
        SettingsEntity.tableShowReleaseDate = tableShowReleaseDate;
    }

    /**
     * Sets whether actions should be displayed in the table view
     *
     * @param tableShowActions true if yes
     */
    public void setTableShowActions(boolean tableShowActions) {
        SettingsEntity.tableShowActions = tableShowActions;
    }

    /**
     * Sets whether new movies should be displayed on the home page
     *
     * @param homePageShowNewMovie true if yes
     */
    public void setHomePageShowNewMovie(boolean homePageShowNewMovie) {
        SettingsEntity.homePageShowNewMovie = homePageShowNewMovie;
    }

    /**
     * Sets whether new tv shows should be displayed on the home page
     *
     * @param homePageShowNewTvShow true if yes
     */
    public void setHomePageShowNewTvShow(boolean homePageShowNewTvShow) {
        SettingsEntity.homePageShowNewTvShow = homePageShowNewTvShow;
    }

    /**
     * Sets whether new games should be displayed on the home page
     *
     * @param homePageShowNewGame true if yes
     */
    public void setHomePageShowNewGame(boolean homePageShowNewGame) {
        SettingsEntity.homePageShowNewGame = homePageShowNewGame;
    }

    /**
     * Sets whether new books should be displayed on the home page
     *
     * @param homePageShowNewBook true if yes
     */
    public void setHomePageShowNewBook(boolean homePageShowNewBook) {
        SettingsEntity.homePageShowNewBook = homePageShowNewBook;
    }

    /**
     * Sets whether new music should be displayed on the home page
     *
     * @param homePageShowNewMusic true if yes
     */
    public void setHomePageShowNewMusic(boolean homePageShowNewMusic) {
        SettingsEntity.homePageShowNewMusic = homePageShowNewMusic;
    }

    /**
     * Sets whether popular movies should be displayed on the home page
     *
     * @param homePageShowPopularMovie true if yes
     */
    public void setHomePageShowPopularMovie(boolean homePageShowPopularMovie) {
        SettingsEntity.homePageShowPopularMovie = homePageShowPopularMovie;
    }

    /**
     * Sets whether popular tv shows should be displayed on the home page
     *
     * @param homePageShowPopularTvShow true if yes
     */
    public void setHomePageShowPopularTvShow(boolean homePageShowPopularTvShow) {
        SettingsEntity.homePageShowPopularTvShow = homePageShowPopularTvShow;
    }

    /**
     * Sets whether popular games should be displayed on the home page
     *
     * @param homePageShowPopularGame true if yes
     */
    public void setHomePageShowPopularGame(boolean homePageShowPopularGame) {
        SettingsEntity.homePageShowPopularGame = homePageShowPopularGame;
    }

    /**
     * Sets whether popular books should be displayed on the home page
     *
     * @param homePageShowPopularBook true if yes
     */
    public void setHomePageShowPopularBook(boolean homePageShowPopularBook) {
        SettingsEntity.homePageShowPopularBook = homePageShowPopularBook;
    }

    /**
     * Sets whether popular music should be displayed on the home page
     *
     * @param homePageShowPopularMusic true if yes
     */
    public void setHomePageShowPopularMusic(boolean homePageShowPopularMusic) {
        SettingsEntity.homePageShowPopularMusic = homePageShowPopularMusic;
    }

    /**
     * Sets the maximum number of entities to be returned when searching
     *
     * @param maxSearchResults the max number
     */
    public void setMaxSearchResults(int maxSearchResults) {
        SettingsEntity.maxSearchResults = maxSearchResults;
    }

    /**
     * Sets the maximum number of entities to be returned when browsing in the UI
     *
     * @param maxUIBrowseResults the max ui browse number
     */
    public void setMaxUIBrowseResults(int maxUIBrowseResults) {
        SettingsEntity.maxUIBrowseResults = maxUIBrowseResults;
    }

    /**
     * Sets the maximum number of entities to be returned when browsing with the API
     *
     * @param maxAPIBrowseResults the max api browse number
     */
    public void setMaxAPIBrowseResults(int maxAPIBrowseResults) {
        SettingsEntity.maxAPIBrowseResults = maxAPIBrowseResults;
    }

    /**
     * Sets the card width in pixels
     *
     * @param cardWidth the width in pixels
     */
    public void setCardWidth(int cardWidth) {
        SettingsEntity.cardWidth = cardWidth;
    }

    /**
     * Sets whether the top menu should sticky to the top of the screen
     *
     * @param stickyTopMenu true if yes
     */
    public void setStickyTopMenu(boolean stickyTopMenu) {
        SettingsEntity.stickyTopMenu = stickyTopMenu;
    }

    /**
     * Gets the database name
     *
     * @return the database name
     */
    public String getDatabaseName() {
        return SettingsEntity.databaseName;
    }

    /**
     * Sets the database name
     *
     * @param databaseName the name of the database
     */
    public void setDatabaseName(String databaseName) {
        SettingsEntity.databaseName = databaseName;
    }

    /**
     * Gets the minimum number of database connections
     *
     * @return the number of connections
     */
    public int getMinDatabaseConnections() {
        return SettingsEntity.minDatabaseConnections;
    }

    /**
     * Sets the minimum number of database connections
     *
     * @param minDatabaseConnections the number of connections
     */
    public void setMinDatabaseConnections(int minDatabaseConnections) {
        SettingsEntity.minDatabaseConnections = minDatabaseConnections;
    }

    /**
     * Gets the maximum number of connections
     *
     * @return the number of connections
     */
    public int getMaxDatabaseConnections() {
        return SettingsEntity.maxDatabaseConnections;
    }

    /**
     * Sets the maximum number of database connections
     *
     * @param maxDatabaseConnections the number of connections
     */
    public void setMaxDatabaseConnections(int maxDatabaseConnections) {
        SettingsEntity.maxDatabaseConnections = maxDatabaseConnections;
    }

    /**
     * Gets whether logging is enabled
     *
     * @return true if yes
     */
    public boolean isLoggingEnabled() {
        return SettingsEntity.loggingEnabled;
    }

    /**
     * Sets whether logging is enabled
     *
     * @param loggingEnabled true if yes
     */
    public void setLoggingEnabled(boolean loggingEnabled) {
        SettingsEntity.loggingEnabled = loggingEnabled;
    }

    /**
     * Gets the base library path where all libraries are kept
     *
     * @return the path
     */
    public String getBaseLibraryPath() {
        return SettingsEntity.baseLibraryPath;
    }

    /**
     * Sets the base library path
     *
     * @param baseLibraryPath the path
     */
    public void setBaseLibraryPath(String baseLibraryPath) {
        SettingsEntity.baseLibraryPath = baseLibraryPath;
    }

    /**
     * Sets the entity packages
     *
     * @param entityPackages the entity packages
     */
    public void setEntityPackage(String[] entityPackages) {
        SettingsEntity.entityPackages = entityPackages;
    }

    /**
     * Gets the entity packages
     *
     * @return the packages
     */
    public String[] getEntityPackages() {
        return SettingsEntity.entityPackages;
    }

    /**
     * Gets whether history is enabled. Nothing is deleted when enabled, but moved to a %table%_history table
     *
     * @return true if enabled
     */
    public boolean isEnableHistory() {
        return SettingsEntity.enableHistory;
    }

    /**
     * Sets whether history is enabled. Nothing is deleted when enabled, but moved to a %table%_history table
     *
     * @param enableHistory true if enabled
     */
    public void setEnableHistory(boolean enableHistory) {
        SettingsEntity.enableHistory = enableHistory;
    }

    /**
     * Gets whether uploading files in enabled
     *
     * @return true if yes
     */
    public boolean isEnableUpload() {
        return SettingsEntity.enableUpload;
    }

    /**
     * Sets whether uploading is enabled
     *
     * @param enableUpload true if yes
     */
    public void setEnableUpload(boolean enableUpload) {
        SettingsEntity.enableUpload = enableUpload;
    }

    /**
     * Gets the path where profile photos are kept
     *
     * @return the path
     */
    public String getProfilePhotoFolder() {
        return SettingsEntity.profilePhotoFolder;
    }

    /**
     * Sets the path of the profile photo library
     *
     * @param profilePhotoFolder the path
     */
    public void setProfilePhotoFolder(String profilePhotoFolder) {
        SettingsEntity.profilePhotoFolder = profilePhotoFolder;
    }

    /**
     * Gets the HTTP 206 chunk size
     *
     * @return the size
     */
    public int getChunk() {
        return SettingsEntity.chunk;
    }

    /**
     * Sets the HTTP 206 chunk size
     *
     * @param chunk the size
     */
    public void setChunk(int chunk) {
        SettingsEntity.chunk = chunk;
    }

    /**
     * Gets the type of database being used
     *
     * @return the database type
     */
    public DatabaseTypeEnum getDatabaseType() {
        return SettingsEntity.getDatabaseType();
    }

    /**
     * Sets the type of database to use
     *
     * @param databaseType the database type
     */
    public void setDatabaseType(DatabaseTypeEnum databaseType) {
        SettingsEntity.databaseType = databaseType;
    }
}

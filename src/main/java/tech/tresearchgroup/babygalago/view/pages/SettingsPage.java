package tech.tresearchgroup.babygalago.view.pages;

import j2html.tags.DomContent;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.babygalago.controller.controllers.NotificationEntityController;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.babygalago.view.components.HeadComponent;
import tech.tresearchgroup.babygalago.view.components.SideBarComponent;
import tech.tresearchgroup.babygalago.view.components.TopBarComponent;
import tech.tresearchgroup.palila.controller.components.*;
import tech.tresearchgroup.palila.model.EnumValuePair;
import tech.tresearchgroup.palila.model.enums.CompressionMethodEnum;
import tech.tresearchgroup.palila.model.enums.PermissionGroupEnum;
import tech.tresearchgroup.palila.model.enums.PlaybackQualityEnum;
import tech.tresearchgroup.palila.model.enums.SearchMethodEnum;
import tech.tresearchgroup.schemas.galago.entities.ExtendedUserEntity;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;
import tech.tresearchgroup.schemas.galago.enums.*;

import java.sql.SQLException;
import java.util.List;

import static j2html.TagCreator.*;

@AllArgsConstructor
public class SettingsPage {
    private final NotificationEntityController notificationEntityController;
    private final SettingsController settingsController;

    public byte @NotNull [] render(boolean loggedIn, ExtendedUserEntity userEntity) throws SQLException {
        PermissionGroupEnum permissionGroupEnum = PermissionGroupEnum.ALL;
        if (userEntity != null) {
            permissionGroupEnum = userEntity.getPermissionGroup();
        }
        return document(
            html(
                HeadComponent.render(SettingsEntity.serverName),
                TopBarComponent.render(notificationEntityController.getNumberOfUnread(userEntity), QueueEntityController.getQueueSize(), loggedIn, permissionGroupEnum, settingsController.isEnableUpload()),
                SideBarComponent.render(loggedIn,
                    SettingsEntity.movieLibraryEnable,
                    SettingsEntity.tvShowLibraryEnable,
                    SettingsEntity.gameLibraryEnable,
                    SettingsEntity.musicLibraryEnable,
                    SettingsEntity.bookLibraryEnable),
                body(
                    div(
                        label(SettingsEntity.serverName + " Server Settings").withClass("overviewLabel"),
                        br(),
                        br(),
                        form(
                            div(
                                AccordionComponent.render("accordion-1", "General", generalSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-2", "View", viewSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-3", "Encoder", encodingSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-4", "Cache", cacheSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-5", "Movie", movieSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-6", "Tv Show", tvShowSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-7", "Game", gameSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-8", "Music", musicSection()),
                                div().withClass("settings-divider"),
                                AccordionComponent.render("accordion-9", "Book", bookSection())
                            ),
                            br(),
                            button("Save").withType("submit")
                        ).withAction("/settings").withMethod("POST"),
                        br(),
                        br()
                    ).withClass("body")
                )
            )
        ).getBytes();
    }

    private DomContent generalSection() {
        return html(
            DropDownBoxComponent.render(
                "Interface method: ",
                "interfaceNetworkUsage",
                SettingsEntity.interfaceMethod,
                List.of(
                    new EnumValuePair(InterfaceMethodEnum.MODAL, "Modal (high)"),
                    new EnumValuePair(InterfaceMethodEnum.REDIRECT, "Redirect (low)")
                )
            ),
            PopoverComponent.renderRight("Using modals requires the server to send a lot more code, whereas redirects don't (useful for slow connections)"),
            br(),
            DropDownBoxComponent.render(
                "Default playback quality: ",
                "defaultPlaybackQuality",
                SettingsEntity.defaultPlaybackQuality,
                List.of(new EnumValuePair(PlaybackQualityEnum.oneFourFourP, "144P"),
                    new EnumValuePair(PlaybackQualityEnum.twoFourZeroP, "240P"),
                    new EnumValuePair(PlaybackQualityEnum.threeSixZeroP, "360P"),
                    new EnumValuePair(PlaybackQualityEnum.fourEightZeroP, "480P"),
                    new EnumValuePair(PlaybackQualityEnum.sevenTwoZeroP, "720P"),
                    new EnumValuePair(PlaybackQualityEnum.oneZeroEightZeroP, "1080P"),
                    new EnumValuePair(PlaybackQualityEnum.twoK, "2K"),
                    new EnumValuePair(PlaybackQualityEnum.fourK, "4K"),
                    new EnumValuePair(PlaybackQualityEnum.eightK, "8K"),
                    new EnumValuePair(PlaybackQualityEnum.ORIGINAL, "Original")
                )
            ),
            PopoverComponent.renderRight("When you visit an item to play it, the player will automatically run at this quality"),
            br(),
            CheckboxComponent.render("debugEnabled", "Debug enabled: ", SettingsEntity.debug),
            PopoverComponent.renderRight("Whether to output errors to the server terminal"),
            br(),
            CheckboxComponent.render("maintenanceMode", "Maintenance mode enabled: ", SettingsEntity.maintenanceMode),
            PopoverComponent.renderRight("When enabled, this immediately redirects all users to a page stating the site is under construction. Useful for when you need to do maintenance, but don't want to stop the software."),
            br(),
            CheckboxComponent.render("securityEnabled", "Security enabled: ", SettingsEntity.enableSecurity),
            PopoverComponent.renderRight("Whether to use logins, or ignore security altogether (WARNING: this will allow anyone visiting the page to do anything that normal users could)"),
            br(),
            DropDownBoxComponent.render(
                "Compression method: ",
                "compressionMethod",
                SettingsEntity.compressionMethod,
                List.of(
                    new EnumValuePair(CompressionMethodEnum.GZIP, "GZip"),
                    new EnumValuePair(CompressionMethodEnum.BR, "Brotli")
                )
            ),
            PopoverComponent.renderRight("GZip for HTTP connections, Brotli for HTTPS (brotli is a better compression, though Mac A1 chips are currently unsupported)"),
            br(),
            InputBoxComponent.render("securityIssuer", "Security issuer: ", SettingsEntity.issuer),
            PopoverComponent.renderRight("A string that you should keep secret as it's used for security"),
            br(),
            InputBoxComponent.render("securitySecretKey", "Security secret key: ", SettingsEntity.secretKey),
            PopoverComponent.renderRight("WARNING: KEEP THIS SECRET! If someone gets a hold of this, they can make API keys"),
            br(),
            InputBoxComponent.render("searchHost", "Search host: ", SettingsEntity.searchHost),
            PopoverComponent.renderRight("The IP address or domain of the search server"),
            br(),
            InputBoxComponent.render("searchKey", "Search key: ", SettingsEntity.searchKey),
            PopoverComponent.renderRight("The key used to access the search server"),
            br(),
            InputBoxComponent.render("serverName", "Server name: ", SettingsEntity.serverName),
            PopoverComponent.renderRight("The name of the server"),
            br(),
            label(
                input().withId("fileToUpload").withName("fileToUpload").withType("file"),
                input().withName("submit").withType("submit").withValue("Upload")
            ).withText("Favicon:"),
            PopoverComponent.renderRight("The icon next to the URL in the browser"),
            br(),
            CheckboxComponent.render("allowRegistration", "Allow registration: ", SettingsEntity.allowRegistration),
            PopoverComponent.renderRight("Allow new accounts to be created")
        );
    }

    private DomContent viewSection() {
        return html(
            DropDownBoxComponent.render(
                "Display mode: ",
                "displayMode",
                SettingsEntity.displayMode,
                List.of(
                    new EnumValuePair(DisplayModeEnum.POSTER, "Poster"),
                    new EnumValuePair(DisplayModeEnum.TABLE, "Table")
                )
            ),
            PopoverComponent.renderRight("The default is poster, table provides a spreadsheet like page"),
            br(),
            label("Home page:").withClass("topicLabel"),
            PopoverComponent.renderRight("What to show on the home page"),
            br(),
            br(),
            label("New:").withClass("subLabel"),
            br(),
            CheckboxComponent.render("showNewBooks", "Books: ", SettingsEntity.homePageShowNewBook),
            CheckboxComponent.render("showNewGames", "Games: ", SettingsEntity.homePageShowNewGame),
            CheckboxComponent.render("showNewMovies", "Movies: ", SettingsEntity.homePageShowNewMovie),
            CheckboxComponent.render("showNewMusic", "Music: ", SettingsEntity.homePageShowNewMusic),
            CheckboxComponent.render("showNewTvShows", "TV Shows: ", SettingsEntity.homePageShowNewTvShow),
            br(),
            label("Popular:").withClass("subLabel"),
            br(),
            CheckboxComponent.render("showPopularBooks", "Books: ", SettingsEntity.homePageShowPopularBook),
            CheckboxComponent.render("showPopularGames", "Games: ", SettingsEntity.homePageShowPopularGame),
            CheckboxComponent.render("showPopularMovies", "Movies: ", SettingsEntity.homePageShowPopularMovie),
            CheckboxComponent.render("showPopularMusic", "Music: ", SettingsEntity.homePageShowPopularMusic),
            CheckboxComponent.render("showPopularTvShows", "TV Shows: ", SettingsEntity.homePageShowPopularTvShow),
            br(),
            label("Search:").withClass("topicLabel"),
            br(),
            br(),
            DropDownBoxComponent.render(
                "Search method: ",
                "searchMethod",
                SettingsEntity.searchMethod,
                List.of(
                    new EnumValuePair(SearchMethodEnum.DATABASE_SEARCH, "Database search"),
                    new EnumValuePair(SearchMethodEnum.SEARCH_SERVER, "Search server")
                )
            ),
            PopoverComponent.renderRight("The method to use to perform searches. Search servers are faster than databases"),
            br(),
            InputBoxComponent.render("maxSearchResults", "Max results: ", String.valueOf(SettingsEntity.maxSearchResults)),
            PopoverComponent.renderRight("The amount of items to display per page"),
            br(),
            label("Browse:").withClass("topicLabel"),
            br(),
            br(),
            InputBoxComponent.render("maxUIBrowseResults", "Max UI results: ", String.valueOf(SettingsEntity.maxUIBrowseResults)),
            PopoverComponent.renderRight("The amount of items to display when browsing"),
            br(),
            InputBoxComponent.render("maxAPIBrowseResults", "Max API results: ", String.valueOf(SettingsEntity.maxAPIBrowseResults)),
            PopoverComponent.renderRight("The amount of items to display when using the API"),
            br(),
            InputBoxComponent.render("cardWidth", "Card width: ", String.valueOf(SettingsEntity.cardWidth)),
            PopoverComponent.renderRight("The width of item cards"),
            br(),
            CheckboxComponent.render("stickTopMenu", "Sticky top menu: ", SettingsEntity.stickyTopMenu),
            PopoverComponent.renderRight("Whether to keep the top menu always accessible, or make it stay at the top of the page"),
            br(),
            label("Show in table view:").withClass("subLabel"),
            PopoverComponent.renderRight("When you're using the table view, these checkboxes customize the displayed columns"),
            br(),
            CheckboxComponent.render("showPoster", "Poster: ", SettingsEntity.tableShowPoster),
            br(),
            CheckboxComponent.render("showName", "Name: ", SettingsEntity.tableShowName),
            br(),
            CheckboxComponent.render("showRuntime", "Runtime: ", SettingsEntity.tableShowRuntime),
            br(),
            CheckboxComponent.render("showGenre", "Genre: ", SettingsEntity.tableShowGenre),
            br(),
            CheckboxComponent.render("showMpaaRating", "MPAA rating: ", SettingsEntity.tableShowMpaaRating),
            br(),
            CheckboxComponent.render("showUserRating", "User rating: ", SettingsEntity.tableShowUserRating),
            br(),
            CheckboxComponent.render("showLanguage", "Language: ", SettingsEntity.tableShowLanguage),
            br(),
            CheckboxComponent.render("showReleaseDate", "Release date: ", SettingsEntity.tableShowReleaseDate),
            br(),
            CheckboxComponent.render("showActions", "Actions: ", SettingsEntity.tableShowActions)
        );
    }

    private DomContent encodingSection() {
        return html(
            label("Programs:").withClass("subLabel"),
            br(),
            DropDownBoxComponent.render(
                "Encoder: ",
                "encoderProgram",
                SettingsEntity.encoderProgram,
                List.of(
                    new EnumValuePair(EncoderProgramEnum.FFMPEG, "FFMpeg"),
                    new EnumValuePair(EncoderProgramEnum.HANDBRAKE, "HandBrake")
                )
            ),
            PopoverComponent.renderRight("This program is responsible for transcoding (converting) your files. FFMpeg is default"),
            br(),
            DropDownBoxComponent.render(
                "Inspector: ",
                "inspectorProgram",
                SettingsEntity.inspectorProgram,
                List.of(
                    new EnumValuePair(InspectorProgramEnum.MEDIAINFO, "MediaInfo"),
                    new EnumValuePair(InspectorProgramEnum.FFPROBE, "FFProbe")
                )
            ),
            PopoverComponent.renderRight("This program is responsible for reading in the metadata of your media files. MediaInfo is default"),
            br(),
            DropDownBoxComponent.render(
                "Audio codec: ",
                "audioCodec",
                SettingsEntity.audioCodec,
                List.of(
                    new EnumValuePair(AudioCodecEnum.AAC, "AAC"),
                    new EnumValuePair(AudioCodecEnum.MP3, "MP3"),
                    new EnumValuePair(AudioCodecEnum.FLAC, "FLAC"),
                    new EnumValuePair(AudioCodecEnum.OGG, "OGG"),
                    new EnumValuePair(AudioCodecEnum.ALAC, "ALAC"),
                    new EnumValuePair(AudioCodecEnum.AC3, "AC3"),
                    new EnumValuePair(AudioCodecEnum.WAV, "WAV")
                )
            ),
            PopoverComponent.renderRight("Which audio codec to use during the transcoding process"),
            br(),
            DropDownBoxComponent.render(
                "Audio rate: ",
                "audioRate",
                SettingsEntity.audioRate,
                List.of(
                    new EnumValuePair(AudioRateEnum.fourEightK, "48K")
                )
            ),
            PopoverComponent.renderRight("The audio rate (only supporting 48K for now)"),
            br(),
            DropDownBoxComponent.render(
                "Audio preset: ",
                "audioPreset",
                SettingsEntity.audioPreset,
                List.of(
                    new EnumValuePair(EncoderPresetEnum.ULTRAFAST, "Ultra Fast"),
                    new EnumValuePair(EncoderPresetEnum.SUPERFAST, "Super Fast"),
                    new EnumValuePair(EncoderPresetEnum.VERYFAST, "Very Fast"),
                    new EnumValuePair(EncoderPresetEnum.FASTER, "Faster"),
                    new EnumValuePair(EncoderPresetEnum.FAST, "Fast"),
                    new EnumValuePair(EncoderPresetEnum.MEDIUM, "Medium"),
                    new EnumValuePair(EncoderPresetEnum.SLOW, "Slow"),
                    new EnumValuePair(EncoderPresetEnum.SLOWER, "Slower"),
                    new EnumValuePair(EncoderPresetEnum.VERYSLOW, "Very Slow"),
                    new EnumValuePair(EncoderPresetEnum.PLACEBO, "Placebo")
                )
            ),
            PopoverComponent.renderRight("The audio preset to use during the transcoding process (the slower, the better quality)"),
            br(),
            DropDownBoxComponent.render(
                "Video container: ",
                "videoContainer",
                SettingsEntity.videoContainer,
                List.of(
                    new EnumValuePair(VideoContainerEnum.FLV, "FLV"),
                    new EnumValuePair(VideoContainerEnum.MP4, "MP4"),
                    new EnumValuePair(VideoContainerEnum.WMV, "WMV"),
                    new EnumValuePair(VideoContainerEnum.AVI, "AVI"),
                    new EnumValuePair(VideoContainerEnum.MKV, "MKV"),
                    new EnumValuePair(VideoContainerEnum.OGV, "OGV"),
                    new EnumValuePair(VideoContainerEnum.TS, "TS"),
                    new EnumValuePair(VideoContainerEnum.M2TS, "M2TS"),
                    new EnumValuePair(VideoContainerEnum.WEBM, "WebM")
                )
            ),
            PopoverComponent.renderRight("The video container to use. MP4 is the most common, WebM is another good option for streaming"),
            br(),
            DropDownBoxComponent.render(
                "Video codec: ",
                "videoCodec",
                SettingsEntity.videoCodec,
                List.of(
                    new EnumValuePair(VideoCodecEnum.H264, "H264"),
                    new EnumValuePair(VideoCodecEnum.H265, "H265"),
                    new EnumValuePair(VideoCodecEnum.FLV, "FLV"),
                    new EnumValuePair(VideoCodecEnum.DIVX, "DivX"),
                    new EnumValuePair(VideoCodecEnum.MPEG4, "Mpeg4")
                )
            ),
            PopoverComponent.renderRight("The video codec to use during the transcoding process (H264 is most supported)"),
            br(),
            DropDownBoxComponent.render(
                "Video preset: ",
                "videoPreset",
                SettingsEntity.encoderPreset,
                List.of(
                    new EnumValuePair(EncoderPresetEnum.ULTRAFAST, "Ultra Fast"),
                    new EnumValuePair(EncoderPresetEnum.SUPERFAST, "Super Fast"),
                    new EnumValuePair(EncoderPresetEnum.VERYFAST, "Very Fast"),
                    new EnumValuePair(EncoderPresetEnum.FASTER, "Faster"),
                    new EnumValuePair(EncoderPresetEnum.FAST, "Fast"),
                    new EnumValuePair(EncoderPresetEnum.MEDIUM, "Medium"),
                    new EnumValuePair(EncoderPresetEnum.SLOW, "Slow"),
                    new EnumValuePair(EncoderPresetEnum.SLOWER, "Slower"),
                    new EnumValuePair(EncoderPresetEnum.VERYSLOW, "Very Slow"),
                    new EnumValuePair(EncoderPresetEnum.PLACEBO, "Placebo")
                )
            ),
            PopoverComponent.renderRight("The video preset to use during the transcoding process (the slower, the better quality)"),
            br(),
            CheckboxComponent.render("tuneFilm", "Video tune film: ", SettingsEntity.videoTuneFilm),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process. Useful when dealing with film"),
            br(),
            CheckboxComponent.render("tuneAnimation", "Video tune animation: ", SettingsEntity.videoTuneAnimation),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process. Useful when dealing with older animation film"),
            br(),
            CheckboxComponent.render("tuneGrain", "Video tune grain: ", SettingsEntity.videoTuneGrain),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process"),
            br(),
            CheckboxComponent.render("stillImage", "Video tune still image: ", SettingsEntity.videoTuneStillImage),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process"),
            br(),
            CheckboxComponent.render("fastDecode", "Video fast decode: ", SettingsEntity.videoTuneFastDecode),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process. Useful when running on older systems or slow hard drives"),
            br(),
            CheckboxComponent.render("zeroLatency", "Video zero latency: ", SettingsEntity.videoTuneZeroLatency),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process. Useful when streaming over the internet"),
            br(),
            CheckboxComponent.render("fastStart", "Video fast start: ", SettingsEntity.videoFastStart),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process. Useful when using slow connections / hard drives"),
            br(),
            CheckboxComponent.render("tunePsnr", "Video tune PSNR: ", SettingsEntity.videoTunePsnr),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process"),
            br(),
            CheckboxComponent.render("tuneSsnr", "Video tune SSNR: ", SettingsEntity.videoTuneSsnr),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process"),
            br(),
            InputBoxComponent.render("videoCrf", "Video CRF: ", String.valueOf(SettingsEntity.videoCrf)),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process"),
            br(),
            CheckboxComponent.render("blackBorderRemoval", "Video black border removal: ", SettingsEntity.videoBlackBorder),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process. Removes black borders around the picture"),
            br(),
            CheckboxComponent.render("cudaAcceleration", "Video CUDA acceleration: ", SettingsEntity.videoCudaAcceleration),
            PopoverComponent.renderRight("Passed to the encoder during the transcoding process. You must have a graphics card with CUDA support"),
            br(),
            InputBoxComponent.render("oneFourFourPTranscodeBitrate", "144P video transcode bitrate: ", String.valueOf(SettingsEntity.oneZeroEightZeroVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 144P version of your media"),
            br(),
            InputBoxComponent.render("twoFourZeroPTranscodeBitrate", "240P video transcode bitrate: ", String.valueOf(SettingsEntity.twoFourZeroVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 240P version of your media"),
            br(),
            InputBoxComponent.render("threeSixZeroPTranscodeBitrate", "360P video transcode bitrate: ", String.valueOf(SettingsEntity.threeSixZeroVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 360P version of your media"),
            br(),
            InputBoxComponent.render("fourEightZeroPTranscodeBitrate", "480P video transcode bitrate: ", String.valueOf(SettingsEntity.fourEightZeroVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 480P version of your media"),
            br(),
            InputBoxComponent.render("sevenTwoZeroPTranscodeBitrate", "720P video transcode bitrate: ", String.valueOf(SettingsEntity.sevenTwoZeroVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 720P version of your media"),
            br(),
            InputBoxComponent.render("oneZeroEightZeroPTranscodeBitrate", "1080P video transcode bitrate: ", String.valueOf(SettingsEntity.oneZeroEightZeroVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 1080P version of your media"),
            br(),
            InputBoxComponent.render("twoKTranscodeBitrate", "2K video transcode bitrate: ", String.valueOf(SettingsEntity.twoKVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 2K version of your media"),
            br(),
            InputBoxComponent.render("fourKTranscodeBitrate", "4k video transcode bitrate: ", String.valueOf(SettingsEntity.fourKVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 4K version of your media"),
            br(),
            InputBoxComponent.render("eightKTranscodeBitrate", "8K video transcode bitrate: ", String.valueOf(SettingsEntity.eightKVideoTranscodeBitrate)),
            PopoverComponent.renderRight("The bitrate you wish to use when the transcoder creates a 8K version of your media")
        );
    }

    private DomContent cacheSection() {
        return html(
            CheckboxComponent.render("cacheEnable", "Cache enable: ", SettingsEntity.cacheEnable),
            PopoverComponent.renderRight("Globally disable caching things in memory (having this enabled makes the server faster)"),
            br(),
            InputBoxComponent.render("maxAssetCacheAge", "Max asset cache age: ", String.valueOf(SettingsEntity.maxAssetCacheAge)),
            PopoverComponent.renderRight("The maximum amount of time that an entity / asset can remain in browser cache")
        );
    }

    private DomContent movieSection() {
        return html(
            CheckboxComponent.render("movieLibraryEnable", "Movie library enable: ", SettingsEntity.movieLibraryEnable),
            PopoverComponent.renderRight("Toggles the movie library"),
            br(),
            InputBoxComponent.render("movieLibraryPath", "Movie library path: ", SettingsEntity.movieLibraryPath),
            PopoverComponent.renderRight("Where on disk your movie files are located"),
            br(),
            CheckboxComponent.render("movieScanEnable", "Movie scan enable: ", SettingsEntity.movieScanEnable),
            PopoverComponent.renderRight("Toggles the media scanner. If off and a file is changed, the changes will not be noticed by the system"),
            br(),
            CheckboxComponent.render("moviePreTranscodeEnable", "Movie pre-transcode enable: ", SettingsEntity.moviePreTranscodeEnable),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files"),
            br(),
            CheckboxComponent.render("moviePreTranscodeOneFourFourP", "Movie pre-transcode 144p: ", SettingsEntity.moviePreTranscode144p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 144P"),
            br(),
            CheckboxComponent.render("moviePreTranscodeTwoFourZeroP", "Movie pre-transcode 240p: ", SettingsEntity.moviePreTranscode240p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 240P"),
            br(),
            CheckboxComponent.render("moviePreTranscodeThreeSixZeroP", "Movie pre-transcode 360p: ", SettingsEntity.moviePreTranscode360p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 360P"),
            br(),
            CheckboxComponent.render("moviePreTranscodeFourEightZeroP", "Movie pre-transcode 480p: ", SettingsEntity.moviePreTranscode480p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 480P"),
            br(),
            CheckboxComponent.render("moviePreTranscodeSevenTwoZeroP", "Movie pre-transcode 720p: ", SettingsEntity.moviePreTranscode720p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 720P"),
            br(),
            CheckboxComponent.render("moviePreTranscodeOneZeroEightZeroP", "Movie pre-transcode 1080p: ", SettingsEntity.moviePreTranscode1080p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 1080P"),
            br(),
            CheckboxComponent.render("moviePreTranscodeTwoK", "Movie pre-transcode 2k: ", SettingsEntity.moviePreTranscode2k),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 2K"),
            br(),
            CheckboxComponent.render("moviePreTranscodeFourK", "Movie pre-transcode 4k: ", SettingsEntity.moviePreTranscode4k),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 4K"),
            br(),
            CheckboxComponent.render("moviePreTranscodeEightK", "Movie pre-transcode 8k: ", SettingsEntity.moviePreTranscode8k),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 8K"),
            br(),
            InputBoxComponent.render("movieScanFrequencyTime", "Scan frequency:", String.valueOf(SettingsEntity.movieScanFrequencyTime)),
            DropDownBoxComponent.render(
                "Movie scan frequency: ",
                "movieScanFrequencyType",
                SettingsEntity.movieScanFrequencyType,
                List.of(
                    new EnumValuePair(ScanFrequencyEnum.MINUTES, "Minutes"),
                    new EnumValuePair(ScanFrequencyEnum.HOURS, "Hours"),
                    new EnumValuePair(ScanFrequencyEnum.DAYS, "Days")
                )
            ),
            PopoverComponent.renderRight("How often to scan the library for changes"),
            br(),
            InputBoxComponent.render("movieLibraryPreTranscodePath", "Movie library pre-transcode path: ", SettingsEntity.moviePreTranscodeLibraryPath),
            PopoverComponent.renderRight("Where on disk your transcoded movie files are placed")
        );
    }

    private DomContent tvShowSection() {
        return html(
            CheckboxComponent.render("tvShowLibraryEnable", "Tv show library enable: ", SettingsEntity.tvShowLibraryEnable),
            PopoverComponent.renderRight("Toggles the tv show library"),
            br(),
            InputBoxComponent.render("tvShowLibraryPath", "TV show library path:", SettingsEntity.tvShowLibraryPath),
            PopoverComponent.renderRight("Where on disk your tv show files are located"),
            br(),
            CheckboxComponent.render("tvShowScanEnable", "TV show scan enable: ", SettingsEntity.tvShowScanEnable),
            PopoverComponent.renderRight("Toggles the media scanner. If off and a file is changed, the changes will not be noticed by the system"),
            br(),
            CheckboxComponent.render("tvShowPreTranscodeEnable", "TV show pre-transcode enable: ", SettingsEntity.tvShowPreTranscodeEnable),
            PopoverComponent.renderRight("Whether to pre-transcode your tv show files"),
            br(),
            CheckboxComponent.render("tvPreTranscodeOneFourFourP", "TV show pre-transcode 144p: ", SettingsEntity.tvShowPreTranscode144p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 144P"),
            br(),
            CheckboxComponent.render("tvPreTranscodeTwoFourZeroP", "TV show pre-transcode 240p: ", SettingsEntity.tvShowPreTranscode240p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 240P"),
            br(),
            CheckboxComponent.render("tvPreTranscodeThreeSixZeroP", "TV show pre-transcode 360p: ", SettingsEntity.tvShowPreTranscode360p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 360P"),
            br(),
            CheckboxComponent.render("tvPreTranscodeFourEightZeroP", "TV show pre-transcode 480p: ", SettingsEntity.tvShowPreTranscode480p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 480P"),
            br(),
            CheckboxComponent.render("tvPreTranscodeSevenTwoZeroP", "TV show pre-transcode 720p: ", SettingsEntity.tvShowPreTranscode720p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 720P"),
            br(),
            CheckboxComponent.render("tvPreTranscodeOneZeroEightZeroP", "TV show pre-transcode 1080p: ", SettingsEntity.tvShowPreTranscode1080p),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 1080P"),
            br(),
            CheckboxComponent.render("tvPreTranscodeTwoK", "TV show pre-transcode 2k: ", SettingsEntity.tvShowPreTranscode2k),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 2K"),
            br(),
            CheckboxComponent.render("tvPreTranscodeFourK", "TV show pre-transcode 4k: ", SettingsEntity.tvShowPreTranscode4k),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 4K"),
            br(),
            CheckboxComponent.render("tvPreTranscodeEightK", "TV show pre-transcode 8k: ", SettingsEntity.tvShowPreTranscode8k),
            PopoverComponent.renderRight("Whether to pre-transcode your movie files to 8K"),
            br(),
            InputBoxComponent.render("tvShowScanFrequencyTime", "Scan frequency:", String.valueOf(SettingsEntity.tvShowScanFrequencyTime)),
            DropDownBoxComponent.render(
                "TV show scan frequency: ",
                "tvShowScanFrequencyType",
                SettingsEntity.tvShowScanFrequencyType,
                List.of(
                    new EnumValuePair(ScanFrequencyEnum.MINUTES, "Minutes"),
                    new EnumValuePair(ScanFrequencyEnum.HOURS, "Hours"),
                    new EnumValuePair(ScanFrequencyEnum.DAYS, "Days")
                )
            ),
            PopoverComponent.renderRight("How often to scan the library for changes"),
            br(),
            InputBoxComponent.render("tvShowLibraryPreTranscodePath", "TV show library pre-transcode path: ", SettingsEntity.tvShowPreTranscodeLibraryPath),
            PopoverComponent.renderRight("The place on disk where your transcoded files will be placed")
        );
    }

    private DomContent gameSection() {
        return html(
            CheckboxComponent.render("gameLibraryEnable", "Game library enable: ", SettingsEntity.gameLibraryEnable),
            PopoverComponent.renderRight("Toggles the game library"),
            br(),
            InputBoxComponent.render("gameLibraryPath", "Game library path: ", SettingsEntity.gameLibraryPath),
            PopoverComponent.renderRight("Where on disk your game files are located"),
            br(),
            CheckboxComponent.render("gameScanEnable", "Game scan enable: ", SettingsEntity.gameScanEnable),
            PopoverComponent.renderRight("Toggles the media scanner. If off and a file is changed, the changes will not be noticed by the system"),
            br(),
            InputBoxComponent.render("gameScanFrequencyTime", "Scan frequency:", String.valueOf(SettingsEntity.gameScanFrequencyTime)),
            DropDownBoxComponent.render(
                "Game scan frequency: ",
                "gameScanFrequencyType",
                SettingsEntity.gameScanFrequencyType,
                List.of(
                    new EnumValuePair(ScanFrequencyEnum.MINUTES, "Minutes"),
                    new EnumValuePair(ScanFrequencyEnum.HOURS, "Hours"),
                    new EnumValuePair(ScanFrequencyEnum.DAYS, "Days")
                )
            ),
            PopoverComponent.renderRight("How often to scan the library for changes")
        );
    }

    private DomContent musicSection() {
        return html(
            CheckboxComponent.render("musicLibraryEnable", "Music library enable: ", SettingsEntity.musicLibraryEnable),
            PopoverComponent.renderRight("Where on disk your music files are located"),
            br(),
            InputBoxComponent.render("musicLibraryPath", "Music library path: ", SettingsEntity.musicLibraryPath),
            PopoverComponent.renderRight("Where on disk your music files are located"),
            br(),
            CheckboxComponent.render("musicScanEnable", "Music scan enable: ", SettingsEntity.musicScanEnable),
            PopoverComponent.renderRight("Toggles the media scanner. If off and a file is changed, the changes will not be noticed by the system"),
            br(),
            CheckboxComponent.render("musicPreTranscodeEnable", "Music pre-transcode enable: ", SettingsEntity.musicPreTranscodeEnable),
            PopoverComponent.renderRight("Whether to pre-transcode your music files"),
            br(),
            CheckboxComponent.render("musicPreTranscodeSixFourK", "Music pre-transcode 64K: ", SettingsEntity.musicPreTranscode64k),
            PopoverComponent.renderRight("Whether to pre-transcode a 64K file"),
            br(),
            CheckboxComponent.render("musicPreTranscodeNineSixK", "Music pre-transcode 96K: ", SettingsEntity.musicPreTranscode96k),
            PopoverComponent.renderRight("Whether to pre-transcode a 96K file"),
            br(),
            CheckboxComponent.render("musicPreTranscodeOneTwoEightK", "Music pre-transcode 128K: ", SettingsEntity.musicPreTranscode128k),
            PopoverComponent.renderRight("Whether to pre-transcode a 128K file"),
            br(),
            CheckboxComponent.render("musicPreTranscodeThreeTwoZeroK", "Music pre-transcode 320K: ", SettingsEntity.musicPreTranscode320k),
            PopoverComponent.renderRight("Whether to pre-transcode a 320K file"),
            br(),
            CheckboxComponent.render("musicPreTranscodeOneFourOneOneK", "Music pre-transcode 1411K: ", SettingsEntity.musicPreTranscode1411k),
            PopoverComponent.renderRight("Whether to pre-transcode a 1411K file"),
            br(),
            InputBoxComponent.render("musicScanFrequencyTime", "Scan frequency:", String.valueOf(SettingsEntity.musicScanFrequencyTime)),
            DropDownBoxComponent.render(
                "Music scan frequency: ",
                "musicScanFrequencyType",
                SettingsEntity.musicScanFrequencyType,
                List.of(
                    new EnumValuePair(ScanFrequencyEnum.MINUTES, "Minutes"),
                    new EnumValuePair(ScanFrequencyEnum.HOURS, "Hours"),
                    new EnumValuePair(ScanFrequencyEnum.DAYS, "Days")
                )
            ),
            PopoverComponent.renderRight("How often to scan the library for changes"),
            br(),
            InputBoxComponent.render("musicPreTranscodeLibraryPath", "Music pre-transcode library path: ", SettingsEntity.musicPreTranscodeLibraryPath),
            PopoverComponent.renderRight("The place on disk where your transcoded files will be placed")
        );
    }

    private DomContent bookSection() {
        return html(
            CheckboxComponent.render("bookLibraryEnable", "Book library enable: ", SettingsEntity.bookLibraryEnable),
            PopoverComponent.renderRight("Toggles the book library"),
            br(),
            InputBoxComponent.render("bookLibraryPath", "Book library path: ", SettingsEntity.bookLibraryPath),
            PopoverComponent.renderRight("Where on disk your book files are located"),
            br(),
            CheckboxComponent.render("bookScanEnable", "Book scan enable: ", SettingsEntity.bookScanEnable),
            PopoverComponent.renderRight("Toggles the media scanner. If off and a file is changed, the changes will not be noticed by the system"),
            br(),
            InputBoxComponent.render("bookScanFrequencyTime", "Scan frequency:", String.valueOf(SettingsEntity.bookScanFrequencyTime)),
            DropDownBoxComponent.render(
                "Book scan frequency: ",
                "bookScanFrequencyType",
                SettingsEntity.bookScanFrequencyType,
                List.of(
                    new EnumValuePair(ScanFrequencyEnum.MINUTES, "Minutes"),
                    new EnumValuePair(ScanFrequencyEnum.HOURS, "Hours"),
                    new EnumValuePair(ScanFrequencyEnum.DAYS, "Days")
                )
            ),
            PopoverComponent.renderRight("How often to scan the library for changes")
        );
    }
}

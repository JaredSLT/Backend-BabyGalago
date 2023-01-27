package tech.tresearchgroup.babygalago.controller.endpoints;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.SettingsController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.controller.CompressionController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class AssetEndpointController extends BasicController {
    private final SettingsController settingsController;

    public AssetEndpointController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    /**
     * Returns the requested asset binary
     *
     * @param httpRequest the request
     * @return the binary
     * @throws IOException if it fails
     */
    public Promisable<HttpResponse> getAsset(HttpRequest httpRequest) throws IOException {
        String file = Objects.requireNonNull(httpRequest.getPathParameter("file"));
        Path path = Path.of("assets/" + file);
        if (path.toFile().exists()) {
            byte[] data = Files.readAllBytes(path);
            byte[] compressed = CompressionController.compress(data);
            return okResponseCompressed(compressed, settingsController.getMaxAssetCacheAge());
        }
        return error();
    }

    /**
     * Combines all CSS minified pages together as styles.min.css
     *
     * @param httpRequest the request
     * @return the combined css
     */
    public @NotNull Promisable<HttpResponse> getCombinedCSS(HttpRequest httpRequest) {
        try {
            Path cssPath = Path.of("assets/css");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(cssPath)) {
                for (Path path : stream) {
                    if (!Files.isDirectory(path)) {
                        if (path.getFileName().toString().contains(".min.css")) {
                            outputStream.write(Files.readAllBytes(path));
                        }
                    }
                }
            }
            byte[] rawData = outputStream.toByteArray();
            byte[] compressed = CompressionController.compress(rawData);
            return okResponseCompressed(compressed, settingsController.getMaxAssetCacheAge());
        } catch (IOException e) {
            if (settingsController.isDebug()) {
                e.printStackTrace();
            }
            return error();
        }
    }
}

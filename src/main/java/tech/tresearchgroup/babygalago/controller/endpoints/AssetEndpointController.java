package tech.tresearchgroup.babygalago.controller.endpoints;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
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
    private final Cache<String, byte[]> assetCache;
    private final SettingsController settingsController;

    public AssetEndpointController(SettingsController settingsController) {
        this.assetCache = Caffeine.newBuilder().build();
        this.settingsController = settingsController;
    }

    public HttpResponse getAsset(HttpRequest httpRequest) throws IOException {
        String file = Objects.requireNonNull(httpRequest.getPathParameter("file"));
        byte[] cachedData = assetCache.getIfPresent(file);
        if (cachedData != null) {
            return okResponseCompressed(cachedData, settingsController.getMaxAssetCacheAge());
        }
        Path path = Path.of("assets/" + file);
        if (path.toFile().exists()) {
            byte[] data = Files.readAllBytes(path);
            byte[] compressed = CompressionController.compress(data);
            assetCache.put(file, compressed);
            return okResponseCompressed(compressed, settingsController.getMaxAssetCacheAge());
        }
        return error();
    }

    public HttpResponse getCombinedCSS() throws IOException {
        byte[] cachedData = assetCache.getIfPresent("styles");
        if (cachedData != null) {
            return okResponseCompressed(cachedData, settingsController.getMaxAssetCacheAge());
        }
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
        assetCache.put("styles", compressed);
        return okResponseCompressed(compressed, settingsController.getMaxAssetCacheAge());
    }
}

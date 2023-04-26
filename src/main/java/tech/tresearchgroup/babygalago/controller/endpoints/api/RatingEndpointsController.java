package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpHeaderValue;
import io.activej.http.HttpHeaders;
import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import org.jetbrains.annotations.NotNull;
import tech.tresearchgroup.babygalago.controller.controllers.RatingEntityController;
import tech.tresearchgroup.palila.controller.BasicController;
import tech.tresearchgroup.palila.model.enums.ReturnType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;

public class RatingEndpointsController extends BasicController {
    private final RatingEntityController ratingEntityController;

    public RatingEndpointsController(RatingEntityController ratingEntityController) {
        this.ratingEntityController = ratingEntityController;
    }

    /**
     * Gets a rating by its id
     *
     * @param httpRequest the request
     * @return the page response
     * @throws SQLException              if its fails
     * @throws IOException               if its fails
     * @throws InvocationTargetException if its fails
     * @throws NoSuchMethodException     if its fails
     * @throws InstantiationException    if its fails
     * @throws IllegalAccessException    if its fails
     */
    public Promisable<HttpResponse> getRating(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long ratingId = Long.parseLong(httpRequest.getPathParameter("ratingId"));
        byte[] data = (byte[]) ratingEntityController.readSecureResponse(ratingId, ReturnType.JSON, httpRequest);
        if (data != null) {
            return okResponseCompressed(data);
        }
        return notFound();
    }

    /**
     * Patch a rating by its id
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> patchRating(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("ratingId"));
        return ok(ratingEntityController.update(id, data, httpRequest));
    }

    /**
     * Deletes a rating by its id
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> deleteRating(HttpRequest httpRequest) throws Exception {
        int ratingId = Integer.parseInt(httpRequest.getPathParameter("ratingId"));
        return ok(ratingEntityController.delete(ratingId, httpRequest));
    }

    /**
     * Creates a rating entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> putRating(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return ok(ratingEntityController.createSecureResponse(data, ReturnType.JSON, httpRequest) != null);
    }

    /**
     * Creates a rating entity
     *
     * @param httpRequest the request
     * @return the page response
     * @throws Exception if its fails
     */
    public Promisable<HttpResponse> postRating(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed((byte[]) ratingEntityController.createSecureResponse(data, ReturnType.JSON, httpRequest));
    }

    /**
     * Gets which methods are available for this endpoint
     *
     * @param httpRequest the request
     * @return the response page
     */
    @NotNull
    public Promisable<HttpResponse> optionsRatingById(@NotNull HttpRequest httpRequest) {
        return HttpResponse.ok200().withHeader(HttpHeaders.ALLOW, HttpHeaderValue.of("GET, PATCH, DELETE, PUT, POST"));
    }
}

package tech.tresearchgroup.babygalago.controller.endpoints.api;

import io.activej.http.HttpRequest;
import io.activej.http.HttpResponse;
import io.activej.promise.Promisable;
import lombok.AllArgsConstructor;
import tech.tresearchgroup.babygalago.controller.controllers.RatingEntityController;
import tech.tresearchgroup.palila.controller.BasicController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.SQLException;

@AllArgsConstructor
public class RatingEndpointsController extends BasicController {
    private final RatingEntityController ratingEntityController;


    public Promisable<HttpResponse> getRating(HttpRequest httpRequest) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        long ratingId = Long.parseLong(httpRequest.getPathParameter("ratingId"));
        byte[] data = ratingEntityController.readSecureAPIResponse(ratingId, httpRequest);
        if (data != null) {
            return okResponseCompressed(data);
        }
        return notFound();
    }

    public Promisable<HttpResponse> patchRating(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        long id = Long.parseLong(httpRequest.getPathParameter("ratingId"));
        return ok(ratingEntityController.update(id, data, httpRequest));
    }

    public Promisable<HttpResponse> deleteRating(HttpRequest httpRequest) throws Exception {
        int ratingId = Integer.parseInt(httpRequest.getPathParameter("ratingId"));
        return ok(ratingEntityController.delete(ratingId, httpRequest));
    }

    public Promisable<HttpResponse> putRating(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return ok(ratingEntityController.createSecureAPIResponse(data, httpRequest) != null);
    }

    public Promisable<HttpResponse> postRating(HttpRequest httpRequest) throws Exception {
        String data = httpRequest.loadBody().getResult().asString(Charset.defaultCharset());
        return okResponseCompressed(ratingEntityController.createSecureAPIResponse(data, httpRequest));
    }
}

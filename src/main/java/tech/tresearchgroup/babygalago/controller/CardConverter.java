package tech.tresearchgroup.babygalago.controller;

import tech.tresearchgroup.palila.model.Card;
import tech.tresearchgroup.palila.model.entities.VideoFileEntity;
import tech.tresearchgroup.schemas.galago.entities.*;

import java.util.LinkedList;
import java.util.List;

public class CardConverter {
    public static List<Card> convert(List objects, String type, Class theClass) {
        switch (theClass.getSimpleName().toLowerCase()) {
            case "bookentity" -> {
                return convertBooks(objects, type);
            }
            case "movieentity" -> {
                return convertMovies(objects, type);
            }
            case "songentity" -> {
                return convertSongs(objects, type);
            }
            case "tvshowentity" -> {
                return convertTvShows(objects, type);
            }
            case "gameentity" -> {
                return convertGames(objects, type);
            }
        }
        return new LinkedList<>();
    }

    public static List<Card> convertMovies(List<MovieEntity> movieEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (movieEntities == null) {
            return null;
        }
        for (MovieEntity movieEntity : movieEntities) {
            Card card = new Card();
            card.setId(movieEntity.getId());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(type);
            card.setMediaType(movieEntity.getClass().getSimpleName().toLowerCase());
            card.setTitle(movieEntity.getTitle());
            card.setReleaseDate(movieEntity.getReleaseDate());
            if (movieEntity.getMpaaRating() != null) {
                card.setMpaaRating(movieEntity.getMpaaRating().toString());
            }
            card.setRuntime(String.valueOf(movieEntity.getRuntime()));
            card.setUserRating(String.valueOf(movieEntity.getUserRating()));
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertBooks(List<BookEntity> bookEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (bookEntities == null) {
            return null;
        }
        for (BookEntity bookEntity : bookEntities) {
            Card card = new Card();
            card.setId(bookEntity.getId());
            card.setPosterLocation("/assets/poster.webp");
            card.setTitle(bookEntity.getTitle());
            card.setType(type);
            card.setMediaType(bookEntity.getClass().getSimpleName().toLowerCase());
            card.setRuntime(String.valueOf(bookEntity.getPageCount()));
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertGames(List<GameEntity> gameEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (gameEntities == null) {
            return null;
        }
        for (GameEntity gameEntity : gameEntities) {
            Card card = new Card();
            card.setId(gameEntity.getId());
            card.setTitle(gameEntity.getTitle());
            card.setPosterLocation("/assets/poster.webp");
            if (gameEntity.getEsrbRating() != null) {
                card.setMpaaRating(gameEntity.getEsrbRating().toString());
            }
            card.setType(type);
            card.setType(gameEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("game");
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertSongs(List<SongEntity> songEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (songEntities == null) {
            return null;
        }
        for (SongEntity songEntity : songEntities) {
            Card card = new Card();
            card.setId(songEntity.getId());
            card.setTitle(songEntity.getTitle());
            if (songEntity.getReleaseDate() != null) {
                card.setReleaseDate(songEntity.getReleaseDate());
            }
            card.setPosterLocation("/assets/poster.webp");
            card.setType(songEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("song");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertTvShows(List<TvShowEntity> tvShowEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (tvShowEntities == null) {
            return null;
        }
        for (TvShowEntity tvShowEntity : tvShowEntities) {
            Card card = new Card();
            card.setId(tvShowEntity.getId());
            card.setTitle(tvShowEntity.getTitle());
            if (tvShowEntity.getFirstAired() != null) {
                card.setReleaseDate(tvShowEntity.getFirstAired());
            }
            card.setRuntime(String.valueOf(tvShowEntity.getRuntime()));
            card.setPosterLocation("/assets/poster.webp");
            card.setType(tvShowEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("tvshow");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertImages(List<ImageEntity> imageEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (imageEntities == null) {
            return null;
        }
        for (ImageEntity imageEntity : imageEntities) {
            Card card = new Card();
            card.setId(imageEntity.getId());
            card.setTitle(imageEntity.getTitle());
            card.setType(imageEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertVideos(List<VideoFileEntity> videoEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (videoEntities == null) {
            return null;
        }
        for (VideoFileEntity videoEntity : videoEntities) {
            Card card = new Card();
            card.setId(videoEntity.getId());
            card.setTitle(videoEntity.getPlaybackQualityEnum().toString());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(videoEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("video");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertPeople(List<PersonEntity> personEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (personEntities == null) {
            return null;
        }
        for (PersonEntity personEntity : personEntities) {
            Card card = new Card();
            card.setId(personEntity.getId());
            card.setTitle(personEntity.getFirstName() + " " + personEntity.getLastName());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(personEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertCompany(List<CompanyEntity> companyEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (companyEntities == null) {
            return null;
        }
        for (CompanyEntity companyEntity : companyEntities) {
            Card card = new Card();
            card.setId(companyEntity.getId());
            card.setTitle(companyEntity.getName());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(companyEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertLyrics(List<LyricsEntity> lyricsEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (lyricsEntities == null) {
            return null;
        }
        for (LyricsEntity lyricsEntity : lyricsEntities) {
            Card card = new Card();
            card.setId(lyricsEntity.getId());
            card.setTitle(lyricsEntity.getLanguage().toString());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(lyricsEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertAlbum(List<AlbumEntity> albumEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (albumEntities == null) {
            return null;
        }
        for (AlbumEntity albumEntity : albumEntities) {
            Card card = new Card();
            card.setId(albumEntity.getId());
            card.setTitle(albumEntity.getName());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(albumEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertSeasons(List<SeasonEntity> seasonEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (seasonEntities == null) {
            return null;
        }
        for (SeasonEntity seasonEntity : seasonEntities) {
            Card card = new Card();
            card.setId(seasonEntity.getId());
            card.setTitle(seasonEntity.getName());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(seasonEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertArtists(List<ArtistEntity> artistEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (artistEntities == null) {
            return null;
        }
        for (ArtistEntity artistEntity : artistEntities) {
            Card card = new Card();
            card.setId(artistEntity.getId());
            card.setTitle(artistEntity.getName());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(artistEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }

    public static List<Card> convertLocations(List<LocationEntity> locationEntities, String type) {
        List<Card> cards = new LinkedList<>();
        if (locationEntities == null) {
            return null;
        }
        for (LocationEntity locationEntity : locationEntities) {
            Card card = new Card();
            card.setId(locationEntity.getId());
            card.setTitle(locationEntity.getName());
            card.setPosterLocation("/assets/poster.webp");
            card.setType(locationEntity.getClass().getSimpleName().toLowerCase());
            card.setMediaType("image");
            card.setType(type);
            cards.add(card);
        }
        return cards;
    }
}

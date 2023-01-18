package tech.tresearchgroup.babygalago.controller;

import tech.tresearchgroup.palila.model.KeyValuePair;
import tech.tresearchgroup.schemas.galago.entities.SubtitleEntity;

import java.util.LinkedList;
import java.util.List;

public class ChipsConverter {
    public static List<KeyValuePair> convertSubtitles(List<SubtitleEntity> subtitleEntities) {
        List<KeyValuePair> keyValuePairs = new LinkedList<>();
        if (subtitleEntities != null) {
            for (SubtitleEntity subtitleEntity : subtitleEntities) {
                keyValuePairs.add(new KeyValuePair(String.valueOf(subtitleEntity.getId()), subtitleEntity.getLanguage()));
            }
        }
        return keyValuePairs;
    }
}

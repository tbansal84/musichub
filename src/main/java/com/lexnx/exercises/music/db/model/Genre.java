package com.lexnx.exercises.music.db.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.GenerationType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public enum Genre {

    ROCK("rock"), PUMP("pump"), POP("pop"), JAZZ("jazz");
    private final String name;
    private static final Map<String, Genre> lookup = new HashMap<>();

    Genre(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }


    static {
        Stream.of(Genre.values()).forEach(e -> lookup.put(e.toString(), e));
    }


    public static Genre get(String name) {
        return lookup.get(name);
    }

    @Converter
    public static class GenreAttributeConverter implements AttributeConverter<Genre, String> {

        @Override
        public String convertToDatabaseColumn(Genre genre) {
            if (genre == null) {
                return null;
            }
            return genre.toString();
        }


        @Override
        public Genre convertToEntityAttribute(String genre) {
            if (genre == null) {
                return null;
            }
            return Genre.get(genre);
        }

    }
}

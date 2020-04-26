package com.lexnx.exercises.music.rest.model.mapper;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.rest.model.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    ArtistEntity modelToEntity(Artist source);

    Artist entityToModel(ArtistEntity destination);
}
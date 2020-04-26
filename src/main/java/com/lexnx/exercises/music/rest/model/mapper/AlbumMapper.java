package com.lexnx.exercises.music.rest.model.mapper;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.rest.model.Album;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
    AlbumEntity modelToEntity(Album source);

    Album entityToModel(AlbumEntity destination);
}
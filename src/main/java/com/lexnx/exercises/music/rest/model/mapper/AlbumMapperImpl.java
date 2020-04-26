package com.lexnx.exercises.music.rest.model.mapper;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.rest.model.Album;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
public class AlbumMapperImpl implements AlbumMapper {

    @Override
    public AlbumEntity modelToEntity(Album source) {
        if (source == null) {
            return null;
        }

        AlbumEntity albumEntity = new AlbumEntity();

        albumEntity.setAlbumId(source.getAlbumId());
        albumEntity.setTitle(source.getTitle());
        albumEntity.setGenre(source.getGenre());
        if (source.getReleaseYear() != null) {
            albumEntity.setReleaseYear(source.getReleaseYear());
        }

        return albumEntity;
    }

    @Override
    public Album entityToModel(AlbumEntity destination) {
        if (destination == null) {
            return null;
        }

        Album album = new Album();

        album.setAlbumId(destination.getAlbumId());
        album.setGenre(destination.getGenre());
        album.setTitle(destination.getTitle());
        if (destination.getReleaseYear() != null) {
            album.setReleaseYear(destination.getReleaseYear());
        }

        return album;
    }
}

package com.marin.librarymanagementsystemapi.dtos;

import com.marin.librarymanagementsystemapi.entities.Author;
import com.marin.librarymanagementsystemapi.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface DTOMappings {
    DTOMappings INSTANCE = Mappers.getMapper(DTOMappings.class);


    AuthorReadDTO toAuthorReadDTO(Author author);
    Author toAuthor(AuthorWriteDTO authorWriteDTO);

    @Mapping(source = "author", target = "author")
    BookReadDTO toBookReadDTO(Book book);

    Book toBook(BookCreateDTO bookCreateDTO);
}

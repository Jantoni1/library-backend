package jantoni1.librarybackend.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static jantoni1.librarybackend.constants.Constants.*;

public class BookListDeserializer extends StdDeserializer<BookListDTO> {


    public BookListDeserializer() {
        this(null);
    }

    public BookListDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BookListDTO deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode root = parser.getCodec().readTree(parser);
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(root.fields(), Spliterator.IMMUTABLE), false)
                .map(this::parseBookNodes)
                .collect(Collectors.toCollection(BookListDTO::new));
    }

    private BookDetailsDTO parseBookNodes(Map.Entry<String, JsonNode> bookNode) {
        return BookDetailsDTO.builder()
                .isbn(bookNode.getKey().replace(ISBN_KEY_SUBSTRING, ""))
                .title(bookNode.getValue().get(TITLE).textValue())
                .numberOfPages(getNumberOfPages(bookNode.getValue()))
                .publishers(getPublishers(bookNode.getValue()))
                .coverUrl(getCoverUrl(bookNode.getValue()))
                .authors(getAuthors(bookNode.getValue()))
                .publishDate(getPublishDate(bookNode.getValue()))
                .build();
    }

    private Integer getNumberOfPages(JsonNode bookNode) {
        if(bookNode.get(NUMBER_OF_PAGES) != null ) {
            return bookNode.get(NUMBER_OF_PAGES).intValue();
        }
        else return null;
    }

    private String getPublishDate(JsonNode bookNode) {
        if(bookNode.get(PUBLISH_DATE) != null) {
            return bookNode.get(PUBLISH_DATE).textValue();
        }
        else return null;
    }

    private String getPublishers(JsonNode bookNode) {
        return StreamSupport
                .stream(bookNode.get(PUBLISHERS).spliterator(), false)
                .map(node -> node.get(NAME))
                .map(JsonNode::textValue)
                .reduce((publisher1, publisher2) -> publisher1.concat(", ").concat(publisher2)).orElse("");

    }

    private String getCoverUrl(JsonNode jsonNode) {
        if(jsonNode.get(COVER) != null) {
            return jsonNode.get(COVER).get(LARGE).textValue();
        }
        else return "";
    }

    private String getAuthors(JsonNode jsonNode) {
        return StreamSupport
                .stream(jsonNode.get(AUTHORS).spliterator(), false)
                .map(node -> node.get("name").textValue())
                .reduce((author1, author2) -> author1.concat(", ").concat(author2)).orElse("");
    }

}

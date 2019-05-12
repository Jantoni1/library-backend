package jantoni1.librarybackend.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static jantoni1.librarybackend.constants.Constants.*;

public class BookDeserializer extends StdDeserializer<BookDTO> {


    public BookDeserializer() {
        this(null);
    }

    public BookDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BookDTO deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode root = parser.getCodec().readTree(parser);
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(root.fields(), Spliterator.IMMUTABLE), false)
                .filter(node -> node.getKey().contains(ISBN_KEY_SUBSTRING))
                .findAny()
                .map(this::parseBookNode)
                .orElse(null);
    }

    private BookDTO parseBookNode(Map.Entry<String, JsonNode> bookNode) {
        return BookDTO.builder()
                .isbn(bookNode.getKey().replace(ISBN_KEY_SUBSTRING, ""))
                .title(bookNode.getValue().get(TITLE).textValue())
                .numberOfPages(bookNode.getValue().get(NUMBER_OF_PAGES).intValue())
                .publishers(getPublishers(bookNode.getValue()))
                .coverUrl(getCoverUrl(bookNode.getValue()))
                .authors(getAuthors(bookNode.getValue()))
                .publishDate(bookNode.getValue().get(PUBLISH_DATE).textValue())
                .build();
    }

    private String getPublishers(JsonNode bookNode) {
        return StreamSupport
                .stream(bookNode.get(PUBLISHERS).spliterator(), false)
                .map(node -> node.get(NAME))
                .map(JsonNode::textValue)
                .reduce((publisher1, publisher2) -> publisher1.concat(", ").concat(publisher2)).orElse("");

    }

    private String getCoverUrl(JsonNode jsonNode) {
        return jsonNode.get(COVER).get(LARGE).textValue();
    }

    private String getAuthors(JsonNode jsonNode) {
        return StreamSupport
                .stream(jsonNode.get(AUTHORS).spliterator(), false)
                .map(node -> node.get("name").textValue())
                .reduce((author1, author2) -> author1.concat(", ").concat(author2)).orElse("");
    }

}

package calendar.serialization;

import calendar.domain.Event;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class CalendarSerializer {

    private static final TypeReference<List<Event>> EVENT_LIST_TYPE = new TypeReference<>() {};

    private final ObjectMapper mapper = new ObjectMapper();

    public String serialize(List<Event> events) throws IOException {
        return mapper.writerFor(EVENT_LIST_TYPE).writeValueAsString(events);
    }

    public List<Event> deserialize(String json) throws IOException {
        return mapper.readValue(json, EVENT_LIST_TYPE);
    }
}

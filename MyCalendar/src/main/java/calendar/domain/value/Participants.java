package calendar.domain.value;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Participants {

    private final List<EventTitle> names;

    private Participants(List<EventTitle> names) {
        this.names = List.copyOf(names);
    }

    public static Participants of(EventTitle... names) {
        return new Participants(Arrays.asList(names));
    }

    public static Participants of(List<EventTitle> names) {
        return new Participants(names);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participants)) return false;
        return Objects.equals(names, ((Participants) o).names);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names);
    }

    @Override
    public String toString() {
        return names.stream().map(EventTitle::toString).collect(Collectors.joining(", "));
    }
}

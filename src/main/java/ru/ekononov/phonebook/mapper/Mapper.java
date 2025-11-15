package ru.ekononov.phonebook.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<S, R> {
    R map(S source);

    default List<R> map(List<S> source) {
        return source.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    default R map(S fromObject, R toObject) {
        return toObject;
    }
}

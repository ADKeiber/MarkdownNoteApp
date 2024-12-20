package com.adk.markdownnoteapp.errorhandling;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Entity Not Found Exception class used when attempting to retrieve an object from a database
 * but that object doesn't exist
 */
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -9197464728969353686L;

    public EntityNotFoundException(Class<?> clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    /**
     * Generates the message for the failure
     * @param entity	the name of the class the user was attempting to retrieve
     * @param searchParams the search params used to look for the object
     * @return the message for the exception
     */
    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) +
                " was not found for parameters " +
                searchParams;
    }

    /**
     * Maps search parameters that the user used when attempting to retrieve an object
     * @param keyType key type
     * @param valueType	value type
     * @param entries the search params
     * @return a map containing the search params
     */
    private static Map<String, String> toMap(
            Class<String> keyType, Class<String> valueType, String... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }

}
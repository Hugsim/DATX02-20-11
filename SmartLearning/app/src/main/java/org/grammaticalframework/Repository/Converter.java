package org.grammaticalframework.Repository;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converter {

    @TypeConverter
    public String fromList(List<String> strings) {
        StringBuilder string = new StringBuilder();
        for(String s : strings) string.append(s).append(",");
        return string.toString();
    }

    @TypeConverter
    public List<String> toList(String concatenatedStrings) {
        return new ArrayList<>(Arrays.asList(concatenatedStrings.split(",")));
    }
}

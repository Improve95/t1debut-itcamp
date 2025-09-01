package ru.t1debut.itcamp.consent.util.mapper;

import org.mapstruct.Named;

import static ru.t1debut.itcamp.consent.util.mapper.MapperUtil.MAPPER_UTIL_NAME;

@Named(MAPPER_UTIL_NAME)
public class MapperUtil {

    public static final String MAPPER_UTIL_NAME = "MapperUtil";

    public static final String CONVERT_PHONE_TO_NUMBER = "convert_phone_to_number";

    @Named(CONVERT_PHONE_TO_NUMBER)
    public static long convertPhoneToNumber(String phone) {
        return Long.parseLong(phone.replaceAll("/+", ""));
    }
}

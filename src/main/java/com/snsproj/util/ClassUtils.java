package com.snsproj.util;

import java.util.Optional;

public class ClassUtils {

    // 캐스팅 할 때 safe 하게 하기 위해 사용
    public static <T> Optional<T> getSafeInstance(Object o, Class<T> clazz) {
        return clazz != null && clazz.isInstance(o) ? Optional.of(clazz.cast(o)) : Optional.empty();
    }
}

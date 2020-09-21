package com.onesports.editor.utils;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: xiejr
 * @date 2019-06-04
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    /**
     * 判断2个集合是否相同
     *
     * @param c1 集合1
     * @param c2 集合2
     * @return 比对结果
     */
    public static <E> boolean equals(Collection<E> c1, Collection<E> c2) {
        if (c1 == null && c2 == null) {
            return true;
        }
        if (c1 == null || c2 == null) {
            return false;
        }
        if (c1.size() == c2.size()) {
            Set<E> originUserIdSet = new HashSet<>(c1);
            Set<E> userIdSet = new HashSet<>(c2);
            originUserIdSet.removeAll(userIdSet);
            return isEmpty(originUserIdSet);
        }
        return false;
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 获取集合中每一个元素的指定字段的值
     * @param collection 集合
     * @param fieldMapper 字段匹配
     * @param <T> 集合泛型
     * @param <R> 字段类型
     * @return .
     */
    public static <T, R> List<R> getFieldValues(Collection<T> collection, Function<? super T, ? extends R> fieldMapper) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream()
                .map(fieldMapper)
                .collect(Collectors.toList());
    }

    public static <T> Optional<T> getFirstByField(Collection<T> collection, Predicate<? super T> predicate) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        return collection.stream()
                .filter(predicate)
                .findFirst();
    }

}

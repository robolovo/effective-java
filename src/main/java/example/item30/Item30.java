package example.item30;

import java.util.*;

/*
 *  아이템 30. 이왕이면 제네릭 메서드로 만들라.
 *
 *  클라이언트에서 입력 매개변수와 반환값을 명시적으로 형변환해야 하는 메서드보다 제네릭 메서드가 더 안전하며 사용하기도 쉽다.
 *  타입과 마찬가지로, 메서드도 형변환 없이 사용할 수 있는 편이 좋으며, 많은 경우 그렇게 하려면 제네릭 메서드가 되어야 한다.
 *  그러므로 형변환을 해줘야 하는 기존 메서드는 제네릭하게 만들자.
 */
public class Item30 {

    /*
     *  제네릭 메서드 기본 예제
     */
    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>();
        result.addAll(s2);
        return result;
    }

    /*
     *  재귀적 타입 한정을 이용해 상호 비교할 수 있음을 표현한다.
     *  <E extends Comparable<E>>는 "모든 타입 E는 자신과 비교할 수 있다"라고 읽을 수 있다.
     */
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty()) {
            throw new IllegalArgumentException("컬렉션이 비어 있습니다.");
        }

        E result = null;

        for (E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        return result;
    }
}

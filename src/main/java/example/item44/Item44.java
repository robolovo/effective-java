package example.item44;

import java.util.*;
import java.util.function.*;

/*
 *  아이템 44. 표준 함수형 인터페이스를 사용하라.
 *
 *  표준 함수형 인터페이스의 장점
 *  - API가 다루는 개념의 수가 줄어들어 익히기 더 쉬워진다.
 *  - 유용한 디폴트 메서드를 많이 제공하므로 다른 코드와의 상호운영성도 크게 좋아진다.
 *
 *  자바 표준 함수형 인터페이스 - 총 43개의 함수형 인터페이스를 제공한다.
 *  https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
 *  -> 기본 인터페이스 6개만 기억하면 나머지는 유추해낼 수 있다.
 *     - UnaryOperator<T> : 인수를 1개 받고, 반환값이 있음 (인수의 타입 == 반환값의 타입)
 *     - BinaryOperator<T> : 인수를 2개 받고, 반환값이 있음 (인수의 타입 == 반환값의 타입)
 *     - Predicate<T> : 인수를 1개 받고, boolean 타입의 반환값이 있음
 *     - Function<T, R> : 인수를 1개 받고, 반환값이 있음 (인수의 타입 != 반환값의 타입)
 *     - Supplier<T> : 인수를 받지 않고, 반환값이 있음
 *     - Consumer<T> : 인수를 1개 받고, 반환값이 없음
 *
 *  ⚠️ 표준 함수형 인터페이스를 사용하되, 아래의 특성 중 하나 이상 만족한다면 전용 함수형 인터페이스를 구현해야 하는 건 아닌지 고민해 봐도 좋다.
 *      - 자주 쓰이며, 이름 자체가 용도를 명확히 설명해준다.
 *      - 반드시 따라야 하는 규약이 있다.
 *      - 유용한 디폴트 메서드를 제공할 수 있다.
 *     Comparator<T> 인터페이스는 위의 세가지 특성을 모두 만족하여 ToIntBiFunction<T, U>이라는 표준 함수형 인터페이스와 구조적으로 동일함에도
 *     독자적인 인터페이스로 살아남았다.
 *
 *  ⚠️ 직접 만든 함수형 인터페이스에는 항상 @FunctionalInterface 애너테이션을 사용해서 의도를 명확히 하자.
 *      - 해당 클래스의 코드나 설명 문서를 읽을 이에게 그 인터페이스가 람다용으로 설계된 것임을 알려준다.
 *      - 해당 인터페이스가 추상 메서드를 오직 하나만 가지고 있어야 컴파일되게 해준다.
 *      - 유지보수 과정에서 누군가 실수로 메서드를 추가하지 못하게 막아준다.
 *
 *  📍 결론
 *   - 필요한 용도에 맞는게 있다면, 불필요하게 직접 구현하지 말고 표준 함수형 인터페이스를 활용하자.
 *   - 보통은 java.util.function 패키지의 표준 함수형 인터페이스를 사용하는 것이 가장 좋은 선택이다.
 */
public class Item44 {

    public static void main(String[] args) {
        MyMap<Integer, String> map = new MyMap<>();

        map.put(1, "java");
        map.put(2, "python");
        map.put(3, "solidity");
        map.put(4, "javascript");

        map.removeSmallMapEldest();
        map.removeBigMapEldest();
    }
}

class MyMap<K, V> extends MyLinkedHashMapWithStandardFi<K, V> {
    private static final int SMALL_SIZE = 3;
    private static final int BIG_SIZE = 100000;

    transient Map.Entry<K, V> head;

    public void removeSmallMapEldest() {
        Map.Entry<K, V> first = head;
        if (removeEldestEntry(this, first, (m, e) -> m.size() > SMALL_SIZE)) {
            System.out.println(this.size());
        }
    }

    public void removeBigMapEldest() {
        Map.Entry<K, V> first = head;
        if (removeEldestEntry(this, first, (m, e) -> m.size() > BIG_SIZE)) {
            System.out.println(this.size());
        }
    }
}

class MyLinkedHashMapWithStandardFi<K, V> extends HashMap<K, V> {
    // ...

    // 자바 표준 함수형 인터페이스인 BiPredicate를 사용한다.
    protected <T, U> boolean removeEldestEntry(
            Map<T, U> map,
            Map.Entry<T, U> eldest,
            BiPredicate<? super Map<T, U>, ? super Map.Entry<T, U>> bp
    ) {
        return bp.test(map, eldest);
    }

    // ...
}

@FunctionalInterface  // 직접 작성한 함수형 인터페이스
interface EldestEntryRemovalFunction<T, U> {
    boolean remove(Map<T, U> map, Map.Entry<T, U> eldest);
}

class MyLinkedHashMapWithCustomFi<K, V> extends HashMap<K, V> implements Map<K, V> {
    // ...

    /*
     *  직접 작성한 함수형 인터페이스인 EldestEntryRemovalFunction을 사용한다.
     *  별 다른 이유가 없으면 표준 함수형 인터페이스를 사용하여 장점을 누리자.
     */
    protected <T, U> boolean removeEldestEntry(
            Map<T, U> map,
            Map.Entry<T, U> eldest,
            EldestEntryRemovalFunction<T, U> f
    ) {
        return f.remove(map, eldest);
    }

    // ...
}


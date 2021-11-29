package example.item44;

import java.util.*;
import java.util.function.*;

/*
 *  아이템 44. 표준 함수형 인터페이스를 사용하라.
 *
 *  표준 함수형 인터페이스를 활용하면 API가 다루는 개념의 수가 줄어들어 익히기 더 쉬워진다.
 *  또한, 표준 함수형 인터페이스들은 유용한 디폴트 메서드를 많이 제공하므로 다른 코드와의 상호운영성도 크게 좋아질 것이다.
 *
 *  자바 표준 함수형 인터페이스 - 총 43개의 함수형 인터페이스를 제공한다.
 *  https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
 *  43개의 함수형 인터페이스를 모조리 외울 순 없으니, 네이밍 컨벤션을 알아두면 적재적소에 필요한 함수형 인터페이스를 쉽게 찾을수 있을 것 같다.
 *
 *  ⚠️ 표준 함수형 인터페이스를 사용하되, 아래의 특성 중 하나 이상 만족한다면 전용 함수형 인터페이스를 구현해야 하는 건 아닌지 고민해 봐도 좋다.
 *      1. 자주 쓰이며, 이름 자체가 용도를 명확히 설명해준다.
 *      2. 반드시 따라야 하는 규약이 있다.
 *      3. 유용한 디폴트 메서드를 제공할 수 있다.
 *     Comparator<T> 인터페이스는 위의 세가지 특성을 모두 만족하여 ToIntBiFunction<T, U>이라는 표준 함수형 인터페이스와 구조적으로 동일함에도
 *     독자적인 인터페이스로 살아남았다.
 *
 *  ⚠️ 직접 만든 함수형 인터페이스에는 항상 @FunctionalInterface 애너테이션을 사용해서 의도를 명확히 하자.
 */
public class Item44 {

    public static void main(String[] args) {
        Operation operation = new Operation((n1, n2) -> n1 * n2);
        Integer result = operation.apply(100, 3);
        System.out.println("result = " + result); // result = 300

        MyLinkedHashMap<String, String> myMap = new MyLinkedHashMap<>((m, e) -> {
            if (m.size() > 1) {
                System.out.println("key: " + e.getKey() + ", " + "value: " + e.getValue());
                return true;
            }
            System.out.println("m = " + m);
            return false;
        });

        Map<String , String> map = new HashMap<>();

        map.put("java", "spring");
        map.put("python", "django");

        Set<Map.Entry<String, String>> entries = map.entrySet();

        entries.forEach(m -> myMap.test(map, m));
    }
}

class Operation {
    private final BiFunction<Integer, Integer, Integer> op;

    public Operation(BiFunction<Integer, Integer, Integer> op) {
        this.op = op;
    }

    public Integer apply(Integer n1, Integer n2) {
        return op.apply(n1, n2);
    }
}

class MyLinkedHashMap<K, V> {
    private final BiPredicate<Map<K, V>, Map.Entry<K, V>> bp;

    MyLinkedHashMap(BiPredicate<Map<K, V>, Map.Entry<K, V>> bp) {
        this.bp = bp;
    }

    public boolean test(Map<K, V> map, Map.Entry<K, V> eldest) {
        return bp.test(map, eldest);
    }
}


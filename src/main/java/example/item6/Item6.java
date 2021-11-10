package example.item6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 *   아이템 6. 불필요한 객체 생성을 피하라.
 *   똑같은 기능의 객체를 매번 생성하기보다는 객체 하나를 재사용하는게 나을 때가 많다.
 */
public class Item6 {

    @Test
    public void type_test() {
        long start = System.currentTimeMillis();
        Long sum = 0L;  // 박싱된 기본 타입
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;  // 이 과정에서 불필요한 Long 인스턴스가 2^31개나 만들어 진다.
        }
        System.out.println(System.currentTimeMillis() - start); // 2790


        long _start = System.currentTimeMillis();
        long _sum = 0L;  // 기본 타입으로
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            _sum += i;
        }
        System.out.println(System.currentTimeMillis() - _start); // 685 -> 4배정도 빨라졌다.
    }

    @Test
    public void map_keySet_Test() {
        Map<Integer, Integer> map = new HashMap<>();

        Set<Integer> keySet1 = map.keySet();
        Set<Integer> keySet2 = map.keySet();

        assertSame(keySet1, keySet2); // Test Success -> Map 인터페이스의 keySet 메서드는 새로운 인스턴스가 아닌 같은 인스턴스를 반환한다.
    }
}

/*
*   Item 클래스안에 heavyString 이 매우 무거운 객체라고 가정한다.
*   heavyString을 static final로 선언하여 한번 생성된 객체를 계속해서 재사용한다.
*   만약 아래의 getHeavyString이라는 메서드가 한번도 호출되지 않는다면 heavyString은 쓸데없이 초기화되는 꼴이다.
*   이 때, 지연 초기화로 불필요한 초기화를 없앨 수는 있지만, 권장되는 방법은 아니라고 한다.(지연 초기화는 코드를 복잡하게 만들고, 성능은 크게 개선되지 않을 때가 많다.)
*/
class Item {
    private static final String heavyString = "Create only once";

    static String getHeavyString() {
        return heavyString;
    }
}
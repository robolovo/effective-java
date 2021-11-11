package example.item7;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.WeakHashMap;

/*
 *   아이템 7. 다 쓴 객체 참조를 해제하라.
 *
 *   이번 절은 GC(가비지 컬렉터)와 메모리 누수에 관한 내용을 집중적으로 정리하였다.
 *
 *   c언어 같은 경우, malloc()이나 free()같은 메소드를 사용해 프로그래머가 명시적으로 메모리를 할당받고 해제하는 작업을 해야한다.
 *   특히, 할당받았던 메모리를 free()함수를 호출해서 직접 해제해주지 않는다면 메모리 누수가 발생하게 되고 프로그램에 악영향을 끼칠 수 있다.
 *   이와 반대로, 자바는 GC가 메모리 관리를 해준다. https://d2.naver.com/helloworld/1329(Java Garbage Collection)
 *   대부분의 경우 가비지 컬렉터가 적절하게 작업을 수행하긴 하지만, 프로그래머가 메모리 관리에 더 이상 신경쓰지 않아도 된다는 것은 아니다. (GC가 쓸모없는 객체라는 것을 알아차리지 못할 수도 있다.)
 *
 *   해당 객체의 참조를 해제하기 위해서는 null 처리(참조 해제)를 해주거나 System.gc()메소드를 호출 하면된다. (하지만, System.gc()를 호출하는 방법은 시스템의 성능에 매우 큰 영향을 끼치므로 절대 사용하면 안된다.)
 *   책의 예제 코드에서는 스택의 pop() 메소드에서 원소를 반환할 때, 해당 원소를 null 처리를 통해 참조 해제한다. (실제 라이브러리에 구현된 Stack도 null 처리를 해준다.)
 *
 *   다만, 실전에서는 모든 안쓰는 객체를 찾아서 일일이 null 처리를 해줄 필요는 없고, 객체 참조를 null 처리하는 일은 예외적인 경우여야 한다.
 *   다 쓴 참조를 해제하는 좋은 방법은 그 참조를 담은 변수를 유효 범위(scope) 밖으로 밀어내면 되는데, 그렇게하면 GC가 알아서 처리해준다.
 *   GC가 언제 작동하는 지에 대한 예제 코드는 아래에 작성하였다.
 *
 */
public class Item7 {
    /*
     *   책에 나온 예제 코드를 그대로 가져왔다.
     */
    static class Stack {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }

        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }

        public Object pop() {
            if (size == 0)
                throw new EmptyStackException();
            Object result = elements[--size];
            elements[size] = null; // 다 쓴 참조 해제 (null 처리)
            return result;
        }

        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    /*
     *   GC는 언제 작동하는 가
     */
    public String example1_gc() {
        String topic = "Garbage Collection"; // 토픽이라는 변수가 JVM의 스택에 만들어지고 해당 문자열이 힙 영역에 생성된다.
        topic += "is ..."; // String 불변이기 때문에 새로운 문자열이 힙 영역에 생성되고 원래 문자열은 해제되지 않은채로 그대로 남아있게 된다.
                           // 이 때, 기존의 문자열은 unreachable 상태가 되고 GC에 의해서 메모리에서 제거되므로 걱정하지 않아도 된다.
        return topic;
    }

    public Integer example2_gc() {
        Integer number1 = 1;
        Integer number2 = 2;

        Integer result = number1 + number2;
        // number1 = null;  -> 이 메소드가 종료되면(스코프에서 벗어나게 되면)알아서 GC에의해 정리되기 때문에
        // number2 = null;  -> 불필요한 작업이다. 일일이 null처리를 통해 참조해제를 해줄 필요가 없다.
        return result;
    }

    /*
    *   스프링에는 3가지 주요 유형의 참조 방식이 존재한다.
    *   1. Strong Reference(강한 참조) -> 일반적인 참조 방식이다. GC의 대상이 되지 않는다.
    *   2. Soft Reference(부드러운 참조) -> 메모리가 부족할 경우에 GC의 대상이 된다.
    *   3. Weak Reference(약한 참) -> GC가 발생하는 시점에 무조건 메모리에서 제거된다.
    */
    public void example3_gc() {
        // WeakHashMap 공식 문서 설명
        // Hash table based implementation of the Map interface, with weak keys.
        // An entry in a WeakHashMap will automatically be removed when its key is no longer in ordinary use.
        Map<Object, Object> cache = new WeakHashMap<>();

        Object key = new Object(), value = new Object();
        cache.put(key, value);

        key = null; // 키만 null로 만들어주어도 GC에 의해 해당 엔트리는 자동으로 제거된다.
    }

}

package example.item31;

import java.util.HashSet;
import java.util.Set;

/*
 *  아이템 31. 한정적 와일드카드를 사용해 API 유연성을 높여라.
 *
 *  와일드카드 공식
 *  - PECS : Producer-Extends, Consumer-Super
 *  - ex) Comparable, Comparator는 consumer이다.
 *
 *  ⚠️ 반환 타입은 한정적 와일드카드 타입을 사용하면 안된다.
 *  - 클라이언트까지 와일드카드 타입을 써야하는 경직성이 생긴다.
 *  - 클래스를 사용하는 클라이언트 코드에서 와일드카드 타입을 신경써야한다면 API에 문제가 있을 가능성이 크다.
 */
public class Item31 {
//    방식1
//    public class Stack1<E> {
//        public void pushAll(Iterable<E> src);
//        public void popAll(Collection<E> dst);
//    }

//    방식2
//    public class Stack2<E> {
//        public void pushAll(Iterable<? extends E> src);  // put, produce
//        public void popAll(Collection<? super E> dst);  // get, consume
//    }

    /*
     *  매개변수에는 한정적 와일드카드 타입 적용.
     *  반환 타입은 여전히 Set<E>임에 주목하자. 반환 타입에는 한정적 와일드카드 타입을 사용하면 안된다!
     */
    public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
        Set<E> result = new HashSet<>();
        result.addAll(s2);
        return result;
    }

}

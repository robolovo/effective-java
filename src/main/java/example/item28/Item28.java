package example.item28;

import java.util.ArrayList;
import java.util.List;

/*
 *  아이템 28. 배열보다는 리스트를 사용하라.
 *
 *  - 서론
 *    평소에도 타입 일관성을 위해서 당연하다는 듯이 배열보다는 리스트를 사용하고 있었다.
 *    하지만 이번 아이템을 읽고나서 그 이유에 대해서 더 자세하게 알 수 있었고, 공변, 불공변, 실체화 타입등 새로운 용어에 대해서도 알게 되었다.
 *    (제네릭 타입 정보가 런타임에는 소거된다는 것도 처음 알았다.)
 *
 *  - 본론
 *    배열과 제네릭 타입에는 중요한 차이가 두가지 있다.
 *    1. 공변(covariant), 불공변(invariant)
 *      - 배열은 공변(함께 변한다는 뜻)이다. Sub가 Super의 하위 타입이라면 배열 Sub[]는 배열 Super[]의 하위 타입이 된다는 뜻이다.
 *      - 반면, 제네릭은 불공변이다. 즉, 서로 다른 타입 Type1과 Type2가 있을 때, List<Type1>은 List<Type2>의 하위 타입도 아니고 상위 타입도 아니다.
 *    2. 실체화(reify)
 *      - 배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다.
 *      - 반면, 제네릭은 타입 정보가 런타임에는 소거된다. 원소 타입을 컴파일타임에만 검사하면 런타임에는 알수조차 없다는 뜻이다.
 *
 *    이상의 주요한 차이로 인해 배열과 제네릭은 함께 사용할 수 없다.
 *      - new List<E>[], new List<String>[], new E[] 모두 컴파일 할 때 제네릭 배열 생성 오류를 일으킨다.
 *
 *   결론은 간단한다.
 *    오류는 가능한 한 발생 즉시, 이상적으로는 컴파일할 때 발견하는 것이 좋다. 그러니 별 이유(성능 ?)가 없다면 리스트<T>를 사용하라 !
 */
public class Item28 {
    public static void main(String[] args) {
        Object[] arr = new Long[10];
        arr[0] = "타입이 달라 넣을 수 없다.";  // <- 컴파일은 잘 된다. 프로그램을 실행하면 ArrayStoreException을 던진다.

//      List<Object> list = new ArrayList<Long>();  // <- 컴파일할 때 오류를 발견할 수 있다. (사실은 IDE가 그전에 넌지시 알려준다.)
//      list.add("타입이 달라 넣을 수 없다.");
    }
}

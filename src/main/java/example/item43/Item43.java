package example.item43;

import java.util.ArrayList;
import java.util.List;

/*
 *  아이템 43. 람다보다는 메서드 참조(method reference)를 사용하라.
 *
 *  람다가 익명 클래스보다 나은 점 중에서 가장 큰 특징은 간결함이다.
 *  그런데 자바에는 함수 객체를 심지어 람다보다도 더 간결하게 만드는 방법이 있으니, 바로 메서드 참조(method reference)이다.
 *  메서드 참조는 람다의 간단명료한 대안이 될 수 있다.
 *  메서드 참조를 사용했을 때 코드가 더 짧고 명확해 진다면 메서드 참조를 쓰고, 그렇지 않을 때만 람다를 사용하자.
 *
 *  - 생각해보기
 *  코드를 읽을 때, 메서드 참조는 메서드의 이름만으로 동작을 유추해야 한다.
 *  물론 메서드 참조가 람다보다 간결하다는 장점이 있지만, 코드를 분석할 때 람다의 매개변수 이름 자체가 프로그래머에게 좋은 가이드가 되기도 한다.
 *  람다와 메서드 참조, 어떤 상황에서 어느 것이 옳은 것인가?
 */
public class Item43 {
    public static void main(String[] args) {
        List<String> nums = new ArrayList<>();

        nums.add("1");
        nums.add("2");
        nums.add("3");
        nums.add("4");

        int sum = nums.stream()
                .map(Integer::parseInt) // 정적
                .mapToInt(i -> i)
                .sum();

        System.out.println("sum = " + sum); // sum = 10

        List<String> countryCodes = new ArrayList<>();
        countryCodes.add("kr");
        countryCodes.add("us");
        countryCodes.add("jp");

        countryCodes.stream()
                .map(String::toUpperCase)  // 한정적(인스턴스)
                .forEach(System.out::println);  // 정적

        // () -> new TreeMap<K, V>()  ---->  TreeMap<K, V>::new
        // len -> new int[len]        ---->  int[]::new
    }
}

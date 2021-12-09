package example.item46;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/*
 *  아이템 46. 스트림에서는 부작용 없는 함수를 사용하라.
 *
 *  스트림은 그저 또 하나의 API가 아닌, 함수형 프로그래밍에 기초한 패러다임이다.
 *  그러므로, 스트림이 제공하는 표현력, 속도, 병렬성을 얻으려면 API는 말할 것도 없고 이 패러다임까지 함께 받아들여야 한다.
 *
 *  핵심은 스트림에서 각 변환 단계는 가능한 한 이전 단계의 결과를 받아 처리하는 순수 함수라는 것!
 *  순수 함수란?
 *  - 오직 입력만이 결과에 영향을 주는 함수. 다른 가변 상태를 참조하지 않고, 함수 스스로도 다른 상태를 변경하지 않는다.
 *  - 중간 단계든 종단 단계든 스트림 연산에 건네는 함수 객체는 모두 부작용(side effect)이 없어야 한다.
 *
 *  부작용 없애기 !
 *  - 스트림 파이프라인 프로그래밍의 핵심은 부작용 없는 함수 객체에 있다.
 *  - 스트림뿐 아니라 스트림 관련 객체에 건네지는 모든 함수 객체가 부작용이 없어야 한다.
 *
 *  스트림을 올바로 사용하려면 수집기를 잘 알아둬야 한다.
 *  - 그중 주용한 수집기는 toList(), toSet(), toMap(), groupingBy(), joining()이다.
 *
 */
public class Item46 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Stream<String> words = new Scanner(file).tokens();

        // groupingBy
        Map<String, Long> frequency =
                words.collect(groupingBy(String::toLowerCase, counting())); // key: 문자열, value: 문자열 빈도수

        frequency.forEach((key, value) -> System.out.println(key + " : " + value));

        // toList
        List<String> topTenFreqWord = frequency.keySet()
                .stream()
                .sorted(comparing(frequency::get).reversed())
                .limit(5)
                .collect(Collectors.toList());  // 빈도수가 가장 높은 5개의 단어를 수집하여 List로 변환

        topTenFreqWord.forEach(System.out::println);

        // toMap
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("javascript");
        list.add("python");

        Map<String, Integer> map = list.stream()
                .collect(toMap(String::toLowerCase, String::length)); // key: 문자열, value: 문자열의 길이

        map.forEach((key, value) -> System.out.println(key + " : " + value));

        // joining
        Stream<String> words2 = new Scanner(file).tokens();

        String join = words2.collect(joining(", "));

        System.out.println(join);
    }

}

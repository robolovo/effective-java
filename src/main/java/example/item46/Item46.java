package example.item46;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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
 */
public class Item46 {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);

        Stream<String> words = new Scanner(file).tokens();
        final Map<String, Long> finalFreq = new HashMap<>();
        words.forEach(word -> finalFreq.merge(word.toLowerCase(), 1L, Long::sum));

        Map<String, Long> freq = new HashMap<>();
        freq = words.collect(groupingBy(String::toLowerCase, counting()));
    }
}

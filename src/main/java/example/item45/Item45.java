package example.item45;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 *  아이템 45. 스트림은 주의해서 사용하라.
 *
 *  스트림(Stream)은요..
 *  - 스트림 API는 다량의 데이터 처리 작업(순차적이든 병렬적이든)을 돕고자 자바 8에 추가되었다.
 *  - 스트림(Stream)은 데이터 원소의 유한 혹은 무한 시퀀스(sequence)를 뜻한다.
 *  - 스트림 파이프라인은 이 원소들로 수행하는 연산 단계를 표현하는 개념이다.
 *  - 소스 스트림에서 시작해 종단 연산으로 끝나며, 그 사이에 하나 이상의 중간 연산이 있을 수 있다.
 *  - 스트림 파이프라인은 지연 평가(lazy evaluation)된다.
 *  - 스트림 API는 메서드 연쇄를 지원한다.
 *
 *  스트림으로는 불가능한 것들.
 *  - 람다에서는 사실상 final인 변수만 읽을 수 있고, 지역변수를 수정하는 건 불가능하다.
 *  - return문을 사용해 메서드에서 빠져나가기,
 *  - break나 continue문으로 블록 바깥의 반복문을 종료하거나 반복을 한 번 건너뛰기,
 *  - 메서드 선언에 명시된 검사 예외 던지기 불가능 하다.
 */
public class Item45 {
    /*
       다음 일들에는 스트림이 안성맞춤이다.

        1. 원소들의 시퀀스를 일관되게 변환한다.
        List<Integer> nums = strNums.stream()
                .map(str -> Integer.parseInt(str))
                .collect(Collectors.toList());

        2. 원소들의 시퀀스를 하나의 연산을 사용해 결합한다.
        int sum = nums.stream()
                .mapToInt(n -> n)
                .sum();

        3. 원소들의 시퀀스를 필터링 한다.
        List<User> adultGroup = users.stream()
                .filter(user -> user.getAge() > 19)
                .collect(Collectors.toList());

        4. 원소들의 시퀀스에서 특정 조건을 만족하는 원소를 찾는다.
        User programmer = users.stream()
                .filter(user -> user.getJob().equals("programmer"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("개발자가 없어요."));

        5. 원소들의 시퀀스를 컬렉션에 모은다.

      */
}

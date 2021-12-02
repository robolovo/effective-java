package example.item42;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import static java.util.Comparator.*;

/*
 *   아이템 42. 익명 클래스보다는 람다를 사용하라.
 *
 *   람다가 사용 가능 하다면 람다를 사용하자.
 *   - 람다를 사용하면 자질구레한 코드들이 사라지고 어떤 동작을 하는지가 명확하게 드러난다. (컴파일러가 문맥을 살펴 타입을 추론해주기 때문에 자질구레한 코드들은 생략이 가능하다.)
 *   - 그러나 상황에 따라 컴파일러가 타입을 결정하지 못할 수도 있는데, 그럴 때는 프로그래머가 직접 명시해야 한다.
 *
 *   ⚠ 메서드나 클래스와 달리, 람다는 이름이 없고 문서화도 못한다. 따라서 코드 자체로 동작이 명확히 설명되지 않거나 코드 줄 수가 많아지면 람다를 쓰지 말아야 한다.
 *     람다가 길거나 읽기 어렵다면 더 간단히 줄여보거나 람다를 쓰지 않는 쪽으로 리팩터링하길 바란다.
 *
 *   람다를 사용하지 못하는 경우도 있다.
 *   람다는 함수형 인터페이스에서만 쓰인다. 그러므로, 추상 클래스의 인스턴스를 만들 때, 추상 메서드가 여러 개인 인터페이스의 인스턴스를 만들 때는 익명 클래스를 사용해야 한다.
 */
public class Item42 {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("hello");
        words.add("effective");
        words.add("java");

        // 익명 클래스
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        // 람다
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        // 비교자 생성 메서드
        Collections.sort(words, comparingInt(String::length));

        // 자바 8부터 List 인터페이스에 추가된 sort 메서드
        words.sort(comparingInt(String::length));

        double plus = Operation.PLUS.apply(1, 10);
        double minus = Operation.MINUS.apply(1, 10);
        double times = Operation.TIMES.apply(1, 10);
        double divide = Operation.DIVIDE.apply(1, 10);

        System.out.println("apply = " + plus);
        System.out.println("apply = " + minus);
        System.out.println("apply = " + times);
        System.out.println("apply = " + divide);
    }
}

enum Operation {
    PLUS ("+", (x, y) -> x + y),
    MINUS ("-", (x, y) -> x - y),
    TIMES ("*", (x, y) -> x * y),
    DIVIDE ("/", (x, y) -> x / y);

    private final String symbol;
    private final DoubleBinaryOperator op;

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }
}

/*
 *   함수 객체(람다)를 인스턴스 필드에 저장해 상수별 동작을 구현한 열거타입
 */
enum PaymentType {
    /*
     *   자세한 구현은 생략하였고, 간단하게 결제방식에 따라 출력문을 바꿔서 출력한다.
     */
    NAVER_PAY("네이버페이", () -> System.out.println("네이버페이 결제를 완료하였습니다.")),
    KAKAO_PAY("카카오페이", () -> System.out.println("카카오페이 결제를 완료하였습니다.")),
    SAMSUNG_PAY("삼성페이", () -> System.out.println("삼성페이 결제를 완료하였습니다."));

    private final PaymentProcess paymentProcess;

    PaymentType(String name, PaymentProcess paymentProcess) {
        this.paymentProcess = paymentProcess;
    }

    public void doPayment() {
        paymentProcess.doPayment();
    }
}

/*
 *   람다를 사용하기 위해 함수형 인터페이스를 만들어준다.
 */
@FunctionalInterface
interface PaymentProcess {
    void doPayment();
}

/*
 *   테스트 코드
 */
class PayTest01 {
    @Test
    public void payment_test() {
        PaymentType naverPay = PaymentType.NAVER_PAY;
        PaymentType kakaoPay = PaymentType.KAKAO_PAY;
        PaymentType samsungPay = PaymentType.SAMSUNG_PAY;

        naverPay.doPayment();    // 네이버페이 결제를 완료하였습니다.
        kakaoPay.doPayment();    // 카카오페이 결제를 완료하였습니다.
        samsungPay.doPayment();  // 삼성페이 결제를 완료하였습니다.
    }
}

package example.item03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/*
*   아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
*
*   싱글턴(singleton)이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다.
*   싱글턴의 전형적인 예로는 함수와 같은 무상태 객체나 설계상 유일해야 하는 시스템 컴포넌트를 들 수 있다.
*
*   자바 진영의 대표적인 프레임워크인 스프링이 관리하는 빈(컴포넌트)들은 기본적으로 싱글턴으로 생성하고 사용된다.
*   그래서 토비의 스프링이라는 책의 싱글턴 패턴의 한계에 관한 내용에 알아보면 좋을 것 같다.
*
*   1. private 생성자를 갖고 있기 때문에 상속할 수 없다.
*      private 생성자를 가진 클래스는 다른 생성자가 없다면 상속이 불가능하다. 객체지향의 장점인 상속과 이를 이용한 다형성을 적용할 수 없다.
*   2. 싱글턴은 테스트하기가 힘들다.
*      싱글턴은 초기화 과정에서 생성자 등을 통해 사용할 오브젝트를 다이내믹하게 주입하기도 힘들기 때문에 필요한 오브젝트는 직접 오브젝트를 만들어 사용할 수 밖에 없다.
*      이런 경우 테스트용 오브젝트로 대체하기가 힘들다. 테스트는 엔터프라이즈 개발의 핵심인데 테스트를 만드는 데 지장이 있다는 것은 큰 단점이다.
*   3. 서버 환경에서는 싱글턴이 하나만 만들어지는 것을 보장하지 못한다.
*      서버에서 클래스 로더를 어떻게 구성하고 있느냐에 따라서 싱글톤 클래스임에도 하나 이상의 오브젝트가 만들어 질 수 있다.
*      멀티스레드 환경이라면 여러 스레드가 동시에 접근해서 사용할 수 있는데, 따라서 상태관리에 주의를 기울여야 한다. (싱글턴 클래스가 인스턴스 변수를 갖는것은 위험하다.)
*   4. 싱글턴의 사용은 전역 상태를 만들 수 있기 때문에 바람직하지 못하다.
*      싱글턴은 사용하는 클라이언트가 정해져 있지 않다. 싱글턴의 스태틱 메소드를 이용해 언제든지 싱글톤에 쉽게 접글할 수 있기 때문에 애플리케이션 어디서든지 사용될 수 있고,
*      그러다 보면 자연스럽게 전역 상태로 사용되기 쉽다. 아무 객체나 자유롭게 접근하고 수정하고 공유할 수 있는 전역 상태를 갖는 것은 객체지향 프로그래밍에서는 권장되지 않는다.
*
*                                                                                                   - 이일민, '토비의 스프링', p.107
*/
public class Item3 {
    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
        elvis.addAge();

        System.out.println("elvis's age: " + elvis.getAge()); // elvis's age: 2

//      elvis = new Elvis(); <- 컴파일 에러. 새로운 인스턴스를 생성할 수 없다.

        Elvis firstElvis = Elvis.INSTANCE;
        firstElvis.addAge();

        System.out.println("firstElvis's age: " + firstElvis.getAge()); // firstElvis's age: 3, 싱글턴이므로 2에 1을 더한 3이 출력된다.

        ElvisEnum elvisEnum = ElvisEnum.INSTANCE;
        elvisEnum.addAge();
        System.out.println("elvisEnum's age = " + elvisEnum.getAge()); // elvisEnum's age: 2

        ElvisEnum secondElvisEnum = ElvisEnum.INSTANCE;
        secondElvisEnum.addAge();
        System.out.println("secondElvisEnum's age = " + secondElvisEnum.getAge()); // secondElvisEnum's age: 3, 싱글턴이므로 2에 1을 더한 3이 출력된다.
    }

    @Test
    public void instanceEqualityTest() {
        Elvis instance1 = Elvis.INSTANCE;
        Elvis instance2 = Elvis.INSTANCE;

        assertSame(instance1, instance2); // Test Success

        Elvis instance3 = Elvis.getInstance();
        Elvis instance4 = Elvis.getInstance();

        assertSame(instance3, instance4); // Test Success

        ElvisEnum instance5 = ElvisEnum.INSTANCE;
        ElvisEnum instance6 = ElvisEnum.INSTANCE;

        assertSame(instance5, instance6); // Test Success
    }
}

/*
*   싱글턴을 만드는 방식
*   1. public static final 필드 방식의 싱글턴
*   2. 정적 팩토리 방식의 싱글턴
*   3. 열거 타입 방식의 싱글턴
*/

class Elvis {
    private int age = 1;

    // 1번 방식
    public static final Elvis INSTANCE = new Elvis();

    // 2번 방식
    private static final Elvis INSTANCE_ = new Elvis();
    public static Elvis getInstance() {
        return INSTANCE_;
    }

    private Elvis() {}

    public int getAge() {
        return age;
    }

    public void addAge() {
        age++;
    }
}

//3번 방식
enum ElvisEnum {
    INSTANCE;

    private int age = 1;

    public int getAge() {
        return age;
    }

    public void addAge() {
        age++;
    }
}

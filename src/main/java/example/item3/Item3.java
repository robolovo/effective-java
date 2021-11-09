package example.item3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/*
*   아이템 3. private 생성자나 열거 타입으로 싱글턴임을 보증하라.
*
*   싱글턴(singleton)이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다. e.g. 설계상 유일해야 하는 시스템 컴포넌트
*
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

package example.item24;

import org.junit.jupiter.api.Test;

/*
 *  아이템 24. 멤버 클래스는 되도록 static으로 만들라.
 *
 *  스택오버플로 검색하다 알게된 것 < Why are you not able to declare a class as static in Java? >
 *  - Top level classes are static by default.
 *    They are static because you don't need any instance of anything to refer to them.
 *    They can be referred to from anywhere. They're in static storage (as in C storage classes).
 *
 *  - At a high level your question deals with the difference between objects and types.
 *    While there are many cars (objects), there is only one Car class (type).
 *    Declaring something as static means that you are operating in the "type" space. There is only one.
 *    The top-level class keyword already defines a type in the "type" space.
 *    As a result "public static class Car" is redundant.
 */
public class Item24 {
    @Test
    public void outer_inner_test() {
        Outer outer = new Outer();
        Outer.InnerOne inner1 = outer.new InnerOne();
        Outer.InnerTwo inner2 = new Outer.InnerTwo();

        inner1.say();  // I'm InnerOne..
        inner2.say();  // I'm InnerTwo..
        outer.say();   // I'm Outer..
    }
}

class Outer {
    String message = "I'm Outer..";

    public class InnerOne {
        public void say() {
            String innerMessage = message; // Outer 필드에 접근 가능
            System.out.println("I'm InnerOne..");
        }
    }

    //static 클래스는 바깥 클래스와 분리된다. (Outer 객체를 생성하지 않아도 사용가능하다.)
    public static class InnerTwo {
        public void say() {
            // String innerMessage = message;  // Outer 필드에 접근 불가
            System.out.println("I'm InnerTwo..");
        }
    }

    public void say() {
        System.out.println(message);
    }
}

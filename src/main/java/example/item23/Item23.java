package example.item23;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 *  아이템 23. 태그 달린 클래스보다는 클래스 계층구조를 활용하라.
 *
 *  태그 달린 클래스는 장황하고, 오류를 내기 쉽고, 비효율적이다.
 *
 *  태그 달린 클래스를 써야 하는 상황은 거의 없다. 새로운 클래스를 작성하는 데 태그 필드가 등장한다면 태그를 없애고 계층구조로 대체하는 방법을 생각해보자.
 *  기존 클래스가 태그 필드를 사용하고 있다면 계층구조로 리팩터링하는 걸 고민해보자.
 */
public class Item23 {

    @Test
    public void circle() {
        Figure circle = new Circle(5);
        Figure rect = new RECTANGLE(10, 15);

        System.out.println(circle.area()); // 78.53981633974483
        System.out.println(rect.area()); // 150.0
    }

    abstract class Figure {
        abstract double area();
    }

    class RECTANGLE extends Figure {
        final double length;
        final double width;

        RECTANGLE(double length, double width) {
            this.length = length;
            this.width = width;
        }

        @Override
        double area() {
            return length * width;
        }
    }

    class Circle extends Figure {
        final double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        @Override
        double area() {
            return Math.PI * Math.pow(radius, 2);
        }
    }
}

/*
 *  BAD Example
 *  여러 구현이 한 클래스에 혼합되어 있어서 가독성이 나쁘다.
 *  다른 의미를 위한 코드도 언제나 함께 하니 메모리도 많이 사용한다.
 */
class Figure {
    enum Shape { RECTANGLE, CIRCLE }

    // 태그 필드 - 현재 모양을 나타낸다.
    final Shape shape;

    // 다음 필드들은 모양이 사각형(RECTANGLE)일 때만 쓰인다.
    double length;
    double width;

    // 다음 필드는 모양이 원(CIRCLE)일 때만 쓰인다.
    double radius;

    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // 사각형용 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}

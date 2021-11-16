package example.item17;

import org.junit.jupiter.api.Test;

/*
 *   아이템 17. 변경 가능성을 최소화하라.
 *
 *   불변 클래스란 간단히 말해 그 인스턴스의 내부 값을 수정할 수 없는 클래스이다.
 *   불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지 않는다.
 *   불변 클래스는 가변 클래스보다 설계하고 구현하고 사용하기 쉬우며, 오류가 생길 여지도 적고 훨씬 안전하다.
 *   또한, 불변이기 때문에 스레드 세이프하여 따로 동기화할 필요가 없다.
 *   단점으로는 객체가 가지는 값마다 새로운 객체가 필요하기 때문에 메모리 누수와 성능저하를 발생 시킬 수 있다.
 *
 *   클래스를 불변으로 만들려면 다음 다섯 가지 규칙을 따르면 된다.
 *   1. 객체의 상태를 변경하는 메서드(변경자)를 제공하지 않는다.
 *   2. 클래스를 확장할 수 없도록 한다.
 *      하위 클래스에서 부주의하게 혹은 나쁜 의도록 객체의 상태를 변하게 만드는 사태를 막아준다.
 *   3. 모든 필드를 final로 선언한다.
 *      시스템이 강제하는 수단을 이용해 설계자의 의도를 명확히 드러내는 방법이다. 새로 생성된 인스턴스를 동기화 없이 다른 스레드로 건네도 문제없이 동작하게끔 보장하는데도 필요하다.
 *   4. 모든 필드를 private으로 선언한다.
 *      필드가 참조하는 가변 객체를 클라이언트에서 직접 접근해 수정하는 일을 막아준다.
 *   5. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
 *
 *   번외1. 쓰레드 안정성과 공유 자원
 *   지역 변수
 *   - 지역 변수는 쓰레드별 고유의 스택에 쌓인다. 쓰레드간에 공유되는 자원이 아니기 때문에 쓰레드 세이프하다.
 *   객체 참조
 *   - 객체 참조는 각 쓰레드의 스택에 저장되지 않고 공유되는 메모리인 힙에 저장된다. 힙에 저장된 자원들은 모든 쓰레드에서 공유되며, 그러므로 쓰레드 세이프하지 않다.
 *   객체 멤버 변수(필드)
 *   - 멤버 변수 또한 참조된 객체와 마찬가지로 힙에 생성이 된다. 그러므로, 만약 두 개의 쓰레드가 동시에 한 객체의 멤버 변수에 접근하여 변경하려고 한다면 그 때는 쓰레드 세이프하지 않다.
 *
 *   번외2. 쓰레드 안정성과 불변성
 *   경합 조건은 다수의 쓰레드가 같은 자원에 접근하고 이 자원에 쓰기 작업을 시도할 때 발생한다.
 *   만일 쓰레드들이 같은 자원에 접근하더라도 읽기 작업을 시도할 때는 경합 조건이 발생하지 않는다.
 *   우리는 쓰레드들이 어떤 객체의 상태를 변경(update)할 수 없도록 함스로써 이 객체에게 불변성(immutability)을 부여할 수 있다. 그리고 이렇게 할 때 이 객체는 쓰레드 세이프하게 된다.
 */
public class Item17 {
    @Test
    public void thread_safe_test() {
        NotThreadSafe notThreadSafe = new NotThreadSafe();
        new Thread(new MyRunnable(notThreadSafe)).start();
    }
}

/*
 *   책의 예제를 그대로 가져왔다.
 *   아래 Complex클래스는 불변 클래스이고 복소수(실수부와 허수부로 구성된 수)를 표현한다.
 */
final class Complex {  // 클래스를 final로 정의하여 상속을 막는다. 모든 생성자를 private으로 만들고 public 정적 팩터리를 제공하는 방법도 있다.
    /*
     *   모든 필드(멤버 변수)를 private final로 선언한다.
     *   final은 설계자의 명확한 의도를 드러내며, 새로 생성된 인스턴스를 동기화 없이 다른 스레도 건네도 문제없이 동작하게끔 보장한다.
     *   private은 필드가 참조하는 가변 객체를 클라이언트에서 직접 접근해 수정하는 일을 막아준다.
     */
    private final double re;
    private final double im;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    /*
     *   정적 팩터리(private 생성자와 함께 사용해야 한다.)
     *   정적 팩터리 방식은 다수의 구현 클래스를 활용한 유연성을 제공하고, 이에 더해 다음 릴리스에서 객체 캐싱 기능을 추가해 성능을 끌어올릴 수도 있다.
     */
    public static Complex of(double re, double im) {
        return new Complex(re, im);
    }

    /*
     *   아래 사칙연산 메서드들은 인스턴스 자신은 수정하지 않고 새로운 인스턴스를 만들어 반환한다.
     *   이처럼 함수를 적용해 그 결과를 반환하지만, 기존의 인스턴스 자체는 그대로인 프로그래밍 패턴을 함수형 프로그래밍이라 한다.
     *   이와 달리, 절차적 혹은 명령형 프로그래밍에서는 메서드에서 자신을 수정해 자신의 상태가 변하게 된다.
     */
    public Complex plus(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }
    public Complex minus(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }
    public Complex times(Complex c) {
        return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
    }
    public Complex dividedBy(Complex c) {
        double tmp = c.re * c.re + c.im * c.im;
        return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Complex))
            return false;
        Complex c = (Complex) o;

        return Double.compare(c.re, re) == 0
                && Double.compare(c.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}

class NotThreadSafe {
    StringBuilder builder = new StringBuilder();

    public void add(String text){
        builder.append(text);
    }
}

class MyRunnable implements Runnable {
    NotThreadSafe instance = null;

    public MyRunnable(NotThreadSafe instance){
        this.instance = instance;
    }

    public void run(){
        this.instance.add("hello");
    }
}


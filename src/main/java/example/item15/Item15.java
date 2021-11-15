package example.item15;

import org.junit.jupiter.api.Test;

/*
 *   아이템 15. 클래스와 멤버의 접근 권한을 최소화하라.
 *
 *   좋은 설계란 무엇인가?
 *   클래스 내부 데이터와 내부 구현 정보를 위부 컴포넌트로부터 얼마나 잘 숨겼느냐이다.
 *   잘 설계된 컴포넌트는 모든 내부 구현을 완벽히 숨겨, 구현과 API를 깔끔히 분리한다.
 *   오직 API를 통해서만 다른 컴포넌트와 소통하며 서로의 내부 동작 방식에는 전혀 개의치 않는다.
 *   정보 은닉, 혹은 캡슐화라고 하는 이 개념은 소프트웨어 설계의 근간이 되는 원리이다.
 *
 *   정보은닉 및 캡슐화의 장점
 *   1. 시스템 개발 속도를 높인다.
 *      여러 컴포넌트를 병렬로 개발할 수 있다.
 *   2. 시스템 관리비용을 낮춘다.
 *      컴포넌트를 빨리 파악하여 디버깅 할 수 있고, 다른 컴포넌트로 교체하는 부담도 적다.
 *   3. 성능 최적화에 도움을 준다.
 *      최적화할 컴포넌트를 정한 다음, 다른 컴포넌트에 영향을 주지 않고 해당 컴포넌트만 최적화할 수 있다.
 *   4. 소프트웨어 재사용성을 높인다.
 *      외부에 거의 의존하지 않고 독자적으로 동작할 수 있는 컴포넌트라면 그 컴포넌트와 함께 개발되지 않은 낯선 환경에서도 유용하게 쓰일 가능성이 크다.
 *   5. 큰 시스템을 제작하는 난이도를 낮춰준다.
 *      시스템 전체가 미완성인 상태에서도 개별 컴포넌트의 동작을 검증할 수 있다.
 *
 *   자바는 정보 은닉 및 캡슐화를 위한 다양한 장치를 제공한다.
 *   그 중 접근 제어 메커니즘은 클래스, 인터페이스, 멤버의 접근성을 명시한다.
 *   각 요소의 접근성은 그 요소가 선언된 위치와 접근 제어자(public, default, protected, private)로 정해진다.
 *   이 접근 제어자를 제대로 활요하는 것이 정보 은닉의 핵심이다.
 *
 *   < 접근 제어자 >
 *   public이 붙은 변수, 메소드는 어떤 클래스에서라도 접근이 가능하다.
 *   protected가 붙은 변수, 메소드는 동일 패키지내의 클래스 또는 해당 클래스를 상속받은 외부 패키지의 클래스에서 접근이 가능하다.
 *   private이 붙은 변수, 메소드는 해당 클래스에서만 접근이 가능하다.
 *   접근제어자를 별도로 설정하지 않는다면 접근제어자가 없는 변수, 메소드는 default 접근제어자가 되어 해당 패키지 내에서만 접근이 가능하다.
 *
 *   프로그램 요소의 접근성은 가능한 한 최소한으로 한다. 꼭 필요한 것만 골라 최소한의 public API를 설계하자.
 */
public class Item15 {
    @Test
    public void price_limit_exception_test() throws PriceLimitException {
        Item item = new Item(10000, 10000);
        item.publicPrice = -20000;  // 해당 필드에 담을 수 있는 값을 제한할 방법이 없다. 클라이언트에서 마음 껏 수정가능하다.
        item.setPrivatePrice(20000);  // 익셉션 발생 ! -> 해당 필드에 담을 수 있는 값을 제한할 수 있다.
    }
}

/*
 *   public 클래스의 인스턴스 필드는 되도록 public이 아니어야 한다.
 *   이유 1. 가변 객체를 참조하거나, final이 아닌 인스턴스 필드를 public으로 선언하면 그 필드에 담을 수 있는 값을 제한할 힘을 잃게 된다.
 *   이유 2. public 가변 필드를 갖는 클래스는 스레드 세이프하지 않다.
 */
class Item {
    public Integer publicPrice;  // (X)
    private Integer privatePrice;  // (O)

    public Item(Integer publicPrice, Integer privatePrice) {
        this.publicPrice = publicPrice;
        this.privatePrice = privatePrice;
    }

    public Integer getPrivatePrice() {
        return privatePrice;
    }

    public void setPrivatePrice(Integer privatePrice) throws PriceLimitException {
        if (privatePrice < 0 || privatePrice > 10000) {
            throw new PriceLimitException("제한범위를 벗어난 가격입니다.");
        }
        this.privatePrice = privatePrice;
    }
}

class PriceLimitException extends Throwable {
    public PriceLimitException(String message) {
        super(message);
    }
}

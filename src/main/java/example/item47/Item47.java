package example.item47;

/*
 *  아이템 47. 반환 타입으로는 스트림보다 컬렉션이 낫다.
 *
 *  스트림(Stream)은 반복(Iteration)을 지원하지 않는다. (Stream이 Iterable을 확장하지 않아서이다.)
 *
 *  공개 API를 작성할 때는 스트림 파이프라인을 사용하는 사람과 반복문에서 쓰려는 사람 모두를 배려해야 한다.
 *  사용자 대부분이 한 방식만 사용할 거라는 그럴싸한 근거가 있다면 상관없지만..
 *
 *  Collection 인터페이스는 Iterable의 하위 타입이고 stream() 메서드도 제공하니 반복과 스트림을 동시에 지원한다.
 *  원소 시퀀스를 반환하는 공개 API의 반환 타입에는 Collection이나 그 하위 타입을 쓰는 게 일반적으로 최선이다.
 *
 */
public class Item47 {

}

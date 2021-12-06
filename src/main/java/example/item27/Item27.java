package example.item27;

import java.util.Arrays;

/*
 *  아이템 27. 비검사 경고를 제거하라.
 *
 *  경고를 제거할 수는 없지만 타입 안전하다고 확신할 수 있다면 @SuppressWarnings("unchecked") 애너테이션을 달아 경고를 숨기자.
 *  @SuppressWarnings 애너테이션은 항상 가능한 한 좁은 범위에 적용하자. (근데 자바 표준 ArrayList에는 메서드 레벨에 정의되어 있는데..?)
 */
public class Item27 {

    static class MyArray<E> {
        Object[] elementData;
        int size;

        public MyArray() {
            elementData = new Object[100000];
            size = 0;
        }

        public void add(E e) {
            elementData[size++] = e;
        }

        public E lastElement() {
            @SuppressWarnings("unchecked")
            E element = (E) elementData[size - 1];

            return element;
        }

        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                @SuppressWarnings("unchecked")
                T[] result = (T[]) Arrays.copyOf(elementData, size, a.getClass());
                return result;
            }

            System.arraycopy(elementData, 0, a, 0, size);
            if (a.length > size) {
                a[size] = null;
            }

            return a;
        }
    }

}

package example.item05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/*
*   아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.
*
*   의존 객체 주입이 클래스의 유연성, 재사용성, 테스트 용이성을 개선해준다.
*   의존 객체가 변경될 일이 없기 때문에 여러 클라이언트가 의존 객체들을 안심하고 공유할 수 있다.
*   하지만 이런 의존 객체 주입은 코드를 어지럽게 만들 수 있다.
*   의존 객체 주입 프레임워크인 스프링의 도움을 받아 어질러짐을 해소할 수 있다.
*/
public class Item5 {
    @Test
    public void test() {
        OxfordDictionary oxford = new OxfordDictionary(); // 객체를 생성한 후에
        CambridgeDictionary cambridge = new CambridgeDictionary(); // 객체를 생성한 후에

        SpellChecker oxfordSpellChecker = new SpellChecker(oxford); // 클라이언트 측에서 의존 객체를 주입한다.
        SpellChecker cambridgeSpellChecker = new SpellChecker(cambridge); // 클라이언트 측에서 의존 객체를 주입한다.

        Dictionary oxfordDictionary = oxfordSpellChecker.getDictionary();
        Dictionary cambridgeDictionary = cambridgeSpellChecker.getDictionary();

        assertEquals(oxfordDictionary.getName(), "OxfordDictionary"); // Test Success
        assertEquals(cambridgeDictionary.getName(), "CambridgeDictionary"); // Test Success

        SpellChecker oxfordSpellChecker2 = new SpellChecker(oxford);
        assertSame(oxfordSpellChecker.getDictionary(), oxfordSpellChecker2.getDictionary()); // Test Success
    }
}

/*
*   private final 로 만들어 의존 객체들의 불변을 보장한다.
*/
class SpellChecker {
    private final Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }
}

abstract class Dictionary {

    public String getName() {
        return "";
    }

    // 생략 ...
}

class OxfordDictionary extends Dictionary {
    private final String name = "OxfordDictionary";

    @Override
    public String getName() {
        return name;
    }
}

class CambridgeDictionary extends Dictionary {
    private final String name = "CambridgeDictionary";

    @Override
    public String getName() {
        return name;
    }
}



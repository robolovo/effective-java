package example.item5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
*   아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.
*
*   의존 객체 주입이 클래스의 유연성, 재사용성, 테스트 용이성을 개선해준다.
*   하지만 이런 의존 객체 주입은 코드를 어지럽게 만들 수 있다.
*   의존 객체 주입 프레임워크인 스프링의 도움을 받아 어질러짐을 해소할 수 있다.
*
*/
public class Item5 {
    @Test
    public void test() {
        SpellChecker oxfordSpellChecker = new SpellChecker(new OxfordDictionary());
        SpellChecker cambridgeSpellChecker = new SpellChecker(new CambridgeDictionary());

        Dictionary oxfordDictionary = oxfordSpellChecker.getDictionary();
        Dictionary cambridgeDictionary = cambridgeSpellChecker.getDictionary();

        Assertions.assertEquals(oxfordDictionary.getName(), "OxfordDictionary"); // Test Success
        Assertions.assertEquals(cambridgeDictionary.getName(), "CambridgeDictionary"); // Test Success
    }
}

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

    // 생략...
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



package io.prochyra.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);

        JsonContent<CashCard> cashCardJsonContent = json.write(cashCard);

        assertThat(cashCardJsonContent).isStrictlyEqualToJson("expected.json");
        assertThat(cashCardJsonContent).hasJsonPathNumberValue("@.id");
        assertThat(cashCardJsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(cashCardJsonContent).hasJsonPathNumberValue("@.amount");
        assertThat(cashCardJsonContent).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id":99,
                    "amount":123.45
                }
                """;
        assertThat(json.parse(expected).getObject())
                .isEqualTo(new CashCard(99L, 123.45));
        assertThat(json.parseObject(expected).id()).isEqualTo(99);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }
}

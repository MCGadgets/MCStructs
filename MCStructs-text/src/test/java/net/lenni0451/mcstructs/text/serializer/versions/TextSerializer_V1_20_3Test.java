package net.lenni0451.mcstructs.text.serializer.versions;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import static net.lenni0451.mcstructs.text.serializer.TextComponentCodec.V1_20_3;

public class TextSerializer_V1_20_3Test extends TextSerializerTest {

    @Override
    protected ATextComponent deserialize(String json) {
        return V1_20_3.deserializeJson(json);
    }

    @Override
    protected String serialize(ATextComponent component) {
        return V1_20_3.serializeJsonString(component);
    }

    @Test
    @Override
    protected void runTests() {
        this.executeDeserializeTests(
                new StringComponent("test"),
                new StringComponent("test"),
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                DESERIALIZE_FAIL,
                new StringComponent("\0"),
                DESERIALIZE_FAIL
        );
        this.executeSerializeTests(
                "\"test\""
        );
    }

}

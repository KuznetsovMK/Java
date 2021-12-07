package hw2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Scope("singleton")
public class ConsoleMessageRender implements MessageRender {

    @Override
    public void render() {
        System.out.println(getMessageProvider().getMessage());
    }

    @Override
    @Lookup
    public MessageProvider getMessageProvider() {
        return null;
    }
}

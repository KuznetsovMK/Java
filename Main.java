package hw2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProductConfig.class);
//        System.out.println(Arrays.asList(context.getBeanDefinitionNames()));


        while (true) {
            MessageRender messageRender = context.getBean(MessageRender.class);
            messageRender.render();
        }

//        context.close();
    }
}

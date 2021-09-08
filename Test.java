package gb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    enum Priority {
        LOWEST(1),
        VERY_LOW(2),
        LOW(3),
        LOWER(4),
        MEDIUM(5),
        HIGHER(6),
        HIGH(7),
        VERY_HIGH(8),
        HIGHEST(9),
        MAX_PRIORITY(10);

        private int priority;

        Priority(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

    Priority priority() default Priority.MEDIUM;
}

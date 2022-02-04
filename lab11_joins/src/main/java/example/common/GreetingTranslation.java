package example.common;

public class GreetingTranslation {
    public String language;
    public String greeting;

    public GreetingTranslation(String language, String greeting) {
        this.language = language;
        this.greeting = greeting;
    }

    @Override
    public String toString() {
        return "GreetingTranslation{" +
                "language='" + language + '\'' +
                ", greeting='" + greeting + '\'' +
                '}';
    }
}

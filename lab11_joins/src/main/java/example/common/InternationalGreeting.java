package example.common;

public class InternationalGreeting {
    public String name;
    public String language;
    public String greeting;

    public InternationalGreeting(String name, String language, String greeting) {
        this.name = name;
        this.language = language;
        this.greeting = greeting;

    }

    @Override
    public String toString() {
        return "InternationalGreeting{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", greeting='" + greeting + '\'' +
                '}';
    }
}

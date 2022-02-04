package example.common;

public class NameAndLanguage {
    public String name;
    public String language;

    public NameAndLanguage(String name, String language) {
        this.name = name;
        this.language = language;
    }

    @Override
    public String toString() {
        return "NameAndGreeting{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}

package example.task02_international;

public class InternationalGreeting {
    public String sender_name;
    public String receiver_name;
    public String sender_language;
    public String receiver_language;
    public String sender_greeting;
    public String receiver_greeting;

    @Override
    public String toString() {
        return "InternationalGreeting{" +
                "sender_name='" + sender_name + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", sender_language='" + sender_language + '\'' +
                ", receiver_language='" + receiver_language + '\'' +
                ", sender_greeting='" + sender_greeting + '\'' +
                ", receiver_greeting='" + receiver_greeting + '\'' +
                '}';
    }
}

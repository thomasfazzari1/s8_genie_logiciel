package fr.ul.miage.fazzari_chartier_colombana.Util;

public class MessageBuilder {
    private StringBuilder message;
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    public MessageBuilder() {
        message = new StringBuilder();
    }

    public MessageBuilder addSuccessMessage(String msg) {
        message.append(GREEN).append(msg).append(RESET).append("\n");
        return this;
    }

    public MessageBuilder addErrorMessage(String msg) {
        message.append(RED).append(msg).append(RESET).append("\n");
        return this;
    }

    public String build() {
        return message.toString();
    }
}

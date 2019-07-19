package kpu.computer.joonggo;

public class Message {
    Integer number;
    String sender;
    String receiver;
    String content;
    String time;

    public Message(Integer number, String sender, String receiver, String content, String time) {
        this.number = number;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.time = time.replaceFirst("20","");
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

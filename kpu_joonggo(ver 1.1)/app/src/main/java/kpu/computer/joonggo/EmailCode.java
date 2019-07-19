package kpu.computer.joonggo;

public class EmailCode {

    String emailCode = createEmailCode();

    public String getEmailCode() {
        return emailCode;
    } //생성된 이메일 인증코드 반환

    private String createEmailCode() { //이메일 인증코드 생성
        String[] str = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();

        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return newCode;
    }

}

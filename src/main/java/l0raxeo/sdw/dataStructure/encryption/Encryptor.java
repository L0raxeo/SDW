package l0raxeo.sdw.dataStructure.encryption;

public class Encryptor
{

    public static String encrypt(String message) {
        StringBuilder scrambled = new StringBuilder();
        int key = (int) (Math.random() * 9) + 1;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (Character.isLetter(c)) {
                c = (char) (c + key);
                if (c > 'z') {
                    c = (char) (c - 26);
                } else if (c < 'a') {
                    c = (char) (c + 26);
                }
            } else if (Character.isDigit(c)) {
                c = (char) (c + key);
                if (c > '9') {
                    c = (char) (c - 10);
                } else if (c < '0') {
                    c = (char) (c + 10);
                }
            }
            scrambled.append(c);
        }
        return String.valueOf(key).concat(scrambled.toString());
    }

    public static String decrypt(String rawEncryption) {
        StringBuilder original = new StringBuilder();
        String encrypted = rawEncryption.substring(1);
        int key = Integer.parseInt(rawEncryption.substring(0, 1));
        for (int i = 0; i < encrypted.length(); i++) {
            char c = encrypted.charAt(i);
            if (Character.isLetter(c)) {
                c = (char) (c - key);
                if (c > 'z') {
                    c = (char) (c - 26);
                } else if (c < 'a') {
                    c = (char) (c + 26);
                }
            } else if (Character.isDigit(c)) {
                c = (char) (c - key);
                if (c > '9') {
                    c = (char) (c - 10);
                } else if (c < '0') {
                    c = (char) (c + 10);
                }
            }
            original.append(c);
        }
        return original.toString();
    }

}

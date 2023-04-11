package com.l0raxeo.sdw.utils;

public class Utils
{

    public static String encrypt(String message)
    {
        // [0,9]
        int key = (int) (Math.random() * 9);
        int tmpKey = key;

        StringBuilder result = new StringBuilder();
        result.append(message);

        for (int i = 0; i < message.length(); i += 2)
        {
            result.setCharAt(i, (char) (result.charAt(i) + tmpKey));
            tmpKey--;
            for (int k = 1; k < message.length(); k += 2)
            {
                int tmp = tmpKey;

                while (tmp > 0)
                {
                    result.setCharAt(k, (char) (result.charAt(k) + 1));
                    tmp--;
                }
            }
        }

        return String.valueOf(key).concat(result.toString());
    }

    public static String decrypt(String message)
    {
        StringBuilder result = new StringBuilder();
        result.append(message.substring(1));
        int key = Integer.parseInt(message.substring(0, 1));

        for (int i = 0; i < result.length(); i += 2)
        {
            result.setCharAt(i, (char) (result.charAt(i) - key));
            key--;
            for (int k = 1; k < result.length(); k += 2)
            {
                int tmp = key;

                while (tmp > 0)
                {
                    result.setCharAt(k, (char) (result.charAt(k) - 1));
                    tmp--;
                }
            }
        }

        return result.toString();
    }

}

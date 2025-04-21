package org.example;

import java.util.UUID;

public class ChaveAutenticacao {

    public String gerarChave() {

        UUID uuid = UUID.randomUUID();

        String uuidStr = uuid.toString().replaceAll("-", "");
        return uuidStr.substring(0, 8) + "-" +
                uuidStr.substring(8, 12) + "-" +
                uuidStr.substring(12, 16) + "-" +
                uuidStr.substring(16, 24);
    }

}

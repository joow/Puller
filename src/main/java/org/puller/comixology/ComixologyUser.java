package org.puller.comixology;

import java.util.HashMap;
import java.util.Map;

class ComixologyUser {
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";

    private final String username;
    private final String password;

    public ComixologyUser(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public Map<String, String> toMap() {
        final HashMap<String, String> map = new HashMap<>();
        map.put(USERNAME_FIELD, username);
        map.put(PASSWORD_FIELD, password);

        return map;
    }
}

package cn.foxkiar.support.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class User {
    private String account;
    private String password;

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (obj instanceof User)
            return this.account.equals(((User)obj).account) && this.password.equals(((User)obj).password);
        else
            return false;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("account", account);
        map.put("password", password);
        return map;
    }
}

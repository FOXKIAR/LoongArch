package cn.foxkiar.support.entity;

import lombok.Data;

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
}

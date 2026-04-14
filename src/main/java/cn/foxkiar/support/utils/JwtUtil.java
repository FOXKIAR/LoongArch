package cn.foxkiar.support.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class JwtUtil {

    public static String generateToken() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR_OF_DAY, 8);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("username", "管理员");
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("test"));
    }

    public static String generateToken(boolean isRememberMe) {
        if (!isRememberMe)
            return generateToken();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, 1);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("username", "管理员");
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("test"));
    }
}
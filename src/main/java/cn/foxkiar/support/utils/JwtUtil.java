package cn.foxkiar.support.utils;

import cn.foxkiar.support.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class JwtUtil {

    public static String generateToken(User user) {
        Map<String, String> claims = user.toMap();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR_OF_DAY, 8);
        JWTCreator.Builder builder = JWT.create();
        claims.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("test"));
    }
}
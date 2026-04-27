package cn.foxkiar.support.utils;

import cn.foxkiar.support.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public static String generateToken(User user) throws IllegalAccessException {
        Calendar instance = Calendar.getInstance();
        if (user.getIsKeepLogged() != null && user.getIsKeepLogged())
            instance.add(Calendar.MONTH, 1);
        else
            instance.add(Calendar.HOUR_OF_DAY, 8);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("logged", map(user));
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("test"));
    }

    public static boolean verifyToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256("test")).build().verify(token);
        return jwt.getClaim("logged").asMap() != null;
    }

    static Map<String,Object> map(Object obj) throws IllegalAccessException {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            result.put(field.getName(), field.get(obj));
        }
        return result;
    }
}
package co.com.ingeneo.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;
import org.apache.wicket.util.crypt.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtil {

    public static String getToken(String signingKey, String subject, Map<String, Object> claims, long expiration) {
        Key key = Keys.hmacShaKeyFor(signingKey.getBytes());
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public static String decodeToken(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return StringUtils.newStringUtf8(Base64.decodeBase64(jwt.getPayload()));
        }catch (Exception e){
            throw e;
        }
    }

    public static Object toClame(String decodedValue) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> claims;
        try {
            claims = mapper.readValue(decodedValue, HashMap.class);
            return claims.get("Principal");
        }catch (Exception e){

        }
        return null;
    }
}

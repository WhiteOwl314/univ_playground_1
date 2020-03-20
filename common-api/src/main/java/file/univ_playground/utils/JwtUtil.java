package file.univ_playground.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {
    /*
    Key :
    암호화, 알고리즘복잡화.
    다양한 알고리즘 사용
    * */
    private Key key;

    //Constructor
    public JwtUtil(String secret){
        /*
        Keys:
        jwt 라이브러리로써 key들을 만들어낸다
        hmacShaKeyFor:
        Keys 소속으로 HMAC-SHA 형식으로 키를 만들어낸다
        * */
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    //JWT TOKEN 생성
    public String createToken(Long userId, String name){

        /*
        Jwts:
        너의 코드를 Jwt 구현체와 단단하게 연결
        claim(요구하다):
        편리하다.
        signWith:
        특정 키와 알고리즘으로 JWT 생성을 서명한다.
        compact(굳히다)
        * */
        String token = Jwts.builder()
                .claim("userId",userId)
                .claim("name",name)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
}

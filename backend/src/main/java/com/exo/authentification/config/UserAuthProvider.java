package com.exo.authentification.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.exo.authentification.dto.UserDto;
import com.exo.authentification.entity.User;
import com.exo.authentification.exception.AppException;
import com.exo.authentification.mapper.UserMapper;
import com.exo.authentification.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class UserAuthProvider {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey; //backend code and encode the token

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); //TO AVOID HAVING THE SECRETKEY IN THE jvm
    }

    public String createToken(UserDto dto){
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(dto.getLogin())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", dto.getFirstname())
                .withClaim("lastName", dto.getLastname())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);


       UserDto user = UserDto.builder()
                .login(decoded.getIssuer())
                .firstname(decoded.getClaim("firstName").asString())
                .lastname(decoded.getClaim("lastname").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

    }

    public Authentication validateTokenStrongly(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decoded = verifier.verify(token);

        User user = userRepository.findByLogin(decoded.getIssuer())
                .orElseThrow(()-> throw new AppException("Unkown user", HttpStatus.NOT_FOUND));

        return new UsernamePasswordAuthenticationToken(userMapper.toUserDto(user), null, Collections.emptyList());
    }



}

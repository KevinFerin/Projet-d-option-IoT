package com.imta.cdi.service.model.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Service
public class TokenVerifierService {

    public boolean checkToken(@Param("token") String token) {
        try {
            FileInputStream is = new FileInputStream(System.getProperty("keystore"));
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            String passwordKS = System.getProperty("password-keystore");
            char[] passwd = passwordKS.toCharArray();
            keystore.load(is, passwd);
            String alias = "gaiaui-keystore";
            Key key = keystore.getKey(alias, passwd);
            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);
                // Get public key
                PublicKey publicKey = cert.getPublicKey();
                String publicKeyString = Base64.encodeBase64String(publicKey
                        .getEncoded());
                System.out.println(publicKeyString);
                Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) key);

                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("auth0")
                        .build(); //Reusable verifier instance
                DecodedJWT jwt = verifier.verify(token);
                System.out.println("Header : " + jwt.getHeader());
                System.out.println("Payload : " + jwt.getPayload());
                System.out.println("Signature : " + jwt.getSignature());
                System.out.println("Token : " + jwt.getToken());
                DecodedJWT jwtDecoded = JWT.decode(token);
                System.out.println("CN : " + jwtDecoded.getClaim("cn").asString());
                System.out.println("DISPLAY-NAME : " + jwtDecoded.getClaim("displayName").asString());
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return false;
    }
}

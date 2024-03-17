package de.kct.kct.configuration;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {

    private final ResourceLoader resourceLoader;

    public JwtService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String extractEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        try {
            var key = getRsaKey(false);
            return Jwts.builder().setHeader(Map.of("typ", "JWT")).setSubject(userDetails.getUsername())
                    .setExpiration(DateUtils.addYears(new Date(), 1)).signWith(key).compact();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    private <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        try {
            var claims = extractClaimsFromToken(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
        }
        return null;
    }

    private Claims extractClaimsFromToken(String token) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.parserBuilder().setSigningKey(getRsaKey(true)).build()
                .parseClaimsJws(token).getBody();
    }

    private Key getRsaKey(Boolean isPublic) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        var filePath = isPublic ? "classpath:keys/publickey.crt" : "classpath:keys/pkcs8.key";
        var keyFromFile = readKeyFromFile(filePath);
        var keyString = sanitizeKeyFromFile(keyFromFile, isPublic);
        byte[] decoded = Base64.decodeBase64(keyString);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        if (isPublic) {
            var x509EncodedKeySpec = new X509EncodedKeySpec(decoded);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        }
        var pkcS8EncodedKeySpec = new PKCS8EncodedKeySpec(decoded);
        return keyFactory.generatePrivate(pkcS8EncodedKeySpec);
    }

    private String readKeyFromFile(String filePath) throws IOException {
        var file = resourceLoader.getResource(filePath);
        return FileCopyUtils.copyToString(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
    }

    private String sanitizeKeyFromFile(String keyFromFile, Boolean isPublic) {
        var keyType = isPublic ? "PUBLIC" : "PRIVATE";
        return keyFromFile.replace("-----BEGIN " + keyType + " KEY-----", "").replace(System.lineSeparator(), "")
                .replace("-----END " + keyType + " KEY-----", "");
    }

    interface ClaimsResolver<T> {
        T apply(Claims claims);
    }

}
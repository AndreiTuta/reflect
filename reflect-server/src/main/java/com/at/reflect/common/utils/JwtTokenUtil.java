//package com.at.reflect.common.utils;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import javax.servlet.http.Cookie;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import com.at.reflect.controller.service.UserService;
//import com.at.reflect.model.entity.User;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class JwtTokenUtil implements Util, Serializable {
//	private static final long serialVersionUID = 1L;
//	static final String CLAIM_KEY_USERNAME = "sub";
//	static final String CLAIM_KEY_CREATED = "iat";
//	private static final String jwtTokenCookieName = "JWT-TOKEN";
//	private static String secret = "secret";
//	private static Long expiration = 604800L;
//	@Autowired
//	private UserService userService;
//
//	public String getUsernameFromToken(String token) {
//		return getClaimFromToken(token, Claims::getSubject);
//	}
//
//	public Date getIssuedAtDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getIssuedAt);
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimFromToken(token, Claims::getExpiration);
//	}
//
//	public Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//	}
//
//	public Boolean isTokenExpired(String token) {
//		final Date expiration = getExpirationDateFromToken(token);
//		return expiration.before(new Date(System.currentTimeMillis()));
//	}
//
//	public String generateToken(String userName) {
//		Map<String, Object> claims = new HashMap<>();
//		return doGenerateToken(claims, userName);
//	}
//
//	public Boolean canTokenBeRefreshed(String token) {
//		return (!isTokenExpired(token));
//	}
//
//	public String refreshToken(String token) {
//		final Date createdDate = new Date(System.currentTimeMillis());
//		final Date expirationDate = calculateExpirationDate(createdDate);
//
//		final Claims claims = getAllClaimsFromToken(token);
//		claims.setIssuedAt(createdDate);
//		claims.setExpiration(expirationDate);
//
//		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
//	}
//
//	/**
//	 * Checks the request for the token cookie.
//	 * 
//	 * @param request
//	 * @return true if the token cookie exists, false otherwise
//	 */
//	public Cookie containsTokenCookie(Cookie[] cookies) {
//		if (cookies != null && cookies.length > 0) {
//			// Loop throught and check for our cookie
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals(jwtTokenCookieName) && !StringUtils.isEmpty(cookie.getValue())) {
//					return cookie;
//				}
//			}
//		}
//		log.info("Cookies are null or empty");
//		return null;
//
//	}
//
//	/**
//	 * Removes the token Cookie.
//	 * 
//	 * @param request, resposne
//	 * @return true if the token cookie has been removed, false if it doesn't exist
//	 */
//	public Cookie disableTokenCookie(Cookie[] cookies) {
//		Cookie cookie = containsTokenCookie(cookies);
//		if (cookie != null) {
//			cookie.setMaxAge(0);
//			return cookie;
//		}
//		return null;
//	}
//
//	/**
//	 * Get the token from the cookie
//	 * 
//	 * @param cookie
//	 * @return Strig token
//	 */
//	public String getTokenFromCookie(Cookie cookie) {
//		if (!StringUtils.isEmpty(cookie.getValue())) {
//			return cookie.getValue();
//		}
//		return null;
//	}
//
//	/**
//	 * Get the user from the token
//	 * 
//	 * @param token
//	 * @return user
//	 */
//	public User getUserFromToken(String jwt) {
//		if (!StringUtils.isEmpty(jwt)) {
//			return userService.fetchUserBySecret(getUsernameFromToken(jwt));
//		}
//		return null;
//	}
//
//	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}
//
//	public Date calculateExpirationDate(Date createdDate) {
//		return new Date(createdDate.getTime() + expiration * 1000);
//	}
//
//	private String doGenerateToken(Map<String, Object> claims, String subject) {
//		Date createdDate = new Date(System.currentTimeMillis());
//		Date expirationDate = calculateExpirationDate(createdDate);
//
//		String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
//				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
//		log.info("JWT generated {}", token);
//		return token;
//	}
//
//	@Override
//	public UtilType getType() {
//		return UtilType.JWT;
//	}
//}
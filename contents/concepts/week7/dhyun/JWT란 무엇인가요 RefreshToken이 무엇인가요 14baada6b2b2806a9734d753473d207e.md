# JWT란 무엇인가요? RefreshToken이 무엇인가요?

## 1. JWT란 무엇인가요?

### 1. 쿠키 vs 세션 vs 토큰

### - 쿠키

쿠키는 공개 가능한 정보를 사용자의 브라우저에 저장시킨다.

- 서버는 클라이언트의 로그인 요청에 대한 응답을 작성할 때, 클라이언트 측에 저장하고 싶은 정보를 응답 헤더의 set-cookie 에 담는다.
- 이후 클라이언트가 재요청 할 때마다 저장된 쿠키를 요청 헤더의 cookie 에 담아 보낸다.
- 서버는 쿠키에 담긴 정보를 바탕으로 해당 요청의 클라이언트가 누군지 식별 할 수 있다.

단점

- http로 개인정보를 주고 받다보면 쿠키가 유출, 조작
- 개인 소유가 아닌 컴퓨터에서 사용할 경우 사용자의 개인정보가 털릴 가능성

### **-세션**

![화면 캡처 2024-11-28 075306.png](%25ED%2599%2594%25EB%25A9%25B4_%25EC%25BA%25A1%25EC%25B2%2598_2024-11-28_075306.png)

**동작과정**

1. 클라이언트가 로그인을 위해 인증 정보(requestMemberDtdo)를 서버에 전송
2. 서버는 메모리(세션)에 사용자를 저장하고, 세션 아이디를 쿠키로 전달

(세션 저장소란 것을 만들어서 세션 아이디를 키로, 실제 정보(id)를 값으로 함)

1. 클라이언트는 쿠키에 저장된 세션 아이디를 이용하여 요청
2. 서버는 일치하는 세션 아이디를 메모리에서 검색한 후 응답

session은 비밀번호와 같은 인증 정보를 쿠키에 저장하지 않고 대신에 사용자의 식별자인 session Id를 쿠키에 저장

단점

- 서버에서 세션 저장소를 사용하므로 요청이 많아지면 서버에 부하가 심해진다

### 토큰

![화면 캡처 2024-11-28 075317.png](%25ED%2599%2594%25EB%25A9%25B4_%25EC%25BA%25A1%25EC%25B2%2598_2024-11-28_075317.png)

토큰 인증은 클라이언트가 서버에 접속을 하면 서버에서 해당 클라이언트에게

인증되었다는 의미로 '토큰'을 부여한다.

1. 클라이언트가 로그인을 위해 인증 정보를 서버에 전송
2. 서버는 secret 정보를 이용하여 중요 정보를 인코딩해 JWT를 생성하고, 클라이언트에게 전달
3. 클라이언트는 로컬 혹은 브라우저에 저장해두었던 JWT를 이용하여 요청
4. 서버는 JWT가 일치하는지 확인한 후(파싱한 후), 응답

토큰은 유일

토큰을 발급받은 클라이언트는 또 다시 서버에 요청을 보낼 때 요청 헤더(Authorization)에 토큰을 심어서 보낸다.

토큰은 서버가 아닌 클라이언트에 저장되기 때문에 메모리나 스토리지 등을 통해 세션을 관리했던 서버의 부담을 덜 수 있다.

클라이언트는 서버측에서 전달받은 토큰을 쿠키나 스토리지에 저장해두고,서버에 요청을 할 때마다 해당 토큰을 HTTP요청 헤더에 포함시켜 전달한다.

단점

- 쿠키 / 세션과 다르게 토큰 자체의 데이터 길이가 길어서,인증 요청이 많아질수록 네트워크 부하가 심해질 수 있다.
- Payload 자체는 조회가 가능하기 때문에 유저의 중요한 정보를 담을 수 없다.
- 토큰은 발급하면 만료될 때까지 계속 사용이 가능하기 때문에토큰이 탈취당하면 대처하기가 어렵다.

### 2. Spring에서 JWT를 어떻게 생성할 수 있나요?

```
implementation 'io.jsonwebtoken:jjwt:0.9.1'
```

```java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public String generateToken(String username) {
    String secretKey = "yourSecretKey";
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 만료 시간 설정 (1시간)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
}
```

### JWT

![json-web-token.png](json-web-token.png)

JWT는 **.** 을 구분자로 나누어지는 세가지 문자열의 조합이다.

**.** 을 기준으로 Header, Payload, Signature을 의미한다.

Header는 JWT에서 사용할 타입과 해시 알고리즘의 종류, base64 인코딩

Payload는 서버에서 첨부한 사용자 권한 정보와 데이터가 담겨, base64인코딩

Signature에는 Header, Payload를 Base64 URL-safe Encode를 한 이후 Header에 명시된 해시함수를 적용하고, 개인키로 서명한 전자서명이 담겨있다. 유효성 검증을 할때 사용하는 암호화 코드를 저장

base64인코딩

- Base64 인코딩은 특수 문자(예: 공백, `&`, `=` 등)가 포함되지 않도록 변환하여 URL이나 헤더에서 문제가 없도록 한다
- JWT는 URL-Safe Base64 인코딩(Base64url)을 사용하여, URL에서 문제가 될 수 있는 `+`, `/`, `=` 문자를 각각 ``, `_`, 생략으로 대체함

![화면 캡처 2024-11-27 164540.png](%25ED%2599%2594%25EB%25A9%25B4_%25EC%25BA%25A1%25EC%25B2%2598_2024-11-27_164540.png)

1. 사용자가 로그인을 한다.
2. 서버에서는 계정 정보를 읽어 사용자를 확인 후, 사용자 고유ID값을 부여한 후,
기타 정보와 함께 Payload에 넣는다.
3. JWT의 유효기간 설정
4. 암호화할 SECRET KEY를 이용하여 Access Token을 발급 한다.
5. 사용자는 Access Token을 받아 로컬 스토리지(혹은 쿠키)에
    
    저장한 후, 인증이 필요한 요청마다 토큰을 헤더에 실어서 보낸다.
    
6. 서버에서는 해당 토큰의 Verify Signature을 SECRET KEY로 복호화한 후,
    
    조작여부, 유효기간을 확인한다.
    
7. 검증이 완료되면, Payload를 디코딩하여 사용자의 ID에 맞는 데이터를 가져온다.

### 3. JWT의 문제점

- 이미 발급된 JWT에 대해서는 돌이킬 수 없다.  세션 / 쿠키의 경우 만일 쿠키가 악의적으로 이용되고 있다면, 해당하는 세션을 지워버리면 된다.
    
    하지만 JWT는 한번 발급되면 유효기간이 완료될 때까지 계속 사용이 가능하다.
    
    따라서 악의적인 사용자는 유효기간이 지나기 전까지 정보를 털어갈 수 있다.
    
    → refreshToken
    

- Payload의 정보가 제한적이다. Payload는 암호화 되지않기 때문에 디코딩하면 누구나 정보를 확인할 수 있다.
    
    따라서 유저의 중요한 정보들은 Payload에 넣을 수 없다.
    
    → 암호화, https
    

- 인증이 필요한 요청이 많아질수록 서버의 자원 낭비가 발생한다.

## 2. RefreshToken이 무엇인가요?

### 1. RefreshToken이 왜 탄생했을까요?

- **액세스 토큰의 보안 강화**: 짧은 만료 시간을 가지는 액세스 토큰만으로 인증을 수행하면 탈취되더라도 위험을 최소화
- **사용자 경험 개선**: 액세스 토큰이 만료될 때마다 로그인을 반복하지 않아도 되도록 만료된 토큰을 재발급받는 방법이 필요

### 2. RefreshToken은 어떻게 구현할 수 있을까요?

- **RefreshToken 생성 및 저장**:
    - 서버에서 RefreshToken을 생성한다
    - 생성된 RefreshToken은 데이터베이스나 메모리 캐시(redis)에 저장
- **RefreshToken 발급 및 재발급**:
    - 클라이언트는 액세스 토큰 만료 시 RefreshToken을 서버에 전달
    - 서버는 RefreshToken의 유효성을 확인한 후 새 액세스 토큰을 발급해서 리턴

### 3. RefreshToken은 어떤 문제를 해결할 수 있을까요?

1. **보안 문제**: 액세스 토큰을 짧게 유지함으로써 탈취 위험을 감소시킴
2. **사용자 경험**: 장기 세션을 유지하면서도 빈번한 로그인 과정을 간소화
3. **상태 관리**: 서버가 RefreshToken을 검증하여 사용자를 신속히 인증

### 4. RefreshToken은 어떤 문제가 있을까요?

1. **보안 공격 위험성**: Refresh Token이 탈취되면 공격자가 해당 토큰을 사용하여 accessToken이 만료되더라도 액세스 권한을 얻을 수 있음
2. **유효 기간 관리**:Refresh Token의 유효 기간을 적절히 관리해야 함. 너무 긴 유효 기간은 보안위험, 너무 짧은 유효 기간은 자주 refreshToken을 삭제하고 생성해야 함
3. **재사용 방지**:Refresh Token이 한 번 사용되고 나면 더 이상 유효하지 않아야 한다. 중복 사용을 방지하기 위한 메커니즘을 구현
4. **저장 및 보안**:Refresh Token은 안전한 곳에 저장되어야 함. 데이터베이스나 안전한 토큰 저장소에 저장하고, 보안을 강화
5. **로그아웃 처리**:사용자가 로그아웃하거나 계정을 삭제할 때, 관련된 Refresh Token도 삭제되어야 함
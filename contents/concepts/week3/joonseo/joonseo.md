
# 3회차

# Controller Layer는 어떤 계층이고 무슨 역할을 하나요

## 1. Intro

컨트롤러 계층을 살펴보기 전에, 클라이언트의 요청이 스프링 컨테이너에 도달하기까지의 과정을 살펴보도록 합시다. ~~아래의 예시에서는 HTTP 요청을 기준으로 하였습니다.~~

![image1.png](image1.png)
1. **클라이언트측 요청 송신**

    ```java
    POST /api/users HTTP/1.1
    Host: api.xunxxoie.com
    Content-Type: application/json
    
    {
        "name": "한준서",
        "email": "ahh0520@naver.com",
        "age": 24
    }
    ```

   위와 같은 요청을 서버에 날렸다고 가정해봅시다.

2. **웹서버측 요청 수신**

   **톰캣**과 같은 **웹서버**가 클라이언트의 요청을 수신합니다. 크게 두가지 과정으로 나눠볼 수 있습니다.

    - **커넥터**(Connector)가 요청을 받으면, **서블릿 컨테이너**(Servlet Container)에게 이를 전달합니다.
    - 서블릿 컨테이너는 해당 요청에 대한 스레드를 할당하고 필터 체인을 통과하게 합니다.

1. **스프링측 요청 처리**

   필터 체인을 통과한 요청은 스프링 컨테이너의 제일 앞부분(**Front Controller**)인 **Dispatcher Servlet**에 도착합니다. 여기서 **어떤 컨트롤러가 요청을 처리할지 찾아 전달**하고 **받아온 데이터를 사용할 수 있는 형식으로 바꿔**줍니다. 추가적으로 데이터에 대한 유효성 검증도 이 과정에서 이루어집니다.


1. **컨트롤러 동작**

   선택된 컨트롤러의 메소드가 해당 요청을 처리합니다.

    ```java
        @PostMapping
        public ResponseEntity<CreateUserResponse> createUser(
                @RequestBody CreateUserRequest request) {
                
            User user = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                               .body(new CreateUserResponse(user));
        }
    ```


## 2. Role of Controller Layer

**Controller Layer**는 **Client**로부터 요청을 전달받아 처리하고 그에 대한 결과를 다시 **Client**에게 전달하는 역할을 합니다. 정확하게는 요청에 대한 직접적인 처리는 **Service Layer**에서 담당하며, **Controller Layer**는 이를 **Service Layer**에게 전달하는 역할을 합니다.

---

# 요청과 응답이란 무엇인가요?

만약 구글 홈페이지에 접속하기 위해 웹 브라우저에 [www.google.com](http://www.google.com) 을 입력하면 어떤 일이 벌어지나요? 아마 아래와 같은 창이 화면에 뜰겁니다.

![image2.png](image2.png)

여기서 URL을 입력하고 앤터를 누르는 행위는 **요청**에 해당하고, 구글 페이지가 웹브라우저에 나타난 것은 요청에 대한 **응답**에 해당합니다.

다른 예시로 제가 아래처럼 댓글을 작성하고 댓글 작성 버튼을 클릭하는 것도 **요청**에 해당합니다.

![image3.png](image3.png)

직접적으로 이번 요청에 대한 응답을 확인할 수는 없었지만, 아래처럼 댓글이 잘 올라간 것을 확인할 수 있습니다.

![image4.png](image4.png)

이처럼 **클라이언트와 서버 간의 상호작용은 대부분 요청과 응답으로 이루어진**다고 볼 수 있습니다. 그럼 이 **요청**과 **응답**이 어떤 식으로 구성되고 이루어지는지 확인해봅시다.

## 1. HTTP Request

사실 **HTTP Request**는 구면입니다. `클라이언트측 요청 송신`에서 한번 봤는데요.

```java
POST /api/users HTTP/1.1
Host: api.myservice.com
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)
Content-Length: 165
Accept: application/json

{
	"name" : "한준서",
	"age" : 24
}
```

**HTTP Request**는 **Start Line, Header, Body** 총 세 부분으로 이루어져 있습니다.

### 1. Start Line

**Start Line**은 아래처럼 다시 세부분으로 나눌 수 있습니다. 각 부분이 의미하는 바는 아래와 같습니다.

<aside>
💁🏻‍♂️

**[HTTP Method] [Request Target] [HTTP Version]**

- **`HTTP Method**` → 요청 타입
- **`Request Target`** → 요청이 전송되는 목표 주소
- **`HTTP Version`** → HTTP 버전. 버전마다 요구하는 양식이 다르기 때문
</aside>

### 2. Header

**Header**는 요청에 대한 추가적인 정보를 담고 있는 부분입니다. **Header**와 **Body**는 데이터를 담고 있다는 측면에서는 유사하지만, **Header**는 **요청에 대한 메타데이터**를 포함하고 있고, **Body**는 **클라이언트가 서버에 전송하고 싶은 실질적인 데이터**를 담고 있다는 차이가 존재합니다. 각 헤더가 의미하는 바는 아래와 같습니다.

<aside>
💁🏻‍♂️

- **`Host`** → 서버의 도메인 네임 주소
- **`Content-Type`** → Body에 들어갈 데이터의 형식을 명시, 서버가 어떻게 파싱할지 알려줌
- **`Authorization`** → 인증에 필요한 토큰(대표적으로 JWT)
- **`User-Agent`** → 클라이언트의 브라우저 정보
- **`Content-Length`** → Body의 데이터 길이(byte)
- **`Accept`** → 클라이언트가 받아들일 수 있는 응답 형식 지정
- **`Origin`** → 해당 요청을 보낸 곳의 URL
- **`Cookie`** → 쿠키 값 명시
</aside>

### 3. Body

서버에 전송될 실질적인 데이터를 담고 있는 부분입니다. **텍스트**, **이미지**, **오디오**, **멀티파트** 데이터 등 다양한 데이터를 전송할 수 있습니다. 다만 데이터 타입을 꼭 **Content-Type 헤더**에 명시해야 합니다.

## 2. **HTTP Response**

```java
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 234
Access-Control-Allow-Origin: *
Date: Fri, 01 Nov 2024 12:00:00 GMT

{
    "data": {
        "id": 123,
        "username": "john_doe",
        "email": "john@example.com"
    }
}
```

**HTTP Response**도 **Status Line, Header, Body** 총 세 부분으로 이루어져 있습니다.

### 1. Status Line

응답에 대한 상태를 주로 나타내고 있습니다. 클라이언트 측에서 해당 응답을 수신하면, **Status Line**을 참고하여 요청의 **성공, 실패 여부를 확인**할 수 있습니다.

<aside>
💁🏻‍♂️

**[HTTP Version] [Status Code] [Status Text]**

- **`HTTP Version` →** HTTP 버전
- **`Status Code` →** 요청-응답 상태 코드 (200, 404, 400 …)
- **`Status Text` →** 상태 코드에 대한 텍스트 (ok, Not Found, Bad Request …)
</aside>

### 2. Header

HTTP Request의 Header와 거의 유사합니다. 하지만 **Server**, **Set-Cookie**, **CORS 관련 헤더**, **리다이렉션 url**과 같은 헤더들은 **Response에만 존재**하는 헤더입니다.

### 3. Body

만약 클라이언트가 동파육의 정보를 요청하였다면, 동파육의 정보를 응답에 추가하여 보내줘야겠죠. 그 데이터를 담는 부분이 **Body**입니다.

이건 google에서 동파육을 검색했을 때, `Response Body`에 담겨져 오는 내용입니다. **검색 결과 데이터**, **CSS 파일** 등 다양한 데이터들이 딸려옵니다.

![content.mov](content.mov)
# Spring에서 요청을 어떻게 처리하나요?

## 1. 스프링에서 객체의 직렬화와 역직렬화는 어떻게 이뤄질까요?

먼저 **직렬화**(Serialization) **역직렬화**(Deserialization)이 무엇인지 살펴보겠습니다.

- **직렬화(Serialization)**

  → 데이터를 메모리, 디스크, 데이터베이스에 저장하기 적합한 형식으로 바꾸거나 또는, 네트워크 통신에 사용하기 위한 형태로 변환하는 과정


- **역직렬화(Deserialization)**

  → 메모리나 디스크, 데이터베이스에서 데이터를 불러와 개발 환경에서 가공 및 조작할 수 있는 형태, 또는 네트워크 통신에 사용할 수 있는 형태로 변환하는 과정


![image5.png](image5.png)

간단하게 말해 `좌 → 우`는 **직렬화** 과정, `우 → 좌`는 **역직렬화** 과정입니다.

```java
public class User {
    private String name = "kim";
    private int age = 20;
}
```

```java
AC ED 00 05 73 72 00 04 55 73 65
72 00 00 00 00 00 00 00 01 02 00
02 49 00 03 61 67 65 4C 00 04 6E
61 6D 65 74 00 12 4C 6A 61 76 61 
```

이 과정이 왜 필요할까요? 이 이유를 이해하려면, JVM에 데이터가 저장되는 방식을 이해해볼 필요가 있습니다. 데이터는 크게 두 종류로 나눌 수 있는데요

- **값 형식(Primitive Type)** : int, double, boolean 등의 데이터. **실제 값이 메모리에 저장됨.**
- **참조 형식(Reference Type)** : Integer, Double, Float와 같은 타입이나, 개발자가 생성한 클래스 타입의 데이터. 실제 데이터가 저장되는 것이 아닌, **해당 데이터를 담고 있는 메모리의 주소 저장.**

![image6.png](image6.png)

**값 형식(Primitive Type)**의 데이터는 스택에 실제 값이 저장되어 있기 때문에, 해당 값을 적절히 변환해 꺼내올 수 있지만, **참조 형식(Reference Type)**의 데이터의 경우에는 **스택**에는 **실제 값이 저장되어 있는 주소값**이 저장되어 있고 **실제 데이터는 힙에 저장**되어 있습니다.

직렬화 과정을 거치지 않고 데이터를 다른 클라이언트나 서버에 전송하게 되면, 스택 메모리에 있는 주소값이 전송되게 됩니다. 따라서 직렬화 과정을 거쳐야 의도한 데이터 값이 전송될 수 있습니다.

## 스프링에서의 직렬화와 역직렬화

그렇다면, 스프링에서 직렬화와 역직렬화는 어떻게 일어날까요?

```yaml
'org.springframework.boot:spring-boot-starter-web'
```

이 의존성을 추가하면, **Jackson**이라는 라이브러리가 함께 추가가 됩니다. 이 **Jackson 라이브러리의 MappingJackson2HttpMessageConverter** 통해 **`자바 객체 → JSON`**, **`JSON → 자바 객체`**로의 직렬화 및 역직렬화가 이루어지는 원리인데요.

HttpMessageConverter의 여러 구현체들을 순회하며, 객체에 맞는 컨버터를 찾은 경우, 해당 컨버터를 통해 직렬화와 역직렬화를 수행하는 방식으로 동작합니다.

조금 더 자세하게 살펴보자면, HttpMessageConverter내부의 **ObjectMapper**가 **직렬화와 역직렬화를 수행**하는 기능을합니다.

**ObjectMapper**의 수행과정을 간략하게 살펴봅시다.

1. **직렬화**

    ```java
    ObjectMapper objectMapper = new ObejctMapper(); // ObjectMapper 생성
    Member member = new Member("Han Joon Seo", 24);
    
    Stirng output = objectMapper.writeValueAsString(member);
    // output = {"name" : "Han Joon Seo", "age" : 24}
    ```


1. **역직렬화**

    ```java
    Member newMember = objectMapper.readValue(output, Member.class);
    ```


이렇게 **`객체 → JSON`**, **`JSON → 객체`**로의 변환이 일어납니다.

조금 더 자세하게 알아보던 중, 한가지 의문이 생겼는데요. 직렬화 과정에서 *Han Joon Seo와 name, 24와 age가 어떻게 매핑*되었을까요? **리플랙션 API**는 **클래스의 구성요소(메소드, 타입, 변수, 어노테이션 등)에 접근**해서 런타임 환경에서 동적으로 그 정보를 가져올 수 있는 기능을 수행합니다.

따라서 클래스의 모든 필드 정보들을 가져와 조사하고 해당 프로퍼티에 접근할 수 있는지, 해당 프로퍼티가 직렬화 대상인지 아닌지와 같은 여부를 확인할 수 있습니다.

이 과정에서 주의할 점이 몇가지 있습니다. **ObjectMapper**는 **public** 필드나 **getter**가 정의되어있는 프로퍼티에 접근할 수 있는데요. 따라서 직렬화 대상에 대해서는 **getter가 반드시 필요**합니다.(필드값을 public으로 두는 경우는 드물기 때문이죠.)

그리고 역직렬화 과정에서, **해당 클래스의 기본 생성자를 통해 객체를 먼저 생성하기 때문**에 기본 생성자가 필수적으로 필요합니다. 따라서 `*@NoArgsConstructor`* 가 반드시 필요합니다. 그리고 직렬화와 동일하게 필드값에 접근해야 하기 때문에, **getter**가 필요합니다.

---

## @RequestBody와 @ModelAttribute

두 어노테이션 모두, **클라이언트나 서버에서 보낸 데이터를 자바에서 사용할 수 있는 객체**로 만들어준다는 공통점이 있습니다. 하지만, 쓰임과 세부 동작에서의 차이가 존재합니다.

### @RequestBody

이 어노테이션은 주로 **JSON이나 XML**형식의 데이터를 받아올 때 사용되는 어노테이션입니다. 아까 살펴본 **HttpMessageConverter**가 **JSON** 형식을 알맞은 자바 객체로 바꿔주기 때문에, 서버 측에서 객체의 접근 방식으로 데이터를 가공할 수 있는 원리입니다.

```java
@PostMapping
public ResponseEntity<ResponseDto<Long>> saveTodo(@Valid@RequestBody TodoDto request){
    Long todoId = todoService.saveTodo(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(todoId, "투두 생성 성공"));
}
```

### @ModelAttribute

이 어노테이션은 **`쿼리 스트링`**에 포함되어 날라오는 데이터나, **`multipart/form-data`**, **`application/x-www-form-urlencoded`** 형식의  데이터를 받아올 때 사용하는 어노테이션입니다. ModelAttribute 어노테이션을 적용하면 스프링 내부에서는 객체를 생성하고 setter를 통해 받아온 값을 매핑하는 구조로 동작하게 됩니다.

```java
GET /users/register?name=홍길동&email=hong@gmail.com&birthDate=1990-01-01
```

```java
@GetMapping("/search")
public String searchUsers(@ModelAttribute UserSearchDto searchDto) {
	return "userList";
}
```

데이터가 날라오면, **@ModelAttribute**가 적용된 파라미터의 객체와 쿼리 파라미터가 매핑되게 됩니다.

---

## @PathVariable과 @RequestParam

스프링에서 @PathVariable과 @RequestParam은 요청 Url에 담겨져오는 데이터를 추출하는데 사용되는 어노테이션입니다.

### @PathVariable

@PathVariable은 URL 경로의 일부를 변수로 취급하여 핸들러 메소드의 파라미터로 전달할 때 사용됩니다.

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    // 예를 들어 **/users/1**로 요청이 들어오면, **id에 1 값이 바인딩**됩니다.
}
```

위와 같이 사용하려면, **파라미터 변수명과 PathVariable의 변수명이 일치**해야 하는데요. 만약, 일치하지 않는 경우에는 아래처럼 받아올 변수명을 직접 지정할 수 있습니다.

```java
@GetMapping("/organizations/{orgId}/departments/{deptId}")
public ResponseEntity<DepartmentResponse> getDepartment(
        @PathVariable("orgId") Long organizationId,
        @PathVariable("deptId") Long departmentId) {
}
```

### @RequestParam

@RequestParam은 주로 쿼리 문자열 파라미터를 추출하는 데 사용됩니다. URL의 '?'  뒤에 오는 키-값 쌍을 처리하는 데 사용이 됩니다.

```java
@GetMapping("/users")
public List<User> searchUsers(@RequestParam String name) {
    // name 파라미터를 이용하여 사용자 검색
}

GET /users?name=한준서
```

RequestParam은 옵션값을 지정할 수 있는데요. 종류와 적용 예시는 아래와 같습니다.

1. **name** → 받아올 쿼리 파라미터 이름 설정
2. **required** → 필수 여부
3. **defaultValue** → 기본값(안들어왔을 때 대비)

```java
@RequestParam(
        name = "name",                 
        required = false,                
        defaultValue = "홍길동",            

    ) String name,
```

**@PathVariable**과 **@RequestParam**은 아무래도 **직접적으로 노출되어지는 데이터 전달 방식**이기 때문에, 민감한 데이터는 포함시키지 않고 다른 방법으로 전달하는 것이 바람직합니다.

---

# 왜 ResponseEntity를 사용하면 좋을까요?

ResponseEntity는 단순히 사용자가 요청한 데이터 뿐만 아니라, 응답 상태, 응답 해더 커스텀을 통해 보다 구체적이고 명확한 응답을 보낼 수 있습니다.

**ResponseEntity**는 단순히 응답에 데이터만 포함시켜서 보내는 것이 아닌, 응답 상태, 응답 해더를 커스텀해서 보다 구체적이고 명확한 응답을 제공할 수 있습니다.

예를 들어, 크리스마스에 선물만 딱 주는게 아니라 이쁜 상자에 선물을 포장하고 편지까지 같이 해서 전달하는 느낌과 비슷하죠.

이 **ResponseEntity**는 **HttpEntity**라는 클래스를 상속받아 구현되었는데요. **HttpEntity**는 아래처럼 **해더, 바디**를 구성요소로 포함하고 있기 때문에, 응답이나 요청을 직접 커스텀하는 것이 가능합니다.

```java
public class HttpEntity<T> {

	/**
	 * The empty {@code HttpEntity}, with no body or headers.
	 */
	public static final HttpEntity<?> EMPTY = new HttpEntity<>();

	private final HttpHeaders headers;

	@Nullable
	private final T body;
	
	.
	.
	.
	
}
```

그리고 **ResponseEntity**는 **HttpStatusCode**의 **status**라는 필드를 갖고 있기 때문에, 응답상태 또한 커스텀이 가능합니다.

```java
public class ResponseEntity<T> extends HttpEntity<T> {

	private final HttpStatusCode status;
	
	.
	.
	
}
```

실제 사용은 아래와 같습니다.

```java
    @PostMapping
    public ResponseEntity<ResponseDto<Long>> createUser(@Valid@RequestBody UserDto request){
        return ResponseEntity
			        .status(HttpStatus.CREATED)
			        .body(ResponseDto.of(userService.createUser(request), "유저 생성" ));
    }
```

**status**에는 **응답** 상태, **body**에는 **요청 데이터 본문**을 포함시켜서 반환하는 구조로 설계할 수 있습니다!
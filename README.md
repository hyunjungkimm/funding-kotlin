## 프로젝트 요약

펀딩서비스를 UI 제외한 간소화한 REST API를 구현하는 것을 목표로 한다.


## 프로젝트 목표

- kotlin으로 작성
- 예외 처리
- 테스트 코드 작성
- 회원 로그인 여부 체크 interceptor 적용
- bean validation 처리


### API 구현

- 전체 펀딩 상품 조회 API
- 펀딩하기 API
- 나의 펀딩상품 조회 API

# 요구사항
- 회원은 펀딩을 할 수 있다.
- 회원은 전체 펀딩 상품을 조회할 수 있다.
- 회원은 내가 펀딩한 기록을 조회할 수 있다.


### Project Stack 🛠

**Server**
- Kotlin
- SpringBoot
- H2
- JPA

**개발 환경**
- intellij


## 솔루션

![image](https://user-images.githubusercontent.com/97015607/155078421-5840da7a-617d-4617-9165-6fa9387f2522.png)

![image](https://user-images.githubusercontent.com/97015607/155078486-3288e6e8-7bf9-4298-8416-f519c17e8f35.png)

![image](https://user-images.githubusercontent.com/97015607/155078845-0121456e-c81a-42ca-aac8-f1f43dad2bea.png)


## schema(스키마)



### 도출된 요구사항


#### 전체 펀딩 상품 조회 API
- 펀딩기간(펀딩시작일시, 펀딩종료일시) 내의 상품만 응답한다.
- 전체 펀딩상품 응답은 다음 내용을 포함한다.
    - 펀딩상품 id, 펀딩명, 펀딩 목표 금액, 현재 펀딩 모집금액, 펀딩 사용자 수, 펀딩 기간



#### 펀딩하기 API
- 펀딩사용자 식별값, 펀딩 상품 id, 펀딩 금액을 입력값으로 받는다.
- 총 펀딩 모집금액을 넘어서면 sold-out 상태를 응답한다.
- 펀딩 한 후에
    - 상품의 현재 펀딩 모집금액, 펀딩 사용자 수를 update 한다.
    - 나의 펀딩 상품을 update 한다.



#### 나의 펀딩상품 조회 API
- 내가 펀딩한 모든 펀딩상품을 반환한다.
- 나의 펀딩상품 응답은 다음 내용을 포함한다.
    - 펀딩상품 id, 펀딩명, 나의 펀딩 금액, 펀딩 일시



### 개념적 설계

#### Entity
- 펀딩 상품
- 펀딩 주문
- 펀딩 회원


## Contributor

📧 E-mail: dosldnjsss@gmail.com

🐱 Github: https://github.com/hyunjungkimm
<br><br>


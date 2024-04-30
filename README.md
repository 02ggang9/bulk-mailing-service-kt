## OverView

Bulk-Mailing-Service-Kt는 Spring Batch 프레임워크를 기반으로 만든 대량 메일 전송 시스템입니다. 많은 사람들에게 일괄적으로 동일한 내용의 메일을 보낼 때 사용하면 됩니다.
청크 단위로 사용자에게 메일을 일괄적으로 보내며, 예상치 못한 에러나 예외가 발생해도 Retry 기능을 통해 메일 전송을 재시도 합니다. 간단한 Markdown 포맷팅(제목 및 이미지)을 지원하고 깔끔한 메일 양식을 제공합니다.

## Quick Start Guide

### 1. Project Clone

~~~java
git clone https://github.com/02ggang9/bulk-mailing-service-kt.git
~~~

### 2. docker-compose 실행

mailing > docker > docker-compose.yml 실행

### 3. application.yml 파일 추가

mailing > src > main > resources > application.yml 파일 추가

~~~yml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: # fix
    password: # fix
    properties:
      mail.smtp.starttls.enable: true
      mail.smtp.auth: true
    protocol: smtp
    test-connection: false
  datasource:
    url: jdbc:mysql://localhost:3306/springBatch?serverTimezone=Asia/Seoul&characterEncoding=UTF-8 # fix {}는 입력하고 제거할 것.
    username: user
    password: 1234
    driver-class-name: com.mysql.cj.jdbc.Driver
  batch:
    jdbc:
      initialize-schema: always
    job:
      enabled: false
      name: ${job.name:NONE}
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect
    defer-datasource-initialization: true
    properties:
      hibernate:
        format_sql: true
    show-sql: true
~~~

## 4. Spring boot 실행 후 localhost:8080 접속

- 메인 페이지(/index.html)
- 회원 저장(/member)
- 회원 조회(/members)
- 전송할 메일 제목과 본문 저장(/news-mail)

## Example

~~~json
{
  "title": "Spring Batch Mailing Service Test 1",
  "text": "### BDD Mailing Service 1차 테스트\n\n안녕하세요. 부산 개발 동아리 BDD입니다.\n\nBDD 뉴스레터를 이용해주시는 선배님들께 진심으로 감사드립니다.\n\n아래는 2024년 1월 BDD의 활동 내역들입니다.\n\n### 메인페이지 시안 작성\n\n![메인페이지 시안](https://file.notion.so/f/f/aaedcf79-6b31-4898-9a1f-5e2ad8ae925e/572fb2d5-3374-4e26-bd5f-65c17b11986f/%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9814.png?id=1f34b78c-5b64-4797-bb6c-6bd69eeeb0c1&table=block&spaceId=aaedcf79-6b31-4898-9a1f-5e2ad8ae925e&expirationTimestamp=1704960000000&signature=01l0U7nCxzkknY-KQ0rQabOsdH3HVhDu8e3lA1cBMGE&downloadName=%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9814.png)\n\n### 팀 페이지 디자이닝\n\n![팀 페이지 디자이닝](https://file.notion.so/f/f/aaedcf79-6b31-4898-9a1f-5e2ad8ae925e/e7925170-bd44-4099-8360-8a767d29c407/%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9813.png?id=7517ebbd-d7cb-4992-af1f-7f6e623257e3&table=block&spaceId=aaedcf79-6b31-4898-9a1f-5e2ad8ae925e&expirationTimestamp=1704960000000&signature=f0LAF0CeWRpJ7CEwgDg562kWvFzfIU0EiZjaVDVTkSY&downloadName=%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9813.png)\n\n앞으로도 저희 BDD를 많이 사랑해주세요. \n\n감사합니다."
}
~~~

~~~json
### BDD Mailing Service 1차 테스트

안녕하세요. 부산 개발 동아리 BDD입니다.

BDD 뉴스레터를 이용해주시는 선배님들께 진심으로 감사드립니다.

아래는 2024년 1월 BDD의 활동 내역들입니다.

### 메인페이지 시안 작성

![메인페이지 시안](https://file.notion.so/f/f/aaedcf79-6b31-4898-9a1f-5e2ad8ae925e/572fb2d5-3374-4e26-bd5f-65c17b11986f/%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9814.png?id=1f34b78c-5b64-4797-bb6c-6bd69eeeb0c1&table=block&spaceId=aaedcf79-6b31-4898-9a1f-5e2ad8ae925e&expirationTimestamp=1704960000000&signature=01l0U7nCxzkknY-KQ0rQabOsdH3HVhDu8e3lA1cBMGE&downloadName=%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9814.png)

### 팀 페이지 디자이닝

![팀 페이지 디자이닝](https://file.notion.so/f/f/aaedcf79-6b31-4898-9a1f-5e2ad8ae925e/e7925170-bd44-4099-8360-8a767d29c407/%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9813.png?id=7517ebbd-d7cb-4992-af1f-7f6e623257e3&table=block&spaceId=aaedcf79-6b31-4898-9a1f-5e2ad8ae925e&expirationTimestamp=1704960000000&signature=f0LAF0CeWRpJ7CEwgDg562kWvFzfIU0EiZjaVDVTkSY&downloadName=%ED%94%84%EB%A6%AC%EC%A0%A0%ED%85%8C%EC%9D%B4%EC%85%9813.png)

앞으로도 저희 BDD를 많이 사랑해주세요.

감사합니다.
~~~

- 발송할 메일 제목과 본문
![](https://github.com/02ggang9/02ggang9.github.io/blob/master/_posts/images/bdd/mail/quickStart/step4.png?raw=true)

- 최종 발송된 메일
![](https://github.com/02ggang9/02ggang9.github.io/blob/master/_posts/images/bdd/mail/quickStart/step6.png?raw=true)

## License
Bulk-Mailing-Service-Kt는 MIT 라이센스에 따라 사용할 수 있습니다. 자세한 내용은 LICENSE 파일을 참조해주세요.

## Update

### 2023.12.30

MVP 모델 업로드 및 README 작성

### 2024.01.10 (v1.0.0)
1. Markdown Heading 3 및 img 태그를 지원하도록 수정
2. 여러가지의 이미지를 직접 삽입할 수 있도록 수정

### 2024.01.11 (v1.0.1)
mailItemReader를 JpaPagingItemReader에서 JdbcCursorItemReader로 수정 (데이터 10만개 기준으로 성능 12배 상승)

### 2024.01.12 (v1.1.0)
ItemProcessor를 동기화에서 비동기적으로 처리하도록 수정 (데이터 1만개 기준으로 340배 상승)

### 2024.01.14 (v2.0.0)
1. 비동기 처리 후 실제 메일 API 테스트 완료
2. 자잘한 Js 코드 수정

### 2024.04.30 (v3.0.0)
1. Kotlin 언어로 전환
2. 비동기 코드를 동기 코드로 수정 (버그 발생 수정)
3. Quick Start Guide 간소화

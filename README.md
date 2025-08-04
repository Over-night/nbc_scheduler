# 사용자 인증 기반 공용 스케쥴러 어플리케이션

---

## 프로젝트 개요

해당 프로젝트는 사용자끼리 스케쥴을 관리하고 의견을 나눌 수 있는 플렛폼입니다.

**주요 기능**
- JWT 기반 사용자 인증
- 스케쥴 CRUD 기능
- 코멘트 CRUD 기능
- JPA를 활용한 데이터 영속화
- Validation을 통한 입력 데이터 검증

---

## 기술 스택
- Java 21
- Intellij IDEA


---

## 패키지 구조

```bash
src
├── main
│   ├── java
│   │   └── com
│   │       └── schedule_manager
│   │           ├── ScheduleManagerApplication.java
│   │           ├── auth
│   │           │   └── dto
│   │           │       └── JwtTokenDto.java
│   │           ├── config
│   │           │   └── SecurityConfig.java
│   │           ├── controller
│   │           │   ├── CommentController.java
│   │           │   ├── MemberController.java
│   │           │   └── ScheduleController.java
│   │           ├── dto
│   │           │   ├── comment
│   │           │   │   ├── CommentDeleteResponseDto.java
│   │           │   │   ├── CommentResponseDto.java
│   │           │   │   ├── CommentReviseRequestDto.java
│   │           │   │   ├── CommentReviseResponseDto.java
│   │           │   │   ├── CommentUploadRequestDto.java
│   │           │   │   └── CommentUploadResponseDto.java
│   │           │   ├── member
│   │           │   │   ├── MemberLoginRequestDto.java
│   │           │   │   ├── MemberPasswordChangeRequestDto.java
│   │           │   │   ├── MemberRegisterRequestDto.java
│   │           │   │   ├── MemberRegisterResponseDto.java
│   │           │   │   ├── MemberResponseDto.java
│   │           │   │   └── MemberUpdateRequestDto.java
│   │           │   └── schedule
│   │           │       ├── ScheduleDeleteResponseDto.java
│   │           │       ├── ScheduleResponseDto.java
│   │           │       ├── ScheduleReviseRequestDto.java
│   │           │       ├── ScheduleReviseResponseDto.java
│   │           │       ├── ScheduleUploadRequestDto.java
│   │           │       └── ScheduleWithCommentResponseDto.java
│   │           ├── model
│   │           │   ├── BaseTimeEntity.java
│   │           │   ├── Comment.java
│   │           │   ├── Member.java
│   │           │   └── Schedule.java
│   │           ├── repository
│   │           │   ├── CommentRepository.java
│   │           │   ├── MemberRepository.java
│   │           │   └── ScheduleRepository.java
│   │           ├── security
│   │           │   ├── JwtAuthenticationFilter.java
│   │           │   └── JwtTokenProvider.java
│   │           └── service
│   │               ├── AuthService.java
│   │               ├── CommentService.java
│   │               ├── CustomUserDetailsService.java
│   │               ├── MemberService.java
│   │               └── ScheduleService.java
│   └── resources
│       ├── application.yml
│       ├── application_temp.yml
│       ├── static
│       └── templates
└── test
    └── java
        └── com
            └── schedule_manager
                └── ScheduleManagerApplicationTests.java

```

---

## 실행방법

```src/main/java/com/schedule_manager/ScheduleManagerApplication.java``` 메인 메서드 실행

---

## 주요 사용자 기능

| 기능     | 설명          |
| ------ | ----------- |
| 회원가입   | ID/PW 기반 회원가입 |
| 로그인    | JWT 발급      |
| 스케쥴 추가 | 인증 필요 |
| 스케쥴 조회 | 누구나 가능      |
| 스케쥴 수정 | 인증 필요 |
| 스케쥴 삭제 | 인증 필요 |
| 댓글 작성  | 인증 필요       |
| 댓글 조회  | 누구나 가능       |
| 댓글 수정  | 인증 필요       |
| 댓글 삭제  | 인증 필요       |

---

## 비고
- 인증방식 : Bearer ```Authorization: Bearer {accessToken}```
- application_temp.yml을 application.yml로 이름 변경 후 별개의 설정 필요
- ERD Diagram : https://www.notion.so/ERD-245889903f748011b1c2c54877563f4e?source=copy_link
- API Document : https://www.notion.so/API-243889903f74807ea269d968af68a2dc?source=copy_link
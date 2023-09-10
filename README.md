## 멋사 SNS  

### 개발 환경


- **개발 툴** : SpringBoot 2.7.5
- **언어** : JAVA 11
- **빌드** : Gradle 6.8
- **서버** : AWS EC2
- **배포** : Docker
- **데이터베이스** : MySql 8.0
- **필수 라이브러리** : SpringBoot Web, MySQL, Spring Data JPA, Lombok, Spring Security

<br/></br>
### Swagger 주소 
http://ec2-54-180-106-134.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/#/post-controller

<br/></br>
### ERD 
![sns_db_model.png](..%2F..%2FOneDrive%2FDesktop%2FLion%2Fsns_db_model.png)


<br/></br>
### 요구사항
- 회원가입
- Swagger
- AWS EC2에 Docker 배포
- Gitlab CI & Crontab CD
- 로그인
- 포스트 작성, 수정, 삭제, 리스트
- 댓글 작성, 수정, 삭제, 리스트
- 좋아요 등록, 갯수 조회
- 마이피드
- 게시물에 댓글이나 좋아요 등록시 알림


<br/></br>
### Endpoints
POST /api/v1//users/join 회원가입 

POST /api/v1/users/login 로그인 

<br>

GET /api/v1/posts/{postId} 상세 조회

GET /api/v1/posts 게시물 목록 조회

POST /api/v1/posts 게시물 등록

PUT /api/v1/posts/{postId} 게시물 수정

DELETE /api/v1/posts/{postId} 게시물 삭제


<br>
GET /api/v1/posts/{postsId}/comments 댓글 목록 조회 

POST /api/v1/posts/{postsId}/comments 댓글 등록

PUT /api/v1/posts/{postsId}/comments/{id} 댓글 수정

DELETE /api/v1/posts/{postsId}/comments/{id} 댓글 삭제

<br>
GET api/v1/posts/{postsId}/likes 좋아요 수 조회

POST /api/v1/posts/{postId}/likes 좋아요 누르기/취소


<br>
GET /api/v1/posts/my 마이피드 조회
<br>

<br>
GET /api/v1/alarms 알람 조회

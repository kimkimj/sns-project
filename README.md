### 종합 프로젝트: 계시판 구현 
-[X] CI/CD 

-[X] 회원가입, 로그인 

-[X] 인증 / 인가 

-[X] 포스트 작성, 수정, 삭제, 상세조회, 전체 조회 

<br/></br>
#### Swagger 주소 
http://ec2-54-180-106-134.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/#/post-controller

<br/></br>
#### Endpoints
POST /api/v1//users/join 회원가입 

POST /api/v1/users/login 로그인 

GET /api/v1/posts 포스트 전체 조회

GET /api/v1/posts/{postId} 상세조회

PUT /api/v1/posts 포스트 작성

PUT /api/v1/posts/{postId} 수정

DELETE /api/v1/posts/{postId} 삭제


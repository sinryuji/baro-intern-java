# 프로젝트 설명
DDD 4계층 아키텍처를 적용하였고, Spring Security를 활용하여 인증 로직 구현하였습니다. Docker를 활용하여 개발 환경 구축 및 배포하였습니다. Mockito를 활용하여 테스트 코드를 작성하였습니다. 

# 실행 방법
1. docker-compose up -d로 docker-compose.yml의 mysql 컨테이너를 구동합니다.
2. 컨터네이네 docker exec -it [container] /bin/bash로 접속한 후 mysql -u root -p로 cli로 접속한 후 create database [database name]; 으로 데이터베이스를 만듭니다.
3. application.yml에 ${DB_URL}에는 만든 테이블을 포함한 URL, username과 password는 각각 root, root 입니다.
4. 나머지 환경 변수를 채우고 실행하면 됩니다.

# Swagger Endpoint
http://43.201.248.44:8080/swagger-ui/index.html

# EC2 Endpoint
http://43.201.248.44:8080

# API
- 일반유저 회원가입
  - POST http://43.201.248.44:8080/api/v1/users/signup
- 관리자 회원가입
  - POST http://43.201.248.44:8080/api/v1/users/signup/admin
  - adminToken이 필요한데 값은 'admin' 입니다.
- 로그인
  - POST http://43.201.248.44:8080/api/v1/users/login
- 내 정보 가져오기
  - GET http://43.201.248.44:8080/api/v1/users/me
  - 토큰 필요합니다.
- 유저 목록 가져오기
  - GET http://43.201.248.44:8080/api/v1/users
  - admin 토큰 필요합니다.

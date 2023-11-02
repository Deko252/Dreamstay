DreamStay Webapp Project 
========================
## 읽기전에
본 리포지토리에는 프로젝트 구동에 필수적인 2개 파일을 민감한 개인정보 노출을 피하기 위해 고의로 제거함<br/>
naver.properties, application.yml

## 프로젝트 소개
호텔 통합관리 웹사이트 제작을 통해 Spring Boot를 통한 웹프로그래밍 프로젝트 과제 수행<br/>
실제 호텔 브랜드사이트 참고하여 모습 및 기능구현 목표 [링크](https://josunhotel.com)<br/>
제작한 프로젝트 사이트(현재불가능) [링크](http://bit-dreamstay.kro.kr)
* 회원/비회원, 공지사항, 문의게시판 등 CRUD 기능
* 메일 인증을 회원가입 인증
* 카카오 및 네이버 소셜로그인
* 예약 현황을 반영한 예약 가능한 객실이 노출되는 예약검색
* 실시간 소켓 프로그래밍을 통한 채팅 상담 기능 구현
* 네이버 스마트에디터 2.0 자체 수정
* 기타 넣어보고 싶은 CSS 효과들.



## 개발 일정
* 기간 : 2023.05.01 ~ 2023.05.22 (약 3주)
* 인원 : 6명


## 팀원
|이름|작업했던 것|Git URL|
|-------------------|---|---|
|유찬민|메인페이지, 예약, 결제페이지|[Deko252](https://github.com/Deko252)|

## 사용 기술
### Front-End
<div>
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
</div>
  
### Back-End
<div>
  <img src="https://img.shields.io/badge/java 11-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/apache tomcat 9-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black">
  <img src="https://img.shields.io/badge/mysql 8-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/Maven-c71a36?style=for-the-badge&logo=Apache Maven&logoColor=white"> 
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
  <img src="https://img.shields.io/badge/ubuntu 20.04-e95420?style=for-the-badge&logo=ubuntu&logoColor=black"> 
</div>

### Infra
<div>
  <img src="https://img.shields.io/badge/naver_cloud-03c75a?style=for-the-badge&logo=naver&logoColor=white">
</div>

### Tools
<div> 
  <img src="https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
  <img src="https://img.shields.io/badge/postman-ff6c37?style=for-the-badge&logo=postman&logoColor=white">
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>

### 사용 API
* 다음 도로명주소(우편번호) 검색
* 네이버/카카오 소셜로그인

## ERD
<img src="https://github.com/itnaupna/DS_PUB/blob/master/image.png?raw=true">

## Images
### * 메인화면
<img src="https://github.com/Deko252/jumjin/assets/114369279/104fc018-c51b-42b8-9462-a06a21269733?raw=true">
<img src="https://github.com/Deko252/jumjin/assets/114369279/8dbfb173-2803-45ea-bee0-3522429b15d9?raw=true">

### * 예약화면
<img src="https://github.com/Deko252/jumjin/assets/114369279/5eb14e95-1160-4299-86f3-1a2cd23bf8d9?raw=true">

## 제거된 파일
<details>
<summary>접기/펼치기</summary>

## application.yml
  <pre>
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://DB주소?serverTimezone=Asia/Seoul
    username: DB 아이디
    password: DB 비밀번호
  servlet:
    multipart:
      max-file-size:
        4MB
  mail:
    host: smtp.naver.com
    port: 465
    username: '네이버 계정'
    password: '네이버 비번'
    protocol: smtp
    properties:
      mail:
        smtp:
          auth: true
          ssl:
            enable: true
            required: true
  #mybatis
mybatis:
  type-aliases-package: com.bitnc4.dto
  mapper-locations:
  - /mapper/**/*.xml
</pre>
  
## naver.properties
  <pre>
cloud.aws.stack.auto=false
cloud.aws.region.static=ap-northeast-2
ncp.accessKey=네이버클라우드 AccessKey
ncp.secretKey=네이버클라우드 SecretKey
ncp.regionName=kr-standard
ncp.endPoint=https://kr.object.ncloudstorage.com
  </pre>
</details>


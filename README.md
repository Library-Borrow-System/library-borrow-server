# library-borrow-server
[프로그래머스 데브코스 백엔드 3기] 개인 프로젝트 과제로 도서 대여 및 반납 서비스를 구현한 서버 입니다.

<br/>

## [3기-C] 김소현 ( [thguss](https://github.com/thguss) )
### 🛠 Used Stacks
![](https://img.shields.io/badge/Java-007396.svg?style=for-the-badge&logo=OpenJDK&logoColor=white)
![](https://img.shields.io/badge/SpringBoot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)
![](https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-badge&logo=MySQL&logoColor=white)
 
 <br/>
 
### 🐾 API 명세서 ( [전체 명세서 바로가기](https://energetic-mimosa-b8e.notion.site/fc9d05f29f2a4c20a7af0d7d56c2e95b?v=2bacf1223cba4ad7bb76fcdbd85a4c4d) )

| 기능명 | url | 명세서 |
| :-----: | :---: | :---: |
| 책 등록 API | [POST] /api/v1/books | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-affe8e000c004f06b06e2f7f5642f5d9) | 
| 책 전체 조회 API | [GET] /api/v1/books | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-8f2b7c02eb0a427d94919187621e9bf6) | 
| 책 상세 조회 API | [GET] /api/v1/book/{bookId} | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-bd1e373b8f6443b4b7ae1f2bb692af1a) |  
| 책 수정 API | [PUT] /api/v1/book/{bookId} | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-dccdd842b8f242468cb0859174ecb347) |  
| 책 삭제 API | [DELETE] /api/v1/book/{bookId} | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-735be36e9a0047c78fbf2a49d4c38e7f) |  
| 책 대여 API | [POST] /api/v1/borrows | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-3cfb9260463040c1bfa69f187c80a361) |  
| 대여 중인 책 조회 API | [GET] /api/v1/borrows | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-1f5cc6108a3e462ca2f0862be508d4f3) |
| 책 반납 API | [POST] /api/v1/borrow/return?borrowId=""&bookId="" | [명세서 바로가기](https://energetic-mimosa-b8e.notion.site/API-e8d40d9f42ca4bbe9e8180be14028b7a) |

<br/>

### 🌿 Branch Strategy

| Branch Name | 설명 |
| :---: | :-----: |
| main | 초기 세팅 존재 |
| develop | 구현 완료 브랜치 |
| feature/#issue_num | 이슈 별 기능 구현 브랜치 |

<br/>

### 📁 Project Foldering

<img width="449" alt="image" src="https://user-images.githubusercontent.com/55437339/205481738-8061b6d2-fb8f-4d56-aaca-72219a5431a5.png">

<br/>

### 🥫 ERD

<img width="711" alt="image" src="https://user-images.githubusercontent.com/55437339/205498657-6a2775df-5b54-47ce-8762-bc668094bc6f.png">


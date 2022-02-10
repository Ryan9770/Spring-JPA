# Spring-JPA
JPA, Mariadb를 이용하여 만든 게시판

# 사진
1) 기본 메인

![1](https://user-images.githubusercontent.com/93500878/153361842-c43736b4-9b9f-4420-83c7-a2ea1f0ec6aa.PNG)

2) 글 작성

![2](https://user-images.githubusercontent.com/93500878/153362327-318d0640-f48c-49a1-b971-661d7e64ef0b.PNG)

3) 리스트

![3](https://user-images.githubusercontent.com/93500878/153362410-de39bf59-116d-4924-925f-80ae846626cd.PNG)

4) 글 보기

![4](https://user-images.githubusercontent.com/93500878/153362451-dd394456-9a9e-4cdd-ade7-a744ff99edec.PNG)

5) 글 수정

![5](https://user-images.githubusercontent.com/93500878/153362682-5d230c10-c919-489b-9be3-bebc659b2482.PNG)
![6](https://user-images.githubusercontent.com/93500878/153362691-5a190e3d-c1f4-43c7-a90c-296b4e3d3cad.PNG)
![캡처](https://user-images.githubusercontent.com/93500878/153362713-be3b92e3-d412-48ad-b4fe-62c20fc261be.PNG)

6) 글 삭제

![7](https://user-images.githubusercontent.com/93500878/153362807-47f34dca-0f84-4913-96c6-d13533ba770d.PNG)

7) 검색

검색 전

![8](https://user-images.githubusercontent.com/93500878/153363134-e3efca54-210b-482f-963c-608e28623d9d.PNG)

검색 후

![캡처](https://user-images.githubusercontent.com/93500878/153363142-8cb913e7-a632-42c4-bbd2-295af176635b.PNG)

# 후기 

JPA를 이용하여 CRUD가 가능한 간단한 게시판을 구현해 봤네요

1:1, 1:다, 다:다 형식의 Entity 관계 형성까진 제가 아직 어려워서 그런지 구현을 못하겠네요.

그리고 JpaRepository 상속받고 어떤 형식으로 CRUD가 이루어지는지 확인했더니

JpaRepository에 PagingAndSortingRepository를 상속받고 거기에 CrudRepository를 상속 받은게 보이더라구요.

정말 신기합니다 ㅋㅋ

이제 JPA를 이용해서 RESTful 방식을 구현해봐야겠어요!

다음 토이 프로젝트 => REST API 구현


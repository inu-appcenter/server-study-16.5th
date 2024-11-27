# Github Actions란 무엇일까요?

## 1. Github Actions 소개

GitHub Actions는 GitHub에서 제공하는 자동화 및 CI/CD 도구

테스트, 빌드, 배포 등 자동화

파이프라인을 구성하는 일관된 품질을 유지하고 효율적인 개발 워크플로우

git에 연관되어 있기 때문에 편리

- 파이프라인→ job(github actions)

코드 변경이 배포 가능한 최종 상태가 되기까지 일련의 자동화된 작업 흐름

- github actions에서의 CI/CD

ci : bulid, test를 github에 위임

cd : 서버에 배포하는 것을 github에 위임

## 2. Workflow란?

### 1. Workflow를 작성하기 위한 문법들

1. workflow

-Workflow 파일은 YAML으로 작성되고, Github Repository의 .github/workflows 폴더 아래에 저장됨

-여러개의 workflow 파일 생성 가능(각자 하는 일, 언제 하는지 설정함)

-자동화 작업을 정의하는 YAML 파일의 최상위 구성

-이름 설정 가능, 여러 개의 Job으로 구성

-하나이 프로세스에서 완료되어야 하는 작업

*on

언제 해당 workflow가 실행되는지

1. event
    
    이벤트 트리거
    Workflow를 Trigger(실행)하는 특정 활동이나 규칙
    
    이벤트 트리거는 코드가 푸시되거나 풀 리퀘스트(schedule, workflow_dispatch)가 생성되는 등의 상황에서 Workflow를 실행시키는 조건
    
2. jobs
    1. 가상환경 선택
    
    Job은 Workflow 내에서 실제 작업을 수행하는 단위
    
    각 Job은 별도의 가상 환경, 컨테이너에서 실행되며, 여러 개의 Job을 병렬 또는 순차적으로 실행
    
    ```yaml
    jobs:
    	build: # build하는 job
    	 ....
    	test: # test하는 job
    	 ....
    ```
    
    - runs-on : 실행환경 지정
    
    Job을 실행하기 위한 가상환경에서 운영체제 선택할 수 있으며, GitHub에서는 
    
    ubuntu-latest, windows-latest, ubuntu-22.02
    
    - step : 각 job에서 실행할 작업., 순서대로
    - run : 스크립트 실행
    - uses : actions(워크플로우 실행 환경)
    
    *가상환경
    
    소프트웨어가 독립적으로 실행될 수 있는 환경
    
    컴퓨터 안의 컴퓨터
    

```yaml
name: CI/CD

on:
  push:
    branches: [ "master" ] # trigger : master branch에 push될 때 workflow가 실행
  pull_request:
    branches: [ "master" ] # trigger : master branch에 PR될 때 workflow가 실행

jobs:
  build: # job이름
    runs-on: ubuntu-latest # Job을 실행할 가상환경을 선택
    steps: # 순서대로
      - uses: actions/checkout@v1 # 실습 워크플로우에 체크아웃 액션을 추가
															    # 소유자 : actions, 저장소 : checkout, @ : 버전

      - name: Set Java
        uses: actions/setup-java@v3 # 가상환경에 Java 17 버전의 JDK를 설치
        with:
          java-version: '17'
          distribution: 'temurin'

      - uses: actions/checkout@v3  # 실행할 셸 명령어
      - run: | 
            mkdir -p ./src/main/resources
            touch ./src/main/resources/application.properties 
      - run: echo "${{ secrets.APPLICATION }}" 
		      > ./src/main/resources/application.properties # gitignore로 git엔 resources가 없기 때문
      - run: cat ./src/main/resources/application.properties # 출력

      - name: Setup Gradle #  Gradle 환경을 설정
        uses: gradle/gradle-build-action@v2

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew # gradlew 파일에 실행 권한을 부여

      - name: Run build with arGradle Wrapper
        run: ./gradlew build #  Gradle 빌드를 실행하여 프로젝트를 컴파일

      - name: Send file to the server
        uses: appleboy/scp-action@master # 서버에 JAR 파일을 전송(scp:파일전송)
        with:
          host: ${{ secrets.HOST }} # 이 호스트(원격 서버의 IP 주소 또는 도메인)의
          username: ${{ secrets.USER }} # 이 유저의 
          password: ${{ secrets.PASSWORD }} # 이 패스워드로
          port: ${{ secrets.SSH_PORT }} # 이 포트로 보냄
          source: "./build/libs/*.jar" # 보내고자 하는 파일위치
          target: download # 저장 위치

      - name: Deploy remote ssh commands using password
        uses: appleboy/ssh-action@master # SSH를 통해 원격 서버에 명령을 실행하거나 파일을 전송하는 데 사용되는 액션
        with:                            # master 브랜치에 있음 
          host: ${{ secrets.HOST }}
          username: ${{ secrets.USER }}
          password: ${{ secrets.PASSWORD }}
          port: ${{ secrets.SSH_PORT }}
          script_stop: true
          script: |
            sh deploy/deploy.sh
            
        # jar 파일 : 컴파일된 코드
        # Java 애플리케이션을 실행할 때 클래스 경로에 jar 파일을 추가하여 안에 포함된 모든 코드를 현재 애플리케이션에서 사용
        # jar 파일 자체를 실행 가능한 애플리케이션이되며 이를 실행
```
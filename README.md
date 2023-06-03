# Read me

# MySQL 설치
우선 mysql 패키지를 검색해본다.
```
~ % brew search mysql
```
mysql 5.7 설치하기
```
~ % brew install mysql@5.7
```
info 명령으로 설치한 5.7 MySQL 을 보면 아래와 같은 메시지를 확인할 수 있다.
```
~ % brew info mysql@5.7 
......
mysql@5.7 is keg-only, which means it was not symlinked into /opt/homebrew,
because this is an alternate version of another formula.

If you need to have mysql@5.7 first in your PATH, run:
  echo 'export PATH="/opt/homebrew/opt/mysql@5.7/bin:$PATH"' >> ~/.zshrc
......
```
5.7은 keg-only 라는데 대체 버전이라 인식이 안되서 별도 연결을 해줘야 한다는 의미여서, 제공해주는 커맨드를 실행해서 사용중인 쉘 rc 스크립트에 path를 직접 추가해준다.  
```
~ % echo 'export PATH="/usr/local/opt/mysql@5.7/bin:$PATH"' >> ~/.zshrc
~ % source ~/.zshrc
```
설정 후 버전 확인 해보기
```
~ % mysql --version
mysql  Ver 14.14 Distrib 5.7.38, for osx10.17 (x86_64) using  EditLine wrapper
```
MySQL 시작
```
 % brew services start mysql@5.7
```
```


```


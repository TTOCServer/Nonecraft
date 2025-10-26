# Nonecraft

- [English (US)](READMEs/README_en_us.md)
- [English (UK)](READMEs/README_en_gb.md)
- [English (Australia)](READMEs/README_en_au.md)
- [日本語](READMEs/README_ja_jp.md)
- [한국어](READMEs/README_ko_kr.md)
- [Русский](READMEs/README_ru_ru.md)
- [Deutsch](READMEs/README_de_de.md)
- [Español](READMEs/README_es_es.md)
- [Français](READMEs/README_fr_fr.md)
- [Português](READMEs/README_pt_pt.md)
- [العربية](READMEs/README_ar_sa.md)
- [Қазақша](READMEs/README_kk_kz.md)
- [Монгол](READMEs/README_mn_mn.md)
- [한국어 (조선)](READMEs/README_ko_kp.md)
- [繁體中文 (香港)](READMEs/README_zh_hk.md)
- [繁體中文 (台灣)](READMEs/README_zh_tw.md)
- [简体中文](READMEs/README_zh_cn.md)

> **AI 생성 선언**: 이 프로젝트는 **부분적으로 또는 완전히 AI에 의해 생성되었습니다**, 코드는 정상 사용, 참조 및 학습 목적으로만 사용되며, **다시 AI 훈련에 사용하지 마십시오**!

- 서버 포트를 기록한 사람들에게 충격을 주기 위한 가짜 Minecraft 서버?
- 실제로는 MOTD를 표시하고 거부 프롬프트를 표시하는 작은 도구로, 서버가 정보를 전송하는 것을 모방합니다.

## 기능 특성

- Minecraft 1.20.1 서버를 가장한 사용자 정의 다중 포트
- (서식이 지정된 코드) 사용자 정의 MOTD 및 거부 연결 요청 코드
- 사용자 정의 IP를 텍스트로 표시

## 빠른 시작

### 설치

```bash
java -jar <파일명>
```
완료!

### ip2stress.txt 구성

일반적으로 .jar는 실행 디렉토리에 ip2stress.txt를 자동으로 생성합니다. 없는 경우 생성하십시오 (비 ASCII 문자 파일 이름에 주의하십시오!)
ip2stress.txt의 형식은 다음과 같습니다:
<IP> = <텍스트>
```text
127.0.0.1 = 로컬
192.168.3.1 = 1학년 1반
192.168.3.2 = 1학년 2반
192.168.3.3 = 1학년 3반
192.168.3.4 = 1학년 4반
…………
```

## 빌드

Maven을 사용하여 프로젝트 빌드:

```bash
mvn clean package
```
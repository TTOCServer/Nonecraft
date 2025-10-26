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

> **AI жасалған мәлімдеме**: Бұл жоба **ішінара немесе толығымен AI жасаған**, код тек қалыпты пайдалану, анықтама және оқу мақсаттарына арналған, **оны қайтадан AI жаттығуына пайдаланбаңыз**!

- Сервер порттарын жазып алған адамдарға шошыру үшін жалған Minecraft сервері?
- Шын мәнінде бұл MOTD көрсететін және бас тарту хабарламаларын көрсететін кішкентай құрал, сервердің ақпарат жіберуін еліктейді.

## Ерекшеліктері

- Minecraft 1.20.1 серверін еліктейтін теңшелетін көп порттар
- (Пішімделген код) Теңшелетін MOTD және байланысты бас тарту сұрауларының кодтары
- Теңшелетін IP мәтінге түрлендіру

## Жылдам бастау

### Орнату

```bash
java -jar <файл_аты>
```
Дайын!

### ip2stress.txt конфигурациясы

Әдетте .jar жұмыс каталогында ip2stress.txt файлын автоматты түрде жасайды. Егер болмаса, оны жасаңыз (ASCII емес таңбалары бар файл атауларына назар аударыңыз!)
ip2stress.txt пішімі келесідей:
<IP> = <мәтін>
```text
127.0.0.1 = Жергілікті
192.168.3.1 = 10 сынып 1
192.168.3.2 = 10 сынып 2
192.168.3.3 = 10 сынып 3
192.168.3.4 = 10 сынып 4
…………
```

## Құрастыру

Maven көмегімен жобаны құрастыру:

```bash
mvn clean package
```
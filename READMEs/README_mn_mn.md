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

> **AI үүсгэсэн мэдэгдэл**: Энэ төсөл нь **хэсэгчлэн эсвэл бүрэн AI үүсгэсэн**, кодыг зөвхөн ердийн хэрэглээ, лавлагаа, сурах зорилгоор ашиглах бөгөөд **дахин AI сургалтад ашиглахгүй байхыг хүсье**!

- Серверийн портуудыг бүртгэсэн хүмүүст цочирдуулах хуурамч Minecraft сервер уу?
- Үнэндээ энэ нь зөвхөн MOTD харуулж, татгалзах мессежүүдийг харуулдаг жижиг хэрэгсэл бөгөөд сервер мэдээлэл илгээхийг дуурайдаг.

## Онцлог шинж чанарууд

- Minecraft 1.20.1 сервер гэж дүр эсгэдэг тохируулсан олон порт
- (Форматлагдсан код) Тохируулсан MOTD ба татгалзах холболтын хүсэлтийн кодуд
- Тохируулсан IP текстийн дэлгэц

## Хурдан эхлэх

### Суулгах

```bash
java -jar <файлын_нэр>
```
Ингээд л боллоо!

### ip2stress.txt тохируулга

Ерөнхийдөө .jar ажиллагааны хавтасанд ip2stress.txt файлыг автоматаар үүсгэдэг. Хэрэв байхгүй бол үүсгэнэ үү (ASCII бус тэмдэгттэй файлын нэрсэд анхаарлаа хандуулаарай!)
ip2stress.txt форматыг дараах байдлаар хийнэ:
<IP> = <текст>
```text
127.0.0.1 = Дотоод
192.168.3.1 = 10-р анги 1
192.168.3.2 = 10-р анги 2
192.168.3.3 = 10-р анги 3
192.168.3.4 = 10-р анги 4
…………
```

## Бүтээх

Maven ашиглан төслийг бүтээх:

```bash
mvn clean package
```
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

> **AI-сгенерированное заявление**: Этот проект **частично или полностью создан AI**, код предназначен только для нормального использования, справки и обучения, **пожалуйста, не используйте его снова для обучения AI**!

- Фальшивый сервер Minecraft, чтобы шокировать тех, кто записал порты своего сервера?
- На самом деле это просто маленький инструмент, который отображает MOTD и сообщения об отказе, имитируя отправку информации сервером.

## Особенности

- Пользовательские мультипорты, притворяющиеся сервером Minecraft 1.20.1
- (Форматированный код) Пользовательские коды MOTD и запросов на отказ в соединении
- Пользовательское отображение IP в виде текста

## Быстрый старт

### Установка

```bash
java -jar <имя_файла>
```
Готово!

### Настройка ip2stress.txt

Обычно .jar автоматически генерирует ip2stress.txt в рабочей директории. Если нет, создайте его (обратите внимание на имена файлов с не-ASCII символами!)
Формат ip2stress.txt следующий:
<IP> = <текст>
```text
127.0.0.1 = Локальный
192.168.3.1 = 10 класс 1
192.168.3.2 = 10 класс 2
192.168.3.3 = 10 класс 3
192.168.3.4 = 10 класс 4
…………
```

## Сборка

Сборка проекта с помощью Maven:

```bash
mvn clean package
```
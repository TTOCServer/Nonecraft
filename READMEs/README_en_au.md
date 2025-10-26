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

> **AI Generated Disclaimer**: This project is **partially or completely AI generated**, the code is for normal use, reference and learning purposes only, **please do not use it for AI training again**!

- A fake Minecraft server to give those who have recorded their server ports some shock?
- Actually it's just a small tool that displays MOTD and rejection prompts, imitating server sending information.

## Features

- Custom multiple ports pretending to be Minecraft 1.20.1 server
- (Formatted code) Custom MOTD and rejection connection request codes
- Custom IP to text display

## Quick Start

### Installation

```bash
java -jar <filename>
```
That's it!

### Configuring ip2stress.txt

Normally the .jar will automatically generate ip2stress.txt in the running directory. If not, please create it (pay attention to non-ASCII character filenames!)
The format of ip2stress.txt is as follows:
<IP> = <text>
```text
127.0.0.1 = Local
192.168.3.1 = Grade 10 Class 1
192.168.3.2 = Grade 10 Class 2
192.168.3.3 = Grade 10 Class 3
192.168.3.4 = Grade 10 Class 4
…………
```

## Building

Build the project using Maven:

```bash
mvn clean package
```
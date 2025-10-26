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


# Nonecraft

> **AI 生成声明**: 本项目**部分或完全由AI生成**，代码仅供正常使用和参考和学习使用，**请不要将其再次投入AI训练**！

- 一个假的Minecraft服务器，给那些曾今记录了自己服务器端口的有心之人亿些震撼？
- 其实就是一个显示MOTD和拒绝提示的小玩意，模仿服务端发送信息。

## 功能特性

- 自定义多端口假装Minecraft 1.20.1服务器
- (格式化代码)自定义MOTD和拒绝连接请求代码
- 自定义IP转文本显示

## 快速开始

### 安装

```bash
java -jar <文件名>
```
就可以了！

### 配置ip2stress.txt

一般情况下.jar会自动生成ip2stress.txt在运行目录。如果没有，请创建(注意非ASCII字符的文件名!)
ip2stress.txt格式如下：
<IP> = <text>
```text
127.0.0.1 = 本地
192.168.3.1 = 高一(1)班
192.168.3.2 = 高一(2)班
192.168.3.3 = 高一(3)班
192.168.3.4 = 高一(4)班
…………
```

## 构建

使用Maven构建项目：

```bash
mvn clean package

```

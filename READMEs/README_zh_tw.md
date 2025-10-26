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

> **AI 生成聲明**: 本專案**部分或完全由AI生成**，代碼僅供正常使用和參考和學習使用，**請不要將其再次投入AI訓練**！

- 一個假的Minecraft伺服器，給那些曾今記錄了自己伺服器端口的有心之人一些震撼？
- 其實就是一個顯示MOTD和拒絕提示的小玩意，模仿服務端發送信息。

## 功能特性

- 自定義多端口假裝Minecraft 1.20.1伺服器
- (格式化代碼)自定義MOTD和拒絕連接請求代碼
- 自定義IP轉文本顯示

## 快速開始

### 安裝

```bash
java -jar <文件名>
```
就可以了！

### 配置ip2stress.txt

一般情況下.jar會自動生成ip2stress.txt在運行目錄。如果沒有，請創建(注意非ASCII字符的文件名!)
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

## 構建

使用Maven構建專案：

```bash
mvn clean package
```
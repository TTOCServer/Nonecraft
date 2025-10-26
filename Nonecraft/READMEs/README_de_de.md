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

> **AI-generierte Erklärung**: Dieses Projekt ist **teilweise oder vollständig von KI generiert**, der Code dient nur dem normalen Gebrauch, Referenz- und Lernzwecken, **bitte verwenden Sie ihn nicht erneut für KI-Training**!

- Ein gefälschter Minecraft-Server, um denen, die ihre Serverports aufgezeichnet haben, einen Schock zu versetzen?
- Eigentlich ist es nur ein kleines Tool, das MOTD anzeigt und Ablehnungsaufforderungen anzeigt und das Senden von Informationen durch den Server nachahmt.

## Funktionen

- Benutzerdefinierte Mehrfachports, die einen Minecraft 1.20.1-Server vortäuschen
- (Formatierter Code) Benutzerdefinierte MOTD- und Ablehnungsverbindungsanforderungscodes
- Benutzerdefinierte IP-zu-Text-Anzeige

## Schnellstart

### Installation

```bash
java -jar <Dateiname>
```
Fertig!

### Konfiguration von ip2stress.txt

Normalerweise generiert die .jar-Datei automatisch ip2stress.txt im Ausführungsverzeichnis. Wenn nicht, erstellen Sie sie bitte (achten Sie auf Dateinamen mit Nicht-ASCII-Zeichen!)
Das Format von ip2stress.txt ist wie folgt:
<IP> = <Text>
```text
127.0.0.1 = Lokal
192.168.3.1 = Klasse 10 Klasse 1
192.168.3.2 = Klasse 10 Klasse 2
192.168.3.3 = Klasse 10 Klasse 3
192.168.3.4 = Klasse 10 Klasse 4
…………
```

## Erstellung

Erstellen Sie das Projekt mit Maven:

```bash
mvn clean package
```
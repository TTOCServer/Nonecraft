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

> **Déclaration générée par IA** : Ce projet est **partiellement ou complètement généré par IA**, le code est destiné à un usage normal, à des fins de référence et d'apprentissage uniquement, **veuillez ne pas l'utiliser à nouveau pour la formation IA** !

- Un faux serveur Minecraft pour choquer ceux qui ont enregistré les ports de leur serveur ?
- En fait, ce n'est qu'un petit outil qui affiche MOTD et les invites de refus, imitant l'envoi d'informations par le serveur.

## Caractéristiques

- Ports multiples personnalisés prétendant être un serveur Minecraft 1.20.1
- (Code formaté) Codes personnalisés MOTD et demandes de refus de connexion
- Affichage personnalisé IP en texte

## Démarrage rapide

### Installation

```bash
java -jar <nom_fichier>
```
C'est fait !

### Configuration de ip2stress.txt

Normalement, le .jar génère automatiquement ip2stress.txt dans le répertoire d'exécution. Si ce n'est pas le cas, veuillez le créer (attention aux noms de fichiers avec des caractères non ASCII !)
Le format de ip2stress.txt est le suivant :
<IP> = <texte>
```text
127.0.0.1 = Local
192.168.3.1 = Classe de 10e 1
192.168.3.2 = Classe de 10e 2
192.168.3.3 = Classe de 10e 3
192.168.3.4 = Classe de 10e 4
…………
```

## Construction

Construisez le projet avec Maven :

```bash
mvn clean package
```
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

> **Declaração Gerada por IA**: Este projeto é **parcial ou completamente gerado por IA**, o código é apenas para uso normal, referência e fins de aprendizagem, **por favor não o use novamente para treinamento de IA**!

- Um servidor falso de Minecraft para dar um choque àqueles que registraram as portas do seu servidor?
- Na verdade é apenas uma pequena ferramenta que exibe MOTD e mensagens de recusa, imitando o envio de informações do servidor.

## Características

- Portas múltiplas personalizadas fingindo ser um servidor Minecraft 1.20.1
- (Código formatado) Códigos personalizados de MOTD e pedidos de recusa de conexão
- Exibição personalizada de IP para texto

## Início Rápido

### Instalação

```bash
java -jar <nome_do_arquivo>
```
Pronto!

### Configuração de ip2stress.txt

Normalmente o .jar gerará automaticamente ip2stress.txt no diretório de execução. Se não, crie-o (preste atenção a nomes de arquivo com caracteres não ASCII!)
O formato de ip2stress.txt é o seguinte:
<IP> = <texto>
```text
127.0.0.1 = Local
192.168.3.1 = 10ª Classe 1
192.168.3.2 = 10ª Classe 2
192.168.3.3 = 10ª Classe 3
192.168.3.4 = 10ª Classe 4
…………
```

## Construção

Construa o projeto usando Maven:

```bash
mvn clean package
```
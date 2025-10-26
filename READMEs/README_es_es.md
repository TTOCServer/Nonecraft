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

> **Declaración generada por IA**: Este proyecto es **parcial o completamente generado por IA**, el código es solo para uso normal, referencia y fines de aprendizaje, **¡por favor no lo use nuevamente para entrenamiento de IA**!

- ¿Un servidor falso de Minecraft para dar un shock a aquellos que han registrado los puertos de su servidor?
- En realidad es solo una pequeña herramienta que muestra MOTD y mensajes de rechazo, imitando el envío de información del servidor.

## Características

- Puertos múltiples personalizados que simulan ser un servidor Minecraft 1.20.1
- (Código formateado) Códigos personalizados de MOTD y solicitudes de rechazo de conexión
- Visualización personalizada de IP a texto

## Inicio rápido

### Instalación

```bash
java -jar <nombre_de_archivo>
```
¡Listo!

### Configuración de ip2stress.txt

Normalmente el .jar generará automáticamente ip2stress.txt en el directorio de ejecución. Si no, créelo (¡preste atención a los nombres de archivo con caracteres no ASCII!)
El formato de ip2stress.txt es el siguiente:
<IP> = <texto>
```text
127.0.0.1 = Local
192.168.3.1 = Grado 10 Clase 1
192.168.3.2 = Grado 10 Clase 2
192.168.3.3 = Grado 10 Clase 3
192.168.3.4 = Grado 10 Clase 4
…………
```

## Construcción

Construya el proyecto usando Maven:

```bash
mvn clean package
```
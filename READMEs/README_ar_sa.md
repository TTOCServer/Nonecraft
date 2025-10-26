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

> **بيان تم إنشاؤه بواسطة الذكاء الاصطناعي**: هذا المشروع **تم إنشاؤه جزئيًا أو كليًا بواسطة الذكاء الاصطناعي**، والكود مخصص للاستخدام العادي والمرجعية وأغراض التعلم فقط، **يرجى عدم استخدامه مرة أخرى لتدريب الذكاء الاصطناعي**!

- خادم Minecraft مزيف لإصابة أولئك الذين سجلوا منافذ خوادمهم بالصدمة؟
- في الواقع هو مجرد أداة صغيرة تعرض MOTD ورسائل الرفض، وتقلد إرسال المعلومات من الخادم.

## الميزات

- منافذ متعددة مخصصة تتظاهر بأنها خادم Minecraft 1.20.1
- (كود منسق) رموز MOTD المخصصة وطلبات رفض الاتصال
- عرض مخصص IP إلى نص

## البدء السريع

### التثبيت

```bash
java -jar <اسم_الملف>
```
هذا كل شيء!

### تكوين ip2stress.txt

عادةً ما يقوم ملف .jar بإنشاء ip2stress.txt تلقائيًا في دليل التشغيل. إذا لم يكن موجودًا، يرجى إنشاؤه (انتبه لأسماء الملفات ذات الأحرف غير ASCII!)
تنسيق ip2stress.txt هو كما يلي:
<IP> = <نص>
```text
127.0.0.1 = محلي
192.168.3.1 = الصف العاشر الفصل 1
192.168.3.2 = الصف العاشر الفصل 2
192.168.3.3 = الصف العاشر الفصل 3
192.168.3.4 = الصف العاشر الفصل 4
…………
```

## البناء

بناء المشروع باستخدام Maven:

```bash
mvn clean package
```
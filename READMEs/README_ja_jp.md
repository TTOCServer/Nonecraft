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

> **AI生成声明**: このプロジェクトは**部分的または完全にAIによって生成されました**。コードは通常の使用、参照、学習目的のみに使用され、**再度AIトレーニングに使用しないでください**！

- サーバーポートを記録した人々に衝撃を与えるための偽のMinecraftサーバー？
- 実際には、MOTDを表示し、拒否プロンプトを表示する小さなツールで、サーバーが情報を送信することを模倣しています。

## 機能特性

- Minecraft 1.20.1サーバーを装ったカスタムマルチポート
- (フォーマット済みコード) カスタムMOTDと拒否接続リクエストコード
- カスタムIPからテキストへの表示

## クイックスタート

### インストール

```bash
java -jar <ファイル名>
```
以上！

### ip2stress.txtの設定

通常、.jarは実行ディレクトリにip2stress.txtを自動生成します。ない場合は作成してください（非ASCII文字のファイル名に注意してください！）
ip2stress.txtの形式は次のとおりです：
<IP> = <テキスト>
```text
127.0.0.1 = ローカル
192.168.3.1 = 高校1年1組
192.168.3.2 = 高校1年2組
192.168.3.3 = 高校1年3組
192.168.3.4 = 高校1年4組
…………
```

## ビルド

Mavenを使用してプロジェクトをビルド：

```bash
mvn clean package
```
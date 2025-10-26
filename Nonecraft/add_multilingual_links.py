#!/usr/bin/env python3
"""
Script to add multilingual navigation links to all README files
"""

import os
import glob

# Multilingual navigation template
MULTILINGUAL_NAV = """

## 多语言支持 | Multilingual Support

- [English (US)](../READMEs/README_en_us.md)
- [English (UK)](../READMEs/README_en_gb.md)
- [English (Australia)](../READMEs/README_en_au.md)
- [日本語](../READMEs/README_ja_jp.md)
- [한국어](../READMEs/README_ko_kr.md)
- [Русский](../READMEs/README_ru_ru.md)
- [Deutsch](../READMEs/README_de_de.md)
- [Español](../READMEs/README_es_es.md)
- [Français](../READMEs/README_fr_fr.md)
- [Português](../READMEs/README_pt_pt.md)
- [العربية](../READMEs/README_ar_sa.md)
- [Қазақша](../READMEs/README_kk_kz.md)
- [Монгол](../READMEs/README_mn_mn.md)
- [한국어 (조선)](../READMEs/README_ko_kp.md)
- [繁體中文 (香港)](../READMEs/README_zh_hk.md)
- [繁體中文 (台灣)](../READMEs/README_zh_tw.md)
- [简体中文](../READMEs/README_zh_cn.md)
- [主README](../README.md)
"""

# Language-specific titles
LANGUAGE_TITLES = {
    'README_ja_jp.md': '\n## 多言語サポート\n',
    'README_ko_kr.md': '\n## 다국어 지원\n',
    'README_ru_ru.md': '\n## Многоязычная поддержка\n',
    'README_de_de.md': '\n## Mehrsprachige Unterstützung\n',
    'README_es_es.md': '\n## Soporte Multilingüe\n',
    'README_fr_fr.md': '\n## Support Multilingue\n',
    'README_pt_pt.md': '\n## Suporte Multilíngue\n',
    'README_ar_sa.md': '\n## الدعم متعدد اللغات\n',
    'README_kk_kz.md': '\n## Көптілді қолдау\n',
    'README_mn_mn.md': '\n## Олон хэлний дэмжлэг\n',
    'README_ko_kp.md': '\n## 다국어 지원\n',
    'README_zh_hk.md': '\n## 多語言支援\n',
    'README_zh_tw.md': '\n## 多語言支援\n',
    'README_en_au.md': '\n## Multilingual Support\n',
    'README_en_gb.md': '\n## Multilingual Support\n'
}

def add_multilingual_links():
    """Add multilingual navigation links to all README files"""
    readme_files = glob.glob('READMEs/*.md')
    
    for file_path in readme_files:
        filename = os.path.basename(file_path)
        
        # Skip files that already have multilingual support section
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            if '多语言支持' in content or 'Multilingual Support' in content or '多言語サポート' in content:
                print(f"Skipping {filename} - already has multilingual section")
                continue
        
        # Add multilingual section
        title = LANGUAGE_TITLES.get(filename, '\n## Multilingual Support\n')
        nav_content = title + '\n' + '\n'.join(MULTILINGUAL_NAV.split('\n')[2:])
        
        with open(file_path, 'a', encoding='utf-8') as f:
            f.write(nav_content)
        
        print(f"Added multilingual links to {filename}")

if __name__ == '__main__':
    add_multilingual_links()
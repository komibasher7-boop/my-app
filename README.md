# Minhag

مشروع Android Studio مولّد بواسطة HTML2APK Studio.

## البناء محلياً

1. افتح المجلد في Android Studio.
2. انتظر مزامنة Gradle.
3. شغّل `gradle :app:assembleDebug`.
4. ستجد ملف APK في `app/build/outputs/apk/debug/`.

## البناء عبر GitHub Actions

ادفع المشروع إلى مستودع GitHub وسيُبنى ملف APK تلقائياً عند كل دفع (انظر `.github/workflows/build-apk.yml`).

## حزمة AAB

شغّل مهمة bundleRelease من سير العمل نفسه — ستجد ملف AAB في أرتيفاكت app-release-aab.

تم تصميم بواسطه Rahamtallh.

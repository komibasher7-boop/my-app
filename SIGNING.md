# توقيع حزمة الإصدار

أنشئ keystore بالأمر:

keytool -genkeypair -v -keystore keystore.jks -alias upload -keyalg RSA -keysize 2048 -validity 10000

ثم ابنِ بالأمر: gradle :app:bundleRelease -PKEYSTORE_FILE=keystore.jks -PKEYSTORE_PASSWORD=YOUR_STORE_PASS -PKEY_ALIAS=YOUR_ALIAS -PKEY_PASSWORD=YOUR_KEY_PASS

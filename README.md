# mule_sftp_sample

##  コンパイル ＆ 実行可能war作成
` mvn clean package`

## 実行
1. 送信

```shell
java -jar target/test-1.0.0-SNAPSHOT.war '{"file":"flow.xml", "name":"receiving", "conf":{"USER_NAME":"username", "USER_PASSWORD":"password", "SFTP_HOST":"sftp", "SFTP_PORT":""}}'
```

2.  受信
```shell
java -jar target/test-1.0.0-SNAPSHOT.war '{"file":"flow.xml", "name":"receiving", "conf":{"USER_NAME":"username", "USER_PASSWORD":"password", "SFTP_HOST":"sftp", "SFTP_PORT":""}}'
```
3. 設定
   - **file** : muleファイル名
   - **name** : flowの名前
   - **conf** : 設定辞書
     - **USER_NAME** : ユーザ名  
       (例)"*receiving*" or "*sending*"

     - **USER_PASSWORD** : パスワード
     - **SFTP_HOST** : SFTPサーバ
     - **SFTP_PORT** : SFTPポート番号

## 設定
`sftp-settings.properties` を修正してください。

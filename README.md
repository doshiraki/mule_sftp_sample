# mule_sftp_sample

##  コンパイル ＆ 実行可能war作成
- ` mvn clean package` で `target/mule_sftp_sample-1.0.0-SNAPSHOT.war`を作成する。

## 実行
1. 送信

```shell
java -jar target/mule_sftp_sample-1.0.0-SNAPSHOT.war '{"file":"flow.xml", "name":"receiving", "conf":{"USER_NAME":"username", "USER_PASSWORD":"password", "SFTP_HOST":"sftp", "SFTP_PORT":""}}'
```

2.  受信
```shell
java -jar target/mule_sftp_sample-1.0.0-SNAPSHOT.war '{"file":"flow.xml", "name":"receiving", "conf":{"USER_NAME":"username", "USER_PASSWORD":"password", "SFTP_HOST":"sftp", "SFTP_PORT":""}}'
```
3. 設定
   1. 引数
      - **file** : muleファイル名  
        (例) [flow.xml](flow.xml)
      - **name** : flowの名前
          (例)"*receiving*" or "*sending*"
      - **conf** : 設定辞書
        - **USER_NAME** : ユーザ名     
        - **USER_PASSWORD** : パスワード
        - **SFTP_HOST** : SFTPサーバ
        - **SFTP_PORT** : SFTPポート番号
   2. mule用xml  
      (例) [flow.xml](flow.xml) はsftpの例

## 設定
`sftp-settings.properties` を修正してください。

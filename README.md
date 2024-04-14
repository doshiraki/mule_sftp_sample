# mule_sftp_sample

##  コンパイル ＆ 実行可能war作成
` mvn clean package`

## 実行
```shell
java -jar target/test-1.0.0-SNAPSHOT.war '{"file":"flow.xml", "name":"receiving", "conf":{"USER_NAME":"username", "USER_PASSWORD":"password", "SFTP_HOST":"sftp", "SFTP_PORT":""}}'
```

## 設定
`sftp-settings.properties` を修正してください。

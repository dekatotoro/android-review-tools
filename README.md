android-review-tools
====================


##▼事前準備
bin/setting.propertiesにGoogleアカウントを設定してください。

##▼使い方
シェルがあるフォルダまで移動して下記コマンドを実行してください。  
``` ./getReview.sh appId [limit]```
  
appId ストア（<https://play.google.com/store>）URLのパラメーターに指定されているid  
limit 取得件数（デフォルト10件）  
  
```sh ./getReview.sh xxxx 30 ```
  
※フルパスで実行したい場合は、各実行環境に合わせてbin、libsにパスを通してください
  


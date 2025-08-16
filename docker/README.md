# introduce

## sftp
使用 atmoz/sftp，帳號 testuser 密碼 password，對應本地資料夾 ./data。

## ftp
使用 stilliard/pure-ftpd，開放本地 2121。

## ftps
同樣基於 pure-ftpd，但啟用 TLS（需要憑證，掛載到 ./certs）。

- 使用 OpenSSL 生成測試憑證：
```bash
mkdir certs
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
-keyout certs/pure-ftpd.pem \
-out certs/pure-ftpd.pem
chmod 600 certs/pure-ftpd.pem
```

# use
- 啟動方式：
```bash
docker-compose up -d
```

## 測試：
```bash
# SFTP
sftp -P 2222 testuser@localhost

# FTP
ftp localhost 2121

# FTPS
lftp -u testuser,password ftps://localhost:2122
```

## 操作

### SFTP
- 上傳
```bash
# put [path/to/localfile.txt] [remotePath]
put localfile.txt upload
put C:\Users\Tommy\Downloads\sftp-file.txt upload
```
- 下載
```bash
# get [remotePath/filename] [path/to/localfile.txt]
get upload/remotefile.txt
get upload/sftp-file.txt ftp/download
```

# 使用金鑰在 sftp
## 生成金鑰
```bash
ssh-keygen -t rsa -b 4096 -m PEM -f .\.ssh\id_rsa_jsch
```

完成後，~/.ssh/id_rsa_jsch 是私鑰，~/.ssh/id_rsa_jsch.pub 是公鑰。
你可以用這組金鑰給 JSch 使用。

把金鑰搬去 docker/keys/id_rsa_jsch.pub 這是因為 docker-compose.yml 要讀取使用

### 連線測試
```bash
sftp -i C:\Users\Tommy\.ssh\id_rsa_jsch -P 2222 testuser@localhost
```

如果有出現以下錯誤，就到 "C:\Users\Tommy\.ssh\known_hosts" 刪除這檔案，然後再執行一次選擇 yes

```text
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@    WARNING: REMOTE HOST IDENTIFICATION HAS CHANGED!     @
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
IT IS POSSIBLE THAT SOMEONE IS DOING SOMETHING NASTY!
Someone could be eavesdropping on you right now (man-in-the-middle attack)!
It is also possible that a host key has just been changed.
The fingerprint for the ED25519 key sent by the remote host is
```

## 使用程式測試金鑰連線
```kotlin
var identity = IdentityInfo(File(System.getProperty("user.home") + "/.ssh/id_rsa_jsch"))
builder.setIdentityProvider(opts, identity)
```

如果有設定 金鑰 需要添加此段，讓 VFS 使用指定的私鑰驗證，不然預設會去抓 ~/.ssh/id_rsa
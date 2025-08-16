# introduce

> [docker-compose.yml](docker-compose.yml) 用來啟動 sftp、ftp、ftps 服務。

## sftp

介紹：sftp 是一種安全的檔案傳輸協定，基於 SSH 協定，提供加密的檔案傳輸功能。
使用 atmoz/sftp，帳號 testuser 密碼 password，對應本地資料夾 ./data。

## ftp

介紹：ftp 是一種傳統的檔案傳輸協定，提供基本的檔案上傳和下載功能。
主動模式：客戶端開啟一個隨機端口，伺服器連過來
被動模式：伺服器開啟一個隨機端口，客戶端連過來
使用 stilliard/pure-ftpd，開放本地 2121。

## ftps

介紹：ftps 是 FTP 的安全版本，使用 TLS 加密傳輸。
同樣基於 pure-ftpd，但啟用 TLS（需要憑證，掛載到 ./certs）。

---

# sftp

## use

- 啟動方式：

```bash
docker-compose up -d
```

## 操作

### 連線

```bash
sftp -P 2222 testuser@localhost
```

### 離開 sftp

```bash
exit
````

### upload

```bash
# put [path/to/localfile.txt] [remotePath]
put localfile.txt upload
put C:\Users\Tommy\Downloads\sftp-file.txt upload
```

### download

```bash
# get [remotePath/filename] [path/to/localfile.txt]
get upload/remotefile.txt
get upload/sftp-file.txt ftp/download
```

## 使用金鑰在 sftp

### 生成金鑰

```bash
ssh-keygen -t rsa -b 4096 -m PEM -f .\.ssh\id_rsa_jsch
```

完成後，~/.ssh/id_rsa_jsch 是私鑰，~/.ssh/id_rsa_jsch.pub 是公鑰。
你可以用這組金鑰給 JSch 使用
把金鑰搬去 docker/keys/id_rsa_jsch.pub 這是因為 docker-compose.yml 要讀取使用

### 連線(使用金鑰)

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

---

# ftp

## use

- 啟動方式：

```bash
docker-compose up -d
```

## 操作

> 由於 windows 內建有 ftp 指令，所以可能會有衝突，要用下面的方式做登入，不能直接輸入 `ftp localhost 2121`

### 連線

```bash
PS C:\Users\Tommy\IdeaProjects\ftp-repo\docker> ftp localhost
> ftp: connect :拒絕連線
ftp> open localhost 2121
已連線到 LAPTOP-GGD0HT4V。
220---------- Welcome to Pure-FTPd [privsep] [TLS] ----------
220-You are user number 1 of 5 allowed.
220-Local time is now 08:08. Server port: 21.
220-This is a private system - No anonymous login
220 You will be disconnected after 15 minutes of inactivity.
200 OK, UTF-8 enabled
使用者 (LAPTOP-GGD0HT4V:(none)): testuser
331 User testuser OK. Password required
密碼:

230 OK. Current directory is /
ftp> 
```

### upload

> 由於 Windows 內建的 ftp 工具僅支援主動模式，而 Docker 服務採用被動模式，導致無法正常上傳檔案
> 建議使用 FileZilla 或 gitbash 等支援被動模式的工具進行檔案傳輸。

```bash
# put [path/to/localfile.txt] [remotePath]
```

### download

> 由於 Windows 內建的 ftp 工具僅支援主動模式，而 Docker 服務採用被動模式，導致無法正常上傳檔案
> 建議使用 FileZilla 或 gitbash 等支援被動模式的工具進行檔案傳輸。

```bash
# get [remotePath/filename] [path/to/localfile.txt]
```

### 離開 ftp

```bash
bye
````

### 顯示本地目錄

```bash
lcd
````
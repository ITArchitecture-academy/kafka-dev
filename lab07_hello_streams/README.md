# Install Faust on MacOS

* `python-rocksdb` does not work with latest `rocksdb`
* Need to install `rocksdb@6.29.*`

# Install old Rocksdb

```shell
brew tap-new $USER/local-rocksdb
brew extract --version=6.29.3 rocksdb $USER/local-rocksdb
brew install rocksdb@6.29.3
```

# Export Buildflags

```shell
export CPPFLAGS=-I/usr/local/Cellar/rocksdb@6.29.3/6.29.3/include
export LDFLAGS="-L/usr/local/Cellar/rocksdb@6.29.3/6.29.3/lib -Lusr/local/Cellar/snappy/1.1.9/lib -L/usr/local/Cellar/lz4/1.9.4/lib"
```

# Install packages

```shell
poetry install
```
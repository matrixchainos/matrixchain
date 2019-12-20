package org.matrixchain.db;

import org.rocksdb.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DBSourceImpl implements DBSource<byte[]> {

    String name;
    RocksDB db;
    ReadOptions readOpts;

    private ReadWriteLock resetDbLock = new ReentrantReadWriteLock();

    static {
        RocksDB.loadLibrary();
    }

    public DBSourceImpl() {
    }

    public DBSourceImpl(String name) {
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void init() {
        resetDbLock.writeLock().lock();

        if (name == null) throw new NullPointerException("no name set to the db");

        try {
            Options options = new Options();

            options.setCreateIfMissing(true);
            options.setCreateIfMissing(true);
            options.setCompressionType(CompressionType.LZ4_COMPRESSION);
            options.setBottommostCompressionType(CompressionType.ZSTD_COMPRESSION);
            options.setLevelCompactionDynamicLevelBytes(true);
            options.setMaxOpenFiles(32);
            options.setIncreaseParallelism(1);

            // key prefix for state node lookups
            options.useFixedLengthPrefixExtractor(16);

            // table options
            final BlockBasedTableConfig tableCfg;
            options.setTableFormatConfig(tableCfg = new BlockBasedTableConfig());
            tableCfg.setBlockSize(16 * 1024);
            tableCfg.setBlockCacheSize(32 * 1024 * 1024);
            tableCfg.setCacheIndexAndFilterBlocks(true);
            tableCfg.setPinL0FilterAndIndexBlocksInCache(true);
            tableCfg.setFilter(new BloomFilter(10, false));

            readOpts = new ReadOptions();
            readOpts = readOpts.setPrefixSameAsStart(true).setVerifyChecksums(false);

            final Path dbPath = getPath();
            if (!Files.isSymbolicLink(dbPath.getParent())) Files.createDirectories(dbPath.getParent());

            if (backupPath().toFile().canWrite()) {
                try {
                    BackupableDBOptions backupOptions = new BackupableDBOptions(backupPath().toString());
                    RestoreOptions restoreOptions = new RestoreOptions(false);
                    BackupEngine backupEngine = null;

                    backupEngine = BackupEngine.open(Env.getDefault(), backupOptions);

                    if (!backupEngine.getBackupInfo().isEmpty()) {
                        backupEngine.restoreDbFromLatestBackup(getPath().toString(), getPath().toString(), restoreOptions);
                    }
                } catch (RocksDBException e) {
                    e.printStackTrace();
                }
            }
            try {
                db = RocksDB.open(options, dbPath.toString());
            } catch (RocksDBException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            resetDbLock.writeLock().unlock();
        }
    }

    private Path getPath() {
        return Paths.get("matrixchain", name);
    }

    private Path backupPath() {
        return Paths.get("matrixchain", "backup", name);
    }

    @Override
    public void close() {
        resetDbLock.writeLock().lock();
        try {
            db.close();
            readOpts.close();
        } catch (Exception e) {

        } finally {
            resetDbLock.writeLock().lock();
        }

    }

    @Override
    public Set<byte[]> keys() throws RuntimeException {
        resetDbLock.readLock().lock();
        try {
            RocksIterator iterator = db.newIterator();
            Set<byte[]> result = new HashSet<>();
            for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                result.add(iterator.key());
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resetDbLock.readLock().unlock();
        }
    }

    @Override
    public void reset() {
        close();

        init();
    }

    @Override
    public void put(byte[] key, byte[] val) {
        resetDbLock.readLock().lock();
        try {
            if (val != null) {
                db.put(key, val);
            } else {
                db.delete(key);
            }
        } catch (RocksDBException e) {
            e.printStackTrace();
        } finally {
            resetDbLock.readLock().unlock();
        }
    }

    @Override
    public byte[] get(byte[] key) {
        resetDbLock.readLock().lock();
        try {
            byte[] value = db.get(readOpts, key);
            return value;
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        } finally {
            resetDbLock.readLock().unlock();
        }
    }

    @Override
    public void delete(byte[] key) {
        resetDbLock.readLock().lock();
        try {
            db.delete(key);
        } catch (RocksDBException e) {
            e.printStackTrace();
        } finally {
            resetDbLock.readLock().unlock();
        }
    }

    @Override
    public boolean flush() {
        return false;
    }
}

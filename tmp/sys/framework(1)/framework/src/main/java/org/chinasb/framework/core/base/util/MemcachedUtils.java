package org.chinasb.framework.core.base.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientStateListener;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.buffer.BufferAllocator;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.ReconnectRequest;
import net.rubyeye.xmemcached.networking.Connector;
import net.rubyeye.xmemcached.transcoders.Transcoder;
import net.rubyeye.xmemcached.utils.Protocol;

/**
 * @author ethan
 */
public class MemcachedUtils {

    private MemcachedClient memcachedClient;

    private static MemcachedUtils memcachedUtils;

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void init() {
        memcachedUtils = this;
        memcachedUtils.memcachedClient = this.memcachedClient;
    }

    public static void setMergeFactor(int mergeFactor) {
        memcachedUtils.memcachedClient.setMergeFactor(mergeFactor);
    }

    public static String getName() {
        return memcachedUtils.memcachedClient.getName();
    }

    public static void setName(String name) {
        memcachedUtils.memcachedClient.setName(name);
    }

    public static long getConnectTimeout() {
        return memcachedUtils.memcachedClient.getConnectTimeout();
    }

    public static void setConnectTimeout(long connectTimeout) {
        memcachedUtils.memcachedClient.setConnectTimeout(connectTimeout);
    }

    public static void setEnableHeartBeat(boolean enableHeartBeat) {
        memcachedUtils.memcachedClient.setEnableHeartBeat(enableHeartBeat);
    }

    public static long getOpTimeout() {
        return memcachedUtils.memcachedClient.getOpTimeout();
    }

    public static void setOpTimeout(long opTimeout) {
        memcachedUtils.memcachedClient.setOpTimeout(opTimeout);
    }

    public static void setHealSessionInterval(long healConnectionInterval) {
        memcachedUtils.memcachedClient.setHealSessionInterval(healConnectionInterval);
    }

    public static long getHealSessionInterval() {
        return memcachedUtils.memcachedClient.getHealSessionInterval();
    }

    public static Map<InetSocketAddress, AuthInfo> getAuthInfoMap() {
        return memcachedUtils.memcachedClient.getAuthInfoMap();
    }

    public static void setAuthInfoMap(Map<InetSocketAddress, AuthInfo> map) {
        memcachedUtils.memcachedClient.setAuthInfoMap(map);
    }

    public static Connector getConnector() {
        return memcachedUtils.memcachedClient.getConnector();
    }

    public static void setOptimizeMergeBuffer(boolean optimizeMergeBuffer) {
        memcachedUtils.memcachedClient.setOptimizeMergeBuffer(optimizeMergeBuffer);
    }

    public static boolean isShutdown() {
        return memcachedUtils.memcachedClient.isShutdown();
    }

    public static void addServer(String server, int port) throws IOException {
        memcachedUtils.memcachedClient.addServer(server, port);
    }

    public static void addServer(String server, int port, int weight) throws IOException {
        memcachedUtils.memcachedClient.addServer(server, port, weight);
    }

    public static void addServer(InetSocketAddress inetSocketAddress) throws IOException {
        memcachedUtils.memcachedClient.addServer(inetSocketAddress);
    }

    public static void addServer(InetSocketAddress inetSocketAddress, int weight) throws IOException {
        memcachedUtils.memcachedClient.addServer(inetSocketAddress, weight);
    }

    public static void addServer(String hostList) throws IOException {
        memcachedUtils.memcachedClient.addServer(hostList);
    }

    public static List<String> getServersDescription() {
        return memcachedUtils.memcachedClient.getServersDescription();
    }

    public static void removeServer(String hostList) {
        memcachedUtils.memcachedClient.removeServer(hostList);
    }

    public static void setOptimizeGet(boolean optimizeGet) {
        memcachedUtils.memcachedClient.setOptimizeGet(optimizeGet);

    }

    public static void setBufferAllocator(BufferAllocator bufferAllocator) {
        memcachedUtils.memcachedClient.setBufferAllocator(bufferAllocator);
    }

    public static <T> T get(String key, long timeout, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.get(key, timeout, transcoder);
    }

    public static <T> T get(String key, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return (T) memcachedUtils.memcachedClient.get(key, timeout);
    }

    public static <T> T get(String key, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.get(key, transcoder);
    }

    public static <T> T get(String key) throws TimeoutException, InterruptedException, MemcachedException {
        return (T) memcachedUtils.memcachedClient.get(key);
    }

    public static <T> GetsResponse<T> gets(String key, long timeout, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(key, timeout, transcoder);
    }

    public static <T> GetsResponse<T> gets(String key) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(key);
    }

    public static <T> GetsResponse<T> gets(String key, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(key, timeout);
    }

    public static <T> GetsResponse<T> gets(String key, Transcoder transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(key, transcoder);
    }

    public static <T> Map<String, T> get(Collection<String> keyCollections) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.get(keyCollections);
    }

    public static <T> Map<String, T> get(Collection<String> keyCollections, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.get(keyCollections, timeout);
    }

    public static <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(keyCollections);
    }

    public static <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(keyCollections, timeout);
    }

    public static <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.set(key, exp, value, transcoder, timeout);
    }

    public static void setWithNoReply(String key, int exp, Object value) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.setWithNoReply(key, exp, value);
    }

    public static <T> void setWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.setWithNoReply(key, exp, value, transcoder);
    }

    public static boolean set(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.set(key, exp, value);
    }

    public static boolean set(String key, int exp, Object value, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.set(key, exp, value, timeout);
    }

    public static <T> boolean set(String key, int exp, T value, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.set(key, exp, value, transcoder);
    }

    public static <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.add(key, exp, value, transcoder, timeout);
    }

    public static boolean add(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.add(key, exp, value);
    }

    public static boolean add(String key, int exp, Object value, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.add(key, exp, value, timeout);
    }

    public static <T> boolean add(String key, int exp, T value, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.add(key, exp, value, transcoder);
    }

    public static void addWithNoReply(String key, int exp, Object value) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.addWithNoReply(key, exp, value);
    }

    public static <T> void addWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.addWithNoReply(key, exp, value, transcoder);
    }

    public static void replaceWithNoReply(String key, int exp, Object value) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.replaceWithNoReply(key, exp, value);
    }

    public static <T> void replaceWithNoReply(String key, int exp, T value, Transcoder<T> transcoder) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.replaceWithNoReply(key, exp, value, transcoder);
    }

    public static <T> boolean replace(String key, int exp, T value, Transcoder<T> transcoder, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.replace(key, exp, value, transcoder, timeout);
    }

    public static boolean replace(String key, int exp, Object value) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.replace(key, exp, value);
    }

    public static boolean replace(String key, int exp, Object value, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.replace(key, exp, value);
    }

    public static <T> boolean replace(String key, int exp, T value, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.replace(key, exp, value, transcoder);
    }

    public static boolean append(String key, Object value) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.append(key, value);
    }

    public static boolean append(String key, Object value, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.append(key, value, timeout);
    }

    public static void appendWithNoReply(String key, Object value) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.appendWithNoReply(key, value);
    }

    public static boolean prepend(String key, Object value) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.prepend(key, value);
    }

    public static boolean prepend(String key, Object value, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.prepend(key, value, timeout);
    }

    public static void prependWithNoReply(String key, Object value) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.prependWithNoReply(key, value);
    }

    public static boolean cas(String key, int exp, Object value, long cas) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, value, cas);
    }

    public static <T> boolean cas(String key, int exp, T value, Transcoder<T> transcoder, long timeout, long cas) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, value, transcoder, timeout, cas);
    }

    public static boolean cas(String key, int exp, Object value, long timeout, long cas) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, value, timeout, cas);
    }

    public static <T> boolean cas(String key, int exp, T value, Transcoder<T> transcoder, long cas) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, value, transcoder, cas);
    }

    public static <T> boolean cas(String key, int exp, CASOperation<T> operation, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, operation, transcoder);
    }

    public static <T> boolean cas(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, getsReponse, operation, transcoder);
    }

    public static <T> boolean cas(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, getsReponse, operation);
    }

    public static <T> void casWithNoReply(String key, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.casWithNoReply(key, operation);
    }

    public static <T> void casWithNoReply(String key, GetsResponse<T> getsResponse, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.casWithNoReply(key, getsResponse, operation);
    }

    public static <T> void casWithNoReply(String key, int exp, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.casWithNoReply(key, exp, operation);
    }

    public static <T> void casWithNoReply(String key, int exp, GetsResponse<T> getsReponse, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.cas(key, exp, getsReponse, operation);
    }

    public static <T> boolean cas(String key, GetsResponse<T> getsReponse, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, getsReponse, operation);
    }

    public static <T> boolean cas(String key, int exp, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, exp, operation);
    }

    public static <T> boolean cas(String key, CASOperation<T> operation) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.cas(key, operation);
    }

    public static boolean delete(String key, int time) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.delete(key, time);
    }

    public static boolean delete(String key, long opTimeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.delete(key, opTimeout);
    }

    public static void deleteWithNoReply(String key, int time) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.deleteWithNoReply(key, time);
    }

    public static void deleteWithNoReply(String key) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.deleteWithNoReply(key);
    }

    public static boolean touch(String key, int exp, long opTimeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.touch(key, exp, opTimeout);
    }

    public static boolean touch(String key, int exp) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.touch(key, exp);
    }

    public static <T> T getAndTouch(String key, int newExp, long opTimeout) throws TimeoutException, InterruptedException, MemcachedException {
        return (T) memcachedUtils.memcachedClient.getAndTouch(key, newExp, opTimeout);
    }

    public static <T> T getAndTouch(String key, int newExp) throws TimeoutException, InterruptedException, MemcachedException {
        return (T) memcachedUtils.memcachedClient.getAndTouch(key, newExp);
    }

    public static long incr(String key, long delta) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.incr(key, delta);
    }

    public static long incr(String key, long delta, long initValue) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.incr(key, delta, initValue);
    }

    public static long incr(String key, long delta, long initValue, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.incr(key, delta, initValue, timeout);
    }

    public static long incr(String key, long delta, long initValue, long timeout, int exp) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.incr(key, delta, initValue, timeout, exp);
    }

    public static void incrWithNoReply(String key, long delta) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.incrWithNoReply(key, delta);
    }

    public static void decrWithNoReply(String key, long delta) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.decrWithNoReply(key, delta);
    }

    public static long decr(String key, long delta) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.decr(key, delta);
    }

    public static long decr(String key, long delta, long initValue) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.decr(key, delta, initValue);
    }

    public static long decr(String key, long delta, long initValue, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.decr(key, delta, initValue, timeout);
    }

    public static long decr(String key, long delta, long initValue, long timeout, int exp) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.decr(key, delta, initValue, timeout, exp);
    }

    public static void flushAll() throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.flushAll();
    }

    public static void flushAllWithNoReply() throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.flushAllWithNoReply();
    }

    public static void flushAllWithNoReply(int exptime) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.flushAllWithNoReply(exptime);
    }

    public static void flushAllWithNoReply(InetSocketAddress address) throws MemcachedException, InterruptedException {
        memcachedUtils.memcachedClient.flushAllWithNoReply(address);
    }

    public static void flushAllWithNoReply(InetSocketAddress address, int exptime) throws MemcachedException, InterruptedException {
        memcachedUtils.memcachedClient.flushAllWithNoReply(address, exptime);
    }

    public static void flushAll(int exptime, long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.flushAll(exptime, timeout);
    }

    public static void flushAll(long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.flushAll(timeout);
    }

    public static void setLoggingLevelVerbosity(InetSocketAddress address, int level) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.setLoggingLevelVerbosity(address, level);
    }

    public static void setLoggingLevelVerbosityWithNoReply(InetSocketAddress address, int level) throws InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.setLoggingLevelVerbosityWithNoReply(address, level);
    }

    public static void flushAll(InetSocketAddress address) throws MemcachedException, InterruptedException, TimeoutException {
        memcachedUtils.memcachedClient.flushAll(address);
    }

    public static void flushAll(InetSocketAddress address, long timeout) throws MemcachedException, InterruptedException, TimeoutException {
        memcachedUtils.memcachedClient.flushAll(address, timeout);
    }

    public static void flushAll(InetSocketAddress address, long timeout, int exptime) throws MemcachedException, InterruptedException, TimeoutException {
        memcachedUtils.memcachedClient.flushAll(address, timeout, exptime);
    }

    public static void flushAll(String host) throws TimeoutException, InterruptedException, MemcachedException {
        memcachedUtils.memcachedClient.flushAll(host);
    }

    public static Map<String, String> stats(InetSocketAddress address) throws MemcachedException, InterruptedException, TimeoutException {
        return memcachedUtils.memcachedClient.stats(address);
    }

    public static Map<String, String> stats(InetSocketAddress address, long timeout) throws MemcachedException, InterruptedException, TimeoutException {
        return memcachedUtils.memcachedClient.stats(address, timeout);
    }

    public static Map<InetSocketAddress, Map<String, String>> getStats() throws MemcachedException, InterruptedException, TimeoutException {
        return memcachedUtils.memcachedClient.getStats();
    }

    public static Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName) throws MemcachedException, InterruptedException, TimeoutException {
        return memcachedUtils.memcachedClient.getStatsByItem(itemName);
    }

    public static Map<InetSocketAddress, Map<String, String>> getStatsByItem(String itemName, long timeout) throws MemcachedException, InterruptedException, TimeoutException {
        return memcachedUtils.memcachedClient.getStatsByItem(itemName, timeout);
    }

    public static Map<InetSocketAddress, String> getVersions() throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.getVersions();
    }

    public static Map<InetSocketAddress, String> getVersions(long timeout) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.getVersions(timeout);
    }

    public static Map<InetSocketAddress, Map<String, String>> getStats(long timeout) throws MemcachedException, InterruptedException, TimeoutException {
        return memcachedUtils.memcachedClient.getStats(timeout);
    }

    public static void shutdown() throws IOException {
        memcachedUtils.memcachedClient.shutdown();
    }

    public static void setConnectionPoolSize(int poolSize) {
        memcachedUtils.memcachedClient.setConnectionPoolSize(poolSize);
    }

    public static boolean delete(String key) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.delete(key);
    }

    public static Transcoder getTranscoder() {
        return memcachedUtils.memcachedClient.getTranscoder();
    }

    public static void setTranscoder(Transcoder transcoder) {
        memcachedUtils.memcachedClient.setTranscoder(transcoder);
    }

    public static Collection<InetSocketAddress> getAvaliableServers() {
        return memcachedUtils.memcachedClient.getAvaliableServers();
    }

    public static void addStateListener(MemcachedClientStateListener listener) {
        memcachedUtils.memcachedClient.addStateListener(listener);
    }

    public static Collection<MemcachedClientStateListener> getStateListeners() {
        return memcachedUtils.memcachedClient.getStateListeners();
    }

    public static void setPrimitiveAsString(boolean primitiveAsString) {
        memcachedUtils.memcachedClient.setPrimitiveAsString(primitiveAsString);
    }

    public static void removeStateListener(MemcachedClientStateListener listener) {
        memcachedUtils.memcachedClient.removeStateListener(listener);
    }

    public static Protocol getProtocol() {
        return memcachedUtils.memcachedClient.getProtocol();
    }

    public static boolean isSanitizeKeys() {
        return memcachedUtils.memcachedClient.isSanitizeKeys();
    }

    public static void setSanitizeKeys(boolean sanitizeKeys) {
        memcachedUtils.memcachedClient.setSanitizeKeys(sanitizeKeys);
    }

    public static Counter getCounter(String key, long initialValue) {
        return memcachedUtils.memcachedClient.getCounter(key, initialValue);
    }

    public static Counter getCounter(String key) {
        return memcachedUtils.memcachedClient.getCounter(key);
    }

    /** @deprecated */
    public static KeyIterator getKeyIterator(InetSocketAddress address) throws MemcachedException, TimeoutException, InterruptedException {
        return memcachedUtils.memcachedClient.getKeyIterator(address);
    }

    public static void setFailureMode(boolean failureMode) {
        memcachedUtils.memcachedClient.setFailureMode(failureMode);
    }

    public static boolean isFailureMode() {
        return memcachedUtils.memcachedClient.isFailureMode();
    }

    public static Queue<ReconnectRequest> getReconnectRequestQueue() {
        return memcachedUtils.memcachedClient.getReconnectRequestQueue();
    }

    public static <T> Map<String, T> get(Collection<String> keyCollections, long timeout, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.get(keyCollections, timeout, transcoder);
    }

    public static <T> Map<String, T> get(Collection<String> keyCollections, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.get(keyCollections, transcoder);
    }

    public static <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, long timeout, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(keyCollections, timeout, transcoder);
    }

    public static <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, Transcoder<T> transcoder) throws TimeoutException, InterruptedException, MemcachedException {
        return memcachedUtils.memcachedClient.gets(keyCollections, transcoder);
    }
}

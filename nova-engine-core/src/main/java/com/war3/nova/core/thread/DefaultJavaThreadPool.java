package com.war3.nova.core.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.war3.nova.Result;

/**
 * 默认的java线程池
 * 
 * @author Cytus_
 * @since 2018年12月18日 上午9:00:02
 * @version 1.0
 */
public class DefaultJavaThreadPool implements ThreadPool {
	
	private String threadPoolName = "default";
	
	private int corePoolSize = DEF_CORE_POOL_SIZE;
	
	private int maxPoolSize = DEF_MAX_POOL_SIZE;
	
	private int queueSize = DEF_WAIT_QUEUE_SIZE;
	
	private ExecutorService executorService;
	
	private BlockingQueue<Runnable> waitQueue;

	@Override
	public void start() {
		try {
			waitQueue = new LinkedBlockingQueue<>(this.queueSize);
			this.executorService = new ThreadPoolExecutor(this.corePoolSize, this.maxPoolSize, 200, TimeUnit.MILLISECONDS, waitQueue);
		} catch (Exception e) {
			LoggerFactory.getLogger(DefaultJavaThreadPool.class).error(e.getMessage(), e);
		}
	}

	@Override
	public void stop() {
		destroy();
	}

	@Override
	public Future<Result<?>> add(Callable<Result<?>> task) throws Exception {
		assertExecutor();
		return executorService.submit(task);
	}

	@Override
	public Collection<Future<Result<?>>> add(Collection<Callable<Result<?>>> tasks) throws Exception {
		assertExecutor();
		if (Objects.nonNull(tasks)) {
			Collection<Future<Result<?>>> results = new ArrayList<>(tasks.size());
			for (Callable<Result<?>> task : tasks) {
				results.add(executorService.submit(task));
			}
			return results;
		}
		return null;
	}

	@Override
	public void destroy() {
		if (Objects.nonNull(executorService)) {
			executorService.shutdown();
			this.waitQueue.clear();
		}
	}

	@Override
	public int getCurrWaitTask() {
		return waitQueue.size();
	}

	@Override
	public String getTreadPoolName() {
		return this.threadPoolName;
	}

	@Override
	public boolean addRunnable(Runnable task) throws Exception {
		assertExecutor();
		executorService.execute(task);
		return true;
	}

	@Override
	public boolean addRunnables(Collection<Runnable> tasks) throws Exception {
		assertExecutor();
		if (Objects.nonNull(tasks)) {
			for (Runnable task : tasks) {
				executorService.execute(task);
			}
		}
		return true;
	}
	
	public void assertExecutor() throws Exception {
		if (Objects.isNull(this.executorService))
			throw new NullPointerException("线程池尚未初始化!");
	}

	public String getThreadPoolName() {
		return threadPoolName;
	}

	public void setThreadPoolName(String threadPoolName) {
		this.threadPoolName = threadPoolName;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public BlockingQueue<Runnable> getWaitQueue() {
		return waitQueue;
	}

	public void setWaitQueue(BlockingQueue<Runnable> waitQueue) {
		this.waitQueue = waitQueue;
	}

}

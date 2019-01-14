package com.war3.nova.support.springcloud.config.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="nova.support.springcloud")
public class SpringCloudSupportProperties {
    
    private final Http http = new Http();
    
    public Http getHttp() {
        return http;
    }

    public class Http {
        
        private String contentType;
        
        private String charset;
        
        private final Pool pool = new Pool();
        
        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public Pool getPool() {
            return pool;
        }

        public class Pool {
            
            private int maxTotal;
            
            private int defaultMaxPreRoute;
            
            private int connectTimeout;
            
            private int connectionRequestTimeout;
            
            private int socketTimeout;
            
            private int validateAfterInactivity;

            public int getValidateAfterInactivity() {
                return validateAfterInactivity;
            }

            public void setValidateAfterInactivity(int validateAfterInactivity) {
                this.validateAfterInactivity = validateAfterInactivity;
            }

            public int getMaxTotal() {
                return maxTotal;
            }

            public void setMaxTotal(int maxTotal) {
                this.maxTotal = maxTotal;
            }

            public int getDefaultMaxPreRoute() {
                return defaultMaxPreRoute;
            }

            public void setDefaultMaxPreRoute(int defaultMaxPreRoute) {
                this.defaultMaxPreRoute = defaultMaxPreRoute;
            }

            public int getConnectTimeout() {
                return connectTimeout;
            }

            public void setConnectTimeout(int connectTimeout) {
                this.connectTimeout = connectTimeout;
            }

            public int getConnectionRequestTimeout() {
                return connectionRequestTimeout;
            }

            public void setConnectionRequestTimeout(int connectionRequestTimeout) {
                this.connectionRequestTimeout = connectionRequestTimeout;
            }

            public int getSocketTimeout() {
                return socketTimeout;
            }

            public void setSocketTimeout(int socketTimeout) {
                this.socketTimeout = socketTimeout;
            }
            
        }
        
    }
    
    private Map<String, Thread> thread;
    
    public Map<String, Thread> getThread() {
        return thread;
    }

    public void setThread(Map<String, Thread> thread) {
        this.thread = thread;
    }
    
    public Thread getThread(String key) {
        return thread.get(key);
    }

    public static class Thread {
        
        private String threadPoolName;
        
        private int corePoolSize;
        
        private int maxPoolSize;
        
        private int waitQueueSize;

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

        public int getWaitQueueSize() {
            return waitQueueSize;
        }

        public void setWaitQueueSize(int waitQueueSize) {
            this.waitQueueSize = waitQueueSize;
        }
        
    }

}

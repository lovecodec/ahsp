package com.crawler.proxy;

/**
 * @author：Dr.chen
 * @date：2019/8/22 14:05
 * @Description：线程操作资源类->资源类即为代理数据，该爬虫只进行校验代理是否有用，不获取代理
 */
public class ProxyPool {
//    public static void main(String[] args) {
//        //给线程资源中添加数据
//        ProxySource proxySource = new ProxySource();
//        proxySource.addProxy();
//        //创建任务
//        ProxyCheckTask task = new ProxyCheckTask(proxySource);
//
////        for (int i = 0; i < 10; i++) {
////            new Thread(() -> {
////                while (!proxySource.isEmpty()) {
////                    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
////                        RequestConfig config = RequestConfig.custom().setProxy(new HttpHost("", 1)).build();
////                        HttpGet httpGet = new HttpGet("https://www.baidu.com");
////                        httpGet.setConfig(config);
////                        CloseableHttpResponse response = httpClient.execute(httpGet);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }, "校验线程" + i).start();
////        }
//
//        //创建线程池,固定大小为10
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//        for (int i = 0; i < 10; i++) {
//            pool.submit(task);
//        }
//
//        pool.shutdown();
//    }
}


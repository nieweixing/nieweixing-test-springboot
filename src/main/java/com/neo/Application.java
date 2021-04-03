package com.neo;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

@SpringBootApplication
public class Application {

	public static final PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);


	static {
		// 添加 Prometheus 全局 Label，建议加一上对应的应用名
		registry.config().commonTags("application", "java-demo");
	}

	public static void main(String[] args) throws Exception {
		// 添加 JVM 监控
		new ClassLoaderMetrics().bindTo(registry);
		new JvmMemoryMetrics().bindTo(registry);
		new JvmGcMetrics().bindTo(registry);
		new ProcessorMetrics().bindTo(registry);
		new JvmThreadMetrics().bindTo(registry);
		new UptimeMetrics().bindTo(registry);
		new FileDescriptorMetrics().bindTo(registry);
		System.gc(); // Test GC
		try {
			// 暴露 Prometheus HTTP 服务，如果已经有，可以使用已有的 HTTP Server
			HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
			server.createContext("/metrics", httpExchange -> {
				String response = registry.scrape();
				httpExchange.sendResponseHeaders(200, response.getBytes().length);
				try (OutputStream os = httpExchange.getResponseBody()) {
					os.write(response.getBytes());
				}
			});
			new Thread(server::start).start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		SpringApplication.run(Application.class, args);
	}
}

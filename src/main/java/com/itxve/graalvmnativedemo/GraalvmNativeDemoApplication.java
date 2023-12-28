package com.itxve.graalvmnativedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
@RestController
@Slf4j
public class GraalvmNativeDemoApplication {

	@RequestMapping("/now")
	String hello() {
		return "Hello,Now :" + new Date();
	}

	@GetMapping(value = "/")
	public String index() {
		StringBuilder evns = new StringBuilder();
		System.getProperties().forEach((key, value) -> {
			evns.append(key + "=" + value + "\n");
		});
		return evns.toString();
	}

	@GetMapping(value = "/dir")
	public List<String> dir() {
		String userDir = System.getProperty("user.dir");
		try {
			Stream<Path> list = Files.list(Path.of(userDir));
			return list.map(p -> p.toString()).collect(Collectors.toList());

		} catch (IOException e) {
			return Collections.EMPTY_LIST;
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(GraalvmNativeDemoApplication.class, args);
	}

}

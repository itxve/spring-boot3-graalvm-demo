package com.itxve.graalvmnativedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
@Slf4j
public class GraalvmNativeDemoApplication {

	@RequestMapping("/")
	String hello() {
		return "Hello,Now :" + new Date();
	}

		@RequestMapping("/dir/list")
	List<Path> home(ServerWebExchange exchange) {
		String path = exchange.getRequest().getQueryParams().getFirst("path");
		try {
			Stream<Path> list = Files.list(Path.of(path));
			return list.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println(e);
			return Collections.emptyList();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(GraalvmNativeDemoApplication.class, args);
	}

}

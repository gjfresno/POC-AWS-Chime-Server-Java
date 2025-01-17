package ar.com.medife.app.api.controller;

import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface GetCallController {

	public Map<String, Object> createVideoCall(@RequestParam String room, @RequestParam String user);
	public Map<String, String> deleteVideoCall(@RequestParam String room, @RequestParam String user);

}
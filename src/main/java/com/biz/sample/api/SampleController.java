package com.biz.sample.api;

import com.framwork.core.common.message.CommonMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Sample API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sample")
public class SampleController {

    private final CommonMessageService commonMessageService;

    @Operation(summary = "Receive VO payload")
    @PostMapping("/vo")
    public Map<String, Object> receiveVo(@Valid @RequestBody SampleRequestVo requestVo) {
        return Map.of("type", "VO", "data", requestVo);
    }

    @Operation(summary = "Receive Record payload")
    @PostMapping("/record")
    public Map<String, Object> receiveRecord(@Valid @RequestBody SampleRequestRecord requestRecord) {
        return Map.of("type", "RECORD", "data", requestRecord);
    }

    @Operation(summary = "Check interceptor logging")
    @GetMapping("/wrong")
    public Map<String, String> wrongCase(@RequestParam String name) {
        return Map.of(
                "message", commonMessageService.getMessage("NOTIFY-0001"),
                "name", name
        );
    }
}

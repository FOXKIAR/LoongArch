package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.util.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/directory")
public class DirectoryController {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileInfo {
        private String name;
        private Boolean isDirectory;
        private Long size;
    }

    // 先使用 wsl 驱动器测试
    @GetMapping
    public ResponseEntity<Result<List<FileInfo>>> directory(String path) {
        File[] files = new File("//wsl.localhost/kali-linux/" + path).listFiles();
        if (files == null)
            return ResponseEntity.noContent().build();
        List<FileInfo> list = Arrays.stream(files).map(file -> {
            FileInfo result = new FileInfo();
            result.setName(file.getName());
            result.setIsDirectory(file.isDirectory());
            result.setSize(file.isDirectory() ? null : file.length());
            return result;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(Result.success(list));
    }
}
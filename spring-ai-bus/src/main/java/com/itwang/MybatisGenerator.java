package com.itwang;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {
    public static void main(String[] args) throws Exception {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/candy_ai", "root", "123456")
                .globalConfig(builder -> builder
                        .author("lmz")
                        .disableOpenDir()
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/spring-ai-bus/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.itwang")
                        .entity("dao.entity")
                        .mapper("dao.mapper")
                        .controller("controller.client")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mappers")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}

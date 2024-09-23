-- candy_ai.ModelPreviewFormItem definition
DROP TABLE if exists `ModelPreviewFormItem`;
CREATE TABLE `ModelPreviewFormItem` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `role_id` bigint NOT NULL COMMENT '模型角色id',
                                        `type` varchar(100) NOT NULL COMMENT 'formItem类型，限制于text file等',
                                        `name` varchar(100) NOT NULL COMMENT '列展示的名字，到时候前端传参数会用这个',
                                        `placeHolder` varchar(100) DEFAULT NULL COMMENT '提示语',
                                        `max` int DEFAULT NULL COMMENT '最大最小限制',
                                        `min` int DEFAULT NULL COMMENT '最大最小限制',
                                        `required` tinyint(1) DEFAULT NULL COMMENT '是否要求前端是否是必填项目',
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- candy_ai.ai_api_key definition
DROP TABLE if exists `ai_api_key`;
CREATE TABLE `ai_api_key` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                              `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                              `platform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '平台',
                              `api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密钥',
                              `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '自定义 API 地址',
                              `status` int NOT NULL COMMENT '状态',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI API 密钥表';


-- candy_ai.ai_chat_conversation definition
DROP TABLE if exists `ai_chat_conversation`;
CREATE TABLE `ai_chat_conversation` (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '对话编号',
                                        `user_id` bigint NOT NULL COMMENT '用户编号',
                                        `role_id` bigint DEFAULT NULL COMMENT '聊天角色',
                                        `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对话标题',
                                        `model_id` bigint NOT NULL COMMENT '模型编号',
                                        `template_id` bigint NOT NULL DEFAULT '0',
                                        `model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型标识',
                                        `pinned` bit(1) NOT NULL COMMENT '是否置顶',
                                        `pinned_time` datetime DEFAULT NULL COMMENT '置顶时间',
                                        `system_message` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色设定',
                                        `temperature` double NOT NULL COMMENT '温度参数',
                                        `max_tokens` int NOT NULL COMMENT '单条回复的最大 Token 数量',
                                        `max_contexts` int NOT NULL COMMENT '上下文的最大 Message 数量',
                                        `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                        `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                        `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `delete_time_stamp` bigint DEFAULT '0' COMMENT '删除标识',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI 聊天对话表';


-- candy_ai.ai_chat_message definition
DROP TABLE if exists `ai_chat_message`;
CREATE TABLE `ai_chat_message` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息编号',
                                   `conversation_id` bigint NOT NULL COMMENT '对话编号',
                                   `reply_id` bigint DEFAULT NULL COMMENT '回复编号',
                                   `user_id` bigint NOT NULL COMMENT '用户编号',
                                   `role_id` bigint DEFAULT NULL COMMENT '角色编号',
                                   `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息类型',
                                   `model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型标识',
                                   `model_id` bigint NOT NULL COMMENT '模型编号',
                                   `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
                                   `use_context` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否携带上下文',
                                   `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
                                   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
                                   `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                   `tenant_id` bigint DEFAULT NULL COMMENT '租户编号',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3097 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI 聊天消息表';


-- candy_ai.ai_chat_model definition
DROP TABLE if exists `ai_chat_model`;
CREATE TABLE `ai_chat_model` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                 `key_id` bigint NOT NULL COMMENT 'API 秘钥编号',
                                 `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型名字',
                                 `model` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型标识',
                                 `platform` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型平台',
                                 `sort` int NOT NULL COMMENT '排序',
                                 `status` tinyint NOT NULL COMMENT '状态',
                                 `temperature` double DEFAULT NULL COMMENT '温度参数',
                                 `max_tokens` int DEFAULT NULL COMMENT '单条回复的最大 Token 数量',
                                 `max_contexts` int DEFAULT NULL COMMENT '上下文的最大 Message 数量',
                                 `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                 `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI 聊天模型表';


-- candy_ai.ai_chat_role definition
DROP TABLE if exists `ai_chat_role`;
CREATE TABLE `ai_chat_role` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色编号',
                                `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
                                `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
                                `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色描述',
                                `status` tinyint DEFAULT NULL COMMENT '状态',
                                `sort` int NOT NULL DEFAULT '0' COMMENT '角色排序',
                                `user_id` bigint DEFAULT NULL COMMENT '用户编号',
                                `public_status` bit(1) NOT NULL COMMENT '是否公开',
                                `category` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色类别',
                                `model_id` bigint DEFAULT NULL COMMENT '模型编号',
                                `system_message` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色上下文',
                                `role_type_id` bigint NOT NULL COMMENT '角色名称 没有就取0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI 聊天角色表';


-- candy_ai.sys_role definition
DROP TABLE if exists `sys_role`;
CREATE TABLE `sys_role` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `role` varchar(100) NOT NULL,
                            `create_time` datetime NOT NULL,
                            `delete_time_stamp` datetime DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';


-- candy_ai.`user` definition
DROP TABLE if exists `sys_role`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
                        `nickname` varchar(255) NOT NULL COMMENT '用户名称',
                        `password` varchar(255) NOT NULL COMMENT '密码',
                        `avatar` varchar(255) DEFAULT '' COMMENT '头像',
                        `email` varchar(255) DEFAULT '' COMMENT '邮箱',
                        `type` smallint DEFAULT '0' COMMENT '0:普通用户 1:管理员用户',
                        `status` tinyint DEFAULT '1' COMMENT '用户状态是否封禁',
                        `delete_time_stamp` bigint DEFAULT '0' COMMENT '删除标识',
                        `create_time` timestamp NOT NULL,
                        `update_time` timestamp NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `nickname` (`nickname`),
                        UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- candy_ai.user_role_relation definition
DROP TABLE if exists `user_role_relation`;
CREATE TABLE `user_role_relation` (
                                      `id` bigint NOT NULL,
                                      `user_id` bigint NOT NULL,
                                      `role_id` bigint NOT NULL,
                                      `delete_time_stamp` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- candy_ai.ai_chat_role_type definition
DROP TABLE if exists `ai_chat_role_type`;
CREATE TABLE `ai_chat_role_type` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `name` varchar(100) NOT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `ai_chat_role_type_unique` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色主分类';






INSERT INTO candy_ai.ModelPreviewFormItem (role_id,`type`,name,placeHolder,max,min,required) VALUES
                                                                                                 (1,'text','学段及学科','例如，小学数学、初中语文等',NULL,2,1),
                                                                                                 (1,'text','背景以及现状分析','背景以及现状描述，包括学生学习情况、教师队伍素质',NULL,NULL,0),
                                                                                                 (1,'text','工作目标','分点简述教研目标，例如：1.定期举办多元化理论教育',NULL,NULL,1),
                                                                                                 (1,'text','工作重点及具体措施','分点简述工作重点或关键词，例如：1.深化基于新课标的教学改革，积极探索大单元整体教学、项目化学习等教学方式……',NULL,NULL,1),
                                                                                                 (2,'text','学段','例如，幼儿园中班、小学三年级',NULL,NULL,1),
                                                                                                 (2,'text','班级情况描述','简单描述班级情况，例如基本概况、优势及弱势表现、个别情况等方面',NULL,NULL,1),
                                                                                                 (2,'text','主要工作','尽可能详细地描述重点工作，例如抓好班级常规，增强幼儿集体意识；做好家校沟通等等',NULL,NULL,1);
INSERT INTO candy_ai.ai_api_key (name,platform,api_key,url,status) VALUES
                                                                       ('deepseek','DeepSeek','sk-f91846a6776b4a64baae2366e5ffc52d','https://api.deepseek.com',1),
                                                                       ('OpenAI','OpenAI','sk-123efsdfjadsdfnsdnfdsnfnasnfnsfsdf','https://gpt.oret1930.workers.dev',1);
INSERT INTO candy_ai.ai_chat_conversation (user_id,role_id,title,model_id,template_id,model,pinned,pinned_time,system_message,temperature,max_tokens,max_contexts,creator,create_time,updater,update_time,delete_time_stamp) VALUES
                                                                                                                                                                                                                                 (1,NULL,'新对话',1,0,'deepseek-chat',0,NULL,'为一项学校作业制定一份简明实用的教学和研究计划，以实际应用为基础。请提供一份结构清晰的提纲，其中包括以下内容： 1. 研究问题： 确定一个与教学相关的具体主题或问题。 2. 学习目标： 确定学生将获得的技能或知识。 3. 教学方法： 描述相关的教学策略，包括理论基础和实际意义。 4. 评估和评价： 概述衡量学生学习和进步的方法。 5. 时间表： 提供完成教学和研究计划的详细时间表。 该计划应针对[插入具体学科/主题]课堂，并满足[插入具体学生年龄组/水平]的需求。请确保您的计划条理清晰、简单易懂，并展示出您对教学和研究最佳实践的清晰理解"。 这一修改后的提示 * 明确界定任务和预期成果 * 对计划内容提出具体要求 * 要求注意细节、条理和连贯性 *  生成适合特定学科和学生群体的计划',1.0,2048,10,NULL,'2024-09-10 14:27:52',NULL,'2024-09-22 14:39:28',0),
                                                                                                                                                                                                                                 (1,1,'老师角色模版',1,0,'deepseek-chat',0,NULL,'你是一个老师',1.0,2048,10,NULL,'2024-09-10 14:29:00',NULL,'2024-09-10 14:29:00',0),
                                                                                                                                                                                                                                 (3,1,'老师角色模版',1,0,'deepseek-chat',0,NULL,'你是一个老师',1.0,2048,10,NULL,'2024-09-11 18:11:40',NULL,'2024-09-11 18:11:40',0),
                                                                                                                                                                                                                                 (3,1,'老师角色模版',1,0,'deepseek-chat',0,NULL,'你是一个老师',1.0,2048,10,NULL,'2024-09-11 18:12:38',NULL,'2024-09-11 18:12:38',0),
                                                                                                                                                                                                                                 (3,1,'老师角色模版',1,0,'deepseek-chat',0,NULL,'你是一个老师',1.0,2048,10,NULL,'2024-09-11 18:12:39',NULL,'2024-09-11 18:12:39',0),
                                                                                                                                                                                                                                 (4,1,'老师角色模版',1,0,'deepseek-chat',0,NULL,'你是一个老师',1.0,2048,10,NULL,'2024-09-12 10:20:45',NULL,'2024-09-12 10:20:45',0),
                                                                                                                                                                                                                                 (4,2,'老师角色模版',2,0,'gpt-4o',0,NULL,'你是一个老师',1.0,2048,10,NULL,'2024-09-12 17:10:14',NULL,'2024-09-12 17:10:14',0);
INSERT INTO candy_ai.ai_chat_model (key_id,name,model,platform,sort,status,temperature,max_tokens,max_contexts,creator,create_time,updater,update_time,deleted,tenant_id) VALUES
                                                                                                                                                                              (1,'DeepSeek','deepseek-chat','DeepSeek',0,1,1.0,2048,10,'','2024-09-10 02:58:46','','2024-09-10 10:05:53',0,0),
                                                                                                                                                                              (2,'OpenAI','gpt-4o','OpenAI',0,1,1.0,2048,10,'','2024-09-10 02:58:46','','2024-09-10 10:05:53',0,0);
INSERT INTO candy_ai.ai_chat_role (name,avatar,description,status,sort,user_id,public_status,category,model_id,system_message,role_type_id) VALUES
                                                                                                                                                ('教研计划','''''','老师角色',1,0,NULL,1,'teacher',1,'为一项学校作业制定一份简明实用的教学和研究计划，以实际应用为基础。请提供一份结构清晰的提纲，其中包括以下内容： 1. 研究问题： 确定一个与教学相关的具体主题或问题。 2. 学习目标： 确定学生将获得的技能或知识。 3. 教学方法： 描述相关的教学策略，包括理论基础和实际意义。 4. 评估和评价： 概述衡量学生学习和进步的方法。 5. 时间表： 提供完成教学和研究计划的详细时间表。 该计划应针对[插入具体学科/主题]课堂，并满足[插入具体学生年龄组/水平]的需求。请确保您的计划条理清晰、简单易懂，并展示出您对教学和研究最佳实践的清晰理解"。 这一修改后的提示 * 明确界定任务和预期成果 * 对计划内容提出具体要求 * 要求注意细节、条理和连贯性 *  生成适合特定学科和学生群体的计划',1),
                                                                                                                                                ('工作计划','''''','老师角色',1,0,NULL,1,'teacher',2,'请根据以下信息，为班级环境制定一份工作计划： * 学年： [插入学年，如幼儿园、中级班、三年级］ * 班级情况： 简要概述班级情况，包括学生表现的优缺点、个人情况及其他相关方面。 详细描述需要完成的主要工作，包括 * 建立促进良好学习环境的课堂常规 * 通过[插入具体策略或技巧]增强幼儿的集体意识 * 促进家校之间的有效沟通，包括[插入具体方法或工具］ 请根据所提供的信息，提供一份全面、可行的工作计划"。 这一修改后的提示更加具体、清晰和简洁',1);
INSERT INTO candy_ai.`user` (nickname,password,avatar,email,`type`,status,delete_time_stamp,create_time,update_time) VALUES
                                                                                                                         ('wang','$2a$10$ZWrIzDwIHLDAoE0AF5IlBO5vIfPW8NM3OvBJUpMAyfU5S2arV9KBO','','',0,1,0,'2024-09-09 10:42:59','2024-09-09 10:42:59'),
                                                                                                                         ('lmz','$2a$10$Bf71oJVxoFX4vDlrhGbete7W72pchiWMSZq4X6arm1GDLcDqFLzIu','','123',0,1,0,'2024-09-11 18:01:35','2024-09-11 18:01:35'),
                                                                                                                         ('lmf','$2a$10$47xjIu/whRZbl0xW.AYZA.UoynBC0T7J/DtCzdAG5BN6wBSF52Q4G','','1234',0,1,0,'2024-09-12 10:19:30','2024-09-12 10:19:30');
INSERT INTO candy_ai.ai_chat_role_type (name) VALUES
                                                  ('AI创意教学'),
                                                  ('AI教学'),
                                                  ('测试3'),
                                                  ('测试4'),
                                                  ('测试5'),
                                                  ('测试6'),
                                                  ('测试7'),
                                                  ('测试8'),
                                                  ('测试9');

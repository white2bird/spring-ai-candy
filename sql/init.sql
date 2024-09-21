drop table if exists `user`
create table user
(
    id                INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    nickname          VARCHAR(255) UNIQUE NOT NULL COMMENT '用户名称',
    password          VARCHAR(255)        NOT NULL COMMENT '密码',
    avatar            VARCHAR(255) DEFAULT "" COMMENT '头像',
    email             VARCHAR(255) DEFAULT "" NULL UNIQUE COMMENT '邮箱',
    type              SMALLINT     DEFAULT 0 COMMENT '0:普通用户 1:管理员用户',
    status            TINYINT      DEFAULT 1 COMMENT '用户状态是否封禁',
    delete_time_stamp BIGINT       DEFAULT 0 COMMENT '删除标识',
    create_time       TIMESTAMP           NOT NULL,
    update_time       TIMESTAMP           NOT NULL

);



DROP TABLE IF EXISTS `ai_chat_conversation`;
CREATE TABLE `ai_chat_conversation`
(
    `id`                bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '对话编号',
    `user_id`           bigint                                                        NOT NULL COMMENT '用户编号',
    `role_id`           bigint NULL DEFAULT NULL COMMENT '聊天角色',
    `title`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对话标题',
    `model_id`          bigint                                                        NOT NULL COMMENT '模型编号',
    `model`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '模型标识',
    `pinned`            bit(1)                                                        NOT NULL COMMENT '是否置顶',
    `pinned_time`       datetime NULL DEFAULT NULL COMMENT '置顶时间',
    `system_message`    varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色设定',
    `temperature` double NOT NULL COMMENT '温度参数',
    `max_tokens`        int                                                           NOT NULL COMMENT '单条回复的最大 Token 数量',
    `max_contexts`      int                                                           NOT NULL COMMENT '上下文的最大 Message 数量',
    `creator`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`       datetime NULL DEFAULT NULL COMMENT '创建时间',
    `updater`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
    `update_time`       datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `delete_time_stamp` BIGINT DEFAULT 0 COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI 聊天对话表';


DROP TABLE IF EXISTS `ai_chat_message`;
CREATE TABLE `ai_chat_message`  (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息编号',
                                    `conversation_id` bigint NOT NULL COMMENT '对话编号',
                                    `reply_id` bigint NULL DEFAULT NULL COMMENT '回复编号',
                                    `user_id` bigint NOT NULL COMMENT '用户编号',
                                    `role_id` bigint NULL DEFAULT NULL COMMENT '角色编号',
                                    `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息类型',
                                    `model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型标识',
                                    `model_id` bigint NOT NULL COMMENT '模型编号',
                                    `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
                                    `use_context` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否携带上下文',
                                    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
                                    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
                                    `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                    `tenant_id` bigint NULL DEFAULT NULL COMMENT '租户编号',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2465 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI 聊天消息表';


DROP TABLE IF EXISTS `ai_chat_model`;
CREATE TABLE `ai_chat_model`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
                                  `key_id` bigint NOT NULL COMMENT 'API 秘钥编号',
                                  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型名字',
                                  `model` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型标识',
                                  `platform` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模型平台',
                                  `sort` int NOT NULL COMMENT '排序',
                                  `status` tinyint NOT NULL COMMENT '状态',
                                  `temperature` double NULL DEFAULT NULL COMMENT '温度参数',
                                  `max_tokens` int NULL DEFAULT NULL COMMENT '单条回复的最大 Token 数量',
                                  `max_contexts` int NULL DEFAULT NULL COMMENT '上下文的最大 Message 数量',
                                  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
                                  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI 聊天模型表';



-- candy_ai.ModelPreviewFormItem definition

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO candy_ai.ModelPreviewFormItem (role_id,`type`,name,placeHolder,max,min,required) VALUES
                                                                                                 (1,'text','老师角色','目前是几年级老师(三年级)',NULL,2,1),
                                                                                                 (1,'text','该孩子平时表现','描述一下孩子的活泼度',NULL,NULL,0);
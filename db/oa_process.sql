/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : guigu-oa

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 31/01/2024 20:04:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oa_process
-- ----------------------------
DROP TABLE IF EXISTS `oa_process`;
CREATE TABLE `oa_process`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `process_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '审批code',
  `user_id` bigint(1) NOT NULL DEFAULT 0 COMMENT '用户id',
  `process_template_id` bigint(20) NULL DEFAULT NULL COMMENT '审批模板id',
  `process_type_id` bigint(20) NULL DEFAULT NULL COMMENT '审批类型id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `form_values` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '表单值',
  `process_instance_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例id',
  `current_auditor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前审批人',
  `status` tinyint(3) NULL DEFAULT NULL COMMENT '状态（0：默认 1：审批中 2：审批通过 -1：驳回）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '删除标记（0:不可用 1:可用）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '审批类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oa_process
-- ----------------------------
INSERT INTO `oa_process` VALUES (1, '审批代码 1', 1, 1, 1, '标题 1', '描述 1', '表单值 1', '流程实例 ID 1', '当前审批人 1', 1, '2024-01-31 18:58:21', '2024-01-31 18:58:21', 0);
INSERT INTO `oa_process` VALUES (2, '审批代码 2', 2, 2, 2, '标题 2', '描述 2', '表单值 2', '流程实例 ID 2', '当前审批人 2', 1, '2024-01-31 18:58:21', '2024-01-31 18:58:21', 0);
INSERT INTO `oa_process` VALUES (3, '审批代码 3', 3, 3, 3, '标题 3', '描述 3', '表单值 3', '流程实例 ID 3', '当前审批人 3', 1, '2024-01-31 18:58:21', '2024-01-31 18:58:21', 0);
INSERT INTO `oa_process` VALUES (4, '审批代码 4', 4, 4, 4, '标题 4', '描述 4', '表单值 4', '流程实例 ID 4', '当前审批人 4', 1, '2024-01-31 18:58:21', '2024-01-31 18:58:21', 0);

SET FOREIGN_KEY_CHECKS = 1;

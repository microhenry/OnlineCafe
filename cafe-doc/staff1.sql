
user
use demo;
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'loginName',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'userName',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password',
  `sex` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'gender',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email',
  `address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'address',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `user` VALUES (1, 'zhangsan', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (2, 'zhangsan1', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (3, 'zhangsan2', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (4, 'zhangsan3', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (5, 'zhangsan4', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (6, 'zhangsan5', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (7, 'zhangsan6', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (8, 'zhangsan7', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (9, 'zhangsan8', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (10, 'zhangsan9', '张三', '123', '男', '888@qq.com', '呼伦贝尔');
INSERT INTO `user` VALUES (11, 'zhangsan10', '张三', '123', '男', '888@qq.com', '呼伦贝尔');

SET FOREIGN_KEY_CHECKS = 1;
#xt37的博客
SQL语句
```mysql
CREATE TABLE `article`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文章内容',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文章标题',
  `group` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文章分类',
  `status` int(1) NULL DEFAULT NULL COMMENT '是否删除',
  `floow` int(255) NULL DEFAULT NULL COMMENT '点赞数',
  `gmt_create` date NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` date NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;
```
```mysql
CREATE TABLE `user`  (
  `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户密码 转MD5后存入',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像',
  `jurisdiction` int(1) NULL DEFAULT NULL COMMENT '权限等级',
  `is_delete` int(1) NOT NULL COMMENT '是否删除 0或1',
  `gmt_greate` date NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` date NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;
```
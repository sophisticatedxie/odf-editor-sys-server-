
-- ----------------------------
-- Table structure for et_attribute
-- ----------------------------
CREATE TABLE IF NOT EXISTS et_attribute  (
  id varchar(50)  NOT NULL COMMENT '属性id',
  attribute_name varchar(50)  NULL DEFAULT NULL COMMENT '属性名',
  val_express varchar(255)  NULL DEFAULT NULL COMMENT '属性内容占位符表达式',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  create_by varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for et_attribute_node_relationship
-- ----------------------------
CREATE TABLE IF NOT EXISTS et_attribute_node_relationship  (
  id varchar(50)  NOT NULL COMMENT '属性节点关联id',
  node_id varchar(50)  NULL DEFAULT NULL COMMENT '节点ID',
  attribute_id varchar(50)  NULL DEFAULT NULL COMMENT '属性id',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  create_by varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ;

-- ----------------------------
-- Table structure for et_crypto
-- ----------------------------
CREATE TABLE IF NOT EXISTS et_crypto  (
  id varchar(50)  NOT NULL COMMENT '加密算法id',
  func_content blob NULL COMMENT '算法内容(js函数)',
  func_name varchar(50)  NULL DEFAULT NULL COMMENT '算法名字',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  create_by varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  relation_id varchar(50)  NULL DEFAULT NULL COMMENT '关联id',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ;

-- ----------------------------
-- Table structure for et_generate_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS et_generate_log  (
  log_id varchar(50)  NOT NULL COMMENT '生成日志id',
  template_id varchar(50)  NULL DEFAULT NULL COMMENT '模板ID',
  source_data blob NULL COMMENT '数据源',
  ip varchar(50)  NULL DEFAULT NULL COMMENT 'ip地址',
  explorer_info varchar(255)  NULL DEFAULT NULL COMMENT '浏览器信息',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  create_by varchar(255)  NULL DEFAULT NULL COMMENT '创建者',
  result blob NULL COMMENT '生成结果(xml)',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (log_id)
) ;

-- ----------------------------
-- Table structure for et_node
-- ----------------------------
CREATE TABLE IF NOT EXISTS et_node  (
  id varchar(50)  NOT NULL COMMENT '节点id',
  element_name varchar(50)  NULL DEFAULT NULL COMMENT '节点名字',
  is_simple int(1) NULL DEFAULT NULL COMMENT '是否单节点(是否循环)',
  auto_field varchar(50)  NULL DEFAULT NULL COMMENT '数据自动域名',
  content_express varchar(255)  NULL DEFAULT NULL COMMENT '节点内容表达式',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  create_by varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  isbody varchar (50) NULL DEFAULT  NULL COMMENT '是否为body节点',
  condition varchar(56) NULL DEFAULT  NULL COMMENT '条件表达式',
  enable_condition int(1) NULL DEFAULT 0 COMMENT '是否启动条件展示 0 否 1是',
  PRIMARY KEY (id)
);


 CREATE TABLE IF NOT EXISTS  et_template_class(
  id varchar(50)  NOT NULL COMMENT '类别id',
  class_name varchar (50) NOT NULL DEFAULT  NULL COMMENT '类别名',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  create_by varchar(50)  NULL DEFAULT NULL COMMENT '创建者',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (id)
 );

-- ----------------------------
-- Table structure for et_print_log
-- ----------------------------
CREATE TABLE IF NOT EXISTS et_print_log  (
  id varchar(50)  NOT NULL COMMENT '打印id',
  machine varchar(50)  NULL DEFAULT NULL COMMENT '打印机名字',
  ip varchar(50)  NULL DEFAULT NULL COMMENT '打印机IP',
  content blob NULL COMMENT '打印内容',
  template_id varchar(50)  NULL DEFAULT NULL COMMENT '模板ID',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '打印事件',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  create_by varchar(50)  NULL DEFAULT NULL COMMENT '打印者',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ;

-- ----------------------------
-- Table structure for et_template
-- ----------------------------
CREATE TABLE IF NOT EXISTS et_template  (
  template_id varchar(50)  NOT NULL COMMENT '模板ID',
  template_name varchar(50)  NULL DEFAULT NULL COMMENT '模板名字',
  template_class_code varchar(255)  NULL DEFAULT NULL COMMENT '所属类别',
  template_content blob NULL COMMENT '模板的dom加密密文',
  create_by varchar(255)  NULL DEFAULT NULL COMMENT '创建者',
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  restful_url varchar(255)  NULL DEFAULT NULL COMMENT '模板数据来源url',
  odf_xml blob  NULL DEFAULT NULL COMMENT '模板对应的xml信息',
  modified_by varchar(50)  NULL DEFAULT NULL COMMENT '更新者',
  modified_time datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  crypto_id varchar(50)  NULL DEFAULT NULL COMMENT '使用加密算法ID',
  body_id varchar (50) null DEFAULT NULL COMMENT 'odfbody节点id',
  PRIMARY KEY (template_id)
);



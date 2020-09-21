package com.onesports.editor.dao;


import com.onesports.editor.entity.model.BaseDao;
import com.onesports.editor.po.EtNode;
import com.onesports.editor.vo.NodeAttributeRelatiioinVO;

import java.util.List;

/**
 * <p>标题: 节点表 Dao 接口</p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2020</p>
 *
 * @version: 1.0
 * @author: xiejiarong
 * @date 2020-07-22
 */
public interface EtNodeDao extends BaseDao<EtNode> {

    List<NodeAttributeRelatiioinVO> nodesList() throws Exception;

}

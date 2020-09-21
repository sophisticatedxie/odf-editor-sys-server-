package com.onesports.editor.service.impl;


import com.onesports.editor.dao.EtNodeDao;
import com.onesports.editor.entity.model.BaseServiceImpl;
import com.onesports.editor.po.EtNode;
import com.onesports.editor.service.EtNodeService;
import com.onesports.editor.vo.NodeAttributeRelatiioinVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>标题: 节点表 Service 实现类</p>
 * <p>描述: </p>
 * <p>版权: Copyright (c) 2020</p>
 *
 * @version: 1.0
 * @author: xiejiarong
 * @date 2020-07-22
 */
@Service
public class EtNodeServiceImpl extends BaseServiceImpl<EtNodeDao, EtNode> implements EtNodeService {

    @Override
    public List<NodeAttributeRelatiioinVO> nodes() throws Exception {
        return this.baseDao.nodesList();
    }
}

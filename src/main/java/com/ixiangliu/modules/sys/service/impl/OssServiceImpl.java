package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.sys.dao.OssDao;
import com.ixiangliu.modules.sys.entity.Oss;
import com.ixiangliu.modules.sys.service.IOssService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OssServiceImpl extends ServiceImpl<OssDao, Oss> implements IOssService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<Oss> page = this.page(
				new Query<Oss>().getPage(params)
		);

		return new PageUtils(page);
	}
	
}

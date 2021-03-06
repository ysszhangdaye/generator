<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = table.classNameLowerCase>  
package ${basepackage}.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;


import ${daopackage}.I${className}Dao;
import ${basepackage}.dto.${className}Req;
import ${basepackage}.dto.${className}Resp;
import ${basepackage}.dto.Modify${className}Req;
import ${basepackage}.model.${className};
import ${basepackage}.service.I${className}Service;
import com.zihe.util.BaseList;
import com.zihe.util.ServiceResult;


@Service
public class ${className}ServiceImpl implements I${className}Service {

	@Resource
	private I${className}Dao ${table.classNameFirstLower}Dao; 	

	@Override
	public ServiceResult<BaseList<${className}Resp>> find${className}ListByPageNo(${className}Req req) {
		ServiceResult<BaseList<${className}Resp>> result = new ServiceResult<BaseList<${className}Resp>>();
		if (req == null) {
			return result.error("查询条件不能为空");
		}
		PageHelper.startPage(req.getPage(), req.getPageSize());

		List<${className}> baseList = ${table.classNameFirstLower}Dao.findList(req.to${className}());
		PageInfo<${className}> pageInfo = new PageInfo<>(baseList);

		List<${className}Resp> list = new ArrayList<${className}Resp>();
		if (!CollectionUtils.isEmpty(pageInfo.getList())) {
			pageInfo.getList().parallelStream().forEachOrdered(temp -> {
				list.add(new ${className}Resp(temp));
			});
		}

		BaseList<${className}Resp> ${table.classNameFirstLower}List = new BaseList<${className}Resp>();
		${table.classNameFirstLower}List.setList(list);
		${table.classNameFirstLower}List.setCurPage(req.getPage());
		${table.classNameFirstLower}List.setPageSize(req.getPageSize());
		${table.classNameFirstLower}List.setTotalPage(pageInfo.getPages());
		${table.classNameFirstLower}List.setTotalRows(pageInfo.getTotal());
		return result.success(${table.classNameFirstLower}List);
	}

	@Transactional
	@Override
	public ServiceResult<${className}Resp> save(Create${className}Req req) {
		ServiceResult<${className}Resp> result = new ServiceResult<${className}Resp>();
		if (req == null) {
			return result.error("保存对象不能为空");
		}
		${className} d = req.to${className}();

		int count = ${table.classNameFirstLower}Dao.save(d);
		if (count == 0) {
			return result.error("保存失败");
		}
		return result.success();
	}

	@Transactional
	@Override
	public ServiceResult<${className}Resp> update(Modify${className}Req req) {

		ServiceResult<${className}Resp> result = new ServiceResult<${className}Resp>();
		if (req == null) {
			return result.error("修改条件不能为空");
		}
		int count = ${table.classNameFirstLower}Dao.update(req.to${className}());
		if (count == 0) {
			return result.error("修改失败");
		}
		return result.success();
	}


	@Override
	public ServiceResult<${className}Resp> getById(${className}Req req) {
		ServiceResult<${className}Resp> result = new ServiceResult<${className}Resp>();
		if (req == null || req.getId() == null) {
			return result.error("查询条件不能为空");
		}
		${className} ${className} = ${table.classNameFirstLower}Dao.getById(req.getId());
		if(${className} == null || ${className}.getId() == null){
			return result.error("获取失败");
		}
		return result.success(new ${className}Resp(${className}));
	}

	
}

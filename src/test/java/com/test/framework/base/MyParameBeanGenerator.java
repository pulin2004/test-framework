package com.test.framework.base;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.databene.benerator.util.UnsafeMethodParamsGenerator;
import org.databene.benerator.wrapper.ProductWrapper;

import com.alibaba.fastjson.JSON;

public class MyParameBeanGenerator extends UnsafeMethodParamsGenerator {
	private String path;
	private Iterator<File> it;

	public MyParameBeanGenerator() {
	}

	// public MyParameBeanGenerator(String path)
	// {
	// this.path = path;
	// }

	public void setPath(String path) {
		this.path = path;
	}

	public ProductWrapper<Object[]> generate(
			ProductWrapper<Object[]> paramProductWrapper) {
		try {
			setIt();
			if (it.hasNext()) {

				return toWrapper();

			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private ProductWrapper<Object[]> toWrapper() {
		String json = JsonFileUtils.readJson(it.next());
		ParamerBean bean = JSON.parseObject(json, ParamerBean.class);
		ProductWrapper wrapper = new ProductWrapper();
		wrapper.wrap(bean.obtainObject());
		return wrapper;
	}

	private void setIt() throws Exception {
		if (it == null) {
			List<File> files = ReadDataXml.ReadXml(path);
			if (CollectionUtils.isNotEmpty(files)) {
				this.it = files.iterator();
			} else {
				throw new Exception("在" + path + "路径中找不到相关文件！");
			}
		}
	}

}

package cn.sportsdata.webapp.youth.common.vo.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestItemSelectorRenderVO {
	private Map<String,List<TestItemSelectorVO>> itemCategoryMap = new LinkedHashMap<String, List<TestItemSelectorVO>>();

	public Map<String, List<TestItemSelectorVO>> getItemCategoryMap() {
		return itemCategoryMap;
	}

	public void setItemCategoryMap(Map<String, List<TestItemSelectorVO>> itemCategoryMap) {
		this.itemCategoryMap = itemCategoryMap;
	}

	public TestItemSelectorRenderVO categoryWith(List<TestItemSelectorVO> fullSelectorList){
		if(fullSelectorList != null && !fullSelectorList.isEmpty()){
			for(TestItemSelectorVO item : fullSelectorList){
				String key = item.getTest_category_name();
				List<TestItemSelectorVO> categoryList;
				if(itemCategoryMap.containsKey(key)){
					categoryList = itemCategoryMap.get(key);
				}else{
					categoryList = new ArrayList<TestItemSelectorVO>();
					itemCategoryMap.put(key, categoryList);
				}
				categoryList.add(item);
			}
		}
		return this;
	}
	
	
	
	
	
}

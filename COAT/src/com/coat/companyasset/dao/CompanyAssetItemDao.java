package com.coat.companyasset.dao;

import java.sql.SQLException;

import com.coat.companyasset.entity.C_CompanyAsset_Item;

import util.Pager;

public interface CompanyAssetItemDao {
	Pager findPager(String[] fields, Pager page, Object... objects) throws SQLException, Exception;
	int save(C_CompanyAsset_Item companyAssetItem);
	int update(C_CompanyAsset_Item companyAssetItem) throws SQLException, ClassNotFoundException;
	int update(Integer itemId,String name,Integer itemnum,Integer remainnum) throws ClassNotFoundException, SQLException;
	int delete(Integer itemId) throws ClassNotFoundException, SQLException;
	int existItemCode(String itemcode) throws Exception;
	C_CompanyAsset_Item findById(Integer itemId) throws ClassNotFoundException, SQLException;
	
}

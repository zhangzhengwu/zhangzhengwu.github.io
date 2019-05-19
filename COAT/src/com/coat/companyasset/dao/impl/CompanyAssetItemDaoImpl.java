package com.coat.companyasset.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.coat.companyasset.dao.CompanyAssetItemDao;
import com.coat.companyasset.entity.C_CompanyAsset_Item;

import dao.common.BaseDao;
import util.Pager;

public class CompanyAssetItemDaoImpl extends BaseDao implements CompanyAssetItemDao {

	Logger logger = Logger.getLogger(CompanyAssetItemDaoImpl.class);
	
	@Override
	public Pager findPager(String[] fields, Pager page, Object... objects) throws Exception {
		String sql=" FROM c_companyasset_item " +
				" where   1 = 1 " +
				" and itemcode like ? " +
				" and itemname like ?" +
				" and sfyx like ?";

		String limit="order by createDate desc limit ?,?";
		Pager pager=null;
		try{
			pager=super.findPager(fields, sql, limit, page, objects);
		}finally{
			super.closeConnection();
		}
		return pager;
	}

	@Override
	public int save(C_CompanyAsset_Item companyAssetItem) {
		return super.saveObject(companyAssetItem);
	}

	@Override
	public int update(C_CompanyAsset_Item companyAssetItem) throws SQLException, ClassNotFoundException {
		String sql = "update c_companyasset_item set itemcode = ?,itemname = ?, itemnum = ?, remainnum = ?, creator = ?, createDate = ?, sfyx = ? where itemId = ?;";
		return super.update(sql,new Object[]{companyAssetItem.getItemcode(),companyAssetItem.getItemname(),companyAssetItem.getItemnum(),companyAssetItem.getRemainnum(),companyAssetItem.getCreator(),companyAssetItem.getCreateDate(),companyAssetItem.getSfyx(),companyAssetItem.getItemId()});
	}

	@Override
	public int delete(Integer itemId) throws ClassNotFoundException, SQLException {
		String sql = "update c_companyasset_item set sfyx = 'D' where itemId = ? and sfyx = 'Y' ;";
		return super.update(sql, new Object[]{itemId});
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		return (T) zhuanhuan(rs, C_CompanyAsset_Item.class);
	}

	@Override
	public int existItemCode(String itemcode) throws Exception {
		String sql = "select count(1) from c_companyasset_item where itemcode = ? and sfyx = 'Y'";
		return super.findCount(sql, itemcode);
	}

	@Override
	public C_CompanyAsset_Item findById(Integer itemId) throws ClassNotFoundException, SQLException {
		String sql = "select * from c_companyasset_item where itemId = ? ";
		return super.find(sql, itemId);
	}

	@Override
	public int update(Integer itemId,String name,Integer itemnum,Integer remainnum) throws ClassNotFoundException, SQLException {
		String sql = "update c_companyasset_item set itemname = ?, itemnum = ?, remainnum = ? where itemId = ?;";
		return super.update(sql,new Object[]{name,itemnum,remainnum,itemId});
	}
	
}

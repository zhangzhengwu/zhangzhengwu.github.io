package dao;

/**
 *  文具表
 * @author sky
 *
 */
public interface QueryMarkPermiumDao {
	/**
	 * Wilson
	 * 验证是否存在code
	 * @param procode
	 * @return
	 */
	public int findAllByCode(String procode) ;
	/**
	 * Wilson
	 * 保存Mark Premium 产品
	 * @param procode
	 * @param ename
	 * @param cname
	 * @param unitprice
	 * @param clubprice
	 * @param spprice
	 * @param unit
	 * @param num
	 * @param blbz
	 * @return
	 */
	public int saveProduct(String procode,String ename,String cname,Double unitprice,Double clubprice,String spprice,String unit,Double num,String blbz);

	public int delProduct(String procode);
	/**
	 * Wilson
	 * 修改 Mark Premium 产品
	 * @param procode
	 * @param ename
	 * @param cname
	 * @param unitprice
	 * @param clubprice
	 * @param spprice
	 * @param unit
	 * @param num
	 * @param blbz
	 * @return
	 */
	public int updProduct(String procode,String ename,String cname,Double unitprice,Double clubprice,String spprice,String unit,Double num,String blbz);

}

package cn.admin.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import util.Util;
/**
 * AD 域验证
 * @author kingxu
 *
 */
public class ADAuth {
	public static void main(String[] args) {
		
		Map<String,String> persion=new HashMap<String, String>();
		try {
			persion=vailADAuth("kingxu","456789");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		for(String key:persion.keySet()){
			System.out.print(key+"  :  "+persion.get(key)+"\r\n");
		}
	}
	/**
	 * 根据用户名密码验证AD域
	 * @author kingxu
	 * @date 2016-2-16
	 * @param username
	 * @param password
	 * @return
	 * @throws NamingException
	 * @return Map<String,String>
	 */
	public static Map<String,String> vailADAuth(String username,String password) throws NamingException{
		return auth(Util.getProValue("public.system.ldapserver.url"), Util.getProValue("public.system.ldapserver.domain"), username, password);
	}
	
	/**
	 * 根据LDAP以及domain验证用户名密码
	 * @author kingxu
	 * @date 2016-2-16
	 * @param server
	 * @param domain
	 * @param username
	 * @param password
	 * @return
	 * @throws NamingException
	 * @return Map<String,String>
	 */
	public static Map<String,String> auth(String server,String domain,String username,String password) throws NamingException{
		Map<String,String> persion=new LinkedHashMap<String, String>();
		Properties env = new Properties();
		LdapContext ctx=null;
		try {
			String ldapURL = "LDAP://"+server;//ip:port
			
		
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
			env.put(Context.SECURITY_PRINCIPAL, username+"@"+domain);
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.PROVIDER_URL, ldapURL);
			
			ctx = new InitialLdapContext(env, null);
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(&(objectCategory=person)(objectClass=*)(name=*)(sAMAccountName="+username+"))";
			String searchBase = "DC=convoy,DC=local";
			//String returnedAtts[] = {"description","sAMAccountName"};
			//searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter,searchCtls);
			if (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				//System.out.println("<<<::[" + sr.getAttributes().toString()+"]::>>>>");
				persion.put("staffCode", sr.getAttributes().get("description").get(0).toString());
				persion.put("username", sr.getAttributes().get("samaccountname").get(0).toString());
				persion.put("chinesename", sr.getAttributes().get("initials").get(0).toString());
				persion.put("mail", sr.getAttributes().get("mail").get(0).toString());
				persion.put("department", sr.getAttributes().get("department").get(0).toString());
				persion.put("createDate", sr.getAttributes().get("whencreated").get(0).toString());
				
				
				//persion.put("lastlogin", DateUtils.longToString(sr.getAttributes().get("lastLogon").get(0).toString(),"yyyy-MM-dd HH:mm:ss"));
				//persion.put("passwordsettime", DateUtils.longToString(sr.getAttributes().get("pwdlastset").get(0).toString(),"yyyy-MM-dd HH:mm:ss"));
				//persion.put("departheadcode", sr.getAttributes().get("st").get(0).toString());
				//	persion.put("departheadname", sr.getAttributes().get("postalcode").get(0).toString());
				//System.out.println(sr.getAttributes().get("lastLogon").get(0).toString());
				//System.out.println(sr.getAttributes().get("pwdlastset").get(0).toString());
 
			}
			
		}catch (NamingException e) {
			//e.printStackTrace();
			System.err.println("Problem searching directory: " + e);
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			if(!Util.objIsNULL(ctx)){
				ctx.close();
			}
		}
		return persion;
	}
}

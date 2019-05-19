package util;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/** 
 * ClassName:HttpUtil <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * @author   Administrator 
 * @version   
 * @since    JDK 1.6 
 * @see       
 */
public class HttpUtil {

	private static HttpClient httpclient;
	private static HttpPost httppost;
	public static void main(String[] args) throws ClientProtocolException, IOException {
		/*String url="http://maps.pccwwifi.com/flashsearch";
		Map<String,String> map=new HashMap<String, String>();
		map.put("lang", "en");
		map.put("rpp", "20");
		map.put("pn", "1");
		map.put("shop", "");
		map.put("category", "1");
		map.put("district", "");
		map.put("region", "1");
		post(url,map);*/

	}
	@SuppressWarnings("deprecation")
	public static String post(String url, Map<String, String> map) throws ClientProtocolException, IOException {
		String str = null;
		if (url == null || url.equals("")) {
			return null;
		}
		// httpclient = wrapClient(new DefaultHttpClient());
		// httpclient = new DefaultHttpClient();
		httpclient = getHttpClient();
		httppost = new HttpPost(url);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) {
			for (String s : map.keySet()) {
				formparams.add(new BasicNameValuePair(s, map.get(s)));
			}
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			HttpResponse response;
			response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				str = EntityUtils.toString(entity, "UTF-8");
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return str;
	}

	public static void get() {

	}

	/*public static HttpClient wrapClient(HttpClient base) {  
        try {  
            SSLContext ctx = SSLContext.getInstance(SSLSocketFactory.SSL); 
            X509TrustManager tm = new X509TrustManager() {  
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
                @Override  
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}  
                @Override  
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}  
            };
            ctx.init(null, new TrustManager[] { tm }, null);  
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
            SchemeRegistry registry = new SchemeRegistry();  
            registry.register(new Scheme("https", 443, ssf));  
            ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);  
            return new DefaultHttpClient(mgr, base.getParams());  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return null;  
        }  
    }  */
    
    public static CloseableHttpClient getHttpClient() {  
        try {  
            SSLContext sslContext = SSLContext.getInstance("SSL");  
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {  
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {  
  
                }  
  
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {  
  
                }  
  
                public X509Certificate[] getAcceptedIssuers() {  
                    /*return new X509Certificate[0];*/
                	return null;
                }  
            }}, new SecureRandom());  
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);  
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();  
            return closeableHttpClient;  
        } catch (Exception e) {  
            return HttpClientBuilder.create().build();  
        }  
    }  
}

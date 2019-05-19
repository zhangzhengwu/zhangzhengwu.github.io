package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FileUtil {
	 
	
 
	/**
	 * 重命名文件名
	 * @param oldName 原名称
	 * @param newName 新名称
	 * @return
	 */
	public static int Rename(String oldName,String newName){
		int num=-1;
		try{
			File file1=new File(oldName);    
			File file2=new File(newName);  
			if(!file1.exists()){
				return 1;
			}
			if(file2.exists()){//新文件名已存在
				if(deleteFile(newName)){//删除已存在的文件
					if(file1.renameTo(file2)){ //重命名文件，返回true
						num=1;////重命名成功
					}else{
						num=3;//重命名失败
					}
				}else{
					num=2;//删除失败
				}
				
			} else {
				if(file1.renameTo(file2)){ //重命名文件，返回true
					num=1;////重命名成功
				}
			}
		}catch(Exception e){
			num=0;//异常
		}
		return num;
	}

	/**
	 * 删除指定路径中所有的文件及目录
	 */
	public static void deleteAll(String rp) {
		File file = new File(rp);

		if (!file.exists()) {
			return;
		}

		File list[] = file.listFiles();
		if (list == null) {
			return;
		}

		boolean bool1 = true;
		for (int i = 0; i < list.length; i++) {
			try {
				if (list[i].isDirectory()) {
					deleteAll(list[i].toString());
					list[i].delete();
				} else {
					String path = list[i].getAbsolutePath();
					boolean flag = (new File(path)).delete();
					if (!flag) {
						bool1 = false;
					}
				}
			} catch (Exception ex) {
			}
		}

		if (bool1 && list.length > 0) {
			return;
		}
	}
	
	/**
	 * 删除指定路径中所有的文件及目录
	 */
	public static void deleteAllAndMy(String rp) {
		File file = new File(rp);

		if (!file.exists()) {
			return;
		}

		File list[] = file.listFiles();
		if (list == null) {
			return;
		}

		boolean bool1 = true;
		for (int i = 0; i < list.length; i++) {
			try {
				if (list[i].isDirectory()) {
					deleteAll(list[i].toString());
					list[i].delete();
				} else {
					String path = list[i].getAbsolutePath();
					boolean flag = (new File(path)).delete();
					if (!flag) {
						bool1 = false;
 
					}
				}
			} catch (Exception ex) {
			}
		}
		
		file.delete();

		if (bool1 && list.length > 0) {
			return;
		}
	}

	/**
	 * 将文件读到String
	 */
	public static String readChars(String file) {
		char[] chrBuffer = null;
		StringBuffer buffer = new StringBuffer(1024);
		java.io.File objFile = new java.io.File(file);
		// 判断文件是否存在
		java.io.FileReader objFileReader = null;
		try {
			if (objFile.exists()) {// 文件存在
				// 创建读文件对�?
				objFileReader = new java.io.FileReader(objFile);

				// 读文件内�?
				while ((objFileReader.read(chrBuffer)) != -1) {

					buffer.append(chrBuffer);
				}

			} else {// 文件不存�?
				System.out.println(file + " don't exist!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			// 关闭读文件对�?
			try {
				objFileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static byte[] readBytes(String path) {

		FileInputStream infile = null;

		// 生成对象infile 准备读取文件
		byte[] buff = null;
		try {
			infile = new FileInputStream(path);
			buff = new byte[infile.available()];
			infile.read(buff);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				infile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buff;
	}
	
	/**
	 * 
	 * @param File
	 * @return
	 */
	public static byte[] readBytes(File file) {

		FileInputStream infile = null;

		// 生成对象infile 准备读取文件
		byte[] buff = null;
		try {
			infile = new FileInputStream(file);
			buff = new byte[infile.available()];
			infile.read(buff);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				infile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buff;
	}

	public static String selFoldType(String str) {
		String sBack = "";
		str = str.toLowerCase();
		if ("doc".equals(str))
			sBack = "word.png";
		else if ("xls".equals(str))
			sBack = "excel.png";
		else if ("ppt".equals(str))
			sBack = "ppt.png";
		else if ("txt".equals(str))
			sBack = "txt.gif";
		else if ("rar".equals(str))
			sBack = "rar.gif";
		else if ("zip".equals(str))
			sBack = "rar.gif";
		else if ("pdf".equals(str))
			sBack = "pdf.gif";
		else if ("mdb".equals(str))
			sBack = "access.png";
		else if ("eml".equals(str))
			sBack = "outlook.png";
		else if ("gif".equals(str) || "jpg".equals(str) || "bmp".equals(str))
			sBack = "image.png";
		else
			sBack = "blank.gif";

		return sBack;
	}

	private static String getUpperPath(String AFileName) {
		for (int i = AFileName.length() - 2; i > 0; i--) {
			if (AFileName.charAt(i) == '/') {
				AFileName = AFileName.substring(0, i + 1);
				break;
			}
		}
		return AFileName;
	}

	/**
	 * 获取路径:WebRoot/WEB-INF/ 适合windows linux 返回系统路径
	 */
	public static String extractFilePath() {
		// Thread.currentThread().getClass().getClassLoader()
		// dir = dir.substring(5); //去除 file:
		// dir = dir.substring(0, dir.length()-8);

		String dir = FileUtil.class.getClassLoader().getResource("").toString();
		dir = dir.replaceAll("file:", "");// 去除 file:
		dir = getUpperPath(dir);
		dir = dir.replaceAll("%20", "");
		return dir;
	}

	/**
	 * 获取WebRoot路径
	 * 
	 * @return
	 */
	public static String getWebRootPath() {
		String sPath = extractFilePath();
		return sPath.substring(0, sPath.length() - 8);
	}

	/**
	 * 获取工程路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		String sPath = extractFilePath();
		return sPath.substring(0, sPath.length() - 16);
	}

	/**
	 * 获取src源代码路�?
	 * 
	 * @return
	 */
	public static String srcPath() {
		String sPath = extractFilePath();
		return sPath.substring(0, sPath.length() - 16) + "src/";
	}

	/**
	 * 指定的文件是否存�?
	 * 
	 * @param AFileName
	 * @return
	 */
	public static boolean fileExists(String AFileName) {
		boolean bFlag = false;
		File myFile = new File(AFileName);
		if (myFile.exists()) {
			if (myFile.isFile()) {
				bFlag = true;
			} else
				bFlag = false;
		} else
			bFlag = false;
		return bFlag;
	}

	/**
	 * 指定的路径是否存�?
	 * 
	 * @param ADirectory
	 * @return
	 */
	public static boolean directoryExists(String ADirectory) {
		boolean bFlag = false;
		File myFile = new File(ADirectory);
		if (myFile.exists()) {
			if (myFile.isDirectory()) {
				bFlag = true;
			} else
				bFlag = false;
		} else
			bFlag = false;
		return bFlag;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param AFileName
	 * @return
	 */
	public static boolean deleteFile(String AFileName) {
		boolean bFlag = true;
		File myFile = new File(AFileName);
		if (myFile.exists()) {
			if (myFile.isFile()) {
				bFlag = myFile.delete();
			} else
				bFlag = false;
		} else
			bFlag = false;
		return bFlag;
	}

	 
	public static void copyFile(File in, File out) {
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream(in);
			fos = new FileOutputStream(out);
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try
			{
				fos.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public  static void copyFile2(String oldPathFile, String newPathFile){
		int byteread = 0;

		int byteCount = 0;

		File oldfile = new File(oldPathFile);
System.out.println(oldPathFile+"==="+newPathFile);
		//用spilit方法实际是在用正则表达式进行匹配，java中//由于转义符代表为/，所以////实际为//，又在正则表达式中//代表/所以////表示/

		//而在不需要正则表达式的方法如String的replace则可以用//表示/这点需要注意

		String[] fileName = oldPathFile.split("////");

		String name = fileName[fileName.length - 1];

		boolean isSuccessful = true;

		if (oldfile.exists()) { // 文件存在时

		try {

		InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件

		BufferedInputStream bufferedInputStream = new BufferedInputStream(inStream);



		//复制到的文件必须是具体的文件不能是文件夹，如果为文件夹则出现拒绝访问的异常，所以加上name

		FileOutputStream fs = new FileOutputStream(newPathFile + "//" + name);

		BufferedOutputStream bufferedOutPutStream = new BufferedOutputStream(fs);



		byte[] buffer = new byte[1024 * 5];

		while ((byteread = bufferedInputStream.read(buffer)) != -1) {

		byteCount += byteread;

		bufferedOutPutStream.write(buffer, 0, byteread);

		}

		inStream.close();

		bufferedInputStream.close();

		fs.close();

		bufferedOutPutStream.close();

		} catch (IOException e) {

		System.out.println("读取文件出错！原因" + e.getMessage());

		isSuccessful = false;

		}

		} else {

		System.out.println("地址" + oldPathFile + "不存在文件");

		isSuccessful = false;

		}

		if (isSuccessful)

		System.out.println("复制成功！共复制" + byteCount + "字节");

		}
	
	public  static boolean copyFile3(String oldPathFile, String newPathFile){
		boolean isSuccessful = true;
		int byteread = 0;		
		int byteCount = 0;		
		File oldfile = new File(oldPathFile);
		System.out.println(oldPathFile+"--->"+newPathFile);
		if (oldfile.exists()) { // 文件存在时			
			try {				
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件				
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1024*5];
				while ((byteread = inStream.read(buffer)) > 0) {
					byteCount += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("读取文件出错！原因" + e.getMessage());
				isSuccessful = false;
			}
		} else {			
			System.out.println("地址" + oldPathFile + "不存在文件");			
			isSuccessful = false;			
		}
		if (isSuccessful){
			System.out.println("复制成功！共复制" + byteCount + "字节");
		}
		return isSuccessful;
	}

	

	public static boolean writeFile(byte[] AData, String AFileName) {

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(AFileName);
			out.write(AData);
			out.flush();
			return true;
		} catch (Exception E) {
			return false;
		} finally {
			try {
				if( out!=null )
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static int getFileSize(String AFileName) {
		int iSize = 0;
		FileInputStream in = null;
		try {
			in = new FileInputStream(AFileName);
			iSize = in.available();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			iSize=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			iSize=0;
		} finally {
			try {
				if( in!=null )
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return iSize;
	}
	
	public static int getFileSize(File file) {
		int iSize = 0;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			iSize = in.available();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			iSize=0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			iSize=0;
		} finally {
			try {
				if( in!=null )
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return iSize;
	}
	
	//统计文件夹的大小
	/*public static long getDirSize(File dir) {   
	   if (dir == null) {   
	       return 0;   
	   }   
	   if (!dir.isDirectory()) {   
	       return 0;   
	   }   
	   long dirSize = 0;   
	   File[] files = dir.listFiles();   
	    for (File file : files) {   
	        if (file.isFile()) {   
	            dirSize += file.length();   
	        } else if (file.isDirectory()) {   
	            dirSize += file.length();   
	            dirSize += getDirSize(file); // 如果遇到目录则�?�过递归调用继续统计   
	        }   
	    }   
	    return dirSize;   
	}*/

	public static byte[] readFile(String AFileName) {

		FileInputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(AFileName);
			data = new byte[in.available()];
			in.read(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	public static String path2Package(String APath) {
		return APath.replaceAll("/", ".");
	}

	public static String package2Path(String APackage) {
		return APackage.replaceAll("\\.", "/");
	}

	/**
	 * 上传文件
	 * 
	 * @param AData
	 * @param AFileName
	 * @return
	 */
	public static boolean uploadFile(byte[] AData, String AFileName) {
		try {
			writeFile(AData, AFileName);
			return true;
		} catch (Exception E) {
			return false;
		}
	}

	/**
	 * 
	 * @param AFileName
	 * @return
	 */
	public static byte[] downloadFile(String AFileName) {
		try {

			return readFile(AFileName);
		} catch (Exception E) {
			return null;
		}
	}

	public static boolean writeString2File(String path, String text) {
		// TODO Auto-generated method stub
		String _path_folder = path.substring(0, path.lastIndexOf("/"));
		if (!fileExists(_path_folder)) {
			File file = new File(_path_folder);
			file.mkdir();
		}

		byte[] byte_date = text.getBytes();
		return writeFile(byte_date, path);
	}

	public static boolean writeString2File(String path, byte[] fileData) {
		// TODO Auto-generated method stub
		String _path_folder = path.substring(0, path.lastIndexOf("/"));
		if (!fileExists(_path_folder)) {
			File file = new File(_path_folder);
			file.mkdir();
		}
		return writeFile(fileData, path);

	}
	
	public static boolean writeString3File(String path, byte[] fileData,String filename) {
		//String _path_folder = path.substring(0, path.lastIndexOf("/"));
		if (!fileExists(filename)) {
			 new File(filename);
			//file.mkdir();
		}
		return writeFile(fileData, path);

	}

	/**
	 * 保存到磁�?
	 * 
	 * @param AFileName
	 */
	public static void saveToFile(String Data, String AFileName) {

		BufferedWriter lineWriter = null;
		try {
			// txtWriter = new FileWriter(AFileName,false);
			// lineWriter = new BufferedWriter(txtWriter);
			lineWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(AFileName), "GBK"));

			// lineWriter.newLine();
			lineWriter.write(Data);
		} catch (Exception E) {
			E.printStackTrace();
		} finally {
			try {
				lineWriter.close();

			} catch (Exception E) {
				E.printStackTrace();
			}
		}
	}

	public static String loadFromFile(String AFileName) {
		String sBack = "";
		BufferedReader lineReader = null;
		try {

			lineReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(AFileName), "GBK"));

			String sText = "";
			while (true) {
				sText = lineReader.readLine();
				if (sText == null)
					break;
				sBack += sText;
			}

		} catch (Exception E) {

		} finally {
			try {

				lineReader.close();
			} catch (Exception E) {

			}
		}
		return sBack;
	}
	
	
	



 

	public static void ensureDir(String path) {
		File dir = new File(path);
		if( !dir.exists() )
		{
			dir.mkdirs();
		}
		else
		{
		}
	}
	
	public static String getClassPath()
	{
		  String p = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		  return p;
	}
	
	public static String getFileSeparator()
	{
		return System.getProperty("file.separator","\\");
	}
	
	/**
	 * 必须传全路径 
	 * @param srcfile
	 * @param destfile
	 * @return
	 */
	public static boolean rename(String srcfile, String destfile)
	{
		File file = new File( srcfile );
//		logger.info(file.getName());
		return file.renameTo(new File( destfile ));		
	}
	/** 
	* Moving a File to Another Directory 
	* @param srcFile eg: c:\windows\abc.txt 
	* @param destPath eg: c:\temp 
	* @return success 
	*/ 
	public static boolean move(String srcFile, String destPath){ 
		boolean success=false;
		try{
		// File (or directory) to be moved 
		File file = new File(srcFile); 
		// Destination directory 
		File dir = new File(destPath); 
		// Move file to new directory 
		 success= file.renameTo(new File(dir, file.getName())); 
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			
		}

	return success; 
	} 
	
	public static int length(String str){
		int sum=0;
		for(int i=0;i<str.length();i++)
		{
			if ((str.charAt(i)>=0) && (str.charAt(i)<=255))    
	    		sum=sum+1;    
	  		else   
	    		sum=sum+2;  
		}
		return sum;
	}
	
	 /** 
     * 移动指定文件或文件夹(包括所有文件和子文件夹) 
     *  
     * @param fromDir 
     *            要移动的文件或文件夹 
     * @param toDir 
     *            目标文件夹 
     * @throws Exception 
     */  
    public static void MoveFolderAndFileWithSelf(String from, String to) throws Exception {  
        try {  
            File dir = new File(from);  
            // 目标  
            to +=  File.separator + dir.getName();  
            File moveDir = new File(to);  
            if(dir.isDirectory()){  
                if (!moveDir.exists()) {  
                    moveDir.mkdirs();  
                }  
            }else{  
                File tofile = new File(to);  
                dir.renameTo(tofile);  
                return;  
            }  
              
            //System.out.println("dir.isDirectory()"+dir.isDirectory());  
            //System.out.println("dir.isFile():"+dir.isFile());  
              
            // 文件一览  
            File[] files = dir.listFiles();  
            if (files == null)  
                return;  
  
            // 文件移动  
            for (int i = 0; i < files.length; i++) {  
                System.out.println("文件名："+files[i].getName());  
                if (files[i].isDirectory()) {  
                    MoveFolderAndFileWithSelf(files[i].getPath(), to);  
                    // 成功，删除原文件  
                    files[i].delete();  
                }  
                File moveFile = new File(moveDir.getPath() + File.separator + files[i].getName());  
                // 目标文件夹下存在的话，删除  
                if (moveFile.exists()) {  
                    moveFile.delete();  
                }  
                files[i].renameTo(moveFile);  
            }  
            dir.delete();  
        } catch (Exception e) {  
            throw e;  
        }  
    }  
    /** 
     * 复制整个文件夹的内容(含自身) 
     * @param oldPath 准备拷贝的目录 
     * @param newPath 指定绝对路径的新目录 
     * @return 
     */  
    public static void copyFolderWithSelf(String oldPath, String newPath) {  
        try {  
            new File(newPath).mkdirs(); //如果文件夹不存在 则建立新文件夹  
            File dir = new File(oldPath);  
            // 目标  
            newPath +=  File.separator + dir.getName();  
            File moveDir = new File(newPath);  
            if(dir.isDirectory()){  
                if (!moveDir.exists()) {  
                    moveDir.mkdirs();  
                }  
            }  
            String[] file = dir.list();  
            File temp = null;  
            for (int i = 0; i < file.length; i++) {  
                if (oldPath.endsWith(File.separator)) {  
                    temp = new File(oldPath + file[i]);  
                } else {  
                    temp = new File(oldPath + File.separator + file[i]);  
                }  
                if (temp.isFile()) {  
                    FileInputStream input = new FileInputStream(temp);  
                    FileOutputStream output = new FileOutputStream(newPath +  
                            "/" +  
                            (temp.getName()).toString());  
                    byte[] b = new byte[1024 * 5];  
                    int len;  
                    while ((len = input.read(b)) != -1) {  
                        output.write(b, 0, len);  
                    }  
                    output.flush();  
                    output.close();  
                    input.close();  
                }  
                if (temp.isDirectory()) { //如果是子文件夹  
                    copyFolderWithSelf(oldPath + "/" + file[i], newPath);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /**
     *      
     * 复制整个文件夹的内容(会将旧目录添加当天时间标识) 
     * @author kingxu
     * @date 2015-9-22
     * @param oldPath 准备拷贝的目录 
     * @param newPath 指定绝对路径的新目录
     * @return void
     */
    public static void copyFolderContext(String oldPath, String newPath) {  
        try {  
         //   new File(newPath).mkdirs(); //如果文件夹不存在 则建立新文件夹  
            File dir = new File(oldPath);  
            // 目标  
            newPath +=  File.separator + dir.getName()+"("+DateUtils.nowtime()+")";  
            File moveDir = new File(newPath);  
            if(dir.isDirectory()){  
                if (!moveDir.exists()) {  
                    moveDir.mkdirs();  
                }  
            }  
            String[] file = dir.list();  
            File temp = null;  
            for (int i = 0; i < file.length; i++) {  
                if (oldPath.endsWith(File.separator)) {  
                    temp = new File(oldPath + file[i]);  
                } else {  
                    temp = new File(oldPath + File.separator + file[i]);  
                }  
                if (temp.isFile()) {  
                    FileInputStream input = new FileInputStream(temp);  
                    FileOutputStream output = new FileOutputStream(newPath +  
                            "/" +  
                            (temp.getName()).toString());  
                    byte[] b = new byte[1024 * 5];  
                    int len;  
                    while ((len = input.read(b)) != -1) {  
                        output.write(b, 0, len);  
                    }  
                    output.flush();  
                    output.close();  
                    input.close();  
                }  
                if (temp.isDirectory()) { //如果是子文件夹  
                	copyFolderContext(oldPath + "/" + file[i], newPath);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
	public static void main(String[] args)
	{
		String file1="C:\\logs";
		String file2="C:\\barcodes";
	try {
		System.out.println("开始");
		copyFolderContext(file1, file2);
		System.out.println("结束");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}

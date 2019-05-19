package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
/**
 * 压缩包操作工具类
 * @author kingxu
 *
 */
public class ZipUtil {
	  
	  
    /** 
      * @Description:  
      *     压缩文件 
      * @param sourcePath 将要压缩的文件或目录的路径，请使用绝对路径 
      * @param zipPath 生成压缩文件的路径，请使用绝对路径。如果该路径以“.zip”为结尾， 
      *         则压缩文件的名称为此路径；如果该路径不以“.zip”为结尾，则压缩文件的名称 
      *         为该路径加上将要压缩的文件或目录的名称，再加上以“.zip”结尾 
      * @param encoding 压缩编码 
      * @param comment 压缩注释 
     */  
    public static void compress(String sourcePath, String zipPath, String encoding, String comment)  
            throws FileNotFoundException, IOException {  
        // 判断要压缩的文件是否存在  
        File sourceFile = new File(sourcePath);  
        if (!sourceFile.exists() || (sourceFile.isDirectory() && sourceFile.list().length == 0)) {  
            throw new FileNotFoundException("要压缩的文件或目录不存在，或者要压缩的目录为空");  
        }  
        // 设置压缩文件路径，默认为将要压缩的路径的父目录为压缩文件的父目录  
        if (zipPath == null || "".equals(zipPath)) {  
            String sourcePathName = sourceFile.getAbsolutePath();  
            int index = sourcePathName.lastIndexOf(".");  
            zipPath = (index > -1 ? sourcePathName.substring(0, index) : sourcePathName) + ".zip";  
        } else {  
            // 如果压缩路径为目录，则将要压缩的文件或目录名做为压缩文件的名字，这里压缩路径不以“.zip”为结尾则认为压缩路径为目录  
            if(!zipPath.endsWith(".zip")){  
                // 如果将要压缩的路径为目录，则以此目录名为压缩文件名；如果将要压缩的路径为文件，则以此文件名（去除扩展名）为压缩文件名  
                String fileName = sourceFile.getName();  
                int index = fileName.lastIndexOf(".");  
                zipPath = zipPath + File.separator + (index > -1 ? fileName.substring(0, index) : fileName) + ".zip";  
            }  
        }  
        // 设置解压编码  
        if (encoding == null || "".equals(encoding)) {  
            encoding = "GBK";  
        }  
        // 要创建的压缩文件的父目录不存在，则创建  
        File zipFile = new File(zipPath);  
        if (!zipFile.getParentFile().exists()) {  
            zipFile.getParentFile().mkdirs();  
        }  
        // 创建压缩文件输出流  
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(zipPath);  
        } catch (FileNotFoundException e) {  
            if (fos != null) {  
                try{ fos.close(); } catch (Exception e1) {}  
            }  
        }  
        // 使用指定校验和创建输出流  
        CheckedOutputStream csum = new CheckedOutputStream(fos, new CRC32());  
        // 创建压缩流  
        ZipOutputStream zos = new ZipOutputStream(csum);  
        // 设置编码，支持中文  
        zos.setEncoding(encoding);  
        // 设置压缩包注释  
        zos.setComment(comment);  
        // 启用压缩  
        zos.setMethod(ZipOutputStream.DEFLATED);  
        // 设置压缩级别为最强压缩  
        zos.setLevel(Deflater.BEST_COMPRESSION);  
        // 压缩文件缓冲流  
        BufferedOutputStream bout = null;  
        try {  
            // 封装压缩流为缓冲流  
            bout = new BufferedOutputStream(zos);  
            // 对数据源进行压缩  
            compressRecursive(zos, bout, sourceFile, sourceFile.getParent());  
        } finally {  
            if (bout != null) {  
                try{ bout.close(); } catch (Exception e) {}  
            }  
        }  
    }  
  
    /** 
      * @Description:  
      *     压缩文件，支持将多个文件或目录压缩到同一个压缩文件中 
      * @param sourcePath 将要压缩的文件或目录的路径的集合，请使用绝对路径 
      * @param zipPath 生成压缩文件的路径，请使用绝对路径。该路不能为空，并且必须以“.zip”为结尾 
      * @param encoding 压缩编码 
      * @param comment 压缩注释 
     */  
    public static void compress(List<String> sourcePaths, String zipPath, String encoding, String comment)  
            throws FileNotFoundException, IOException {  
        // 设置压缩文件路径，默认为将要压缩的路径的父目录为压缩文件的父目录  
        if (zipPath == null || "".equals(zipPath) || !zipPath.endsWith(".zip")) {  
            throw new FileNotFoundException("必须指定一个压缩路径，而且该路径必须以'.zip'为结尾");  
        }  
        // 设置解压编码  
        if (encoding == null || "".equals(encoding)) {  
            encoding = "GBK";  
        }  
        // 要创建的压缩文件的父目录不存在，则创建  
        File zipFile = new File(zipPath);  
        if (!zipFile.getParentFile().exists()) {  
            zipFile.getParentFile().mkdirs();  
        }  
        // 创建压缩文件输出流  
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(zipPath);  
        } catch (FileNotFoundException e) {  
            if (fos != null) {  
                try{ fos.close(); } catch (Exception e1) {}  
            }  
        }  
        // 使用指定校验和创建输出流  
        CheckedOutputStream csum = new CheckedOutputStream(fos, new CRC32());  
        // 创建压缩流  
        ZipOutputStream zos = new ZipOutputStream(csum);  
        // 设置编码，支持中文  
        zos.setEncoding(encoding);  
        // 设置压缩包注释  
        zos.setComment(comment);  
        // 启用压缩  
        zos.setMethod(ZipOutputStream.DEFLATED);  
        // 设置压缩级别为最强压缩  
        zos.setLevel(Deflater.BEST_COMPRESSION);  
        // 压缩文件缓冲流  
        BufferedOutputStream bout = null;  
        try {  
            // 封装压缩流为缓冲流  
            bout = new BufferedOutputStream(zos);  
            // 迭代压缩每一个路径  
            for (int i=0,len=sourcePaths.size(); i<len; i++) {  
                // 获取每一个压缩路径  
                File sourceFile = new File(sourcePaths.get(i));  
                // 对数据源进行压缩  
                compressRecursive(zos, bout, sourceFile, sourceFile.getParent());  
            }  
        } finally {  
            if (bout != null) {  
                try{ bout.close(); } catch (Exception e) {}  
            }  
        }  
    }  
      
    /** 
      * @Description:  
      *     压缩文件时，所使用的迭代方法 
      * @param zos 压缩输出流 
      * @param bout 封装压缩输出流的缓冲流 
      * @param sourceFile 将要压缩的文件或目录的路径 
      * @param prefixDir 整个将要压缩的文件或目录的父目录，传入此值为了获取压缩条目的名称 
     */  
    private static void compressRecursive(ZipOutputStream zos, BufferedOutputStream bout,  
            File sourceFile, String prefixDir) throws IOException, FileNotFoundException {  
        // 获取压缩条目名，初始时将要压缩的文件或目录的相对路径  
        String entryName = sourceFile.getAbsolutePath().substring(prefixDir.length() + File.separator.length());  
        // 判断是文件还是目录，如果是目录，则继续迭代压缩  
        if (sourceFile.isDirectory()) {  
            // 如果是目录，则需要在目录后面加上分隔符('/')  
            //ZipEntry zipEntry = new ZipEntry(entryName + File.separator);  
            //zos.putNextEntry(zipEntry);  
            // 获取目录中的文件，然后迭代压缩  
            File[] srcFiles = sourceFile.listFiles();  
            for (int i = 0; i < srcFiles.length; i++) {  
                // 压缩  
                compressRecursive(zos, bout, srcFiles[i], prefixDir);  
            }  
        } else {  
            // 开始写入新的ZIP文件条目并将流定位到条目数据的开始处  
            ZipEntry zipEntry = new ZipEntry(entryName);  
            // 向压缩流中写入一个新的条目  
            zos.putNextEntry(zipEntry);  
            // 读取将要压缩的文件的输入流  
            BufferedInputStream bin = null;  
            try{  
                // 获取输入流读取文件  
                bin = new BufferedInputStream(new FileInputStream(sourceFile));  
                // 读取文件，并写入压缩流  
                byte[] buffer = new byte[1024];  
                int readCount = -1;  
                while ((readCount = bin.read(buffer)) != -1) {  
                    bout.write(buffer, 0, readCount);  
                }  
                // 注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新，不然可能有的内容就会存入到后面条目中去了  
                bout.flush();  
                // 关闭当前ZIP条目并定位流以写入下一个条目  
                zos.closeEntry();  
            } finally {  
                if (bin != null) {  
                    try { bin.close(); } catch (IOException e) {}  
                }  
            }  
        }  
    }  
      
    /** 
      * @Description:  
      *     解压文件 
      * @param zipPath 被压缩文件，请使用绝对路径 
      * @param targetPath 解压路径，解压后的文件将会放入此目录中，请使用绝对路径 
      *         默认为压缩文件的路径的父目录为解压路径 
      * @param encoding 解压编码 
     */  
    public static void decompress(String zipPath, String targetPath, String encoding)  
            throws FileNotFoundException, ZipException, IOException {  
        // 获取解缩文件  
        File file = new File(zipPath);  
        if (!file.isFile()) {  
            throw new FileNotFoundException("要解压的文件不存在");  
        }  
        // 设置解压路径  
        if (targetPath == null || "".equals(targetPath)) {  
            targetPath = file.getParent();  
        }  
        // 设置解压编码  
        if (encoding == null || "".equals(encoding)) {  
            encoding = "GBK";  
        }  
        // 实例化ZipFile对象  
        ZipFile zipFile = new ZipFile(file, encoding);  
        // 获取ZipFile中的条目  
        Enumeration<ZipEntry> files = zipFile.getEntries();  
        // 迭代中的每一个条目  
        ZipEntry entry = null;  
        // 解压后的文件  
        File outFile = null;  
        // 读取压缩文件的输入流  
        BufferedInputStream bin = null;  
        // 写入解压后文件的输出流  
        BufferedOutputStream bout = null;  
        while (files.hasMoreElements()) {  
            // 获取解压条目  
            entry = files.nextElement();  
            // 实例化解压后文件对象  
            outFile = new File(targetPath + File.separator + entry.getName());  
            // 如果条目为目录，则跳向下一个  
            if (entry.getName().endsWith(File.separator)) {  
                outFile.mkdirs();  
                continue;  
            }  
            // 创建目录  
            if (!outFile.getParentFile().exists()) {  
                outFile.getParentFile().mkdirs();  
            }  
            // 创建新文件  
            outFile.createNewFile();  
            // 如果不可写，则跳向下一个条目  
            if (!outFile.canWrite()) {  
                continue;  
            }  
            try {  
                // 获取读取条目的输入流  
                bin = new BufferedInputStream(zipFile.getInputStream(entry));  
                // 获取解压后文件的输出流  
                bout = new BufferedOutputStream(new FileOutputStream(outFile));  
                // 读取条目，并写入解压后文件  
                byte[] buffer = new byte[1024];  
                int readCount = -1;  
                while ((readCount = bin.read(buffer)) != -1) {  
                    bout.write(buffer, 0, readCount);  
                }  
            } finally {  
                try {  
                    bin.close();  
                    bout.flush();  
                    bout.close();  
                } catch (Exception e) {}  
            }  
        }  
    }  
      
    public static void main(String[] args) throws Exception{  
        //compressTest();  
        //compressTest2();  
        //decompressTest();  
    }  
      
    public static void compressTest() throws Exception {  
        String sourcePath = "E:" + File.separator + "文档" + File.separator + "音乐";  
        String zipPath = "E:" + File.separator + "我的音乐.zip";  
        String comment = "音乐压缩文件";  
        compress(sourcePath, zipPath, "GBK", comment);  
    }  
      
    public static void decompressTest() throws Exception {  
        String zipPath = "E:" + File.separator + "我的音乐.zip";  
        String targetPath = "E:" + File.separator + "temp";  
        decompress(zipPath, targetPath, "GBK");  
    }  
      
    public static void compressTest2() throws Exception {  
        List<String> list = new ArrayList<String>();  
        list.add("E:" + File.separator + "文档" + File.separator + "音乐");  
        list.add("E:" + File.separator + "文档" + File.separator + "视频");  
        list.add("E:" + File.separator + "文档" + File.separator + "资料");  
        list.add("E:" + File.separator + "文档" + File.separator + "书籍");  
        String zipPath = "E:" + File.separator + "我的文档压缩文件.zip";  
        String comment = "我的文档压缩文件";  
        compress(list, zipPath, "GBK", comment);  
    }  
      
}

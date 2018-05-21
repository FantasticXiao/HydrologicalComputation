package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Amos Zhao
 * @description  txt file read and write util
 */
public class FileUtil {
    /**
     * download certain file from internet 
     * @param url file url
     * @param fileName file name tobe stored
     * @param savePath file path to be saved
     * @return boolean
     */
    public boolean  downLoadFromUrl(String url,String fileName,String savePath) {
        InputStream inputStream=null;
        FileOutputStream fos=null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
            conn.setConnectTimeout(3*1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] getData = bos.toByteArray();
            bos.close();
            inputStream.close();
            File saveDir = new File(savePath);
            if(!saveDir.exists()){
                saveDir.mkdir();
            }
            File file = new File(saveDir+File.separator+fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);
            fos.close();
        }catch (IOException e1){
            e1.printStackTrace();
        }
        return true;
    }

    /**
     * convert  text file into List which store line String
     * @param filePath file name and file path
     * @return List
     */
    public  List<String> readTextFile(String filePath){
        File file =new File(filePath);
        List<String> list=new ArrayList<String>();
        InputStreamReader read= null;
        try {
            read = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader=new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = reader.readLine()) != null){
                list.add(lineTxt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * convert List which contains line String into text file
     * @param filepath save file name and file String
     * @param list String container
     * @return boolean
     */
    public boolean writeTextFile(String filepath,List<String>list){
        FileWriter fw = null;
        try {
            File f=new File(filepath);
            fw = new FileWriter(f, false);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        PrintWriter pw = new PrintWriter(fw);
        for(String s:list){
            pw.println(s);
        }
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

package Service;

import Bean.UploadIMGJson;
import com.alibaba.fastjson.JSON;

import config.Config;
import jdk.internal.org.objectweb.asm.TypeReference;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * @description 模拟form表单，上传图片
 * @param fileName -- 图片路径
 * @return 接口返回的json数据
 * 原理：模拟form表单提交：把请求头部信息和和img 信息 写入到输出流中，
 * 通过流把img写入到服务器临时目录里，然后服务器再把img移到指定的位置
 * 最后通过写入流来获取post的响应信息。
 *
 */
public class Upload {
    public  static String uploadIMG(String fileName) throws IOException {
        final String newLine = "\r\n";
        final String boundaryPrefix = "--";
        // 定义数据分隔线
       String BOUNDARY = "========7d4a6d158c932";
        URL url = new URL(Config.BASE_URL+"/upload");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        // 设置请求头参数
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        //conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" );


        OutputStream out = new DataOutputStream(conn.getOutputStream());
        // 上传文件
        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();
        sb.append(boundaryPrefix);
        sb.append(BOUNDARY);
        sb.append(newLine);
        // 文件参数,photo参数名可以随意修改
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + fileName
                + "\"" + newLine);
        sb.append("Content-Type:image/jpeg");
        // 参数头设置完以后需要两个换行，然后才是参数内容
        sb.append(newLine);
        sb.append(newLine);

        // 将参数头的数据写入到输出流中
        out.write(sb.toString().getBytes());

        // 数据输入流,用于读取文件数据
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        byte[] bufferOut = new byte[1024];
        int bytes = 0;
        // 每次读1KB数据,并且将文件数据写入到输出流中
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        // 最后添加换行
        out.write(newLine.getBytes());
        in.close();

        // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
        byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                .getBytes();

        // 写上结尾标识
        out.write(end_data);
        out.flush();
        out.close();

        // 定义BufferedReader输入流来读取URL的响应 ----读取返回的结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String line = null;
        StringBuffer stb=new StringBuffer();

        while ((line = reader.readLine()) != null) {
            stb.append(line);
        }

        String s = stb.toString();
        UploadIMGJson res = JSON.parseObject(s, UploadIMGJson.class);
        String r=res.getUploadedFilePath();

        String re = r.replace("\\", "/");
        return re;
    }

    @Test
    public void test() {
        try {

            System.out.println(uploadIMG("C:\\Users\\Lye\\Desktop\\computer.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

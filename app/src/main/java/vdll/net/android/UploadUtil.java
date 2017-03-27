package vdll.net.android;



import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;


/**
 * 
 * �ϴ�������
 * @author spring sky
 * Email:vipa1888@163.com
 * QQ:840950105
 * MyName:ʯ����
 */
public class UploadUtil
{
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 100 * 1000;   //��ʱʱ��
    private static final String CHARSET = "utf-8"; //���ñ���
    /**
     * android�ϴ��ļ���������
     * @param file  ��Ҫ�ϴ����ļ�
     * @param RequestURL  �����rul
     * @return  ������Ӧ������
     */
    public static String uploadFile(File file, String RequestURL)
    {
        String result = null;
        String  BOUNDARY =  UUID.randomUUID().toString();  //�߽��ʶ   ������
        String PREFIX = "--" , LINE_END = "\r\n"; 
        String CONTENT_TYPE = "multipart/form-data";   //��������

        try
		{
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);  //����������
            conn.setDoOutput(true); //���������
            conn.setUseCaches(false);  //������ʹ�û���
            conn.setRequestMethod("POST");  //����ʽ
            conn.setRequestProperty("Charset", CHARSET);  //���ñ���
            conn.setRequestProperty("connection", "keep-alive");   
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY); 

            if (file != null)
            {
            	OutputStream outputSteam = conn.getOutputStream();   
                DataOutputStream dos = new DataOutputStream(outputSteam);//0000000000000
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_END); 
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1)
                {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();

                int res = conn.getResponseCode();  
                if (res == 200)//请求成功
                {
                    InputStream input =  conn.getInputStream();
                    StringBuffer sb1= new StringBuffer();
                    int ss ;
                    while ((ss = input.read()) != -1)
                    {
                        sb1.append((char)ss);
                    }
                    result = sb1.toString();
					String[] vso = result.split("<br />");
					for(String s : vso)
					{
						System.out.println(s);
					}
					//System.out.println(result);
				}
				else
				{              
					System.out.println(  "请求失败");
				}
            }
        }
		catch (MalformedURLException e)
		{
            e.printStackTrace();
        }
		catch (IOException e)
		{
            e.printStackTrace();
        }
        return result;
    }
}

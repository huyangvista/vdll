package vdll.tools.fileobj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 编码 encode  解码 decode
 * @author Hocean
 *
 */
public class Base64
{
	public static String encode(String paramString) throws RuntimeException
	{
		byte[] arrayOfByte1 = paramString.getBytes();
		byte[] arrayOfByte2 = encode(arrayOfByte1);
		try
		{
			return new String(arrayOfByte2, "ASCII");
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException)
		{
			throw new RuntimeException("ASCII is not supported!", localUnsupportedEncodingException);
		}
	}

	public static String encode(String paramString1, String paramString2) throws RuntimeException
	{
		byte[] arrayOfByte1;
		try
		{
			arrayOfByte1 = paramString1.getBytes(paramString2);
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException1)
		{
			throw new RuntimeException("Unsupported charset: " + paramString2, localUnsupportedEncodingException1);
		}
		byte[] arrayOfByte2 = encode(arrayOfByte1);
		try
		{
			return new String(arrayOfByte2, "ASCII");
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException2)
		{
			throw new RuntimeException("ASCII is not supported!", localUnsupportedEncodingException2);
		}
	}

	public static String decode(String paramString) throws RuntimeException
	{
		byte[] arrayOfByte1;
		try
		{
			arrayOfByte1 = paramString.getBytes("ASCII");
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException)
		{
			throw new RuntimeException("ASCII is not supported!", localUnsupportedEncodingException);
		}
		byte[] arrayOfByte2 = decode(arrayOfByte1);
		return new String(arrayOfByte2);
	}

	public static String decode(String paramString1, String paramString2) throws RuntimeException
	{
		byte[] arrayOfByte1;
		try
		{
			arrayOfByte1 = paramString1.getBytes("ASCII");
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException1)
		{
			throw new RuntimeException("ASCII is not supported!", localUnsupportedEncodingException1);
		}
		byte[] arrayOfByte2 = decode(arrayOfByte1);
		try
		{
			return new String(arrayOfByte2, paramString2);
		}
		catch (UnsupportedEncodingException localUnsupportedEncodingException2)
		{
			throw new RuntimeException("Unsupported charset: " + paramString2, localUnsupportedEncodingException2);
		}
	}

	public static byte[] encode(byte[] paramArrayOfByte) throws RuntimeException
	{
		return encode(paramArrayOfByte, 0);
	}

	public static byte[] encode(byte[] paramArrayOfByte, int paramInt) throws RuntimeException
	{
		ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try
		{
			encode(localByteArrayInputStream, localByteArrayOutputStream, paramInt);
		}
		catch (IOException localIOException)
		{
		}
		finally
		{
			try
			{
				localByteArrayInputStream.close();
			}
			catch (Throwable localThrowable3)
			{
			}
			try
			{
				localByteArrayOutputStream.close();
			}
			catch (Throwable localThrowable4)
			{
			}
		}
		return localByteArrayOutputStream.toByteArray();
	}

	public static byte[] decode(byte[] paramArrayOfByte) throws RuntimeException
	{
		ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		try
		{
			decode(localByteArrayInputStream, localByteArrayOutputStream);
		}
		catch (IOException localIOException)
		{
		}
		finally
		{
			try
			{
				localByteArrayInputStream.close();
			}
			catch (Throwable localThrowable3)
			{
			}
			try
			{
				localByteArrayOutputStream.close();
			}
			catch (Throwable localThrowable4)
			{
			}
		}
		return localByteArrayOutputStream.toByteArray();
	}

	public static void encode(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException
	{
		encode(paramInputStream, paramOutputStream, 0);
	}

	public static void encode(InputStream paramInputStream, OutputStream paramOutputStream, int paramInt) throws IOException
	{
		Base64OutputStream localBase64OutputStream = new Base64OutputStream(paramOutputStream, paramInt);
		copy(paramInputStream, localBase64OutputStream);
		localBase64OutputStream.commit();
	}

	public static void decode(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException
	{
		copy(new Base64InputStream(paramInputStream), paramOutputStream);
	}

	public static void encode(File paramFile1, File paramFile2, int paramInt) throws IOException
	{
		FileInputStream localFileInputStream = null;
		FileOutputStream localFileOutputStream = null;
		try
		{
			localFileInputStream = new FileInputStream(paramFile1);
			localFileOutputStream = new FileOutputStream(paramFile2);
			encode(localFileInputStream, localFileOutputStream, paramInt);
		}
		finally
		{
			if (localFileOutputStream != null) try
			{
				localFileOutputStream.close();
			}
			catch (Throwable localThrowable3)
			{
			}
			if (localFileInputStream != null) try
			{
				localFileInputStream.close();
			}
			catch (Throwable localThrowable4)
			{
			}
		}
	}

	public static void encode(File paramFile1, File paramFile2) throws IOException
	{
		FileInputStream localFileInputStream = null;
		FileOutputStream localFileOutputStream = null;
		try
		{
			localFileInputStream = new FileInputStream(paramFile1);
			localFileOutputStream = new FileOutputStream(paramFile2);
			encode(localFileInputStream, localFileOutputStream);
		}
		finally
		{
			if (localFileOutputStream != null) try
			{
				localFileOutputStream.close();
			}
			catch (Throwable localThrowable3)
			{
			}
			if (localFileInputStream != null) try
			{
				localFileInputStream.close();
			}
			catch (Throwable localThrowable4)
			{
			}
		}
	}

	public static void decode(File paramFile1, File paramFile2) throws IOException
	{
		FileInputStream localFileInputStream = null;
		FileOutputStream localFileOutputStream = null;
		try
		{
			localFileInputStream = new FileInputStream(paramFile1);
			localFileOutputStream = new FileOutputStream(paramFile2);
			decode(localFileInputStream, localFileOutputStream);
		}
		finally
		{
			if (localFileOutputStream != null) try
			{
				localFileOutputStream.close();
			}
			catch (Throwable localThrowable3)
			{
			}
			if (localFileInputStream != null) try
			{
				localFileInputStream.close();
			}
			catch (Throwable localThrowable4)
			{
			}
		}
	}

	private static void copy(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException
	{
		byte[] arrayOfByte = new byte[1024];
		int i = 0;
		while ((i = paramInputStream.read(arrayOfByte)) != -1)
		{
			paramOutputStream.write(arrayOfByte, 0, i);
		}
	}

}

/**
 * 解码流
 * @author Hocean
 *
 */
class Base64InputStream extends InputStream
{
	private InputStream inputStream;
	private int[] buffer;
	private int bufferCounter = 0;
	private boolean eof = false;

	public Base64InputStream(InputStream paramInputStream)
	{
		this.inputStream = paramInputStream;
	}

	//读入
	public int read() throws IOException
	{
		if ((this.buffer == null) || (this.bufferCounter == this.buffer.length))
		{
			if (this.eof) return -1;
			acquire();
			if (this.buffer.length == 0)
			{
				this.buffer = null;
				return -1;
			}
			this.bufferCounter = 0;
		}
		return this.buffer[(this.bufferCounter++)];
	}

	//解码
	private void acquire() throws IOException
	{
		int k;
		char[] arrayOfChar = new char[4];
		int i = 0;
		do
		{
			int j = this.inputStream.read();
			if (j == -1)
			{
				if (i != 0) throw new IOException("Bad base64 stream");
				this.buffer = new int[0];
				this.eof = true;
				return;
			}
			k = (char) j;
			if ((Shared.chars.indexOf(k) != -1) || (k == Shared.pad))
				arrayOfChar[(i++)] = (char) k;
			else if ((k != 13) && (k != 10)) throw new IOException("Bad base64 stream");
		} while (i < 4);
		int j = 0;
		for (i = 0; i < 4; ++i)
		{
			if (arrayOfChar[i] != Shared.pad)
			{
				if (j == 0) break;
				throw new IOException("Bad base64 stream");
			}
			if (j == 0) j = 1;
		}
		if (arrayOfChar[3] == Shared.pad)
		{
			if (this.inputStream.read() != -1) throw new IOException("Bad base64 stream");
			this.eof = true;
			if (arrayOfChar[2] == Shared.pad)
				k = 1;
			else k = 2;
		}
		else
		{
			k = 3;
		}
		int l = 0;
		for (i = 0; i < 4; ++i)
			if (arrayOfChar[i] != Shared.pad) l |= Shared.chars.indexOf(arrayOfChar[i]) << 6 * (3 - i);
		this.buffer = new int[k];
		for (i = 0; i < k; ++i)
			this.buffer[i] = (l >>> 8 * (2 - i) & 0xFF);
	}

	//关闭
	public void close() throws IOException
	{
		this.inputStream.close();
	}
}

/**
 * 编码流
 * @author Hocean
 *
 */
class Base64OutputStream extends OutputStream
{
	private OutputStream outputStream;
	private int buffer;
	private int bytecounter;
	private int linecounter;
	private int linelength;

	public Base64OutputStream(OutputStream paramOutputStream)
	{
		this(paramOutputStream, 76);
	}

	public Base64OutputStream(OutputStream paramOutputStream, int paramInt)
	{
		this.outputStream = null;
		this.buffer = 0;
		this.bytecounter = 0;
		this.linecounter = 0;
		this.linelength = 0;
		this.outputStream = paramOutputStream;
		this.linelength = paramInt;
	}

	//写出
	public void write(int paramInt) throws IOException
	{
		int i = (paramInt & 0xFF) << 16 - this.bytecounter * 8;
		this.buffer |= i;
		this.bytecounter += 1;
		if (this.bytecounter == 3) commit();
	}

	//关闭
	public void close() throws IOException
	{
		commit();
		this.outputStream.close();
	}

	//编码
	protected void commit() throws IOException
	{
		if (this.bytecounter > 0)
		{
			if ((this.linelength > 0) && (this.linecounter == this.linelength))
			{
				this.outputStream.write("\r\n".getBytes());
				this.linecounter = 0;
			}
			int i = Shared.chars.charAt(this.buffer << 8 >>> 26);
			int j = Shared.chars.charAt(this.buffer << 14 >>> 26);
			int k = (this.bytecounter < 2) ? Shared.pad : Shared.chars.charAt(this.buffer << 20 >>> 26);
			int l = (this.bytecounter < 3) ? Shared.pad : Shared.chars.charAt(this.buffer << 26 >>> 26);
			this.outputStream.write(i);
			this.outputStream.write(j);
			this.outputStream.write(k);
			this.outputStream.write(l);
			this.linecounter += 4;
			this.bytecounter = 0;
			this.buffer = 0;
		}
	}
}

/**
 * 散列值
 * @author Hocean
 *
 */
class Shared
{
	static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	static final char pad = '=';
}


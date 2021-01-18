package e3mall.fast;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import e3mall.common.utils.FastDFSClient;


public class FastDfdTest {
	
/*
 * 
	public static void main(String[] args) throws FileNotFoundException, IOException, MyException {
		//创建一个配置文件。文件名任意。内容就是tracker服务器的地址。
		//使用全局对象加载配置文件。
		ClientGlobal.init("C:\\Users\\lenovo\\Eeclipse-workspace\\e3-manage-web\\src\\main\\resources\\conf\\client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//通过TrackClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StrorageServer的引用，可以是null
		StorageServer storageServer = null;
		//创建一个StorageClient，参数需要TrackerServer和StrorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传文件。 C:\Users\lenovo\Pictures\Pictures\大学
		String[] strings = storageClient.upload_file("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\123.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
*/
	public static void main(String[] args) throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("C:\\\\Users\\\\lenovo\\\\Eeclipse-workspace\\\\e3-manage-web\\\\src\\\\main\\\\resources\\\\conf\\\\client.conf");
		String string = fastDFSClient.uploadFile("C:\\Users\\lenovo\\Pictures\\Saved Pictures\\456.jpg");
		System.out.println(string);
	}
}

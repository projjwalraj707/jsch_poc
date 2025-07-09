import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;

import java.io.ByteArrayOutputStream;

public class MyClient {
	public static void main(String[] args) throws Exception {
		String HOST = "127.0.0.1";
		int PORT = 22;
		String USERNAME = "projjwalraj707";
		String PASSWORD = "somePassword";

		JSch jsch = new JSch();
		Session session = jsch.getSession(USERNAME, HOST, PORT);
		session.setPassword(PASSWORD);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setConfig("kex", "sntrup768x25519-sha256,mlkem768x25519-sha256");
		session.connect();
		
		ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
		channelExec.setCommand("cat data.txt");
		ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
		channelExec.setOutputStream(responseStream);
		channelExec.connect();

		while (channelExec.isConnected()) {
			Thread.sleep(100);
		}

		String responseString = new String(responseStream.toByteArray());
		System.out.println(responseString);

		if (session != null) session.disconnect();
		if (channelExec != null) channelExec.disconnect();
	}
}


package is.us.timr;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Date;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

public class DavTest {

	public static void main( String[] argv ) {
		Date now = new Date();
		Format formatter = new SimpleDateFormat( "EEE, d MMM yyyy HH:mm:ss" );
		HttpClient client = new HttpClient();

		String fromSrv = "mail.sks.is";
		String prootPath = "http://mail.sks.is/Inbox";
		try {
			String fromSrvIP = InetAddress.getByName( fromSrv ).getHostAddress();
		}
		catch( UnknownHostException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NTCredentials creds = new NTCredentials( "SKS\\hugi", "Nes11kaffi", "mail.sks.is", "SKS" );
		int status = 0;
		GetMethod get = null;

		client.getState().setCredentials( new AuthScope( fromSrv, AuthScope.ANY_PORT ), creds );
		String rootPath = getRootPath( client, prootPath );
		//
		//		if (rootPath == null)
		//			rootPath = prootPath; // Path not found maybe
		//		// FBA is needed
		//		PropFindMethod propfind = new PropFindMethod(rootPath);
		//		try {
		//			propfind.setRequestEntity(new StringRequestEntity(getInboxMsg(), null,	null));
		//			status = client.executeMethod(propfind);
		//		} catch (HttpException httpe) {
		//			System.err.println("HttpException in fetchAll()):"+httpe.getMessage());
		//		} catch (IOException ioe) {
		//			System.err.println("IO error in fetchAll():"+ioe.getMessage());
		//		}
		//		if (status == 440 || status == 404) {
		//			// Try to authenticate with FBA because that's
		//			// what status 440 is supposed to mean
		//
		//			status = doFbaAuth(client,connType);
		//			if (status != 302) {
		//				// It didn't work. FBApath wrong?
		//				System.out.println("Form based authentication failed. Possibly wrong path:"
		//								+ fbapath);
		//				System.exit(1);
		//			}
		//
		//			// Let's try find rootpath again
		//
		//			rootPath = getRootPath(client, prootPath);
		//			if (rootPath == null) {
		//				System.out.println("Can't find path for user. Exiting after trying FBA\n");
		//				System.exit(1);
		//			}
		//
		//			// Let's try again with property find
		//			propfind = new PropFindMethod(rootPath);
		//			try {
		//				propfind.setRequestEntity(new StringRequestEntity(getInboxMsg(), null, null));
		//				status = client.executeMethod(propfind); // System.out.println("Status="+
		//															// status + "\n" +
		//															// propfind.getResponseBodyAsString());
		//			} catch (HttpException httpe) {
		//				System.err.println("HttpException in fetchAll()):"+httpe.getMessage());
		//			} catch (IOException ioe) {
		//				System.err.println("IO error in fetchAll():"+ioe.getMessage());
		//			}
		//		}
		//		if (debug) {
		//			System.out.println("---Inbox reply:");
		//			try {
		//				System.out.println(propfind.getResponseBodyAsString());
		//			} catch (IOException ioe) {
		//				System.err.println("IO error in fetchAll():"+ioe.getMessage());
		//			}
		//			System.out.println("---Inbox reply end");
		//		}
	}

	private static String getRootPath( HttpClient client, String prootPath ) {
		String rootPath = null;
		GetMethod get = new GetMethod( prootPath );
		get.setDoAuthentication( true );

		int status = 0;
		try {
			status = client.executeMethod( get );
		}
		catch( IOException ioe ) {
			System.out.println( "Exception in connection:" + ioe.getMessage() );
			System.out.println( "Exiting." );
			System.exit( 1 );
		}

		switch (status) {
			case 401:
				System.out.println( "Authentication failed. Exiting" );
				System.exit( 1 );
			case 404:
				System.out.println( "Exchange user path doesn't exist. Exiting!" );
				System.exit( 1 );
			default:
				System.out.println( "Status=" + status + ". Exiting!" );
				System.exit( 1 );
			case 200: // OK

		}
		String buffer;
		try {
			// System.out.println(status + "\n" +
			// get.getResponseBodyAsString());
			BufferedReader bodyReader = new BufferedReader( new InputStreamReader( get.getResponseBodyAsStream() ) );
			while( (buffer = bodyReader.readLine()) != null ) {
				if( buffer.indexOf( "<BASE" ) >= 0 ) {
					rootPath = findRootPath( buffer );
					if( rootPath != null )
						break;
				}
			}
		}
		catch( IOException ioe ) {
			System.err.println( "Exception in getRootPath" );
			ioe.printStackTrace();
		}

		return rootPath;
	}

	static String findRootPath( String message ) {
		String rootpath = null;
		// Let's try to find <base> where correct root path can be
		// found
		int start = message.indexOf( "<BASE" );
		if( start >= 0 ) {
			// find start of url
			int urlstart = message.indexOf( '"', start ) + 1;
			int urlstop = message.indexOf( '"', urlstart );
			if( urlstart < urlstop )
				rootpath = message.substring( urlstart, urlstop );

		}
		return rootpath;
	}
}

package is.us.timr;

import java.util.Properties;

import javax.mail.*;

public class ImapTest {

	public static void main( String[] argv ) {

		Properties props = System.getProperties();
		props.setProperty( "mail.imap.host", "postur.us.is" );
		props.setProperty( "mail.imap.port", "993" );
		props.setProperty( "mail.imap.connectiontimeout", "5000" );
		props.setProperty( "mail.imap.timeout", "5000" );

		try {
			javax.mail.Session session = javax.mail.Session.getDefaultInstance( props, null );
			URLName urlName = new URLName( "imap://hugi:Nes11kaffi@postur.us.is" );
			Store store = session.getStore( urlName );
			if( !store.isConnected() ) {
				store.connect();
			}
		}
		catch( NoSuchProviderException e ) {
			e.printStackTrace();
			System.exit( 1 );
		}
		catch( MessagingException e ) {
			e.printStackTrace();
			System.exit( 2 );
		}

		//		Properties props = System.getProperties();
		//		props.setProperty( "mail.store.protocol", "imaps" );
		//		props.setProperty( "mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
		//		props.setProperty( "mail.imap.socketFactory.fallback", "false" );
		//
		//		try {
		//			javax.mail.Session session = javax.mail.Session.getDefaultInstance( props, null );
		//			Store store = session.getStore( "imaps" );
		//			store.connect( "postur.us.is", "hugi", "Nes11kaffi" );
		//		}
		//		catch( NoSuchProviderException e ) {
		//			e.printStackTrace();
		//			System.exit( 1 );
		//		}
		//		catch( MessagingException e ) {
		//			e.printStackTrace();
		//			System.exit( 2 );
		//		}
	}
}

package is.us.timr;

import java.io.IOException;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;

public class PropFindMethod extends EntityEnclosingMethod {

	//private String body;

	public PropFindMethod( String path ) {
		super( path );
	}

	public String getName() {
		return "PROPFIND";
	}

	public void addRequestHeaders( HttpState state, HttpConnection conn ) throws IOException, HttpException {

		if( getRequestHeader( "Content-Type" ) == null )
			super.setRequestHeader( "Content-Type", "text/xml" );
		super.addRequestHeaders( state, conn );
		super.setRequestHeader( "Depth", "0" );
		super.setRequestHeader( "Brief", "t" );
	}
}

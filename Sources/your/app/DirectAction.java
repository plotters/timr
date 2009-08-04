package your.app;

import your.app.components.Main;

import com.webobjects.appserver.*;

import er.extensions.appserver.ERXDirectAction;

/**
 * @author Hugi Thordarson
 */

public class DirectAction extends ERXDirectAction {
	public DirectAction( WORequest request ) {
		super( request );
	}

	@Override
	public WOActionResults defaultAction() {
		return pageWithName( Main.class.getName() );
	}
}

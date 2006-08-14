package org.authorsite.email;

import java.util.List;
import java.util.ArrayList;

public class MessagePartContainer extends AbstractEmailPart {

	private List<AbstractEmailPart> children = new ArrayList<AbstractEmailPart>();
	
	public MessagePartContainer() {
		super();
	}
	
	public void addChildPart(AbstractEmailPart part) {
		assert part != null;
		if ( ! children.contains(part)) {
			children.add(part);
		}
	}
}

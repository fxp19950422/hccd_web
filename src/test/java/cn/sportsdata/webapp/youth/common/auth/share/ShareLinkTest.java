package cn.sportsdata.webapp.youth.common.auth.share;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;

public class ShareLinkTest {

	@Test
	public void testToJson() {
		ShareLink shareLink = new ShareLink("utraining/share_plan");
		shareLink.addParameter("orgId", String.valueOf(1));
		shareLink.addParameter("utrainingId", String.valueOf(2));
		try {
			System.out.print(shareLink.toJson());
		} catch (SoccerProException e) {
			e.printStackTrace();
			fail();
		}
	}

}

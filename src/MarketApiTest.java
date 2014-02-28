import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import net.arnx.jsonic.JSON;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.Comment;
import com.gc.android.market.api.model.Market.CommentsRequest;
import com.gc.android.market.api.model.Market.CommentsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;

class MarketApiTest {

	MarketSession session;

	public static void main(String[] args) {
		// デフォルト10件
		int limit = 10;
		String appId = "";
		if (args.length < 1) {
			System.err.println("appIdを指定してください");
			return;
		}
		
		if (args[0] == null || args[0].length() < 1) {
			System.err.println("appIdを指定してください");
			return;
		}

		if (args.length == 2) {
			try {
				limit = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("limitを数字で指定してください");
				return;
			}
		}

		MarketApiTest marketApi = new MarketApiTest();
		marketApi.test(appId, limit);
	}

	public void test(String appId, int limit) {
		session = new MarketSession();
		session.login(getId(), getPassword());
		// session.getContext().setAndroidId("jp.ameba");

		int startIndex = 0;
		final List<Review> reviewList = new ArrayList<Review>();

		while (startIndex < limit) {
			getReview(appId, limit, startIndex, reviewList);
			startIndex += 10;
		}

		System.out.println(JSON.encode(reviewList));
	}

	private void getReview(final String appId, final int limit, final int startIndex,
			final List<Review> reviewList) {
		CommentsRequest commentsRequest = CommentsRequest.newBuilder()
				.setAppId("jp.ameba").setStartIndex(startIndex)
				.setEntriesCount(10).build();

		session.append(commentsRequest, new Callback<CommentsResponse>() {
			@Override
			public void onResult(ResponseContext context,
					CommentsResponse response) {

				for (Comment comment : response.getCommentsList()) {
					if (reviewList.size() >= limit) {
						break;
					}
					Review review = new Review();
					review.authorName = comment.getAuthorName();
					review.text = comment.getText();
					review.creationTime = comment.getCreationTime();
					review.rating = comment.getRating();
					reviewList.add(review);
				}
			}
		});
		session.flush();
	}

	private static ResourceBundle getResource() {
		return ResourceBundle.getBundle("setting");
	}

	public static String getId() {
		return getResource().getString("id");
	}

	public static String getPassword() {
		return getResource().getString("password");
	}
}

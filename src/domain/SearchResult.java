package domain;

public class SearchResult {
	private int docid;
	private String title;
	private String url;
	
	public SearchResult(int docid, String title, String url) {
		this.docid = docid;
		this.title = title;
		this.url = url;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}

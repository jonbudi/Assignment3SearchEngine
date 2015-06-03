package domain;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FileDumpObject {

	/** all invalid file extensions will be ignored by the crawler **/
	private static final Pattern INVALIDEXTENSIONS = Pattern.compile(".*\\.(css|js|bmp|gif|jpe?g|jpg|ico"
			+ "|png|tiff?|tiff|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|mkv|ogg|ogv|pdf"
			+ "|ps|eps|tex|ppt|pptx|doc|docx|xls|xlsx|names|data|dat|exe|bz2|tar|msi|bin|7z"
			+ "|psd|dmg|iso|epub|dll|cnf|tgz|sha1|thmx|mso|arff|rtf|jar|csv|rm|smil|wmv|swf|wma|zip|rar|gz"
			+ "|java|py|c|cc|cpp|h|r|pde|txt|dirs|pps|dat|lif|rle|pov|z|sql|dtd|eml|start|xml|arff)$");

	/** accept urls only from this domain **/
	private static final String VALIDDOMAIN = "ics.uci.edu";

	private String text;
	private String url;
	private String title;
	private String html;
	private int id;

	public FileDumpObject(JSONObject json) {
		this.text = json.get("text").toString();
		this.url = json.get("_id").toString();
		this.title = json.get("title").toString();
		this.html = json.get("html").toString();
		this.id = (int) Double.parseDouble(json.get("id").toString());
	}

	/** returns true if FDO is valid **/
	public boolean isValid() {
		return getText() != null && !getText().isEmpty();
	}

	public Set<String> getLinks() {
		Set<String> links = new TreeSet<String>();

		Document doc = Jsoup.parse(html);
		String href;
		// get all href elements
		for (Element e : doc.select("a")) {
			// get relative url path 
			e.setBaseUri(url);
			href = e.attr("abs:href");
			
			// remove anything beyond "?" or "#"
			if (href.contains("?")) {
				href = href.substring(0, href.indexOf("?"));
			}
			if (href.contains("#")) {
				href = href.substring(0, href.indexOf("#"));
			}
			
			href = href.replace("../", "");
			
			// Ignore the url if it has an extension that matches our defined set of extensions.
			if (href.contains(VALIDDOMAIN) && !INVALIDEXTENSIONS.matcher(href).matches()) {
				links.add(href);
			}
		}

		return links;
	}

	@Override
	public String toString() {
		return this.text + "\n" + this.url + "\n" + this.title + "\n" + this.html + "\n" + this.id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

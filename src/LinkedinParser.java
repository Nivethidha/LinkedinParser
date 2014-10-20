import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 
 * author admin
 *
 */
public class LinkedInParser {
	public static void main(String[] args) {
		LinkedInParser linkedInParser = new LinkedInParser();
		try {
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("/home/serendio/LinkedInContent.txt"));
			//BufferedWriter bw = null;
			List<String> linkedInUrls = linkedInParser.getLinkedInUrls();
			for(String url: linkedInUrls){
				String content = linkedInParser.getContent(url);
				/*String title = content.split("\\|")[0].trim();
				System.err.println(title);
				bw = new BufferedWriter(new FileWriter("E:\\Jsoup\\"+title+".txt"));*/
				bw.write(content);
				/*bw.flush();
				bw.close();*/
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<String> getLinkedInUrls() throws IOException {
		List<String> urlList = new ArrayList<String>();
		FileInputStream fin = new FileInputStream("/home/serendio/LinkedinUrls.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fin));
		String sCurrentLine = null;
		while ((sCurrentLine = br.readLine()) != null) {
			if(sCurrentLine.contains("linkedin.com"))
			urlList.add(sCurrentLine);
		}
		br.close();
		return urlList;
	}
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private String getContent(String url) throws IOException {
	
		if(!url.contains(":"))
			url = "https://"+url;
		
		Document doc = Jsoup.connect(url).get();

		// get page title
		String title = doc.title();
		String[] title1 = new String[] { title };
		List<String> strs = Arrays.asList(title1);
		StringBuffer sbContent = new StringBuffer();
		for (int index = 0; index < strs.size(); index++) {
			sbContent.append(strs.get(index));

		}
		sbContent.append("\n");
		String text = doc.text();
		String[] text1 = new String[] { text };
		List<String> strs1 = Arrays.asList(text1);
		for (int index = 0; index < strs1.size(); index++) {
			sbContent.append(strs1.get(index));

		}
		sbContent.append("\n");

		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String s = link.toString();
			String[] s1 = new String[] { s };

			List<String> strs2 = Arrays.asList(s1);
			for (int index = 0; index < strs2.size(); index++) {
				sbContent.append(strs2.get(index));
			}
		}
		
		sbContent.append("\n");
		return sbContent.toString();
	}

}





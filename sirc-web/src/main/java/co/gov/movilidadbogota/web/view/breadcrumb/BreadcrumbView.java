package co.gov.movilidadbogota.web.view.breadcrumb;

import java.util.ArrayList;
import java.util.List;

public class BreadcrumbView {
	
	private String link;
	private String code;
	
	public BreadcrumbView(String link, String code) {
		this.link = link;
		this.code = code;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}

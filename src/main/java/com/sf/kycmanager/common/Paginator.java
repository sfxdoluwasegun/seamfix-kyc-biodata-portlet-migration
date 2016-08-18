package com.sf.kycmanager.common;

/**
 * @author Ofoegbu Tochukwu
 * 
 */

import java.util.Collection;
import org.apache.log4j.Logger;


public abstract class Paginator<T> {
	static Logger logger = Logger.getLogger(Paginator.class);
		
	private int index = 1;
	private int count;
	String appRoot, first, last, next, previous, refresh;
	
	public Paginator(String appRoot, String first,String previous,String next,String last, String refresh){
		this.first = first;
		this.previous = previous;
		this.next = next;
		this.last = last;
		this.refresh = refresh;
		this.appRoot = appRoot;
		}
	
	public abstract Collection<T> fetch(int index, int count);

	public abstract int collectionSize();

	public abstract int pageSize();
	
	
	public String toString(){		
		return "<font style=\"font-family:verdana, tahoma, arial; font-size:8pt;\" color=\"00007d\">" +
			   "page size</font>&nbsp;<input type=\"text\" name=\"pageSize\" id=\"pageSize\" value=\""+this.pageSize()+"\" " +
			   "maxlength=\"10\" style=\"color:#00007d; font-family:verdana, tahoma, arial; font-size:8pt; " +
			   "width:40px; height:9px\" />&nbsp;" +
			   
			   "<a > <img style=\"cursor:pointer\" src=\"/"+this.appRoot+"/html/images/iconRefresh.png\""+
			   "onclick=\"javascript:paginatorSubmit('"+this.refresh+"')\" alt=\"refresh\" " +
			   "title=\"Click to refresh page\" align=\"absmiddle\" /></a>&nbsp;&nbsp;" +
			   "&nbsp;"+
		
			   "<a > <img style=\"cursor:pointer\" src=\"/"+this.appRoot+"/html/images/iconFirst.png\""+
			   "onclick=\"javascript:paginatorSubmit('"+this.first+"')\" alt=\"<<first\" " +
			   "title=\"Click to view first page\" align=\"absmiddle\" /></a>&nbsp;&nbsp;" +
			   
			   "<a> <img style=\"cursor:pointer\" src=\"/"+this.appRoot+"/html/images/iconPrevious.png\""+ 
			   "onclick=\"javascript:paginatorSubmit('"+this.previous+"')\" alt=\"<previous\"" +
			   "title=\"Click to view previous page\" align=\"absmiddle\" /></a>&nbsp; &nbsp;" +

			   "<span class=\"cpBodyLink\">"+ ((collectionSize() == 0)? 0:index+1) + "-" + (((index+count) > collectionSize())? collectionSize():(index+count)) + 
			   "&nbsp; of &nbsp;" + collectionSize() + "</span>&nbsp;&nbsp; " +
         	   
			   "<a> <img style=\"cursor:pointer\" src=\"/"+this.appRoot+"/html/images/iconNext.png\"" +
			   "onclick=\"javascript:paginatorSubmit('"+this.next+"')\" alt=\"next>\"" +
         	   "title=\"Click to view next page\" align=\"absmiddle\" /></a>&nbsp;&nbsp;" +
         	   
         	   "<a> <img style=\"cursor:pointer\" src=\"/"+this.appRoot+"/html/images/iconLast.png\"" +
			   "onclick=\"javascript:paginatorSubmit('"+this.last+"')\" alt=\"last>>\"" +
         	   "title=\"Click to view last page\" align=\"absmiddle\" /></a>&nbsp;&nbsp;";            	   
	}

	public long getCurrentIndex() {
		return index;
	}
	
	public Collection<T> first() {
		index = 0;
		count = pageSize();
		return fetch(index, count);
	}

	public Collection<T> previous() {
		index -= pageSize();
		index = (index <= 0) ? 0 : index;
		count = pageSize();
		return fetch(index, count);
	}

	public Collection<T> next() {
		if (index + pageSize() >= collectionSize()) {
			//don't do jack
		}else{
			index += pageSize();
			count = pageSize();
		}
		return fetch(index, count);
	}

	public Collection<T> last(){		
		index = (collectionSize() / pageSize()) * pageSize();
		count = collectionSize() % pageSize();
		if(count == 0){
			index = index - count;
			count = pageSize();
		}
		return fetch(index, count);
	}
	
	public Collection<T> refreshList(){
		count = pageSize();
		return fetch(index, count);
	}
	
	public long getIndex() {
		return index;
	}
	
}

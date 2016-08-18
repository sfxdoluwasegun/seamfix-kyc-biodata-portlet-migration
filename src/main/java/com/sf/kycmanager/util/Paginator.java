/**
 * This is the class that contains implementation of the display tag pagination.
 */

package com.sf.kycmanager.util;

import java.io.Serializable;
import java.util.List;

import javax.portlet.ActionRequest;

import nw.commons.NeemClazz;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

/**
 * 
 * @ModifiedBy IFIOK UDOH
 * @param <T>
 * @since Monday, September 8th, 2014.
 */
@SuppressWarnings("serial")
public class Paginator<T> extends NeemClazz implements PaginatedList, Serializable {
	
	private int fullListSize = 0;
	private int objectsPerPage = 50;
	private int pageNumber = 1;
	private int sortItemNo;
	private String searchId;
	private String sortCriterion;
	private String sortName;
	private List<T> partialList;
	private SortOrderEnum sortDirection = SortOrderEnum.ASCENDING;
	
	private int pageIndex = 0;
	

	public Paginator() {
		this(SortOrderEnum.ASCENDING, 1, 1);
	}
	
	public Paginator(int pageSize){
		this.setObjectsPerPage(pageSize);
	}
	
	public void next(){
		setPageNumber(getPageNumber() + 1);
	}

	public void prev(){
		setPageNumber(getPageNumber() - 1);
	}

	public int getFullListSize() {
		return fullListSize;
	}

	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	public int getObjectsPerPage() {
		return objectsPerPage;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		this.setPageIndex((pageNumber - 1) * objectsPerPage);
	}

	public int getSortItemNo() {
		return sortItemNo;
	}

	public void setSortItemNo(int sortItemNo) {
		this.sortItemNo = sortItemNo;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getSortCriterion() {
		return sortCriterion;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public List<T> getPartialList() {
		return partialList;
	}

	public void setPartialList(List<T> list) {
		this.partialList = list;
	}

	public SortOrderEnum getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

	public interface IRequestParameters {
		String SORT = "sort";
		String DEFAULT_SORT = "default_sort";
		String PAGE = "page";
		String ASC = "asc";
		String DESC = "desc";
		String DIRECTION = "dir";
	}


	public Paginator(ActionRequest request) {
		this(SortOrderEnum.DESCENDING, 1, 1);

		String pageNo = request.getParameter(IRequestParameters.PAGE);
		setPageNumber((pageNo != null) ? Integer.parseInt(pageNo) : 1);

		setSortDirection(IRequestParameters.DESC.equals(request
				.getParameter(IRequestParameters.DIRECTION)) ? SortOrderEnum.DESCENDING
				: SortOrderEnum.ASCENDING);
		setSortCriterion(request.getParameter(IRequestParameters.SORT));
	}

	public Paginator(SortOrderEnum sortDirection, int pageNumber, int sortItemNo) {
		setSortCriterion("");
		setFullListSize(0);
		setSortDirection(sortDirection);
		setPageNumber(pageNumber);
		setSortItemNo(sortItemNo);
		setSortName("");
	}

	@Override
	public List<T> getList() {
		return getPartialList();
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
}

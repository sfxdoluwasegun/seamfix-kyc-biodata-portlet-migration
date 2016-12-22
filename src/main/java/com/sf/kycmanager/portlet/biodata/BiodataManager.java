package com.sf.kycmanager.portlet.biodata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;
import com.sf.biocapture.pojos.SearchResult;
import com.sf.lfa.ext.core.SfPortlet;
import com.sf.lfa.msg.MessageType;

/**
 * Portlet implementation class Biodata
 * 
 * @author Stanley Obidiagha
 */
public class BiodataManager extends SfPortlet<BiodataManagerState, BiodataManagerDataService> {

	@Override
	protected void getStartupDataSet(RenderRequest req) {
		BiodataManagerState state = getState(req);
		state.setCurrentView(BiodataManagerViews.HOME);
	}

	@ProcessAction(name = "gotoHomePage")
	public void gotoHomePage(ActionRequest aReq, ActionResponse aRep) {
		BiodataManagerState state = getState(aReq);
		state.setBiodata(null);
		state.setCurrentView(BiodataManagerViews.HOME);
	}

	@ProcessAction(name = "gotoBioViewPage")
	public void gotoBioViewPage(ActionRequest aReq, ActionResponse aRep) {

		BiodataManagerDataService dataService = getDataService(aReq);
		BiodataManagerState state = getState(aReq);

		Long pk = ParamUtil.getLong(aReq, "bID", 0L);
		state.setPk(pk);

		if (pk.equals(0L)) {
			addAlert("Unable to display subscriber's information", MessageType.DANGER, aReq);
			state.setCurrentView(BiodataManagerViews.HOME);
		}

		try {
			state.setDemographicsData(dataService.getDemographicsData(pk));
			state.setCurrentView(BiodataManagerViews.BIOVIEW);
		} catch (Exception ex) {
			logger.error("Exception thrown in getting subsriber demographics: " + ex.getMessage());

			addAlert("Unable to display subscriber's information", MessageType.DANGER, aReq);
			state.setCurrentView(BiodataManagerViews.HOME);
		}

	}

	@ProcessAction(name = "searchBioData")
	public void searchBioData(ActionRequest aReq, ActionResponse aRep) {

            BiodataManagerDataService dataService = getDataService(aReq);
            BiodataManagerState state = getState(aReq);

            String phoneNumber = ParamUtil.getString(aReq, "phoneNumber", null);
            String name = ParamUtil.getString(aReq, "name", null);
            String uniqueID = ParamUtil.getString(aReq, "uniqueID", null);

            state.setPhoneNumber(phoneNumber);
            state.setName(name);
            state.setUniqueID(uniqueID);

            List<SearchResult> bioData = dataService.getTableContent(state.getPhoneNumber(), state.getName(),
                            state.getUniqueID(), state.getPageSize());

            if (bioData != null && !bioData.isEmpty()) {
                    state.setBiodata(bioData);
            } else {
                    state.setBiodata(null);
            }

            state.setCurrentView(BiodataManagerViews.HOME);
	}

	@ProcessAction(name = "resetPageSize")
	public void resetPageSize(ActionRequest aReq, ActionResponse aRes) {
		BiodataManagerState portletState = getState(aReq);
		String pageSize = ParamUtil.get(aReq, "pageSize", portletState.getPageSize() + "");
		portletState.setPageSize(Integer.valueOf(pageSize));
	}

	/**
	 * This is a special method by liferay which is used to implement downloads,
	 * ajax and its likes You override it to use it.
	 */
	@Override
	public void serveResource(ResourceRequest aReq, ResourceResponse aRes) {

		BiodataManagerDataService dataService = getDataService(aReq);
		BiodataManagerState state = getState(aReq);

		String category = ParamUtil.getString(aReq, "category");

		try {
			PrintWriter writer = aRes.getWriter();
			JSONObject object = JSONFactoryUtil.createJSONObject();

			logger.info("The category is " + category);

			if (category.equals("specialData")) {
				try {
					object.put("specialData", dataService.getSpecialDataCaptured(state.getPk()));
				} catch (Exception ex) {
					logger.error("Exception in retrieving special data:", ex);
					object.put("specialData", "N/A");
				}
			}

			if (category.equals("passport")) {
				try {
					object.put("passport", dataService.getPassport(state.getPk()));
				} catch (Exception ex) {
					logger.error("Exception in retrieving passport", ex);
					object.put("passport", "");
				}
			}

			if (category.equals("fingerprint")) {
				try {
					object.put("fingerPrintCount", dataService.getFingerPrintCount(state.getPk()));
				} catch (Exception ex) {
					logger.error("Exception in retrieving fingerprint count:", ex);
					object.put("fingerPrintCount", "0");
				}
			}

			writer.print(object);

		} catch (IOException e) {
			logger.error("Error trying to Passport and fingerprint count :", e);
		}
	}

}

//package com.sf.kycmanager.common;
//	import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.Date;
//
//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//
//import com.sf.biocapture.ServiceLocator;
//import com.sf.biocapture.main.entity.enrollmentref.EnrollmentRef;
//import com.sf.biocapture.main.exception.biocaptureexception.BioCaptureException;
//import com.sf.biocapture.main.service.biocaptureservice.BioCaptureService;
//import com.sf.kycmanager.entity.duplicatenode.DuplicateNode;
//import com.sf.kycmanager.entity.node.Node;
//import com.sf.kycmanager.exception.KYCManagerException;
//import com.sf.kycmanager.service.KycManagerService;
//
//	public class TagNumber extends HttpServlet {
//
//		private static final long serialVersionUID = 3745378918529004348L;
//		private static Logger logger = Logger.getLogger(TagNumber.class);
//		private String[] reqItems;
//		private KycManagerService kService = ServiceLocator.getInstance().getKycManagerService();
//		private BioCaptureService bService = ServiceLocator.getInstance().getBioCaptureService();
//		private Node node = null;
//
//		@Override
//		protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//			super.service(request, response);
//			ServletOutputStream out = response.getOutputStream();		
//			try {
//				 	if(verifyTag(request, response)){
////						out.write(node.getMacAddress().getBytes());
//						out.write("true".getBytes());
//						out.flush();
//				 	}
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//					e.printStackTrace();
//					out.write("false".getBytes());
//					out.flush();
//				}							
//		}
//		
//		private boolean verifyTag(HttpServletRequest request, HttpServletResponse response) throws IOException, KYCManagerException, BioCaptureException{
//			String tagRequest = request.getParameter("tagRequest");			
//			if(tagRequest != null){
//				logger.debug("---------------------- Tag verification ----------------");
//				System.out.println("request : verify " + tagRequest);
//				reqItems = tagRequest.split(",");
//				String tagName = reqItems[0];
//				String macAddress = reqItems[1];
//				String cardName = reqItems[2];
//				String creator = reqItems[3];
//				node = CustomService.getNodeByTagName(tagName);
//				if(node == null){
//					//brand new node 
//					logger.debug("------------------------------> creating a brand new node");
//					node = new Node();
//					node.setTagName(tagName.replaceAll("\\s","").toUpperCase());
//					node.setInstallationTimestamp(new Timestamp(new Date().getTime()));
//					node.setInstalledBy(creator);
//					node.setMacAddress(macAddress);
//					node.setNetworkCardName(cardName);
//					node = kService.createNode(node);
//					createEnrollmentRef(node);
//					removeNodeFromDuplicateNodeStamp(node);
//					return true;
//				}else if(node != null && node.getMacAddress().equals(null)){
//					//node exist without mac address, this will stamp this node as the main owner of the tag
//					logger.debug("------------------------------> updating and existing node");
//					node.setTagName(tagName.replaceAll("\\s","").toUpperCase());
//					node.setInstallationTimestamp(new Timestamp(new Date().getTime()));
//					node.setInstalledBy(creator);
//					node.setMacAddress(macAddress);
//					node.setNetworkCardName(cardName);
//					kService.updateNode(node);			
//					updateEnrollmentRef(node);
//					return true;
//				}else if(node != null && !node.getMacAddress().equals(null) 
//						&& !doesMatch(macAddress,node.getMacAddress())){
//					//node already exist with different mac Address, therefore it will be stamped as duplicate
//					logger.debug("------------------------------> node already exist, stamping it as duplicate.");
//					DuplicateNode alreadyStampedDuplicateNode = CustomService.getDuplicateNodeByMacAddress(macAddress);
//					if (alreadyStampedDuplicateNode == null){
//						DuplicateNode duplicateNode = new DuplicateNode();
//						duplicateNode.setTagName(tagName.replaceAll("\\s","").toUpperCase());
//						duplicateNode.setMacAddress(macAddress);
//						duplicateNode.setInstalledBy(creator);
//						duplicateNode.setIntallationTimestamp(new Timestamp(new Date().getTime()));
//						duplicateNode.setNetworkCardName(cardName);
//						kService.createDuplicateNode(duplicateNode);
//					}else{			
//						alreadyStampedDuplicateNode.setTagName(tagName.replaceAll("\\s","").toUpperCase());
//						alreadyStampedDuplicateNode.setIntallationTimestamp(new Timestamp(new Date().getTime()));
//						alreadyStampedDuplicateNode.setMacAddress(macAddress);
//						alreadyStampedDuplicateNode.setInstalledBy(creator);
//						alreadyStampedDuplicateNode.setNetworkCardName(cardName);
//						kService.updateDuplicateNode(alreadyStampedDuplicateNode);
//					}
//					return false;
//				}else if(node != null && !node.getMacAddress().equals(null) 
//						&& doesMatch(macAddress,node.getMacAddress())){
//					//node already exist with same mac Address, therefore it is already verified.
//					logger.debug("------------------------------> node already verified.");
//					return true;
//				}
//		}
//			return false;
//	}
//		private void removeNodeFromDuplicateNodeStamp(Node node) throws KYCManagerException{
//			DuplicateNode alreadyStampedDuplicateNode = CustomService.getDuplicateNodeByMacAddress(node.getMacAddress());
//			if (alreadyStampedDuplicateNode == null){
//				return;
//			}else{
//				node.setPreviousTagName(alreadyStampedDuplicateNode.getTagName());
//				kService.updateNode(node);
//				kService.deleteDuplicateNode(alreadyStampedDuplicateNode.getId());
//			}
//		}
//		private void createEnrollmentRef(Node node) throws KYCManagerException{
//			EnrollmentRef eRef;
//			if (node == null){
//				return;
//			}else{
//
//				eRef = new EnrollmentRef();
//				eRef.setName(node.getTagName());
//				eRef.setCode(node.getTagName());
//				eRef.setDateInstalled(new Timestamp(new Date().getTime()));
//				eRef.setInstalledBy(node.getInstalledBy());
//				eRef.setMacAddress(node.getMacAddress());
//				eRef.setNetworkCardName(node.getNetworkCardName());
//				bService.createEnrollmentRef(eRef);
//			}
//		}
//		private void updateEnrollmentRef(Node node) throws BioCaptureException{
//			EnrollmentRef eRef;
//			if (node == null){
//				return;
//			}else{
//				eRef = bService.getEnrollmentRefByCode(node.getTagName());
//				eRef.setMacAddress(node.getMacAddress());
//				eRef.setInstalledBy(node.getInstalledBy());
//				eRef.setNetworkCardName(node.getNetworkCardName());
//				bService.updateEnrollmentRef(eRef);
//			}
//		}
//		private boolean doesMatch(String firstString, String secondString){
//			if(firstString == null || secondString == null || firstString.equals("") || secondString.equals("")){
//				return false;
//			}
//			if(firstString.equalsIgnoreCase(secondString)){
//				return true;
//			}
//			return false;
//		}
//}
//


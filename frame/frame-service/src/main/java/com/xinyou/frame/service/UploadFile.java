package com.xinyou.frame.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xinyou.frame.domain.biz.FrameConfig;
import com.xinyou.util.Config;

@Path("/UploadFile")
@Produces({MediaType.TEXT_HTML})
public class UploadFile{
	
	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA}) 
	@Path(value = "/UploadImgAS/{width}x{height}")
	public void UploadImgAS(@PathParam("width")int width, @PathParam("height")int height, @Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse) throws Exception {
		String imgFolderName = new Config(FrameConfig.CONFIGNAME).get("ImgFilePath@path");
		String subFolderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		String fullFileName = "";
		String smallImgName = "";
		
//		@SuppressWarnings("deprecation")
//		String uploadPath = httpRequest.getRealPath("/")+imgFolderName+File.separator+subFolderName;//"uploadfiles";
		String uploadPath = imgFolderName+File.separator+subFolderName;//"uploadfiles";
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		
		httpResponse.setHeader("Charset","UTF-8");  
		httpResponse.setContentType("text/html;charset=UTF-8");  
		
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(httpRequest);
			for (int i = 0; i < items.size(); i++) {
				if(!items.get(i).isFormField()){
					FileItem item = (FileItem) items.get(i);
					String ext = item.getName();
					int extpos = ext.lastIndexOf('.');
					ext = (extpos<0)?"":ext.substring(extpos);
					
					String fileName = UUID.randomUUID().toString();
					fullFileName = fileName + ext;
					
					File dir = new File(uploadPath);
					if(!dir.exists())dir.mkdirs();
					
					item.write(new File(uploadPath, fullFileName));
					
                    smallImgName = fileName + "_"+width+"x"+height + ext;
					
					BufferedImage resizedBI = resizeImage(ImageIO.read(new File(uploadPath, fullFileName)), width, height, BufferedImage.TYPE_3BYTE_BGR);
					ImageIO.write(resizedBI, "jpg", new File(uploadPath, smallImgName));
				}
			}
		} catch (Exception e) {
			System.console().printf(e.getMessage());
		}
				
		ServletOutputStream out = null;
	    
		out = httpResponse.getOutputStream();
		out.print("/"+subFolderName+"/"+smallImgName);
		out.flush();  
		out.close();
	}
 
	private static BufferedImage resizeImage(BufferedImage originalImage,int width,int height, int type) {

		BufferedImage resizedImage = new BufferedImage( width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		
//		g.setComposite(AlphaComposite.Src);
//		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}

	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA}) 
	@Path(value = "/UploadImg")
	public void UploadImg(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse) throws Exception
	{
		String dirName = "";
		String fileName = "";
		String tmp = "";
		
		//@SuppressWarnings("deprecation")
		//String uploaddir = httpRequest.getRealPath("/")+readConfig.getImgFilePath();//"uploadfiles";
		String uploaddir = new Config(FrameConfig.CONFIGNAME).get("ImgFilePath@path");//"uploadfiles";
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");
		
		httpResponse.setHeader("Charset","UTF-8");  
		httpResponse.setContentType("text/html;charset=UTF-8");  
		
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(httpRequest);
			for (int i = 0; i < items.size(); i++) {
				if(!items.get(i).isFormField()){
					FileItem item = (FileItem) items.get(i);
					String ext = item.getName();
					int extpos = ext.lastIndexOf('.');
					ext = (extpos<0)?"":ext.substring(extpos);
					
					dirName = new SimpleDateFormat("yyyyMMdd").format(new Date());
					tmp = dirName+File.separator;
					File dir = new File(uploaddir,tmp);
					
					if(!dir.exists())dir.mkdirs();
					fileName = UUID.randomUUID().toString()+ext;
					tmp += fileName;
					item.write(new File(uploaddir, tmp));
				}
			}
		} catch (Exception e) {
			System.console().printf(e.getMessage());
		}
				
		ServletOutputStream out = null;
	    
		out = httpResponse.getOutputStream();
//		out.print(readConfig.getBaseURL()+readConfig.getImgFilePath()+"/"+dirName+"/"+fileName);
		out.print("/"+dirName+"/"+fileName);
		out.flush();  
		out.close();  
	}
	
	@POST
	@Consumes({MediaType.MULTIPART_FORM_DATA}) 
	@Path(value = "/UploadTempFile")
	public void UploadTempFile(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse) throws Exception
	{
		String dirName = "";
		String fileName = "";
		String tmp = "";
		
		String uploaddir = new Config(FrameConfig.CONFIGNAME).get("TempFilePath@path");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//设置内存缓冲区，超过后写入临时文件
		//factory.setSizeThreshold(10240000);
		//设置临时文件存储位置
		//factory.setRepository(new File(uploaddir));
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设置单个文件的最大上传值
		//upload.setFileSizeMax(102400000);
		//设置整个request的最大值
		//upload.setSizeMax(102400000);
		upload.setHeaderEncoding("utf-8");
		
		httpResponse.setHeader("Charset","UTF-8");  
		httpResponse.setContentType("text/html;charset=UTF-8");  
		
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(httpRequest);
			for (int i = 0; i < items.size(); i++) {
				if(!items.get(i).isFormField()){
					FileItem item = (FileItem) items.get(i);
					String ext = item.getName();
					int extpos = ext.lastIndexOf('.');
					ext = (extpos<0)?"":ext.substring(extpos);
					
					dirName = new SimpleDateFormat("yyyyMMdd").format(new Date());
					tmp = dirName+File.separator;
					File dir = new File(uploaddir,tmp);
					
					if(!dir.exists())dir.mkdirs();
					fileName = UUID.randomUUID().toString()+ext;
					tmp += fileName;
					item.write(new File(uploaddir, tmp));
				}
			}
		} catch (Exception e) {
			System.console().printf(e.getMessage());
		}
				
		ServletOutputStream out = null;
	    
		out = httpResponse.getOutputStream();
		out.print("/"+dirName+"/"+fileName);
		out.flush();  
		out.close();  
	}
	
	@GET
	@Consumes({MediaType.TEXT_HTML}) 
    @Path(value = "/Login/{username}/{password}")
	public String Login(@PathParam("username")String username, @PathParam("password")String password)
    {
        return "Hello";
    }
}


package org.java.web;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文档下载
 */
@Controller
@RequestMapping(value = "/")
public class PluploadController {

	private static final int BUFFER_SIZE = 100 * 1024;  //代表将被加入缓冲器的元素的最大数 （单位字节）
	private static final Logger logger = Logger.getLogger(PluploadController.class);

	/**
	 * 使用plupload上传文件
	 * @param file		文件对象
	 * @param name		文件名称
	 * @param chunk		数据块序号  从浏览器请求头中获得 默认值为0
	 * @param chunks	数据块总数  从浏览器请求头中获得  默认根据文件大小，自动分配大小
	 * @return
	 */
	@ResponseBody
	@PostMapping("/plupload")
	public String plupload(@RequestParam MultipartFile file, HttpServletRequest request, HttpSession session) {
		try {
			String name = request.getParameter("name");
			Integer chunk = 0, chunks = 0;
			if(null != request.getParameter("chunk") && !request.getParameter("chunk").equals("")){
				chunk = Integer.valueOf(request.getParameter("chunk"));
			}
			if(null != request.getParameter("chunks") && !request.getParameter("chunks").equals("")){
				chunks = Integer.valueOf(request.getParameter("chunks"));
			}
			logger.info("chunk:[" + chunk + "] chunks:[" + chunks + "]");
			//检查文件目录，不存在则创建
			String relativePath = "plupload\\files\\";
			//获得完整路径
			String realPath = "D:\\project\\oa\\pathplupload\\";
			File folder = new File(realPath + relativePath);
			//判断文件权是否存在 就是如果存在知的话返回“true”，否则道就是返回“版false”。举例
			if (!folder.exists()) {
				folder.mkdirs();
			}
			session.setAttribute("realPath",realPath+relativePath);
			session.setAttribute("name",name);
			//目标文件 
			File destFile = new File(folder, name);
			//文件已存在删除旧文件（上传了同名的文件） 
	        if (chunk == 0 && destFile.exists()) {  
	        	destFile.delete();  
	        	destFile = new File(folder, name);
	        }
	        //合成文件
	        appendFile(file.getInputStream(), destFile);  
	        if (chunk == chunks - 1) {  
	            logger.info("上传完成");
	        }else {
	        	logger.info("还剩["+(chunks-1-chunk)+"]个块文件");
	        }
			
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return "";
	}
	
	private void appendFile(InputStream in, File destFile) {
		OutputStream out = null;
		try {
			// plupload 配置了chunk的时候新上传的文件append到文件末尾
			if (destFile.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(destFile, true), BUFFER_SIZE); 
			} else {
				out = new BufferedOutputStream(new FileOutputStream(destFile),BUFFER_SIZE);
			}
			in = new BufferedInputStream(in, BUFFER_SIZE);
			
			int len = 0;
			byte[] buffer = new byte[BUFFER_SIZE];			
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {		
			try {
				if (null != in) {
					in.close();
				}
				if(null != out){
					out.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}


	@RequestMapping("download")
	public void download(HttpSession session, HttpServletResponse response) throws IOException {
		String realPath = session.getAttribute("realPath").toString();
		String fname = session.getAttribute("name").toString();
		//如果下载的文件名称中包含中文，在面板中显示时，将会产生乱码,只需要重新指定编码即可
//		String fileName = URLEncoder.encode(fname, "utf-8");

		String path=realPath+fname;
		String fileName = URLEncoder.encode(fname, "utf-8");
		//设置ContentType字段值
		response.setContentType("text/html;charset=utf-8");

		//显示下载的面板
		response.setContentType("application/ms-download");
		response.addHeader("Content-Disposition", "attachment;filename="+fileName);
		//通知文件流读取文件
		InputStream in = new FileInputStream(path);
		//获取response对象的输出流
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		//循环取出流中的数据
		while((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();
	}
}

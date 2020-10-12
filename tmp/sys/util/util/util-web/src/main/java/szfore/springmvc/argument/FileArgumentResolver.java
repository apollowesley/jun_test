package szfore.springmvc.argument;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import jodd.io.FileNameUtil;
import org.springframework.core.MethodParameter;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileArgumentResolver implements WebArgumentResolver {

	public Object resolveArgument(MethodParameter parameter,
			NativeWebRequest webRequest) throws Exception {
		
		FileVariable fileVariableAnnot = parameter.getParameterAnnotation(FileVariable.class);
		
		if(fileVariableAnnot != null)
		{
			String attrName = fileVariableAnnot.value();
			if(attrName == null || attrName.equals(""))
			{
				throw new Exception("@FileVariable(value)注解，value不能留空");
			}
			
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			
			if(request instanceof MultipartHttpServletRequest)
			{
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
				MultiValueMap<String, MultipartFile> fileMap = multiRequest.getMultiFileMap();
				MultipartFile multipartFile = fileMap.getFirst(attrName);
				if(multipartFile.isEmpty())
				{
					return null;
				}
				
				return upload(multiRequest, multipartFile);
			}
			throw new Exception("提交的请求里没有包含文件");
		}
		return WebArgumentResolver.UNRESOLVED;
	}

	private File upload(HttpServletRequest request, MultipartFile multipartFile) 
	{
		String diskServerPath = request.getSession().getServletContext().getRealPath("/");
		File tempFolder = new File(diskServerPath + "tempFiles");
		if(!tempFolder.exists())
		{
			tempFolder.mkdir();
		}
		
		String saveName = tempFolder.getAbsolutePath() + "/" +UUID.randomUUID().toString() + "." + FileNameUtil.getExtension(multipartFile.getOriginalFilename());
		File saveFile = new File(saveName);
		
		try {
			byte[] bytes = multipartFile.getBytes();
			FileCopyUtils.copy(bytes, saveFile);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return saveFile;
	}	
}

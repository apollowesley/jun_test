package tangjl.async;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(urlPatterns = { "/DownloadServlet" }, asyncSupported = true)
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resourcePath = request.getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(resourcePath);
		String zipName = System.currentTimeMillis() + ".zip";
		String zipPath = file.getParent() + File.separator + zipName;
		System.out.println(zipPath);
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + zipName + "\"");
        File downloadFile = new ZipCompressor().compress(zipPath, resourcePath, false);
		FileUtils.copyFile(downloadFile, response.getOutputStream());
		response.getOutputStream().close();
		downloadFile.delete();
		System.out.println("下载完成");
	}

}

package com.shinsegae_inc.sitims.common.web.view;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.shinsegae_inc.sitims.common.util.CmFunction;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

@SuppressWarnings("rawtypes")
@Component("jasperReportView")
public class JasperReportView extends AbstractView {
	@Autowired
	private transient ResourceLoader	resourceLoader;

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OutputStream	out			= response.getOutputStream();
		HttpSession		session		= CmFunction.getCurrentRequest().getSession();
		try {
			String	jrxmlPath		= (String)model.get("jrxmlPath");
			String	downloadType	= (String)model.get("downloadType");
			
			response.setContentType("application/x-download");
			String filename = (String)session.getAttribute("S_WORD_FILE");

			String path = resourceLoader.getResource("classpath:"+jrxmlPath).getURI().getPath();

			JasperReport jasperReport = JasperCompileManager.compileReport(path);

		// Parameters for report
			JRDataSource	dataSource	= (JRDataSource)model.get("datasource");
		//	JasperPrint		jasperPrint	= JasperFillManager.fillReport(jasperReport, null, dataSource);
			JasperPrint		jasperPrint	= JasperFillManager.fillReport(jasperReport, model, dataSource);

			if ("pdf".equals(downloadType)) {
				response.setHeader("Content-Disposition", String.format("attachment; filename=\""+new String(filename.getBytes("EUC-KR"),"8859_1") +"."+downloadType+"\""));
				JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			} else if ("docx".equals(downloadType)) {
				response.setHeader("Content-Disposition", String.format("attachment; filename=\""+new String(filename.getBytes("EUC-KR"),"8859_1") +"."+downloadType+"\""));
				JRDocxExporter	exporter	= new JRDocxExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
				
//				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, new String(filename.getBytes("EUC-KR"),"8859_1") +"."+downloadType);

				exporter.exportReport();
			}
		}finally {
			if(out != null) {
				out.flush();
				out.close();
			}
		}
	}
}



/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.30
 * Generated at: 2013-07-11 14:46:38 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class joint_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/WEB-INF/struts-html.tld", Long.valueOf(1115383660000L));
    _jspx_dependants.put("/WEB-INF/struts-bean.tld", Long.valueOf(1115383660000L));
    _jspx_dependants.put("/WEB-INF/struts-logic.tld", Long.valueOf(1115383660000L));
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"><!-- InstanceBegin template=\"/Templates/template.dwt\" codeOutsideHTMLIsLocked=\"false\" -->\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"bg.css\"/>\r\n");
      out.write("<link rel=\"shortcut icon\" href=\"favicon.ico\" />\r\n");
      out.write("<!-- InstanceBeginEditable name=\"doctitle\" -->\r\n");
      out.write("<title>.: ReMoto :.</title>\r\n");
      out.write("<!-- InstanceEndEditable -->\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write(".first {\r\n");
      out.write("\tmargin-right: auto;\r\n");
      out.write("\tmargin-left: auto;\r\n");
      out.write("\twidth: 1020px;\r\n");
      out.write("\tbackground-color: #666;\r\n");
      out.write("\tmin-height: 620px;\r\n");
      out.write("\tmin-width: 800px;\r\n");
      out.write("\tpadding-top: 5px;\r\n");
      out.write("\tpadding-right: 2px;\r\n");
      out.write("\tpadding-bottom: 10px;\r\n");
      out.write("\tpadding-left: 2px;\r\n");
      out.write("\tborder-top-width: 0px;\r\n");
      out.write("\tborder-right-width: 0px;\r\n");
      out.write("\tborder-bottom-width: 0px;\r\n");
      out.write("\tborder-left-width: 0px;\r\n");
      out.write("\tborder-top-style: none;\r\n");
      out.write("\tborder-right-style: none;\r\n");
      out.write("\tborder-bottom-style: none;\r\n");
      out.write("\tborder-left-style: none;\r\n");
      out.write("\theight: auto;\r\n");
      out.write("}\r\n");
      out.write(".banner {\r\n");
      out.write("\theight: 154px;\r\n");
      out.write("\twidth: 1024px;\r\n");
      out.write("\tmargin-right: auto;\r\n");
      out.write("\tmargin-left: auto;\r\n");
      out.write("\tmax-width: 1024px;\r\n");
      out.write("\tborder-top-width: 2px;\r\n");
      out.write("\tborder-right-width: 2px;\r\n");
      out.write("\tborder-bottom-width: 0px;\r\n");
      out.write("\tborder-left-width: 2px;\r\n");
      out.write("\tborder-top-style: none;\r\n");
      out.write("\tborder-right-style: none;\r\n");
      out.write("\tborder-bottom-style: none;\r\n");
      out.write("\tborder-left-style: none;\r\n");
      out.write("\tbackground-image: url(remoto_top.png);\r\n");
      out.write("}\r\n");
      out.write(".second {\r\n");
      out.write("\tbackground-color: #154151;\r\n");
      out.write("\twidth: 98%;\r\n");
      out.write("\tmargin-right: auto;\r\n");
      out.write("\tmargin-left: auto;\r\n");
      out.write("\tborder-left-width: medium;\r\n");
      out.write("\tborder-left-style: solid;\r\n");
      out.write("\tborder-left-color: #59ACBB;\r\n");
      out.write("\tpadding: 0px;\r\n");
      out.write("\theight: 42px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#links {\r\n");
      out.write("\tfont-family: \"Times New Roman\", Times, serif;\r\n");
      out.write("\tfont-size: small;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\twidth: 1024px;\r\n");
      out.write("\tmargin-right: auto;\r\n");
      out.write("\tmargin-left: auto;\r\n");
      out.write("}\r\n");
      out.write("#footer {\r\n");
      out.write("\tfont-family: \"Times New Roman\", Times, serif;\r\n");
      out.write("\tfont-size: small;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\twidth: 1024px;\r\n");
      out.write("\tmargin-right: auto;\r\n");
      out.write("\tmargin-left: auto;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".links_banner {\r\n");
      out.write("\tpadding-top: 112px;\r\n");
      out.write("\ttext-align: right;\r\n");
      out.write("\tpadding-right: 10px;\r\n");
      out.write("\tcolor: #FFF;\r\n");
      out.write("\tfont-family: \"Times New Roman\", Times, serif;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<!-- InstanceBeginEditable name=\"head\" -->\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write(".stimulation1 {\r\n");
      out.write("\tbackground-color: #F5F9FA;\r\n");
      out.write("\theight: auto;\r\n");
      out.write("\twidth: 950px;\r\n");
      out.write("\tmargin-right: auto;\r\n");
      out.write("\tmargin-left: auto;\r\n");
      out.write("\tborder: medium solid #C0DCE2;\r\n");
      out.write("\ttext-align: center;\r\n");
      out.write("\tpadding-top: 0px;\r\n");
      out.write("\tpadding-right: 10px;\r\n");
      out.write("\tpadding-bottom: 0px;\r\n");
      out.write("\tpadding-left: 10px;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("<script>\r\n");
      out.write("\r\n");
      out.write("function change()\r\n");
      out.write("{\r\n");
      out.write("\tdocument.JointForm.task.value = \"change\";\r\n");
      out.write("\tdocument.JointForm.submit();\r\n");
      out.write("}\r\n");
      out.write("function setJointModel()\r\n");
      out.write("{\r\n");
      out.write("\tdocument.JointForm.action = 'biomechanicalElements.do';\r\n");
      out.write("\tdocument.JointForm.submit();\r\n");
      out.write("}\r\n");
      out.write("</script>\r\n");
      out.write("<link href=\"hint.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<link href=\"apply.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<!-- InstanceEndEditable -->\r\n");
      out.write("<script src=\"SpryAssets/SpryMenuBar.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<link href=\"SpryAssets/SpryMenuBarHorizontal.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("  var _gaq = _gaq || [];\r\n");
      out.write("  _gaq.push(['_setAccount', 'UA-5761986-3']);\r\n");
      out.write("  _gaq.push(['_trackPageview']);\r\n");
      out.write("\r\n");
      out.write("  (function() {\r\n");
      out.write("    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\r\n");
      out.write("    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\r\n");
      out.write("    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\r\n");
      out.write("  })();\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"banner\">\r\n");
      out.write("\r\n");
      out.write("<div class=\"links_banner\"><br />\r\n");
      out.write("Current Version: 2.2 beta</div>\r\n");
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("<div class=\"first\">\r\n");
      out.write("  <div class=\"second\">\r\n");
      out.write("  <ul id=\"MenuBar\" class=\"MenuBarHorizontal\">\r\n");
      out.write("    <li><a href=\"/remoto/overview.htm\">Overview</a></li>\r\n");
      out.write("    <li><a href=\"/remoto/general.do\">Scenarios</a>      </li>\r\n");
      out.write("<li><a class=\"MenuBarItemSubmenu\">Structure</a>\r\n");
      out.write("  <ul>\r\n");
      out.write("    <li><a href=\"/remoto/input.do\">Descending Commands</a></li>\r\n");
      out.write("    <li><a href=\"/remoto/network.do\">Neural Elements</a></li>\r\n");
      out.write("    <li><a class=\"MenuBarItemSubmenu\">Neural Parameters</a>\r\n");
      out.write("      <ul>\r\n");
      out.write("        <li><a href=\"/remoto/synapse.do\">Synapses</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/conductance.do\">Conductances</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/motoneuron.do?task=MN\">Motoneurons</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/interneuron.do?task=IN\">Interneurons</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/sensory.do?task=AF\">Sensory Fibers</a></li>\r\n");
      out.write("      </ul>\r\n");
      out.write("    </li>\r\n");
      out.write("    <li><a class=\"MenuBarItemSubmenu\">Proprioceptors</a>\r\n");
      out.write("      <ul>\r\n");
      out.write("        <li><a href=\"/remoto/muscleSpindle.do\">Muscle Spindle</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/golgiTendonOrgan.do\">Golgi Tendon Organ</a></li>\r\n");
      out.write("      </ul>\r\n");
      out.write("    </li>\r\n");
      out.write("    <li><a href=\"/remoto/biomechanicalElements.do\">Biomechanical Elements</a></li>\r\n");
      out.write("    <li><a class=\"MenuBarItemSubmenu\">Biomechanical Parameters</a>\r\n");
      out.write("      <ul>\r\n");
      out.write("        <li><a href=\"/remoto/joint.do\">Joint</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/musculotendon.do\">Musculotendon</a></li>\r\n");
      out.write("      </ul>\r\n");
      out.write("    </li>\r\n");
      out.write("    <li><a href=\"/remoto/emg.do\">Electromyogram</a></li>\r\n");
      out.write("    <li><a href=\"/remoto/miscellaneous.do\">Miscellaneous</a>      </li>\r\n");
      out.write("      </ul>\r\n");
      out.write("  </li>\r\n");
      out.write("    <li><a class=\"MenuBarItemSubmenu\">Stimulation</a>\r\n");
      out.write("      <ul>\r\n");
      out.write("        <li><a href=\"/remoto/stimulation.do\">Nerve Stimulation</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/injectedCurrent.do\">Injected Current</a></li>\r\n");
      out.write("        <li><a href=\"/remoto/biomechanicalInput.do\">Biomechanical Input</a></li>\r\n");
      out.write("      </ul>\r\n");
      out.write("    </li>\r\n");
      out.write("    <li><a href=\"/remoto/start.do\">Run</a></li>\r\n");
      out.write("    <li><a href=\"/remoto/results.do?opt=chart_img\">Results</a>      </li>\r\n");
      out.write("    <li><a href=\"/remoto/login.do\">Login</a></li>\r\n");
      out.write("  </ul>\r\n");
      out.write("</div>\r\n");
      out.write("  <!-- InstanceBeginEditable name=\"EditRegion1\" -->\r\n");
      out.write("<p>\r\n");
      out.write("<div class=\"stimulation1\">");
      if (_jspx_meth_html_005fform_005f0(_jspx_page_context))
        return;
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("</p>\r\n");
      out.write("<!-- InstanceEndEditable --></div>\r\n");
      out.write("<div id=\"links\">\r\n");
      out.write("\t\t\t<hr /><a href=\"http://www.usp.br/\" target=\"_blank\">USP</a> |\r\n");
      out.write("\t\t\t<a href=\"http://www.poli.usp.br/\" target=\"_blank\">POLI</a> |\r\n");
      out.write("<a href=\"http://www.leb.usp.br/\" target=\"_blank\">LEB</a><hr />\r\n");
      out.write("\t\t</div>\r\n");
      out.write("<div id=\"footer\">\r\n");
      out.write("\t\t\tCopyright &copy; 2012 Biomedical Engineering Laboratory. All rights \r\n");
      out.write("\t\t\treserved.</div>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var MenuBar = new Spry.Widget.MenuBar(\"MenuBar\", {imgDown:\"SpryAssets/SpryMenuBarDownHover.gif\", imgRight:\"SpryAssets/SpryMenuBarRightHover.gif\"});\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("<!-- InstanceEnd --></html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_html_005fform_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:form
    org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
    _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fform_005f0.setParent(null);
    // /jsp/joint.jsp(197,26) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fform_005f0.setMethod("post");
    // /jsp/joint.jsp(197,26) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fform_005f0.setAction("/joint");
    int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
    if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\r\n");
        out.write("\t");
        if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("\r\n");
        out.write("\t<table width=\"920\" border=\"0\">\r\n");
        out.write("\t\t<tr>\r\n");
        out.write("\t\t\t<td colspan=\"3\" width=\"515\" height=\"90\" align=\"center\" valign=\"middle\">\r\n");
        out.write("\t\t\t<p><b><font size=\"4\" face=\"Arial, Helvetica, sans-serif\">Joint Parameters</font></b></p>\r\n");
        out.write("\t\t\t</td>\r\n");
        out.write("\t\t</tr>\r\n");
        out.write("\r\n");
        out.write("<tr>\r\n");
        out.write("\t\t\t\t\t<td width=\"400\"  height=\"50\" align=\"left\" bgcolor=\"#EEF5F7\"><b>&nbsp;&nbsp;&nbsp;Joint:\r\n");
        out.write("\t\t\t</b>  ");
        if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("            </td>\r\n");
        out.write("            \r\n");
        out.write("                     <td >&nbsp;</td>\r\n");
        out.write("                     \r\n");
        out.write("         <td width=\"300\"  align=\"center\" bgcolor=\"#EEF5F7\"><a href=\"#\" onclick=\"setJointModel();\">Change Biomechanical Models</a></td>\r\n");
        out.write("        </tr>     \r\n");
        out.write("\r\n");
        out.write("\t\t<tr>\r\n");
        out.write("\t\t  <td width=\"400\"  height=\"50\" align=\"left\" bgcolor=\"#EEF5F7\"><b>&nbsp;&nbsp;&nbsp;Joint model:\r\n");
        out.write("\t\t</b> ");
        if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
          return true;
        out.write("</td></tr>\r\n");
        out.write("\t\r\n");
        out.write("\r\n");
        out.write("                    \r\n");
        out.write("         <tr><td colspan=\"3\"><table width=\"100%\" border=\"0\" bgcolor=\"#DAEAEB\">\r\n");
        out.write("            \r\n");
        out.write("            <tr>\r\n");
        out.write("            <td colspan=\"2\" height=\"56\" align=\"center\" bgcolor=\"#EEF5F7\"><b>Parameters</b></td>\r\n");
        out.write("            </tr>\r\n");
        out.write("\t\t\t<tr>\r\n");
        out.write("\t\t\t\t<td width=\"80%\" height=\"25\" valign=\"middle\" bgcolor=\"#C2DDE0\">\r\n");
        out.write("                \t&nbsp;&nbsp;&nbsp;Ankle initial angle [deg]\r\n");
        out.write("                </td>\r\n");
        out.write("                <td align=\"center\" valign=\"middle\" bgcolor=\"#EEF5F7\">\r\n");
        out.write("               \t  ");
        if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("                </td>\r\n");
        out.write("                 \r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("            <tr>\r\n");
        out.write("\t\t\t\t<td height=\"25\" valign=\"middle\" bgcolor=\"#C2DDE0\">\r\n");
        out.write("                \t&nbsp;&nbsp;&nbsp;Knee angle [deg]\r\n");
        out.write("                </td>\r\n");
        out.write("                <td align=\"center\" valign=\"middle\" bgcolor=\"#EEF5F7\">\r\n");
        out.write("               \t  ");
        if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
          return true;
        out.write("\r\n");
        out.write("                </td>\r\n");
        out.write("                 \r\n");
        out.write("\t\t\t</tr>\r\n");
        out.write("            \r\n");
        out.write("            \r\n");
        out.write("\t</table></td>\r\n");
        out.write("\t\t\t\t</tr>\r\n");
        out.write("                \r\n");
        out.write("                \r\n");
        out.write("\t\t\r\n");
        out.write("\r\n");
        out.write("\t<tr><td height=\"90\" colspan=\"3\" align=\"center\">\r\n");
        out.write("    \t<input type=\"button\" value=\"  Apply  \"\r\n");
        out.write("\t\tonClick=\"submit();\">\r\n");
        out.write("    </td></tr>\r\n");
        out.write("\t</table>\r\n");
        out.write("\r\n");
        out.write("\r\n");
        int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /jsp/joint.jsp(200,1) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fhidden_005f0.setName("JointForm");
    // /jsp/joint.jsp(200,1) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fhidden_005f0.setProperty("task");
    // /jsp/joint.jsp(200,1) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005fhidden_005f0.setValue("saveStimulus");
    int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
    if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /jsp/joint.jsp(212,9) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f0.setName("JointForm");
    // /jsp/joint.jsp(212,9) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f0.setProperty("cdJoint");
    int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
    if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f1 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
    _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /jsp/joint.jsp(222,7) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f1.setName("JointForm");
    // /jsp/joint.jsp(222,7) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_bean_005fwrite_005f1.setProperty("cdJointModel");
    int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
    if (_jspx_th_bean_005fwrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
    _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /jsp/joint.jsp(236,18) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f0.setName("JointForm");
    // /jsp/joint.jsp(236,18) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f0.setProperty("jointAngle");
    // /jsp/joint.jsp(236,18) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f0.setSize("6");
    int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
    if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
    _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
    // /jsp/joint.jsp(246,18) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f1.setName("JointForm");
    // /jsp/joint.jsp(246,18) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f1.setProperty("kneeAngle");
    // /jsp/joint.jsp(246,18) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_html_005ftext_005f1.setSize("6");
    int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
    if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fsize_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
    return false;
  }
}
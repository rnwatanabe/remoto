

<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ page import="java.util.*"%>

<%
String cdSimulation = (String)request.getAttribute( "cdSimulation" );
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template2.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="bg.css"/>
<link rel="shortcut icon" href="favicon.ico" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>.: ReMoto :.</title>

<style type="text/css">
.summary {
	background-color: #F5F9FA;
	padding: 50px;
	height: auto;
	width: 700px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
}
</style>

<!-- InstanceEndEditable -->
<style type="text/css">
.first {
	margin-right: auto;
	margin-left: auto;
	width: 1020px;
	background-color: #666;
	height: auto;
	min-height: 620px;
	min-width: 800px;
	padding-top: 5px;
	padding-right: 2px;
	padding-bottom: 10px;
	padding-left: 2px;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
}
.banner {
	height: auto;
	width: 1024px;
	margin-right: auto;
	margin-left: auto;
	max-width: 1024px;
	border-top-width: 2px;
	border-right-width: 2px;
	border-bottom-width: 0px;
	border-left-width: 2px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
}
.second {
	background-color: #154151;
	width: 98%;
	margin-right: auto;
	margin-left: auto;
	border-left-width: medium;
	border-left-style: solid;
	border-left-color: #59ACBB;
	padding: 0px;
	height: 42px;
}

#links {
	font-family: "Times New Roman", Times, serif;
	font-size: small;
	text-align: center;
	width: 1024px;
	margin-right: auto;
	margin-left: auto;
}
#footer {
	font-family: "Times New Roman", Times, serif;
	font-size: small;
	text-align: center;
	width: 1024px;
	margin-right: auto;
	margin-left: auto;
}

</style>
<!-- InstanceBeginEditable name="head" -->
<link href="hint.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-5761986-3']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

<!-- InstanceEndEditable -->

</head>

<body>
<div class="banner"><img src="remoto_top.png" width="1024" height="154" alt="ReMoto" /></div>
<div class="first"><!-- InstanceBeginEditable name="EditRegion1" -->

<p>
<div class="summary"><jsp:plugin type="applet"
	archive="AppletChartGenerator.jar, jfreechart-1.0.13.jar, jcommon-1.0.16.jar"
	codebase="../remoto" code="br.remoto.applet.AppletChartGenerator.class"
	width="700" height="500">
	<jsp:params><jsp:param name="cdSimulation"
			value="<%=cdSimulation%>" /></jsp:params>
	<jsp:fallback>
		<p> Problem in loading graphic </p>
	</jsp:fallback>
</jsp:plugin> <br />
<br />
</div>
</p>


<p>&nbsp;</p>
<p>&nbsp;</p>
<!-- InstanceEndEditable --></div>
<div id="links">
			<hr /><a href="http://www.usp.br/" target="_blank">USP</a> |
			<a href="http://www.poli.usp.br/" target="_blank">POLI</a> |
<a href="http://www.leb.usp.br/" target="_blank">LEB</a><hr />
		</div>
<div id="footer">
			Copyright &copy; 2011 Biomedical Engineering Laboratory. All rights 
			reserved.</div>

</body>
<!-- InstanceEnd --></html>

<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="bg.css"/>
<link rel="shortcut icon" href="favicon.ico" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>.: ReMoto :.</title>


<!-- InstanceEndEditable -->
<style type="text/css">
.first {
	margin-right: auto;
	margin-left: auto;
	width: 1020px;
	background-color: #666;
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
	height: auto;
}
.banner {
	height: 154px;
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
	background-image: url(remoto_top.png);
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

.links_banner {
	padding-top: 112px;
	text-align: right;
	padding-right: 10px;
	color: #FFF;
	font-family: "Times New Roman", Times, serif;
}
</style>
<!-- InstanceBeginEditable name="head" -->
<style type="text/css">
.modulation {
	background-color: #FFF;
	padding: 50px;
	height: 500px;
	width: 600px;
	margin-right: auto;
	margin-left: auto;
}
</style>

<script>

<%
	String retorno = (String)request.getParameter("retorno");
	String peak = (String)request.getParameter("peak");
%>

function goToBack()
{
	document.ModulationForm.action = '<%=retorno%>.do';
	document.ModulationForm.submit();
}

</script>

<!-- InstanceEndEditable -->
<script src="SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
<link href="SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />

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

</head>

<body>
<div class="banner">

<div class="links_banner"><br />
Current Version: 2.2 beta</div>

</div>
<div class="first">
  <div class="second">
  <ul id="MenuBar" class="MenuBarHorizontal">
    <li><a href="/remoto/overview.htm">Overview</a></li>
    <li><a href="/remoto/general.do">Scenarios</a>      </li>
<li><a class="MenuBarItemSubmenu">Structure</a>
  <ul>
    <li><a href="/remoto/input.do">Descending Commands</a></li>
    <li><a href="/remoto/network.do">Neural Elements</a></li>
    <li><a class="MenuBarItemSubmenu">Neural Parameters</a>
      <ul>
        <li><a href="/remoto/synapse.do">Synapses</a></li>
        <li><a href="/remoto/conductance.do">Conductances</a></li>
        <li><a href="/remoto/motoneuron.do?task=MN">Motoneurons</a></li>
        <li><a href="/remoto/interneuron.do?task=IN">Interneurons</a></li>
        <li><a href="/remoto/sensory.do?task=AF">Sensory Fibers</a></li>
      </ul>
    </li>
    <li><a class="MenuBarItemSubmenu">Proprioceptors</a>
      <ul>
        <li><a href="/remoto/muscleSpindle.do">Muscle Spindle</a></li>
        <li><a href="/remoto/golgiTendonOrgan.do">Golgi Tendon Organ</a></li>
      </ul>
    </li>
    <li><a href="/remoto/biomechanicalElements.do">Biomechanical Elements</a></li>
    <li><a class="MenuBarItemSubmenu">Biomechanical Parameters</a>
      <ul>
        <li><a href="/remoto/joint.do">Joint</a></li>
        <li><a href="/remoto/musculotendon.do">Musculotendon</a></li>
      </ul>
    </li>
    <li><a href="/remoto/emg.do">Electromyogram</a></li>
    <li><a href="/remoto/miscellaneous.do">Miscellaneous</a>      </li>
      </ul>
  </li>
    <li><a class="MenuBarItemSubmenu">Stimulation</a>
      <ul>
        <li><a href="/remoto/stimulation.do">Nerve Stimulation</a></li>
        <li><a href="/remoto/injectedCurrent.do">Injected Current</a></li>
        <li><a href="/remoto/biomechanicalInput.do">Biomechanical Input</a></li>
      </ul>
    </li>
    <li><a href="/remoto/start.do">Run</a></li>
    <li><a href="/remoto/results.do?opt=chart_img">Results</a>      </li>
    <li><a href="/remoto/login.do">Login</a></li>
  </ul>
</div>
  <!-- InstanceBeginEditable name="EditRegion1" -->
<p>&nbsp;</p>
<div class="modulation"><html:form method="post"
	action="/modulation">

	<html:hidden name="ModulationForm" property="task"
		value="saveModulation" />
	<html:hidden name="ModulationForm" property="retorno"
		value="<%=retorno%>" />
	<input type="hidden" name="peak" value="<%=peak%>">

	<table border="0">

		<tr>
			<td align="center"><b><font size="4"
				face="Arial, Helvetica, sans-serif">Signal modulation</font></b></td>
		</tr>

		<tr>
			<td align="center">&nbsp;</td>
		</tr>

		<tr>
			<td>

			<table border="0">

				<tr>
					<td width="60" height=30><font size="2"
						face="Arial, Helvetica, sans-serif"><b>Target</b></font></td>
					<td width="120" height=30><font size="2"
						face="Arial, Helvetica, sans-serif">Modulating Signal</font></td>
					<td width="60" align="center"><font size="2"
						face="Arial, Helvetica, sans-serif">start[ms]</font></td>
					<td width="60" align="center"><font size="2"
						face="Arial, Helvetica, sans-serif">stop[ms]</font></td>
					<td width="80" align="center"><font size="2"
						face="Arial, Helvetica, sans-serif">peak[<%=peak%>]</font></td>
					<td width="60" align="center"><font size="2"
						face="Arial, Helvetica, sans-serif">freq[Hz]</font></td>
					<td width="60" align="center"><font size="2"
						face="Arial, Helvetica, sans-serif">width[ms]</font></td>
				</tr>

				<tr>
					<td align="left"><bean:write name="ModulationForm"
						property="cdSignal" /></td>
					<td align="center"><html:select name="ModulationForm"
						property="type">
						<html:option value="none">none</html:option>
						<html:option value="constant">constant</html:option>
						<html:option value="ramp">ramp</html:option>
						<html:option value="random">random</html:option>
						<html:option value="sine">sine</html:option>
						<html:option value="square">square</html:option>
					</html:select> <html:hidden name="ModulationForm" property="cdNucleus" /> <html:hidden
						name="ModulationForm" property="cdSignal" /></td>
					<td align="center"><html:text name="ModulationForm"
						property="tini" size="3" /></td>
					<td align="center"><html:text name="ModulationForm"
						property="tfin" size="3" /></td>
					<td align="center"><html:text name="ModulationForm"
						property="amp" size="3" /></td>
					<td align="center"><html:text name="ModulationForm"
						property="freq" size="3" /></td>
					<td align="center"><html:text name="ModulationForm"
						property="width" size="3" /></td>
				</tr>

			</table>

			</td>
		</tr>

		<TR>
			<TD align="center"><br> <input type="button"
				value="  Return " onClick="goToBack();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="  Apply  " onClick="submit();">
			</TD>
		</TR>

	</table>
</html:form></div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<!-- InstanceEndEditable --></div>
<div id="links">
			<hr /><a href="http://www.usp.br/" target="_blank">USP</a> |
			<a href="http://www.poli.usp.br/" target="_blank">POLI</a> |
<a href="http://www.leb.usp.br/" target="_blank">LEB</a><hr />
		</div>
<div id="footer">
			Copyright &copy; 2012 Biomedical Engineering Laboratory. All rights 
			reserved.</div>
<script type="text/javascript">
var MenuBar = new Spry.Widget.MenuBar("MenuBar", {imgDown:"SpryAssets/SpryMenuBarDownHover.gif", imgRight:"SpryAssets/SpryMenuBarRightHover.gif"});
</script>
</body>
<!-- InstanceEnd --></html>

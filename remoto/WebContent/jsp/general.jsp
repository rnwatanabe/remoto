
<%@ page contentType="text/html;charset=utf-8"%>
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
.general {
	background-color: #F5F9FA;
	height: auto;
	width: 500px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	padding-top: 0px;
	padding-right: 50px;
	padding-bottom: 20px;
	padding-left: 50px;
}
</style>
<script src="SpryAssets/SpryTooltip.js" type="text/javascript"></script>
<link href="SpryAssets/SpryTooltip.css" rel="stylesheet" type="text/css" />
<link href="hint.css" rel="stylesheet" type="text/css" />
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
<p>
<div class="general"><script>
    <%
String msg = (String)request.getAttribute("msg");
if( msg == null ) 
	msg = "";
%>
	var msg = "<%= msg %>";
	
  </script> <script>

function post(task)
{
	document.GeneralForm.task.value = task;
	
	if( task == 'reload' )
	{
		if( confirm("Load configuration? (current settings will be deleted)") == true )
			document.GeneralForm.submit();
	}	
	else if( task == 'delete' )
	{
		if( confirm("Delete selected scenario (all configuration settings)?") == true )
			document.GeneralForm.submit();
	}	
	else if( task == 'new' )
	{
		if( confirm("Create new scenario based on the current one?") == true )
			document.GeneralForm.submit();
	}
	else
	{
		document.GeneralForm.submit();
	}
}

</script> <html:form method="post" action="/general">

	<html:hidden name="GeneralForm" property="task" value="save" />


	<table width="100%" border="0">
		<tr>
			<td width="409" height="90" align="center"><b><font size="4"
				face="Arial, Helvetica, sans-serif">Scenarios</font></b></td>
		</tr>

		<tr>
			<td align="left">
			<table width="100%" border="0">

				<tr>
					<td>
					<table width="100%" border="0">

						<tr>
							<td width="0"><font face="Arial, Helvetica, sans-serif"
								size="2"><b>Scenario</b></font></td>
							<td width="450" align=left id="sprytrigger5"><html:select
								name="GeneralForm" style="width: 405px" property="id"
								onchange="post('load');">
								<html:optionsCollection name="GeneralForm"
									property="configurations" label="name" value="id" />
							</html:select></td>
						</tr>

						<tr>
							<td><font face="Arial, Helvetica, sans-serif" size="2"><b>Name</b></font></td>
							<td><html:text name="GeneralForm" style="width: 400px"
								property="nmConfiguration" /></td>
						</tr>

						<tr>
							<td><font face="Arial, Helvetica, sans-serif" size="2"><b>Description</b></font></td>
							<td><html:textarea name="GeneralForm" style="width: 400px"
								cols="43" rows="13" property="dsConfiguration" /></td>
						</tr>

						<logic:notEqual name="GeneralForm" property="cdUser" value="guest">
							<logic:notEqual name="GeneralForm" property="id" value="1">
								<tr>
									<td colspan=2>&nbsp;</td>
								</tr>
								<tr align="left" valign="top">
									<td colspan=2><html:checkbox name="GeneralForm"
										property="storeResults" /><font
										face="Arial, Helvetica, sans-serif" size="2"> Store
									simulation results for future visualization</font></td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>

						<tr>
							<td colspan=2>&nbsp;</td>
						</tr>



						<tr>
							<td colspan="2" align=center><input type="button"
								id="sprytrigger1" onClick="post('new');" value="  New  ">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
								type="button" id="sprytrigger2" onClick="post('delete');"
								value=" Delete ">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
								type="button" id="sprytrigger3" onClick="post('save');"
								value="  Save  ">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
								type="button" id="sprytrigger4" onClick="post('reload');"
								value=" Load "></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>



					</table>



					</td>
				</tr>



			</table>

			</td>
		</tr>

	</table>
</html:form></div>
</p>
<div class="tooltipContent" id="sprytooltip5">Select a scenario or
demo to be loaded. You must click on &ldquo;Load&rdquo; in order to
confirm this operation.</div>
<div class="tooltipContent" id="sprytooltip4">Load selected
scenario. You must run a simulation in order to see results.</div>
<div class="tooltipContent" id="sprytooltip3">Save current
scenario.</div>
<div class="tooltipContent" id="sprytooltip2">Delete current
scenario.</div>
<div class="tooltipContent" id="sprytooltip1">Create a blank
scenario.</div>
<script type="text/javascript">
var sprytooltip1 = new Spry.Widget.Tooltip("sprytooltip1", "#sprytrigger1", {followMouse:true});
var sprytooltip2 = new Spry.Widget.Tooltip("sprytooltip2", "#sprytrigger2", {followMouse:true});
var sprytooltip3 = new Spry.Widget.Tooltip("sprytooltip3", "#sprytrigger3", {followMouse:true});
var sprytooltip4 = new Spry.Widget.Tooltip("sprytooltip4", "#sprytrigger4", {followMouse:true});
var sprytooltip5 = new Spry.Widget.Tooltip("sprytooltip5", "#sprytrigger5", {followMouse:true});
  </script> <script>
  if(msg != '') 	alert(msg);
  </script> <!-- InstanceEndEditable --></div>
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


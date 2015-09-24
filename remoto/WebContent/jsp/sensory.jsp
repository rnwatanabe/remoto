
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
.sensory {
	background-color: #F5F9FA;
	height: auto;
	width: 500px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	padding-top: 0px;
	padding-right: 20px;
	padding-bottom: 0px;
	padding-left: 20px;
}
</style>

<script>

function change()
{
	document.NeuronForm.task.value = "MN";
	document.NeuronForm.submit();
}

</script>

<link href="hint.css" rel="stylesheet" type="text/css" />
<link href="apply.css" rel="stylesheet" type="text/css" />
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
        
      </ul>
    </li>
    <li><a href="/remoto/start.do">Run</a></li>
    <li><a href="/remoto/results.do?opt=chart_img">Results</a>      </li>
    <li><a href="/remoto/login.do">Login</a></li>
  </ul>
</div>
  <!-- InstanceBeginEditable name="EditRegion1" -->
<p>&nbsp;</p>
<div class="sensory"><html:form method="post" action="/sensory">

	<html:hidden name="NeuronForm" property="task" value="saveAF" />

	<TABLE width="100%" border=0>
		<tr>
			<td height="90" colspan=3 align="center"><b><font size="4"
				face="Arial, Helvetica, sans-serif">Parameters of the sensory
			fibers</font></b></td>
		</tr>


		<tr>
        <td height="50" align="center" bgcolor="#EEF5F7"><b>Joint:
			</b> <html:select name="NeuronForm" property="cdJoint">
				<html:option value="ankle">Ankle</html:option>
			</html:select>
            </td>
            
			<td colspan=2 align="center" bgcolor="#EEF5F7"><b>Pool</b> <html:select name="NeuronForm"
				property="cdNucleus" onchange="change();">
				<html:optionsCollection name="NeuronForm" property="nuclei"
					label="name" value="cd" />
			</html:select></td>
		</tr>

		<tr>
			<td colspan=2>&nbsp;</td>
		</tr>

		<tr>
			<td valign="top">
			<table border=0>
				<TR>
					<TD width="210" height=27>&nbsp;</TD>
				</TR>
				<TR>
					<TD width="210" height=27>&nbsp;</TD>
				</TR>
				<TR>
					<TD width="210" height=27 bgcolor="#EEF5F7">Axon threshold
					[mA]</TD>
				</TR>
				<TR>
					<TD width="210" height=27 bgcolor="#EEF5F7">Axon conduction
					velocity [m/s]</TD>
				</TR>
				
			</table>
			</td>

			<logic:iterate name="NeuronForm" property="sensories" id="sfType"
				indexId="index">
				<td valign="top">
				<TABLE border=0 bgcolor="#DAEAEB">
					<TR>
						<TD height=27 colspan=2 align="center" valign="middle"
							bgcolor="#C2DDE0"><b>&nbsp;&nbsp; <bean:write
							name="sfType" property="type" /> <html:hidden name="sfType"
							property="type" indexed="true" /> <html:hidden name="sfType"
							property="category" indexed="true" /> <html:hidden name="sfType"
							property="cdNucleus" indexed="true" /> <html:hidden name="sfType"
							property="active" indexed="true" /> <html:hidden name="sfType"
							property="quantity" indexed="true" /> <html:hidden name="sfType"
							property="cdNerve" indexed="true" /></TD>
					</TR>

					<TR>
						<TD width="60" height=27 align=center valign="middle"
							bgcolor="#C2DDE0">from</TD>
						<TD width="60" height=27 align=center valign="middle"
							bgcolor="#C2DDE0">to</TD>
					</TR>

					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="sfType" property="axonThreshold1" size="2" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="sfType" property="axonThreshold2" size="2" indexed="true" /></TD>
					</TR>
					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="sfType" property="axonVelocity1" size="3" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="sfType" property="axonVelocity2" size="3" indexed="true" /></TD>
					</TR>
					
				</TABLE>
				</td>
			</logic:iterate>

		</tr>
		<tr><td height="90" colspan="3" align="center">
        		<input type="button" value="  Apply  "
		onclick="submit();" />
        </td></tr>

	</TABLE>
	

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

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
.synapse {
	background-color: #F5F9FA;
	height: auto;
	width: 950px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	padding-top: 0%;
	padding-right: 1%;
	padding-bottom: 0%;
	padding-left: 1%;
}
</style>

<script>

function change(value)
{
	document.SynapseForm.task.value = value;
	document.SynapseForm.submit();
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
<div class="synapse"><html:form method="post" action="/synapse">

	<html:hidden name="SynapseForm" property="task" value="save" />

	<TABLE width="100%" border=0>

		<tr>
			<td height="90" colspan=9 align="center"><b><font size="4"
				face="Arial, Helvetica, sans-serif">Synaptic Parameters</font></b></td>
		</tr>

		<tr>
        
        	<td height="50" align="center" bgcolor="#EEF5F7"><b>Joint:
			</b> 
            <bean:write name="SynapseForm" property="cdJoint"></bean:write>
            
            
            </td>
        
        
		  <td align="center" bgcolor="#EEF5F7"><b>Pool</b> <html:select name="SynapseForm"
				property="cdNucleus" onchange="change('change');">
				<html:optionsCollection name="SynapseForm" property="nuclei"
					label="name" value="cd" />
			</html:select></td>
		  <td align="center" bgcolor="#EEF5F7"><b>Incoming cell</b> <html:select name="SynapseForm"
				property="typePre" onchange="change('change');">
				<html:optionsCollection name="SynapseForm" property="typesPre"
					label="categoryTypeForSynapse" value="categoryTypeForSynapse" />
			</html:select></td>
		  <td align="center" bgcolor="#EEF5F7"><b>Target cell</b> <html:select name="SynapseForm"
				property="typePos" onchange="change('change');">
				<html:optionsCollection name="SynapseForm" property="typesPos"
					label="categoryTypeForSynapse" value="categoryTypeForSynapse" />
			</html:select></td>
			<td align="center" bgcolor="#EEF5F7"><a href="markov.do?option=S">Kinetic
			Properties</a></td>
		</tr>

		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5">
			<table width="100%" bgcolor="#DAEAEB">
				<logic:notEmpty name="SynapseForm" property="conductances">

					<tr>
						<td width="30" bgcolor="#EEF5F7"></td>
						<td width=160 height="50" align="center" valign="middle"
							bgcolor="#C2DDE0"><b>Incoming</b></td>
						<td width=90 height="50" align="center" valign="middle"
							bgcolor="#C2DDE0"><b>Target</b></td>
						<td width=80 height="50" align=center valign="middle"
							bgcolor="#C2DDE0"><b>Polarity</b></td>
						<td width=100 height="50" align=center valign="middle"
							bgcolor="#C2DDE0"><b>Compartment</b></td>
						<td width=100 height="50" align=center valign="middle"
							bgcolor="#C2DDE0"><b>Maximum conductance [nS]</b></td>
						<td width=90 height="50" align=center valign="middle"
							bgcolor="#C2DDE0"><b>Connectivity [%]</b></td>
						<td width=90 height="50" align=center valign="middle"
							bgcolor="#C2DDE0"><b>Transmission delay [ms]</b></td>
						<td width=90 height="50" align=center valign="middle"
							bgcolor="#C2DDE0"><b>Dynamics</b></td>
					</tr>

					<logic:iterate name="SynapseForm" property="conductances"
						id="conducType" indexId="index">
						<tr>
							<td width="30" height="40" align="center" valign="middle"
								bgcolor="#EEF5F7"><html:checkbox name="conducType"
								property="active" indexed="true" /></td>
							<td height="40" align="center" valign="middle" bgcolor="#EEF5F7"><bean:write
								name="conducType" property="pre" />
							<td height="40" align="center" valign="middle" bgcolor="#EEF5F7"><bean:write
								name="conducType" property="pos" /> <html:hidden
								name="conducType" property="cdConductanceType" indexed="true" />
							<html:hidden name="conducType" property="cdNucleus"
								indexed="true" /> <html:hidden name="conducType"
								property="cdNucleusPre" indexed="true" /></td>
							<td height="40" align=center valign="middle" bgcolor="#EEF5F7">
							<bean:write name="conducType" property="polarity" /> <html:hidden
								name="conducType" property="e" indexed="true" /> <html:hidden
								name="conducType" property="tpeak" indexed="true" /></td>
							<td height="40" align=center valign="middle" bgcolor="#EEF5F7">
							<logic:notEqual name="conducType" property="compartment" value="">
								<html:select name="conducType" property="compartment"
									indexed="true">
									<html:option value="S">soma</html:option>
									<html:option value="D">dendrite</html:option>
								</html:select>
							</logic:notEqual> <logic:equal name="conducType" property="compartment" value="">
						not applicable
				    	<html:hidden name="conducType" property="compartment"
									indexed="true" />
							</logic:equal></td>
							<td height="40" align=center valign="middle" bgcolor="#EEF5F7"><html:text
								name="conducType" property="gmax" size="4" indexed="true" /></td>
							<td height="40" align=center valign="middle" bgcolor="#EEF5F7"><html:text
								name="conducType" property="connectivity" size="3"
								indexed="true" /></td>
							<td height="40" align=center valign="middle" bgcolor="#EEF5F7"><html:text
								name="conducType" property="delay" size="3" indexed="true" /></td>
							<td height="40" align=center valign="middle" bgcolor="#EEF5F7">
							<a
								href='dynamics.do?cdNucleusPre=<bean:write name="conducType" property="cdNucleusPre"/>&cdNucleus=<bean:write name="conducType" property="cdNucleus"/>&cdConductanceType=<bean:write name="conducType" property="cdConductanceType"/>'>
							<bean:write name="conducType" property="dynamicType" /> </a></td>
						</tr>
					</logic:iterate>

				</logic:notEmpty>

			</table>
			</td>
		</tr>

		<tr>
			<td height="90" colspan="5" align="center"><input type="button"
				value="  Apply  " onClick="submit();"></td>
		</tr>


	</TABLE>


</html:form></div>

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

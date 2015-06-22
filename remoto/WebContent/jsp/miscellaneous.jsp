
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
.miscellaneous {
	background-color: #F5F9FA;
	height: auto;
	width: 550px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	text-align: left;
	padding-top: 50px;
	padding-right: 50px;
	padding-bottom: 30px;
	padding-left: 50px;
}
</style>
<script src="SpryAssets/SpryCollapsiblePanel.js" type="text/javascript"></script>
<link href="apply.css" rel="stylesheet" type="text/css" />
<link href="SpryAssets/SpryCollapsiblePanel_misc.css" rel="stylesheet"
	type="text/css" />
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
<p>&nbsp;</p>

<div class="miscellaneous"><html:form method="post"
	action="/miscellaneous">
	<html:hidden name="MiscellaneousForm" property="task" value="save" />

	<b><font size="4" face="Arial, Helvetica, sans-serif">Miscellaneous</font></b>

	<p>&nbsp;</p>


	<div id="CollapsiblePanel" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Dendritic PIC
	properties</div>
	<div class="CollapsiblePanelContent">

	<table width="100%" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="35" length="2">
			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>
		</logic:iterate>
	</table>

	</div>
	</div>


	<div id="CollapsiblePanel1" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Refractory periods</div>
	<div class="CollapsiblePanelContent">

	<table width="100%" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="1" length="7">
			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>
		</logic:iterate>
	</table>

	</div>
	</div>


	<div id="CollapsiblePanel2" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Anatomical position
	of spinal neurons</div>
	<div class="CollapsiblePanelContent">
	<table width="100%" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="8" length="12">
			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>
		</logic:iterate>
	</table>
	</div>
	</div>


	<div id="CollapsiblePanel3" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Synaptic declining
	factor</div>
	<div class="CollapsiblePanelContent">
	<table width="100%" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="20" length="2">
			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>

			<logic:equal name="prop" property="division" value="true">
			</logic:equal>

		</logic:iterate>
	</table>
	</div>
	</div>


	<div id="CollapsiblePanel4" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Muscle and skin
	properties</div>
	<div class="CollapsiblePanelContent">
	<table width="100%" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="22" length="5">
			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>

			<logic:equal name="prop" property="division" value="true">
			</logic:equal>

		</logic:iterate>
	</table>
	</div>
	</div>



	<div id="CollapsiblePanel5" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Surface EMG
	properties</div>
	<div class="CollapsiblePanelContent">
	<table width="100%" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="27" length="3">
			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>

			<logic:equal name="prop" property="division" value="true">
			</logic:equal>

		</logic:iterate>
	</table>
	</div>
	</div>


	<div id="CollapsiblePanel6" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Spontaneous firing
	rate of RCs</div>
	<div class="CollapsiblePanelContent">
	<table width="100%" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="30" length="2">

			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>


		</logic:iterate>
	</table>
	</div>
	</div>



	<div id="CollapsiblePanel7" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0">Random properties</div>
	<div class="CollapsiblePanelContent">
	<table width="100%" border="0">

		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="32" length="3">
			<tr>
				<td align="left"><font face="Arial, Helvetica, sans-serif"
					size="2"><bean:write name="prop" property="description" /></font></td>
				<td align="right"><html:text name="prop" property="value"
					size="4" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>

			<logic:equal name="prop" property="division" value="true">
				<tr>
					<td colspan=2>&nbsp;</td>
				</tr>
				<tr>
					<td colspan=2 align="left">These properties can vary for each
					simulation run, according to the chosen CV</td>
				</tr>
			</logic:equal>

		</logic:iterate>
	</table>
	</div>
	</div>


	<table width="250" border="0">
		<logic:iterate name="MiscellaneousForm" property="miscellaneous"
			id="prop" indexId="index" offset="0" length="1">
			<tr>
				<td align="left"><html:hidden name="prop" property="value"
					value="0.05" indexed="true" /> <html:hidden name="prop"
					property="property" indexed="true" /> <html:hidden name="prop"
					property="description" indexed="true" /> <html:hidden name="prop"
					property="index" indexed="true" /> <html:hidden name="prop"
					property="division" indexed="true" /></td>
			</tr>
		</logic:iterate>
	</table>


	<p><html:checkbox name="MiscellaneousForm" property="storeSignals" /><font
		face="Arial, Helvetica, sans-serif" size="2"> Store neuron
	signals (membrane potential and conductance time course)</font></p>
	<p><html:checkbox name="MiscellaneousForm"
		property="keepProperties" /><font face="Arial, Helvetica, sans-serif"
		size="2"> Keep the same random property values for repeatable
	simulations</font></p>
	<br />



	<div class="apply"><input type="button" value="  Apply  "
		onclick="submit();" /></div>

</html:form></div>
<p>&nbsp;</p>

<script type="text/javascript">
var CollapsiblePanel = new Spry.Widget.CollapsiblePanel("CollapsiblePanel", {contentIsOpen:false});
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel1", {contentIsOpen:false});
var CollapsiblePanel2 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel2", {contentIsOpen:false});
var CollapsiblePanel3 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel3", {contentIsOpen:false});
var CollapsiblePanel4 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel4", {contentIsOpen:false});
var CollapsiblePanel5 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel5", {contentIsOpen:false});
var CollapsiblePanel6 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel6", {contentIsOpen:false});
var CollapsiblePanel7 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel7", {contentIsOpen:false});
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

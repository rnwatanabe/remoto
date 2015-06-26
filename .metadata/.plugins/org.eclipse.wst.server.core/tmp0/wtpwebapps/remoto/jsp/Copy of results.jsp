<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ page import="java.util.*,br.remoto.model.vo.Summary"%>
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
<title>ReMoto</title>
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
.result {
	background-color: #F5F9FA;
	height: auto;
	width: 700px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	padding: 50px;
}
</style>

<script src="SpryAssets/SpryCollapsiblePanel.js" type="text/javascript"></script>
<script src="SpryAssets/SpryTooltip.js" type="text/javascript"></script>
<script>

function change()
{
	document.ResultsForm.action.value = "change";
	document.ResultsForm.target = "";
	document.ResultsForm.submit();
}


function post(task)
{
	document.ResultsForm.opt.value = task;
	
	if( task == 'chart' )
	{
			document.ResultsForm.target = "";
			document.ResultsForm.submit();
	}	
	else if( task == 'chart_img' )
	{
			document.ResultsForm.target = "_blank";
			document.ResultsForm.submit();
	}	
	else if( task == 'file' )
	{
			document.ResultsForm.target = "_blank";
			document.ResultsForm.submit();
	}
	else
	{
		document.ResultsForm.target = "";
		document.ResultsForm.submit();
	}
	
}


</script>
<style type="text/css">
@import url("SpryAssets/SpryCollapsiblePanel_results.css");
</style>
<link href="hint.css" rel="stylesheet" type="text/css" />
<link href="SpryAssets/SpryTooltip.css" rel="stylesheet" type="text/css" />
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
<div class="result"><html:form method="post" action="/results">
	<html:hidden name="ResultsForm" property="action" value="" />
	<html:hidden name="ResultsForm" property="opt" />
	<input type="hidden" name="serialNumber" value="<%=Math.random()%>">

	<table>
		<tr>
			<td align="center" colspan=2><b><font size="4"
				face="Arial, Helvetica, sans-serif"> Simulation Results </font></b></td>
		</tr>

		<tr>
			<td colspan=2>&nbsp;</td>
		</tr>

		<tr>
			<td colspan=2><b>Pool</b> <html:select name="ResultsForm"
				property="cdNucleus" onchange="change();">
				<html:optionsCollection name="ResultsForm" property="nuclei"
					label="name" value="cd" />
			</html:select></td>
		</tr>

	</table>

	<br>
	<table width="700">
		<tr>

			<td>
			<table width="100%" bgcolor="#DAEAEB">

				<logic:notEmpty name="ResultsForm" property="elementsSpikes">
					<tr height=25>
						<td><html:radio name="ResultsForm" property="task"
							value="spikes" /></td>
						<td bgcolor="#EEF5F7">Spike times</td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsFiringRate">
					<tr height=25>
						<td><html:radio name="ResultsForm" property="task"
							value="firingRate" /></td>
						<td bgcolor="#EEF5F7">Firing rate</td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsForce">
					<tr height=25>
						<td><html:radio name="ResultsForm" property="task"
							value="force" /></td>
						<td bgcolor="#EEF5F7">Force</td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsEMG">
					<tr height=25>
						<td><html:radio name="ResultsForm" property="task"
							value="EMG" /></td>
						<td bgcolor="#EEF5F7">EMG</td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsHistogram">
					<tr height=25>
						<td><html:radio name="ResultsForm" property="task"
							value="histogram" /></td>
						<td bgcolor="#EEF5F7">ISI histogram</td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsSignal">
					<tr height=25>
						<td><html:radio name="ResultsForm" property="task"
							value="signal" /></td>
						<td bgcolor="#EEF5F7">Signal</td>
					</tr>
				</logic:notEmpty>
				<logic:equal name="ResultsForm" property="cdNucleus" value="DT">
					<logic:notEmpty name="ResultsForm" property="elementsTracts">
						<tr height=25>
							<td><html:radio name="ResultsForm" property="task"
								value="signal" /></td>
							<td bgcolor="#EEF5F7">Signal</td>
						</tr>
					</logic:notEmpty>
				</logic:equal>

				<logic:notEmpty name="ResultsForm" property="elementsForce">
					<logic:equal name="ResultsForm" property="opt" value="chart">
						<logic:notEmpty name="ResultsForm" property="elementsSpikes">
							<tr height=25>
								<td><html:radio name="ResultsForm" property="task"
									value="spikesAndForce" /></td>
								<td bgcolor="#EEF5F7">Force and spikes</td>
							</tr>

							<tr height=25>
								<td><html:radio name="ResultsForm" property="task"
									value="spikesAndEMG" /></td>
								<td bgcolor="#EEF5F7">EMG and spikes</td>
							</tr>
						</logic:notEmpty>
					</logic:equal>
				</logic:notEmpty>

				<logic:equal name="ResultsForm" property="opt" value="file">
					<logic:notEmpty name="ResultsForm" property="elementsProperties">
						<tr height=25>
							<td><html:radio name="ResultsForm" property="task"
								value="properties" /></td>
							<td bgcolor="#EEF5F7">Properties</td>
						</tr>
					</logic:notEmpty>
				</logic:equal>


			</table>
			</td>

			<td valign=top>
			<table width="100%" bgcolor="#DAEAEB">

				<logic:notEmpty name="ResultsForm" property="elementsSpikes">
					<tr height=25>
						<td valign=top bgcolor="#EEF5F7"><html:select
							name="ResultsForm" property="cdSpikes">
							<html:optionsCollection name="ResultsForm"
								property="elementsSpikes" label="cd" value="cd" />
						</html:select></td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsFiringRate">
					<tr height=25>
						<td valign=top bgcolor="#EEF5F7"><html:select
							name="ResultsForm" property="cdFiringRate">
							<html:optionsCollection name="ResultsForm"
								property="elementsFiringRate" label="cd" value="cd" />
						</html:select></td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsForce">
					<tr height=25>
						<td valign=top bgcolor="#EEF5F7"><html:select
							name="ResultsForm" property="cdForce">
							<html:optionsCollection name="ResultsForm"
								property="elementsForce" label="cd" value="cd" />
						</html:select></td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsEMG">
					<tr height=25>
						<td valign=top bgcolor="#EEF5F7"><html:select
							name="ResultsForm" property="cdEMG">
							<html:optionsCollection name="ResultsForm" property="elementsEMG"
								label="cd" value="cd" />
						</html:select></td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsHistogram">
					<tr height=25>
						<td valign=top bgcolor="#EEF5F7"><html:select
							name="ResultsForm" property="cdHistogram">
							<html:optionsCollection name="ResultsForm"
								property="elementsHistogram" label="cd" value="cd" />
						</html:select> max # of bins <html:text name="ResultsForm"
							property="numberOfBins" size="3" /> width <html:text
							name="ResultsForm" property="binWidth" size="3" /></td>
					</tr>
				</logic:notEmpty>

				<logic:notEmpty name="ResultsForm" property="elementsSignal">
					<tr>
						<td valign=top bgcolor="#EEF5F7"><html:select
							name="ResultsForm" property="whichSignal">
							<html:option value="Vs">soma potential</html:option>
							<html:option value="Vd">dendrite potential</html:option>
							<html:option value="g">conductance</html:option>
							<html:option value="inj">injected current</html:option>
						</html:select> <html:select name="ResultsForm" property="cdSignal">
							<html:optionsCollection name="ResultsForm"
								property="elementsSignal" label="cd" value="cd" />
						</html:select> <html:select name="ResultsForm" property="cdConductance">
							<html:optionsCollection name="ResultsForm"
								property="elementsConductance" label="cdConductanceType"
								value="cdConductanceType" />
						</html:select></td>
					</tr>
				</logic:notEmpty>
				<logic:equal name="ResultsForm" property="cdNucleus" value="DT">
					<logic:notEmpty name="ResultsForm" property="elementsTracts">
						<tr>
							<td valign=top bgcolor="#EEF5F7"><html:select
								name="ResultsForm" property="cdSignal">
								<html:optionsCollection name="ResultsForm"
									property="elementsTracts" label="cd" value="cd" />
							</html:select></td>
						</tr>
					</logic:notEmpty>
				</logic:equal>

				<logic:notEmpty name="ResultsForm" property="elementsForce">
					<logic:equal name="ResultsForm" property="opt" value="chart">
						<logic:notEmpty name="ResultsForm" property="elementsSpikes">
							<tr height=25>
								<td valign=top bgcolor="#EEF5F7">(MN spikes at end-plate)</td>
							</tr>
							<tr height=25>
								<td valign=top bgcolor="#EEF5F7">(MN spikes at end-plate)</td>
							</tr>
						</logic:notEmpty>
					</logic:equal>
				</logic:notEmpty>

				<logic:equal name="ResultsForm" property="opt" value="file">
					<logic:notEmpty name="ResultsForm" property="elementsProperties">
						<tr height=25>
							<td valign=top bgcolor="#EEF5F7"><html:select
								name="ResultsForm" property="cdProperties">
								<html:optionsCollection name="ResultsForm"
									property="elementsProperties" label="cd" value="cd" />
							</html:select></td>
						</tr>
					</logic:notEmpty>
				</logic:equal>

			</table>
			</td>
		</tr>

		<tr>
			<td height="70" align="center" valign="middle" bgcolor="#EEF5F7">

			<center><input type="button" onclick="post('chart');"
				value="  Generate" /></center>



			</td>
			<td height="70"></td>
		</tr>
		<logic:notEqual name="ResultsForm" property="opt" value="file">
			<tr>
				<td height="70" align="center" valign="middle" bgcolor="#EEF5F7">
				<center><input type="button" onclick="post('chart_img');"
					value="  Save as image  " /></center>
				</td>
				<td height="70"></td>
			</tr>
		</logic:notEqual>

	</table>
	</p>
	<p>&nbsp;</p>
	<div id="CollapsiblePanel1" class="CollapsiblePanel">
	<div class="CollapsiblePanelTab" tabindex="0"><font
		face="Arial, Helvetica, sans-serif" size="2"><b>Configuration
	(click here to expand/collapse)</b></font></div>
	<div class="CollapsiblePanelContent"><br />
	&nbsp;&nbsp;Sampling rate <html:select name="ResultsForm"
		property="sample">
		<html:optionsCollection name="ResultsForm" property="listSamples"
			label="label" value="value" />
	</html:select> kHz <logic:notEmpty name="ResultsForm" property="elementsEMG">
		<br />
		<br />
    &nbsp;&nbsp;EMG filtering &nbsp;&nbsp;&nbsp;
    low-freq
    <html:text name="ResultsForm" size="4" property="fcLow" />
    high-freq
    <html:text name="ResultsForm" size="4" property="fcHigh" />
    Hz </logic:notEmpty> <logic:empty name="ResultsForm" property="elementsEMG">
		<html:hidden name="ResultsForm" property="fcLow" />
		<html:hidden name="ResultsForm" property="fcHigh" />
	</logic:empty> <br />
	</p>
	<table>
		<logic:notEmpty name="ResultsForm" property="elementsEMG">
			<tr>
				<td><html:radio name="ResultsForm" property="filter"
					value="1st2nd" /> 1st order low - 2nd order high</td>
			</tr>
			<tr>
				<td><html:radio name="ResultsForm" property="filter"
					value="3rd3rd" /> 3rd order low - 3rd order high</td>
			</tr>
			<tr>
				<td><html:radio name="ResultsForm" property="filter"
					value="noFiltering" /> No filtering</td>
			</tr>
		</logic:notEmpty>
	</table>
	<br />
	&nbsp; <html:checkbox name="ResultsForm" property="withEMGnoise" /> EMG
	with noise <br />
	&nbsp; <html:checkbox name="ResultsForm" property="withEMGattenuation" />
	EMG with amplitude attenuation / lowpass filtering <br />
	<table>
		<br />
		<tr>
			<td>X axis</td>
			<td>&nbsp;&nbsp;<html:text name="ResultsForm" property="xini"
				size="5" /></td>
			<td>to <html:text name="ResultsForm" property="xfin" size="5" /></td>
		</tr>
		<tr>
			<td>Y axis</td>
			<td>&nbsp;&nbsp;<html:text name="ResultsForm" property="yini"
				size="5" /></td>
			<td>to <html:text name="ResultsForm" property="yfin" size="5" /></td>
		</tr>
	</table>
	</div>
	<div class="CollapsiblePanelContent"><html:form method="post"
		action="/results"></html:form></div>
	</div>
	<p>&nbsp;</p>
	<div class="hint" id="sprytrigger1">Hint</div>
</html:form></div>
<div class="tooltipContent" id="sprytooltip1">You have two
possibilities visualize the simulation results: by means of interactive
graphic (click on &quot;Generate&quot;), which will require a fast
internet connection and the JRE installed; or by means of a static
figure (click on &quot;Save as image&quot;), which can be easily
downloaded.</div>
<script type="text/javascript">
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel1", {contentIsOpen:false});
var sprytooltip1 = new Spry.Widget.Tooltip("sprytooltip1", "#sprytrigger1");
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

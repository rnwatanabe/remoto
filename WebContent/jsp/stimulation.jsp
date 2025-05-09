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
.stimulation1 {
	background-color: #F5F9FA;
	height: auto;
	width: 950px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	text-align: center;
	padding-top: 0px;
	padding-right: 10px;
	padding-bottom: 0px;
	padding-left: 10px;
}
</style>
<script>

function change()
{
	document.StimulationForm.task.value = "change";
	document.StimulationForm.submit();
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
<p>
<div class="stimulation1"><html:form method="post"
	action="/stimulation">

	<html:hidden name="StimulationForm" property="task"
		value="saveStimulus" />

	<table width="920" border="0">
		<tr>
			<td colspan="2" width="515" height="90" align="center" valign="middle">
			<p><b><font size="4" face="Arial, Helvetica, sans-serif">Nerve
			Stimulation</font></b></p>
			</td>
		</tr>

		<tr>
			<td colspan="2">

			<table width="100%" border="0" align="center" bgcolor="#DAEAEB">



				<tr align="left" valign="top">
					<td height="50" colspan="2" align="center" valign="middle"
						bgcolor="#CBE0E2"><strong><font size="2"
						face="Arial, Helvetica, sans-serif">Nerve</font></strong></td>
                        <td valign="middle">&nbsp;&nbsp;Motor-unit recruitment order:</b> <html:select
						name="StimulationForm" property="recruitmentOrderFES" onchange="submit();">
                        <html:option value="linear">Linear</html:option>
                     
					</html:select>
                    </td>



				</tr>
				<logic:iterate name="StimulationForm" property="nerves" id="nerve"
					indexId="index">
					<tr align="left" valign="top">
						<td width="10" height="30" align="center" valign="middle"
							bgcolor="#EEF5F7"><html:checkbox name="nerve"
							property="active" indexed="true" onchange="submit();" /></td>
						<td width="100" height="30" align="center" valign="middle"
							bgcolor="#EEF5F7"><font face="Arial, Helvetica, sans-serif"
							size="2"> <bean:write name="nerve" property="cdNerve" /> </font>
						<html:hidden name="nerve" property="cdNerve" indexed="true" /></td>

						<td height="30" align="center" valign="middle" bgcolor="#EEF5F7"><font
							size="2" face="Arial, Helvetica, sans-serif">Distance to
						spinal entry:</font><html:text name="nerve"
							property="stimulusSpinalEntry" size="3" indexed="true" /> <font
							size="2" face="Arial, Helvetica, sans-serif"> [m]</font></td>
						<td height="30" align="center" valign="middle" bgcolor="#EEF5F7"><font
							size="2" face="Arial, Helvetica, sans-serif">Distance to
						end plate:</font><html:text name="nerve" property="stimulusEndPlate"
							size="3" indexed="true" /> <font size="2"
							face="Arial, Helvetica, sans-serif"> [m]</font></td>
					</tr>

					<logic:equal name="nerve" property="active" value="true">


						<tr>
							<td colspan="4">
							<table width="100%">
								<tr>
									<td height="30" colspan="3" align="left" bgcolor="#CBE0E2">
									└► Stimulus:</td>
									<td height="30" align="center" valign="middle"
										bgcolor="#CBE0E2"><font size="2"
										face="Arial, Helvetica, sans-serif">Pulse width:</font> 1 ms</td>
									<td height="30" align="center" valign="middle"
										bgcolor="#CBE0E2"><font size="2"
										face="Arial, Helvetica, sans-serif">Start:</font><html:text
										name="nerve" property="tini" size="3" indexed="true" /><font
										size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
									<td height="30" align="center" valign="middle"
										bgcolor="#CBE0E2"><font size="2"
										face="Arial, Helvetica, sans-serif">Stop:</font><html:text
										name="nerve" property="tfin" size="3" indexed="true" /><font
										size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
									<td height="30" align="center" valign="middle"
										bgcolor="#CBE0E2"><font size="2"
										face="Arial, Helvetica, sans-serif">Intensity:</font><html:text
										name="nerve" property="amp" size="3" indexed="true" /><font
										size="2" face="Arial, Helvetica, sans-serif">[mA]</font></td>
									<td height="30" align="center" valign="middle"
										bgcolor="#CBE0E2"><font size="2"
										face="Arial, Helvetica, sans-serif">Frequency:</font><html:text
										name="nerve" property="freq" size="3" indexed="true" /><font
										size="2" face="Arial, Helvetica, sans-serif">[Hz]</font></td>

									<td height="30" align="center" valign="middle"
										bgcolor="#CBE0E2"><font size="2"
										face="Arial, Helvetica, sans-serif">Modulation:</font><html:select
										name="nerve" property="cdSignal" indexed="true"
										onchange="submit();">
										<html:option value="none">none</html:option>
										<html:option value="constant">constant</html:option>
										<html:option value="ramp">ramp</html:option>
										<html:option value="sine">sine</html:option>
										<html:option value="random">random</html:option>
										<html:option value="trapezoid">trapezoid</html:option>
										<html:option value="triangle">triangle</html:option>
                                        <html:option value="stochastic">stochastic Gamma</html:option>
									</html:select></td>
								</tr>
							</table>
							</td>
						</tr>
					</logic:equal>

					<logic:equal name="nerve" property="active" value="true">
						<logic:notEqual name="nerve" property="cdSignal" value="">
							<logic:notEqual name="nerve" property="cdSignal" value="none">

								<tr>
									<td colspan="4">
									<table width="100%">
										<tr>

											<td height="30" colspan="3" align="left" bgcolor="#CBE0E2">
											└► Frequency modulating signal:</td>

											<td height="30" align="center" valign="middle"
												bgcolor="#CBE0E2"><font size="2"
												face="Arial, Helvetica, sans-serif">Start:</font><html:text
												name="nerve" property="modulating_tini" size="3"
												indexed="true" /><font size="2"
												face="Arial, Helvetica, sans-serif">[ms]</font></td>

											<td height="30" align="center" valign="middle"
												bgcolor="#CBE0E2"><font size="2"
												face="Arial, Helvetica, sans-serif">Stop:</font><html:text
												name="nerve" property="modulating_tfin" size="3"
												indexed="true" /><font size="2"
												face="Arial, Helvetica, sans-serif">[ms]</font></td>

											<td height="30" align="center" valign="middle"
												bgcolor="#CBE0E2"><font size="2"
												face="Arial, Helvetica, sans-serif">Peak:</font><html:text
												name="nerve" property="modulating_amp" size="3"
												indexed="true" /><font size="2"
												face="Arial, Helvetica, sans-serif">[Hz]</font></td>


											<logic:notEqual name="nerve" property="cdSignal" value="ramp">
												<logic:notEqual name="nerve" property="cdSignal"
													value="constant">

													<logic:notEqual name="nerve" property="cdSignal"
														value="random">
														<logic:notEqual name="nerve" property="cdSignal"
															value="trapezoid">
															<logic:notEqual name="nerve" property="cdSignal"
																value="triangle">
																<td height="30" align="center" valign="middle"
																	bgcolor="#CBE0E2"><font size="2"
																	face="Arial, Helvetica, sans-serif"> Frequency:</font><html:text
																	name="nerve" property="modulating_freq" size="5"
																	indexed="true" /><font size="2"
																	face="Arial, Helvetica, sans-serif">[Hz]</font></td>
															</logic:notEqual>
														</logic:notEqual>
													</logic:notEqual>
												</logic:notEqual>
											</logic:notEqual>

											<logic:notEqual name="nerve" property="cdSignal"
												value="constant">
												<logic:notEqual name="nerve" property="cdSignal"
													value="random">
													<logic:notEqual name="nerve" property="cdSignal"
														value="sine">
														<td height="30" align="center" valign="middle"
															bgcolor="#CBE0E2"><font size="2"
															face="Arial, Helvetica, sans-serif"><logic:equal
															name="nerve" property="cdSignal" value="square">Width:</logic:equal><logic:equal
															name="nerve" property="cdSignal" value="ramp">Time-to-peak:</logic:equal><logic:equal
															name="nerve" property="cdSignal" value="trapezoid">Time-to-peak and Plateau:</logic:equal><logic:equal
															name="nerve" property="cdSignal" value="triangle">Time-to-peak:</logic:equal></font><html:text
															name="nerve" property="width" size="5" indexed="true" /><font
															size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
													</logic:notEqual>
												</logic:notEqual>
											</logic:notEqual>
										</tr>
									</table>
									</td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>
					</logic:equal>




					<html:hidden name="nerve" property="tini" indexed="true" />
					<html:hidden name="nerve" property="tfin" indexed="true" />

					<html:hidden name="nerve" property="amp" indexed="true" />


					<html:hidden name="nerve" property="freq" indexed="true" />


					<html:hidden name="nerve" property="cdSignal" indexed="true" />

					<html:hidden name="nerve" property="modulating_tini" indexed="true" />
					<html:hidden name="nerve" property="modulating_tfin" indexed="true" />

					<html:hidden name="nerve" property="modulating_amp" indexed="true" />


					<html:hidden name="nerve" property="modulating_freq" indexed="true" />
					<html:hidden name="nerve" property="width" indexed="true" />

					<html:hidden name="nerve" property="cdJoint" indexed="true" />

				</logic:iterate>
			</table>
			</td>
		</tr>

		<tr>
        
        <td height="200" width="500" colspan="1" rowspan="1" align="center">
            <img src="Nerve_stimulation3.png" width="378" />
</td>
          
			<td width="500" height="400" align="center" valign="middle"
				bgcolor="#D1DCDC"><IMG
				SRC="/remoto/servlet/ServletChartGenerator?cdSimulation=stimulation&id=<%=Math.random()%>"
				alt="Input graphic" BORDER=0 /></td>
		</tr>

	<tr><td height="90" colspan="2" align="center">
    	<input type="button" value="  Apply  "
		onClick="submit();">
    </td></tr>
	</table>


</html:form></div>

</p>
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

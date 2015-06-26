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
.conductance {
	background-color: #F5F9FA;
	width: 800px;
	height: auto;
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

function change(value)
{
	document.ConductanceForm.task.value = value;
	document.ConductanceForm.submit();
}

</script>

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
<div class="conductance"><html:form method="post"
	action="/conductance">

	<html:hidden name="ConductanceForm" property="task" value="save" />

	<TABLE width="800" border=0>

		<tr>
			<td height="90" colspan=3 align="center"><b><font size="4"
				face="Arial, Helvetica, sans-serif">Parameters of the
			voltage-dependent conductances</font></b></td>
		</tr>

		<tr>
			
          <td height="50" align="center" bgcolor="#EEF5F7"><b>Joint:
			</b> <html:select name="ConductanceForm" property="cdJoint">
				<html:option value="ankle">Ankle</html:option>
			</html:select></td>
            
            
            <td width="33%" align="center" bgcolor="#EEF5F7"><b>Pool</b> <html:select name="ConductanceForm"
				property="cdNucleus" onchange="change('change');">
				<html:optionsCollection name="ConductanceForm" property="nuclei"
					label="name" value="cd" />
			</html:select></td>

	    <td align="center" bgcolor="#EEF5F7"><b>Neuron</b> <html:select name="ConductanceForm"
				property="type" onchange="change('change');">
				<html:optionsCollection name="ConductanceForm" property="types"
					label="categoryType" value="type" />
			</html:select></td>

		</tr>



		<tr>
			<td colspan=3>&nbsp;</td>
		</tr>

		<tr>
			<td colspan=3>
			<table bgcolor="#DAEAEB">
				<logic:notEmpty name="ConductanceForm" property="conductances">
					<tr>
						<td width=70 align="center" valign="middle" bgcolor="#C2DDE0"><b>Compartment</b></td>
						<td width=70 align="center" valign="middle" bgcolor="#C2DDE0"><b>Conductance</b></td>
						<td width=200 align=center valign="middle" bgcolor="#C2DDE0"><b>Maximum
						conductance [mS/cm²]</b></td>
						<td width=200 align=center valign="middle" bgcolor="#C2DDE0"><b>Equilibrium
						potential * [mV]</b></td>
						<td width=200 align=center valign="middle" bgcolor="#C2DDE0"><b>State
						Variable</b></td>
						<td width=200 align=center valign="middle" bgcolor="#C2DDE0"><b>Time-to-peak
						[ms]</b></td>
						<td width=200 align=center valign="middle" bgcolor="#C2DDE0"><b>Peak
						value of alpha [1/ms]</b></td>
						<td width=200 align=center valign="middle" bgcolor="#C2DDE0"><b>Peak
						value of beta [1/ms]</b></td>
					</tr>

					<% int i = 0; %>
					<logic:notEqual name="ConductanceForm" property="cdNucleus"
						value="IN_ext">
						<logic:notEqual name="ConductanceForm" property="cdNucleus"
							value="IN_flex">
							<logic:iterate name="ConductanceForm" property="conductances"
								id="conducType" indexId="index">
								<tr>

									<td <% if (i == 0){ %> rowspan="2" <%}%> align="center"
										bgcolor="#EEF5F7">
									<% switch(i){ 
						case 0: out.println("Soma"); break;
						case 1: out.println("Inactivation (h)"); break;
						case 2: out.println("Soma"); break;
						case 3: out.println("Soma"); break;
						case 4: out.println("Dendrite"); break;
					   }
					   i++;
					%> <html:hidden name="conducType" property="cdConductanceType"
										indexed="true" /> <html:hidden name="conducType"
										property="cdNucleus" indexed="true" /> <html:hidden
										name="conducType" property="cdNucleusPre" indexed="true" /></td>


									<% if(i == 1){ %>
									<td rowspan="2" align="center" bgcolor="#EEF5F7">
									<% out.println("Na⁺"); 	%>
									</td>
									<td rowspan=2 align=center valign=center bgcolor="#EFF7F7"><html:text
										name="conducType" property="gmax" size="5" indexed="true" /></td>
									<td rowspan=2 align=center valign=center bgcolor="#EFF7F7"><html:text
										name="conducType" property="e" size="4" indexed="true" /></td>
									<%} else if(i == 3 || i == 4 || i == 5){ %>
									<td align="center" bgcolor="#EEF5F7">
									<% switch(i){
                                case 3: out.println("Fast K⁺"); break;
                                case 4: out.println("Slow K⁺"); break;
                                case 5: out.println("L-type Ca⁺⁺"); break;
                               }
                            %>
									</td>
									<td align=center bgcolor="#EFF7F7"><html:text
										name="conducType" property="gmax" size="5" indexed="true" /></td>
									<td align=center bgcolor="#EFF7F7"><html:text
										name="conducType" property="e" size="4" indexed="true" /></td>
									<%}%>

									<% if(i != 2){ %>
									<td align="center" valign="middle" bgcolor="#EEF5F7">
									<% switch(i){ 
						case 1: out.println("Activation (m)"); break;
						//case 2: out.println("Inactivation (h)"); break;
						case 3: out.println("Activation (n)"); break;
						case 4: out.println("Activation (q)"); break;
						case 5: out.println("Activation (p)"); break;
					   }
				%>
									</td>
									<%}%>

									<td align=center bgcolor="#EFF7F7"><html:text
										name="conducType" property="tpeak" size="4" indexed="true" /></td>
									<td align=center bgcolor="#EFF7F7"><html:text
										name="conducType" property="alpha" size="4" indexed="true" /></td>
									<td align=center bgcolor="#EFF7F7"><html:text
										name="conducType" property="beta" size="4" indexed="true" /></td>
								</tr>
							</logic:iterate>
						</logic:notEqual>
					</logic:notEqual>






					<logic:equal name="ConductanceForm" property="cdNucleus"
						value="IN_ext">
						<logic:iterate name="ConductanceForm" property="conductances"
							id="conducType" indexId="index" length="4">
							<tr>

								<td <% if (i == 0){ %> rowspan="2" <%}%> align="center"
									bgcolor="#EEF5F7">
								<% switch(i){ 
						case 0: out.println("not applicable"); break;
						case 1: out.println("Inactivation (h)"); break;
						case 2: out.println("not applicable"); break;
						case 3: out.println("not applicable"); break;
						case 4: out.println("not applicable"); break;
					   }
					   i++;
					%> <html:hidden name="conducType" property="cdConductanceType"
									indexed="true" /> <html:hidden name="conducType"
									property="cdNucleus" indexed="true" /> <html:hidden
									name="conducType" property="cdNucleusPre" indexed="true" /></td>


								<% if(i == 1){ %>
								<td rowspan="2" align="center" bgcolor="#EEF5F7">
								<% out.println("Na⁺"); 	%>
								</td>
								<td rowspan=2 align=center valign=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="gmax" size="5" indexed="true" /></td>
								<td rowspan=2 align=center valign=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="e" size="4" indexed="true" /></td>
								<%} else if(i == 3 || i == 4 || i == 5){ %>
								<td align="center" bgcolor="#EEF5F7">
								<% switch(i){
                                case 3: out.println("Fast K⁺"); break;
                                case 4: out.println("Slow K⁺"); break;
                                case 5: out.println("L-type Ca⁺⁺"); break;
                               }
                            %>
								</td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="gmax" size="5" indexed="true" /></td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="e" size="4" indexed="true" /></td>
								<%}%>

								<% if(i != 2){ %>
								<td align="center" valign="middle" bgcolor="#EEF5F7">
								<% switch(i){ 
						case 1: out.println("Activation (m)"); break;
						//case 2: out.println("Inactivation (h)"); break;
						case 3: out.println("Activation (n)"); break;
						case 4: out.println("Activation (q)"); break;
						case 5: out.println("Activation (p)"); break;
					   }
				%>
								</td>
								<%}%>

								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="tpeak" size="4" indexed="true" /></td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="alpha" size="4" indexed="true" /></td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="beta" size="4" indexed="true" /></td>
							</tr>
						</logic:iterate>
					</logic:equal>






					<logic:equal name="ConductanceForm" property="cdNucleus"
						value="IN_flex">
						<logic:iterate name="ConductanceForm" property="conductances"
							id="conducType" indexId="index" length="4">
							<tr>

								<td <% if (i == 0){ %> rowspan="2" <%}%> align="center"
									bgcolor="#EEF5F7">
								<% switch(i){ 
						case 0: out.println("not applicable"); break;
						case 1: out.println("Inactivation (h)"); break;
						case 2: out.println("not applicable"); break;
						case 3: out.println("not applicable"); break;
						case 4: out.println("not applicable"); break;
					   }
					   i++;
					%> <html:hidden name="conducType" property="cdConductanceType"
									indexed="true" /> <html:hidden name="conducType"
									property="cdNucleus" indexed="true" /> <html:hidden
									name="conducType" property="cdNucleusPre" indexed="true" /></td>


								<% if(i == 1){ %>
								<td rowspan="2" align="center" bgcolor="#EEF5F7">
								<% out.println("Na⁺"); 	%>
								</td>
								<td rowspan=2 align=center valign=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="gmax" size="5" indexed="true" /></td>
								<td rowspan=2 align=center valign=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="e" size="4" indexed="true" /></td>
								<%} else if(i == 3 || i == 4 || i == 5){ %>
								<td align="center" bgcolor="#EEF5F7">
								<% switch(i){
                                case 3: out.println("Fast K⁺"); break;
                                case 4: out.println("Slow K⁺"); break;
                                case 5: out.println("L-type Ca⁺⁺"); break;
                               }
                            %>
								</td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="gmax" size="5" indexed="true" /></td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="e" size="4" indexed="true" /></td>
								<%}%>

								<% if(i != 2){ %>
								<td align="center" valign="middle" bgcolor="#EEF5F7">
								<% switch(i){ 
						case 1: out.println("Activation (m)"); break;
						//case 2: out.println("Inactivation (h)"); break;
						case 3: out.println("Activation (n)"); break;
						case 4: out.println("Activation (q)"); break;
						case 5: out.println("Activation (p)"); break;
					   }
				%>
								</td>
								<%}%>

								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="tpeak" size="4" indexed="true" /></td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="alpha" size="4" indexed="true" /></td>
								<td align=center bgcolor="#EFF7F7"><html:text
									name="conducType" property="beta" size="4" indexed="true" /></td>
							</tr>
						</logic:iterate>
					</logic:equal>





				</logic:notEmpty>
			</table>
			</td>
		</tr>





		<TR>
			<TD colspan=3 align="left"><br>* with respect to resting
			potential
			</TD>
		</TR>

		<TR>
			<TD height="90" colspan=3 align="center" valign="middle"><input
				type="button" value="  Apply  " onClick="submit();"></TD>
		</TR>

	</TABLE>

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

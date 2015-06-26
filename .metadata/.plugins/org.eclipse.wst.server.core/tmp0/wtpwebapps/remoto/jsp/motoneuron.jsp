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
.motoneuron {
	background-color: #F5F9FA;
	height: auto;
	width: 85%;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	padding-top: 0%;
	padding-right: 3%;
	padding-bottom: 0%;
	padding-left: 3%;
}
</style>

<script>

function change()
{
	document.NeuronForm.task.value = "MN";
	document.NeuronForm.submit();
}

function properties()
{
	document.NeuronForm.action = 'mn_electrotonic.do?task=MN';
	document.NeuronForm.submit();
}

</script>

<link href="hint.css" rel="stylesheet" type="text/css" />
<link href="../jsp/apply.css" rel="stylesheet" type="text/css" />
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
<div class="motoneuron"><html:form method="post"
	action="/motoneuron">

	<html:hidden name="NeuronForm" property="task" value="saveMN" />

	<TABLE border=0>
		<tr>
			<td height="90" colspan=4 align="center"><b><font size="4"
				face="Arial, Helvetica, sans-serif">Parameters of the alpha
			motoneurons</font></b></td>
			
		</tr>

		<tr>
       	  <td height="50" align="left" bgcolor="#EEF5F7"><b>Joint:
			</b> <html:select name="NeuronForm" property="cdJoint">
				<html:option value="ankle">Ankle</html:option>
			</html:select></td>
  <td height="200" colspan=3 rowspan="3" align="center">
            <img src="schematic_geometry.png" width="400"/>
          </td>
            
        </tr>
		<tr>
			
<TD height=50 colspan="1" align="left" bgcolor="#EEF5F7"><b>Pool</b> <html:select
						name="NeuronForm" property="cdNucleus" onchange="change();">
						<html:optionsCollection name="NeuronForm" property="nuclei"
							label="name" value="cd" />
					</html:select>
                    </td>
                    
		</tr>
        
        <tr>
			
          <td height="50" align="center" bgcolor="#EEF5F7"><a href="#" onclick="properties();">Mean properties</a></td>
                    
		</tr>
        
        <tr>
			
                    <td colspan="4">&nbsp;</td>
		</tr>
        
		<tr>
			<td valign="top">
			<TABLE width="300" border=0>
				<TR>
					<td height="56" align="center" bgcolor="#EEF5F7"><b><u>General electrotonic parameters</u></b>
			</td>
			  </TR>
                
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Dendrite specific resistance
					[Ωcm²]</TD>
				</TR>
				<TR>
		<TD height=27 bgcolor="#EEF5F7">Dendrite Ca⁺⁺ channel threshold [mV]</TD>    
		  </TR>
		  <TR>
					<TD height=27 bgcolor="#EEF5F7">Soma threshold [mV]</TD>
			  </TR>
                <TR>
					<TD height=27 bgcolor="#EEF5F7">Soma specific resistance
					[Ωcm²]</TD>
				</TR>
				
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Axon threshold [mA]</TD>
				</TR>
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Axon conduction velocity [m/s]</TD>
				</TR>
				
				
				
				
                
		  <TR>
					<TD height=27 bgcolor="#EEF5F7">Membrane specific capacitance
					[µF/cm²]</TD>
			  </TR>
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Cytoplasm resistivity [Ωcm]</TD>
				</TR>
                

			</TABLE>
			</td>

			<logic:iterate name="NeuronForm" property="motoneurons" id="mnType"
				indexId="index">
				<td valign="top">
				<TABLE border=0 bgcolor="#DAEAEB">
					<TR>
						<TD height=27 colspan=2 align=center valign="middle"
							bgcolor="#C2DDE0"><b> <bean:write name="mnType"
							property="type" /></b> <html:hidden name="mnType" property="type"
							indexed="true" /> <html:hidden name="mnType" property="cdNucleus"
							indexed="true" /> <html:hidden name="mnType" property="category"
							indexed="true" /> <html:hidden name="mnType" property="quantity"
							indexed="true" /> <html:hidden name="mnType" property="active"
							indexed="true" /> <html:hidden name="mnType" property="cdNerve"
							indexed="true" /></TD>
					</TR>
					<TR>
						<TD width="100" height=27 align=center valign="middle"
							bgcolor="#C2DDE0">from</TD>
						<TD width="100" height=27 align=center valign="middle"
							bgcolor="#C2DDE0">to</TD>
					</TR>
                    
                    <TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="rmDend1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="rmDend2" size="6" indexed="true" /></TD>
					</TR>
					
					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="thresholdCa1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="thresholdCa2" size="6" indexed="true" /></TD>
				  </TR>
                    
					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="threshold1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="threshold2" size="6" indexed="true" /></TD>
					</TR>
					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="rmSoma1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="rmSoma2" size="6" indexed="true" /></TD>
					</TR>
					

					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="axonThreshold1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="axonThreshold2" size="6" indexed="true" /></TD>
					</TR>
					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="axonVelocity1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="axonVelocity2" size="6" indexed="true" /></TD>
					</TR>
					
					
                    
			  <TR>
						<TD height=27 colspan=2 align="center" valign="middle"
							bgcolor="#EEF5F7"><html:text name="mnType" property="cm"
							size="6" indexed="true" /></TD>
				  </TR>
					<TR>
						<TD height=27 colspan=2 align="center" valign="middle"
							bgcolor="#EEF5F7"><html:text name="mnType" property="ri"
							size="6" indexed="true" /></TD>
					</TR>
                    
					
					<TR>
						
					</TR>
					
				</TABLE>
				</td>
			</logic:iterate>

		</tr>
   
        <tr>
			<td colspan=4 >
            	<TABLE border=0>
				
                
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Neuromodulation level (value inside the interval [0,1])</TD>
                    <TD height=27 align="left" bgcolor="#EEF5F7">
                    	&nbsp;<html:text name="NeuronForm" property="neuromodulationLevel" size="6"/>
                          </TD>
				</TR>
				<TR>
		<TD height=27 bgcolor="#EEF5F7">Calcium channel voltage threshold coefficient of variation</TD> 
        <TD height=27 align="left" bgcolor="#EEF5F7">&nbsp;<html:text name="NeuronForm" property="CVThresholdCa" size="6"/> </TD>    
		  </TR>
                

			</TABLE>
          </td>
		</tr>
        <tr>
			<td colspan=4 >&nbsp;</td>
		</tr>
        
        <tr>
			<td valign="top">
			<TABLE width="300" border=0>
				<TR>
					<td height="56" align="center" bgcolor="#EEF5F7"><b><u>Geometric parameters</u></b>


			</td>
			  </TR>
				
                
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Soma diameter [µm]</TD>
				</TR>
                <TR>
					<TD height=27 bgcolor="#EEF5F7">Soma length [µm]</TD>
				</TR>
				
                
				
				
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Dendrite diameter [µm]</TD>
				</TR>
				<TR>
					<TD height=27 bgcolor="#EEF5F7">Dendrite length [µm]</TD>
				</TR>
                
				
				

				


			</TABLE>
			</td>

			<logic:iterate name="NeuronForm" property="motoneurons" id="mnType"
				indexId="index">
				<td valign="top">
				<TABLE border=0 bgcolor="#DAEAEB">
					<TR>
						<TD height=27 colspan=2 align=center valign="middle"
							bgcolor="#C2DDE0"><b> <bean:write name="mnType"
							property="type" /></b> <html:hidden name="mnType" property="type"
							indexed="true" /> <html:hidden name="mnType" property="cdNucleus"
							indexed="true" /> <html:hidden name="mnType" property="category"
							indexed="true" /> <html:hidden name="mnType" property="quantity"
							indexed="true" /> <html:hidden name="mnType" property="active"
							indexed="true" /> <html:hidden name="mnType" property="cdNerve"
							indexed="true" /></TD>
					</TR>
					<TR>
						<TD width="100" height=27 align=center valign="middle"
							bgcolor="#C2DDE0">from</TD>
						<TD width="100" height=27 align=center valign="middle"
							bgcolor="#C2DDE0">to</TD>
					</TR>
                    <TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="dsoma1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="dsoma2" size="6" indexed="true" /></TD>
					</TR>
                    <TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="lsoma1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="lsoma2" size="6" indexed="true" /></TD>
					</TR>
					
					
					
					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="ddend1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="ddend2" size="6" indexed="true" /></TD>
					</TR>
					<TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="ldend1" size="6" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="ldend2" size="6" indexed="true" /></TD>
					</TR>
                    
					
					
				</TABLE>
				</td>
			</logic:iterate>

		</tr>
        <tr>
			<td colspan=4 >&nbsp;</td>
        
        
            
            
        
        
        
        
        
        
        <TR>
          <td><table width="300">
          <tr>
					<td height="27" colspan=4 align="center" bgcolor="#EEF5F7"><u><strong>Additional configuration</strong></u></td>
			  </TR>
        <TR>
					<TD height=27 bgcolor="#EEF5F7">Index of MNs that will have signals
					stored</TD>
                    
		  </TR></table></td>
                <td colspan="3"><table width="100%"><tr>
                <logic:iterate name="NeuronForm" property="motoneurons" id="mnType"
				indexId="index">
				<td valign="top">
				<TABLE width="100%" border=0 bgcolor="#DAEAEB">
                <TR>
						<TD height=27 colspan=2 align=center valign="middle"
							bgcolor="#C2DDE0"><b> <bean:write name="mnType"
							property="type" /></b> <html:hidden name="mnType" property="type"
							indexed="true" /> <html:hidden name="mnType" property="cdNucleus"
							indexed="true" /> <html:hidden name="mnType" property="category"
							indexed="true" /> <html:hidden name="mnType" property="quantity"
							indexed="true" /> <html:hidden name="mnType" property="active"
							indexed="true" /> <html:hidden name="mnType" property="cdNerve"
							indexed="true" /></TD>
					</TR>
                    
                    <TR>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="indexStoreVm1" size="4" indexed="true" /></TD>
						<TD height=27 align="center" valign="middle" bgcolor="#EEF5F7"><html:text
							name="mnType" property="indexStoreVm2" size="4" indexed="true" /></TD>
					</TR>
				</TABLE>
				</td>
			</logic:iterate>
            </tr></table>
            
            
            
	  <tr>
			<td height="90" colspan=4 align="center" valign="middle"><span
				class="apply"> <input type="button" value="    Apply    "
				onclick="submit();" /> </span></td>
		</tr>
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

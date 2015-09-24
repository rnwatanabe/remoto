<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
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
.network {
	background-color: #F5F9FA;
	height: auto;
	width: 900px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	padding-top: 0px;
	padding-right: 5px;
	padding-bottom: 0px;
	padding-left: 5px;
}
</style>

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

<div class="network"><html:form method="post" action="/network">

	<html:hidden name="NeuronForm" property="task" value="save" />
	<table width="100%" border="0">
		<tr>
			<td colspan=2 width="600" height="90" align="center" valign="middle">
			<p><b><font size="4" face="Arial, Helvetica, sans-serif">Neural
			Elements</font></b></p>
			</td>
		</tr>
		<tr>
			<td width="50%" height="50" align="center" bgcolor="#EEF5F7"><b>Joint:
			</b> 
              <bean:write name="NetworkForm" property="cdJoint"></bean:write>
            
            
            </td>
			<td>&nbsp;</td>
		</tr>
		<tr>
		  <td colspan="2"><table width="100%">
		<tr>
			<td width="50%" valign="top">
			<table width="100%" bgcolor="#DAEAEB">
				<tr>
					<td bgcolor="#EEF5F7" align="center">
					<p><strong><em>Ankle Extensors (Plantarflexors)</em></strong></p>
					</td>

				</tr>
				<tr>
					<td bgcolor="#EEF5F7" align="left">
					<p>Innervating nerve:<em> Posterior Tibial Nerve (PTN)</em></p>
					</td>

			  </tr>
				<tr>
					<td width="10" height="30" bgcolor="#EEF5F7"><html:checkbox
						name="NetworkForm" property="activeAnkleExtensorMNs"
						onchange="submit();" />Motoneurons</td>

				</tr>

				<logic:equal name="NetworkForm" property="activeAnkleExtensorMNs"
					value="true">
					<tr>
						<td>
						<table width="100%">
							<tr>
								<td width="100%" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">Soleus
								(SOL)</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" length="3">
									<td>
									<table bgcolor="#EEF5F7">
										<tr>
											<td width='10' height="25" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td width='75' height="25" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>

											<td width='70' height="25" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>

					<tr>
						<td>
						<table width="100%" #EEF5F7>
							<tr>
								<td width="100%" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">Medial
								gastrocnemius (MG)</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="6" length="3">
									<td>
									<table bgcolor="#EEF5F7">
										<tr>
											<td width='10' height="25" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td width='75' height="25" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>
											<td width='70' height="25" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>

					<tr>
						<td>
						<table width="100%">
							<tr>
								<td width="100%" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">Lateral
								gastrocnemius (LG)</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="12" length="3">
									<td>
									<table bgcolor="#EEF5F7">
										<tr>
											<td width='10' height="25" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td width='75' height="25" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>
											<td width='70' height="25" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>
				</logic:equal>


				<tr>
					<td width="10" height="30" bgcolor="#EEF5F7"><html:checkbox
						name="NetworkForm" property="activeAnkleExtensorINs"
						onchange="submit();" />Interneurons</td>

				</tr>

				<logic:equal name="NetworkForm" property="activeAnkleExtensorINs"
					value="true">
					<tr>
						<td>
						<table width="100%">
							<tr>
								<td width="100%" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">SOL-MG-LG</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="24" length="4">
									<td>
									<table width="10" bgcolor="#EEF5F7">
										<tr>
											<td width='10' height="0" align="center" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td width='75' height="0" align="center" bgcolor="#EEF5F7">
											<bean:write name="nucleus" property="type" /></td>
											<td width='70' height="0" align="center" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>
				</logic:equal>


				<tr>
					<td width="10" height="30" bgcolor="#EEF5F7"><html:checkbox
						name="NetworkForm" property="activeAnkleExtensorSAs"
						onchange="submit();" />Sensory Afferents</td>

				</tr>

				<logic:equal name="NetworkForm" property="activeAnkleExtensorSAs"
					value="true">
					<tr>
						<td>
						<table width="100%">
							<tr>
								<td width="170" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">SOL</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="3" length="3">
									<td align="center" bgcolor="#EEF5F7">
									<table width="10" bgcolor="#EEF5F7">
										<tr>
											<td bgcolor="#EEF5F7"><html:checkbox name="nucleus"
												property="active" indexed="true" onchange="submit();"/></td>
											<td bgcolor="#EEF5F7"><bean:write name="nucleus"
												property="type" /></td>
											<td bgcolor="#EEF5F7"><html:text name="nucleus"
												property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>

					<tr>
						<td>
						<table width="100%">
							<tr>
								<td width="170" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">MG</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="9" length="3">
									<td align="center" bgcolor="#EEF5F7">
									<table width="10" bgcolor="#EEF5F7">
										<tr>
											<td width='10' height="25" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td width='75' height="25" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>
											<td width='70' height="25" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>

					<tr>
						<td>
						<table width="100%">
							<tr>
								<td width="170" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">LG</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="15" length="3">
									<td align="center" bgcolor="#EEF5F7">
									<table width="10" bgcolor="#EEF5F7">
										<tr>
											<td width='10' height="25" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td width='75' height="25" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>
											<td width='70' height="25" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>
				</logic:equal>

			</table>
			</td>
			<td valign="top">
			<table width="100%" bgcolor="#DAEAEB">
				<tr>
					<td bgcolor="#EEF5F7" align="center">
					<p><strong><em>Ankle Flexor (Dorsiflexor)</em></strong></p>
					</td>
				</tr>
				<tr>
					<td bgcolor="#EEF5F7" align="left">
					<p>Innervating nerve: <em>Common Peroneal Nerve (CPN)</em></p>
					</td>

			  </tr>
				<tr>

					<td width="10" height="30" bgcolor="#EEF5F7"><html:checkbox
						name="NetworkForm" property="activeAnkleFlexorMNs"
						onchange="submit();" />Motoneurons</td>
				</tr>

				<logic:equal name="NetworkForm" property="activeAnkleFlexorMNs"
					value="true">
					<tr>
						<td bgcolor="#EEF5F7">
						<table width="100%" align="center" bgcolor="#DAEAEB">


							<tr>
								<td width="140" align="center" valign="middle" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">Tibial
								anterior (TA)</font></td>
								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="18" length="3">

									<td align="center" bgcolor="#EEF5F7">
									<table width="10" bgcolor="#EEF5F7">
										<tr>
											<td align="center" valign="middle" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td align="center" valign="middle" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>
											<td align="center" valign="middle" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
										</tr>
									</table>
									</td>
									<html:hidden name="nucleus" property="type" indexed="true" />
									<html:hidden name="nucleus" property="cdNucleus" indexed="true" />

								</logic:iterate>
							</tr>




						</table>
						</td>




					</tr>
				</logic:equal>

				<tr>

					<td width="10" height="30" bgcolor="#EEF5F7"><html:checkbox
						name="NetworkForm" property="activeAnkleFlexorINs"
						onchange="submit();" />Interneurons</td>
				</tr>

				<logic:equal name="NetworkForm" property="activeAnkleFlexorINs"
					value="true">
					<tr>
						<td bgcolor="#EEF5F7">
						<table width="100%" align="center" bgcolor="#DAEAEB">

							<tr>

								<td width="140" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">TA</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="28" length="4">

									<td align="center" bgcolor="#EEF5F7">
									<table width="10" bgcolor="#EEF5F7">
										<tr>
											<td align="center" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td align="center" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>
											<td height="0" align="center" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>
				</logic:equal>


				<tr>

					<td width="10" height="30" bgcolor="#EEF5F7"><html:checkbox
						name="NetworkForm" property="activeAnkleFlexorSAs"
						onchange="submit();" />Sensory Afferents</td>
				</tr>

				<logic:equal name="NetworkForm" property="activeAnkleFlexorSAs"
					value="true">
					<tr>
						<td bgcolor="#EEF5F7">
						<table width="100%" align="center" bgcolor="#DAEAEB">

							<tr>

								<td width="140" align="center" bgcolor="#C2DDE0"><font
									size="2" face="Arial, Helvetica, sans-serif">TA</font></td>

								<logic:iterate name="NetworkForm" property="neurons"
									id="nucleus" indexId="index" offset="21" length="3">
									<td align="center" bgcolor="#EEF5F7">
									<table width="10" bgcolor="#EEF5F7">
										<tr>
											<td width='10' align="center" bgcolor="#EEF5F7"><html:checkbox
												name="nucleus" property="active" indexed="true" onchange="submit();"/></td>
											<td width='75' align="center" bgcolor="#EEF5F7"><bean:write
												name="nucleus" property="type" /></td>
											<td width='70' align="center" bgcolor="#EEF5F7"><html:text
												name="nucleus" property="quantity" size="2" indexed="true" /></td>
											<html:hidden name="nucleus" property="type" indexed="true" />
											<html:hidden name="nucleus" property="cdNucleus"
												indexed="true" />
										</tr>
									</table>
									</td>
								</logic:iterate>

							</tr>
						</table>
						</td>
					</tr>
				</logic:equal>

			</table>
			</td>

		</tr>
        </table></td></tr>
        <tr>
			<td height="90" colspan=2 align="center"></td>
		</tr>
        <tr>
          <td width="10" height="30" bgcolor="#EEF5F7">&nbsp;&nbsp;&nbsp;<strong>Anatomical position of spinal neurons</strong></td>
        </tr>
        <tr>
<td height="90" align="center">
            	<table width="100%" border="0" bgcolor="#DAEAEB">
            <tr>
				<td width="200" height="35" valign="top" bgcolor="#EEF5F7">&nbsp;</td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
                	Initial
                </td>
                 <td align="center" valign="middle" bgcolor="#EEF5F7">
                	Final
                </td>
			</tr>
			<tr>
				<td height="25" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;SOL MN pool
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xIniSOL"
					size="6"/>
                </td>
                 <td align="center" valign="middle" bgcolor="#EEF5F7">
                	<html:text name="NetworkForm" property="xEndSOL"
					size="6"/>
                </td>
			</tr>
            <tr>
				<td height="25" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;MG MN pool
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xIniMG"
					size="6"/>
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
                	<html:text name="NetworkForm" property="xEndMG"
					size="6"/>
                </td>
			</tr>
            
            <tr>
				<td height="25" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;LG MN pool
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xIniLG"
					size="6"/>
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
                	<html:text name="NetworkForm" property="xEndLG"
					size="6"/>
                </td>
			</tr>
            <tr>
				<td height="25" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;TA MN pool
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xIniTA"
					size="6"/>
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
                	<html:text name="NetworkForm" property="xEndTA"
					size="6"/>
                </td>
			</tr>
            <tr>
				<td height="25" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;Extensors IN pool
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xIniIN_ext"
					size="6"/>
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xEndIN_ext"
					size="6"/>
                </td>
			</tr>
            <tr>
				<td height="25" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;Flexors (TA) IN pool
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xIniIN_flex"
					size="6"/>
                </td>
                <td align="center" valign="middle" bgcolor="#EEF5F7">
               	  <html:text name="NetworkForm" property="xEndIN_flex"
					size="6"/>
                </td>
			</tr>
            
            
            
	</table>
    </td>
    <td height="222" align="center" valign="middle" bgcolor="#D1DCDC"><IMG SRC="/remoto/servlet/ServletChartGenerator?cdSimulation=neuronPositions&id=<%=Math.random()%>" alt="Input graphic" BORDER=0 /></td>
		</tr>
        
		<tr>
			<td height="90" colspan=2 align="center"><input type="button"
				value="  Apply  " onclick="submit();" /></td>
		</tr>
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

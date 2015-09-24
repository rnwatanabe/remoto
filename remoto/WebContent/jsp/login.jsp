
<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ page import="br.remoto.model.vo.User"%>
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
.login {
	background-color: #F5F9FA;
	padding: 50px;
	height: auto;
	width: 350px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
}
</style>


<script>

function newUser()
{
	document.LoginForm.task.value = "new";
	document.LoginForm.submit();
}

function register()
{
	document.LoginForm.task.value = "register";
	document.LoginForm.submit();
}

function login()
{
	document.LoginForm.task.value = "login";
	document.LoginForm.submit();
}

function logout()
{
	document.LoginForm.task.value = "logout";
	document.LoginForm.submit();
}

function exclude()
{
	if( confirm("Are you sure deleting user's configuration?") == true )
	{
		document.LoginForm.task.value = "exclude";
		document.LoginForm.submit();
	}	
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
        
      </ul>
    </li>
    <li><a href="/remoto/start.do">Run</a></li>
    <li><a href="/remoto/results.do?opt=chart_img">Results</a>      </li>
    <li><a href="/remoto/login.do">Login</a></li>
  </ul>
</div>
  <!-- InstanceBeginEditable name="EditRegion1" -->
<p>&nbsp;</p>
<div class="login"><html:form method="post" action="/login">

	<html:hidden name="LoginForm" property="task" value="login" />

	<script>
<%
String msg = (String)request.getAttribute("msg");
if( msg == null ) 
	msg = "";
%>

var msg = "<%= msg %>";
</script>

	<table width="100%" border="0">
		<tr>
			<td width="300" align="center"><b><font size="4"
				face="Arial, Helvetica, sans-serif">User authentication</font></b></td>
		</tr>
		<tr>
			<td width="300">&nbsp;</td>
		</tr>
		<tr>
			<td width="300" align="left">
			<table border="0">

				<tr>
					<td width="100%">
					<%
				User user = (User)session.getAttribute("user");
				String name = (String)user.getName();
				
				if( name.isEmpty() ) name = (String)user.getCdUser();
				%> <br> <font face=verdana size=2> You are currently
					logged as: <b><%=name%></b>
					</td>
				</tr>

				<tr>
					<td width="500">&nbsp;</td>
				</tr>
				<tr>
					<td width="500">
					<table border="0">

						<logic:equal name="LoginForm" property="register" value="true">
							<tr>
								<td width="50%" align=left><b>Name (Optional)</b></td>
								<td width="50%" colspan="2" align=left valign="top"><html:text
									name="LoginForm" property="nmUser" size="25" /></td>
							</tr>

							<tr>
								<td width="50%"><b>E-mail (Optional)*</b></td>
								<td width="50%" colspan="2" align="left" valign="top"><html:text
									name="LoginForm" property="email" size="25" /></td>
							</tr>

							<tr>
								<td width="50%"><b>Institution (Optional)</b></td>
								<td width="50%" colspan="2" align="left" valign="top"><html:text
									name="LoginForm" property="institution" size="25" /></td>
							</tr>
						</logic:equal>

						<tr>
							<td width="50%"></td>
						</tr>

						<tr>
							<td width="50%"><b>Username</b></td>
							<td width="50%" colspan="2" align="left" valign="top"><html:text
								name="LoginForm" property="cdUser" size="25" /></td>
						</tr>
						<tr>
							<td width="50%"><b>Password</b></td>
							<td width="50%" align="left" valign="top"><html:password
								name="LoginForm" property="password" size="25" />
						</tr>
						<logic:equal name="LoginForm" property="register" value="true">
							<tr>
								<td width="50%"><b>Confirm password</b></td>
								<td width="50%" align="left" valign="top"><html:password
									name="LoginForm" property="password2" size="25" />
							</tr>
						</logic:equal>

						<tr align="center" valign="middle">
							<td colspan=3>&nbsp;</td>
						</tr>
						<tr align="center" valign="middle">
							<td colspan=3><logic:notEqual name="LoginForm"
								property="register" value="true">
								<input type="button" value=" Login " onClick="login();">
								<input type="button" value="Delete user" onclick="exclude();" />
								<input type="button" value="  Logout  " onclick="logout();" />
							</logic:notEqual> <logic:equal name="LoginForm" property="register" value="true">
								<input type="button" value=" Register " onclick="register();" />
							</logic:equal></td>
						</tr>
						<tr>
							<td colspan=3>&nbsp;</td>
						</tr>
						<logic:equal name="LoginForm" property="register" value="true">
							<tr align="center" valign="middle">
								<td colspan=3><strong>*If you want to subscribe to
								our mail-list, please inform a valid e-mail address.</strong></td>
							</tr>
						</logic:equal>

						<tr>
							<td colspan=3>&nbsp;</td>
						</tr>

						<logic:equal name="LoginForm" property="register" value="false">
							<tr>
								<td colspan=3><a href="#" onClick="newUser();"> <img
									src="image/seta.gif" border=0> Create an account</a></td>
							</tr>
						</logic:equal>

					</table>
					</td>
				</tr>

			</table>

			</td>
		</tr>
	</table>

</html:form></div>

<script>
  if(msg != '') 	alert(msg);
  </script>

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
